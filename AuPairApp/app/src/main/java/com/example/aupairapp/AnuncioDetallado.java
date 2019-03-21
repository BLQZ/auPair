package com.example.aupairapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aupairapp.Fragments.ComentarioFragment;
import com.example.aupairapp.Generator.ServiceGenerator;
import com.example.aupairapp.Generator.TipoAutenticacion;
import com.example.aupairapp.Generator.UtilToken;
import com.example.aupairapp.Generator.UtilUser;
import com.example.aupairapp.Listener.ComentarioListener;
import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.Model.Comentario;
import com.example.aupairapp.Model.ComentarioDto;
import com.example.aupairapp.Model.ResponseContainer;
import com.example.aupairapp.Services.AnuncioService;
import com.example.aupairapp.Services.ComentarioService;
import com.example.aupairapp.ViewModel.ComentarioViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnuncioDetallado extends AppCompatActivity implements ComentarioListener {

    TextView tvName, tvEmail, tvContenido;
    ImageView imgOwner, imgOwnerNewComent, send;
    EditText etNewComent;
    Anuncio anuncio;
    String idAnuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio_detallado);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvContenido = findViewById(R.id.tvContenido);

        imgOwner = findViewById(R.id.imgOwner);
        imgOwnerNewComent = findViewById(R.id.imgOwnerNewComent);

        etNewComent = findViewById(R.id.etNewComent);
        send = findViewById(R.id.btnAddComent);

        Glide
                .with(AnuncioDetallado.this)
                .load(UtilUser.getImagen(AnuncioDetallado.this))
                .into(imgOwnerNewComent);


        Bundle extras = getIntent().getExtras();
        idAnuncio = extras.getString("idAnuncio");

        getAnuncio(idAnuncio);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.comentarios, new ComentarioFragment(idAnuncio))
                .commit();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newComent(idAnuncio, etNewComent.getText().toString());
                /*getComentarios(AnuncioDetallado.this, idAnuncio);*/
            }
        });
    }

    public void getAnuncio(String id){
        AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(AnuncioDetallado.this), TipoAutenticacion.JWT);
        Call<Anuncio> call = service.getOneAnuncio(id);

        call.enqueue(new Callback<Anuncio>() {
            @Override
            public void onResponse(Call<Anuncio> call, Response<Anuncio> response) {
                if(response.isSuccessful()){
                    anuncio = response.body();

                    tvEmail.setText(anuncio.getOwnerId().getEmail());
                    tvName.setText(anuncio.getOwnerId().getName());
                    tvContenido.setText(anuncio.getContenido());

                    Glide
                            .with(AnuncioDetallado.this)
                            .load(anuncio.getOwnerId().getPicture())
                            .into(imgOwner);
                }
            }

            @Override
            public void onFailure(Call<Anuncio> call, Throwable t) {
                Toast.makeText(AnuncioDetallado.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void newComent(final String id, String contenido){

        ComentarioService service = ServiceGenerator.createService(ComentarioService.class, UtilToken.getToken(AnuncioDetallado.this), TipoAutenticacion.JWT);
        Call<Comentario> call = service.addComentario(new ComentarioDto(id, contenido));

        call.enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                if(response.isSuccessful()){
                    getComentarios(AnuncioDetallado.this, id);
                    /*Toast.makeText(AnuncioDetallado.this, "Comentario añadido", Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                Toast.makeText(AnuncioDetallado.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void verComentario(String comentario) {

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
}
