### Google Drive

```bash
sudo apt install rclone
sudo rclone selfupdate
https://rclone.org/drive/
```

### compare

```bash
rclone --links check ~/drive drive: --differ - 2>/dev/null
rclone --links check drive: ~/drive
```

### copy

```bash
rclone --links -v copy drive:/documents ~/drive/documents
rclone --links -v copy ~/drive drive:
rclone -v copyto ~/drive/readme.txt drive:/readme.txt

rclone --links -v sync ~/drive drive:
```

### fix

```bash
find ~/drive/ -name *.rclonelink
find ~/drive/ -name *.rclonelink | xargs rm
```

### mount

~/.local/share/applications/mount_google_drive.desktop
```
[Desktop Entry]
Name=mount google drive
Exec=xterm -T "google drive mounted" -e "rclone mount drive: ~/drive/"
Type=Application
Icon=/usr/share/icons/Papirus/24x24/apps/google-drive.svg
Terminal=false
```
