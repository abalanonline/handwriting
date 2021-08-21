package ab;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Map.entry;

public class HidGadgetTest {

  public static final Map<String, Integer> kmod = Map.ofEntries(
      entry("--left-ctrl", 0x01),
      entry("--right-ctrl", 0x10),
      entry("--left-shift", 0x02),
      entry("--right-shift", 0x20),
      entry("--left-alt", 0x04),
      entry("--right-alt", 0x40),
      entry("--left-meta", 0x08),
      entry("--right-meta", 0x80)
  );

  public static final Map<String, Integer> kval = Map.ofEntries(
      entry("--return", 0x28),
      entry("--esc", 0x29),
      entry("--bckspc", 0x2a),
      entry("--tab", 0x2b),
      entry("--spacebar", 0x2c),
      entry("--caps-lock", 0x39),
      entry("--f1", 0x3a),
      entry("--f2", 0x3b),
      entry("--f3", 0x3c),
      entry("--f4", 0x3d),
      entry("--f5", 0x3e),
      entry("--f6", 0x3f),
      entry("--f7", 0x40),
      entry("--f8", 0x41),
      entry("--f9", 0x42),
      entry("--f10", 0x43),
      entry("--f11", 0x44),
      entry("--f12", 0x45),
      entry("--insert", 0x49),
      entry("--home", 0x4a),
      entry("--pageup", 0x4b),
      entry("--del", 0x4c),
      entry("--end", 0x4d),
      entry("--pagedown", 0x4e),
      entry("--right", 0x4f),
      entry("--left", 0x50),
      entry("--down", 0x51),
      entry("--kp-enter", 0x58),
      entry("--up", 0x52),
      entry("--num-lock", 0x53)
  );

  public static byte[] keyboardFillReport(String bufStr, AtomicBoolean hold) {
    Integer v;
    List<Integer> keys = new ArrayList<>();
    int modifiers = 0;

    for (String tok : bufStr.split("\\s+")) {
      if (tok.isEmpty()) continue;
      if (tok.equals("--quit")) {
        return null;
      }

      if (tok.equals("--hold")) {
        hold.set(true);
        continue;
      }

      v = kval.get(tok);
      if (v != null) {
        keys.add(v);
        continue;
      }

      char c = tok.charAt(0);
      if (c >= 'a' && c <= 'z') {
        keys.add(c - 'a' + 0x04);
        continue;
      }

      v = kmod.get(tok);
      if (v != null) {
        modifiers = modifiers | v;
        continue;
      }

      System.err.println("unknown option: " + tok);
    }

    byte[] report = new byte[8];
    report[0] = (byte) modifiers;
    for (int i = 0; i < Math.min(6, keys.size()); i++) {
      report[i + 2] = keys.get(i).byteValue();
    }
    return report;
  }

  public static final Map<String, Integer> mmod = Map.ofEntries(
      entry("--b1", 0x01),
      entry("--b2", 0x02),
      entry("--b3", 0x04)
  );

  public static byte[] mouseFillReport(String bufStr, AtomicBoolean hold) {
    Integer v;
    byte[] report = new byte[3];
    int mvt = 0;
    for (String tok : bufStr.split("\\s+")) {
      if(tok.equals("--quit")) {
        return null;
      }

      if(tok.equals("--hold")) {
        hold.set(true);
        continue;
      }

      v = mmod.get(tok);
      if (v != null) {
        report[0] = (byte)(report[0] | v);
        continue;
      }

      if (mvt >= 2) break;
      try {
        report[1 + mvt++] = (byte) Integer.parseInt(tok);
        continue;
      } catch (NumberFormatException e) {
        // do nothing
      }

      System.err.println("unknown option: " + tok);
    }
    return report;
  }

  public static final Map<String, Integer> jmod = Map.ofEntries(
      entry("--b1", 0x10),
      entry("--b2", 0x20),
      entry("--b3", 0x40),
      entry("--b4", 0x80),
      entry("--hat1", 0x00),
      entry("--hat2", 0x01),
      entry("--hat3", 0x02),
      entry("--hat4", 0x03),
      entry("--hatneutral", 0x04)
  );

  public static byte[] joystickFillReport(String bufStr, AtomicBoolean hold) {
    Integer v;
    byte[] report = new byte[4];
    int mvt = 0;

    hold.set(true);

    /* set default hat position: neutral */
    report[3] = (byte)0x04;

    for (String tok : bufStr.split("\\s+")) {
      if(tok.equals("--quit")) {
        return null;
      }

      v = jmod.get(tok);
      if (v != null) {
        report[3] = (byte)(report[3] & 0xF0 | v);
        continue;
      }

      if (mvt >= 3) break;
      try {
        report[mvt++] = (byte) Integer.parseInt(tok);
        continue;
      } catch (NumberFormatException e) {
        // do nothing
      }

      System.err.println("unknown option: " + tok);
    }
    return report;
  }

  public static void printOptions(char c) {
    if(c == 'k') {
      System.out.println("	keyboard options:\n		--hold");
      for (String opt : kmod.keySet()) {
        System.out.println("\t\t" + opt);
      }
      System.out.println("\n	keyboard values:\n		[a-z] or");
      boolean firstColumn = false;
      for (String opt : kval.keySet()) {
        firstColumn = !firstColumn;
        System.out.printf("\t\t%-8s%s", opt, firstColumn ? "\n" : "");
      }
      System.out.println();
    } else if(c == 'm') {
      System.out.println("	mouse options:\n		--hold");
      for (String opt : mmod.keySet()) {
        System.out.println("\t\t" + opt);
      }
      System.out.println("\n	mouse values:\n		Two signed numbers\n--quit to close");
    } else {
      System.out.println("	joystick options:");
      for (String opt : jmod.keySet()) {
        System.out.println("\t\t" + opt);
      }
      System.out.println("\n	joystick values:\n		three signed numbers\n--quit to close");
    }
  }

//  public static void main(String[] args) {
//    String8[] cArgs = new String8[args.length + 1];
//    cArgs[0] = s8(DemoTranslation.class.getSimpleName());
//    for(int i = 0; i < args.length; i++) {
//      cArgs[i + 1] = s8(args[i]);
//    }
//    int ret = main(cArgs.length, cArgs);
//    if(ret != 0) {
//      System.exit(ret);
//    }
//  }

  public static void main(String[] args) {
    byte[] toSend;
    AtomicBoolean hold = new AtomicBoolean(false);

    if(args.length < 2) {
      System.err.println("Usage: HidGadgetTest devname mouse|keyboard|joystick");
      System.exit(1);
    }

    char dev = args[1].charAt(0);
    if(dev != 'k' && dev != 'm' && dev != 'j') {
      System.exit(2);
    }

    printOptions(dev);

    Scanner input = new Scanner(System.in);
    try (FileOutputStream outputStream = new FileOutputStream(args[0])) {

      while (input.hasNext()) {
  //      if(fdIsset(fd, rfds)) {
  //        cmdLen = (int)read(fd, buf, BUF_LEN - 1);
  //        System.out.print("recv report:");
  //        for(int i = 0; i < cmdLen; i++) {
  //          System.out.printf(" %02x", buf.get(i));
  //        }
  //        System.out.println();
  //      }

        String bufStr = input.nextLine();
        hold.set(false);

        if(dev == 'k') {
          toSend = keyboardFillReport(bufStr, hold);
        } else if(dev == 'm') {
          toSend = mouseFillReport(bufStr, hold);
        } else {
          toSend = joystickFillReport(bufStr, hold);
        }
        if(toSend == null) {
          break;
        }

        outputStream.write(toSend);
        if (!hold.get()) {
          outputStream.write(new byte[toSend.length]);
        }

      }
    // close(fd);
    } catch (IOException e) {
      throw new java.io.UncheckedIOException(e);
    }
  }

}
