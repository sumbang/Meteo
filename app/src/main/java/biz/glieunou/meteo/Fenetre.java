package biz.glieunou.meteo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sumbang on 09/04/15.
 */
public class Fenetre extends DialogFragment {

    public int cle; private static final int CODE_ACTIVITE=5;

    // default constructor of Fragment

    public static Fenetre newInstance(int someInt) {

        Fenetre myFragment = new Fenetre();

        Bundle args = new Bundle();
        args.putInt("cle", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Resources re=getResources();

        String[] ta={""+re.getString(R.string.option2),""+re.getString(R.string.option3),""+re.getString(R.string.option4)};

        AlertDialog.Builder dialog3 = builder.setTitle(R.string.todo)

                .setItems(ta, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int action) {
                        // The 'which' argument contains the index position
                        // of the selected item

                        // choix a faire en fonction de la valeur d'action

                        Bundle objetbundle = new Bundle();

                        final Resources re = getResources();

                        if (action == 0) {

                            final Query req = new Query(getActivity());

                            req.open();

                            Ville v4 = req.getTopVille(1);

                            if (v4 != null) {

                                v4.setPosition(0);
                                req.updateVille(v4, v4.getId());
                            }

                            Ville v3 = req.getVille(cle);

                            v3.setPosition(1);

                            req.updateVille(v3, cle);

                            req.close();

                            ((MainActivity) getActivity()).refreshTop();

                            String mes = re.getString(R.string.update);

                            Toast.makeText(getActivity(), "" + mes + "", Toast.LENGTH_LONG).show();

                            Fenetre.this.getDialog().cancel();

                        } else if (action == 1) {

                            DialogFragment dialog2 = City.newInstance(getArguments().getInt("cle"));

                            dialog2.show(getActivity().getSupportFragmentManager(), "dialog3");

                            Fenetre.this.getDialog().cancel();

                        } else if (action == 2) {

                            final Query req = new Query(getActivity());

                            req.open();
                            req.removeVille(cle);
                            req.close();

                            ((MainActivity) getActivity()).refreshdata();

                            String mes = re.getString(R.string.delete);

                            Toast.makeText(getActivity(), "" + mes + "", Toast.LENGTH_LONG).show();

                            Fenetre.this.getDialog().cancel();
                        }

                    }
                });

        return builder.create();
    }


}
