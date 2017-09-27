package com.szxb.buspay.module.QueryRecord;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szxb.buspay.R;
import com.szxb.buspay.entity.CardRecord;
import com.szxb.buspay.entity.OnLineInfo;
import com.szxb.xblog.XBLog;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class RecordAdapter extends BaseAdapter {
    private List<CardRecord> mList;//数据源
    private LayoutInflater mInflater;//布局装载器对象
    private int position=0;
    private ViewHolder viewHolder;

    private int logpost=0;
    public RecordAdapter(Context context , List<CardRecord> cardRecords) {
        mList=cardRecords;
        mInflater=LayoutInflater.from(context);
        Log.d("Record",cardRecords.size()+"");

    }



    @Override
    public int getCount() {
        return mList.size();

    }

   public void NotifyData(){
       notifyDataSetInvalidated();
   }



    public void DownKey(){
        if (position==(getCount()-1)){
            position=0;
        }else {
            position++;

        } XBLog.d(position);
         notifyDataSetChanged();
    }

    public  void UpKey(){

        if (position==0){
            position=getCount()-1;
        }else {
            position--;

        }XBLog.d(position);
        notifyDataSetChanged();

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("Record", i + "");
        try {
            if (view == null) {
                viewHolder = new ViewHolder();
                view = mInflater.inflate(R.layout.record_item, null);
                viewHolder.Card_id = (TextView) view.findViewById(R.id.card_id);
                viewHolder.Card_Money = (TextView) view.findViewById(R.id.card_money);
                viewHolder.Card_Pay = (TextView) view.findViewById(R.id.pay_money);
                viewHolder.Time = (TextView) view.findViewById(R.id.time);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            CardRecord cardRecord = mList.get(i);
            if (i == logpost && position != logpost) {
                view.setBackgroundResource(R.drawable.list_view);
                logpost = position;
            }

            Log.d("Record", cardRecord.getCardNumber());

            viewHolder.Card_id.setText(cardRecord.getCardNumber());

            DecimalFormat    df   = new DecimalFormat("######0.00");
            viewHolder.Card_Pay.setText(df.format(Double.parseDouble(Integer.parseInt(cardRecord.getPayMoney(), 16)/100.00+""))+"元");
            viewHolder.Card_Money.setText(df.format(Double.parseDouble(Integer.parseInt(cardRecord.getCardMoney(), 16)/100.00+""))+"元");
            viewHolder.Time.setText(getSfTime(cardRecord.getDateTime()));
            return view;
        }catch (Exception e){
                 Log.d("Record223",e.getMessage());
        }
        return null;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    class ViewHolder{
        private TextView Card_id;
        private TextView Card_Money;
        private TextView Card_Pay;
        private TextView Time;
    }


    String getSfTime(String time){
        String Time=time.substring(0,4)+"年"+time.substring(4,6)+"月"+time.substring(6,8)+"日"
                +time.substring(8,10)+"时"+time.substring(10,12)+"分"+time.substring(12,14);

        return Time;
    }
}
