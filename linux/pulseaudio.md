### PulseAudio sound server

### List sources, sinks, loopbacks

```bash
pactl list | grep --color=never "^Source #" -A 3
pactl list | grep --color=never "^Sink #" -A 3
pactl list | grep --color=never "Name: module-loopback$" -C 1
```

### Loopback module

```bash
pactl load-module module-loopback
pavucontrol-qt
pactl load-module module-loopback latency_msec=1 source=3 sink=0
pactl list | grep --color=never "Name: module-loopback$" -C 1
pactl unload-module module-loopback
```
