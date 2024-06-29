package vlu.android.sqlitelab10_01.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import vlu.android.sqlitelab10_01.Model.Khoa;

public class KhoaHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "qlsv";
    private static final int DB_VERSION =1;
    private static final String TABLE_NAME = "Khoa";
    private static final String MKHOA_COL = "makhoa";
    private static final String TENKHOA_COL = "makhoa";
    private static final String PATH = "/data/data/vlu.android.sqlitelab10_01/database/qlsv.db";

    public KhoaHandler(@Nullable Context context, @Nullable String name,
                       @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    //Tao bang Khoa
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        onUpgrade(sqLiteDatabase,1,2);
        sqLiteDatabase =SQLiteDatabase.openDatabase(PATH,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + MKHOA_COL +" INTEGER NOT NULL UNIQUE, " +
                TENKHOA_COL + " TEXT NOT NULL UNIQUE, PRIMARY KEY(" + MKHOA_COL + "))";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
    //Them mot dong du lieu dau tien vao bang Khoa
    public void initData()
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH,null,SQLiteDatabase.CREATE_IF_NECESSARY);

        String sql = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + MKHOA_COL + ", " + TENKHOA_COL + ") Values ('1','CNTT')";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + MKHOA_COL + ", " + TENKHOA_COL + ") Values ('2','TCKT')";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + MKHOA_COL + ", " + TENKHOA_COL + ") Values ('3','QTKD')";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + MKHOA_COL + ", " + TENKHOA_COL + ") Values ('4','Dien-Co-Mtinh')";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();

    }

    public ArrayList<Khoa> loadKhoaTable()
    {
        ArrayList<Khoa> lsKhoa = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME,null);
        cursor.moveToFirst();
        do {
            Khoa khoa = new Khoa();
            khoa.setMaKhoa(cursor.getString(0));
            khoa.setTenKhoa(cursor.getString(1));
            lsKhoa.add(khoa);
        }while (!cursor.moveToNext());
        cursor.close();
        sqLiteDatabase.close();
        return lsKhoa;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
    }

    //Them 1 dong khoa vao bang Khoa
    public void addRecordIntoKhoaTable (Khoa khoa)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MKHOA_COL,khoa.getMaKhoa());
        contentValues.put(TENKHOA_COL,khoa.getTenKhoa());
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }
    //Xoa 1 dong co id la makhoa trong bang
    public void deleteRecord(String maKhoa)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,MKHOA_COL+ "=?",new String[]{maKhoa});
        sqLiteDatabase.close();
    }
    //Update 1 dong co id la maKhoa trong bang
    public void updateRecord (Khoa khoa)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MKHOA_COL,khoa.getMaKhoa());
        contentValues.put(TENKHOA_COL,khoa.getTenKhoa());
        sqLiteDatabase.update(TABLE_NAME,contentValues,
                MKHOA_COL+"=?",new String[]{khoa.getMaKhoa()});
        close();
    }

}
