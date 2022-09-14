### headless no password sudo, text runlevel

```bash
vi /etc/sudoers
user ALL=(ALL:ALL) NOPASSWD: ALL
:wq!

sudo systemctl set-default multi-user.target
```

### reveal host names in ~/.ssh/known_hosts

```bash
sudo vi /etc/ssh/ssh_config
```
comment HashKnownHosts

### search bash history list with up/down

~/.inputrc
```bash
"\e[A":history-search-backward
"\e[B":history-search-forward
```
