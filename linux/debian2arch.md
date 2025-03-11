### Debian packages lacking in Arch

### seq24

```bash
wget http://archive.debian.org/debian/pool/main/s/seq24/seq24_0.9.3-2_amd64.deb
ar xv seq24_0.9.3-2_amd64.deb
tar xf data.tar.xz
sudo cp -r usr/ /
```

### ent

```bash
wget http://archive.debian.org/debian/pool/main/e/ent/ent_1.2debian-2_amd64.deb
ar xv ent_1.2debian-2_amd64.deb
tar xf data.tar.xz
cp usr/bin/ent ~
```
