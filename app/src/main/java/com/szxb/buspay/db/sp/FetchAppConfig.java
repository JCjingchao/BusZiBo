package com.szxb.buspay.db.sp;


import android.os.Environment;

/**
 * 作者: Tangren on 2017/7/12
 * 包名：com.szxb.onlinbus.util
 * 邮箱：996489865@qq.com
 * TODO:获取全局的SP数据
 */

public class FetchAppConfig {

    //获取posId
    public static String posId() {
        return (String) CommonSharedPreferences.get("posId", "10001");
    }


    //获取appId
    public static String appId() {
        return (String) CommonSharedPreferences.get("appId", "20000007");
    }

    //车牌号
    public static String busNo() {
        return (String) CommonSharedPreferences.get("busNo", "000000");
    }

    //获取起始站
    public static String startStationName() {
        return (String) CommonSharedPreferences.get("startStationName", "未设置");
    }

    //获取终点站
    public static String endStationName() {
        return (String) CommonSharedPreferences.get("endStationName", "未设置");
    }

    //线路号
    public static String lineName() {
        return (String) CommonSharedPreferences.get("lineName", "未设置");
    }

    //备注
    public static String orderDesc() {
        return (String) CommonSharedPreferences.get("orderDesc", "淄博公交7");
    }

    //获取状态
    public static boolean saveState() {
        return (boolean) CommonSharedPreferences.get("save_state", true);
    }

    //获取票价
    public static int ticketPrice() {
        return (Integer) CommonSharedPreferences.get("ticketPrice", 0);
    }

    //实际扣款
    public static int payTicketPrice() {
        return (Integer) CommonSharedPreferences.get("payticketPrice", 0);
    }

    //获取城市编码
    public static String cityCode() {
        return (String) CommonSharedPreferences.get("cityCode", "440300");
    }


    //司机卡号
    public static String saveDriver() {
        return (String) CommonSharedPreferences.get("DriverCard", "00000000");
    }

    //连接蓝牙设备号
    public static String BluetoothDevice() {
        return (String) CommonSharedPreferences.get("BluetoothDevice", "Xperia Z5 Compact");
    }

    //线路号
    public static String LineName() {
        return (String) CommonSharedPreferences.get("LineName", "0302");
    }

    //上次提交文件到服务器的时间，格式：yyyy-MM-dd HH:mm:ss
    public static String lastTimePushFile() {
        return (String) CommonSharedPreferences.get("lastTimePushFile", "");
    }

    //参数版本号
    public static String ParameterVersion(){
        return (String)CommonSharedPreferences.get("ParameterVersion","");
    }

    //线路名称说明
    public static String chinese_name(){
        return (String)CommonSharedPreferences.get("chinese_name","");
    }

    //是否为固定票价

    public static String is_fixed_price(){
        return (String)CommonSharedPreferences.get("is_fixed_price","");
    }


    //票价
    public static String fixed_price(){
        return (String)CommonSharedPreferences.get("fixed_price","1");
    }

    //折扣
    public static String coefficient(){
        return (String)CommonSharedPreferences.get("coefficient","");
    }

    //快速票价
    public static String shortcut_price(){
        return (String)CommonSharedPreferences.get("shortcut_price","");
    }


    //FTP参数路径
    public static String FTPParameter(){
        return (String)CommonSharedPreferences.get("FTPParameter","pram/dlydt.json");
    }

    //FTP黑名单路径
    public static String FTPBlackList(){
       return (String)CommonSharedPreferences.get("FTPBlcakList","black/blacklist.json");
    }
    //FTP保存路径
    public static String FTPLocalPath(){
        return (String)CommonSharedPreferences.get("FTPLocalPath", Environment.getExternalStorageDirectory()+"/");
    }

    //保存参数文件名
    public static String ParameterName(){
        return (String)CommonSharedPreferences.get("ParameterName","dlydt.json");
    }

    //保存黑名单文件名
    public static String BlackListName(){
        return (String)CommonSharedPreferences.get("BlackListName","blacklist.json");
    }

//    //保存记录文件路径
//    public static String FileRecordName(){
//        return (String)CommonSharedPreferences.get("FileRecordName",Environment.getExternalStorageDirectory()+"/"+"record.json");
//    }

    //是否选择过线路名
    public static String FristNo(){
        return (String)CommonSharedPreferences.get("FristNo","");
    }


    //参数标志位，(0:扣款，1:不扣款)最右1为员工卡，右2为免费卡，右3为老年卡是否扣款标志
    public static String Param_Flag(){
        return (String)CommonSharedPreferences.get("Param_Flag","00000000");

    }


    public static String init(){
        return (String)CommonSharedPreferences.get("init","0");
    }
    public static String blackVersion(){
        return (String)CommonSharedPreferences.get("blackversion","0");
    }
}