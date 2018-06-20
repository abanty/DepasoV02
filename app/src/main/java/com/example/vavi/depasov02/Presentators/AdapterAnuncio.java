package com.example.vavi.depasov02.Presentators;
//COMENTAR SI HAY ERROR
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    String LongDescDetails;
    String Titleproductdetail;
    String priceproducto;
    String Modalidad;
    String Location;


    private FirebaseFirestore firebaseFirestore;

    public AdapterAnuncio(List<AnuncioModel> Postanuncios){
        this.Postanuncios = Postanuncios;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent,false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


//      holder.CallPhoneDirect();

//      holder.GoAnotherActivity();
        final String data_titulo = Postanuncios.get(position).getTitulo_anuncio();
        holder.setTitleText(data_titulo);

        final String data_descripcion = Postanuncios.get(position).getDescripcion();
        holder.setDescText(data_descripcion);

        final String data_descripcion_larga = Postanuncios.get(position).getDescripcion_larga();
        holder.setLongDescText(data_descripcion_larga);

        final String data_precio = Postanuncios.get(position).getPrecio();
        holder.setPrecioText(data_precio);

        final String data_phone = Postanuncios.get(position).getTelefono_anuncio();
        holder.setPhoneText(data_phone);

        final String data_paymode = Postanuncios.get(position).getModalidad();
        holder.setpaymodeText(data_paymode);

        final String imagen_url = Postanuncios.get(position).getUrl_imagen();
        holder.setImgAnuncio(imagen_url);

        String idusuario = Postanuncios.get(position).getId_usuario() ;

        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                Titleproductdetail = data_titulo;
                ImagenDetalle = imagen_url;
                LongDescDetails = data_descripcion_larga;
                phonedepa = data_phone;
                priceproducto = data_precio;
                Modalidad = data_paymode;

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

        a.putExtra("TITLE_KEY",Titleproductdetail);
        a.putExtra("IMAGEN_KEY",ImagenDetalle);
        a.putExtra("DESCRIPCION_LARGO_KEY",LongDescDetails);
        a.putExtra("PRICE_KEY",priceproducto);
        a.putExtra("PHONE_KEY",phonedepa);
        a.putExtra("PAYMODE_KEY",Modalidad);
        a.putExtra("CHOICE_KEY",choice);

        context.startActivity(a);

    }



    //OBTENER ELEMENTOS DEL LAYOUT ITEMS

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener{

        private View mview;
        private TextView ViewTitulo;
        private TextView ViewDescripcion;
        private TextView ViewDescripcionlarga;
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

        public void setTitleText(String titleText){
            ViewTitulo = mview.findViewById(R.id.title_description);
            ViewTitulo.setText(titleText) ;
        }


        public void setDescText(String descText){
            ViewDescripcion = mview.findViewById(R.id.cardview_short_description);
            ViewDescripcion.setText(descText) ;
        }

        public void setLongDescText(String longdescText){
            ViewDescripcionlarga = mview.findViewById(R.id.longdesc);
            ViewDescripcionlarga.setText(longdescText);
        }


        public void setPrecioText(String precioText){
            ViewPrecio = mview.findViewById(R.id.price_ad);
            ViewPrecio.setText(precioText) ;
        }

        public void setPhoneText(String phoneText){
            ViewTelefono = mview.findViewById(R.id.phone);
            ViewTelefono.setText(phoneText) ;
        }


        public void setpaymodeText(String paymodeText){
            ViewModalidadPago = mview.findViewById(R.id.modo);
            ViewModalidadPago.setText(paymodeText) ;
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

//        public void CallPhoneDirect(){
//            ViewTelefono = mview.findViewById(R.id.celularanuncio);
//            ViewTelefono.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String celular = ViewTelefono.getText().toString();
//
//
//                    Uri uri = Uri.parse("tel:" + celular);
//                    Intent i = new Intent(Intent.ACTION_CALL, uri);
//                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    context.startActivity(i);
//                }
//            });
//        }


        public void setImgAnuncio(String downloadUri){
            ViewAnuncioImagen = mview.findViewById(R.id.cardview_image);
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
