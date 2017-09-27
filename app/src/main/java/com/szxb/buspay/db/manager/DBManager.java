package com.szxb.buspay.db.manager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szxb.buspay.db.dao.BlackListEntityDao;
import com.szxb.buspay.db.dao.MacKeyEntityDao;
import com.szxb.buspay.db.dao.PublicKeyEntityDao;
import com.szxb.buspay.db.dao.ScanInfoEntityDao;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.BlackListEntity;
import com.szxb.buspay.entity.MacKeyEntity;
import com.szxb.buspay.entity.PublicKeyEntity;
import com.szxb.buspay.entity.ScanInfoEntity;
import com.szxb.buspay.util.DateUtil;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * 作者：Tangren on 2017/6/21 10:02
 * 邮箱：wu_tangren@163.com
 * TODO:数据库操作类
 */
public class DBManager<T> {

    /**
     * 扫码数据
     *
     * @return 扫码List
     */
    public static List<ScanInfoEntity> getScanEntityList() {
        ScanInfoEntityDao dao = DBCore.getDaoSession().getScanInfoEntityDao();
        String lastTimePushFile = FetchAppConfig.lastTimePushFile();
        if (!lastTimePushFile.equals("")) {
            return dao.queryBuilder().where(ScanInfoEntityDao.Properties.Time.between(lastTimePushFile, DateUtil.getCurrentDate())).build().list();
        } else {
            return dao.queryBuilder().build().list();
        }
    }


    /**
     * 获取公钥列表
     *
     * @return
     */
    public static List<PublicKeyEntity> getPublicKeylist() {
        PublicKeyEntityDao dao = DBCore.getDaoSession().getPublicKeyEntityDao();
        return dao.queryBuilder().build().list();
    }

    /***
     * 获取公钥

     * @param keyId 公钥ID
     * @return
     */
    public static String getPublicKey(String keyId) {
        PublicKeyEntityDao dao = DBCore.getDaoSession().getPublicKeyEntityDao();
        PublicKeyEntity unique = dao.queryBuilder().where(PublicKeyEntityDao.Properties.Key_id.eq(keyId)).build().unique();
        if (unique != null)
            return unique.getPubkey();
        return "";
    }

    /**
     * 获取mac秘钥列表
     *
     * @return
     */
    public static List<MacKeyEntity> getMacList() {
        MacKeyEntityDao dao = DBCore.getDaoSession().getMacKeyEntityDao();
        return dao.queryBuilder().build().list();
    }

    /**
     * 获取mac秘钥
     *
     * @param keyId
     * @return
     */
    public static String getMac(String keyId) {
        MacKeyEntityDao dao = DBCore.getDaoSession().getMacKeyEntityDao();
        MacKeyEntity unique = dao.queryBuilder().where(MacKeyEntityDao.Properties.Key_id.eq(keyId)).unique();
        if (unique != null)
            return unique.getPubkey();
        return "";

    }

    /**
     * 是否属于黑名单
     *
     * @param openID
     * @return
     */
    public static boolean filterBlackName(String openID) {
        BlackListEntityDao dao = DBCore.getDaoSession().getBlackListEntityDao();
        Query<BlackListEntity> build = dao.queryBuilder().where(BlackListEntityDao.Properties.Open_id.eq(openID),
                BlackListEntityDao.Properties.Time.le(DateUtil.currentLong())).build();
        BlackListEntity blackEntity = build.unique();
        return blackEntity != null;
    }


    /**
     * 验码成功,保存数据
     *
     * @param object
     */
    public static void insert(JSONObject object, String mch_trx_id) {
        ScanInfoEntity infoEntity = new ScanInfoEntity();
        infoEntity.setStatus(1);//1表示未扣款
        infoEntity.setBiz_data_single(object.toJSONString());
        infoEntity.setMch_trx_id(mch_trx_id);
        infoEntity.setTime(DateUtil.getCurrentDate());
        DBCore.getDaoSession().getScanInfoEntityDao().insert(infoEntity);
    }


    /**
     * @return 得到未支付的数据每次最多25条, 降序排列取得最新的最多25条数据
     */
    public static List<ScanInfoEntity> getSwipeList() {
        ScanInfoEntityDao dao = DBCore.getDaoSession().getScanInfoEntityDao();
        Query<ScanInfoEntity> qb = dao.queryBuilder().whereOr(ScanInfoEntityDao.Properties.Status.eq(1),
                ScanInfoEntityDao.Properties.Status.eq(4))
                .limit(25).orderDesc(ScanInfoEntityDao.Properties.Id).build();
        return qb.list();
    }


    /**
     * 删除过期的黑名单
     */
    private static void deleteOverDueBlackName() {
        BlackListEntityDao dao = DBCore.getDaoSession().getBlackListEntityDao();
        List<BlackListEntity> list = dao.queryBuilder().where(BlackListEntityDao.Properties.Time
                .le(DateUtil.currentLong())).build().list();
        dao.deleteInTx(list);
    }

    /**
     * 更新黑名单
     *
     * @param memberList
     */
    public static void addBlackList(JSONArray memberList) {
        deleteOverDueBlackName();
        for (int i = 0; i < memberList.size(); i++) {
            JSONObject object = memberList.getJSONObject(i);
            BlackListEntity entity = new BlackListEntity();
            entity.setOpen_id(object.getString("open_id"));
            entity.setTime(object.getLong("time"));
            DBCore.getDaoSession().insert(entity);
        }
    }


    /**
     * 修改支付状态
     *
     * @param mch_trx_id 订单号
     * @param status     支付状态1:新订单，未支付，0:准实时扣款，2：批处理扣款成功 3：扣款失败后台处理,4：系统错误再次下次提交
     * @param result     返回码
     * @param tr_status  交易状态码
     */
    public static void updateTransInfo(String mch_trx_id, int status, String result, String tr_status) {
        ScanInfoEntityDao dao = DBCore.getDaoSession().getScanInfoEntityDao();
        ScanInfoEntity entity = dao.queryBuilder().where(ScanInfoEntityDao.Properties.Mch_trx_id.eq(mch_trx_id)).build().unique();
        if (entity != null) {
            entity.setStatus(status);
            if (result != null)
                entity.setResult(result);
            if (tr_status != null)
                entity.setTr_status(tr_status);
            dao.update(entity);
        }
    }


}
