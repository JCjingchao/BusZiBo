package com.szxb.buspay.task;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.szxb.buspay.db.dao.BlackListCardDao;
import com.szxb.buspay.db.dao.LineEntityDao;
import com.szxb.buspay.db.dao.OnLineInfoDao;
import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.BlackListCard;
import com.szxb.buspay.entity.LineEntity;
import com.szxb.buspay.entity.OnLineInfo;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.util.Constant;
import com.szxb.buspay.util.sign.FileByte;
import com.szxb.xblog.XBLog;

import org.greenrobot.greendao.annotation.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import static android.R.id.list;
import static com.szxb.buspay.db.manager.DBCore.getDaoSession;

/**
 * 作者：Evergarden on 2017-09-01 11:43
 * QQ：1941042402
 */

public class LoadingData extends Thread {

    private OnPushTask onPushTask;
    private int Type;


    private String name;//文件名
    public void SetLoadType(int type){
        this.Type=type;
    }

    public void SetListenter(OnPushTask onPushTask){
        this.onPushTask=onPushTask;
    }
    public void SetName(String name){
        this.name=name;
    }

    @Override
    public void run() {
        switch (Type){
            case Constant.BlackVersion:
               IsBlackVersion();
                break;

            case Constant.ParamVersion:
                IsPramVersion();
                break;

            case Constant.LoadParamVersion:
                LoadPramVersion();
                break;

            case Constant.BlackList:
              boolean black=  UpBlackData();
                if (black){
                    onPushTask.task(Constant.LoadblacklistSuccess,"");
                } else {
                    onPushTask.task(Constant.LoadblacklistFail, "");
                }
                break;

            case Constant.Parameter:
                Log.d("FTP Parameter",name);
               boolean parameter= UpParameterData(name);
                Log.d("FTP Parameter",name);
                if (parameter)
                    onPushTask.task(Constant.LoadParameterdownSuccess,"");
                else
                    onPushTask.task(Constant.LoadParameterdownFail,name);
                break;
        }
        super.run();
    }

    public void IsPramVersion(){
        FileByte FileByte=new FileByte();
        byte[] PramVesion= FileByte.File2byte(FetchAppConfig.FTPLocalPath() +"allline.json");

        try {
            Log.d("FTP",new String(PramVesion, "GB2312"));
            JSONObject jsonObject = JSONObject.parseObject(new String(PramVesion, "GB2312"));

            if (jsonObject.size() > 0){
                List<JSONObject> version=  ( List<JSONObject>) jsonObject.get("allline");
                Log.d("FTP Version",version.toString());
                List<String> list=getPramName(version);
                Log.d("FTP",list.toString());
                //加载成功 且不需要更新
                if (list.size()==0) {
                    onPushTask.task(Constant.ParamLoadVersionSuccess,"");
                }
                    //加载成功 需要更新
                else{
                    onPushTask.task(Constant.ParamLoadVersionMust,list);
                }

                return;
            }
        } catch (Exception e) {
            XBLog.d("FTP ERRO"+e.getMessage());
        }
        //加载失败 需要重新加载
        onPushTask.task(Constant.ParamLoadVersionFail,"");
    }


    public void LoadPramVersion(){
        FileByte FileByte=new FileByte();
        byte[] PramVesion= FileByte.File2byte(FetchAppConfig.FTPLocalPath() +"allline.json");
        try {
            Log.d("FTP",new String(PramVesion, "GB2312"));
            JSONObject jsonObject = JSONObject.parseObject(new String(PramVesion, "GB2312"));

            if (jsonObject.size() > 0){
                List<JSONObject> version=  ( List<JSONObject>) jsonObject.get("allline");
                LoadPramVersion(version);
                onPushTask.task(Constant.LoadParamVersionSuccess,"");
                return;
            }
        } catch (Exception e) {
            XBLog.d("onDownLoadProgress(DownBlackList.java:62)"+e.getMessage());

        }
        //加载失败 需要重新加载
        onPushTask.task(Constant.LoadParamVersionFail,"");
    }


    public void LoadPramVersion(List<JSONObject> list){
        LineEntityDao lineEntityDao= DBCore.getDaoSession().getLineEntityDao();
        lineEntityDao.deleteAll();
            for (JSONObject jsonObject:list){
                LineEntity lineEntity=new LineEntity();
                String acnt=(String) jsonObject.get("acnt");
                String routeno=(String) jsonObject.get("routeno");
                String routename=(String) jsonObject.get("routename");
                String routeversion=(String) jsonObject.get("routeversion");
                lineEntity.setAcnt(acnt);
                lineEntity.setRouteno(routeno);
                lineEntity.setRoutename(routename);
                lineEntity.setRouteversion(routeversion);
                lineEntityDao.insert(lineEntity);
            }
    }

    public List<String> getPramName(List<JSONObject> list){
        Log.d("FTP name",list.toString());
        List<String> listPram=new ArrayList<>();
        List<LineEntity> lineEntities=DBCore.getDaoSession().getLineEntityDao().loadAll();
        if (lineEntities!=null && lineEntities.size()>0) {
            for (JSONObject jsonObject : list) {
             List<LineEntity> lineEntit=DBCore.getDaoSession().getLineEntityDao().queryBuilder()
                        .where(LineEntityDao.Properties.Acnt.eq((String) jsonObject.get("acnt"))
                                ,LineEntityDao.Properties.Routeno.eq((String) jsonObject.get("routeno"))
                        ,LineEntityDao.Properties.Routeno.eq((String) jsonObject.get("routeversion")))
                        .build().list();
                if (lineEntit.size()>0){
                      listPram.add((String)jsonObject.get("acnt")+","+(String) jsonObject.get("routeno")+".json");
                }else{

                }
                lineEntit.clear();
                }
        }else{
            for (JSONObject jsonObject : list) {
                listPram.add((String)jsonObject.get("acnt")+","+(String) jsonObject.get("routeno")+".json");
            }
        }

        Set<String> set=new HashSet<String>(listPram);
        listPram=new Vector<String>(set);

        return listPram;
    }






    public void IsBlackVersion(){
        FileByte FileByte=new FileByte();
        byte[] blackVesion= FileByte.File2byte(FetchAppConfig.FTPLocalPath() +"blackversion.json");

        try {
         Log.d("FTP",new String(blackVesion, "GB2312"));
            JSONObject jsonObject = JSONObject.parseObject(new String(blackVesion, "GB2312"));

            if (jsonObject.size() > 0){
                String version=  (String) jsonObject.get("blackversion");
                String ver=FetchAppConfig.blackVersion();
                if (version.equals(FetchAppConfig.blackVersion())){
                    //加载黑名单数据
                    Log.d("FTP",version);
                    onPushTask.task(Constant.BlackLoadVersionSuccess,"");
                    return;
                }else{
                    CommonSharedPreferences.put("blackversion",version);
                    onPushTask.task(Constant.BlackLoadVersionMust,"");
                    return;
                }
            }
        } catch (Exception e) {
            XBLog.d("onDownLoadProgress(DownBlackList.java:62)"+e.getMessage());
        }
        onPushTask.task(Constant.BlackLoadVersionFail,"");
    }

    public boolean UpBlackData(){
        FileByte FileByte=new FileByte();
        byte[] blackData= FileByte.File2byte(FetchAppConfig.FTPLocalPath() + FetchAppConfig.BlackListName());
        try {
            XBLog.d("FTP"+new String(blackData, "GB2312"));
            JSONObject jsonObject = JSONObject.parseObject(new String(blackData, "GB2312"));
            List<String> list = (List<String>) jsonObject.get("blacklist");
            if (list.size() > 0) {
                XBLog.d(list.toString());
                BlackListDao(list);
                return true;
            }
        } catch (Exception e) {
            XBLog.d("onDownLoadProgress(DownBlackList.java:62)"+e.getMessage());
        }

        return false;

    }


    public boolean UpParameterData(String name){

            FileByte FileByte = new FileByte();
            byte[] ParameterData = FileByte.File2byte(FetchAppConfig.FTPLocalPath() + name);
            try {

                JSONObject jsonObject = JSONObject.parseObject(new String(ParameterData, "GB2312"));
                if (jsonObject!=null){
                    Log.d("FTP",jsonObject.toJSONString());
                    ParameterDao(jsonObject);
                    return true;
                }

            } catch (Exception e) {
                Log.d("FTP",e.getMessage());
            }


        return false;

    }




    public void BlackListDao(List<String> list){
        BlackListCardDao dao= getDaoSession().getBlackListCardDao();
        dao.deleteAll();

        for (String card_id:list){
            BlackListCard blackListCard=new BlackListCard();
            blackListCard.setCard_id(card_id);
            dao.insert(blackListCard);
              List<BlackListCard> listCards=dao.loadAll();
            Log.d("FTP",listCards.get(listCards.size()-1).toString());
        }

    }

    //保存参数
    public void ParameterDao(JSONObject object){
            OnLineInfoDao dao= getDaoSession().getOnLineInfoDao();
            OnLineInfo onLineInfo=new OnLineInfo();
            onLineInfo.setLine((String)object.get("line"));
            onLineInfo.setVersion((String)object.get("version"));
            onLineInfo.setUp_station((String)object.get("up_station"));
            onLineInfo.setDwon_station((String)object.get("down_station"));
            onLineInfo.setChinese_name((String)object.get("chinese_name"));
            onLineInfo.setIs_fixed_price((String)object.get("is_fixed_price"));
            onLineInfo.setIs_keyboard((String)object.get("is_keyboard"));
            onLineInfo.setFixed_price((String)object.get("fixed_price"));
            onLineInfo.setCoefficient((String)object.get("coefficient"));
            onLineInfo.setShortcut_price((String)object.get("shortcut_price"));
            dao.insert(onLineInfo);
            List<OnLineInfo> lineInfos=dao.loadAll();
            Log.d("FTP",lineInfos.get(lineInfos.size()-1).toString());
    }
}
