package com.example.aupairapp.Fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aupairapp.Adapters.MyComentarioRecyclerViewAdapter;
import com.example.aupairapp.Generator.ServiceGenerator;
import com.example.aupairapp.Listener.ComentarioListener;
import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.Model.Comentario;
import com.example.aupairapp.Model.ResponseContainer;
import com.example.aupairapp.R;
import com.example.aupairapp.Services.ComentarioService;
import com.example.aupairapp.ViewModel.AnuncioViewModel;
import com.example.aupairapp.ViewModel.ComentarioViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ComentarioFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ComentarioListener mListener;
    private List<Comentario> listComentarios;
    private Context ctx;
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private String idAnuncio;
    private MyComentarioRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ComentarioFragment() {
    }

    @SuppressLint("ValidFragment")
    public ComentarioFragment(String id) {
        idAnuncio = id;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ComentarioFragment newInstance(int columnCount) {
        ComentarioFragment fragment = new ComentarioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comentariofragment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            ctx = view.getContext();
            recyclerView = (RecyclerView) view;

            listComentarios = new ArrayList<>();

            ComentarioService service = ServiceGenerator.createService(ComentarioService.class);
            Call<ResponseContainer<Comentario>> call = service.getComentariosAnuncio(idAnuncio);

            call.enqueue(new Callback<ResponseContainer<Comentario>>() {
                @Override
                public void onResponse(Call<ResponseContainer<Comentario>> call, Response<ResponseContainer<Comentario>> response) {
                    if(response.isSuccessful()){
                        listComentarios = response.body().getRows();

                        adapter = new MyComentarioRecyclerViewAdapter(
                                ctx,
                                listComentarios,
                                mListener
                        );

                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Comentario>> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });

            lanzarViewModel(this.ctx);


        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComentarioListener) {
            mListener = (ComentarioListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ComentarioListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        /*void onListFragmentInteraction(DummyItem item);*/
    }

    private void lanzarViewModel(Context ctx) {
        ComentarioViewModel comentarioViewModel= ViewModelProviders.of((FragmentActivity) ctx)
                .get(ComentarioViewModel.class);
        comentarioViewModel.getAll().observe(getActivity(), new Observer<List<Comentario>>() {
            @Override
            public void onChanged(@Nullable List<Comentario> comentarios) {
                adapter.setNuevosComentarios(comentarios);
            }
        });
    }
}
