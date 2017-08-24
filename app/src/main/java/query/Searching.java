package query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

import developer.mstudio.foodchk.MainActivity;
import helper.daneDBHelper;


public class Searching {
    private daneDBHelper dbHelper;
    public Searching(Context context) throws IOException {
        dbHelper = new daneDBHelper(context);
    }

    public Cursor getKodList(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT rowid as _id,id,barcode,sklad FROM Kod";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;

    }
    public Cursor  getKodListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT rowid as _id,id,barcode,sklad FROM Kod WHERE barcode LIKE  '%" +search + "%' ";


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }
    public Cursor getSkladList(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //nie
        String selectQuery = "SELECT rowid as _id,id,nazwa,wartosc,link FROM Skladniki";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;

    }
    public Cursor  getSkladListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery="";
        //nie
//        if(MainActivity.wyszuk==2){
//            Log.v("--------","()()()()()()");
//            selectQuery= "SELECT rowid as _id, id,nazwa,wartosc,link FROM Skladniki WHERE nazwa LIKE  '%" +MainActivity.wuszukiwanie + "%' ";
//            Log.v("========++++////", String.valueOf(MainActivity.wyszuk));
//            MainActivity.wyszuk = 0;
//            Log.v("========++++?????", String.valueOf(MainActivity.wyszuk));
//        }else
        if(MainActivity.wuszukiwanie!=""){
            search = MainActivity.wuszukiwanie;
            MainActivity.wuszukiwanie="";
        }
        if(search.contains(",")==false){
            selectQuery= "SELECT rowid as _id, id,nazwa,wartosc,link FROM Skladniki WHERE nazwa LIKE  '%" +search + "%' ";
            Log.v("test","1");
        }else{
            String [] tab= search.split(",");
            if(tab.length==1){
                selectQuery= "SELECT rowid as _id, id,nazwa,wartosc,link FROM Skladniki WHERE nazwa LIKE  '%" +tab[0]+ "%' ";
                Log.v("test","2");
            }else {
                selectQuery = "SELECT rowid as _id, id,nazwa,wartosc,link FROM Skladniki WHERE nazwa LIKE  '%" + tab[0] + "%' ";
                Log.v("test","3");
                for (int i = 1; i < tab.length; i++) {
                    String temp = tab[i].trim();
                    selectQuery += " OR nazwa LIKE '%" + temp + "%'";
                }
            }

        }
        Log.v("--------->", selectQuery);


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }


}
