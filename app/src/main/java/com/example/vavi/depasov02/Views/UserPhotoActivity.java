package com.example.vavi.depasov02.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vavi.depasov02.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserPhotoActivity extends AppCompatActivity {

    private CircleImageView setupimage;
    private Uri mypersonalimagenUri = null;
    private Toolbar tolbaruser;
    private String user_id;
    private boolean Cambiado = false;

    //ELEMENTS XML
    private EditText setupName;
    private Button setupbtn;
    private ProgressBar setup_progressbar;

    //SERVICES FIREBASE
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_userphoto);
        showToolbar(getResources().getString(R.string.toolbar_tittle_optionuser), true);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid(); //para validate id_usuario
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        setupimage = findViewById(R.id.usercircle);
        setupName =  findViewById(R.id.setup_name);
        setupbtn =  findViewById(R.id.btn_setup);
        setup_progressbar = findViewById(R.id.setup_progress);

        setup_progressbar.setVisibility(View.VISIBLE);
        setupbtn.setEnabled(false);

        firebaseFirestore.collection("Usuarios").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){
                        String name = task.getResult().getString("nombre");
                        String image = task.getResult().getString("imagen");

                        mypersonalimagenUri = Uri.parse(image);
                        setupName.setText(name);
                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.usercircle);
                        Glide.with(UserPhotoActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(setupimage);

                    }

                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(UserPhotoActivity.this, "FIRESTORE Error: " + error, Toast.LENGTH_SHORT).show();

                }

                setup_progressbar.setVisibility(View.GONE);
                setupbtn.setEnabled(true);
            }
        });


        setupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String user_name = setupName.getText().toString();

                if (!TextUtils.isEmpty(user_name) && mypersonalimagenUri != null) {

                setup_progressbar.setVisibility(View.VISIBLE);

                if (Cambiado) {

                        user_id = firebaseAuth.getCurrentUser().getUid();

                        StorageReference image_patch = storageReference.child("perfil_imagenes").child(user_id + ".jpg");
                        image_patch.putFile(mypersonalimagenUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if (task.isSuccessful()) {

                                    storeFirestore(task, user_name);

                                } else {

                                    String error = task.getException().getMessage();
                                    Toast.makeText(UserPhotoActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();

                                    setup_progressbar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }else{

                    storeFirestore(null,user_name);

                  }

                }
            }
        });

        setupimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(UserPhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(UserPhotoActivity.this, "Permisos Denegados", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(UserPhotoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                    }else{

                        BringImagePicker();
                    }
                }else{

                    BringImagePicker();
                }
            }
        });
    }

    private void storeFirestore(@NonNull Task<UploadTask.TaskSnapshot> task, String user_name) {

        Uri download_url;

        if (task != null){
            download_url = task.getResult().getDownloadUrl();

        }else{
            download_url = mypersonalimagenUri;

        }

        Map<String, String> userMap = new HashMap<>();
        userMap.put("nombre",user_name);
        userMap.put("imagen",download_url.toString());

        firebaseFirestore.collection("Usuarios").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(UserPhotoActivity.this, "Configuracion Actualizada", Toast.LENGTH_SHORT).show();
                    Intent MainIntent = new Intent(UserPhotoActivity.this, MainActivity.class);
                    startActivity(MainIntent);
                    finish();

                }else{
                String error = task.getException().getMessage();
                Toast.makeText(UserPhotoActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
             }
            }
        });
    }



    private void BringImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(UserPhotoActivity.this);
    }

    private void showToolbar(String title, boolean upButton) {

        tolbaruser = (Toolbar) findViewById(R.id.setuptolbar);
        setSupportActionBar(tolbaruser);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().getThemedContext();
        tolbaruser.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_atras)); //Marca ERROR ADVERTENCIA
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mypersonalimagenUri = result.getUri();
                setupimage.setImageURI(mypersonalimagenUri);

                Cambiado = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }
}
