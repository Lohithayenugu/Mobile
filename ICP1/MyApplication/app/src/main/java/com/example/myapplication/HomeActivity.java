package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        Intent intent=getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        TextView textviewCtrl = (TextView) findViewById(R.id.textView2);
        textviewCtrl.append(" "+message);

    }

    public void logout(View view){
        Intent redirect = new Intent(HomeActivity.this,MainActivity.class);
        HomeActivity.this.startActivity(redirect);
    }
}
