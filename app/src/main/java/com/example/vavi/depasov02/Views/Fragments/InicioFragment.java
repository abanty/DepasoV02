package com.example.vavi.depasov02.Views.Fragments;
//COMENTAR SI HAY ERROR

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.example.vavi.depasov02.Models.AnuncioModel;
//import com.example.vavi.depasov02.Presentators.AdapterAnuncio;
import com.example.vavi.depasov02.Models.AnuncioModel;
import com.example.vavi.depasov02.Presentators.AdapterAnuncio;
import com.example.vavi.depasov02.R;
import com.example.vavi.depasov02.Views.MapsActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    private RecyclerView RecyclerViewListaDepas;
    private List<AnuncioModel> ListPostanuncios;
    private FirebaseFirestore firebaseFirestore;
    private AdapterAnuncio adapterAnuncio;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisible;


    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        ListPostanuncios = new ArrayList<>();
        RecyclerViewListaDepas = view.findViewById(R.id.lista_anuncios);

        firebaseAuth = FirebaseAuth.getInstance();

        adapterAnuncio = new AdapterAnuncio(ListPostanuncios);
        RecyclerViewListaDepas.setLayoutManager(new LinearLayoutManager(container.getContext()));
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
                        Toast.makeText(container.getContext(), "Alcance : " + descripcion, Toast.LENGTH_SHORT).show();
                        CargarAnuncios();

                    }

                }
            });

            Query primeraconsulta = firebaseFirestore.collection("Anuncios").orderBy("tiempo_marcado",Query.Direction.DESCENDING);
            primeraconsulta.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                    if (documentSnapshots != null) {
                    lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size()-1);
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                AnuncioModel anuncioModel = doc.getDocument().toObject(AnuncioModel.class);
                                ListPostanuncios.add(anuncioModel);
                                adapterAnuncio.notifyDataSetChanged();

                            }
                        }
//                    }

                }
            });
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btn_add_anuncio);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), MapsActivity.class));
                }
            });

        }

      // Inflate the layout for this fragment
        return view;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        adapterAnuncio.getSelectedContextMenuItems(item);
        return super.onContextItemSelected(item);
    }

    public void CargarAnuncios(){

        if (firebaseAuth.getCurrentUser() != null) {

            Query segundaconsulta = firebaseFirestore.collection("Anuncios")
                    .orderBy("tiempo_marcado", Query.Direction.DESCENDING)
                    .startAfter(lastVisible)
                    .limit(3);

            segundaconsulta.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
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
