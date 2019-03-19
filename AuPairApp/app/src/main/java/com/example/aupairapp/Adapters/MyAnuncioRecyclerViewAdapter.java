package com.example.aupairapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aupairapp.DashboardActivity;
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

    public MyAnuncioRecyclerViewAdapter(Context ctx, List<Anuncio> items, AnuncioListener listener) {
        contexto = ctx;
        mValues = items;
        mListener = listener;
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
                            Toast.makeText(holder.mView.getContext(), "Anuncio borrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseContainer<Anuncio>> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(v.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
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
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvName, tvEmail, tvContenido, tvCreatedAt;
        public final TextView mContentView;
        public final ImageView imgOwner, ivDeleteAnuncio, fav;
        public Anuncio mItem;
        public Context contextaso;

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
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }


    }
}
