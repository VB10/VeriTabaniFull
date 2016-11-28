package com.vb.veritabanifull;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

/**
 * Created by Vb on 28.11.2016.
 */

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //vb.db olusan veritabanın adı olacak istediğiniz isim koyabilirisiniz
        super(context, "vb.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql_komutu="CREATE TABLE Ogrenci( ID INTEGER PRIMARY KEY AUTOINCREMENT, AD TEXT UNIQE ,SOYAD TEXT )";
        sqLiteDatabase.execSQL(sql_komutu);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql_komutu2="Drop TABLE IF EXISTS Ogrenci";
        sqLiteDatabase.execSQL(sql_komutu2);
        onCreate(sqLiteDatabase);

    }

    public void add_Ogrenci (String ad,String soyad){
        //cv nesnesine gelen lemanları atayıp bunlarrı veritabanımıza yazıyoruz
        ContentValues cv = new ContentValues();
        cv.put("AD",ad);
        cv.put("SOYAD",soyad);
        this.getWritableDatabase().insertOrThrow("Ogrenci","",cv);
    }
    public void delete_Ogrenci(String ad) {
        //yine where komutu bu kez deletenin özel kullanımı
        this.getWritableDatabase().delete("Ogrenci","AD='"+ad+"'",null);

    }
    public void update_Ogrenci(String eski_ad,String yeni_ad)  {

        //where komutuyla eski adı bulup yenisiyle yer değiştriyoruz
        this.getWritableDatabase().execSQL("UPDATE Ogrenci SET AD='"+yeni_ad+"'WHERE AD='"+eski_ad+"'");

    }
    public void list_all_Ogrenci(TextView textView){
        Cursor cursor=this.getReadableDatabase().rawQuery("Select * From Ogrenci",null);
        textView.setText("");
        while (cursor.moveToNext()){ //cursor nesnemizle tüm ekli elamnları dolasıyoruz
            //ad için 1 soyad için 2
            textView.append(cursor.getString(1)+" "+cursor.getString(2)+"\n");
        }
    }
}

