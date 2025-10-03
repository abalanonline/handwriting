### .zshrc with git status

~/.zshrc
```
HISTFILE=~/.histfile
HISTSIZE=100000
SAVEHIST=100000
bindkey -e

# disable Ctrl+s
stty -ixon

bindkey "^[[A" history-beginning-search-backward
bindkey "^[[B" history-beginning-search-forward

alias ls='ls --color=auto'
alias ll='ls -la --color=auto --time-style=long-iso'
alias grep='grep --color=auto'

setopt PROMPT_SUBST
source /usr/share/git/completion/git-prompt.sh
GIT_PS1_SHOWDIRTYSTATE="true"
GIT_PS1_SHOWSTASHSTATE="true"
GIT_PS1_SHOWUNTRACKEDFILES="true"
GIT_PS1_SHOWUPSTREAM="auto"
GIT_PS1_SHOWCOLORHINTS="true"
unset GIT_PS1_SHOWCOLORHINTS
#PS1='[%n@%m %1~]$ '
PS1='[%n@%m %1~%F{green}$(__git_ps1 " (%s)")%f]$ '

precmd() { printf "\033]0;%s@%s:%s\007" "${USER}" "${HOST%%.*}" "${PWD/#$HOME/~}" }
```
