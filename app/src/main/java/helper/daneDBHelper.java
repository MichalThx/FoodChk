package helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import szablon.Kod;
import szablon.Sklad;

/**
 * Created by MS on 21.07.2016.
 */
public class daneDBHelper extends SQLiteOpenHelper{
    private static String DB_PATH = "";
    private static String DB_NAME = "dany.db";
    private static String TABLE_KOD= "Kod";
    private static String TABLE_SKLAD= "Skladniki";
    //  private static final String SP_KEY_DB_VER = "db_ver";
    private static int CurrentVersion =3;
    //private static int OldVersion;

    private final Context context;
    private SQLiteDatabase db;

    public daneDBHelper(Context context) {
        super(context, DB_NAME, null, CurrentVersion);

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.context = context;
    }

    public void create() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            dbUpdate();
            //do nothing - database already exist
        } else {
            // By calling this method and empty database will be created into the default system path
            // of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
    private void dbUpdate() throws IOException {
        String path = DB_PATH + DB_NAME;

        SQLiteDatabase upd = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        int ver = upd.getVersion();
        if(ver!= CurrentVersion){
            upd.execSQL("DROP TABLE " + TABLE_KOD);
            upd.execSQL("DROP TABLE "+ TABLE_SKLAD);
            copyDataBase();
        }
    }
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database don't exist yet.
            e.printStackTrace();
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    public boolean open() {
        try {
            String myPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            return true;
        } catch (SQLException sqle) {
            db = null;
            return false;
        }
    }
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public List<Kod> getKods() {
        List<Kod> kods = new ArrayList<>();

        try {
            //get all rows from Department table
            String query = "SELECT * FROM " + TABLE_KOD;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                int kodID = Integer.parseInt(cursor.getString(0));
                Log.v("id---", String.valueOf(kodID));
                int kodBAR = Integer.parseInt(cursor.getString(1));
                String kodSKL = cursor.getString(2);
               // Log.v("===", String.valueOf(kodID));
                Kod kod = new Kod(kodID, kodBAR, kodSKL);

                kods.add(kod);
            }
        } catch(Exception e) {
            Log.d("DB", e.getMessage());
        }
        return kods;
    }
    public List<Sklad> getSklads() {
        List<Sklad> sklads= new ArrayList<>();

        try {
            //get all rows from Employee table
            String query = "SELECT * FROM " + TABLE_SKLAD;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
           Cursor e = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
          //  Cursor c = db.rawQuery("PRAGMA table_info(table_name)",null);
            Log.v( "Table Name=> ","-");

            if (e.moveToFirst()) {
                while ( !e.isAfterLast() ) {
                    Log.v( "Table Name=> "+e.getString(0),"-");

                    e.moveToNext();
                }
            }


            Cursor c = db.rawQuery("SELECT * FROM Kod WHERE 0", null);
            try {
                String[] columnNames = c.getColumnNames();
                for(String w:columnNames){
                    Log.v("TABELA 12 =>", w);
                }
            } finally {
                c.close();
            }


            Cursor cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                int sklID = Integer.parseInt(cursor.getString(0));
                String sklNAZ = cursor.getString(1);
                int sklWAR = Integer.parseInt(cursor.getString(2));
                String sklLIN = cursor.getString(3);

                //Get Phone Numbers associated to this employee

                Sklad sklad = new Sklad(sklID, sklNAZ, sklWAR, sklLIN);

                sklads.add(sklad);
            }
        } catch(Exception e) {
            Log.d("DB", e.getMessage());
        }

        return sklads;
    }
}

