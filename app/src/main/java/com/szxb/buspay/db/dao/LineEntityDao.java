package com.szxb.buspay.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.szxb.buspay.entity.LineEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LINE_ENTITY".
*/
public class LineEntityDao extends AbstractDao<LineEntity, Long> {

    public static final String TABLENAME = "LINE_ENTITY";

    /**
     * Properties of entity LineEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "Id", true, "_id");
        public final static Property Acnt = new Property(1, String.class, "acnt", false, "ACNT");
        public final static Property Routeno = new Property(2, String.class, "routeno", false, "ROUTENO");
        public final static Property Routename = new Property(3, String.class, "routename", false, "ROUTENAME");
        public final static Property Routeversion = new Property(4, String.class, "routeversion", false, "ROUTEVERSION");
    }


    public LineEntityDao(DaoConfig config) {
        super(config);
    }
    
    public LineEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LINE_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: Id
                "\"ACNT\" TEXT," + // 1: acnt
                "\"ROUTENO\" TEXT," + // 2: routeno
                "\"ROUTENAME\" TEXT," + // 3: routename
                "\"ROUTEVERSION\" TEXT);"); // 4: routeversion
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LINE_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LineEntity entity) {
        stmt.clearBindings();
 
        Long Id = entity.getId();
        if (Id != null) {
            stmt.bindLong(1, Id);
        }
 
        String acnt = entity.getAcnt();
        if (acnt != null) {
            stmt.bindString(2, acnt);
        }
 
        String routeno = entity.getRouteno();
        if (routeno != null) {
            stmt.bindString(3, routeno);
        }
 
        String routename = entity.getRoutename();
        if (routename != null) {
            stmt.bindString(4, routename);
        }
 
        String routeversion = entity.getRouteversion();
        if (routeversion != null) {
            stmt.bindString(5, routeversion);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LineEntity entity) {
        stmt.clearBindings();
 
        Long Id = entity.getId();
        if (Id != null) {
            stmt.bindLong(1, Id);
        }
 
        String acnt = entity.getAcnt();
        if (acnt != null) {
            stmt.bindString(2, acnt);
        }
 
        String routeno = entity.getRouteno();
        if (routeno != null) {
            stmt.bindString(3, routeno);
        }
 
        String routename = entity.getRoutename();
        if (routename != null) {
            stmt.bindString(4, routename);
        }
 
        String routeversion = entity.getRouteversion();
        if (routeversion != null) {
            stmt.bindString(5, routeversion);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LineEntity readEntity(Cursor cursor, int offset) {
        LineEntity entity = new LineEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // acnt
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // routeno
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // routename
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // routeversion
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LineEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAcnt(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setRouteno(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRoutename(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRouteversion(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LineEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LineEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LineEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
