package br.com.crudjaimenejaim.app.monitoramentoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.crudjaimenejaim.app.monitoramentoapp.model.Monitoramento;


/**
 * Created by jaimenejaim on 17/07/17.
 */


public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    // Database Name
    private static final String DATABASE_NAME = "monitoramentoDB";



    private static final String TABLE_NAME_MONITORAMENTO = "Monitoramento";
    private static final String ID_TABLE_MONITORAMENTO = "id";
    private static final String NAME_TABLE_MONITORAMENTO = "name";
    private static final String CPF_TABLE_MONITORAMENTO = "cpf";
    private static final String PHONE_TABLE_MONITORAMENTO = "phone";
    private static final String MAIL_TABLE_MONITORAMENTO = "mail";
    private static final String CREATED_AT_TABLE_MONITORAMENTO = "created_at";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MODEL_TABLE_MONITORAMENTO = "CREATE TABLE " + TABLE_NAME_MONITORAMENTO + " ("
                + ID_TABLE_MONITORAMENTO + " INTEGER PRIMARY KEY, "
                + NAME_TABLE_MONITORAMENTO + " TEXT, "
                + CPF_TABLE_MONITORAMENTO + " TEXT, "
                + PHONE_TABLE_MONITORAMENTO + " TEXT, "
                + MAIL_TABLE_MONITORAMENTO + " TEXT, "
                + CREATED_AT_TABLE_MONITORAMENTO + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(CREATE_MODEL_TABLE_MONITORAMENTO);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_MONITORAMENTO);
        onCreate(db);
    }


    public void addMonitoramento(Monitoramento monitoramento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_TABLE_MONITORAMENTO, monitoramento.getName());
        values.put(CPF_TABLE_MONITORAMENTO, monitoramento.getCpf());
        values.put(PHONE_TABLE_MONITORAMENTO, monitoramento.getPhone());
        values.put(MAIL_TABLE_MONITORAMENTO, monitoramento.getMail());

        // Inserting Row
        db.insert(TABLE_NAME_MONITORAMENTO, null, values);
        db.close();
    }

    public Monitoramento getLastMonitoramentoAdded(){

        return new Monitoramento();
    }

    public void updateMonitoramento(Monitoramento monitoramento){
        String query  = "UPDATE " + TABLE_NAME_MONITORAMENTO +
                " SET "+NAME_TABLE_MONITORAMENTO+" = '" + monitoramento.getName() +
                "', " + CPF_TABLE_MONITORAMENTO + " = '" + monitoramento.getCpf() +
                "', " + PHONE_TABLE_MONITORAMENTO + " = '" + monitoramento.getPhone() +
                "', " + MAIL_TABLE_MONITORAMENTO + " = '" + monitoramento.getMail() +
                "' WHERE "+ ID_TABLE_MONITORAMENTO +" = " + monitoramento.getId();

        Log.i("updateMonitoramento",query);

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void removeMonitoramento(Monitoramento monitoramento){
        SQLiteDatabase db = this.getWritableDatabase();
        String query  = "DELETE FROM " + TABLE_NAME_MONITORAMENTO + " WHERE "+ ID_TABLE_MONITORAMENTO +" = " + monitoramento.getId();

        db.execSQL(query);
        db.close();
    }

    public List<Monitoramento> listMonitoramento(){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Monitoramento> monitoramentos = new ArrayList<Monitoramento>();

        String query = "SELECT  * FROM " + TABLE_NAME_MONITORAMENTO + " ORDER BY " + CREATED_AT_TABLE_MONITORAMENTO +" DESC";


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Monitoramento monitoramento = new Monitoramento();
                monitoramento.setId(Integer.parseInt(cursor.getString(0)));
                monitoramento.setName(cursor.getString(1));
                monitoramento.setCpf(cursor.getString(2));
                monitoramento.setPhone(cursor.getString(3));
                monitoramento.setMail(cursor.getString(4));
                monitoramento.setCreated_at(cursor.getString(5));
                monitoramentos.add(monitoramento);
            } while (cursor.moveToNext());
        }
        return monitoramentos;

    }



    public Monitoramento getLastAddedMonitoramento(){


        String selectQuery = "SELECT "+ID_TABLE_MONITORAMENTO+","
                +NAME_TABLE_MONITORAMENTO+","
                +CPF_TABLE_MONITORAMENTO+","
                +PHONE_TABLE_MONITORAMENTO+","
                +MAIL_TABLE_MONITORAMENTO+","
                +CREATED_AT_TABLE_MONITORAMENTO+
                " FROM "+TABLE_NAME_MONITORAMENTO+" ORDER BY "+ID_TABLE_MONITORAMENTO+" DESC LIMIT 1";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Monitoramento monitoramento = null;

        if (cursor.moveToFirst()) {
            monitoramento  = new Monitoramento();
            monitoramento.setId(Integer.parseInt(cursor.getString(0)));
            monitoramento.setName(cursor.getString(1));
            monitoramento.setCpf(cursor.getString(2));
            monitoramento.setPhone(cursor.getString(3));
            monitoramento.setMail(cursor.getString(4));
            monitoramento.setCreated_at(cursor.getString(5));


        }
        return monitoramento;
    }


    public Monitoramento getMonitoramentoByID(int id){


        String query = "SELECT "+ID_TABLE_MONITORAMENTO+","
                +NAME_TABLE_MONITORAMENTO+","
                +CPF_TABLE_MONITORAMENTO+","
                +PHONE_TABLE_MONITORAMENTO+","
                +MAIL_TABLE_MONITORAMENTO+","
                +CREATED_AT_TABLE_MONITORAMENTO+
                " FROM "+TABLE_NAME_MONITORAMENTO+" WHERE "+ID_TABLE_MONITORAMENTO+" = "+ id;
        Log.i("query_getByID",""+query);


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Monitoramento monitoramento = null;

        if (cursor.moveToFirst()) {
            monitoramento  = new Monitoramento();
            monitoramento.setId(Integer.parseInt(cursor.getString(0)));
            monitoramento.setName(cursor.getString(1));
            monitoramento.setCpf(cursor.getString(2));
            monitoramento.setPhone(cursor.getString(3));
            monitoramento.setMail(cursor.getString(4));
            monitoramento.setCreated_at(cursor.getString(5));


        }
        return monitoramento;
    }




//    public void setSelectedPositionHomePlace(int place_id){
//        String updateQuery  = "UPDATE " + TABLE_NAME_POSITION_PLACE + " SET "+ID_TABLE_PLACE+" = " + place_id;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(updateQuery);
//        db.close();
//
//
//    }
//
//    public void addRoomInOtherPlace(Room room){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(DESCRIPTION_TABLE_ROOM, room.getDescription());
//        values.put(TYPE_TABLE_ROOM, room.getType().toString());
//        values.put(ID_TABLE_PLACE, getOtherPlaceId());
//
//        // Inserting Row
//        db.insert(TABLE_NAME_ROOM, null, values);
//        db.close(); // Closing database connection
//
//    }
//
//
//    public void addRoomInPlace(Room room){
//
//
//        String updateQuery  = "UPDATE " + TABLE_NAME_ROOM + " SET "+ID_TABLE_PLACE+" = "+room.getPlace().getId()+" WHERE "+ ID_TABLE_ROOM +" = " + room.getId();
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(updateQuery);
//        db.close();
//
//    }
//
//    public void removeRoomFromPlace(Room room){
//
//    }
//
//    public List<Room> getAllRoom(){
//        ArrayList<Room> modelList = new ArrayList<Room>();
//
//        String selectQuery = "SELECT  * FROM " + TABLE_NAME_ROOM;
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Room model = new Room();
//                model.setId(Integer.parseInt(cursor.getString(0)));
//                model.setDescription(cursor.getString(1));
//                model.setType(RoomType.valueOf(cursor.getString(2)));
//                model.getPlace().setId(cursor.getInt(3));
//                modelList.add(model);
//            } while (cursor.moveToNext());
//        }
//        return modelList;
//    }
//
//
//
//    public List<Room> getAllRoomByPlaceID(int placeID){
//
//        ArrayList<Room> modelList = new ArrayList<Room>();
//
//        String selectQuery = "SELECT  * FROM " + TABLE_NAME_ROOM + " WHERE " + ID_TABLE_PLACE + " = " + placeID;
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Room model = new Room();
//                model.setId(Integer.parseInt(cursor.getString(0)));
//                model.setDescription(cursor.getString(1));
//                model.setType(RoomType.valueOf(cursor.getString(2)));
//                model.getPlace().setId(cursor.getInt(3));
//                modelList.add(model);
//            } while (cursor.moveToNext());
//        }
//        return modelList;
//    }
//
//
//    public Room getRoomByAccessoryID(int id){
//        Room retorno = null;
//
//        String selectQuery = "SELECT"+ TABLE_NAME_ROOM + "." + ID_TABLE_ROOM + ","
//                                     + TABLE_NAME_ROOM + "." + DESCRIPTION_TABLE_ROOM + ","
//                                     + TABLE_NAME_ROOM + "." + TYPE_TABLE_ROOM + " " +
//                "FROM " + TABLE_NAME_ROOM + ", " + TABLE_NAME_ACCESSORY +
//                "WHERE "+ TABLE_NAME_ACCESSORY+"."+ID_TABLE_ROOM + " = " + TABLE_NAME_ROOM + "." + ID_TABLE_ROOM + " AND "+ TABLE_NAME_ACCESSORY + "." + ID_TABLE_ACCESSORY + " = " + id;
//
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Room place  = new Room();
//                place.setId(Integer.parseInt(cursor.getString(0)));
//                place.setDescription(cursor.getString(1));
//                place.setType(RoomType.valueOf(cursor.getString(2)));
//
//            } while (cursor.moveToNext());
//        }
//        return retorno;
//    }
//
//
//    public Room getRoom(int id){
//        Room retorno = null;
//
//        String selectQuery = "SELECT "+ID_TABLE_ROOM+","+DESCRIPTION_TABLE_ROOM+","+TYPE_TABLE_ROOM+" FROM " + TABLE_NAME_ROOM + " WHERE " + ID_TABLE_ROOM + " = " + id;
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Room place  = new Room();
//                place.setId(Integer.parseInt(cursor.getString(0)));
//                place.setDescription(cursor.getString(1));
//                place.setType(RoomType.valueOf(cursor.getString(2)));
//
//            } while (cursor.moveToNext());
//        }
//        return retorno;
//    }
//
//
//
//
//    public void addNewSelectedPositionHomePLace(int id){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(ID_TABLE_PLACE, id);
//
//        // Inserting Row
//        db.insert(TABLE_NAME_POSITION_PLACE, null, values);
//        db.close(); // Closing database connection
//
//    }
//
//
//    public int  getSelectedPositionHomePLace(){
//        int retorno = 0;
//
//        String selectQuery = "SELECT "+ID_TABLE_PLACE+" FROM " + TABLE_NAME_POSITION_PLACE +  " ASC LIMIT 1";
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                retorno = Integer.parseInt(cursor.getString(0));
//
//            } while (cursor.moveToNext());
//        }
//        return retorno;
//    }
//
//
//    public Place getPlace(int id){
//        Place retorno = null;
//
//        String selectQuery = "SELECT "+ID_TABLE_PLACE+","+NAME_TABLE_PLACE+","+TYPE_TABLE_PLACE+" FROM " + TABLE_NAME_PLACE + " WHERE " + ID_TABLE_PLACE + " = " + id;
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Place place  = new Place();
//                place.setId(Integer.parseInt(cursor.getString(0)));
//                place.setName(cursor.getString(1));
//                place.setType(PlaceType.valueOf(cursor.getString(2)));
//
//            } while (cursor.moveToNext());
//        }
//        return retorno;
//    }
//
//
//    public ArrayList<Monitoramento> getAllAccessoryWithoutPlace(){
//
//        ArrayList<Monitoramento> modelList = new ArrayList<Monitoramento>();
//
//        String selectQuery = "SELECT  * FROM " + TABLE_NAME_ACCESSORY + " WHERE " + ID_TABLE_PLACE + " = "+getOtherPlaceId();
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Monitoramento model = new Monitoramento();
//                model.setId(Integer.parseInt(cursor.getString(0)));
//                model.setDescription(cursor.getString(1));
//
//                List<Integer> ip = new ArrayList<>();
//                ip.add(cursor.getInt(2));
//                ip.add(cursor.getInt(3));
//                ip.add(cursor.getInt(4));
//                ip.add(cursor.getInt(5));
//
//                model.setIpAdress((ArrayList<Integer>) ip);
//
//                model.setUsername(cursor.getString(6));
//                model.setPassword(cursor.getString(7));
//                model.setSerieNumber(cursor.getString(8));
//                model.setType(AccessoryType.valueOf(cursor.getString(9)));
//                model.getRoom().setId(cursor.getInt(10));
//
//
//
//                // Adding contact to list
//                modelList.add(model);
//            } while (cursor.moveToNext());
//        }
//        return modelList;
//    }
//
//
//
//
//    public void removeAccessoryFromPlaceToOtherPlace(int id_current_place){
//
//        String updateQuery  = "UPDATE " + TABLE_NAME_ACCESSORY + " SET "+ID_TABLE_PLACE+" = "+getOtherPlaceId()+" WHERE "+ ID_TABLE_ACCESSORY +" = " + id_current_place;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(updateQuery);
//        db.close();
//
//    }
//
//
//
//    public void removePlace(int id){
//        String deleteQuery  = "DELETE FROM " + TABLE_NAME_PLACE + " WHERE "+ ID_TABLE_PLACE +" = " + id;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(deleteQuery);
//        db.close();
//
//    }

}
