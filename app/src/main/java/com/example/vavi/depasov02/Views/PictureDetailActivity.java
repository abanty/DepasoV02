package com.example.vavi.depasov02.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vavi.depasov02.R;

public class PictureDetailActivity extends AppCompatActivity {
    private TextView titulodepa, detallelargoanuncio,telefonodepa,preciodepa,modopagodepa;
    private ImageView imagenDetalle;





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
        imagenDetalle = findViewById(R.id.imageHeader);
        titulodepa = findViewById(R.id.titledetail);
        detallelargoanuncio = findViewById(R.id.longdetailspicture);
        telefonodepa = findViewById(R.id.phonedepa);
        preciodepa = findViewById(R.id.pricedetailpicture);
        modopagodepa = findViewById(R.id.modopago);


        Intent i = getIntent();
        String imagenurl = i.getExtras().getString("IMAGEN_KEY");
        String titulodetalle = i.getExtras().getString("TITLE_KEY");
        String detallelargo = i.getExtras().getString("DESCRIPCION_LARGO_KEY");
        String preciodetalle = i.getExtras().getString("PRICE_KEY");
        String celulardepa = i.getExtras().getString("PHONE_KEY");
        String modopago = i.getExtras().getString("PAYMODE_KEY");
        String choice = i.getExtras().getString("CHOICE_KEY");





        detallelargoanuncio.setText(detallelargo);
        telefonodepa.setText(celulardepa);
        titulodepa.setText(titulodetalle);
        preciodepa.setText(preciodetalle);
        modopagodepa.setText(modopago);
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
//                        detallelargoanuncio.setText(name);
////                        RequestOptions placeholderRequest = new RequestOptions();
////                        placeholderRequest.placeholder(R.drawable.usercircle);
////                        Glide.with(UserPhotoActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(setupimage);
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
