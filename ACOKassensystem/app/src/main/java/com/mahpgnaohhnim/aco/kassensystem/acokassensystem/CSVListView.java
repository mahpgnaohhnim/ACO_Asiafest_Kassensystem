package com.mahpgnaohhnim.aco.kassensystem.acokassensystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by henry on 11.06.17.
 */

public class CSVListView extends Activity {

    FileCSV csvHandler;
    ListView csvListView;
    ArrayList<String> entryArrList;
    ArrayAdapter adapter;
    int selectedItemIndex;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_csvlist);
        entryArrList = new ArrayList<>();

        csvHandler = new FileCSV(this);
        csvListView = (ListView) findViewById(R.id.csvListView);
        csvListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        csvListView.setSelector(R.color.colorPrimary);

        csvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemIndex = position;
                long idtest = id;
            }
        });


        Button backBtn = (Button) findViewById(R.id.backBtn);
        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitActivity();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteSelectedItem();
            }
        });

        updateList();
    }

    private void exitActivity(){
        finish();
    }

    private void deleteSelectedItem(){
        if(selectedItemIndex > 0) {
            entryArrList.remove(selectedItemIndex);
            csvHandler.rewriteFile(entryArrList);
            updateList();
        }
    }

    private void updateList(){
        entryArrList = csvHandler.getCSVArrList();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_listviewtext, entryArrList);
        csvListView.setAdapter(adapter);
    }

}
