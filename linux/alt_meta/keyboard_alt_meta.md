### replace alt and meta in keyboard driver

/etc/udev/hwdb.d/10-alt-meta.hwdb
```
evdev:atkbd:dmi:*
 KEYBOARD_KEY_db=leftalt     # bind leftwin to leftalt
 KEYBOARD_KEY_38=leftmeta    # bind leftalt to leftwin
```
