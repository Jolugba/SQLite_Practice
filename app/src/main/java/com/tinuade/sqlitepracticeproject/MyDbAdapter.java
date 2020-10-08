package com.tinuade.sqlitepracticeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbAdapter {
    myDbHelper myHelper;

    public MyDbAdapter(Context context) {
        myHelper = new myDbHelper(context);
    }

    public long insertData(String name, String password) {
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, password);
        long id = sqLiteDatabase.insert(myDbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getData() {
        SQLiteDatabase sqLiteDatabase= myHelper.getWritableDatabase();
        String[] columns={myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD};
        Cursor cursor=sqLiteDatabase.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuilder stringBuilder =new StringBuilder();
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name=cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String password=cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
            stringBuilder.append(id+" "+name+" "+password+" \n");
        }
        return stringBuilder.toString();
    }
    public  int delete(String uname){
        SQLiteDatabase sqLiteDatabase= myHelper.getWritableDatabase();
        String[] whereArgs={uname};
        int count =sqLiteDatabase.delete(myDbHelper.TABLE_NAME,myDbHelper.NAME+" =?",whereArgs);
        return  count;
    }
    public int updateName(String oldName , String newName){
        SQLiteDatabase sqLiteDatabase= myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        String[] whereArgs={oldName};
        int count=sqLiteDatabase.update(myDbHelper.TABLE_NAME,contentValues,myDbHelper.NAME+" =?",whereArgs);
        return count;
    }

    private  class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;   // Database Version
        private static final String UID = "_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String MyPASSWORD = "Password";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255) ," + MyPASSWORD + " VARCHAR(225));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context, e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                Message.message(context, "Upgrade");
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);

            } catch (Exception e) {
                Message.message(context, e.toString());
            }
        }
    }
}
