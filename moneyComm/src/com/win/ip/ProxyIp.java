package com.win.ip;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ice.jni.registry.Registry;

/**
 * ProxyIp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "proxy_ip", catalog = "money")
public class ProxyIp implements java.io.Serializable {
    // Fields    

    private Long id;

    private String ip;

    private String ipCountry;

    private String ipArea;

    private Integer port;

    private Integer speed;

    private Integer proxyLevel;

    private String status;

    private Timestamp createTime;

    // Constructors

    /** 取得目前IE的代理IP*/
    public static ProxyIp getIEProxy() {
        try {
            String proxy = Registry.getProxy();
            if (proxy != null && proxy.contains(":")) {
                ProxyIp proxyIp = new ProxyIp(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1]), null, null);
                return proxyIp;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProxyIp() {
    }

    /** full constructor */
    public ProxyIp(String ip, Integer port, String status, Timestamp createTime) {
        this.ip = ip;
        this.port = port;
        this.status = status;
        this.createTime = createTime;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ip")
    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "port")
    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Column(name = "status", length = 45)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "create_time", length = 19)
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "ip_area")
    public String getIpArea() {
        return ipArea;
    }

    public void setIpArea(String ipArea) {
        this.ipArea = ipArea;
    }

    @Column(name = "ip_country")
    public String getIpCountry() {
        return ipCountry;
    }

    public void setIpCountry(String ipCountry) {
        this.ipCountry = ipCountry;
    }

    @Column(name = "speed")
    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Column(name = "proxy_level")
    public Integer getProxyLevel() {
        return proxyLevel;
    }

    public void setProxyLevel(Integer proxyLevel) {
        this.proxyLevel = proxyLevel;
    }

    public String toString() {
        return this.ip + ":" + port + "#" + ipArea;
    }

}