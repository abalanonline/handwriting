### GNOME 3

### switch applications and windows with the same shortcut

Settings - Keyboard Shortcuts - Switch windows

### typematic rate 30 cps, typematic delay 250 ms

```bash
gsettings set org.gnome.desktop.peripherals.keyboard repeat-interval 30
gsettings set org.gnome.desktop.peripherals.keyboard delay 250
```

### disable suspend

/etc/gdm3/greeter.dconf-defaults
```bash
[org/gnome/settings-daemon/plugins/power]
sleep-inactive-ac-type='blank'
```

### disable update notifications

```bash
gsettings set org.gnome.software allow-updates false
```

### icons to start terminals with different colors/profiles

~/.local/share/applications/gterm2.desktop
```bash
[Desktop Entry]
Type=Application
Name=Terminal 2
Exec=gnome-terminal --profile=2
Icon=utilities-terminal
```
