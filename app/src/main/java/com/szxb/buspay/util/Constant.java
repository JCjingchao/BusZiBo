package com.szxb.buspay.util;

/**
 * 作者：Evergarden on 2017/7/20 10:37
 * QQ：1941042402
 */

public class  Constant {

    public static final  int KeyMessage=0x11;//按键
    public static final  int FindCardMessage=0x12;//寻卡
    public static final  int CardPayMessage=0x13;//消费
     public static final  int CardPayMessageErro=0xF3;//消费

    public static final  int ALLMessage=0x99;
    public static final int DriverSgin=0x01;//司机签到
    public static final int SkipPar=0x02;//消费跳转线路选择
    public static final int SettingSuccess=0x03;//设置成功
    public static final int SettingFail=0x04;//设置失败
    public static final int RecordBack=0x09;//查询记录返回
    public static final int BlackList=0x15;//黑名单下载
    public static final int Parameter=0x016;//参数下载
    public static final int RecordGo=0x10;//进入记录
    public static final int MenuG0=0x14;//进入菜单
    public static final int Sgin=0x0A;//进入菜单
    public static final int SetBus=0xA1;//进入设置车号

    public static final int ParamVersionSuccess=0x51;//参数版本
    public static final int ParamVersionFail=0x52;//参数版本
    public static final int ParamVersion=0x53;
    public static final int ParamLoadVersionSuccess=0x54;
    public static final int ParamLoadVersionFail=0x55;
    public static final int ParamLoadVersionMust=0x56;
    public static final int LoadParamVersion=0x57;
    public static final int LoadParamVersionSuccess=0x58;
    public static final int LoadParamVersionFail=0x59;

    public static final int BlackVersionSuccess=0x61;//黑名单版本
    public static final int BlackVersionFail=0x62;//参数版本
    public static final int BlackVersion=0x63;
    public static final int BlackLoadVersionSuccess=0x64;
    public static final int BlackLoadVersionFail=0x65;
    public static final int BlackLoadVersionMust=0x66;

    public static final int ParameterdownSuccess=0x05;//参数下载成功
    public static final int ParameterdownFail=0x07;//参数下载下载失败

    public static final int blacklistdownSuccess=0x06;//黑名单下载成功
    public static final int blacklistdownFail=0x08;//黑名单下载失败

    public static final int LoadParameterdownSuccess=0x17;//参数加载成功
    public static final int LoadParameterdownFail=0x18;//参数下载加载失败

    public static final int LoadblacklistSuccess=0x19;//黑名单加载成功
    public static final int LoadblacklistFail=0x20;//黑名单加载失败


}
