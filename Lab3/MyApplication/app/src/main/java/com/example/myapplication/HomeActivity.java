package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.InputStream;

public class HomeActivity extends AppCompatActivity {
   // private GoogleApiClient googleApiClient;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    private Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        Intent intent=getIntent();
        String username = intent.getStringExtra("USER_NAME");
        String birthday = intent.getStringExtra("BIRTHDAY");
        String email = intent.getStringExtra("EMAIL");
        String location = intent.getStringExtra("LOCATION");
        String gender = intent.getStringExtra("GENDER");
        String image_url = intent.getStringExtra("IMAGE_URL");
        TextView textviewCtrl = (TextView) findViewById(R.id.textView2);
        TextView textviewCtrl2 = (TextView) findViewById(R.id.textView3);
        TextView textviewCtrl3 = (TextView) findViewById(R.id.textView4);
        TextView textviewCtrl4 = (TextView) findViewById(R.id.textView5);
        TextView textviewCtrl5 = (TextView) findViewById(R.id.textView6);
        signout = (Button) findViewById(R.id.button3);
        textviewCtrl.append(" "+username);
        textviewCtrl2.append(": "+birthday);
        textviewCtrl3.append(": "+email);
        textviewCtrl4.append(": "+location);
        textviewCtrl5.append(": "+gender);
        if(image_url!=null){
        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(image_url);}
    }

    public void logout(View view){
        if (MainActivity.googleApiClient.isConnected()) {
            MainActivity.googleApiClient.connect();
            signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.signOutFromGoogle();
                }
            });
        }
        LoginManager.getInstance().logOut();
        Intent redirect = new Intent(HomeActivity.this,MainActivity.class);
        HomeActivity.this.startActivity(redirect);
    }

}

final  class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
