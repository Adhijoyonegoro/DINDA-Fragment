package com.example.dinda.Libraries;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.dinda.Model.History;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    SQLiteDatabase db;
    ContentResolver contentResolver;
    public String[] paramTemplate;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DINDA";

    // Table Names
    public static final String TABLE_M_TEMPLATE = "M_TEMPLATE";
    public static final String TABLE_M_PROFILE = "M_PROFILE";
    public static final String TABLE_T_KPI = "T_KPI";
    public static final String TABLE_M_DATE = "M_DATE";
//    public static final String TABLE_COUNT_FLAG_INPUT = "COUNT_FLAG_INPUT";
//    public static final String TABLE_PRESS_FLAG_INPUT = "PRESS_FLAG_INPUT";
    public static final String TABLE_M_VERSION_UPDATE = "M_VERSION_UPDATE";
    public static final String TABLE_LOG_PENGIRIMAN = "LOG_PENGIRIMAN";

    // Common column names
    public static final String KEY_UOM = "UOM";

    // TABLE_M_TEMPLATE -  COLUMN NAMES
    public static final String KEY_TEMPLATE_COMPANY_OFFICE = "COMPANY_OFFICE";
    public static final String KEY_TEMPLATE_KATEGORI1 = "KATEGORI1";
    public static final String KEY_TEMPLATE_KATEGORI2 = "KATEGORI2";
    public static final String KEY_TEMPLATE_AFD = "AFD";
    public static final String KEY_TEMPLATE_POS_CODE = "POS_CODE";
    public static final String KEY_TEMPLATE_DI_ID = "DI_ID";
    public static final String KEY_TEMPLATE_UOM = "UOM";
    public static final String KEY_TEMPLATE_MAX_VALUE = "MAX_VALUE";
    public static final String KEY_TEMPLATE_TYPE_OPERATION = "TYPE_OPERATION";
    public static final String KEY_TEMPLATE_DESC_OPERATION = "DESC_OPERATION";
    public static final String KEY_TEMPLATE_DI_ID_REF = "DI_ID_REF";
    public static final String KEY_TEMPLATE_DESC_ID_REF = "DESC_ID_REF";
    public static final String KEY_TEMPLATE_URUT = "URUT";
    public static final String KEY_TEMPLATE_CONDITION = "CONDITION";

    // TABLE_M_PROFILE - column names
    public static final String KEY_PROFILE_MODUL = "MODUL";
    public static final String KEY_PROFILE_PROFILE = "PROFILE";
    public static final String KEY_PROFILE_NORMA = "NORMA";
    public static final String KEY_PROFILE_URUT = "URUT";
    public static final String KEY_PROFILE_UOM = "UOM";

    // TABLE_T_KPI -  COLUMN NAMES
    public static final String KEY_TEMPLATE_DATE = "TDATE";
    public static final String KEY_TEMPLATE_CREATEDATE = "CREATEDATE";
    public static final String KEY_TEMPLATE_VALUE = "VALUE";
    public static final String KEY_TEMPLATE_SENT = "SENT";
    // VERSION_UPDATE Table = colmn names
    public static final String KEY_VERSION = "VERSION";

    // LOG_PENGIRIMAN - column
    public static final String KEY_LOG_STATUS = "LOG_STATUS";


    // NOTE_TAGS Table - column names
//    private static final String KEY_TODO_ID = "todo_id";
//    private static final String KEY_TAG_ID = "tag_id";

    // Table Create Statements
    //  USER table create statement
    private static final String CREATE_TABLE_M_TEMPLATE = "CREATE TABLE "
            + TABLE_M_TEMPLATE + "("
            + KEY_TEMPLATE_COMPANY_OFFICE + " TEXT,"
            + KEY_TEMPLATE_KATEGORI1 + " TEXT,"
            + KEY_TEMPLATE_KATEGORI2 + " TEXT,"
            + KEY_TEMPLATE_AFD + " TEXT,"
            + KEY_TEMPLATE_POS_CODE + " TEXT,"
            + KEY_TEMPLATE_DI_ID + " TEXT,"
            + KEY_TEMPLATE_UOM + " TEXT,"
            + KEY_TEMPLATE_MAX_VALUE + " TEXT,"
            + KEY_TEMPLATE_TYPE_OPERATION + " TEXT,"
            + KEY_TEMPLATE_DESC_OPERATION + " TEXT,"
            + KEY_TEMPLATE_DI_ID_REF + " TEXT,"
            + KEY_TEMPLATE_URUT + " NUMBER,"
            + KEY_TEMPLATE_CONDITION + " TEXT,"
            + KEY_TEMPLATE_DESC_ID_REF + " TEXT"
        +")";

    private static final String CREATE_TABLE_T_KPI = "CREATE TABLE "
            + TABLE_T_KPI + "("
            + KEY_TEMPLATE_DATE + " TEXT,"
            + KEY_TEMPLATE_KATEGORI1 + " TEXT,"
            + KEY_TEMPLATE_COMPANY_OFFICE + " TEXT,"
            + KEY_TEMPLATE_AFD + " TEXT,"
            + KEY_TEMPLATE_POS_CODE + " TEXT,"
            + KEY_TEMPLATE_DI_ID + " TEXT,"
            + KEY_TEMPLATE_CREATEDATE + " TEXT,"
            + KEY_TEMPLATE_VALUE + " TEXT,"
            + KEY_TEMPLATE_SENT + " INTEGER DEFAULT 0,"
            + "  PRIMARY KEY (TDATE, COMPANY_OFFICE, KATEGORI1, AFD, POS_CODE, DI_ID)"
            +")";


    // MASTER table create statement
    private static final String CREATE_TABLE_M_PROFILE = "CREATE TABLE "
            + TABLE_M_PROFILE + "("
            + KEY_PROFILE_PROFILE + " TEXT,"
            + KEY_PROFILE_MODUL + " TEXT,"
            + KEY_PROFILE_NORMA + " TEXT,"
            + KEY_PROFILE_URUT + " TEXT,"
            + KEY_PROFILE_UOM + " TEXT"
            + ")";

    // MASTER table create statement
    private static final String CREATE_TABLE_M_DATE = "CREATE TABLE "
            + TABLE_M_DATE + "("
            + KEY_TEMPLATE_DATE + " TEXT PRIMARY KEY"
            + ")";

    private static final String ALTER_T_KPI_SENT = "ALTER TABLE "
            + TABLE_T_KPI + " ADD COLUMN " + KEY_TEMPLATE_SENT + " integer default 0;";
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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        contentResolver = context.getContentResolver();
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_M_TEMPLATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_M_PROFILE);
        Log.e("create", CREATE_TABLE_T_KPI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_T_KPI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_M_DATE);

        db.execSQL(CREATE_TABLE_M_TEMPLATE);
        db.execSQL(CREATE_TABLE_M_PROFILE);
        db.execSQL(CREATE_TABLE_T_KPI);
        db.execSQL(CREATE_TABLE_M_DATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
//            db.execSQL(ALTER_T_KPI_SENT);
        }
        if (oldVersion < 3) {
//            db.execSQL(CREATE_TABLE_M_DATE);
        }
        if (oldVersion < 5) {
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_T_KPI);
//            db.execSQL(CREATE_TABLE_T_KPI);
        }
    }

    public boolean insertTemplate(String[] param) {
        SQLiteDatabase db = getWritableDatabase();
//        onCreate(db);
        ContentValues contentValues = new ContentValues();
//        Log.i("arr:", Arrays.toString(param));
        contentValues.put(KEY_TEMPLATE_COMPANY_OFFICE, param[0]);
        contentValues.put(KEY_TEMPLATE_KATEGORI1, param[1]);
        contentValues.put(KEY_TEMPLATE_KATEGORI2, param[2]);
        contentValues.put(KEY_TEMPLATE_AFD, param[3]);
        contentValues.put(KEY_TEMPLATE_POS_CODE, param[4]);
        contentValues.put(KEY_TEMPLATE_DI_ID, param[5]);
        contentValues.put(KEY_TEMPLATE_UOM, param[6]);
        contentValues.put(KEY_TEMPLATE_MAX_VALUE, param[7]);
        contentValues.put(KEY_TEMPLATE_TYPE_OPERATION, param[8]);
        contentValues.put(KEY_TEMPLATE_DESC_OPERATION, param[9]);
        contentValues.put(KEY_TEMPLATE_DI_ID_REF, param[10]);
        contentValues.put(KEY_TEMPLATE_DESC_ID_REF, param[11]);
        contentValues.put(KEY_TEMPLATE_URUT, param[12]);
        contentValues.put(KEY_TEMPLATE_CONDITION, param[13]);
        long result = db.insert(TABLE_M_TEMPLATE, null, contentValues);
        db.close();
        return result != -1;
    }

    public Cursor getTemplate(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_M_TEMPLATE+" ORDER BY KATEGORI1, KATEGORI2, POS_CODE, DI_ID", null);
    }

    public Cursor getTemplateMenu(){
        SQLiteDatabase db = this.getReadableDatabase();
        //db.close();
        return db.rawQuery("SELECT DISTINCT COMPANY_OFFICE, KATEGORI1, AFD FROM "+TABLE_M_TEMPLATE+" ORDER BY KATEGORI1, COMPANY_OFFICE, AFD", null);

    }

    public Cursor getTemplateQuestion(String[] _args){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor _cursor;
        String[] _items= new String[_args.length-1];
        Log.e("_argslength", String.valueOf(_args.length));
        if( _args.length == 5 ) { // kalo ada afdnya
//            WHERE KATEGORI1 = ? AND A.COMPANY_OFFICE = ? AND AFD = ? AND URUT = ? AND TDATE = ?
            _items[0] = _args[0];
            _items[1] = _args[1];
            _items[2] = _args[2];
            _items[3] = _args[3];
            _cursor =  db.rawQuery("SELECT A.*, B.VALUE, B.TDATE FROM " + TABLE_M_TEMPLATE + " A LEFT JOIN T_KPI B ON A.COMPANY_OFFICE=B.COMPANY_OFFICE AND A.POS_CODE=B.POS_CODE AND A.DI_ID=B.DI_ID WHERE A.KATEGORI1 = ? AND A.COMPANY_OFFICE = ? AND A.AFD = ? AND URUT = ? AND TDATE = ?", _args);
        }
        else {
//            WHERE KATEGORI1 = ? AND A.COMPANY_OFFICE = ?  AND URUT = ? AND TDATE = ?
            _items[0] = _args[0];
            _items[1] = _args[1];
            _items[2] = _args[2];
            _cursor =  db.rawQuery("SELECT A.*, B.VALUE, B.TDATE FROM " + TABLE_M_TEMPLATE + " A LEFT JOIN T_KPI B ON A.COMPANY_OFFICE=B.COMPANY_OFFICE AND A.POS_CODE=B.POS_CODE AND A.DI_ID=B.DI_ID WHERE A.KATEGORI1 = ? AND A.COMPANY_OFFICE = ?  AND URUT = ? AND TDATE = ?", _args);
        }

        if (!_cursor.moveToFirst()) {
            if( _args.length == 5 ) { // kalo ada afdnya
                _cursor = db.rawQuery("SELECT A.*, B.VALUE, B.TDATE FROM " + TABLE_M_TEMPLATE + " A LEFT JOIN T_KPI B ON A.COMPANY_OFFICE=B.COMPANY_OFFICE AND A.POS_CODE=B.POS_CODE AND A.DI_ID=B.DI_ID WHERE A.KATEGORI1 = ? AND A.COMPANY_OFFICE = ? AND A.AFD = ? AND URUT = ? ", _items);
            }
            else {
                _cursor = db.rawQuery("SELECT A.*, B.VALUE, B.TDATE FROM " + TABLE_M_TEMPLATE + " A LEFT JOIN T_KPI B ON A.COMPANY_OFFICE=B.COMPANY_OFFICE AND A.POS_CODE=B.POS_CODE AND A.DI_ID=B.DI_ID WHERE A.KATEGORI1 = ? AND A.COMPANY_OFFICE = ? AND URUT = ? ", _items);
            }
        }
//        db.close();
        //_cursor.close();
        return _cursor;
    }

    public String getTemplateIdRef(String[] _args){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor _cursor = db.rawQuery("SELECT * FROM "+TABLE_T_KPI+" WHERE TDATE = ? AND POS_CODE=? AND DI_ID =? " , _args);
        Log.e( "idref:", DatabaseUtils.dumpCursorToString(_cursor) );
       db.close();
        if (_cursor.moveToFirst()) {
            return _cursor.getString(_cursor.getColumnIndex(KEY_TEMPLATE_VALUE));
        }
        else {
            return "OK";
        }
    }

    public Cursor getKPISyncStatus() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT DISTINCT TDATE, KATEGORI1, A.COMPANY_OFFICE, AFD, SENT " +
                " FROM "+TABLE_T_KPI+" A INNER JOIN "+ TABLE_M_TEMPLATE +" B " +
                " ORDER BY TDATE, KATEGORI1, A.COMPANY_OFFICE, AFD DESC LIMIT 7";
        Log.e( "SYNC_STATUS:", sql );
        return db.rawQuery( sql, null);
    }

    public Cursor getTemplateQuestionCount(String[] _args){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] _items= new String[_args.length];
        Log.e("_argscountlength", String.valueOf(_args.length-1));
        //db.close();
        if( _items.length == 3 ) { // kalo ada afdnya
            _items[0] = _args[0];
            _items[1] = _args[1];
            _items[2] = _args[2];
            return db.rawQuery("SELECT COUNT(*) AS ROWS FROM " + TABLE_M_TEMPLATE + " WHERE KATEGORI1 = ? AND COMPANY_OFFICE = ? AND AFD = ?", _items);
        }
        else {
            _items[0] = _args[0];
            _items[1] = _args[1];
            return db.rawQuery("SELECT COUNT(*) AS ROWS FROM " + TABLE_M_TEMPLATE + " WHERE KATEGORI1 = ? AND COMPANY_OFFICE = ?", _items);
        }
    }

    public void deleteTemplate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_M_TEMPLATE);
        db.close();
    }

    public boolean updateKPISent(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TEMPLATE_SENT, 1);
        long result = db.update( TABLE_T_KPI, contentValues, KEY_TEMPLATE_SENT+" =" + 0, null);
        db.close();
        return result != -1;
    }

    public String[] getKPIDateNotSent(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor _cursor =  db.rawQuery("SELECT DISTINCT TDATE FROM "+TABLE_T_KPI+" WHERE SENT =0 ORDER BY TDATE DESC", null);
        String _tdate="";
        String _poscode="";
        String _company ="";
        String _di_id ="";
        String _value = "";
        String _kpicode = "";

        if( _cursor.moveToFirst() ) {
            for ( _cursor.moveToFirst(); !_cursor.isAfterLast(); _cursor.moveToNext()) {
                String _strtdate = _cursor.getString(_cursor.getColumnIndex(KEY_TEMPLATE_DATE));
                _tdate += _strtdate+"^";
                Cursor _cursor_detail =  db.rawQuery("SELECT POS_CODE,COMPANY_OFFICE,DI_ID,VALUE FROM "+TABLE_T_KPI+" WHERE SENT = 0 AND TDATE = '"+_strtdate+"' ORDER BY TDATE DESC, POS_CODE, COMPANY_OFFICE, DI_ID", null);
                if( _cursor_detail.moveToFirst() ) {
                    for ( _cursor_detail.moveToFirst(); !_cursor_detail.isAfterLast(); _cursor_detail.moveToNext()) {
                        _poscode = _cursor_detail.getString(_cursor_detail.getColumnIndex(KEY_TEMPLATE_POS_CODE));
                        _company = _cursor_detail.getString(_cursor_detail.getColumnIndex(KEY_TEMPLATE_COMPANY_OFFICE));
                        _di_id = _cursor_detail.getString(_cursor_detail.getColumnIndex(KEY_TEMPLATE_DI_ID));
                        _value += _cursor_detail.getString(_cursor_detail.getColumnIndex(KEY_TEMPLATE_VALUE))+"|";
                        _kpicode += _poscode+"_"+_company+_di_id+"|";
                    }
                    _kpicode = _kpicode.substring(0, _kpicode.length() - 1);
                    _value = _value.substring(0, _value.length() - 1);
                    _kpicode +="^";
                    _value += "^";
                }
                _cursor_detail.close();
            }
        }
        _cursor.close();
        _tdate = _tdate.substring(0, _tdate.length() - 1);
        _kpicode = _kpicode.substring(0, _kpicode.length() - 1);
        _value = _value.substring(0, _value.length() - 1);
        String[] _postData = new String[3];
        _postData[0] = _tdate;
        _postData[1] = _kpicode;
        _postData[2] = _value;
        return _postData;
    }

    public Cursor getProfile(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_M_PROFILE+" ORDER BY MODUL, URUT", null);
    }

    public boolean insertKPI(String[] _args) {
        SQLiteDatabase db = getWritableDatabase();
        String[] _items= new String[4];
        _items[0] = _args[0];
        _items[1] = _args[1];
        _items[2] = _args[2];
        _items[3] = _args[3];

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TEMPLATE_DATE, _args[0]);
        contentValues.put(KEY_TEMPLATE_COMPANY_OFFICE, _args[1]);
        contentValues.put(KEY_TEMPLATE_POS_CODE, _args[2]);
        contentValues.put(KEY_TEMPLATE_DI_ID, _args[3]);
        contentValues.put(KEY_TEMPLATE_VALUE, _args[4]);
        contentValues.put(KEY_TEMPLATE_KATEGORI1, _args[5]);
        contentValues.put(KEY_TEMPLATE_AFD, _args[6]);
        contentValues.put(KEY_TEMPLATE_SENT, 0);
//        contentValues.put(KEY_TEMPLATE_CREATEDATE, _args[5]);
//
        Cursor _cursor = db.rawQuery("SELECT COUNT(*) AS ROWS FROM " + TABLE_T_KPI + " WHERE TDATE = ? AND COMPANY_OFFICE = ? AND POS_CODE = ? AND DI_ID = ?", _items);
        if (_cursor.moveToFirst()) {
            int _answerd = _cursor.getInt(_cursor.getColumnIndex("ROWS"));
            if( _answerd == 0 )
                contentValues.put(KEY_TEMPLATE_CREATEDATE, _args[7]);
        }

        long result = db.replace(TABLE_T_KPI, null, contentValues);
        db.close();
        return result != -1;
//        return true;
    }

    public boolean insertKPITanpaKegiatan(String[] _args) {
        SQLiteDatabase db = getWritableDatabase();
//        _paramAnswer[0] = tdate;
//        _paramAnswer[1] = _items[0]; // KATEGORI1
//        _paramAnswer[2] = _items[1]; // COMPANY_OFFICE
//        _paramAnswer[3] = _items[2]; // AFD
//        _paramAnswer[4] = _createdate;
        Cursor _cursor;
        Log.e( "items:", Arrays.toString(_args) );
        if( _args[3].trim().length() == 0 ) {
            String[] _items = new String[2];
            _items[0] = _args[1].trim();
            _items[1] = _args[2].trim();
            Log.e( "items_!AFD", Arrays.toString(_items) );
            _cursor = db.rawQuery("SELECT DISTINCT KATEGORI1, AFD, POS_CODE, SUBSTR( DI_ID, 1, 4 )||'000' AS DI_ID FROM " + TABLE_M_TEMPLATE + " WHERE KATEGORI1 = ? AND COMPANY_OFFICE = ?", _items);
        }
        else {
            String[] _items = new String[3];
            _items[0] = _args[1].trim();
            _items[1] = _args[2].trim();
            _items[2] = _args[3].trim();
            Log.e( "items_AFD", Arrays.toString(_items) );
            _cursor = db.rawQuery("SELECT DISTINCT KATEGORI1, AFD, POS_CODE, SUBSTR( DI_ID, 1, 4 )||'000' AS DI_ID FROM " + TABLE_M_TEMPLATE + " WHERE KATEGORI1 = ? AND COMPANY_OFFICE = ? AND SUBSTR( POS_CODE, 7, 2 ) = ?", _items);
        }
        long result=0;
        if (_cursor.moveToFirst()) {
            String _kategori1 = _cursor.getString(_cursor.getColumnIndex(KEY_TEMPLATE_KATEGORI1));
            String _afd = _cursor.getString(_cursor.getColumnIndex(KEY_TEMPLATE_AFD));
            String _pos_code = _cursor.getString(_cursor.getColumnIndex(KEY_TEMPLATE_POS_CODE));
            String _di_id = _cursor.getString(_cursor.getColumnIndex(KEY_TEMPLATE_DI_ID));

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TEMPLATE_DATE, _args[0]);
            contentValues.put(KEY_TEMPLATE_COMPANY_OFFICE, _args[2]);
            contentValues.put(KEY_TEMPLATE_POS_CODE, _pos_code);
            contentValues.put(KEY_TEMPLATE_DI_ID, _di_id);
            contentValues.put(KEY_TEMPLATE_VALUE, 0);
            contentValues.put(KEY_TEMPLATE_KATEGORI1, _kategori1);
            contentValues.put(KEY_TEMPLATE_AFD, _afd);
            contentValues.put(KEY_TEMPLATE_SENT, 0);
            result = db.replace(TABLE_T_KPI, null, contentValues);
            db.close();
        }
        else {
            db.close();
            Log.e( "AAXX:", "GEMBEL" );
        }
        return result != -1;
    }

    public int getKPIAnswerStatus(String[] _args){
        SQLiteDatabase db = this.getReadableDatabase();
//        _paramAnswer[0] = tdate;
//        _paramAnswer[1] = _items[0]; // KATEGORI1
//        _paramAnswer[2] = _items[1]; // COMPANY_OFFICE
//        _paramAnswer[3] = _items[2]; // AFD

        Cursor _cursor;
        Log.e( "items:", Arrays.toString(_args) );

        if( _args.length == 3 ) {
            String[] _items = new String[2];
            _items[0] = _args[1];
            _items[1] = _args[2];
            _cursor =  db.rawQuery("SELECT A.POS_CODE, A.DI_ID FROM " + TABLE_M_TEMPLATE + " A WHERE KATEGORI1 = ? AND A.COMPANY_OFFICE = ?", _items );
        }
        else {
            String[] _items = new String[3];
            _items[0] = _args[1];
            _items[1] = _args[2];
            _items[2] = _args[3];
            _cursor =  db.rawQuery("SELECT A.POS_CODE, A.DI_ID FROM " + TABLE_M_TEMPLATE + " A WHERE KATEGORI1 = ? AND A.COMPANY_OFFICE = ? AND AFD = ?", _items );
        }

        Log.e( "CURSOR:", DatabaseUtils.dumpCursorToString(_cursor) );
        if (_cursor.moveToFirst()) {
            String[] _poscode = new String[2];
            _poscode[0] = _cursor.getString(_cursor.getColumnIndex(KEY_TEMPLATE_POS_CODE));
            _poscode[1] = _args[0];
//            String _di_id = _cursor.getString(_cursor.getColumnIndex(KEY_TEMPLATE_DI_ID));
            Log.e( "CURSOR:", DatabaseUtils.dumpCursorToString(_cursor) );
            _cursor =  db.rawQuery("SELECT DI_ID FROM " + TABLE_T_KPI + " WHERE POS_CODE =? AND TDATE=? LIMIT 1", _poscode );
            if (_cursor.moveToFirst()) {
                String _di_id = _cursor.getString(_cursor.getColumnIndex(KEY_TEMPLATE_DI_ID)).substring(4,7);
                Log.e( "DIID000:", _di_id );
                if( _di_id.equals ( "000" ))
                    return 2;
                else
                    return 1;
            }
            else
                return 0;
        }
        else
            return 0;
    }

    public boolean insertProfile(String[] param) {
        SQLiteDatabase db = getWritableDatabase();
//        onCreate(db);
        ContentValues contentValues = new ContentValues();
//        Log.i("arr:", Arrays.toString(param));
        contentValues.put(KEY_PROFILE_PROFILE, param[0]);
        contentValues.put(KEY_PROFILE_MODUL, param[1]);
        contentValues.put(KEY_PROFILE_NORMA, param[2]);
        contentValues.put(KEY_PROFILE_UOM, param[3]);
        contentValues.put(KEY_PROFILE_URUT, param[4]);
        long result = db.insert(TABLE_M_PROFILE, null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean insertDate(String param) {
        SQLiteDatabase db = getWritableDatabase();
//        onCreate(db);
        ContentValues contentValues = new ContentValues();
//        Log.i("arr:", Arrays.toString(param));
        contentValues.put(KEY_TEMPLATE_DATE, param);
        long result = db.replace(TABLE_M_DATE, null, contentValues);
        db.close();
        return result != -1;
    }

    public void deleteProfile(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_M_PROFILE);
        db.close();
    }



//    public boolean checkUserPassword(String empCode){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT COUNT("+KEY_PASS+") FROM "+TABLE_USER+" where "+KEY_EMP_CODE+" ='"+ empCode +"'", null);
//        cursor.moveToFirst();
//        int icount = cursor.getInt(0);
//        return icount > 0;
//    }

//    public boolean regisPass(String nik, String password, String photo_path){
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(KEY_PASS, password);
//        contentValues.put(KEY_PHOTO_PATH, photo_path);
//        long result = db.update(TABLE_USER, contentValues, KEY_EMP_CODE+" =" + nik, null);
//        db.close();
//        return result != -1;
//    }

//    public boolean regisPass(String nik, String password){
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(KEY_PASS, password);
////        contentValues.put(KEY_PHOTO_PATH, photo_path);
//        long result = db.update(TABLE_USER, contentValues, KEY_EMP_CODE+" = '"+ nik +"'", null);
//        db.close();
//        return result != -1;
//    }
//
//    public boolean updateFoto(String nik, String photo_path){
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
////        contentValues.put(KEY_PASS, password);
//        contentValues.put(KEY_PHOTO_PATH, photo_path);
//        long result = db.update(TABLE_USER, contentValues, KEY_EMP_CODE+" = '"+ nik +"'", null);
//        db.close();
//        return result != -1;
//    }
//
//    public boolean updatePassword(String empcode, String password) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(KEY_PASS, password);
//        long result = db.update(TABLE_USER, contentValues, KEY_EMP_CODE+" = '" +empcode+"'", null);
//        db.close();
//        return result != -1;
//    }

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
//    public boolean insertVersion(String version){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
//        c.put(KEY_VERSION, version);
//
//        long result = db.insert(TABLE_VERSION_UPDATE, KEY_ID, c);
//        return result != -1;
//    }
//
//    //method insert
//    public boolean insertLogPengiriman(String log, String tdate){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
//        c.put(KEY_LOG_STATUS, log);
//        c.put(KEY_CREATED_AT, tdate);
//
//        long result = db.insert(TABLE_LOG_PENGIRIMAN, KEY_ID, c);
//        return result != -1;
//    }
//
//    //method insert master
//    public boolean insertMaster(String companyId, String unitcode, String unitname, String station,
//                                String unit, String typecode, String stdParam, String upperLimit,
//                                String lowerLimit, String uom, String nom, String active, String qrCode,
//                                String millcode) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
//        c.put(KEY_COMPANY_ID, companyId);
//        c.put(KEY_UNITCODE, unitcode);
//        c.put(KEY_UNITNAME, unitname);
//        c.put(KEY_STATION, station);
//        c.put(KEY_UNIT, unit);
//        c.put(KEY_TYPECODE, typecode);
//        c.put(KEY_STD_PARAM, stdParam);
//        c.put(KEY_UPPER_LIMIT, upperLimit);
//        c.put(KEY_LOWER_LIMIT, lowerLimit);
//        c.put(KEY_UOM, uom);
//        c.put(KEY_NOM, nom);
//        c.put(KEY_ACTIVE, active);
//        c.put(KEY_QR_CODE, qrCode);
//        c.put(KEY_MILLCODE, millcode);
//
//        long result = db.insert(TABLE_MASTER, KEY_ID, c);
//        return result != -1;
//    }
//
//    //method insert mesin
//    /*untuk status :    0 success ke server
//    *                   1 gagal ke server
//    *                   2 siap untuk disimpan ke server */
//    public boolean insertMesin(String companyId, String millcode, String tdate, String shift,
//                               String hours, String station, String unit, String unitcode, String unitcodeDummy,
//                               String typecode, String subtypecode, double actual, String image1,
//                               String image2, String createBy, String createDate, String updateBy,
//                               String updateDate, int status, String serialNumber, String flagInput) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
//        c.put(KEY_COMPANY_ID, companyId);
//        c.put(KEY_MILLCODE, millcode);
//        c.put(KEY_CREATED_AT, tdate);
//        c.put(KEY_SHIFT, shift);
//        c.put(KEY_HOURS, hours);
//        c.put(KEY_STATION, station);
//        c.put(KEY_UNIT, unit);
//        c.put(KEY_UNITCODE, unitcode);
//        c.put(KEY_UNITCODE_DUMMY, unitcodeDummy);
//        c.put(KEY_TYPECODE, typecode);
//        c.put(KEY_SUBTYPECODE, subtypecode);
//        c.put(KEY_ACTUAL, actual);
//        c.put(KEY_IMAGE1, image1);
//        c.put(KEY_IMAGE2, image2);
//        c.put(KEY_CREATE_BY, createBy);
//        c.put(KEY_CREATE_DATE, createDate);
//        c.put(KEY_UPDATE_BY, updateBy);
//        c.put(KEY_UPDATE_DATE, updateDate);
//        c.put(KEY_STATUS, status);
//        c.put(KEY_SERIAL_NUMBER, serialNumber);
//        c.put(KEY_FLAG_INPUT, flagInput);
//
//        long result = db.insert(TABLE_MESIN, KEY_ID, c);
//        return result != -1;
//    }

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
//    public boolean updatetMaster(String id, String companyId, String unitcode, String unitname, String station,
//                                String unit, String typecode, String stdParam, String upperLimit,
//                                String lowerLimit, String uom, String nom, String active, String qrCode,
//                                String millcode) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
//        c.put(KEY_ID, id);
//        c.put(KEY_COMPANY_ID, companyId);
//        c.put(KEY_UNITCODE, unitcode);
//        c.put(KEY_UNITNAME, unitname);
//        c.put(KEY_STATION, station);
//        c.put(KEY_UNIT, unit);
//        c.put(KEY_TYPECODE, typecode);
//        c.put(KEY_STD_PARAM, stdParam);
//        c.put(KEY_UPPER_LIMIT, upperLimit);
//        c.put(KEY_LOWER_LIMIT, lowerLimit);
//        c.put(KEY_UOM, nom);
//        c.put(KEY_NOM, nom);
//        c.put(KEY_ACTIVE, active);
//        c.put(KEY_QR_CODE, qrCode);
//        c.put(KEY_MILLCODE, millcode);
//
//        long result = db.update(TABLE_MASTER, c, "ID = ?", new String[]{id});
//        return result != -1;
//    }

    //method update mesin
//    public boolean updateMesin(String id, String companyId, String millcode, String tdate, String shift,
//                               String hours, String station, String unit, String unitcode,
//                               String typecode, String subtypecode, String actual, byte[] image1,
//                               byte[] image2) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
//        c.put(KEY_ID, id);
//        c.put(KEY_COMPANY_ID, companyId);
//        c.put(KEY_MILLCODE, millcode);
//        c.put(KEY_CREATED_AT, tdate);
//        c.put(KEY_SHIFT, shift);
//        c.put(KEY_HOURS, hours);
//        c.put(KEY_STATION, station);
//        c.put(KEY_UNIT, unit);
//        c.put(KEY_UNITCODE, unitcode);
//        c.put(KEY_TYPECODE, typecode);
//        c.put(KEY_SUBTYPECODE, subtypecode);
//        c.put(KEY_ACTUAL, actual);
//        c.put(KEY_IMAGE1, image1);
//        c.put(KEY_IMAGE2, image2);
//
//        long result = db.update(TABLE_MESIN, c, "ID = ?", new String[]{id});
//        return result != -1;
//    }

//    public boolean updateHour(String id, String hours) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
////        c.put(KEY_ID, id);
////        c.put(KEY_COMPANY_ID, companyId);
////        c.put(KEY_MILLCODE, millcode);
////        c.put(KEY_CREATED_AT, tdate);
////        c.put(KEY_SHIFT, shift);
//        c.put(KEY_HOURS, hours);
////        c.put(KEY_STATION, station);
////        c.put(KEY_UNIT, unit);
////        c.put(KEY_UNITCODE, unitcode);
////        c.put(KEY_TYPECODE, typecode);
////        c.put(KEY_SUBTYPECODE, subtypecode);
////        c.put(KEY_ACTUAL, actual);
////        c.put(KEY_IMAGE1, image1);
////        c.put(KEY_IMAGE2, image2);
//
//        long result = db.update(TABLE_MESIN, c, "ID = ?", new String[]{id});
//        return result != -1;
//    }

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
//
//    public List<String> getAllStation(String millcode){
//        List<String> labels = new ArrayList<String>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {KEY_STATION};
//        Cursor cursor = db.query(true, TABLE_MASTER, columns, "MILLCODE=?", new String[] { millcode }, null, null, KEY_STATION +" ASC", null);
//
//        labels.add(0, "PILIH STATION");
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()){
//            do {
//                labels.add(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//
//        // closing connection
//        cursor.close();
//        db.close();
//
//        //returning labels
//        return labels;
//    }
//
//    public List<String> getAllUnitcode(String millcode, String station){
//        List<String> labels = new ArrayList<String>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {KEY_UNITCODE};
//        Cursor cursor = db.query(TABLE_MASTER, columns, "MILLCODE=? AND STATION=?", new String[] {millcode, station }, null, null, KEY_UNITCODE +" ASC");
//        labels.add(0, "PILIH UNITCODE");
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()){
//            do {
//                labels.add(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//
//        // closing connection
//        cursor.close();
//        db.close();
//
//        //returning labels
//        return labels;
//    }
//
//    public List<String> getVrsion(){
//        List<String> labels = new ArrayList<String>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {KEY_VERSION};
//        Cursor cursor = db.query(TABLE_VERSION_UPDATE, columns, null, null, null, null, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()){
//            do {
//                labels.add(cursor.getString(1));
//            } while (cursor.moveToNext());
//        }
//
//        // closing connection
//        cursor.close();
//        db.close();
//
//        //returning labels
//        return labels;
//    }
//
//    public List<String> getFilterData(String station, String unitcode, String millcode){
//        List<String> labels = new ArrayList<String>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {KEY_UNITCODE};
//        Cursor cursor = db.query(TABLE_MASTER, columns, "STATION=? AND UNITCODE=? AND MILLCODE=?", new String[] { station, unitcode, millcode }, null, null, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()){
//            do {
//                labels.add(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//
//        // closing connection
//        cursor.close();
//        db.close();
//
//        //returning labels
//        return labels;
//    }
//
//    public void deleteTable(String table) {
//        String sql = "DELETE FROM " + table.toLowerCase();
//        this.getReadableDatabase().execSQL(sql);
//    }
//
//    public boolean updateStatus(int id, int status) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
////        c.put(KEY_ID_AKUN, idAkun);
////        c.put(KEY_NM_KAT, nmKat);
////        c.put(KEY_NM_MENU, nmMenu);
//        c.put(KEY_STATUS, status);
////        c.put(KEY_NM_SATUAN, nmSatuan);
////        c.put(KEY_KET_LIST_PESANAN, ketListPesanan);
////        c.put(KEY_STATUS_DISKON, statusDiskon);
////        c.put(KEY_STATUS_MASAK, statusMasak);
//
//        long result = db.update(TABLE_MESIN, c, KEY_ID+" = ?",
//                new String[] {String.valueOf(id)});
//        return result != -1;
//    }
//
//    public boolean updateStatusLaporan(String station, String unitcode,String createDate, String hours, int status) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
////        c.put(KEY_ID_AKUN, idAkun);
////        c.put(KEY_NM_KAT, nmKat);
////        c.put(KEY_NM_MENU, nmMenu);
//        c.put(KEY_STATUS, status);
////        c.put(KEY_NM_SATUAN, nmSatuan);
////        c.put(KEY_KET_LIST_PESANAN, ketListPesanan);
////        c.put(KEY_STATUS_DISKON, statusDiskon);
////        c.put(KEY_STATUS_MASAK, statusMasak);
//
//        long result = db.update(TABLE_MESIN, c, KEY_STATION+" = ? AND "+KEY_UNITCODE+" = ? AND "+KEY_CREATE_DATE+" = ? AND "+KEY_HOURS+" = ?",
//                new String[] {station, unitcode, createDate, hours});
//        return result != -1;
//    }
//
//    public void insertTable(String table, String column, String result){
//        Log.e("table", table.toUpperCase());
//        Log.e("column", column);
//        Log.e("data", result);
//        if (table.equals(TABLE_MESIN)) {
//            //data
//            String biasa[] = result.split("\",\"");
//            String norealId = biasa[0];
//            String norealCompanyId = biasa[1];
//            String norealMillcode = biasa[2];
//            String norealCreatedAt = biasa[3];
//            String norealShift = biasa[4];
//            String norealHours = biasa[5];
//            String norealStation = biasa[6];
//            String norealUnit = biasa[7];
//            String norealUnitcode = biasa[8];
//            String norealUnitcodeDumy = biasa[9];
//            String norealTypecode = biasa[10];
//            String norealSubtypecode = biasa[11];
//            String norealActual = biasa[12];
//            String norealImage1 = biasa[13];
//            String norealImage2 = biasa[14];
//            String norealCreateBy = biasa[15];
//            String norealCreateDate = biasa[16];
//            String norealUpdateBy = biasa[17];
//            String norealUpdateDate = biasa[18];
//            String norealStatus = biasa[19];
//            String norealSerialNumber = biasa[20];
//            String norealFlagInput[] = biasa[21].split("\"");
//
////            Log.e("flag", norealFlagInput[0]);
//
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues c = new ContentValues();
//            c.put(KEY_COMPANY_ID, norealCompanyId);
//            c.put(KEY_MILLCODE, norealMillcode);
//            c.put(KEY_CREATED_AT, norealCreatedAt);
//            c.put(KEY_SHIFT, norealShift);
//            c.put(KEY_HOURS, norealHours);
//            c.put(KEY_STATION, norealStation);
//            c.put(KEY_UNIT, norealUnit);
//            c.put(KEY_UNITCODE, norealUnitcode);
//            c.put(KEY_UNITCODE_DUMMY, norealUnitcodeDumy);
//            c.put(KEY_TYPECODE, norealTypecode);
//            c.put(KEY_SUBTYPECODE, norealSubtypecode);
//            c.put(KEY_ACTUAL, norealActual);
//            c.put(KEY_IMAGE1, norealImage1);
//            c.put(KEY_IMAGE2, norealImage2);
//            c.put(KEY_CREATE_BY, norealCreateBy);
//            c.put(KEY_CREATE_DATE, norealCreateDate);
//            c.put(KEY_UPDATE_BY, norealUpdateBy);
//            c.put(KEY_UPDATE_DATE, norealUpdateDate);
//            c.put(KEY_STATUS, norealStatus);
//            c.put(KEY_SERIAL_NUMBER, norealSerialNumber);
//            c.put(KEY_FLAG_INPUT, norealFlagInput[0]);
//
//            db.insert(TABLE_MESIN, KEY_ID, c);
//        }else {
//            String sql = "INSERT INTO " + table.toLowerCase()+"("+column+")" + " VALUES (" + result + ");";
//            Log.e("sql "+table, sql);
//            try {
//                this.getReadableDatabase().execSQL(sql);
//            }catch (Exception e){
//                Log.d(e.toString()," --insert table-"+column+"-"+result);
//            }
//        }
//    }
}
