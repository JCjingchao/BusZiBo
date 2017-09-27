package com.szxb.buspay.module.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szxb.buspay.R;
import com.szxb.buspay.entity.CardRecord;
import com.szxb.xblog.XBLog;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class MenuAdapter extends BaseAdapter {
    private List<String> mList;//数据源
    private LayoutInflater mInflater;//布局装载器对象
    private int position=0;
    private ViewHolder viewHolder;

    private int logpost=0;
    public MenuAdapter(Context context , List<String> Menus) {
        mList=Menus;
        mInflater=LayoutInflater.from(context);

    }

    public int getPosition() {
        return position;
    }

    @Override
    public int getCount() {
        return mList.size();

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

        if (view==null){
            viewHolder=new ViewHolder();
            view=mInflater.inflate(R.layout.menu_item,null);
            viewHolder.Menu_Text=(TextView)view.findViewById(R.id.menu);

            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();
        }
        String Menu=mList.get(i);

         if (i==logpost&&position!=logpost){
             view.setBackgroundResource(R.drawable.list_view);
             logpost=position;
         }
        if (i==position){
            view.setBackgroundResource(R.drawable.list_shape);
        }

        viewHolder.Menu_Text.setText(Menu);


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
        private TextView Menu_Text;

    }
}
