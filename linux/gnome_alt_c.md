### alt-c alt-v for copy and paste in GNOME 3

~/.themes/Default/gtk-3.0/gtk-keys.css
```
@binding-set gtk-mac-text-entry
{
  bind "<alt>c" { "copy-clipboard" () };
  bind "<alt>v" { "paste-clipboard" () };
}

entry {
  -gtk-key-bindings: gtk-mac-text-entry;
}

textview {
  -gtk-key-bindings: gtk-mac-text-entry;
}
```
