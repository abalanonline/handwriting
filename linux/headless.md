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

### git status in bash prompt

~/.bashrc
```bash
GIT_PS1_SHOWDIRTYSTATE="true"
GIT_PS1_SHOWSTASHSTATE="true"
GIT_PS1_SHOWUNTRACKEDFILES="true"
GIT_PS1_SHOWUPSTREAM="auto"
GIT_PS1_SHOWCOLORHINTS="true"
PS1='\[\e[0;32m\]$(__git_ps1 " (%s)")\[\e[0m\]'
```

### set the time

```bash
sudo ntpdate time.nrc.ca;sudo hwclock --systohc
```
