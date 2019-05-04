package Lab4.faceapi.ui;//package ganeshannt.lol.faceapi.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import Lab4.faceapi.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void detection(View view) {
        Intent intent = new Intent(this, Lab4.faceapi.ui.DetectionActivity.class);
        startActivity(intent);
    }

}
