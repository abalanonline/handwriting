### bashrc with git status

```
#
# ~/.bashrc
#

# If not running interactively, don't do anything
[[ $- != *i* ]] && return

HISTSIZE=100000
HISTFILESIZE=100000

alias ls='ls --color=auto'
alias ll='ls -la --color=auto --time-style=long-iso'
alias grep='grep --color=auto'

source /usr/share/git/completion/git-prompt.sh
GIT_PS1_SHOWDIRTYSTATE="true"
GIT_PS1_SHOWSTASHSTATE="true"
GIT_PS1_SHOWUNTRACKEDFILES="true"
GIT_PS1_SHOWUPSTREAM="auto"
GIT_PS1_SHOWCOLORHINTS="true"
unset GIT_PS1_SHOWCOLORHINTS
PS1='[\u@\h \W$(__git_ps1 " \[\e[0;32m\](%s)\[\e[0m\]")]\$ '
#PS1='[\u@\h \W$(__git_ps1 " (%s)")]\$ '
```
