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
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


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

        SellItem bBaoK = new SellItem(this, "Banh Bao Klassisch", 3.5f);
        SellItem bBaoV = new SellItem(this, "Banh Bao Vegetarisch", 3.0f);
        SellItem bBaoKBundle = new SellItem(this, "Banh Bao Bundle Klassisch", 5.0f);
        SellItem bBaoVBundle = new SellItem(this, "Banh Bao Bundle Vegetarisch", 5.0f);

        SellItem sRollK = new SellItem(this, "Sommer Rollen Klassisch", 4f);
        SellItem sRollV = new SellItem(this, "Sommer Rollen Vegetarisch", 4f);
        SellItem sRollKBundle = new SellItem(this, "Sommer Rollen Bundle Klassisch", 5.5f);
        SellItem sRollVBundle = new SellItem(this, "Sommer Rollen Bundle Vegetarisch", 5.5f);

        SellItem limJuice = new SellItem(this, "Limetten Saft", 2.5f);

        TableRow summary = initSummaryRow();

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

        /*ArrayList<View> allButtons;
        allButtons = ((LinearLayout) findViewById(R.id.itemTable)).getTouchables();
        for(View touchables : allButtons){
            touchables.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        updateTotalSum();
                    }
                    return false;
                }
            });
        }*/
    }

    private TableRow initSummaryRow(){

        TableLayout.LayoutParams totalLabelParam = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);


        TableRow totalRow = new TableRow(this);
        totalRow.setLayoutParams(totalLabelParam);
        totalRow.setBackgroundColor(Color.rgb(119,182,239));

        TableRow.LayoutParams txtLayParam = new TableRow.LayoutParams(80,200);
        TextView textLabel = new TextView(this);
        textLabel.setText("Gesamtsumme:");
        textLabel.setLayoutParams(txtLayParam);
        textLabel.setGravity(Gravity.CENTER);

        TextView totalSum = new TextView(this);
        totalSum.setId(R.id.totalSumLabel);
        totalSum.setText("0€");

        totalRow.addView(textLabel);
        totalRow.addView(totalSum);
        totalRow.setGravity(Gravity.CENTER);

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


    /*private void submit() {
        String price = totalSum.getText().toString();
        String cause = dropDown.getSelectedItem().toString();
        String count = totalCount.getText().toString();
        if (count != "0") {

            csvFilehandler.countAdult = currentAdultCount;
            csvFilehandler.plz = postalCode.getText().toString();
            csvFilehandler.countChild = currentChildCount;
            csvFilehandler.sum = price;
            csvFilehandler.cause = cause;

            csvFilehandler.writeFile();

            resetStats();
        }
    }


    private void updateCounts() {
        int priceChild = 2;
        int priceAdult = 5;
        int totalChildPrice = currentChildCount * priceChild;
        int totalAdultPrice = currentAdultCount * priceAdult;
        int totalPrice = totalAdultPrice + totalChildPrice;
        int counts = currentChildCount + currentAdultCount;

        totalSum.setText(totalPrice + "€");
        childSum.setText(totalChildPrice + "€");
        adultSum.setText(totalAdultPrice + "€");
        totalCount.setText(Integer.toString(counts));
    }

    private void resetStats() {
        currentAdultCount = 0;
        currentChildCount = 0;
        countChild.setText("0");
        countAdult.setText("0");
        postalCode.setText("");
        updateCounts();
    }*/

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
        Intent csvViewer = csvFilehandler.showFile();
        startActivity(csvViewer);
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
