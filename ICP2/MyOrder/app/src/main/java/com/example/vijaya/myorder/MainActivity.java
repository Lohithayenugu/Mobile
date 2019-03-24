package com.example.vijaya.myorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 10;
    final int PEPPERONI_PRICE = 3;
    final int BACON_PRICE = 4;
    final int PESTO_PRICE = 2;
    final int SPINACH_PRICE = 1;
    int quantity = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        TextView quantity_text_view = (TextView) findViewById(R.id.quantity_text_view);
        quantity = Integer.parseInt(quantity_text_view.getText().toString());

        // check if whipped cream is selected
        CheckBox spinach = (CheckBox) findViewById(R.id.spinach_checked);
        boolean hasSpinach = spinach.isChecked();

        // check if chocolate is selected
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni_checked);
        boolean hasPepperoni = pepperoni.isChecked();

        CheckBox bacon = (CheckBox) findViewById(R.id.bacon_checked);
        boolean hasBacon = bacon.isChecked();

        CheckBox pesto = (CheckBox) findViewById(R.id.pesto_checked);
        boolean hasPesto = pesto.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasSpinach, hasPepperoni, hasBacon, hasPesto);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasSpinach, hasPepperoni, hasBacon, hasPesto, totalPrice);

        Intent redirect = new Intent(MainActivity.this, MyOrderActivity.class);
        redirect.putExtra("EXTRA_MESSAGE", orderSummaryMessage);
        MainActivity.this.startActivity(redirect);

        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents

    }

    public void sendEmail(View view) {
        // Write the relevant code for triggering email

        // Hint to accomplish the task

        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        TextView quantity_text_view = (TextView) findViewById(R.id.quantity_text_view);
        quantity = Integer.parseInt(quantity_text_view.getText().toString());

        // check if whipped cream is selected
        CheckBox spinach = (CheckBox) findViewById(R.id.spinach_checked);
        boolean hasSpinach = spinach.isChecked();

        // check if chocolate is selected
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni_checked);
        boolean hasPepperoni = pepperoni.isChecked();

        CheckBox bacon = (CheckBox) findViewById(R.id.bacon_checked);
        boolean hasBacon = bacon.isChecked();

        CheckBox pesto = (CheckBox) findViewById(R.id.pesto_checked);
        boolean hasPesto = pesto.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasSpinach, hasPepperoni, hasBacon, hasPesto);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasSpinach, hasPepperoni, hasBacon, hasPesto, totalPrice);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
String TO[] = {"lohithay1@gmail.com"};
        String CC[] =    {"boyapatiramya1205@gmail.com"};
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC,CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pizza Order Details");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }*/
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasSpinach, boolean hasPepperoni, boolean hasBacon, boolean hasPesto, float price) {
        String orderSummaryMessage = "ORDER DETAILS"+ "\n\n" +getString(R.string.order_summary_name, userInputName) + "\n\n" +
                getString(R.string.order_summary_spinach, boolToString(hasSpinach)) + "\n" +
                getString(R.string.order_summary_bacon, boolToString(hasBacon)) + "\n" +
                getString(R.string.order_summary_pepperoni, boolToString(hasPepperoni)) + "\n" +
                getString(R.string.order_summary_pesto, boolToString(hasPesto)) + "\n\n\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasSpinach, boolean hasPesto, boolean hasPepperoni, boolean hasBacon) {
        int basePrice = PIZZA_PRICE;
        if (hasSpinach) {
            basePrice += SPINACH_PRICE;
        }
        if (hasPesto) {
            basePrice += PESTO_PRICE;
        }
        if (hasPepperoni) {
            basePrice += PEPPERONI_PRICE;
        }
        if (hasBacon) {
            basePrice += BACON_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred cups of coffee");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one cup of coffee");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}