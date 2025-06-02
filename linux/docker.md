### sudoless docker

```bash
sudo apt install docker.io
sudo usermod -aG docker $USER
sudo reboot
docker ps
```

### arch

```bash
sudo pacman -S docker
sudo systemctl start docker.socket
sudo usermod -aG docker $USER
docker ps
```
