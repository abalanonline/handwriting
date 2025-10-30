/*
 * Copyright (C) 2025 Aleksei Balan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PdfFix {

  public static final String[] FIX_MAP = {"d41d8cd9-8f00-3204-a980-0998ecf8427e", "0000",};
  Map<UUID, byte[]> fixMap;
  Map<UUID, Integer> replacements = new LinkedHashMap<>();

  public static void debugStream(COSStream stream) throws IOException {
    byte[] bytes;
    try (InputStream inputStream = stream.createInputStream()) {
      bytes = inputStream.readAllBytes();
    }
    for (int i = 0, il = 0; i < bytes.length; i++) {
      if (bytes[i] != 0x0A) continue;
      int i0 = il;
      il = i + 1;
      if (bytes[i0] != '(' || bytes[i - 1] != 0x0D || bytes[i - 2] != '\'' || bytes[i - 3] != ')') continue;
      StringBuilder s = new StringBuilder();
      byte[] range = Arrays.copyOfRange(bytes, i0 + 1, i - 3);
      for (byte b : range) s.append(String.format("%02X", b));
      UUID uuid = UUID.nameUUIDFromBytes(range);
      System.out.println(uuid);
      System.out.println(s);
    }
  }

  public void fixStream(COSStream stream) throws IOException {
    byte[] bytes;
    try (InputStream inputStream = stream.createInputStream()) {
      bytes = inputStream.readAllBytes();
    }
    for (int i = 0, il = 0; i < bytes.length; i++) {
      if (bytes[i] != 0x0A) continue;
      int i0 = il;
      il = i + 1;
      if (bytes[i0] != '(' || bytes[i - 1] != 0x0D || bytes[i - 2] != '\'' || bytes[i - 3] != ')') continue;
      int from = i0 + 1;
      int to = i - 3;
      byte[] range = Arrays.copyOfRange(bytes, from, to);
      UUID uuid = UUID.nameUUIDFromBytes(range);
      byte[] fix = fixMap.get(uuid);
      if (fix == null) continue;
      byte[] bytes2 = new byte[bytes.length + fix.length - range.length];
      System.arraycopy(bytes, 0, bytes2, 0, from);
      System.arraycopy(fix, 0, bytes2, from, fix.length);
      System.arraycopy(bytes, to, bytes2, from + fix.length, bytes.length - to);
      bytes = bytes2;
      i += fix.length - range.length;
      il = i + 1;
      replacements.put(uuid, replacements.getOrDefault(uuid, 0) + 1);
    }
    try (OutputStream outputStream = stream.createOutputStream()) {
      outputStream.write(bytes);
    }
  }

  public PdfFix() throws IOException {
    fixMap = new HashMap<>();
    for (int i = 0; i < FIX_MAP.length; i += 2) {
      UUID uuid = UUID.fromString(FIX_MAP[i]);
      String s = FIX_MAP[i + 1];
      byte[] bytes = new byte[s.length() / 2];
      for (int j = 0; j < s.length() / 2; j++) bytes[j] = (byte) ((Character.digit(s.charAt(2 * j), 16) << 4)
          + Character.digit(s.charAt(2 * j + 1), 16));
      fixMap.put(uuid, bytes);
    }

    PDDocument document = Loader.loadPDF(Files.readAllBytes(Paths.get("assets/text.pdf")));
    if (document.isEncrypted()) throw new IllegalStateException();
    for (PDPage page : document.getPages()) {
      PDResources resources = page.getResources();
      for (COSName xObjectName : resources.getXObjectNames()) {
        COSStream stream = resources.getXObject(xObjectName).getStream().getCOSObject();
        debugStream(stream);
        fixStream(stream);
      }
      Iterator<PDStream> contentStreams = page.getContentStreams();
      while (contentStreams.hasNext()) {
        COSStream stream = contentStreams.next().getCOSObject();
        //debugStream(stream);
        fixStream(stream);
      }
    }
    document.save(Files.newOutputStream(Paths.get("assets/text_fixed.pdf")));
    document.close();
    for (Map.Entry<UUID, Integer> entry : replacements.entrySet()) System.out.println(entry.getKey() + " " + entry.getValue());
  }

  public static void main(String[] args) throws IOException {
    new PdfFix();
  }
}
