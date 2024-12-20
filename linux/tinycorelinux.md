### Tiny Core Linux

### apps
```
libXtst.tcz
alsa.tcz
alsa-config.tcz
fontconfig.tcz
```

### autorun
~/.X.d/autorun
```bash
sh /mnt/sda1/autorun.sh &
```

### disable screen saver / power saver
```bash
xset -dpms
xset s off
```

### mount disk
```bash
mount /dev/sda1
```

### sreen resolution
~/.xsession

### alsa settings
```bash
alsactl --file /mnt/sda1/asound.state store
alsactl --file /mnt/sda1/asound.state restore
amixer set Capture cap
amixer set Digital 100
```

### java boot script
```bash
mount /dev/mmcblk0p1
cd /mnt/mmcblk0p1/
for file in *.jar; do
if [ -e "$file" ]; then
java -jar $file &
break
fi
done
```
