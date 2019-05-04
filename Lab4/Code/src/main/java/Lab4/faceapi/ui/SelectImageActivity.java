package Lab4.faceapi.ui;//package ganeshannt.lol.faceapi.ui;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import Lab4.faceapi.R;

// The activity for the user to select a image and to detect faces in the image.
public class SelectImageActivity extends AppCompatActivity {
    // Flag to indicate the request of the next task to be performed
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 1;
    private ImageView image;
    // The URI of photo taken with camera
    private Uri mUriPhotoTaken;
    File file = null;
    // When the activity is created, set all the member variables to initial state.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = (ImageView) findViewById(R.id.image);
        setContentView(R.layout.activity_select_image);
    }

    // Save the activity state when it's going to stop.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ImageUri", mUriPhotoTaken);
    }

    // Recover the saved state when the activity is recreated.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mUriPhotoTaken = savedInstanceState.getParcelable("ImageUri");
    }

    // Deal with the result of selection of the photos and faces.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri imageUri;
        Intent intent = new Intent();
        Log.d("err1",requestCode+"");
        Log.d("err2",resultCode+"");
        switch (requestCode)
        {
            case REQUEST_TAKE_PHOTO:
                imageUri = data.getData();
                Log.d("err1", String.valueOf(imageUri));
                if (resultCode == RESULT_OK) {

                    if (data == null || data.getData() == null) {
                        imageUri = mUriPhotoTaken;
                    } else {
                        imageUri = data.getData();
                    }

                    intent.setData(imageUri);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            case REQUEST_SELECT_IMAGE_IN_ALBUM:
                if (resultCode == RESULT_OK) {

                    if (data == null || data.getData() == null) {
                        imageUri = mUriPhotoTaken;
                    } else {
                        imageUri = data.getData();
                    }

                    intent.setData(imageUri);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    // When the button of "Take a Photo with Camera" is pressed.
    public void takePhoto(View view) {
       try {
            Log.d("err", "1");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Log.d("err", "2");

            if (intent.resolveActivity(getPackageManager()) != null) {
                // Save the photo taken to a temporary file.
                Log.d("err", "3");
                try {
                    Log.d("err", "4");
                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    Log.d("err", "5");
                     file = File.createTempFile("IMG_", ".jpg", storageDir);
                   // mUriPhotoTaken = Uri.fromFile(file);
                    Log.d("err", "6");
                    mUriPhotoTaken = FileProvider.getUriForFile(SelectImageActivity.this, "Lab4.faceapi.fileprovider", file);
                    Log.d("err", "7");
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);
                    Log.d("err", "8");
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    Log.d("err", "9");
                } catch (IOException e) {
                    Log.d("err", e.toString());
                    setInfo(e.getMessage());
                }
            }
        }
        catch(Exception e)
        {
            Log.d("err", e.toString());
        }
    }

    // When the button of "Select a Photo in Album" is pressed.
    public void selectImageInAlbum(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM);
        }
    }

    // Set the information panel on screen.
    private void setInfo(String info) {
        TextView textView = (TextView) findViewById(R.id.info);
        textView.setText(info);
    }
}
