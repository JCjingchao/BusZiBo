package com.szxb.buspay.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Evergarden on 2017/9/21.
 */

@Entity
public class LineEntity {
    //"acnt":"3","routeno":"1","routename":"招远快线","routeversion":"20170921120800"
    @Id(autoincrement = true)
    private Long Id;
    private String acnt;//公司号
    private String routeno;//线路号
    private String routename;//线路名
    private String routeversion;//线路版本名
    @Generated(hash = 257276776)
    public LineEntity(Long Id, String acnt, String routeno, String routename,
            String routeversion) {
        this.Id = Id;
        this.acnt = acnt;
        this.routeno = routeno;
        this.routename = routename;
        this.routeversion = routeversion;
    }
    @Generated(hash = 1586593410)
    public LineEntity() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getAcnt() {
        return this.acnt;
    }
    public void setAcnt(String acnt) {
        this.acnt = acnt;
    }
    public String getRouteno() {
        return this.routeno;
    }
    public void setRouteno(String routeno) {
        this.routeno = routeno;
    }
    public String getRoutename() {
        return this.routename;
    }
    public void setRoutename(String routename) {
        this.routename = routename;
    }
    public String getRouteversion() {
        return this.routeversion;
    }
    public void setRouteversion(String routeversion) {
        this.routeversion = routeversion;
    }


}
