### alt-c alt-v for copy and paste in GNOME 3

~/.themes/Default/gtk-3.0/gtk-keys.css

~/.config/gtk-3.0/gtk.css

```
@binding-set gtk-mac-text-entry
{
  bind "<alt>c" { "copy-clipboard" () };
  bind "<alt>v" { "paste-clipboard" () };
  bind "<alt>x" { "cut-clipboard" () };
  bind "<alt>a" { "select-all" (1) };
  bind "<shift><alt>a" { "select-all" (0) };
}

textview {
  -gtk-key-bindings: gtk-mac-text-entry;
}
```
