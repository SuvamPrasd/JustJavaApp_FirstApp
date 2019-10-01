package com.example.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.time.Duration;

import static android.widget.Toast.makeText;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        int whippedCreamPrice = 1;
        int chocolateCreamPrice  = 2;
        CheckBox hasWhippedCheck = (CheckBox) findViewById(R.id.hasWhippedCream);
        CheckBox chocolate_check = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText getName = (EditText) findViewById(R.id.name_edit_text);
        Editable customerName = getName.getText();
        boolean checkState = hasWhippedCheck.isChecked();
        boolean hasChocolate = chocolate_check.isChecked();

        if (checkState){
            price += quantity * (whippedCreamPrice);
        }
        if (hasChocolate){
            price += quantity * (chocolateCreamPrice);
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + customerName);
        intent.putExtra(intent.EXTRA_TEXT,createOrderSummary(price,checkState,hasChocolate, customerName));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(int price, boolean checkState, boolean hasChocolate, Editable customerName) {
        String whippedCream = "Name :" + customerName + "\n";
        whippedCream += "And whipped cream? " + checkState;
        whippedCream += "\n Add chocolate? " + hasChocolate;
        whippedCream += "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank You!";
        return   whippedCream;
    }

    private int calculatePrice() {

        return quantity * 5;
    }

  /*  private void displayMessage(String message){
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    public void increment(View view){
       if (quantity == 100){
           Toast.makeText(this,"You cannot order more than 100 coffees", Toast.LENGTH_SHORT).show();
           return;
       }
       quantity += 1;
        displayQuantity(quantity);
    }
    public void decrement(View view){
       if (quantity == 1){
           Toast.makeText(this,"You cannot order less than 1 coffees", Toast.LENGTH_SHORT).show();
           return;
       }
            quantity = quantity - 1;
            displayQuantity(quantity);
    }
}
