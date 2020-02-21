package com.example.dinda.Libraries;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    SQLiteDatabase db;
    ContentResolver contentResolver;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MILLI";

    // Table Names
    public static final String TABLE_USER = "USER";
    public static final String TABLE_MASTER = "MASTER";
    public static final String TABLE_MESIN = "MESIN";
//    public static final String TABLE_COUNT_FLAG_INPUT = "COUNT_FLAG_INPUT";
//    public static final String TABLE_PRESS_FLAG_INPUT = "PRESS_FLAG_INPUT";
    public static final String TABLE_VERSION_UPDATE = "VERSION_UPDATE";
    public static final String TABLE_LOG_PENGIRIMAN = "LOG_PENGIRIMAN";

    // Common column names
    public static final String KEY_ID = "ID";
    public static final String KEY_COMPANY_ID = "COMPANY_ID";
    public static final String KEY_MILLCODE = "MILLCODE";
    public static final String KEY_CREATED_AT = "TDATE";
    public static final String KEY_STATION = "STATION";
    public static final String KEY_UNIT = "UNIT";
    public static final String KEY_UNITCODE = "UNITCODE";
    public static final String KEY_TYPECODE = "TYPECODE";

    // USER TABLE -  COLUMN NAMES
    public static final String KEY_JABATAN = "JABATAN";
    public static final String KEY_EMP_CODE = "EMP_CODE";
    public static final String KEY_NAMA = "NAMA";
    public static final String KEY_EMP_NO = "EMP_NO";
    public static final String KEY_SITE = "SITE";
    public static final String KEY_PASS = "PASS";
    public static final String KEY_PHOTO_PATH = "PHOTO_PATH";

    // MASTER Table - column names
    public static final String KEY_UNITNAME = "UNITNAME";
    public static final String KEY_STD_PARAM = "STD_PARAM";
    public static final String KEY_UPPER_LIMIT = "UPPER_LIMIT";
    public static final String KEY_LOWER_LIMIT = "LOWER_LIMIT";
    public static final String KEY_UOM = "UOM";
    public static final String KEY_NOM = "NOM";
    public static final String KEY_ACTIVE = "ACTIVE";
    public static final String KEY_QR_CODE = "QR_CODE";

    // MESIN Table - column names
    public static final String KEY_SHIFT = "SHIFT";
    public static final String KEY_HOURS = "HOURS";
    public static final String KEY_SUBTYPECODE = "SUBTYPECODE";
    public static final String KEY_ACTUAL = "ACTUAL";
    public static final String KEY_IMAGE1 = "FILENAME_1";
    public static final String KEY_IMAGE2 = "FILENAME_2";
    public static final String KEY_CREATE_BY = "CREATE_BY";
    public static final String KEY_CREATE_DATE = "CREATE_DATE";
    public static final String KEY_UPDATE_BY = "UPDATE_BY";
    public static final String KEY_UPDATE_DATE = "UPDATE_DATE";
    public static final String KEY_STATUS = "STATUS";
    public static final String KEY_SERIAL_NUMBER = "SERIAL_NUMBER";
    public static final String KEY_FLAG_INPUT = "FLAG_INPUT";

    // COUNT_FLAG_INPUT Table - column names
//    public static final String KEY_COUNT = "COUNT";
//    public static final String KEY_DATE = "DATE";

    // PRESS_FLAG_INPUT
//    public static final String KEY_ID_MESIN = "ID_MESIN";
    public static final String KEY_UNITCODE_DUMMY = "UNITCODE_DUMMY";

    // VERSION_UPDATE Table = colmn names
    public static final String KEY_VERSION = "VERSION";

    // LOG_PENGIRIMAN - column
    public static final String KEY_LOG_STATUS = "LOG_STATUS";


    // NOTE_TAGS Table - column names
//    private static final String KEY_TODO_ID = "todo_id";
//    private static final String KEY_TAG_ID = "tag_id";

    // Table Create Statements
    //  USER table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_JABATAN + " TEXT,"
            + KEY_EMP_CODE + " TEXT,"
            + KEY_NAMA + " TEXT,"
            + KEY_EMP_NO + " TEXT,"
            + KEY_SITE + " TEXT,"
            + KEY_PASS + " TEXT,"
            + KEY_PHOTO_PATH + " TEXT,"
            + KEY_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")";

    // MASTER table create statement
    private static final String CREATE_TABLE_MASTER = "CREATE TABLE "
            + TABLE_MASTER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_COMPANY_ID + " TEXT,"
            + KEY_UNITCODE + " TEXT,"
            + KEY_UNITNAME + " TEXT,"
            + KEY_STATION + " TEXT,"
            + KEY_UNIT + " TEXT,"
            + KEY_TYPECODE + " TEXT,"
            + KEY_STD_PARAM + " TEXT,"
            + KEY_UPPER_LIMIT + " TEXT,"
            + KEY_LOWER_LIMIT + " TEXT,"
            + KEY_UOM + " TEXT,"
            + KEY_NOM + " TEXT,"
            + KEY_ACTIVE + " TEXT,"
            + KEY_QR_CODE + " TEXT,"
            + KEY_MILLCODE + " TEXT,"
            + KEY_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")";

    // MESIN table create statement
    private static final String CREATE_TABLE_MESIN = "CREATE TABLE "
            + TABLE_MESIN + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_COMPANY_ID + " TEXT NOT NULL,"
            + KEY_MILLCODE + " TEXT NOT NULL,"
            + KEY_CREATED_AT + " TEXT NOT NULL,"
            + KEY_SHIFT + " TEXT NOT NULL,"
            + KEY_HOURS + " TEXT NOT NULL,"
            + KEY_STATION + " TEXT NOT NULL,"
            + KEY_UNIT + " TEXT NOT NULL,"
            + KEY_UNITCODE + " TEXT NOT NULL,"
            + KEY_UNITCODE_DUMMY + " TEXT NOT NULL,"
            + KEY_TYPECODE + " TEXT NOT NULL,"
            + KEY_SUBTYPECODE + " TEXT NOT NULL,"
            + KEY_ACTUAL + " DOUBLE NOT NULL,"
            + KEY_IMAGE1 + " TEXT,"
            + KEY_IMAGE2+ " TEXT,"
            + KEY_CREATE_BY + " TEXT NOT NULL,"
            + KEY_CREATE_DATE + " TEXT NOT NULL,"
            + KEY_UPDATE_BY + " TEXT NOT NULL,"
            + KEY_UPDATE_DATE + " TEXT NOT NULL,"
            + KEY_STATUS + " INT,"
            + KEY_SERIAL_NUMBER + " TEXT NOT NULL,"
            + KEY_FLAG_INPUT + " TEXT NOT NULL" + ")";

//    // COUNT_FLAG_INPUT table create statement
//    private static final String CREATE_TABLE_COUNT_FLAG_INPUT = "CREATE TABLE "
//            + TABLE_COUNT_FLAG_INPUT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + KEY_CREATE_BY + " TEXT NOT NULL,"
//            + KEY_COUNT + " INTEGER NOT NULL,"
//            + KEY_HOURS + " TEXT NOT NULL,"
//            + KEY_DATE + " TEXT NOT NULL" + ")";
//
//    // PRESS_FLAG_INPUT table create statement
//    private static final String CREATE_TABLE_PRESS_FLAG_INPUT = "CREATE TABLE "
//            + TABLE_PRESS_FLAG_INPUT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + KEY_STATION + " TEXT NOT NULL,"
//            + KEY_UNITCODE + " TEXT NOT NULL,"
//            + KEY_UNITCODE_DUMMY + " TEXT NOT NULL,"
//            + KEY_CREATE_BY + " TEXT NOT NULL,"
//            + KEY_HOURS + " TEXT NOT NULL,"
//            + KEY_DATE + " TEXT NOT NULL" + ")";

    // UPDATE_VERSION table create statement
    private static final String CREATE_TABLE_VERSION_UPDATE = "CREATE TABLE "
            + TABLE_VERSION_UPDATE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_VERSION + " TEXT NOT NULL,"
            + KEY_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")";

    // LOG_PENGIRIMAN table create statement
    private static final String CREATE_TABLE_LOG_PENGIRIMAN = "CREATE TABLE "
            + TABLE_LOG_PENGIRIMAN + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_LOG_STATUS + " TEXT NOT NULL,"
            + KEY_CREATED_AT + " TEXT NOT NULL" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        contentResolver = context.getContentResolver();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MASTER);
        db.execSQL(CREATE_TABLE_MESIN);
//        db.execSQL(CREATE_TABLE_COUNT_FLAG_INPUT);
//        db.execSQL(CREATE_TABLE_PRESS_FLAG_INPUT);
        db.execSQL(CREATE_TABLE_VERSION_UPDATE);
        db.execSQL(CREATE_TABLE_LOG_PENGIRIMAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESIN);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNT_FLAG_INPUT);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESS_FLAG_INPUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VERSION_UPDATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG_PENGIRIMAN);
        onCreate(db);
    }

    public boolean checkUser(String emp_code){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+KEY_EMP_CODE+" FROM "+TABLE_USER+" where "+KEY_EMP_CODE+" = '"+ emp_code +"'", null);
        return cursor.getCount() == 0;
    }

    public boolean addUser(String jabatan, String empCode, String nama, String empNo, String site) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_JABATAN, jabatan);
        contentValues.put(KEY_EMP_CODE, empCode);
        contentValues.put(KEY_NAMA, nama);
        contentValues.put(KEY_EMP_NO, empNo);
        contentValues.put(KEY_SITE, site);
        long result = db.insert(TABLE_USER, null, contentValues);
        db.close();
        return result != -1;
    }

    public Cursor getUserByEmpCode(String empCode){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM "+TABLE_USER+" WHERE "+KEY_EMP_CODE+" = '"+empCode+"'", null);
    }

    public void deleteUserByEmpCode(String empCode){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_USER+" WHERE "+KEY_EMP_CODE+" = '"+ empCode +"'");
        db.close();
    }

    public boolean checkUserPassword(String empCode){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT("+KEY_PASS+") FROM "+TABLE_USER+" where "+KEY_EMP_CODE+" ='"+ empCode +"'", null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        return icount > 0;
    }

//    public boolean regisPass(String nik, String password, String photo_path){
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(KEY_PASS, password);
//        contentValues.put(KEY_PHOTO_PATH, photo_path);
//        long result = db.update(TABLE_USER, contentValues, KEY_EMP_CODE+" =" + nik, null);
//        db.close();
//        return result != -1;
//    }

    public boolean regisPass(String nik, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PASS, password);
//        contentValues.put(KEY_PHOTO_PATH, photo_path);
        long result = db.update(TABLE_USER, contentValues, KEY_EMP_CODE+" = '"+ nik +"'", null);
        db.close();
        return result != -1;
    }

    public boolean updateFoto(String nik, String photo_path){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(KEY_PASS, password);
        contentValues.put(KEY_PHOTO_PATH, photo_path);
        long result = db.update(TABLE_USER, contentValues, KEY_EMP_CODE+" = '"+ nik +"'", null);
        db.close();
        return result != -1;
    }

    public boolean updatePassword(String empcode, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PASS, password);
        long result = db.update(TABLE_USER, contentValues, KEY_EMP_CODE+" = '" +empcode+"'", null);
        db.close();
        return result != -1;
    }

//    public ArrayList<UserModel> getAllDataUser(){
//        ArrayList<UserModel> arrayList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT "+KEY_EMP_CODE+", "+KEY_NAMA+" FROM "+TABLE_USER+"", null);
//
//        while (cursor.moveToNext()){
//            String nik = cursor.getString(0);
//            String nama = cursor.getString(1);
//            UserModel user = new UserModel(nik, nama);
//
//            arrayList.add(user);
//        }
//
//        return arrayList;
//    }

    //method insert
    public boolean insertVersion(String version){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_VERSION, version);

        long result = db.insert(TABLE_VERSION_UPDATE, KEY_ID, c);
        return result != -1;
    }

    //method insert
    public boolean insertLogPengiriman(String log, String tdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_LOG_STATUS, log);
        c.put(KEY_CREATED_AT, tdate);

        long result = db.insert(TABLE_LOG_PENGIRIMAN, KEY_ID, c);
        return result != -1;
    }

    //method insert master
    public boolean insertMaster(String companyId, String unitcode, String unitname, String station,
                                String unit, String typecode, String stdParam, String upperLimit,
                                String lowerLimit, String uom, String nom, String active, String qrCode,
                                String millcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_COMPANY_ID, companyId);
        c.put(KEY_UNITCODE, unitcode);
        c.put(KEY_UNITNAME, unitname);
        c.put(KEY_STATION, station);
        c.put(KEY_UNIT, unit);
        c.put(KEY_TYPECODE, typecode);
        c.put(KEY_STD_PARAM, stdParam);
        c.put(KEY_UPPER_LIMIT, upperLimit);
        c.put(KEY_LOWER_LIMIT, lowerLimit);
        c.put(KEY_UOM, uom);
        c.put(KEY_NOM, nom);
        c.put(KEY_ACTIVE, active);
        c.put(KEY_QR_CODE, qrCode);
        c.put(KEY_MILLCODE, millcode);

        long result = db.insert(TABLE_MASTER, KEY_ID, c);
        return result != -1;
    }

    //method insert mesin
    /*untuk status :    0 success ke server
    *                   1 gagal ke server
    *                   2 siap untuk disimpan ke server */
    public boolean insertMesin(String companyId, String millcode, String tdate, String shift,
                               String hours, String station, String unit, String unitcode, String unitcodeDummy,
                               String typecode, String subtypecode, double actual, String image1,
                               String image2, String createBy, String createDate, String updateBy,
                               String updateDate, int status, String serialNumber, String flagInput) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_COMPANY_ID, companyId);
        c.put(KEY_MILLCODE, millcode);
        c.put(KEY_CREATED_AT, tdate);
        c.put(KEY_SHIFT, shift);
        c.put(KEY_HOURS, hours);
        c.put(KEY_STATION, station);
        c.put(KEY_UNIT, unit);
        c.put(KEY_UNITCODE, unitcode);
        c.put(KEY_UNITCODE_DUMMY, unitcodeDummy);
        c.put(KEY_TYPECODE, typecode);
        c.put(KEY_SUBTYPECODE, subtypecode);
        c.put(KEY_ACTUAL, actual);
        c.put(KEY_IMAGE1, image1);
        c.put(KEY_IMAGE2, image2);
        c.put(KEY_CREATE_BY, createBy);
        c.put(KEY_CREATE_DATE, createDate);
        c.put(KEY_UPDATE_BY, updateBy);
        c.put(KEY_UPDATE_DATE, updateDate);
        c.put(KEY_STATUS, status);
        c.put(KEY_SERIAL_NUMBER, serialNumber);
        c.put(KEY_FLAG_INPUT, flagInput);

        long result = db.insert(TABLE_MESIN, KEY_ID, c);
        return result != -1;
    }

//    public boolean insertCount(String createBy, int count, String hours, String date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(KEY_CREATE_BY, createBy);
//        contentValues.put(KEY_COUNT, count);
//        contentValues.put(KEY_HOURS, hours);
//        contentValues.put(KEY_DATE, date);
//        long result = db.insert(TABLE_COUNT_FLAG_INPUT, null, contentValues);
//        db.close();
//        return result != -1;
//    }

//    public boolean insertPress(String station, String unitcode, String unitcodeDummy, String createBy, String hours, String date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(KEY_STATION, station);
//        contentValues.put(KEY_UNITCODE, unitcode);
//        contentValues.put(KEY_UNITCODE_DUMMY, unitcodeDummy);
//        contentValues.put(KEY_CREATE_BY, createBy);
//        contentValues.put(KEY_HOURS, hours);
//        contentValues.put(KEY_DATE, date);
//        long result = db.insert(TABLE_PRESS_FLAG_INPUT, null, contentValues);
//        db.close();
//        return result != -1;
//    }

//    public boolean updateCount(String createBy, int count, String hours, String date) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues c = new ContentValues();
//        c.put(KEY_COUNT, count);
//        long result = db.update(TABLE_COUNT_FLAG_INPUT, c, KEY_CREATE_BY+" = ? AND "+KEY_HOURS+" = ? AND "+KEY_DATE+" = ?", new String[]{createBy, hours, date});
//        db.close();
//        return result != -1;
//    }

    //method update
    public boolean updatetMaster(String id, String companyId, String unitcode, String unitname, String station,
                                String unit, String typecode, String stdParam, String upperLimit,
                                String lowerLimit, String uom, String nom, String active, String qrCode,
                                String millcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_ID, id);
        c.put(KEY_COMPANY_ID, companyId);
        c.put(KEY_UNITCODE, unitcode);
        c.put(KEY_UNITNAME, unitname);
        c.put(KEY_STATION, station);
        c.put(KEY_UNIT, unit);
        c.put(KEY_TYPECODE, typecode);
        c.put(KEY_STD_PARAM, stdParam);
        c.put(KEY_UPPER_LIMIT, upperLimit);
        c.put(KEY_LOWER_LIMIT, lowerLimit);
        c.put(KEY_UOM, nom);
        c.put(KEY_NOM, nom);
        c.put(KEY_ACTIVE, active);
        c.put(KEY_QR_CODE, qrCode);
        c.put(KEY_MILLCODE, millcode);

        long result = db.update(TABLE_MASTER, c, "ID = ?", new String[]{id});
        return result != -1;
    }

    //method update mesin
    public boolean updateMesin(String id, String companyId, String millcode, String tdate, String shift,
                               String hours, String station, String unit, String unitcode,
                               String typecode, String subtypecode, String actual, byte[] image1,
                               byte[] image2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_ID, id);
        c.put(KEY_COMPANY_ID, companyId);
        c.put(KEY_MILLCODE, millcode);
        c.put(KEY_CREATED_AT, tdate);
        c.put(KEY_SHIFT, shift);
        c.put(KEY_HOURS, hours);
        c.put(KEY_STATION, station);
        c.put(KEY_UNIT, unit);
        c.put(KEY_UNITCODE, unitcode);
        c.put(KEY_TYPECODE, typecode);
        c.put(KEY_SUBTYPECODE, subtypecode);
        c.put(KEY_ACTUAL, actual);
        c.put(KEY_IMAGE1, image1);
        c.put(KEY_IMAGE2, image2);

        long result = db.update(TABLE_MESIN, c, "ID = ?", new String[]{id});
        return result != -1;
    }

    public boolean updateHour(String id, String hours) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
//        c.put(KEY_ID, id);
//        c.put(KEY_COMPANY_ID, companyId);
//        c.put(KEY_MILLCODE, millcode);
//        c.put(KEY_CREATED_AT, tdate);
//        c.put(KEY_SHIFT, shift);
        c.put(KEY_HOURS, hours);
//        c.put(KEY_STATION, station);
//        c.put(KEY_UNIT, unit);
//        c.put(KEY_UNITCODE, unitcode);
//        c.put(KEY_TYPECODE, typecode);
//        c.put(KEY_SUBTYPECODE, subtypecode);
//        c.put(KEY_ACTUAL, actual);
//        c.put(KEY_IMAGE1, image1);
//        c.put(KEY_IMAGE2, image2);

        long result = db.update(TABLE_MESIN, c, "ID = ?", new String[]{id});
        return result != -1;
    }

//    public boolean updateHourPress(String station, String unitcode, String createBy, String hours, String date, String hoursValue) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
////        c.put(KEY_ID, id);
////        c.put(KEY_COMPANY_ID, companyId);
////        c.put(KEY_MILLCODE, millcode);
////        c.put(KEY_CREATED_AT, tdate);
////        c.put(KEY_SHIFT, shift);
//        c.put(KEY_HOURS, hoursValue);
////        c.put(KEY_STATION, station);
////        c.put(KEY_UNIT, unit);
////        c.put(KEY_UNITCODE, unitcode);
////        c.put(KEY_TYPECODE, typecode);
////        c.put(KEY_SUBTYPECODE, subtypecode);
////        c.put(KEY_ACTUAL, actual);
////        c.put(KEY_IMAGE1, image1);
////        c.put(KEY_IMAGE2, image2);
//
//        db.update(TABLE_PRESS_FLAG_INPUT, c, "STATION = ? AND UNITCODE = ? AND CREATE_BY = ? AND HOURS = ? AND DATE = ?", new String[]{station, unitcode, createBy, hours, date});
//        return true;
//    }

    //method delete master
    public boolean deleteMaster(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_MASTER, null, null);
        return result != -1;
    }

    //method delete mesin
    public boolean deleteMesin(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_MESIN, null, null);
        return result != -1;
    }

    public boolean deleteMesinById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_MESIN, KEY_ID+" <= ?", new String[]{String.valueOf(id)});
        return result != -1;
    }

    public List<String> getAllStation(String millcode){
        List<String> labels = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_STATION};
        Cursor cursor = db.query(true, TABLE_MASTER, columns, "MILLCODE=?", new String[] { millcode }, null, null, KEY_STATION +" ASC", null);

        labels.add(0, "PILIH STATION");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        //returning labels
        return labels;
    }

    public List<String> getAllUnitcode(String millcode, String station){
        List<String> labels = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_UNITCODE};
        Cursor cursor = db.query(TABLE_MASTER, columns, "MILLCODE=? AND STATION=?", new String[] {millcode, station }, null, null, KEY_UNITCODE +" ASC");
        labels.add(0, "PILIH UNITCODE");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        //returning labels
        return labels;
    }

    public List<String> getVrsion(){
        List<String> labels = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_VERSION};
        Cursor cursor = db.query(TABLE_VERSION_UPDATE, columns, null, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        //returning labels
        return labels;
    }

    public List<String> getFilterData(String station, String unitcode, String millcode){
        List<String> labels = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_UNITCODE};
        Cursor cursor = db.query(TABLE_MASTER, columns, "STATION=? AND UNITCODE=? AND MILLCODE=?", new String[] { station, unitcode, millcode }, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        //returning labels
        return labels;
    }

    public void deleteTable(String table) {
        String sql = "DELETE FROM " + table.toLowerCase();
        this.getReadableDatabase().execSQL(sql);
    }

    public boolean updateStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
//        c.put(KEY_ID_AKUN, idAkun);
//        c.put(KEY_NM_KAT, nmKat);
//        c.put(KEY_NM_MENU, nmMenu);
        c.put(KEY_STATUS, status);
//        c.put(KEY_NM_SATUAN, nmSatuan);
//        c.put(KEY_KET_LIST_PESANAN, ketListPesanan);
//        c.put(KEY_STATUS_DISKON, statusDiskon);
//        c.put(KEY_STATUS_MASAK, statusMasak);

        long result = db.update(TABLE_MESIN, c, KEY_ID+" = ?",
                new String[] {String.valueOf(id)});
        return result != -1;
    }

    public boolean updateStatusLaporan(String station, String unitcode,String createDate, String hours, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
//        c.put(KEY_ID_AKUN, idAkun);
//        c.put(KEY_NM_KAT, nmKat);
//        c.put(KEY_NM_MENU, nmMenu);
        c.put(KEY_STATUS, status);
//        c.put(KEY_NM_SATUAN, nmSatuan);
//        c.put(KEY_KET_LIST_PESANAN, ketListPesanan);
//        c.put(KEY_STATUS_DISKON, statusDiskon);
//        c.put(KEY_STATUS_MASAK, statusMasak);

        long result = db.update(TABLE_MESIN, c, KEY_STATION+" = ? AND "+KEY_UNITCODE+" = ? AND "+KEY_CREATE_DATE+" = ? AND "+KEY_HOURS+" = ?",
                new String[] {station, unitcode, createDate, hours});
        return result != -1;
    }

    public void insertTable(String table, String column, String result){
        Log.e("table", table.toUpperCase());
        Log.e("column", column);
        Log.e("data", result);
        if (table.equals(TABLE_MESIN)) {
            //data
            String biasa[] = result.split("\",\"");
            String norealId = biasa[0];
            String norealCompanyId = biasa[1];
            String norealMillcode = biasa[2];
            String norealCreatedAt = biasa[3];
            String norealShift = biasa[4];
            String norealHours = biasa[5];
            String norealStation = biasa[6];
            String norealUnit = biasa[7];
            String norealUnitcode = biasa[8];
            String norealUnitcodeDumy = biasa[9];
            String norealTypecode = biasa[10];
            String norealSubtypecode = biasa[11];
            String norealActual = biasa[12];
            String norealImage1 = biasa[13];
            String norealImage2 = biasa[14];
            String norealCreateBy = biasa[15];
            String norealCreateDate = biasa[16];
            String norealUpdateBy = biasa[17];
            String norealUpdateDate = biasa[18];
            String norealStatus = biasa[19];
            String norealSerialNumber = biasa[20];
            String norealFlagInput[] = biasa[21].split("\"");

//            Log.e("flag", norealFlagInput[0]);

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues c = new ContentValues();
            c.put(KEY_COMPANY_ID, norealCompanyId);
            c.put(KEY_MILLCODE, norealMillcode);
            c.put(KEY_CREATED_AT, norealCreatedAt);
            c.put(KEY_SHIFT, norealShift);
            c.put(KEY_HOURS, norealHours);
            c.put(KEY_STATION, norealStation);
            c.put(KEY_UNIT, norealUnit);
            c.put(KEY_UNITCODE, norealUnitcode);
            c.put(KEY_UNITCODE_DUMMY, norealUnitcodeDumy);
            c.put(KEY_TYPECODE, norealTypecode);
            c.put(KEY_SUBTYPECODE, norealSubtypecode);
            c.put(KEY_ACTUAL, norealActual);
            c.put(KEY_IMAGE1, norealImage1);
            c.put(KEY_IMAGE2, norealImage2);
            c.put(KEY_CREATE_BY, norealCreateBy);
            c.put(KEY_CREATE_DATE, norealCreateDate);
            c.put(KEY_UPDATE_BY, norealUpdateBy);
            c.put(KEY_UPDATE_DATE, norealUpdateDate);
            c.put(KEY_STATUS, norealStatus);
            c.put(KEY_SERIAL_NUMBER, norealSerialNumber);
            c.put(KEY_FLAG_INPUT, norealFlagInput[0]);

            db.insert(TABLE_MESIN, KEY_ID, c);
        }else {
            String sql = "INSERT INTO " + table.toLowerCase()+"("+column+")" + " VALUES (" + result + ");";
            Log.e("sql "+table, sql);
            try {
                this.getReadableDatabase().execSQL(sql);
            }catch (Exception e){
                Log.d(e.toString()," --insert table-"+column+"-"+result);
            }
        }
    }
}
