package com.example.randomteams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.dialog.InsetDialogOnTouchListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Manual extends AppCompatActivity {

    private ArrayList<String> namesArray;
    private ArrayAdapter<String> adapter;
    private ListView namesList;
    private Button addName, generateTeams;
    private EditText names;
    private TextView crossDelNames, nameCounter;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        counter = 0;
        namesArray = new ArrayList<>();             //Arraylist
        addName = findViewById(R.id.addNameBtn);    //Add button
        generateTeams = findViewById(R.id.genBtn);  //Gen Btn
        namesList = findViewById(R.id.nameList);    //ListView
        names = findViewById(R.id.names);           //EditText
        nameCounter = findViewById(R.id.countNames);// TextView for names counting
        crossDelNames = findViewById(R.id.deleteName); //TextView with a cross
        /*
        Note: WHen there are multiple textviews in the layout u providing to arrayadapter
        give the id of view u want to populate.!!! see R.id.displayName
        refer: https://stackoverflow.com/questions/5916459/you-must-supply-a-resource-id-for-a-textview-android-error
         */
        adapter = new ArrayAdapter<>(this, R.layout.name_list_custom, R.id.displayName, namesArray);

        addName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNamesClicked();
            }
        });

        generateTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeTeamsDialogBox();
            }
        });
        /**
         * Error here. See maybe coz its ambiguous as to which cross is clicked. so many in ListView
         * See adding it in activity_manual and somethimg
         * or selecting all view by long press and then delete!!??
         */
//        crossDelNames.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                deleteNames();
//                Log.i("TEST", "onClick: Cross view CLICKED");
//            }
//        });

    }

    private void makeTeamsDialogBox() {
        //Making object of my DialogBox class
        DialogBox_totalTeams dialogBox = new DialogBox_totalTeams();
        // calling show on my dialog to present dialog box on screen
        dialogBox.show(getSupportFragmentManager(), "MakeTeams Dialog");
    }

//    private void deleteNames() {
//        String nameDeleted = names.getText().toString();
//        int delNameIndex = namesArray.indexOf(nameDeleted);
//        namesArray.remove(delNameIndex);
//        Log.i("Test", "deleteNames: nameDeleted: ***" + nameDeleted + "*** at index= " + delNameIndex+"New element at that index = "+namesArray.get(delNameIndex));
//    }

    private void addNamesClicked() {
        //TODO: also add condition when only space is there
        if(!names.getText().toString().isEmpty()) {
            Log.i("TEST", "addNamesClicked: Button CLicked, Text in box = " + names.getText().toString());
            namesArray.add(names.getText().toString());
            names.setText("");
            //TODO: Do see how to make list display from top of screen and not from bottom
            //DOne See xml and note the reason!!!
            namesList.setAdapter(adapter);
            counter++;
            nameCounter.setText("Total Names: " + counter);
        }
    }
}