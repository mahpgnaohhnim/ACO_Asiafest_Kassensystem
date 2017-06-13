package com.mahpgnaohhnim.aco.kassensystem.acokassensystem;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
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
            }
        });


        Button backBtn = (Button) findViewById(R.id.backBtn);
        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        Button uploadBtn = (Button) findViewById(R.id.uploadBtn);

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

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String channel = "testing";
                uploadToSlack(channel);
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

    private void uploadToSlack(String channel){
        String fileName = "Eintrag.csv";
        File path = this.getExternalFilesDir(null);
        File file = new File(path, fileName); // external Storage

        if(file.exists()) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file));
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing File");
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Sharing File");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            this.startActivity(Intent.createChooser(shareIntent,"Share File"));
        }
    }

}
