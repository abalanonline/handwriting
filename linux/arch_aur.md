### script for installing arch linux aur packages

aur.sh
```bash
set -x
echo Installing AUR $1
git clone https://aur.archlinux.org/$1.git
cd $1
makepkg
sudo pacman -U $1-*.pkg.tar.zst
cd ..
echo Installation complete, clearing
read -p "rm -rf $1/"
rm -rf $1/
```
