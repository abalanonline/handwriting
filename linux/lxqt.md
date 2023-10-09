### lxqt speaker icon fix

```bash
pacman -S papirus-icon-theme obsidian-icon-theme
cd /usr/share/icons/
cp Obsidian/status/22/audio-volume-muted.png oxygen/base/22x22/status/audio-volume-muted.png
cp Obsidian/status/22/audio-volume-low.png oxygen/base/22x22/status/audio-volume-low.png
cp Obsidian/status/22/audio-volume-medium.png oxygen/base/22x22/status/audio-volume-medium.png
cp Obsidian/status/22/audio-volume-high.png oxygen/base/22x22/status/audio-volume-high.png
cp Obsidian/status/96/audio-volume-muted.svg Papirus/24x24/panel/audio-volume-muted.svg
cp Obsidian/status/96/audio-volume-low.svg Papirus/24x24/panel/audio-volume-low.svg
cp Obsidian/status/96/audio-volume-medium.svg Papirus/24x24/panel/audio-volume-medium.svg
cp Obsidian/status/96/audio-volume-high.svg Papirus/24x24/panel/audio-volume-high.svg
```
