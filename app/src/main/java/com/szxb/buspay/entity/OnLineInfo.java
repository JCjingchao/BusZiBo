package com.szxb.buspay.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Evergarden on 2017/8/21.
 */


//          "acnt":"03",
//         "line":"0001",
//         "version": "20170921120800",
//         "up_station": "17",
//         "down_station": "17",
//         "chinese_name": "招远快线",
//         "is_fixed_price": "0",
//         "is_keyboard":"1",
//         "fixed_price": "200",
//         "coefficient": "100100100100100100100100",
//         "shortcut_price": "010002000300040005000600070008000900"
@Entity
public class OnLineInfo {
    @Id(autoincrement = true)
    private Long Id;

    private String line;   //线路号
    private String version;  //版本号
    private String up_station;//线路上行站点数量
    private String dwon_station;//线路下行站点数量
    private String chinese_name;//线路名称
    private String is_fixed_price;//是否为固定票价线路,1=是, 其他=不是
    private String is_keyboard;//是否支持键盘，0为不支持，1为支持，当线路为非单一票价并且不支持键盘时，下面的站点经纬度和票价表生效，当线路为非单一票价并且支持键盘时，下面键盘快捷价格生效
    private String fixed_price;//如果是固定票价线路,此项才有意义,是票价,单位分
    private String coefficient;//8种卡折扣率，依次为普通卡、学生卡、老年卡、免费卡、优惠卡1、员工卡、优惠卡2、优惠卡3、微信支付
    //private String param_flag;//参数标志位，(0:扣款，1:不扣款)最右1为员工卡，右2为免费卡，右3为老年卡是否扣款标志。
    private String shortcut_price;//快捷票价,通过小键盘按键输入，数字1-9，直接代表金额，以分为单位。
    @Generated(hash = 610345756)
    public OnLineInfo(Long Id, String line, String version, String up_station, String dwon_station,
            String chinese_name, String is_fixed_price, String is_keyboard, String fixed_price,
            String coefficient, String shortcut_price) {
        this.Id = Id;
        this.line = line;
        this.version = version;
        this.up_station = up_station;
        this.dwon_station = dwon_station;
        this.chinese_name = chinese_name;
        this.is_fixed_price = is_fixed_price;
        this.is_keyboard = is_keyboard;
        this.fixed_price = fixed_price;
        this.coefficient = coefficient;
        this.shortcut_price = shortcut_price;
    }
    @Generated(hash = 1296336398)
    public OnLineInfo() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getLine() {
        return this.line;
    }
    public void setLine(String line) {
        this.line = line;
    }
    public String getVersion() {
        return this.version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getUp_station() {
        return this.up_station;
    }
    public void setUp_station(String up_station) {
        this.up_station = up_station;
    }
    public String getDwon_station() {
        return this.dwon_station;
    }
    public void setDwon_station(String dwon_station) {
        this.dwon_station = dwon_station;
    }
    public String getChinese_name() {
        return this.chinese_name;
    }
    public void setChinese_name(String chinese_name) {
        this.chinese_name = chinese_name;
    }
    public String getIs_fixed_price() {
        return this.is_fixed_price;
    }
    public void setIs_fixed_price(String is_fixed_price) {
        this.is_fixed_price = is_fixed_price;
    }
    public String getIs_keyboard() {
        return this.is_keyboard;
    }
    public void setIs_keyboard(String is_keyboard) {
        this.is_keyboard = is_keyboard;
    }
    public String getFixed_price() {
        return this.fixed_price;
    }
    public void setFixed_price(String fixed_price) {
        this.fixed_price = fixed_price;
    }
    public String getCoefficient() {
        return this.coefficient;
    }
    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }
    public String getShortcut_price() {
        return this.shortcut_price;
    }
    public void setShortcut_price(String shortcut_price) {
        this.shortcut_price = shortcut_price;
    }

    @Override
    public String toString() {
        return "OnLineInfo{" +
                "Id=" + Id +
                ", line='" + line + '\'' +
                ", version='" + version + '\'' +
                ", up_station='" + up_station + '\'' +
                ", dwon_station='" + dwon_station + '\'' +
                ", chinese_name='" + chinese_name + '\'' +
                ", is_fixed_price='" + is_fixed_price + '\'' +
                ", is_keyboard='" + is_keyboard + '\'' +
                ", fixed_price='" + fixed_price + '\'' +
                ", coefficient='" + coefficient + '\'' +
                ", shortcut_price='" + shortcut_price + '\'' +
                '}';
    }
}
