package com.example.vijaya.myorder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MyOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder);
        Intent intent=getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        TextView textviewCtrl = (TextView) findViewById(R.id.textView);
        textviewCtrl.append(" "+message);

    }

    public void logout(View view){
        Intent redirect = new Intent(MyOrderActivity.this,MainActivity.class);
        MyOrderActivity.this.startActivity(redirect);
    }
}
