package com.example.aupairapp.Adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aupairapp.Generator.ServiceGenerator;
import com.example.aupairapp.Generator.TipoAutenticacion;
import com.example.aupairapp.Generator.UtilToken;
import com.example.aupairapp.Generator.UtilUser;
import com.example.aupairapp.Listener.ComentarioListener;
import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.Model.Comentario;
import com.example.aupairapp.Model.ResponseContainer;
import com.example.aupairapp.R;
import com.example.aupairapp.Services.AnuncioService;
import com.example.aupairapp.Services.ComentarioService;
import com.example.aupairapp.ViewModel.AnuncioViewModel;
import com.example.aupairapp.ViewModel.ComentarioViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyComentarioRecyclerViewAdapter extends RecyclerView.Adapter<MyComentarioRecyclerViewAdapter.ViewHolder> {

    private List<Comentario> mValues;
    private final ComentarioListener mListener;
    private Context ctx;

    public MyComentarioRecyclerViewAdapter(Context context, List<Comentario> items, ComentarioListener listener) {
        ctx = context;
        mValues = items;
        mListener = listener;
    }

    public void setNuevosComentarios(List<Comentario> nuevosComentarios) {
        this.mValues = nuevosComentarios;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comentariofragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        holder.tvName.setText(this.mValues.get(position).getOwnerId().getName());
        holder.tvContenido.setText(this.mValues.get(position).getContenido());
        holder.tvCreatedAt.setText(this.mValues.get(position).getCreatedAt().toString());
        holder.ivDeleteComentario.setVisibility(View.GONE);

        Glide
                .with(ctx)
                .load(this.mValues.get(position).getOwnerId().getPicture())
                .into(holder.imgOwner);

        if(UtilUser.getEmail(ctx).equals(mValues.get(position).getOwnerId().getEmail())){
            Log.d("s", "true");
            holder.ivDeleteComentario.setVisibility(View.VISIBLE);
        }

        holder.ivDeleteComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteComentario(mValues.get(position).getId(), mValues.get(position).getAnuncioId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvName, tvContenido, tvCreatedAt;
        public final ImageView imgOwner, ivDeleteComentario;
        public Comentario mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = view.findViewById(R.id.tvName);
            tvContenido = view.findViewById(R.id.tvContenido);
            tvCreatedAt = view.findViewById(R.id.tvCreatedAt);
            imgOwner = view.findViewById(R.id.imgOwner);
            ivDeleteComentario = view.findViewById(R.id.ivDeleteAnuncio);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvContenido.getText() + "'";
        }
    }

    public void getComentarios(final Context ctx, String idAn){
        ComentarioService service = ServiceGenerator.createService(ComentarioService.class);
        Call<ResponseContainer<Comentario>> call = service.getComentariosAnuncio(idAn);

        call.enqueue(new Callback<ResponseContainer<Comentario>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Comentario>> call, Response<ResponseContainer<Comentario>> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {
                    ComentarioViewModel mViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(ComentarioViewModel.class);
                    mViewModel.selectComentarioList(response.body().getRows());
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Comentario>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteComentario(String idComentario, final String idAn) {
        ComentarioService service = ServiceGenerator.createService(ComentarioService.class, UtilToken.getToken(this.ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<Comentario>> call = service.deleteComentario(idComentario);

        call.enqueue(new Callback<ResponseContainer<Comentario>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Comentario>> call, Response<ResponseContainer<Comentario>> response) {
                if (response.isSuccessful()){
                    getComentarios(ctx, idAn);
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Comentario>> call, Throwable t) {
                Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
