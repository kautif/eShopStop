package com.example.eshopstop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "PaymentActivity";
    private TextView subtotalTextview;
    private TextView totalTextView;
    private Button checkoutBtn;
    private double amount;
    private String img_url;
    private String name;

    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        amount = 0.0;
        amount = getIntent().getDoubleExtra("amount", 0.0);
        img_url = getIntent().getStringExtra("img_url");
        name = getIntent().getStringExtra("name");

        subtotalTextview = findViewById(R.id.payment_subtotal_textView);
        totalTextView = findViewById(R.id.payment_total_textView);
        checkoutBtn = findViewById(R.id.checkout_button);

        subtotalTextview.setText("$" + String.valueOf(amount));
        Checkout.preload(getApplicationContext());

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PaymentActivity.this, "Checkout button clicked", Toast.LENGTH_SHORT).show();
                startPayment();
            }
        });
    }

    public void startPayment() {
        Checkout checkout = new Checkout();  /**   * Set your logo here   */
        checkout.setKeyID("rzp_test_UtcloQct1nbrsJ");    /**   * Instantiate Checkout   */
//        checkout.setImage(R.drawable.logo);  /**   * Reference to current activity   */
        final Activity activity = PaymentActivity.this;  /**   * Pass your payment options to the Razorpay Checkout as a JSONObject   */
        try {
            JSONObject options = new JSONObject();      /**      * Merchant Name      * eg: ACME Corp || HasGeek etc.      */
            options.put("name", "eShopStop");      /**      * Description can be anything      * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.      *     Invoice Payment      *     etc.      */
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "USD");      /**      * Amount is always passed in currency subunits      * Eg: "500" = INR 5.00      */
           // double total = Double.parseDouble(subtotalTextview.getText().toString());
            amount = amount * 100;
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "kautif@gmail.com");
            preFill.put("contact", "9496900046");
            options.put("prefill", preFill);
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("name", name);
        mMap.put("img_url", img_url);
        mMap.put("payment_id", s);
        mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                .collection("Orders").add(mMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                startActivity(new Intent(PaymentActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        Log.e("Error", s);
    }

    public void payWithStripe(View view) {
        startActivity(new Intent(PaymentActivity.this, StripeActivity.class));
    }
}
