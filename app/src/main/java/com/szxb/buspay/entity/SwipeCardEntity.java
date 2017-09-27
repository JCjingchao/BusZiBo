package com.szxb.buspay.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者: Tangren on 2017/7/17
 * 包名：com.szxb.buspay.entity
 * 邮箱：996489865@qq.com
 * TODO:扫码交易表
 */
@Entity
public class SwipeCardEntity implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private String open_id;
    private String mch_trx_id;
    private Long order_time;
    private int total_fee;
    private int pay_fee;
    private String city_code;
    private int in_station_id; //上车站台编号
    private String in_station_name; //站台名称
    private int paystatus; //支付状态
    private String transaction_id;//result=0时返回财付通单号
    private String status;
    private String status_desc;
    private String record;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getMch_trx_id() {
        return mch_trx_id;
    }

    public void setMch_trx_id(String mch_trx_id) {
        this.mch_trx_id = mch_trx_id;
    }

    public Long getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Long order_time) {
        this.order_time = order_time;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public int getPay_fee() {
        return pay_fee;
    }

    public void setPay_fee(int pay_fee) {
        this.pay_fee = pay_fee;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public int getIn_station_id() {
        return in_station_id;
    }

    public void setIn_station_id(int in_station_id) {
        this.in_station_id = in_station_id;
    }

    public String getIn_station_name() {
        return in_station_name;
    }

    public void setIn_station_name(String in_station_name) {
        this.in_station_name = in_station_name;
    }

    public int getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(int paystatus) {
        this.paystatus = paystatus;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "SwipeCardEntity{" +
                "id=" + id +
                ", open_id='" + open_id + '\'' +
                ", mch_trx_id='" + mch_trx_id + '\'' +
                ", order_time=" + order_time +
                ", total_fee=" + total_fee +
                ", pay_fee=" + pay_fee +
                ", city_code='" + city_code + '\'' +
                ", in_station_id=" + in_station_id +
                ", in_station_name='" + in_station_name + '\'' +
                ", paystatus=" + paystatus +
                ", transaction_id='" + transaction_id + '\'' +
                ", status='" + status + '\'' +
                ", status_desc='" + status_desc + '\'' +
                ", record='" + record + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.open_id);
        dest.writeString(this.mch_trx_id);
        dest.writeValue(this.order_time);
        dest.writeInt(this.total_fee);
        dest.writeInt(this.pay_fee);
        dest.writeString(this.city_code);
        dest.writeInt(this.in_station_id);
        dest.writeString(this.in_station_name);
        dest.writeInt(this.paystatus);
        dest.writeString(this.transaction_id);
        dest.writeString(this.status);
        dest.writeString(this.status_desc);
        dest.writeString(this.record);
    }

    public SwipeCardEntity() {
    }

    protected SwipeCardEntity(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.open_id = in.readString();
        this.mch_trx_id = in.readString();
        this.order_time = (Long) in.readValue(Long.class.getClassLoader());
        this.total_fee = in.readInt();
        this.pay_fee = in.readInt();
        this.city_code = in.readString();
        this.in_station_id = in.readInt();
        this.in_station_name = in.readString();
        this.paystatus = in.readInt();
        this.transaction_id = in.readString();
        this.status = in.readString();
        this.status_desc = in.readString();
        this.record = in.readString();
    }

    @Generated(hash = 135490196)
    public SwipeCardEntity(Long id, String open_id, String mch_trx_id, Long order_time, int total_fee,
            int pay_fee, String city_code, int in_station_id, String in_station_name, int paystatus,
            String transaction_id, String status, String status_desc, String record) {
        this.id = id;
        this.open_id = open_id;
        this.mch_trx_id = mch_trx_id;
        this.order_time = order_time;
        this.total_fee = total_fee;
        this.pay_fee = pay_fee;
        this.city_code = city_code;
        this.in_station_id = in_station_id;
        this.in_station_name = in_station_name;
        this.paystatus = paystatus;
        this.transaction_id = transaction_id;
        this.status = status;
        this.status_desc = status_desc;
        this.record = record;
    }

    public static final Parcelable.Creator<SwipeCardEntity> CREATOR = new Parcelable.Creator<SwipeCardEntity>() {
        @Override
        public SwipeCardEntity createFromParcel(Parcel source) {
            return new SwipeCardEntity(source);
        }

        @Override
        public SwipeCardEntity[] newArray(int size) {
            return new SwipeCardEntity[size];
        }
    };
}
