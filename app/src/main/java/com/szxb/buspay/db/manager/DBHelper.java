package com.szxb.buspay.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.szxb.buspay.db.dao.BlackListCardDao;
import com.szxb.buspay.db.dao.BlackListEntityDao;
import com.szxb.buspay.db.dao.CardPayEntityDao;
import com.szxb.buspay.db.dao.CardRecordDao;
import com.szxb.buspay.db.dao.DaoMaster;
import com.szxb.buspay.db.dao.MacKeyEntityDao;
import com.szxb.buspay.db.dao.OnLineInfoDao;
import com.szxb.buspay.db.dao.PublicKeyEntityDao;
import com.szxb.buspay.db.dao.ScanInfoEntityDao;
import com.szxb.buspay.db.dao.SwipeCardEntityDao;

/**
 * 作者：Tangren_ on 2017/3/23 0023.
 * 邮箱：wu_tangren@163.com
 * TODO：更新数据库
 */


public class DBHelper extends DaoMaster.OpenHelper {
    DBHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db,
                SwipeCardEntityDao.class,
                BlackListEntityDao.class,
                CardPayEntityDao.class,
                CardRecordDao.class,
                OnLineInfoDao.class,
                BlackListCardDao.class,
                SwipeCardEntityDao.class,
                PublicKeyEntityDao.class,
                ScanInfoEntityDao.class,
                MacKeyEntityDao.class);
    }
}
