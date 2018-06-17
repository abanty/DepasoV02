package com.example.vavi.depasov02.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vavi.depasov02.R;
//import com.example.vavi.depasov02.Views.Fragments.InicioFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class AnunciosActivity extends AppCompatActivity {

    private Toolbar nuevoanunciotoolbar;
//    private ImageView nuevaimagen;
//    private EditText edtdescripcion;
//    private Button btnaddanuncio;
//    private Uri nuevaimagenUri = null;
//    private ProgressBar anuncioprogressbar;
//    private StorageReference storageReference;
//    private FirebaseFirestore firebaseFirestore;
//    private FirebaseAuth firebaseAuth;
//
//    private String current_user_id;
//    private Bitmap comprimirArchivoImagen;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        showToolbar(getResources().getString(R.string.toolbar_tittle_post), true);
//
//        storageReference = FirebaseStorage.getInstance().getReference();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//
//         current_user_id = firebaseAuth.getCurrentUser().getUid();
//
//
//        nuevaimagen = findViewById(R.id.anuncio_imagen);
//        edtdescripcion = findViewById(R.id.anuncio_descripcion);
//        btnaddanuncio = findViewById(R.id.anuncio_boton);
//        anuncioprogressbar = findViewById(R.id.anuncio_progressbar);
//
//        nuevaimagen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setMinCropResultSize(512,512)
//                        .setAspectRatio(1,1)
//                        .start(AnunciosActivity.this);
//
//            }
//        });
//
//        btnaddanuncio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final String desc = edtdescripcion.getText().toString();
//
//                if (!TextUtils.isEmpty(desc)&& nuevaimagenUri!= null){
//
//                    anuncioprogressbar.setVisibility(View.VISIBLE);
//
//                    final String nombrealeatorio = UUID.randomUUID().toString();
//                    StorageReference ArchivoImagen = storageReference.child("Anuncio_inmuebles").child(nombrealeatorio + ".jpg");
//                    ArchivoImagen.putFile(nuevaimagenUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
//
//                            final String descargaUri = task.getResult().getDownloadUrl().toString();
//
//                            if (task.isSuccessful()){
//
//                                File nuevoarchivoimagen = new File(nuevaimagenUri.getPath());
//
//                                try {
//                                    comprimirArchivoImagen = new Compressor(AnunciosActivity.this)
//                                            .setMaxHeight(100)
//                                            .setMaxWidth(100)
//                                            .setQuality(2)
//                                            .compressToBitmap(nuevoarchivoimagen);
//
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                                comprimirArchivoImagen.compress(Bitmap.CompressFormat.JPEG,100, baos);
//                                byte[] data = baos.toByteArray();
//
//                                UploadTask uploadTask = storageReference.child("Anuncio_inmuebles/renderizados")
//                                        .child(nombrealeatorio+".jpg").putBytes(data);
//
//                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                        String downloadthumbUri = taskSnapshot.getDownloadUrl().toString();
//
//                                        Map<String, Object> anuncioMap = new HashMap<>();
//                                        anuncioMap.put("url_imagen",descargaUri);
//                                        anuncioMap.put("renderizados",downloadthumbUri);
//                                        anuncioMap.put("descripcion",desc);
//                                        anuncioMap.put("id_usuario", current_user_id);
//                                        anuncioMap.put("tiempo_marcado",FieldValue.serverTimestamp());
//
//
//                                        firebaseFirestore.collection("Anuncios").add(anuncioMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                                                if (task.isSuccessful()){
//
//                                                    Toast.makeText(AnunciosActivity.this, "Anuncio fue agregado", Toast.LENGTH_SHORT).show();
//                                                    Intent nuevointenanuncio = new Intent(AnunciosActivity.this, MainActivity.class);
//                                                    startActivity(nuevointenanuncio);
//                                                    finish();
//
//                                                }else{
//
//                                                }
//                                                anuncioprogressbar.setVisibility(View.INVISIBLE);
//                                            }
//                                        });
//
//
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//
//                                        //POSIBLES ERRORES
//
//                                    }
//                                });
//
//
//
//
//
//                            }else{
//
//                                anuncioprogressbar.setVisibility(View.INVISIBLE);
//
//                            }
//
//                        }
//                    });
//
//                }
//
//            }
//        });
//
//
//
//
//
    }

    private void showToolbar(String title, boolean upButton) {

        Toolbar toolbar = findViewById(R.id.nuevo_anuncio_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().getThemedContext();
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_atras)); //Marca ERROR ADVERTENCIA
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
//                nuevaimagenUri = result.getUri();
//                nuevaimagen.setImageURI(nuevaimagenUri);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//
//                Exception error = result.getError();
//
//            }
//        }
//    }
//
}
