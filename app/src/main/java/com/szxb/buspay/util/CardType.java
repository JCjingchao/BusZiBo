package com.szxb.buspay.util;

/**
 * Created by Administrator on 2017/8/20.
 */




/*#define	NO_ERROR					0x00	//成功
        #define	CARD_DISABLE_FLAG			0xF1	//卡片未启用
        #define	CARD_TIME_OVER				0xF2	//卡片过期
        #define	CARD_NO_MONEY				0xF3	//卡内余额不足
        #define	CARD_ERROR					0xF4	//备用
        #define	CONSUME_ERROR				0xFF	//扣费异常，这个我们做过了，*/

/*
#define CARDT_NORMAL		0x01	//普通卡和CPU福礼卡
        #define CARDT_STUDENT_A     0x02    //A学生卡
        #define CARDT_OLDMAN        0x03    //老年卡
        #define CARDT_FREE		    0x04    //免费卡
        #define CARDT_MEMORY		0x05	//纪念卡
        #define CARDT_DRIVER		0x06	//员工卡
        #define CARDT_FAVOR1	    0x05    //优惠卡1
        #define CARDT_FAVOR2		0x07	//优惠卡2
        #define CARDT_FAVOR3		0x08	//优惠卡3

        #define CARDT_FAVOR			0x07	//优惠卡
        #define CARDT_STUDENT_B		0x08	//B学生卡

        #define CARDT_SET			0x10	//线路票价车号设置卡
        #define CARDT_GATHER        0x11    //数据采集卡
        #define CARDT_SIGNED        0x12	//签点卡
        #define CARDT_CHECKED		0x13	//检测卡
        #define CARDT_CHECK		    0x18	//稽查卡*/
public class CardType {

    public static String Driver="06";
    public static final String NO_ERROR="0";
    public static final String CARD_DISABLE_FLAG="f1";
    public static final String CARD_TIME_OVER="f2";
    public static final String CARD_NO_MONEY="f3";
    public static final String CARD_ERROR="f4";
    public static final String CONSUME_ERROR="ff";



    public static final String CARDT_NORMAL="01";
    public static final String CARDT_STUDENT_A="02";
    public static final String CARDT_OLDMAN="03";
    public static final String CARDT_FREE="04";
    public static final String CARDT_MEMORY="05";
    public static final String CARDT_DRIVER="06";
    public static final String CARDT_FAVOR1="05";
    public static final String CARDT_FAVOR2="07";
    public static final String CARDT_FAVOR3="08";
    public static final String CARDT_FAVOR="07";
    public static final String CARDT_STUDENT_B="08";
    public static final String CARDT_SET="10";
    public static final String CARDT_GATHER="11";
    public static final String CARDT_SIGNED="12";
    public static final String CARDT_CHECKED="13";
    public static final String CARDT_CHECK="18";
}
