package biz.glieunou.meteo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by ChristianBaurel on 4/24/2015.
 */
public class About extends ActionBarActivity {

    private static final int CODE_ACTIVITE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

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

            Intent intent=new Intent(About.this,About.class);

            startActivityForResult(intent,CODE_ACTIVITE);

            return true;
        }

        else if(id == R.id.refresh){

            // lancement de la requete sur le serveur openweather map et lecture des resultats

            Intent intent=new Intent(About.this,About.class);

            startActivityForResult(intent,CODE_ACTIVITE);

            return true;
        }

        else if(id == R.id.city){

            Intent intent=new Intent(About.this,About.class);

            startActivityForResult(intent,CODE_ACTIVITE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
