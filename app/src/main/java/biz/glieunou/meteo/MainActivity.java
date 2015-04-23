package biz.glieunou.meteo;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public class MainActivity extends ActionBarActivity {

    private ListView liste; private ArrayList<Ville> listev; private SimpleAdapter mSchedule;

    private  ArrayList<HashMap<String,String>> listItem;

    private TextView date,temp,variation,vent,ville; private ImageView nuage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Query req = new Query(this); req.open(); listev=req.getAllVille(); req.close();

        // fixation de la ville par defaut en haut !!

        req.open(); Ville v1=req.getTopVille(1); req.close();

            date = (TextView) findViewById(R.id.textView);
            temp = (TextView) findViewById(R.id.textView2);
            variation = (TextView) findViewById(R.id.textView3);

            vent = (TextView) findViewById(R.id.textView4);
            ville = (TextView) findViewById(R.id.textView5);
            nuage = (ImageView) findViewById(R.id.imageView);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int jr = cal.get(Calendar.DAY_OF_MONTH);

        String[] tab1 = {"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};

        if(v1!=null) {

            temp.setText(v1.getTemp());
            vent.setText(v1.getVent()+" m/s");
            ville.setText(v1.getName());
            variation.setText(v1.getDes());
            date.setText(jr + " " + tab1[month] + " " + year);

            if(!v1.getClimat().isEmpty())  nuage.setImageResource(Integer.parseInt(v1.getClimat()));

            else nuage.setImageResource(R.drawable.art_clouds);


        } else {

            temp.setText("ND"); vent.setText("ND"); ville.setText("ND"); variation.setText("ND"); date.setText(jr + " " + tab1[month] + " " + year);
        }

        liste=(ListView)findViewById(R.id.listView);  req.open(); listev=req.getAllVille(); req.close();

        if(listev!=null){

            Iterator tab = listev.iterator();

            listItem = new ArrayList<HashMap<String, String>>();

            while (tab.hasNext()){

                Ville v = new Ville(); v = (Ville) tab.next();

                HashMap<String, String> map;

                map = new HashMap<String,String>();

                if(!v.getMini().isEmpty()) map.put("icone",String.valueOf(Integer.parseInt(v.getMini())));

                else map.put("icone",String.valueOf(R.drawable.ic_cloudy));

                map.put("lieu",v.getName());

                map.put("temp",v.getTemp());

                map.put("key",""+v.getId());

                listItem.add(map);

            }

           mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.citylayout,
                    new String[] {"icone", "lieu", "temp", "key"}, new int[] {R.id.icone, R.id.lieu, R.id.temp , R.id.key});

            liste.setAdapter(mSchedule);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    HashMap<String, String> map1 = (HashMap<String,String>) liste.getItemAtPosition(position);

                    // lancement des operations pour l'item selectionnee

                    Fenetre dialog1 = new Fenetre(Integer.parseInt(map1.get("key")));

                    FragmentManager manager=getSupportFragmentManager();

                    dialog1.show(manager, "dialog2");

                }
            });



        }

    }


    public void refreshdata(){

        // vidange de la liste existante

       if(listev!=null){ listev.clear(); listItem.clear(); }

        final Query req1 = new Query(this); req1.open(); listev=req1.getAllVille(); req1.close();

        if(listev!=null) {

            Iterator tab = listev.iterator();

            listItem = new ArrayList<HashMap<String, String>>();

            while (tab.hasNext()) {

                Ville v = new Ville();
                v = (Ville) tab.next();

                HashMap<String, String> map;

                map = new HashMap<String, String>();

                if(!v.getMini().isEmpty()) map.put("icone", String.valueOf(Integer.parseInt(v.getMini())));

                else map.put("icone",String.valueOf(R.drawable.ic_cloudy));

                map.put("lieu", v.getName());

                map.put("temp", v.getTemp());

                map.put("key", "" + v.getId());

                listItem.add(map);

            }

            mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.citylayout,
                    new String[] {"icone", "lieu", "temp", "key"}, new int[] {R.id.icone, R.id.lieu, R.id.temp , R.id.key});

            liste.setAdapter(mSchedule);

        }


        }

    public void refreshTop(){

        final Query req2 = new Query(this);

        req2.open(); Ville v2=req2.getTopVille(1); req2.close();

        if(v2!=null) {

            temp.setText(v2.getTemp());
            vent.setText(v2.getVent()+" m/s");
            ville.setText(v2.getName());
            variation.setText(v2.getDes());

            if(v2.getClimat().isEmpty()) nuage.setImageResource(R.drawable.art_clouds);

            else nuage.setImageResource(Integer.parseInt(v2.getClimat()));


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        else if(id == R.id.refresh){

            // lancement de la requete sur le serveur openweather map et lecture des resultats

            final Query req = new Query(this); req.open(); Ville v=req.getTopVille(1); req.close();

            if(v!=null) { Connection con=new Connection(this); con.execute(v.getName()); }

               return true;
        }

        else if(id == R.id.city){

            DialogFragment dialog = new City(0);

            dialog.show(getSupportFragmentManager(), "dialog1");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static final String BASEURL ="http://api.openweathermap.org/data/2.5/weather?q=";

    private class Connection extends AsyncTask<String,String,String> {

        public MainActivity activity;

        public Connection(MainActivity a){

            this.activity=a;
        }

        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog

            final Resources re=getResources();  String mes=re.getString(R.string.start);

            Toast.makeText(getApplicationContext(),mes,Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onProgressUpdate(String... values){

        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            final Resources re=getResources();  String mes=re.getString(R.string.finish);

            // Toast.makeText(getApplicationContext(),mes,Toast.LENGTH_LONG).show();

            // Enregistrement des donnees JSON recues dans la bd et mise a jours des interfaces

            try {

                JSONObject jObj = new JSONObject(result);

                JSONObject main = getObject("main", jObj); JSONObject wind = getObject("wind", jObj); JSONArray weathertab = jObj.getJSONArray("weather");

                JSONObject weather = weathertab.getJSONObject(0);

                final Query req1 = new Query(getApplicationContext()); req1.open(); Ville v=req1.getTopVille(1);

                float tps=getFloat("temp",main); Float f=new Float(273.15); tps=tps-f;

                DecimalFormat df = new DecimalFormat("##.##");

                v.setTemp(df.format(tps)+" C"); v.setVent("Wind : "+getString("speed",wind)); //v.setClimat(getString("main", weather));

                v.setDes(getString("description", weather));  String icon=getString("icon",weather);

                v.setClimat(""+getIcon(icon,1)); v.setMini(""+getIcon(icon,2));

                req1.updateVille(v,v.getId());

                req1.close();

                activity.refreshTop(); activity.refreshdata();

            } catch (JSONException e) {

                e.printStackTrace();

            } catch (NullPointerException e){

                final Resources re1=getResources();

                String mes1=re1.getString(R.string.error);

                Toast.makeText(getApplicationContext(), "" + mes1 + "", Toast.LENGTH_LONG).show();
            }



        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
           return getMeteoData(arg0[0]);

        }

    }

    // fonction pour retourner l'icone correspondand a la temperature

    public int getIcon(String icon, int position){

        if(icon.equals("01d") && position==1) return R.drawable.art_clear;

        else if(icon.equals("01n") && position==1) return R.drawable.art_fog;

        else if(icon.equals("02d") && position==1) return R.drawable.art_light_clouds;

        else if(icon.equals("02n") && position==1) return R.drawable.art_fog;

        else if(icon.equals("03d") && position==1) return R.drawable.art_clouds;

        else if(icon.equals("03n") && position==1) return R.drawable.art_clouds;

        else if(icon.equals("04d") && position==1) return R.drawable.art_clouds;

        else if(icon.equals("04n") && position==1) return R.drawable.art_clouds;

        else if(icon.equals("09d") && position==1) return R.drawable.art_light_rain;

        else if(icon.equals("09n") && position==1) return R.drawable.art_light_rain;

        else if(icon.equals("10d") && position==1) return R.drawable.art_rain;

        else if(icon.equals("10n") && position==1) return R.drawable.art_rain;

        else if(icon.equals("11d") && position==1) return R.drawable.art_storm;

        else if(icon.equals("11n") && position==1) return R.drawable.art_storm;

        else if(icon.equals("13d") && position==1) return R.drawable.art_snow;

        else if(icon.equals("13n") && position==1) return R.drawable.art_snow;

        else if(icon.equals("50d") && position==1) return R.drawable.art_fog;

        else if(icon.equals("50n") && position==1) return R.drawable.art_fog;

        else if(icon.equals("01d") && position==2) return R.drawable.ic_clear;

        else if(icon.equals("01n") && position==2) return R.drawable.ic_fog;

        else if(icon.equals("02d") && position==2) return R.drawable.ic_light_clouds;

        else if(icon.equals("02n") && position==2) return R.drawable.ic_fog;

        else if(icon.equals("03d") && position==2) return R.drawable.ic_cloudy;

        else if(icon.equals("03n") && position==2) return R.drawable.ic_cloudy;

        else if(icon.equals("04d") && position==2) return R.drawable.ic_cloudy;

        else if(icon.equals("04n") && position==2) return R.drawable.ic_cloudy;

        else if(icon.equals("09d") && position==2) return R.drawable.ic_light_rain;

        else if(icon.equals("09n") && position==2) return R.drawable.art_light_rain;

        else if(icon.equals("10d") && position==2) return R.drawable.ic_rain;

        else if(icon.equals("10n") && position==2) return R.drawable.ic_rain;

        else if(icon.equals("11d") && position==2) return R.drawable.ic_storm;

        else if(icon.equals("11n") && position==2) return R.drawable.ic_storm;

        else if(icon.equals("13d") && position==2) return R.drawable.ic_snow;

        else if(icon.equals("13n") && position==2) return R.drawable.ic_snow;

        else if(icon.equals("50d") && position==2) return R.drawable.ic_fog;

        else if(icon.equals("50n") && position==2) return R.drawable.ic_fog;

        else return 0;

    }

   // fonctions permettant d'exploiter du contenu JSON

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    // fonction qui permet de faire une requete sur openweathermap et de retourner le resulat sous format JSON

    public String getMeteoData(String location) {

        HttpURLConnection con = null;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(BASEURL + location)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Lecture du resultat sous format Json
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();

        }

        catch(Throwable t) {
            t.printStackTrace();
        }

        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }


}
