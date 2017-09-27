package com.szxb.buspay.module.Parameter;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.szxb.buspay.R;
import com.szxb.buspay.entity.OnLineInfo;
import com.szxb.xblog.XBLog;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class OnlineAdapter extends BaseAdapter {
    private List<OnLineInfo> mList;//数据源
    private LayoutInflater mInflater;//布局装载器对象
    private int position=0;
    private ViewHolder viewHolder;

    private int logpost=0;
    public OnlineAdapter(Context context , List<OnLineInfo> lineInfos) {
        mList=lineInfos;
        mInflater=LayoutInflater.from(context);

    }



    @Override
    public int getCount() {
        return mList.size();

    }


    public int getPosition() {
        return position;
    }

    public void DownKey(){
        if (position==(getCount()-1)){
            position=0;
        }else {
            position++;

        } 
        XBLog.d("DownKey(OnlineAdapter.java:56)"+position);
         notifyDataSetChanged();
    }

    public  void UpKey(){

        if (position==0){
            position=getCount()-1;
        }else {
            position--;

        }
       XBLog.d("UpKey(OnlineAdapter.java:68)"+position);
        notifyDataSetChanged();

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view==null){
            viewHolder=new ViewHolder();
            view=mInflater.inflate(R.layout.parameter_item,null);
            viewHolder.LineNo=(TextView)view.findViewById(R.id.ParameterNo);
            viewHolder.LineText=(TextView)view.findViewById(R.id.ParameterText);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();
        }
        OnLineInfo onLineInfo=mList.get(i);

         if (i==logpost&&position!=logpost){
             view.setBackgroundResource(R.drawable.list_view);
             logpost=position;
         }
        if (i==position){
            view.setBackgroundResource(R.drawable.list_shape);
        }
     //   XBLog.d(onLineInfo.getChinese_name()+onLineInfo.getLineNo());
        viewHolder.LineText.setText(onLineInfo.getChinese_name());
        viewHolder.LineNo.setText(onLineInfo.getLine());
        return view;
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
        private TextView LineNo;
        private TextView LineText;
    }
}
