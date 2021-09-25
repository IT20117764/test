package com.example.sample2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    Button ch,up;
    ImageView img;
    StorageReference mStorageRef;

    public Uri imguri;
   // private Object DatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStorageRef= FirebaseStorage.getInstance().getReference("Images");

        ch = (Button)findViewById(R.id.btnchoose);
        up = (Button)findViewById(R.id.btnupload);
        img = (ImageView)findViewById(R.id.imgview);
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechooser();

            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fileuploader();
            }
        });

    }


    private String getExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }


    private void Fileuploader()
    {
        StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));

        // Create a reference to "mountains.jpg"

        StorageReference mStorageRef = Ref.child("image/*");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = Ref.child("image/*");

// While the file names are the same, the references point to different files
        mStorageRef.getName().equals(mountainImagesRef.getName());    // true
        mStorageRef.getPath().equals(mountainImagesRef.getPath());    // false
    }

    private void Filechooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data != null && data != null && data.getData()!=null)
        {
            imguri = data.getData();
            img.setImageURI(imguri);

        }

    }





}