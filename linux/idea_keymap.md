### macOS keymap file for Idea

```bash
jar xf ~/opt/idea-IC-193.7288.26/lib/resources.jar keymaps/\$default.xml "keymaps/Mac OS X.xml" "keymaps/Mac OS X 10.5+.xml"
javac IdeaKeymap.java
java IdeaKeymap
mv "macOS copy.xml" ~/.IdeaIC2019.3/config/keymaps/
rm IdeaKeymap.*
rm -rf keymaps/
```

IdeaKeymap.java
```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IdeaKeymap {

  public static final String KEY1 = "control";
  public static final String KEY2 = "alt"; // option
  public static final String KEY3 = "meta"; // command

  public static final Pattern PATTERN_ACTION = Pattern.compile("\\s*<action id\\s*=\"([\\w.$]+)\"\\s*/?>");
  public static final Pattern PATTERN_KEYBOARD = Pattern.compile(
      "\\s*<keyboard-shortcut first-keystroke=\"[\\w\\s]+\"\\s?(second-keystroke=\"\\w+\")?(replace-all=\"true\")?/>");
  public static final Pattern PATTERN_MOUSE = Pattern.compile("\\s*<mouse-shortcut keystroke=\"[\\w\\s]+\"\\s*/>");
  public static final Pattern PATTERN_COMMENT = Pattern.compile("\\s*<!--[\\w\\s?]+-->");
  public static final Pattern PATTERN_GESTURE = Pattern.compile(
      "\\s*<keyboard-gesture-shortcut keystroke=\"meta 1\" modifier=\"dblClick\"/>");

  static Map<String, List<String>> map = new LinkedHashMap<>();

  static String remap(String s, String control, String alt, String meta) {
    String[] strings = s.split("\"");
    strings[1] = strings[1].replace("control", "<c>").replace("alt", "<a>").replace("meta", "<m>")
        .replace("<c>", control).replace("<a>", alt).replace("<m>", meta);
    return String.join("\"", strings);
  }

  static void read(String fileName, UnaryOperator<String> remap) throws IOException {
    try (Stream<String> stream = Files.lines(Paths.get("keymaps", fileName))) {
      AtomicReference<String> key = new AtomicReference<>();
      stream
          .filter(s -> !s.contains("keymap"))
          .map(s -> (s + '\n').split("(?<=>)"))
          .flatMap(Arrays::stream).forEach(s -> {
        Matcher m = PATTERN_ACTION.matcher(s);
        boolean matches = m.matches();
        if (matches) {
          key.set(m.group(1));
          map.remove(m.group(1));
          map.put(m.group(1), new ArrayList<>());
        }
        if (PATTERN_KEYBOARD.matcher(s).matches() || PATTERN_MOUSE.matcher(s).matches()) {
          s = remap.apply(s);
          matches = true;
        }
        matches |= s.trim().isEmpty() ||
            PATTERN_COMMENT.matcher(s).matches() ||
            PATTERN_GESTURE.matcher(s).matches() ||
            "</action>".equals(s.trim());
        if (!matches) throw new IllegalStateException(s);
        map.get(key.get()).add(s);
      });
    }
  }

  public static void main(String[] args) throws IOException {
    map.put("<", Collections.singletonList("<keymap version=\"1\" name=\"macOS copy\" parent=\"Mac OS X 10.5+\">\n"));
    read("$default.xml", s -> remap(s, KEY3, KEY2, KEY3));
    read("Mac OS X.xml", s -> remap(s, KEY1, KEY2, KEY3));
    read("Mac OS X 10.5+.xml", s -> remap(s, KEY1, KEY2, KEY3));
    map.put(">", Collections.singletonList("</keymap>\n"));
    Files.write(Paths.get("macOS copy.xml"),
        map.values().stream().flatMap(List::stream).collect(Collectors.joining()).getBytes(),
        StandardOpenOption.CREATE_NEW);
  }
}
```

