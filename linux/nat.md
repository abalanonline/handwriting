### nat 192.168.10.0/24 -> 192.168.0.0/24

```bash
sudo apt install isc-dhcp-server
sudo systemctl restart isc-dhcp-server.service
sudo vi /etc/sysctl.conf #net.ipv4.ip_forward=1
echo 1 > /proc/sys/net/ipv4/ip_forward
sudo iptables -t nat -A POSTROUTING -s 192.168.10.0/24 -j MASQUERADE
sudo iptables -t nat -L
```

/etc/dhcp/dhcpd.conf
```
subnet 192.168.10.0 netmask 255.255.255.0 {
  range 192.168.10.2 192.168.10.254;
  option domain-name-servers 192.168.0.1;
  option routers 192.168.10.1;
}
```
