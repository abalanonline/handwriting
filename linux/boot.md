### grub b/w theme

/etc/grub.d/06_theme
```bash
echo "set menu_color_normal=light-gray/black"
echo "set menu_color_highlight=black/light-gray"
```

```bash
sudo chmod a+x /etc/grub.d/06_theme
```

### plymouth splash screen

```bash
sudo vi /etc/default/grub
GRUB_CMDLINE_LINUX_DEFAULT="quiet splash"
sudo update-grub

sudo plymouth-set-default-theme -R gnome
```
