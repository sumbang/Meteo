package biz.glieunou.meteo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sumbang on 09/04/15.
 */
public class Database extends SQLiteOpenHelper {


    // déclaration des variables désignant mes tables et ma BD

    private static final String TABLE_VILLE="ville";


    // déclaration des champs des tables

    private static final String V_ID="v_id";

    private static final String V_NAME="v_name";

    private static final String V_TEMPS="v_temps";

    private static final String V_VENT="v_vent";

    private static final String V_CLIMAT="v_climat";

    private static final String V_DES="v_des";

    private static final String V_POSITION="v_pos";

    private static final String V_MINI="v_mini";


    // requete de création des tables

    private static final String CREATE_V="CREATE TABLE "+TABLE_VILLE+" ("+V_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+V_NAME+" TEXT NOT NULL, "+V_TEMPS+" TEXT NOT NULL, "+V_VENT+" TEXTE NOT NULL, "+V_CLIMAT+" TEXTE NOT NULL, "+V_DES+" TEXTE NOT NULL, "+V_POSITION+" TEXT NOT NULL, "+V_MINI+" TEXT NOT NULL); ";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_V);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_VILLE);
        onCreate(db);

    }


}
