package com.mahpgnaohhnim.aco.kassensystem.acokassensystem;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by henry on 10.06.17.
 */

public class SellItem extends TableRow {

    String itemName;
    Float sellPrice;
    int quantity;
    final Button subBtn, addBtn;
    TextView nameLabel, priceLabel, quantityLabel;
    MainActivity mainActivity;

    public SellItem(final Context context, String name, final float price){
        super(context);
        mainActivity = (MainActivity) context;

        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        this.setLayoutParams(tableRowParams);

        this.itemName = name;
        this.sellPrice = price;
        this.quantity = 0;

        subBtn = new Button(context);
        addBtn = new Button(context);
        nameLabel = new TextView(context);
        priceLabel = new TextView(context);
        quantityLabel = new TextView(context);


        TableRow.LayoutParams nameLabelParams = new TableRow.LayoutParams(600,150);

        nameLabel.setText(this.itemName);
        nameLabel.setGravity(Gravity.CENTER_VERTICAL);
        nameLabel.setLayoutParams(nameLabelParams);

        TableRow.LayoutParams textLabelParams = new TableRow.LayoutParams(150,150);
        textLabelParams.gravity = Gravity.CENTER_VERTICAL;

        priceLabel.setText(Integer.toString(this.quantity)+"€");
        priceLabel.setGravity(Gravity.CENTER);
        priceLabel.setLayoutParams(textLabelParams);

        quantityLabel.setText(Integer.toString(this.quantity));
        quantityLabel.setGravity(Gravity.CENTER);
        quantityLabel.setLayoutParams(textLabelParams);

        TableRow.LayoutParams btnParams = new TableRow.LayoutParams(100,100);
        btnParams.gravity = Gravity.CENTER;

        addBtn.setText("+");
        addBtn.setBackgroundColor(Color.GREEN);
        addBtn.setLayoutParams(btnParams);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                quantity += 1;
                updateLabels();
            }
        });

        subBtn.setText("-");
        subBtn.setBackgroundColor(Color.RED);
        subBtn.setLayoutParams(btnParams);
        subBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(quantity>0) {
                    quantity -= 1;
                    updateLabels();
                }
            }
        });

        this.addView(nameLabel);
        this.addView(subBtn);
        this.addView(quantityLabel);
        this.addView(addBtn);
        this.addView(priceLabel);
    }

    private void updateLabels(){
        quantityLabel.setText(Integer.toString(this.quantity));
        priceLabel.setText(Float.toString(this.sellPrice * this.quantity) + "€");
        mainActivity.updateTotalSum();
    }

    public void resetStats(){
        this.quantity = 0;
        updateLabels();
    }

}
