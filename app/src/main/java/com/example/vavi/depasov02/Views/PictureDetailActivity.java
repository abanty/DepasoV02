package com.example.vavi.depasov02.Views;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.widget.TextView;
import android.widget.Toast;

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
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid(); //para validate id_usuario
        detalleanuncio = findViewById(R.id.userNameDetail);


        firebaseFirestore.collection("Anuncios").document("GsxTxyoCk3cyUdNHOt2C").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){
                        String name = task.getResult().getString("descripcion");
//                        String image = task.getResult().getString("imagen");

//                        mypersonalimagenUri = Uri.parse(image);
                        detalleanuncio.setText(name);
//                        RequestOptions placeholderRequest = new RequestOptions();
//                        placeholderRequest.placeholder(R.drawable.usercircle);
//                        Glide.with(SetupActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(setupimage);

                    }

                }else{
//                    String error = task.getException().getMessage();
//                    Toast.makeText(PictureDetailActivity.this, "FIRESTORE Error: " + error, Toast.LENGTH_SHORT).show();

                }

//                setup_progressbar.setVisibility(View.GONE);
//                setupbtn.setEnabled(true);
            }
        });








    }


    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
    }
}
