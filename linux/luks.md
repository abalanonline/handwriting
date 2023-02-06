### LUKS/LVM PV resize

```bash
pvdisplay --units m
```

```
PV Name: /dev/mapper/sda2_crypt
PE Size: 4.00 MiB
Allocated PE: 92160
```

minSize = Allocated PE * PE Size + 1 MiB = 92160 * 4 + 1 = 368641

```bash
pvresize --setphysicalvolumesize 368641m /dev/mapper/sda2_crypt
```

Sanity check: Pretending size = minSize * 2048 = 754976768

```bash
fdisk /dev/sda
```

minSize += 16 MiB = 368657

Delete and create LUKS partition, size: +368657M
