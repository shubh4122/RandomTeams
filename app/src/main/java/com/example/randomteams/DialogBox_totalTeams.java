package com.example.randomteams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.randomteams.DisplayTeams;
import com.example.randomteams.R;

/**
 * Note:
 * THIS is a CUSTOM layout Dialog box!!The custom layout includes edittext
 * note this down in the notes. How to make dialog... Basics and related things. Note more to learn on this
 * More see here:
 * 1. https://developer.android.com/guide/topics/ui/dialogs#CustomLayout
 * 2. https://www.youtube.com/watch?v=ARezg1D9Zd0
 */


//VV IMP note this:

/**
 * Here my class is extending DialogFormat. THere were 2 such classes in the options
 * one is marked "android.app"
 * and other is "androidx.fragment.app"
 *
 * SO first i was using 1. .show(...) showed error in Manual class
 * but 2nd one works fine; Note this, and find why is it so??
 */
public class DialogBox_totalTeams extends DialogFragment {
    //AppCompatDialogFragment see using this,, in video this was used
    //TODO: Show How many members name has been entered in dialog box

    EditText totalTeams;
    private DialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //getting LayoutInflater
        LayoutInflater inflater = getActivity().getLayoutInflater();



        //See as we have to do this, so using Method 1 above is better coz there View view means same.
        //hence here it could have been reduced to :
        // totalTeams = view.findView.....;
        totalTeams = (EditText) inflater.inflate(R.layout.dialog_custom_layout, null).findViewById(R.id.totalTeams);



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        /**
         * Method 1:
         * View view = inflater.inflate(R.layout.dialog_custom_layout, null);
         * builder.setView(view) and rest is same.
         * This is prefered method because of Edittext. watch below.
         *
         * Method 2:
         * below:
         */
        builder.setView(inflater.inflate(R.layout.dialog_custom_layout, null))
                //Adds title for the dialog boc
                .setTitle("Enter total Teams:")
                //Adds action button.
                // 3 TYpes of buttons, Negative Positive Neutral
                // refer https://developer.android.com/guide/topics/ui/dialogs#AddingButtons
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no need to do anything here. Negative button itself means taking u back!
                    }
                })
                //Also note we need to add OnclickListener also!
                .setPositiveButton("Randomize", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!totalTeams.getText().toString().isEmpty())
                            displayTeams();
                        else {

                            Toast.makeText(getActivity(), "Enter a value. Cant be left blank", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //Adds message in dialog box
//                .setMessage("Total names: "  );  // Add this later. See it says param constructor cant be used in fragments activity??



        return builder.create();
    }


    private void displayTeams() {

        int n = 0; //Total Number of teams to be made
        try {
            n = Integer.parseInt(totalTeams.getText().toString());
        } catch (NumberFormatException e) {
//            e.printStackTrace();

            Toast.makeText(getActivity(), "!!!Some error occured!!!", Toast.LENGTH_LONG).show();
        }
        //see what this line does!!??
        listener.getTotalTeamsInput(n);

        /**
         * IMP NOTE
         * when i was writing an explicit intent to open another activity I was writing as we write for all other such cases
         * that is
         *  Intent i = new Intent(this, DisplayTeams.class);
         *
         *  Now this here refers to the present context that is, this Fragment(Dialog Box), and Fragment is not a valid CONTEXT according to:
         *   https://stackoverflow.com/questions/46047979/cannot-resolve-constructor-intent
         *
         *  But Activity is the correct context. So used getActivity() to pass the current activity as the context.
         *  NOTE: Use of getActivity too
         *
         *  also Research and learn about what Fragments are.. see that udacity course. it has it
         */
        Intent i = new Intent(getActivity(), DisplayTeams.class); //SEE why this error?? --- SORTED
        startActivity(i);
    }


    // see why do we do this. WHat is use of onAttach?
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//shortcut/ use ctrl + alt + T. for below try cathc and other automatically
        /**
         * 1. see why using try cathc?
         * 2. context here?
         * 3. what if i have to pass context to a new activity and not the underlaying one??
         */
        try {
            Log.i("______________________", context.toString());
            listener = (DialogListener) context;

            Log.i("======================", "onAttach: Completed the suspicious Try block");
        } catch (Exception e) {
            //ClassCastException?
            //see what is difference if we write particular exception in catch param instead of Exception e
//            e.printStackTrace();
//            throw new ClassCastException(context.toString() + "Must implement DialogListener");
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    // This is done to send the entered text to another activity. Without this
// It is not possible. SEe? Also see whether we need constructors anymore?
    public interface DialogListener {
        void getTotalTeamsInput(int totalTeamsEntered);
    }
}
