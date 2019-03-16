package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String usernames[] = new String[5];
    String passwords[] = new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textviewCtrl = (TextView) findViewById(R.id.textView);
        textviewCtrl.setTextColor(Color.RED);
        textviewCtrl.setText("");
        usernames[0]="lohitha";
        passwords[0]="lohitha";
        usernames[1]="admin";
        passwords[1]="admin";
        usernames[2]="ramya";
        passwords[2]="ramya";
        usernames[3]="aparna";
        passwords[3]="aparna";
        usernames[4]="xyz";
        passwords[4]="xyz";

    }

    public  void validate(View view){


       EditText usernameCtrl = (EditText) findViewById(R.id.editText3);
        EditText passwordCtrl = (EditText) findViewById(R.id.editText4);

        String username = usernameCtrl.getText().toString().toLowerCase();
        String password = passwordCtrl.getText().toString();
        int i;
for(i=0;i<usernames.length;i++) {
    if ( username.equals(usernames[i]) && password.equals(passwords[i])) {
        Intent redirect = new Intent(MainActivity.this, HomeActivity.class);
        redirect.putExtra("EXTRA_MESSAGE", usernames[i].toUpperCase());
        MainActivity.this.startActivity(redirect);
        break;
    }
}

           // textView
        if(i==usernames.length) {
            TextView textviewCtrl = (TextView) findViewById(R.id.textView);
            textviewCtrl.setTextColor(Color.RED);
            textviewCtrl.setText("Invalid Credentials");
        }


    }

    }
