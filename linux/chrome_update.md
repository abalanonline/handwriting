### disable chrome update notifications

https://stackoverflow.com/questions/27962454/disable-chrome-is-out-of-date-notification

~/.local/share/applications/google-chrome.desktop
```
[Desktop Entry]
Version=1.0
Name=Google Chrome
GenericName=Web Browser
Comment=Access the Internet
Exec=/usr/bin/google-chrome-stable --simulate-outdated-no-au='Tue, 31 Dec 2099 23:59:59 GMT' %U
StartupNotify=true
Terminal=false
Icon=google-chrome
Type=Application
Categories=Network;WebBrowser;
MimeType=application/pdf;application/rdf+xml;application/rss+xml;application/xhtml+xml;application/xhtml_xml;application/xml;image/gif;image/jpeg;image/png;image/webp;text/html;text/xml;x-scheme-handler/http;x-scheme-handler/https;
Actions=new-window;new-private-window;

[Desktop Action new-window]
Name=New Window
Exec=/usr/bin/google-chrome-stable --simulate-outdated-no-au='Tue, 31 Dec 2099 23:59:59 GMT'

[Desktop Action new-private-window]
Name=New Incognito Window
Exec=/usr/bin/google-chrome-stable --simulate-outdated-no-au='Tue, 31 Dec 2099 23:59:59 GMT' --incognito
```
