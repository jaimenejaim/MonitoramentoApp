package br.com.crudjaimenejaim.app.monitoramentoapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



/**
 * Created by jaimenejaim on 17/07/17.
 */


public class DataBaseHelper {
    private static SQLiteDatabase sampleDB = null;
    private static final String TABELA_MONITORAMENTO = "MONITORAMENTO";

    public DataBaseHelper(Context context) {
        createDataBase(context);
    }


    public void createDataBase(Context context) {
        sampleDB = context.openOrCreateDatabase("TestHomeKitDB.db", Context.MODE_PRIVATE, null);


        //Tabela monitoramento
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABELA_MONITORAMENTO +
                "( idPlace INTEGER PRIMARY KEY AUTOINCREMENT," +
                " placeName TEXT," +
                " type TEXT);");

//        //Table Movies
//        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + "ACCESSORY" +
//                " (idAccessory INTEGER PRIMARY KEY AUTOINCREMENT," +
//                " description TEXT," +
//                " ipAddress TEXT," +
//                " username TEXT, " +
//                " password TEXT, " +
//                " idPlace INTEGER," +
//                " FOREIGN KEY(idPlace) REFERENCES PLACE(idPlace));");
    }


    public void executeSql(String sqlQuery) {
        sampleDB.execSQL(sqlQuery);
    }

    public Cursor rawQuery(String sqlQuery) {
        return sampleDB.rawQuery(sqlQuery, null);
    }
}
