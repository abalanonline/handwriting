### backup

```bash
mount /dev/sda1 /mnt
rm /mnt/hiberfil.sys
rm /mnt/pagefile.sys
rm /mnt/swapfile.sys
dd if=/dev/zero of=/mnt/0 bs=1M status=progress
rm /mnt/0
umount /mnt
dd if=/dev/sda1 bs=1M status=progress | gzip --fast > sda1.img.gz
```

### restore

```bash
gunzip -c sda1.img.gz | dd of=/dev/sda1 bs=1M status=progress
```

### backup with resize

```bash
ntfsresize -m /dev/sda1
Minsize (in MB): 18424
```

Warning: decimal megabytes

Warning: will fail on estimated size, add 100MB


```bash
ntfsresize -s 18524M /dev/sda1
ntfsfix -d /dev/sda1
dd if=/dev/sda1 bs=1MB count=18524 status=progress | gzip --fast > sda1.img.gz
```

### restore with resize

```bash
dd if=/dev/zero of=/dev/sda1 bs=1M status=progress
gunzip -c sda1.img.gz | dd of=/dev/sda1 bs=1M status=progress
ntfsresize /dev/sda1
ntfsfix -d /dev/sda1
```
