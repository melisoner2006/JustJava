package com.melisoner.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;


public class MainActivity extends ActionBarActivity {

    int quantity = 2;
    int price = 5;
    private String orderHistory = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view){
        updateOrderHistory(view);
    }

    private void display(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int getTotalPrice(int number){

        int total = 0;
        total = number*price;
        if(hasWhippedCream()){
            total += 1*quantity;
        }
        if(hasChocolate()) {
            total +=2*quantity;
        }

        return total;

    }

    public void increment(View view){
        if(quantity < 100){
            quantity ++;
        }
        else{
            quantity = 100;
        }
        display(quantity);
    }


    public void decrement(View view){
        if(quantity>0){
            quantity--;
        }
        else{
            quantity = 0;
        }
        display(quantity);
    }

    public boolean hasWhippedCream(){
        final CheckBox checkbox = (CheckBox)findViewById(R.id.whippedCream);
        if(checkbox.isChecked()){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean hasChocolate(){
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate);
        if(checkBox.isChecked()){
            return true;
        }else{
            return false;
        }
    }

    public void updateOrderHistory(View view){
        TextView orderTextView = (TextView) findViewById(R.id.order_text_view);

        orderHistory += "Name: "+getName()+"\n";
        orderHistory +=  quantity+ " coffees\n";
        orderHistory += "Has whipped cream? "+hasWhippedCream()+"\n";
        orderHistory += "Has chocolate? "+hasChocolate()+"\n";
        orderHistory += "Total: " + getTotalPrice(quantity) +"\n";
        orderHistory += "Thank you!";
        orderTextView.setText(orderHistory);

        composeEmail();
    }

    public String getName(){
        String name;
        EditText nameField = (EditText) findViewById(R.id.name_edit_text);
        name = nameField.getText().toString();
        return name;
    }

    public void composeEmail(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "melisoner2006@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for "+getName());
        intent.putExtra(Intent.EXTRA_TEXT, orderHistory);
        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }
    }

}
