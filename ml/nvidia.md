### Nvidia

Popular hardware for gaming and machine learning.

### install

Secure Boot OFF in BIOS to use proprietary drivers.

```bash
apt install linux-headers-$(uname -r)
apt install nvidia-driver firmware-misc-nonfree
```

### graphics

No need to modify /etc/mod*, all the necessary kernel module configuration is done by updating glx to auto.

```bash
update-alternatives --config glx
```

For dual graphic card systems these 3 files are required:
* /etc/X11/xorg.conf
* /usr/share/gdm/greeter/autostart/optimus.desktop
* ~/.xsessionrc

They can be copied from this page https://wiki.debian.org/NVIDIA%20Optimus

This configuration will lock X11 to Nvidia card as intended.

PCI BusID
```bash
# lspci | grep -i vga
0000:00:02.0 VGA compatible controller: Intel
0000:01:00.0 VGA compatible controller: NVIDIA
```

/etc/X11/xorg.conf
```
Section "Device"
    Identifier "nvidia"
    BusID "PCI:1:0:0"
EndSection
Section "Device"
    Identifier "intel"
    BusID "PCI:0:2:0"
EndSection
```

### cuda

```bash
apt install nvidia-cuda-dev nvidia-cuda-toolkit
wget https://developer.download.nvidia.com/compute/redist/cudnn/v8.4.1/local_installers/11.6/cudnn-linux-x86_64-8.4.1.50_cuda11.6-archive.tar.xz
tar xf cudnn-linux-x86_64-8.4.1.50_cuda11.6-archive.tar.xz
mv cudnn-linux-x86_64-8.4.1.50_cuda11.6-archive/lib/libcudnn* /usr/lib/x86_64-linux-gnu/
modprobe nvidia-current-uvm

echo nvidia-current-uvm > /etc/modules-load.d/nvidia-current-uvm.conf
reboot
nvidia-smi
```

### embedded graphics

It is possible to switch between video cards with update-alternatives. To do so

Blacklist nvidia drm for mesa the same way as nouveau blacklisted for nvidia.

```bash
echo blacklist nvidia-current-drm > /etc/nvidia/nvidia-blacklists-drm.conf
editor /var/lib/dpkg/alternatives/glx
```

Put the line /etc/nvidia/nvidia-blacklists-drm.conf in this exact place in glx (empty lines are important)

/var/lib/dpkg/alternatives/glx
```bash
/usr/lib/mesa-diverted/x86_64-linux-gnu/libGLESv1_CM.so.1
/usr/lib/mesa-diverted/x86_64-linux-gnu/libGLESv2.so.2
/usr/lib/x86_64-linux-gnu/libGLX_mesa.so.0


/etc/nvidia/nvidia-blacklists-drm.conf





/usr/lib/nvidia
100
/usr/lib/mesa-diverted/x86_64-linux-gnu/libEGL.so.1
/usr/lib/mesa-diverted/x86_64-linux-gnu/libGL.so.1
/usr/lib/mesa-diverted/x86_64-linux-gnu/libGLESv1_CM.so.1
```

Wayland is auto disabling itself if nvidia driver is used.

/usr/lib/udev/rules.d/61-gdm.rules
```bash
DRIVER=="nvidia", RUN+="/usr/libexec/gdm-disable-wayland"
```

That make sense if nvidia driver is used for graphics but not if it's used for cuda computing.

This is how wayland rule can be fixed.

/usr/lib/udev/rules.d/61-gdm.rules
```bash
SUBSYSTEM=="drm", DRIVERS=="nvidia", RUN+="/usr/libexec/gdm-disable-wayland"
```

And there is no need to backup X11 configuration because modern Wayland will be used for Mesa alternative.

```bash
update-alternatives --config glx
```

### troubleshooting

enable secure boot

lsmod | grep nvidia

nvidia-smi

glxgears benchmark

```bash
sudo apt install mesa-utils
vblank_mode=0 glxgears -info
```

update-initramfs -u - idk if it work or not

some configuration insights can be found in this unstable package http://http.us.debian.org/debian/pool/non-free/n/nvidia-cudnn/nvidia-cudnn_8.4.1.50~cuda11.6_amd64.deb
