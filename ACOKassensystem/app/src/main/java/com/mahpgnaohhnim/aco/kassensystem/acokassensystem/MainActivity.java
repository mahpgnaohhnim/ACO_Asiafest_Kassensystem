package com.mahpgnaohhnim.aco.kassensystem.acokassensystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    FileCSV csvFilehandler = new FileCSV(this);
    TableLayout itemTable;
    float totalSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        totalSum = 0;
        itemTable = (TableLayout) findViewById(R.id.itemTable);

        SellItem bBaoK = new SellItem(this, "Banh Bao Klassisch", 3.50f);
        SellItem bBaoV = new SellItem(this, "Banh Bao Vegetarisch", 3f);
        SellItem bBaoKBundle = new SellItem(this, "Banh Bao Bundle Klassisch", 5.50f);
        SellItem bBaoVBundle = new SellItem(this, "Banh Bao Bundle Vegetarisch", 5f);

        SellItem sRollK = new SellItem(this, "Sommer Rollen Klassisch", 4.50f);
        SellItem sRollV = new SellItem(this, "Sommer Rollen Vegetarisch", 4f);
        SellItem sRollKBundle = new SellItem(this, "Sommer Rollen Bundle Klassisch", 6.50f);
        SellItem sRollVBundle = new SellItem(this, "Sommer Rollen Bundle Vegetarisch", 6f);

        SellItem limJuice = new SellItem(this, "Limetten Saft", 3f);

        LinearLayout summary = initSummaryRow();

        bBaoKBundle.setBackgroundColor(Color.rgb(255,223,117));
        bBaoVBundle.setBackgroundColor(Color.rgb(255,223,117));
        sRollKBundle.setBackgroundColor(Color.rgb(255,223,117));
        sRollVBundle.setBackgroundColor(Color.rgb(255,223,117));
        limJuice.setBackgroundColor(Color.rgb(255,223,117));

        itemTable.addView(bBaoK);
        itemTable.addView(bBaoV);
        itemTable.addView(bBaoKBundle);
        itemTable.addView(bBaoVBundle);
        itemTable.addView(sRollK);
        itemTable.addView(sRollV);
        itemTable.addView(sRollKBundle);
        itemTable.addView(sRollVBundle);
        itemTable.addView(limJuice);
        itemTable.addView(summary);

    }

    private LinearLayout initSummaryRow(){

        TableLayout.LayoutParams totalLabelParam = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);


        LinearLayout totalRow = new LinearLayout(this);
        totalRow.setLayoutParams(totalLabelParam);
        totalRow.setBackgroundColor(Color.rgb(119,182,239));

        LinearLayout.LayoutParams txtLayParam = new LinearLayout.LayoutParams(400,200);
        TextView textLabel = new TextView(this);
        textLabel.setText("Gesamtsumme:");
        textLabel.setLayoutParams(txtLayParam);
        textLabel.setGravity(Gravity.CENTER);

        TextView totalSum = new TextView(this);
        totalSum.setId(R.id.totalSumLabel);
        totalSum.setText("0€");

        Button submitBtn = new Button(this);
        submitBtn.setText("submit");
        submitBtn.setGravity(Gravity.CENTER);

        Button showListBtn = new Button(this);
        showListBtn.setText("show List");
        showListBtn.setGravity(Gravity.CENTER);

        totalRow.addView(textLabel);
        totalRow.addView(totalSum);
        totalRow.addView(submitBtn);
        totalRow.addView(showListBtn);
        totalRow.setGravity(Gravity.CENTER);

        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                submit();
            }
        });

        showListBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showCSVList();
            }
        });

        return  totalRow;
    }

    public void updateTotalSum(){
        calcTotalSum();
        TextView totalSumLabel = (TextView) findViewById(R.id.totalSumLabel);
        totalSumLabel.setText(Float.toString(totalSum)+"€");
    }

    private void calcTotalSum(){
        int tableLength = itemTable.getChildCount();
        totalSum = 0;
        for(int i =0; i<tableLength; i++){
            View child = itemTable.getChildAt(i);
            if(child instanceof SellItem){
                SellItem item = (SellItem) child;
                int quantity = item.quantity;
                float price = item.sellPrice;
                float sum = quantity*price;
                totalSum += sum;
            }
        }
    }


    private void submit() {

        TextView totalSumLabel = (TextView) findViewById(R.id.totalSumLabel);
        String totalSum = totalSumLabel.getText().toString();
        System.out.println(totalSum);

        if (!totalSum.equals("0€") && !totalSum.equals("0.0€"))  {
            String currentEntry = getCurrentEntry();
            csvFilehandler.writeFile(currentEntry);
            Toast toast = Toast.makeText(this, "Saved", Toast.LENGTH_SHORT);
            toast.show();
            resetStats();
        }
    }

    private String getCurrentEntry(){
        String currentEntry = "";
        float finalPrice = 0;
        int tableLength = itemTable.getChildCount();
        for(int i =0; i<tableLength; i++){
            View child = itemTable.getChildAt(i);
            if(child instanceof SellItem){
                SellItem item = (SellItem) child;
                int quantity = item.quantity;
                float price = item.sellPrice;
                float sum = quantity*price;
                finalPrice += sum;

                currentEntry += quantity+";";
            }
        }
        currentEntry += finalPrice+"€;";

        return currentEntry;
    }


    private void resetStats() {
        int tableLength = itemTable.getChildCount();
        for(int i =0; i<tableLength; i++){
            View child = itemTable.getChildAt(i);
            if(child instanceof SellItem){
                SellItem item = (SellItem) child;
                item.resetStats();
            }
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                System.out.println("yeah");
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                System.out.println("yeah");
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        } else {
            Log.v("Permission", "Permission Granted");
        }
    }

    private void showCSVList() {
        startActivity(new Intent(this, CSVListView.class));
    }



    /*private void deleteLine() {
        AlertDialog.Builder alertDelete = new AlertDialog.Builder(this);
        alertDelete.setMessage(R.string.alertDeleteLineMessage)
                .setPositiveButton(R.string.alertDeleteLinePositive, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        csvFilehandler.deleteLastLine();
                    }

                })
                .setNegativeButton(R.string.alertDeleteLineNegative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDelete.create();
        alertDialog.show();

    }*/
}
