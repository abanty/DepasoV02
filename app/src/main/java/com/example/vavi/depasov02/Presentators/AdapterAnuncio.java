package com.example.vavi.depasov02.Presentators;
//COMENTAR SI HAY ERROR
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.transition.Explode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vavi.depasov02.Interfaces.ItemClickListener;
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

import static com.example.vavi.depasov02.R.string.transitionname_picture;


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

    /*UID USUARIOS DATOS:*/
    String nameuser;
    String date;
    String imagenprofileuser;


    private FirebaseFirestore firebaseFirestore;
    private View view;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

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

        final String idusuario = Postanuncios.get(position).getId_usuario() ;
        
        long milisegundo = Postanuncios.get(position).getTiempo_marcado().getTime();
        final String tiempo_fecha = DateFormat.format("MM/dd/yyyy", new Date(milisegundo)).toString();
        holder.setTiempo(tiempo_fecha);

//        firebaseFirestore.collection("Usuarios").document(idusuario).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    String ImagenUsuario;
//                    String NombreUsuario;
//                    ImagenUsuario= task.getResult().getString("imagen");
//                    NombreUsuario = task.getResult().getString("nombre");
//                    holder.setInfoUsuario(NombreUsuario, ImagenUsuario);//
//                }
//            }
//        });

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
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
////                        Explode explode = new Explode();
////                        explode.setDuration(1000);
////                        activity.getWindow().setEnterTransition(explode);
////                        context.startActivity(goanotheractivity, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v, context.getString(R.string.transitionname_picture)).toBundle());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(final int pos, final View v) {

                firebaseFirestore.collection("Usuarios").document(idusuario).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            final Intent a = new Intent(context, PictureDetailActivity.class);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                        Explode explode = new Explode();
                                        explode.setDuration(1000);
                                        ((Activity) context).getWindow().setEnterTransition(explode);

                                        String ImagenUsuario;
                                        String NombreUsuario;

                                        ImagenUsuario = task.getResult().getString("imagen");
                                        NombreUsuario = task.getResult().getString("nombre");
//                            holder.setInfoUsuario(NombreUsuario, ImagenUsuario);//.

                                        Titleproductdetail = data_titulo;
                                        nameuser = ImagenUsuario;
                                        imagenprofileuser = NombreUsuario;
                                        ImagenDetalle = imagen_url;
                                        LongDescDetails = data_descripcion_larga;
                                        phonedepa = data_phone;
                                        priceproducto = data_precio;
                                        Modalidad = data_paymode;
                                        /*Datos usuario*/
                                        date = tiempo_fecha;


                                        a.putExtra("TITLE_KEY", Titleproductdetail);
                                        a.putExtra("IMAGEN_KEY", ImagenDetalle);
                                        a.putExtra("DESCRIPCION_LARGO_KEY", LongDescDetails);
                                        a.putExtra("PRICE_KEY", priceproducto);
                                        a.putExtra("PHONE_KEY", phonedepa);
                                        a.putExtra("PAYMODE_KEY", Modalidad);
                                        a.putExtra("DATE_KEY", date);
                                        a.putExtra("NAMEUSER_KEY", nameuser);
                                        a.putExtra("IMGPRO_KEY", imagenprofileuser);

                                context.startActivity(a,ActivityOptionsCompat.makeSceneTransitionAnimation
                                        ((Activity) context,v,context.getString(R.string.transitionname_picture)).toBundle());


//                                context.startActivity(a);
//                                        Toast.makeText(context, "aqui es A", Toast.LENGTH_SHORT).show();


                            } else {


//                                Toast.makeText(context, "aqui es B", Toast.LENGTH_SHORT).show();

                                context.startActivity(a);

                            }
                        }
                    }
                });
            }
        });

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

    /*OPEN NEW ACTIVITY*/
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

        private View mview;
        private CheckBox Viewaddfavorites;
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
        ItemClickListener itemClickListener;


        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

            Viewaddfavorites = itemView.findViewById(R.id.likeCheckCard);
            Viewaddfavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Viewaddfavorites.isChecked()){
                        Toast.makeText(context, "Anuncio Agregado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Anuncio Eliminado", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.setOnClickListener(this);
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

//        public void setInfoUsuario(String nombreuser, String fotousuario){
//
//            ViewFotoUsuario = mview.findViewById(R.id.anuncio_usuario_imagen);
//            ViewNombreUsuario = mview.findViewById(R.id.anuncio_usuario_nombre);
//
//            ViewNombreUsuario.setText(nombreuser);
//            RequestOptions placeholderOptions = new RequestOptions();
//            placeholderOptions.placeholder(R.drawable.profile_placeholder);
//
//            Glide.with(context).applyDefaultRequestOptions(placeholderOptions).load(fotousuario).into(ViewFotoUsuario);
//        }


        public void setItemLongClickListener(ItemLongClickListener ic){
            this.itemLongClickListener = ic;
        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
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

        @Override
        public void onClick(View view) {
//            Intent a = new Intent(context,PictureDetailActivity.class);
////            a.putExtra("TITLE_KEY",Titleproductdetail);
////            context.startActivity(a);
            this.itemClickListener.onClick(getLayoutPosition(),view);


        }
    }



}
