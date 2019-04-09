package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    String usernames[] = new String[5];
    String passwords[] = new String[5];
    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;

    private LinearLayout profile_section;
    private Button signout;
    private SignInButton signin;
    private TextView name, email;
    private ImageView profile_picture;
    public static GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
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
        loginButton = (LoginButton)findViewById(R.id.fb_login_btn);
        textView = (TextView)findViewById(R.id.textView2);
        callbackManager = CallbackManager.Factory.create();
        final String[] name = new String[7];
        signin = (SignInButton)findViewById(R.id.google_login_btn);
        signin.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestScopes(new Scope(Scopes.PLUS_LOGIN)).requestEmail().requestProfile().requestScopes(new Scope(Scopes.PLUS_ME)).requestScopes(new Scope(Scopes.PLUS_LOGIN)).build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
        loginButton.setReadPermissions(Arrays.asList("public_profile,email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    final FacebookRequestError error = response.getError();
                                    if (error != null) {
                                        String message = error.getErrorMessage();
                                        if (message == null) {
                                            message = "Error staging photo.";
                                        }
                                        throw new FacebookGraphResponseException(response, message);
                                    }
                                    final JSONObject data = response.getJSONObject();
                                    if (data == null) {
                                        throw new FacebookException("Error staging photo.");
                                    }
                                    name[0] = data.optString("name");
                                    name[1] = data.optString("birthday");
                                    name[2] = data.optString("email");
                                    final JSONObject location = data.optJSONObject("location");
                                    name[3] = location.optString("name");
                                    name[4] = data.optString("id");
                                    name[5] = "https://graph.facebook.com/" + name[4] + "/picture?width=400&height=400";
                                    name[6] = data.optString("gender");
                                } catch (Exception e) {
                                    Log.v("Login", String.valueOf(e));
                                }
                                Intent redirect = new Intent(MainActivity.this, HomeActivity.class);
                                redirect.putExtra("USER_NAME", name[0]);
                                redirect.putExtra("BIRTHDAY", name[1]);
                                redirect.putExtra("EMAIL", name[2]);
                                redirect.putExtra("LOCATION",name[3]);
                                redirect.putExtra("ID",name[4]);
                                redirect.putExtra("IMAGE_URL",name[5]);
                                redirect.putExtra("GENDER",name[6]);
                                MainActivity.this.startActivity(redirect);

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email,gender,birthday,location");
                request.setParameters(parameters);
                request.executeAsync();

            }




            @Override
            public void onCancel() {
                textView.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode!=REQ_CODE) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
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
                redirect.putExtra("USER_NAME", "Lohitha");
                redirect.putExtra("BIRTHDAY", "21/07/1995");
                redirect.putExtra("EMAIL", "xyz@gmail.com");
                redirect.putExtra("LOCATION","Hyderabad");
                redirect.putExtra("ID", ((String) null));
                redirect.putExtra("IMAGE_URL", ((String) null));
                redirect.putExtra("GENDER","Female");
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.google_login_btn:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    private void handleResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            Uri img_uri = account.getPhotoUrl();
            String acc = account.getAccount().toString();
            String image_url = null;
            if(img_uri!=null)
            {
                image_url = img_uri.toString();
            }
            Log.v("Login google","12");
            Intent redirect = new Intent(MainActivity.this, HomeActivity.class);
            redirect.putExtra("USER_NAME", name);
            redirect.putExtra("BIRTHDAY", "null");
            redirect.putExtra("EMAIL", email);
            redirect.putExtra("LOCATION","null");
            redirect.putExtra("ID","null");
            redirect.putExtra("IMAGE_URL",image_url);
            redirect.putExtra("GENDER","null");
            MainActivity.this.startActivity(redirect);
        }
    }
    public static void signOutFromGoogle() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        return;
                    }
                });
    }
}
