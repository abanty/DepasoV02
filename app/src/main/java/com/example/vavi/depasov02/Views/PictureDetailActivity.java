package com.example.vavi.depasov02.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.vavi.depasov02.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PictureDetailActivity extends AppCompatActivity {
    private TextView titulodepa, detallelargoanuncio,telefonodepa,preciodepa,modopagodepa,/*DATOS USUARIO*/username,date,prueba;
    private ImageView imagenDetalle;
    private CircleImageView imagenprofile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        showToolbar("",true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }

        prueba = findViewById(R.id.imgtest);
        date = findViewById(R.id.fecha_anuncio);
        username = findViewById(R.id.anuncio_usuario_nombre);
        imagenprofile = findViewById(R.id.img_user);
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
        String fechahora = i.getExtras().getString("DATE_KEY");
        String usuarionombre = i.getExtras().getString("IMGPRO_KEY");
        String imagenprofileurl = i.getExtras().getString("NAMEUSER_KEY");

        String choice = i.getExtras().getString("CHOICE_KEY");


//        RequestOptions placeholderOptions = new RequestOptions();
//        placeholderOptions.placeholder(R.drawable.profile_placeholder);
        Glide.with(PictureDetailActivity.this).load(imagenprofileurl).into(imagenprofile);
        date.setText(fechahora);
        username.setText(usuarionombre);
        detallelargoanuncio.setText(detallelargo);
        telefonodepa.setText(celulardepa);
        titulodepa.setText(titulodetalle);
        preciodepa.setText(preciodetalle);
        modopagodepa.setText(modopago);

//        prueba.setText(imagenprofileurl);
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


//        Toast.makeText(this, choice, Toast.LENGTH_SHORT).show();

   }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
    }
}
