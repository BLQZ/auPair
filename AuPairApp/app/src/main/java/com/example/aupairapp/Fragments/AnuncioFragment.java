package com.example.aupairapp.Fragments;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aupairapp.Adapters.MyAnuncioRecyclerViewAdapter;
import com.example.aupairapp.DashboardActivity;
import com.example.aupairapp.Generator.ServiceGenerator;
import com.example.aupairapp.Generator.TipoAutenticacion;
import com.example.aupairapp.Generator.UtilToken;
import com.example.aupairapp.Listener.AnuncioListener;
import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.Model.ResponseContainer;
import com.example.aupairapp.R;
import com.example.aupairapp.Services.AnuncioService;
import com.example.aupairapp.ViewModel.AnuncioViewModel;

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
public class AnuncioFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ANUNCIO_LIST_TYPE = "ANUNCIO_LIST_TYPE";
    private static final int ANUNCIO_LIST_ALL = 1;
    private static final int ANUNCIO_LIST_MINE = 2;
    private static final int ANUNCIO_LIST_FAVS = 3;
    private static final int ANUNCIO_LIST_ALL_AUTH = 4;
    // TODO: Customize parameters
    private int anuncioListType = 1;
    private AnuncioListener mListener;
    private List<Anuncio> anuncioList;
    private MyAnuncioRecyclerViewAdapter adapter;
    private Context ctx;
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;

    public AnuncioFragment() {
    }

    public static AnuncioFragment newInstance(int anuncioListType) {
        AnuncioFragment fragment = new AnuncioFragment();
        Bundle args = new Bundle();
        args.putInt(ANUNCIO_LIST_TYPE, anuncioListType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            anuncioListType = getArguments().getInt(ANUNCIO_LIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anuncio_list, container, false);
        swipe = view.findViewById(R.id.swipeAnuncios);

        // Set the adapter
        if (view instanceof SwipeRefreshLayout) {
            ctx = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.list);

            recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
            anuncioList = new ArrayList<>();

            if(anuncioListType == ANUNCIO_LIST_ALL){

                AnuncioService service = ServiceGenerator.createService(AnuncioService.class);
                Call<ResponseContainer<Anuncio>> call = service.getAnuncios();

                call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                        if(response.isSuccessful()){
                            anuncioList = response.body().getRows();

                            adapter = new MyAnuncioRecyclerViewAdapter(
                                    ctx,
                                    anuncioList,
                                    mListener
                            );
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });


                lanzarViewModel(ctx);

            }

            if(anuncioListType == ANUNCIO_LIST_ALL_AUTH){

                AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
                Call<ResponseContainer<Anuncio>> call = service.getAnunciosAuth();

                call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                        if(response.isSuccessful()){
                            anuncioList = response.body().getRows();

                            adapter = new MyAnuncioRecyclerViewAdapter(
                                    ctx,
                                    anuncioList,
                                    mListener
                            );
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });


                lanzarViewModel(ctx);

            }

            if(anuncioListType == ANUNCIO_LIST_MINE){
                AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
                Call<ResponseContainer<Anuncio>> call = service.getMineAnuncios();

                call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                        if(response.isSuccessful()){
                            anuncioList = response.body().getRows();

                            adapter = new MyAnuncioRecyclerViewAdapter(
                                    ctx,
                                    anuncioList,
                                    mListener
                            );
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if(anuncioListType == ANUNCIO_LIST_FAVS){
                AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
                Call<ResponseContainer<Anuncio>> call = service.getFavAnuncios();

                call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                        if(response.isSuccessful()){
                            anuncioList = response.body().getRows();

                            adapter = new MyAnuncioRecyclerViewAdapter(
                                    ctx,
                                    anuncioList,
                                    mListener
                            );
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipe.setRefreshing(false);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AnuncioListener) {
            mListener = (AnuncioListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
        AnuncioViewModel anuncioViewModel = ViewModelProviders.of((FragmentActivity) ctx)
                .get(AnuncioViewModel.class);
        anuncioViewModel.getAll().observe(getActivity(), new Observer<List<Anuncio>>() {
            @Override
            public void onChanged(@Nullable List<Anuncio> anuncios) {
                adapter.setNuevosAnuncios(anuncios);
            }
        });
    }

    public void refreshData() {
        if(anuncioListType == ANUNCIO_LIST_ALL){

            AnuncioService service = ServiceGenerator.createService(AnuncioService.class);
            Call<ResponseContainer<Anuncio>> call = service.getAnuncios();

            call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                @Override
                public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                    if(response.isSuccessful()){
                        recyclerView.setAdapter(new MyAnuncioRecyclerViewAdapter(
                                ctx,
                                response.body().getRows(),
                                mListener
                        ));
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(anuncioListType == ANUNCIO_LIST_ALL_AUTH){

            AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
            Call<ResponseContainer<Anuncio>> call = service.getAnunciosAuth();

            call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                @Override
                public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                    if(response.isSuccessful()){
                        anuncioList = response.body().getRows();

                        adapter = new MyAnuncioRecyclerViewAdapter(
                                ctx,
                                anuncioList,
                                mListener
                        );
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });


            lanzarViewModel(ctx);

        }

        if(anuncioListType == ANUNCIO_LIST_MINE){
            AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
            Call<ResponseContainer<Anuncio>> call = service.getMineAnuncios();

            call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                @Override
                public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                    if(response.isSuccessful()){
                        anuncioList = response.body().getRows();

                        adapter = new MyAnuncioRecyclerViewAdapter(
                                ctx,
                                anuncioList,
                                mListener
                        );
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(anuncioListType == ANUNCIO_LIST_FAVS){
            AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
            Call<ResponseContainer<Anuncio>> call = service.getFavAnuncios();

            call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                @Override
                public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                    if(response.isSuccessful()){
                        anuncioList = response.body().getRows();

                        adapter = new MyAnuncioRecyclerViewAdapter(
                                ctx,
                                anuncioList,
                                mListener
                        );
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
        lanzarViewModel(ctx);
    }


}
