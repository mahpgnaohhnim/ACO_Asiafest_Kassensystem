package com.mahpgnaohhnim.aco.kassensystem.acokassensystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by henry on 11.06.17.
 */

public class CSVListView extends Activity {

    FileCSV csvHandler;
    ListView csvListView;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_csvlist);

        csvHandler = new FileCSV(this);
        csvListView = (ListView) findViewById(R.id.csvListView);

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

        showList();
    }

    private void exitActivity(){
        finish();
    }

    private void deleteSelectedItem(){

    }

    private void showList(){
        Button test = new Button(this);
        test.setText("hii");
    }
}
