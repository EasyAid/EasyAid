package com.easy.aid.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "easyaid";

    private static final String TABLE_FARMACO = "Farmaco";
    private static final String COL1_FARMACO = "IdFarmaco";
    private static final String COL2_FARMACO = "NomeFarmaco";
    private static final String COL3_FARMACO = "Confezione";
    private static final String COL4_FARMACO = "Prezzo";

    private static final String TABLE_MEDICO = "Medico";
    private static final String COL1_MEDICO = "IdMedico";
    private static final String COL2_MEDICO = "CodiceFiscale";
    private static final String COL3_MEDICO = "Password";
    private static final String COL4_MEDICO = "Nome";
    private static final String COL5_MEDICO = "Cognome";
    private static final String COL6_MEDICO = "DataNascita";
    private static final String COL7_MEDICO = "Sesso";
    private static final String COL8_MEDICO = "ProvinciaNascita";
    private static final String COL9_MEDICO = "CittaNascita";
    private static final String COL10_MEDICO = "ProvinciaStudio";
    private static final String COL11_MEDICO = "CittaStudio";
    private static final String COL12_MEDICO = "ViaStudio";
    private static final String COL13_MEDICO = "Email";
    private static final String COL14_MEDICO = "Telefono";

    private static final String TABLE_ORDINE = "Ordine";
    private static final String COL1_ORDINE = "IdOrdine";
    private static final String COL2_ORDINE = "IdFarmacia";
    private static final String COL3_ORDINE = "IdRicetta";
    private static final String COL4_ORDINE = "Pagato";
    private static final String COL5_ORDINE = "Totale";

    private static final String TABLE_RICETTA = "Ricetta";
    private static final String COL1_RICETTA = "IdRicetta";
    private static final String COL2_RICETTA = "IdMedico";
    private static final String COL3_RICETTA = "IdPaziente";
    private static final String COL4_RICETTA = "IdFarmaco";
    private static final String COL5_RICETTA = "NumeroScatole";
    private static final String COL6_RICETTA = "Descrizione";
    private static final String COL7_RICETTA = "EsenzionePatologia";
    private static final String COL8_RICETTA = "EsenzioneReddito";
    private static final String COL9_RICETTA = "StatoRichiesta";
    private static final String COL10_RICETTA = "Data";
    private static final String COL11_RICETTA = "Ora";

    private static final String TABLE_VISITA = "Visita";
    private static final String COL1_VISITA = "IdVisita";
    private static final String COL2_VISITA = "IdPaziente";
    private static final String COL3_VISITA = "IdMedico";
    private static final String COL4_VISITA = "Data";
    private static final String COL5_VISITA = "Ora";
    private static final String COL6_VISITA = "StatoRichiesta";


    private static final String TABLE_PAZIENTE = "Paziente";
    private static final String COL1_PAZIENTE = "IdPaziente";
    private static final String COL2_PAZIENTE = "Nome";
    private static final String COL3_PAZIENTE = "Cognome";
    private static final String COL4_PAZIENTE = "CodiceFiscale";
    private static final String COL5_PAZIENTE = "DataNascita";
    private static final String COL6_PAZIENTE = "Sesso";
    private static final String COL7_PAZIENTE = "ProvinciaNascita";
    private static final String COL8_PAZIENTE = "CittaNascita";
    private static final String COL9_PAZIENTE = "ProvinciaResidenza";
    private static final String COL10_PAZIENTE = "CittaResidenza";
    private static final String COL11_PAZIENTE = "ViaResidenza";
    private static final String COL12_PAZIENTE = "IdMedicoBase";
    private static final String COL13_PAZIENTE = "Password";

    private static final String CREATE_TABLE_FARMACO =

            "CREATE TABLE " + TABLE_FARMACO + "(" +
                    COL1_FARMACO + " INTEGER PRIMARY KEY," +
                    COL2_FARMACO + " VARCHAR(60)," +
                    COL3_FARMACO + " VARCHAR(70)," +
                    COL4_FARMACO + " VARCHAR(7))";

    private static final String CREATE_TABLE_MEDICO =

            "CREATE TABLE " + TABLE_MEDICO + "(" +
                    COL1_MEDICO + " INTEGER PRIMARY KEY," +
                    COL2_MEDICO + " VARCHAR(45)," +
                    COL3_MEDICO + " VARCHAR(20)," +
                    COL4_MEDICO + " VARCHAR(45)," +
                    COL5_MEDICO + " VARCHAR(45)," +
                    COL6_MEDICO + " DATE," +
                    COL7_MEDICO + " ENUM," + //todo
                    COL8_MEDICO + " VARCHAR(20)," +
                    COL9_MEDICO + " VARCHAR(20)," +
                    COL10_MEDICO + " VARCHAR(20)," +
                    COL11_MEDICO + " VARCHAR(20)," +
                    COL12_MEDICO + " VARCHAR(75)," +
                    COL13_MEDICO + " VARCHAR(30)," +
                    COL14_MEDICO + " VARCHAR(30))";

    private static final String CREATE_TABLE_ORDINE =

            "CREATE TABLE " + TABLE_ORDINE + "(" +
                    COL1_ORDINE + " INTEGER PRIMARY KEY," +
                    COL2_ORDINE + " INTEGER," +
                    COL3_ORDINE + " INTEGER," +
                    COL4_ORDINE + " TYNINT," + //TODO
                    COL5_ORDINE + " VARCHAR(5))";

    private static final String CREATE_TABLE_RICETTA =

            "CREATE TABLE " + TABLE_RICETTA + "(" +
                    COL1_RICETTA + " INTEGER PRIMARY KEY," +
                    COL2_RICETTA + " INTEGER," +
                    COL3_RICETTA + " INTEGER," +
                    COL4_RICETTA + " INTEGER," +
                    COL5_RICETTA + " INTEGER," +
                    COL6_RICETTA + " VARCHAR(200)," +
                    COL7_RICETTA + " TYINT," + //TODO
                    COL8_RICETTA + " TYNINT," +
                    COL9_RICETTA + " VARCHAR(40)," + //TODO
                    COL10_RICETTA + " DATE," +
                    COL11_RICETTA + " TIME)";

    private static final String CREATE_TABLE_VISITA =

            "CREATE TABLE " + TABLE_VISITA + "(" +
                    COL1_VISITA + " INTEGER PRIMARY KEY," +
                    COL2_VISITA + " INTEGER," +
                    COL3_VISITA + " INTEGER," +
                    COL4_VISITA + " DATE," +
                    COL5_VISITA + " TIME," +
                    COL6_VISITA + " ENUM)"; //TODO

    private static final String CREATE_TABLE_PAZIENTE =

            "CREATE TABLE " + TABLE_PAZIENTE + "(" +
                    COL1_PAZIENTE + " INTEGER PRIMARY KEY," +
                    COL2_PAZIENTE + " VARCHAR(45)," +
                    COL3_PAZIENTE + " VARCHAR(45)," +
                    COL4_PAZIENTE + " VARCHAR(45)," +
                    COL5_PAZIENTE + " DATE," +
                    COL6_PAZIENTE + " ENUM," + //TODO
                    COL7_PAZIENTE + " VARCHAR(20)," +
                    COL8_PAZIENTE + " VARCHAR(20)," +
                    COL9_PAZIENTE + " VARCHAR(20)," +
                    COL10_PAZIENTE + " VARCHAR(20)," +
                    COL11_PAZIENTE + " VARCHAR(75)," +
                    COL12_PAZIENTE + " INTEGER," +
                    COL13_PAZIENTE + " VARCHAR(32))";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FARMACO);
        db.execSQL(CREATE_TABLE_MEDICO);
        db.execSQL(CREATE_TABLE_ORDINE);
        db.execSQL(CREATE_TABLE_RICETTA);
        db.execSQL(CREATE_TABLE_VISITA);
        db.execSQL(CREATE_TABLE_PAZIENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMACO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RICETTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAZIENTE);
        onCreate(db);
    }


    public boolean addData(String table, Object i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch (table){
            case "Ricetta":{
                Ricetta item = (Ricetta) i;
                contentValues.put("IdRicetta", item.getIdRicetta());
                contentValues.put("IdMedico", item.getIdMedico());
                contentValues.put("IdPaziente", item.getIdPaziente());
                contentValues.put("IdFarmaco", item.getIdFarmaco());
                contentValues.put("NumeroScatole", item.getNumeroScatole());
                contentValues.put("Descrizione", item.getDescrizione());
                contentValues.put("EsenzionePatologia", "0");
                contentValues.put("EsenzioneReddito", "0");
                contentValues.put("StatoRichiesta", item.getStatoRichiesta());
                contentValues.put("Data", item.getData());
                contentValues.put("Ora", item.getOra());
                break;
            }

        }

        long result = db.insert(table, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor readData(String table, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = null;

        switch (table){
            case "5":{
                if(id.equals("1")){
                    query = "SELECT * FROM Ricetta WHERE StatoRichiesta='ACCETTATA'";
                }
                break;
            }
        }


        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String table, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COL1 FROM " + table +
                " WHERE COL2 = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean exist(String table, Object object){
        SQLiteDatabase db = this.getWritableDatabase();

        switch (table){

            case "Ricetta":{
                Ricetta item = (Ricetta) object;
                String query = "SELECT * FROM " + table +
                        " WHERE IdRicetta = '" + item.getIdRicetta() + "'";
                Cursor data = db.rawQuery(query, null);

                if(data != null) return true;
                else return false;

            }

        }

        return false;
    }

    public void updateName(String table, Object object){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = null;

        switch (table){

            case "Ricetta":{
                Ricetta item = (Ricetta) object;

                query = "UPDATE " + table +
                        " SET " +
                        "IdMedico= '" + item.getIdRicetta() + "', " +
                        "IdPaziente= '" + item.getIdPaziente() + "', " +
                        "IdFarmaco= '" + item.getIdFarmaco() + "', " +
                        "NumeroScatole= '" + item.getNumeroScatole() + "', " +
                        "Descrizione= '" + item.getDescrizione() + "', " +
                        "EsenzionePatologia= '0', " + //todo
                        "EsenzioneReddito= '0', " +
                        "StatoRichiesta= '" + item.getStatoRichiesta() + "', " +
                        "Data= '" + item.getData() + "', " +
                        "Ora= '" + item.getOra() + "' " +

                        "WHERE IdRicetta = '" + item.getIdRicetta() + "'";

            }

        }

        db.execSQL(query);
    }

    public void deleteName(String table, int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + table +
                " WHERE COL1 = '" +
                id + "'" +
                " AND COL2 = '" + name + "'";
        Log.d(DATABASE_NAME, "deleteName: query: " + query);
        Log.d(DATABASE_NAME, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

}
