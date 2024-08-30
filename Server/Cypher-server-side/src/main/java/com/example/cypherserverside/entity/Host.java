package com.example.cypherserverside.entity;

import lombok.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Host {
    private Integer hostId;
    private String hostName;
    private String hostIP;
    private List<VirtualHost> vHosts = new LinkedList<>();
    private Boolean hostStatus = Boolean.FALSE;                 //表示是否能被监控

    public void setHostIP() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            hostIP = ip.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHostName() {
        try {
            InetAddress name = InetAddress.getLocalHost();
            hostName = name.getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void addVirtualHost(String hostName, String ip) {
        VirtualHost virtualHost = new VirtualHost();
        virtualHost.setHostName(hostName);
        virtualHost.setHostIP(ip);
        virtualHost.setByTheHostIp(hostIP);
        vHosts.add(virtualHost);
    }

    public boolean removeVirtualHost(String ip) {
        VirtualHost vHost = vHosts.stream().filter(v->v.getHostIP().equals(ip))
                .findFirst().orElse(null);
        if (vHost == null) {
            return false;
        }
        vHosts.remove(vHost);
        return true;
    }

}
