package biz.glieunou.meteo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by sumbang on 09/04/15.
 */
public class City extends DialogFragment {

    public int cle; private static final int CODE_ACTIVITE=5;

    // default constructor of Fragment

    public static City newInstance(int someInt) {

        City myFragment = new City();

        Bundle args = new Bundle();
        args.putInt("cle", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Resources re=getResources();

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogview=inflater.inflate(R.layout.city, null);

        String textbt; cle=getArguments().getInt("cle");

        if(cle!=0){

            final Query req = new Query(getActivity());

            req.open();

            Ville v4=req.getVille(cle);

            req.close();

            EditText town = (EditText)dialogview.findViewById(R.id.cite);

            town.setText(v4.getName());

           textbt=re.getString(R.string.option3);

        } else { textbt=re.getString(R.string.addcity); }


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setView(dialogview)

               .setTitle(R.string.citytitle)

                // Add action buttons
                .setPositiveButton(textbt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // enregistrement de la ville et test des donnees meteo

                        final Query req = new Query(getActivity());

                        EditText town = (EditText)dialogview.findViewById(R.id.cite);

                        String towname=town.getText().toString();

                        if(cle==0){  Ville v = new Ville(); v.setName(towname); v.setTemp("ND"); v.setPosition(0);

                        req.open(); req.inserVille(v); req.close();  String mes=re.getString(R.string.success);

                        Toast.makeText(getActivity(), "" + mes + "", Toast.LENGTH_LONG).show();

                        }

                        else {

                            req.open(); Ville v6=req.getVille(cle); v6.setName(towname); req.updateVille(v6,cle); req.close();

                            String mes=re.getString(R.string.update);

                            Toast.makeText(getActivity(), "" + mes + "", Toast.LENGTH_LONG).show();
                        }



                        ((MainActivity)getActivity()).refreshdata();

                        City.this.getDialog().cancel();


                    }
                })
                .setNegativeButton(R.string.cancelcity, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        City.this.getDialog().cancel();

                    }

                });
        return builder.create();
    }



}
