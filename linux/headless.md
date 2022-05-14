### headless no password sudo, text runlevel

```bash
vi /etc/sudoers
user ALL=(ALL:ALL) NOPASSWD: ALL
:wq!

sudo systemctl set-default multi-user.target
```
