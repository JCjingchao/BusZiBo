package com.szxb.buspay.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.szxb.buspay.entity.OnLineInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ON_LINE_INFO".
*/
public class OnLineInfoDao extends AbstractDao<OnLineInfo, Long> {

    public static final String TABLENAME = "ON_LINE_INFO";

    /**
     * Properties of entity OnLineInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "Id", true, "_id");
        public final static Property Line = new Property(1, String.class, "line", false, "LINE");
        public final static Property Version = new Property(2, String.class, "version", false, "VERSION");
        public final static Property Up_station = new Property(3, String.class, "up_station", false, "UP_STATION");
        public final static Property Dwon_station = new Property(4, String.class, "dwon_station", false, "DWON_STATION");
        public final static Property Chinese_name = new Property(5, String.class, "chinese_name", false, "CHINESE_NAME");
        public final static Property Is_fixed_price = new Property(6, String.class, "is_fixed_price", false, "IS_FIXED_PRICE");
        public final static Property Is_keyboard = new Property(7, String.class, "is_keyboard", false, "IS_KEYBOARD");
        public final static Property Fixed_price = new Property(8, String.class, "fixed_price", false, "FIXED_PRICE");
        public final static Property Coefficient = new Property(9, String.class, "coefficient", false, "COEFFICIENT");
        public final static Property Shortcut_price = new Property(10, String.class, "shortcut_price", false, "SHORTCUT_PRICE");
    }


    public OnLineInfoDao(DaoConfig config) {
        super(config);
    }
    
    public OnLineInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ON_LINE_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: Id
                "\"LINE\" TEXT," + // 1: line
                "\"VERSION\" TEXT," + // 2: version
                "\"UP_STATION\" TEXT," + // 3: up_station
                "\"DWON_STATION\" TEXT," + // 4: dwon_station
                "\"CHINESE_NAME\" TEXT," + // 5: chinese_name
                "\"IS_FIXED_PRICE\" TEXT," + // 6: is_fixed_price
                "\"IS_KEYBOARD\" TEXT," + // 7: is_keyboard
                "\"FIXED_PRICE\" TEXT," + // 8: fixed_price
                "\"COEFFICIENT\" TEXT," + // 9: coefficient
                "\"SHORTCUT_PRICE\" TEXT);"); // 10: shortcut_price
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ON_LINE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, OnLineInfo entity) {
        stmt.clearBindings();
 
        Long Id = entity.getId();
        if (Id != null) {
            stmt.bindLong(1, Id);
        }
 
        String line = entity.getLine();
        if (line != null) {
            stmt.bindString(2, line);
        }
 
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(3, version);
        }
 
        String up_station = entity.getUp_station();
        if (up_station != null) {
            stmt.bindString(4, up_station);
        }
 
        String dwon_station = entity.getDwon_station();
        if (dwon_station != null) {
            stmt.bindString(5, dwon_station);
        }
 
        String chinese_name = entity.getChinese_name();
        if (chinese_name != null) {
            stmt.bindString(6, chinese_name);
        }
 
        String is_fixed_price = entity.getIs_fixed_price();
        if (is_fixed_price != null) {
            stmt.bindString(7, is_fixed_price);
        }
 
        String is_keyboard = entity.getIs_keyboard();
        if (is_keyboard != null) {
            stmt.bindString(8, is_keyboard);
        }
 
        String fixed_price = entity.getFixed_price();
        if (fixed_price != null) {
            stmt.bindString(9, fixed_price);
        }
 
        String coefficient = entity.getCoefficient();
        if (coefficient != null) {
            stmt.bindString(10, coefficient);
        }
 
        String shortcut_price = entity.getShortcut_price();
        if (shortcut_price != null) {
            stmt.bindString(11, shortcut_price);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, OnLineInfo entity) {
        stmt.clearBindings();
 
        Long Id = entity.getId();
        if (Id != null) {
            stmt.bindLong(1, Id);
        }
 
        String line = entity.getLine();
        if (line != null) {
            stmt.bindString(2, line);
        }
 
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(3, version);
        }
 
        String up_station = entity.getUp_station();
        if (up_station != null) {
            stmt.bindString(4, up_station);
        }
 
        String dwon_station = entity.getDwon_station();
        if (dwon_station != null) {
            stmt.bindString(5, dwon_station);
        }
 
        String chinese_name = entity.getChinese_name();
        if (chinese_name != null) {
            stmt.bindString(6, chinese_name);
        }
 
        String is_fixed_price = entity.getIs_fixed_price();
        if (is_fixed_price != null) {
            stmt.bindString(7, is_fixed_price);
        }
 
        String is_keyboard = entity.getIs_keyboard();
        if (is_keyboard != null) {
            stmt.bindString(8, is_keyboard);
        }
 
        String fixed_price = entity.getFixed_price();
        if (fixed_price != null) {
            stmt.bindString(9, fixed_price);
        }
 
        String coefficient = entity.getCoefficient();
        if (coefficient != null) {
            stmt.bindString(10, coefficient);
        }
 
        String shortcut_price = entity.getShortcut_price();
        if (shortcut_price != null) {
            stmt.bindString(11, shortcut_price);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public OnLineInfo readEntity(Cursor cursor, int offset) {
        OnLineInfo entity = new OnLineInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // line
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // version
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // up_station
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // dwon_station
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // chinese_name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // is_fixed_price
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // is_keyboard
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // fixed_price
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // coefficient
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // shortcut_price
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, OnLineInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLine(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setVersion(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUp_station(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDwon_station(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setChinese_name(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIs_fixed_price(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIs_keyboard(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setFixed_price(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCoefficient(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setShortcut_price(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(OnLineInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(OnLineInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(OnLineInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
