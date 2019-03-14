package com.example.aupairapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aupairapp.Listener.AnuncioListener;
import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.R;

import java.util.List;

public class MyAnuncioRecyclerViewAdapter extends RecyclerView.Adapter<MyAnuncioRecyclerViewAdapter.ViewHolder> {

    private final List<Anuncio> mValues;
    private final AnuncioListener mListener;
    private Context contexto;

    public MyAnuncioRecyclerViewAdapter(Context ctx, List<Anuncio> items, AnuncioListener listener) {
        contexto = ctx;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_anuncio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvName.setText(mValues.get(position).getOwnerId().getName());
        holder.tvEmail.setText(mValues.get(position).getOwnerId().getEmail());
        holder.tvContenido.setText(mValues.get(position).getContenido());
        holder.tvCreatedAt.setText(mValues.get(position).getCreatedAt().toString());

        Glide
                .with(this.contexto)
                .load(mValues.get(position).getOwnerId().getPicture())
                .into(holder.imgOwner);


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
        public final ImageView imgOwner;
        public Anuncio mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvContenido = view.findViewById(R.id.tvContenido);
            tvCreatedAt = view.findViewById(R.id.tvCreatedAt);
            mContentView = (TextView) view.findViewById(R.id.content);
            imgOwner = view.findViewById(R.id.imgOwner);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}