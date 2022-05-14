### vnc server - headless gnome

```bash
sudo apt install tigervnc-standalone-server
vncserver -SecurityTypes None
vncserver -kill :1
```

~/.vnc/xstartup
```bash
#!/bin/sh
[ -x /etc/vnc/xstartup ] && exec /etc/vnc/xstartup
[ -r $HOME/.Xresources ] && xrdb $HOME/.Xresources
vncconfig -iconic &
dbus-launch --exit-with-session gnome-session &
```

### vnc client

```bash
sudo apt install gvncviewer
ssh -L 5900:localhost:5901 vncserver
gvncviewer localhost
```

~/.local/share/applications/gvncviewer.desktop
```
[Desktop Entry]
Name=vnc
Comment=vnc
Exec=gvncviewer localhost
Type=Application
Categories=Utility;
Keywords=vnc;
```
