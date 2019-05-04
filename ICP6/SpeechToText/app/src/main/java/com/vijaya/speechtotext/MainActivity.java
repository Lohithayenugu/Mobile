package com.vijaya.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    private final int CHECK_CODE = 0x1;
    private final int LONG_DURATION = 5000;
    private Speaker speaker ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        checkTTS();

    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void checkTTS() {
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    private void initializeSMSReceiver() {

                        String text = "Hello";
                        speaker.pause(LONG_DURATION);
                        speaker.speak(text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                    if(result.get(0).toString().toLowerCase().contains("not feeling good"))
                    {
                        speaker.allow(true);
                        speaker.speak("I can understand");
                    }
                    if(result.get(0).toString().toLowerCase().contains("medicine should i take"))
                    {
                        speaker.allow(true);
                        speaker.speak("I think you have fever. Please take medicine");
                    }
                    else if(result.get(0).toString().toLowerCase().contains("thank you my medical assistant"))
                    {
                        speaker.allow(true);
                        speaker.speak("Thank you to Doctors");
                    }
                }
                break;
            }
            case CHECK_CODE: {
                if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    Log.d("err","1");
                    speaker = new Speaker(this);
                    Log.d("err","3");
                    speaker.allow(true);
                    speaker.pause(LONG_DURATION);
                    speaker.speak("hello");
                } else {
                    Log.d("err","2");
                    Intent install = new Intent();
                    install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(install);
                    Log.d("err","3");
                    speaker.allow(true);
                    speaker.pause(LONG_DURATION);
                    speaker.speak("hello");
                }
                break;
            }
        }
    }
}