package com.example.vavi.depasov02.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vavi.depasov02.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class PictureDetailActivity extends AppCompatActivity {
    private TextView detalleanuncio;
    private TextView telefonodepa;
    private ImageView imagenDetalle;
    private String user_id;
    FirebaseFirestore firebaseFirestore ;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        showToolbar("",true);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            getWindow().setEnterTransition(new Fade());
//        }
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        user_id = firebaseAuth.getCurrentUser().getUid(); //para validate id_usuario
        telefonodepa = findViewById(R.id.phonedepa);
        detalleanuncio = findViewById(R.id.userNameDetail);
        imagenDetalle = findViewById(R.id.imageHeader);

        Intent i = getIntent();
        String celulardepa = i.getExtras().getString("PHONE_KEY");
        String imagenurl = i.getExtras().getString("IMAGEN_KEY");
        String detalle = i.getExtras().getString("DESCRIPCION_KEY");
        String choice = i.getExtras().getString("CHOICE_KEY");





        detalleanuncio.setText(detalle);
        telefonodepa.setText(celulardepa);
        Glide.with(PictureDetailActivity.this).load(imagenurl).into(imagenDetalle);


        telefonodepa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String celularcall = telefonodepa.getText().toString();
                Uri uri = Uri.parse("tel:" + celularcall);
                Intent i = new Intent(Intent.ACTION_CALL, uri);
                if (ContextCompat.checkSelfPermission(PictureDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(i);
            }
        });



        Toast.makeText(this, choice, Toast.LENGTH_SHORT).show();

//        firebaseFirestore.collection("Anuncios").document("GsxTxyoCk3cyUdNHOt2C").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                if (task.isSuccessful()){
//
//                    if (task.getResult().exists()){
//                        String name = task.getResult().getString("descripcion");
////                        String image = task.getResult().getString("imagen");
//
////                        mypersonalimagenUri = Uri.parse(image);
//                        detalleanuncio.setText(name);
////                        RequestOptions placeholderRequest = new RequestOptions();
////                        placeholderRequest.placeholder(R.drawable.usercircle);
////                        Glide.with(SetupActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(setupimage);
//
//                    }
//
//                }else{
////                    String error = task.getException().getMessage();
////                    Toast.makeText(PictureDetailActivity.this, "FIRESTORE Error: " + error, Toast.LENGTH_SHORT).show();
//
//                }
//
////                setup_progressbar.setVisibility(View.GONE);
////                setupbtn.setEnabled(true);
//            }
//        });


   }


    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
    }
}
