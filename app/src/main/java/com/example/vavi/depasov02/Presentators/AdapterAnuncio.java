package com.example.vavi.depasov02.Presentators;
//COMENTAR SI HAY ERROR
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vavi.depasov02.Models.AnuncioModel;
import com.example.vavi.depasov02.R;
import com.example.vavi.depasov02.Views.PictureDetailActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterAnuncio extends RecyclerView.Adapter<AdapterAnuncio.ViewHolder> {

    public List<AnuncioModel> Postanuncios;
    public Context context;


    private FirebaseFirestore firebaseFirestore;

    public AdapterAnuncio(List<AnuncioModel> Postanuncios){
        this.Postanuncios = Postanuncios;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_anuncios_item, parent,false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        holder.GoAnotherActivity();

        String data_descripcion = Postanuncios.get(position).getDescripcion();
        holder.setDescText(data_descripcion);

        String imagen_url = Postanuncios.get(position).getUrl_imagen();
        holder.setImgAnuncio(imagen_url);

        String idusuario = Postanuncios.get(position).getId_usuario() ;


            //Informacion de usuario Aqui
            firebaseFirestore.collection("Usuarios").document(idusuario).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                        if (task.isSuccessful()) {

                            String ImagenUsuario = task.getResult().getString("imagen");
                            String NombreUsuario = task.getResult().getString("nombre");


                            holder.setInfoUsuario(NombreUsuario, ImagenUsuario);

                        }
                    }

            });

        long milisegundo = Postanuncios.get(position).getTiempo_marcado().getTime();
        String tiempo_fecha = DateFormat.format("MM/dd/yyyy", new Date(milisegundo)).toString();

        holder.setTiempo(tiempo_fecha);


//        holder.ViewAnuncioImagen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity, PictureDetailActivity.class);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                    Explode explode = new Explode();
//                    explode.setDuration(1000);
//                    activity.getWindow().setEnterTransition(explode);
//                    activity.startActivity(intent,
//                            ActivityOptionsCompat.makeSceneTransitionAnimation
//                                    (activity, view, activity.getString(R.string.transitionname_picture)).toBundle());
//                }else{
//                    activity.startActivity(intent);
//                }
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return Postanuncios.size() ;
    }


    //OBTENER ELEMENTOS DEL LAYOUT ITEMS

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mview;
        private TextView ViewDescripcion;
        private ImageView ViewAnuncioImagen;
        private TextView ViewAnuncioFecha;
        private TextView ViewNombreUsuario;
        private CircleImageView ViewFotoUsuario;


        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
//            ViewAnuncioImagen = itemView.findViewById(R.id.imagen_lista_anuncio);
        }


        public void setDescText(String descText){
            ViewDescripcion = mview.findViewById(R.id.anuncio_descripcion);
            ViewDescripcion.setText(descText) ;
        }


        public void GoAnotherActivity(){
            ViewAnuncioImagen = mview.findViewById(R.id.imagen_lista_anuncio);
            ViewAnuncioImagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goanotheractivity = new Intent(context,PictureDetailActivity.class);
                    context.startActivity(goanotheractivity);

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                        Explode explode = new Explode();
//                        explode.setDuration(1000);
//                        activity.getWindow().setEnterTransition(explode);
//                        context.startActivity(goanotheractivity, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v, context.getString(R.string.transitionname_picture)).toBundle());
//                    }else{
//                        context.startActivity(goanotheractivity);
//
//                    }
                }
            });
        }


        public void setImgAnuncio(String downloadUri){
            ViewAnuncioImagen = mview.findViewById(R.id.imagen_lista_anuncio);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image_placeholder);

            Glide.with(context).load(downloadUri).into(ViewAnuncioImagen);
        }



        public void setTiempo(String fecha){
            ViewAnuncioFecha = mview.findViewById(R.id.fecha_anuncio);
            ViewAnuncioFecha.setText(fecha);
        }

        public void setInfoUsuario(String nombreuser, String fotousuario){

            ViewFotoUsuario = mview.findViewById(R.id.anuncio_usuario_imagen);
            ViewNombreUsuario = mview.findViewById(R.id.anuncio_usuario_nombre);


            ViewNombreUsuario.setText(nombreuser);

            RequestOptions placeholderOptions = new RequestOptions();
            placeholderOptions.placeholder(R.drawable.profile_placeholder);

            Glide.with(context).applyDefaultRequestOptions(placeholderOptions).load(fotousuario).into(ViewFotoUsuario);



        }



    }



}
