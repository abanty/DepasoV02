package com.example.vavi.depasov02.Views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.vavi.depasov02.Models.AnuncioModel;
import com.example.vavi.depasov02.Presentators.AdapterAnuncio;
import com.example.vavi.depasov02.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListPostActivity extends AppCompatActivity {

    private RecyclerView RecyclerViewListaDepas;
    private List<AnuncioModel> ListPostanuncios;
    private FirebaseFirestore firebaseFirestore;
    private AdapterAnuncio adapterAnuncio;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);

        ListPostanuncios = new ArrayList<>();
        RecyclerViewListaDepas = findViewById(R.id.lista_anuncios);

        firebaseAuth = FirebaseAuth.getInstance();

        adapterAnuncio = new AdapterAnuncio(ListPostanuncios);
        RecyclerViewListaDepas.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewListaDepas.setAdapter(adapterAnuncio);


        if(firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            RecyclerViewListaDepas.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);

                    if (reachedBottom){

                        String descripcion = lastVisible.getString("descripcion");
                        Toast.makeText(ListPostActivity.this, "Alcance : " + descripcion, Toast.LENGTH_SHORT).show();
                        CargarAnuncios();

                    }

                }
            });

            Query primeraconsulta = firebaseFirestore.collection("Anuncios").orderBy("tiempo_marcado",Query.Direction.DESCENDING);
            primeraconsulta.addSnapshotListener(ListPostActivity.this,new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size()-1);
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            AnuncioModel anuncioModel = doc.getDocument().toObject(AnuncioModel.class);
                            ListPostanuncios.add(anuncioModel);
                            adapterAnuncio.notifyDataSetChanged();

                        }
                    }
                }
            });
        }
    }


    public void CargarAnuncios(){

        if (firebaseAuth.getCurrentUser() != null) {

            Query segundaconsulta = firebaseFirestore.collection("Anuncios")
                    .orderBy("precio", Query.Direction.DESCENDING)
                    .startAfter(lastVisible)
                    .limit(3);

            segundaconsulta.addSnapshotListener(ListPostActivity.this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {

                        lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size()-1);
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                AnuncioModel anuncioModel = doc.getDocument().toObject(AnuncioModel.class);
                                ListPostanuncios.add(anuncioModel);
                                adapterAnuncio.notifyDataSetChanged();

                            }
                        }
                    }

                }
            });

        }
    }
}
