import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class QuineTest {

  @Test
  void test() throws IOException {
    // never written a quine in my life, this is my first one
    byte[] expected = Files.readAllBytes(Paths.get("src/main/java/Quine.java"));
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(stream));
    Quine.main(new String[0]);
    byte[] actual = stream.toByteArray();
    assertEquals(new String(expected), new String(actual));
    assertArrayEquals(expected, actual);
  }

}
