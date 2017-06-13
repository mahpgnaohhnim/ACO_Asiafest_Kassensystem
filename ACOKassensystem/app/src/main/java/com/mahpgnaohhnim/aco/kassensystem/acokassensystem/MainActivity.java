package com.mahpgnaohhnim.aco.kassensystem.acokassensystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        setContentView(R.layout.main_activity);

        checkPermission();

        totalSum = 0;
        itemTable = (TableLayout) findViewById(R.id.itemTable);

        SellItem bBaoK = new SellItem(this, "Banh Bao Klassisch", 3.5f);
        SellItem bBaoV = new SellItem(this, "Banh Bao Vegetarisch", 3.0f);
        SellItem bBaoKBundle = new SellItem(this, "Banh Bao Bundle Klassisch", 5.0f);
        SellItem bBaoVBundle = new SellItem(this, "Banh Bao Bundle Vegetarisch", 5.0f);

        SellItem sRollK = new SellItem(this, "Sommer Rollen Klassisch", 4f);
        SellItem sRollV = new SellItem(this, "Sommer Rollen Vegetarisch", 4f);
        SellItem sRollKBundle = new SellItem(this, "Sommer Rollen Bundle Klassisch", 5.5f);
        SellItem sRollVBundle = new SellItem(this, "Sommer Rollen Bundle Vegetarisch", 5.5f);

        SellItem limJuice = new SellItem(this, "Limetten Saft", 2.5f);

        initSummaryRow();

        bBaoKBundle.setBackgroundColor(Color.rgb(255,223,117));
        bBaoVBundle.setBackgroundColor(Color.rgb(255,223,117));
        sRollKBundle.setBackgroundColor(Color.rgb(255,223,117));
        sRollVBundle.setBackgroundColor(Color.rgb(255,223,117));

        itemTable.addView(bBaoK);
        itemTable.addView(bBaoV);
        itemTable.addView(bBaoKBundle);
        itemTable.addView(bBaoVBundle);
        itemTable.addView(sRollK);
        itemTable.addView(sRollV);
        itemTable.addView(sRollKBundle);
        itemTable.addView(sRollVBundle);
        itemTable.addView(limJuice);
    }

    private void initSummaryRow(){

        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        Button showListBtn = (Button) findViewById(R.id.showCSVBtn);

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

    }

    public void updateTotalSum(){
        calcTotalSum();
        TextView totalSumLabel = (TextView) findViewById(R.id.totalSumVal);
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

        TextView totalSumLabel = (TextView) findViewById(R.id.totalSumVal);
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
