### arch linux install with encryption

Example how to install with no encryption on /dev/sda2 and then copy to encrypted volume /dev/sdb3.

```bash
ls -l /dev/disk/by-uuid
lrwxrwxrwx 1 root root 10 Oct  9 00:00 1fc39c72-8598-ecee-5b41-48d745e8b8ce -> ../../sdb2
lrwxrwxrwx 1 root root 10 Oct  9 00:00 24db7c0a-d580-4359-a877-7a54f3ab37fc -> ../../sdb3

dd if=/dev/zero of=/dev/sdb3 bs=1M

cryptsetup luksFormat /dev/sdb3
```

archinstall

chroot? y

```bash
vi /etc/fstab
UUID=24db7c0a-d580-4359-a877-7a54f3ab37fc / ext4
UUID=1fc39c72-8598-ecee-5b41-48d745e8b8ce /boot ext2 defaults

vi /etc/mkinitcpio.conf
"block encrypt filesystems"

mkinitcpio -p linux
```

copy vmlinuz initramfs image files, edit grub

```bash
search --no-floppy --fs-uuid --set=root 1fc39c72-8598-ecee-5b41-48d745e8b8ce
linux /vmlinuz-linux cryptdevice=UUID=24db7c0a-d580-4359-a877-7a54f3ab37fc:root root=/dev/mapper/root rw
```

```bash
umount /dev/sda1 /dev/sda2
tune2fs -O "^orphan_file" /dev/sda2
e2fsck -f /dev/sda2
resize2fs /dev/sda2 4G
cryptsetup open /dev/sdb3 root
dd if=/dev/sda2 of=/dev/mapper/root bs=1M count=4096
resize2fs /dev/mapper/root
e2fsck -f /dev/mapper/root
cryptsetup close root
```
