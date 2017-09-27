package com.szxb.buspay.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者: Tangren on 2017/7/19
 * 包名：com.szxb.buspay.entity
 * 邮箱：996489865@qq.com
 * TODO:扫码记录表
 * status:支付状态，默认false
 * biz_data_single:单个扫码记录的jsonObject
 */
@Entity
public class ScanInfoEntity {
    @Id(autoincrement = true)
    private Long id;
    //支付状态1:新订单，未支付，0:准实时扣款，2：批处理扣款成功 3：扣款失败后台处理
    private int status = 1;
    //一笔订单
    private String biz_data_single;
    //订单号
    private String mch_trx_id;
    //返回码
    private String result;
    //交易状态码
    private String tr_status;
    //保存的时间yyyy-MM-dd HH:mm:ss
    private String time;
    private String remark_1;
    private String remark_2;
    @Generated(hash = 602196483)
    public ScanInfoEntity(Long id, int status, String biz_data_single,
            String mch_trx_id, String result, String tr_status, String time,
            String remark_1, String remark_2) {
        this.id = id;
        this.status = status;
        this.biz_data_single = biz_data_single;
        this.mch_trx_id = mch_trx_id;
        this.result = result;
        this.tr_status = tr_status;
        this.time = time;
        this.remark_1 = remark_1;
        this.remark_2 = remark_2;
    }
    @Generated(hash = 1829284389)
    public ScanInfoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getBiz_data_single() {
        return this.biz_data_single;
    }
    public void setBiz_data_single(String biz_data_single) {
        this.biz_data_single = biz_data_single;
    }
    public String getMch_trx_id() {
        return this.mch_trx_id;
    }
    public void setMch_trx_id(String mch_trx_id) {
        this.mch_trx_id = mch_trx_id;
    }
    public String getResult() {
        return this.result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getTr_status() {
        return this.tr_status;
    }
    public void setTr_status(String tr_status) {
        this.tr_status = tr_status;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getRemark_1() {
        return this.remark_1;
    }
    public void setRemark_1(String remark_1) {
        this.remark_1 = remark_1;
    }
    public String getRemark_2() {
        return this.remark_2;
    }
    public void setRemark_2(String remark_2) {
        this.remark_2 = remark_2;
    }

    @Override
    public String toString() {
        return "ScanInfoEntity{" +
                "id=" + id +
                ", status=" + status +
                ", biz_data_single='" + biz_data_single + '\'' +
                ", mch_trx_id='" + mch_trx_id + '\'' +
                ", result='" + result + '\'' +
                ", tr_status='" + tr_status + '\'' +
                ", time='" + time + '\'' +
                ", remark_1='" + remark_1 + '\'' +
                ", remark_2='" + remark_2 + '\'' +
                '}';
    }
}
