package biz.glieunou.meteo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by sumbang on 09/04/15.
 */
public class Query {


    private static final String NOM_BDD="weather.db";

    private static final String TABLE_VILLE="ville";

    private static final int VERSION_BDD=4;


    private static final String V_ID="v_id";

    private static final int NUM_ID=0;

    private static final String V_NAME="v_name";

    private static final int NUM_NAME=1;

    private static final String V_TEMPS="v_temps";

    private static final int NUM_TEMPS=2;

    private static final String V_VENT="v_vent";

    private static final int NUM_VENT=3;

    private static final String V_CLIMAT="v_climat";

    private static final int NUM_CLIMAT=4;

    private static final String V_DES="v_des";

    private static final int NUM_DES=5;

    private static final String V_POS="v_pos";

    private static final int NUM_POS=6;

    private static final String V_MINI="v_mini";

    private static final int NUM_MINI=7;


    private SQLiteDatabase bdd;

    private Database ma_bdd;

    public Query(Context context){

        // on crée la bdd et ses tables
        ma_bdd=new Database(context, NOM_BDD, null, VERSION_BDD);
    }


    public void open(){

        // ouverture de la BDD en écriture
        bdd=ma_bdd.getWritableDatabase();
    }


    public void close(){

        // on ferme l'accès à la BDD
        bdd.close();
    }


    public SQLiteDatabase getBdd(){

        return bdd;
    }


    public long inserVille(Ville city){

        // création d'un content values

        ContentValues values=new ContentValues();

        values.put(V_NAME,city.getName());

        values.put(V_TEMPS,city.getTemp());

        values.put(V_VENT,city.getVent());

        values.put(V_CLIMAT,city.getClimat());

        values.put(V_DES,city.getDes());

        values.put(V_POS,city.getPosition());

        values.put(V_MINI,city.getMini());

        return bdd.insert(TABLE_VILLE,null,values);

    }


    public long updateVille(Ville city,int id){

        ContentValues values=new ContentValues();

        //values.put(W_ID,bourse.getID());

        values.put(V_NAME,city.getName());

        values.put(V_TEMPS,city.getTemp());

        values.put(V_VENT,city.getVent());

        values.put(V_CLIMAT,city.getClimat());

        values.put(V_DES,city.getDes());

        values.put(V_POS,city.getPosition());

        values.put(V_MINI,city.getMini());

        return bdd.update(TABLE_VILLE,values, V_ID+"="+id,null);

    }


    public int removeVille(int id){

        return bdd.delete(TABLE_VILLE, V_ID+"="+id,null);
    }


    public Ville getVille(int id){

        Cursor c=bdd.query(TABLE_VILLE, new String[]{V_ID,V_NAME,V_TEMPS,V_VENT,V_CLIMAT,V_DES,V_POS,V_MINI}, V_ID+"="+id, null,null,null,null);

        return cursorVille(c);

    }

    public Ville getTopVille(int key){

        Cursor c=bdd.query(TABLE_VILLE, new String[]{V_ID,V_NAME,V_TEMPS,V_VENT,V_CLIMAT,V_DES,V_POS,V_MINI}, V_POS+"="+key, null,null,null,null);

        return cursorVille(c);

    }


    public Ville cursorVille(Cursor c){

        if(c.getCount()==0){

            return null;

        }

        else { c.moveToFirst();

            Ville city=new Ville();

            city.setId(c.getInt(NUM_ID));

            city.setName(c.getString(NUM_NAME));

            city.setTemp(c.getString(NUM_TEMPS));

            city.setVent(c.getString(NUM_VENT));

            city.setClimat(c.getString(NUM_CLIMAT));

            city.setDes(c.getString(NUM_DES));

            city.setPosition(c.getInt(NUM_POS));

            city.setMini(c.getString(NUM_MINI));

            c.close();

            return city;

        }

    }

    public ArrayList<Ville> getAllVille(){

        Cursor c=bdd.query(TABLE_VILLE, new String[]{V_ID,V_NAME,V_TEMPS,V_VENT,V_CLIMAT,V_DES,V_POS,V_MINI}, null, null,null,null,null);

        return cursorVilleAll(c);

    }


    public ArrayList<Ville> cursorVilleAll(Cursor c){

        ArrayList<Ville> list=new ArrayList<Ville>();

        if(c.getCount()==0){

            return null;

        }

        else {

            // déplaçons le curseur sur le premier enregistrement

            c.moveToFirst();

            // tant que le curseur pourra se déplacer sur les autres éléments, remplir la liste

            while(c.isAfterLast()==false){

                Ville city=new Ville();

                city.setId(c.getInt(NUM_ID));

                city.setName(c.getString(NUM_NAME));

                city.setTemp(c.getString(NUM_TEMPS));

                city.setVent(c.getString(NUM_VENT));

                city.setClimat(c.getString(NUM_CLIMAT));

                city.setDes(c.getString(NUM_DES));

                city.setPosition(c.getInt(NUM_POS));

                city.setMini(c.getString(NUM_MINI));

                list.add(city);

                c.moveToNext();

            }

            c.close();

            return list;

        }

    }





}
