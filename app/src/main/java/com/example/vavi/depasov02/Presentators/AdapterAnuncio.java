package com.example.vavi.depasov02.Presentators;
//COMENTAR SI HAY ERROR
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vavi.depasov02.Interfaces.ItemLongClickListener;
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
    String phonedepa;
    String ImagenDetalle;
    String DescDetails;



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


        holder.CallPhoneDirect();

//        holder.GoAnotherActivity();


        final String data_descripcion = Postanuncios.get(position).getDescripcion();
        holder.setDescText(data_descripcion);

        final String data_telefono = Postanuncios.get(position).getTelefono_anuncio();
        holder.setPhoneText(data_telefono);

        String data_precio = Postanuncios.get(position).getPrecio();
        holder.setPrecioText(data_precio);

        String data_modalidad = Postanuncios.get(position).getModalidad();
        holder.setModoText(data_modalidad);

        final String imagen_url = Postanuncios.get(position).getUrl_imagen();
        holder.setImgAnuncio(imagen_url);

        String idusuario = Postanuncios.get(position).getId_usuario() ;


        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                phonedepa = data_telefono;
                ImagenDetalle = imagen_url;
                DescDetails = data_descripcion;
            }
        });


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



    public void getSelectedContextMenuItems(MenuItem item){
            this.openDetailActivity(item.getTitle().toString());
    }

//    OPEN NEW ACTIVITY
    private void openDetailActivity(String choice){
        Intent a = new Intent(context,PictureDetailActivity.class);

        a.putExtra("PHONE_KEY",phonedepa);
        a.putExtra("IMAGEN_KEY",ImagenDetalle);
        a.putExtra("DESCRIPCION_KEY",DescDetails);
        a.putExtra("CHOICE_KEY",choice);

        context.startActivity(a);

    }



    //OBTENER ELEMENTOS DEL LAYOUT ITEMS

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener{

        private View mview;
        private TextView ViewDescripcion;
        private TextView ViewModalidadPago;
        private TextView ViewTelefono;
        private TextView ViewPrecio;
        private ImageView ViewAnuncioImagen;
        private TextView ViewAnuncioFecha;
        private TextView ViewNombreUsuario;
        private CircleImageView ViewFotoUsuario;
        ItemLongClickListener itemLongClickListener;


        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setDescText(String descText){
            ViewDescripcion = mview.findViewById(R.id.anuncio_descripcion);
            ViewDescripcion.setText(descText) ;
        }

        public void setModoText(String modoText){
            ViewModalidadPago = mview.findViewById(R.id.modalidaddepago);
            ViewModalidadPago.setText(modoText) ;
        }

        public void setPhoneText(String celText){
            ViewTelefono = mview.findViewById(R.id.celularanuncio);
            ViewTelefono.setText(celText) ;
        }

        public void setPrecioText(String precioText){
            ViewPrecio = mview.findViewById(R.id.precioanuncio);
            ViewPrecio.setText(precioText) ;
        }

//        public void GoAnotherActivity(){
//            ViewAnuncioImagen = mview.findViewById(R.id.imagen_lista_anuncio);
//            ViewAnuncioImagen.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent goanotheractivity = new Intent(context,PictureDetailActivity.class);
//                    context.startActivity(goanotheractivity);
//
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
////                        Explode explode = new Explode();
////                        explode.setDuration(1000);
////                        activity.getWindow().setEnterTransition(explode);
////                        context.startActivity(goanotheractivity, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v, context.getString(R.string.transitionname_picture)).toBundle());
////                    }else{
////                        context.startActivity(goanotheractivity);
////
////                    }
//                }
//            });
//        }

        public void CallPhoneDirect(){
            ViewTelefono = mview.findViewById(R.id.celularanuncio);
            ViewTelefono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String celular = ViewTelefono.getText().toString();


                    Uri uri = Uri.parse("tel:" + celular);
                    Intent i = new Intent(Intent.ACTION_CALL, uri);
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(i);
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


        public void setItemLongClickListener(ItemLongClickListener ic){
            this.itemLongClickListener = ic;
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemLongClickListener.onLongClick(getLayoutPosition());
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.setHeaderTitle("IR a : ");
            menu.add(0,0,0,"Ver Detalle");
        }
    }



}
