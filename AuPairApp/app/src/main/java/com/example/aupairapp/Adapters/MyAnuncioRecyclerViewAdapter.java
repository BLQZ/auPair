package com.example.aupairapp.Adapters;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aupairapp.AnuncioDetallado;
import com.example.aupairapp.DashboardActivity;
import com.example.aupairapp.Fragments.AnuncioFragment;
import com.example.aupairapp.Generator.ServiceGenerator;
import com.example.aupairapp.Generator.TipoAutenticacion;
import com.example.aupairapp.Generator.UtilToken;
import com.example.aupairapp.Generator.UtilUser;
import com.example.aupairapp.Listener.AnuncioListener;
import com.example.aupairapp.MainActivity;
import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.Model.ResponseContainer;
import com.example.aupairapp.R;
import com.example.aupairapp.Services.AnuncioService;
import com.example.aupairapp.SessionActivity;
import com.example.aupairapp.ViewModel.AnuncioViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAnuncioRecyclerViewAdapter extends RecyclerView.Adapter<MyAnuncioRecyclerViewAdapter.ViewHolder> {

    private List<Anuncio> mValues;
    private final AnuncioListener mListener;
    private Context contexto;
    private AnuncioViewModel mViewModel;
    private int typeList;

    private static final int ANUNCIO_LIST_ALL = 1;
    private static final int ANUNCIO_LIST_MINE = 2;
    private static final int ANUNCIO_LIST_FAVS = 3;
    private static final int ANUNCIO_LIST_ALL_AUTH = 4;

    public MyAnuncioRecyclerViewAdapter(Context ctx, List<Anuncio> items, AnuncioListener listener, int type) {
        contexto = ctx;
        mValues = items;
        mListener = listener;
        typeList = type;
    }

    public void setNuevosAnuncios(List<Anuncio> nuevosAnuncios) {
        this.mValues = nuevosAnuncios;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_anuncio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.tvName.setText(mValues.get(position).getOwnerId().getName());
        holder.tvEmail.setText(mValues.get(position).getOwnerId().getEmail());
        holder.tvContenido.setText(mValues.get(position).getContenido());
        holder.tvCreatedAt.setText(mValues.get(position).getCreatedAt().toString());
        holder.ivDeleteAnuncio.setVisibility(View.GONE);
        holder.fav.setImageResource(R.drawable.ic_favorite_border_24dp);

        Glide
                .with(this.contexto)
                .load(mValues.get(position).getOwnerId().getPicture())
                .into(holder.imgOwner);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UtilToken.getToken(v.getContext()) == null){
                    /*v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), SessionActivity.class));*/
                } else {
                    Intent i = new Intent(v.getContext().getApplicationContext(), AnuncioDetallado.class);
                    i.putExtra("idAnuncio", mValues.get(position).getId());
                    v.getContext().startActivity(i);
                }
            }
        });

        holder.imgOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UtilToken.getToken(v.getContext()) == null){
                    v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), SessionActivity.class));
                } else {
                    Log.d("t", UtilToken.getToken(v.getContext()).toString());
                }
            }
        });

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UtilToken.getToken(v.getContext()) == null){
                    v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), SessionActivity.class));
                } else {
                    if(mValues.get(position).isFav()){
                        AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(contexto), TipoAutenticacion.JWT);
                        Call<ResponseContainer<Anuncio>> call = service.removeFavAnuncio(mValues.get(position).getId());

                        call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                            @Override
                            public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                                if(response.isSuccessful()){
                                    holder.fav.setImageResource(R.drawable.ic_favorite_border_24dp);
                                    /*if(typeList == ANUNCIO_LIST_ALL_AUTH){
                                        getAnunciosAuth(contexto);
                                    } else if(typeList == ANUNCIO_LIST_FAVS){
                                        getFavsAnuncios(contexto);
                                    } else if(typeList == ANUNCIO_LIST_MINE){
                                        getMineAnuncios(contexto);
                                    }*/
                                    /*switch (typeList){
                                        case ANUNCIO_LIST_ALL_AUTH:
                                            getAnunciosAuth(contexto);
                                            break;
                                        case ANUNCIO_LIST_MINE:
                                            getMineAnuncios(contexto);
                                            break;
                                        case ANUNCIO_LIST_FAVS:
                                            getFavsAnuncios(contexto);
                                            break;
                                    }*/
                                    Toast.makeText(contexto, "Eliminado Favoritos", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                                Toast.makeText(contexto, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(contexto), TipoAutenticacion.JWT);
                        Call<ResponseContainer<Anuncio>> call = service.addFavAnuncio(mValues.get(position).getId());

                        call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                            @Override
                            public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                                if(response.isSuccessful()){
                                    holder.fav.setImageResource(R.drawable.ic_favorite_24dp);
                                    holder.fav.setImageResource(R.drawable.ic_favorite_border_24dp);
                                    /*switch (typeList){
                                        case ANUNCIO_LIST_ALL_AUTH:
                                            getAnunciosAuth(contexto);
                                            break;
                                        case ANUNCIO_LIST_MINE:
                                            getMineAnuncios(contexto);
                                            break;
                                        case ANUNCIO_LIST_FAVS:
                                            getFavsAnuncios(contexto);
                                            break;
                                    }
*/
                                    /*if(typeList == ANUNCIO_LIST_ALL_AUTH){
                                        getAnunciosAuth(contexto);
                                    }
                                    if(typeList == ANUNCIO_LIST_FAVS){
                                        getFavsAnuncios(contexto);
                                    }
                                    if(typeList == ANUNCIO_LIST_MINE){
                                        getMineAnuncios(contexto);
                                    }*/
                                    Toast.makeText(contexto, "Añadido a Favoritos", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                                Toast.makeText(contexto, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                /*if(typeList == ANUNCIO_LIST_ALL_AUTH){
                    getAnunciosAuth(contexto);
                } else if(typeList == ANUNCIO_LIST_FAVS){
                    getFavsAnuncios(contexto);
                } else if(typeList == ANUNCIO_LIST_MINE){
                    getMineAnuncios(contexto);
                }*/

                refreshData();
            }
        });

        if(UtilToken.getToken(this.contexto) == null){
            holder.ivDeleteAnuncio.setVisibility(View.INVISIBLE);
        } else {
            if(this.mValues.get(position).isFav()){
                Log.d("fav", "true");
                holder.fav.setImageResource(R.drawable.ic_favorite_24dp);
            }
            String id = UtilUser.getEmail(this.contexto);
            String idOwner=  this.mValues.get(position).getOwnerId().getEmail();
            Log.d("d", this.mValues.get(position).toString());
            if(id.equals(idOwner)){
                holder.ivDeleteAnuncio.setVisibility(View.VISIBLE);
            } else {
                holder.ivDeleteAnuncio.setVisibility(View.INVISIBLE);
            }

        }

        holder.ivDeleteAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(v.getContext()), TipoAutenticacion.JWT);
                Call<ResponseContainer<Anuncio>> call = service.removeAnuncio(mValues.get(position).getId());

                call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                        if(response.isSuccessful()){
                            /*if(typeList == ANUNCIO_LIST_ALL_AUTH){
                                 getAnunciosAuth(v.getContext());
                            }
                            if(typeList == ANUNCIO_LIST_FAVS){
                                getFavsAnuncios(v.getContext());
                            }
                            if(typeList == ANUNCIO_LIST_MINE){
                                getMineAnuncios(v.getContext());
                            }*//*
                            switch (typeList){
                                case ANUNCIO_LIST_ALL_AUTH:
                                    getAnunciosAuth(contexto);
                                    break;
                                case ANUNCIO_LIST_MINE:
                                    getMineAnuncios(contexto);
                                    break;
                                case ANUNCIO_LIST_FAVS:
                                    getFavsAnuncios(contexto);
                                    break;
                            }*/
                            Toast.makeText(holder.mView.getContext(), "Anuncio borrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseContainer<Anuncio>> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(v.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
                refreshData();
            }
        });



        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        if(mValues == null) return 0;
        return mValues.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvName, tvEmail, tvContenido, tvCreatedAt;
        public final TextView mContentView;
        public final ImageView imgOwner, ivDeleteAnuncio, fav;
        public Anuncio mItem;
        public Context contextaso;
        public CardView card;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvContenido = view.findViewById(R.id.tvContenido);
            tvCreatedAt = view.findViewById(R.id.tvCreatedAt);
            mContentView = (TextView) view.findViewById(R.id.content);
            imgOwner = view.findViewById(R.id.imgOwner);
            ivDeleteAnuncio = view.findViewById(R.id.ivDeleteAnuncio);
            contextaso = view.getContext();
            fav = view.findViewById(R.id.fav);
            card = view.findViewById(R.id.cardView_inmueble);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }


    }

    public void getAnunciosAuth(final Context ctx){
        AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<Anuncio>> call = service.getAnunciosAuth();

        call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {
                    AnuncioViewModel mViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(AnuncioViewModel.class);
                    mViewModel.selectAnuncioList(response.body().getRows());

                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMineAnuncios(final Context ctx){
        AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<Anuncio>> call = service.getMineAnuncios();

        call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {
                    AnuncioViewModel mViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(AnuncioViewModel.class);
                    mViewModel.selectAnuncioList(response.body().getRows());

                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getFavsAnuncios(final Context ctx){
        AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<Anuncio>> call = service.getAnunciosAuth();

        call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {
                    AnuncioViewModel mViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(AnuncioViewModel.class);
                    mViewModel.selectAnuncioList(response.body().getRows());

                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void lanzarViewModel(Context ctx) {
        AnuncioViewModel anuncioViewModel = ViewModelProviders.of((FragmentActivity) ctx)
                .get(AnuncioViewModel.class);
        anuncioViewModel.getAll().observe((LifecycleOwner) this.contexto, new Observer<List<Anuncio>>() {
            @Override
            public void onChanged(@Nullable List<Anuncio> anuncios) {
                setNuevosAnuncios(anuncios);
            }
        });
    }

    public void refreshData() {
        if(this.typeList == ANUNCIO_LIST_ALL){

            AnuncioService service = ServiceGenerator.createService(AnuncioService.class);
            Call<ResponseContainer<Anuncio>> call = service.getAnuncios();

            call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                @Override
                public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                    if(response.isSuccessful()){
                        mValues = response.body().getRows();
                        setNuevosAnuncios(mValues);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(contexto, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }else if(typeList == ANUNCIO_LIST_ALL_AUTH){

            AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(this.contexto), TipoAutenticacion.JWT);
            Call<ResponseContainer<Anuncio>> call = service.getAnunciosAuth();

            call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                @Override
                public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                    if(response.isSuccessful()){
                        mValues = response.body().getRows();
                        setNuevosAnuncios(mValues);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(contexto, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });


            lanzarViewModel(this.contexto);

        }else if(typeList == ANUNCIO_LIST_MINE){
            AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(this.contexto), TipoAutenticacion.JWT);
            Call<ResponseContainer<Anuncio>> call = service.getMineAnuncios();

            call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                @Override
                public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                    if(response.isSuccessful()){
                        mValues = response.body().getRows();
                        setNuevosAnuncios(mValues);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(contexto, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(typeList == ANUNCIO_LIST_FAVS){
            AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(this.contexto), TipoAutenticacion.JWT);
            Call<ResponseContainer<Anuncio>> call = service.getFavAnuncios();

            call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
                @Override
                public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                    if(response.isSuccessful()){
                        mValues = response.body().getRows();
                        setNuevosAnuncios(mValues);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(contexto, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
