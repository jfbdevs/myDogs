package com.example.android.mydogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class selectImage extends Activity {

    private final int REQUEST_CAMERA = 0;
    private final int PICK_IMAGE_REQUEST = 1;
    private ImageView ivImage;
    private String userChosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        Button btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void setImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(selectImage.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(selectImage.this);

                if (items[item].equals("Take Photo")) {
                    userChosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),PICK_IMAGE_REQUEST);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST)

                try {
                    onSelectFromGalleryResult(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
            else if (requestCode == REQUEST_CAMERA){
                onCaptureImageResult(data);
        }
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert thumbnail != null;
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        String folder_main = "myDogs2";
        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }

        File destination = new File(Environment.getExternalStorageDirectory()+"/myDogs2/",
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }


    private void onSelectFromGalleryResult(Intent data) throws IOException {
        OutputStream outStream = null;
        Uri uri = data.getData();
        String folder_main = "myDogs2";
        File f = new File(Environment.getExternalStorageDirectory(), folder_main);

        final String TAG = "MyActivity";
        Log.d(TAG, String.valueOf(f));
        if (!f.exists()) {
            f.mkdirs();
        }
        File destination = new File(Environment.getExternalStorageDirectory()+"/myDogs2/",
                System.currentTimeMillis() + ".jpg");
        Log.d(TAG, "destination: "+String.valueOf(destination));
        TextView textView = (TextView) findViewById(R.id.textView);

      //  File source=new File(uri.getPath());
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            //scale
            int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
            // Log.d(TAG, String.valueOf(bitmap));
            // save to external directory for retrieve
            outStream = new FileOutputStream(destination);
            scaled.compress(Bitmap.CompressFormat.PNG, 90, outStream);

            outStream.flush();
            outStream.close();
            textView.setText("destination:"+String.valueOf(destination)+" source:"+String.valueOf(uri));
           // ImageView imageView = (ImageView) findViewById(R.id.ivImage);
            ivImage.setImageBitmap(scaled);

            // save the image path in the preferences file
            SharedPreferences prefs = this.getSharedPreferences(
                    "com.example.MyDogs", Context.MODE_PRIVATE);

            // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("image",String.valueOf(destination));
            editor.apply();



        } catch (IOException e) {
            e.printStackTrace();
        }



    }




}

