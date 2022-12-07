### backup

```bash
mount /dev/sda1 /mnt
dd if=/dev/zero of=/mnt/0 bs=1M status=progress
rm /mnt/0
umount /mnt
dd if=/dev/sda1 bs=1M status=progress | gzip --fast > sda1.img.gz
```

### restore

```bash
gunzip -c sda1.img.gz | dd of=/dev/sda1 bs=1M status=progress
```
