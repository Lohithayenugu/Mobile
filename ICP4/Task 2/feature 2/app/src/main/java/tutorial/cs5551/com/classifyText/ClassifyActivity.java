package tutorial.cs5551.com.classifyText;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.EditText;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tutorial.cs5551.com.translateapp.R;
import android.speech.RecognizerIntent;
import java.util.Locale;
import android.content.ActivityNotFoundException;
import android.widget.Toast;


public class ClassifyActivity extends AppCompatActivity {
    String sourceText;
    TextView outputTextView;
    TextView outputTextView1;
    Context mContext;
    private TextView Text;

    protected static final int RESULT_SPEECH = 1;

    private ImageButton btnSpeak;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        outputTextView = (TextView) findViewById(R.id.txt_Result);
        outputTextView1 = (TextView) findViewById(R.id.txt_Result1);

        Text = (TextView) findViewById(R.id.Text);

        editText = (EditText)findViewById(R.id.txt_Email);

        btnSpeak = (ImageButton) findViewById(R.id.mic);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    Text.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void logout(View v) {
        Intent redirect = new Intent(ClassifyActivity.this, LoginActivity.class);
        startActivity(redirect);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    //Text.setText(text.get(0));
                    editText.setText(text.get(0));
                }
                break;
            }

        }
    }

    public void classifyText(View v) {
        TextView sourceTextView = (TextView) findViewById(R.id.txt_Email);

        sourceText = sourceTextView.getText().toString();
        String getURL = "https://api.uclassify.com/v1/uClassify/Sentiment/classify/?readKey=9QRZ6ZWbVRpH&text="+sourceText;
        final String response1 = "";
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(getURL)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {
                        jsonResult = new JSONObject(result);
                        final String convertedText = jsonResult.getString("positive");
                        final String convertedText1 = jsonResult.getString("negative");

                        Log.d("okHttp", jsonResult.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                float a;
                                a=Float.parseFloat(convertedText);
                                a=a*100;
                                outputTextView.setText("Positive: "+String.valueOf(a)+"%");
                                a=Float.parseFloat(convertedText1);
                                a=a*100;
                                outputTextView1.setText("Negative: "+String.valueOf(a)+"%");
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception ex) {
            outputTextView.setText(ex.getMessage());

        }

    }
}
