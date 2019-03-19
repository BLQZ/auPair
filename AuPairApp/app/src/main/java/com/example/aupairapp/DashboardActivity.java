package com.example.aupairapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aupairapp.Adapters.MyAnuncioRecyclerViewAdapter;
import com.example.aupairapp.Dialogs.AddAnuncioFragment;
import com.example.aupairapp.Fragments.AnuncioFragment;
import com.example.aupairapp.Generator.ServiceGenerator;
import com.example.aupairapp.Generator.TipoAutenticacion;
import com.example.aupairapp.Generator.UtilToken;
import com.example.aupairapp.Generator.UtilUser;
import com.example.aupairapp.Listener.AnuncioListener;
import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.Model.AnuncioDto;
import com.example.aupairapp.Model.LoginResponse;
import com.example.aupairapp.Model.ResponseContainer;
import com.example.aupairapp.Services.AnuncioService;
import com.example.aupairapp.ViewModel.AnuncioViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements AnuncioListener, AddAnuncioFragment.AddAnuncioDialogListener {

    private TextView mTextMessage;
    private FloatingActionButton btnToAddAnuncio;
    private AnuncioViewModel anuncioViewModel;
    private MyAnuncioRecyclerViewAdapter adapter;
    private static final int ANUNCIO_LIST_ALL = 1;
    private static final int ANUNCIO_LIST_MINE = 2;
    private static final int ANUNCIO_LIST_FAVS = 3;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_anuncios:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, AnuncioFragment.newInstance(ANUNCIO_LIST_ALL))
                            .commit();
                    btnToAddAnuncio.show();
                    return true;
                case R.id.navigation_favoritos:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, AnuncioFragment.newInstance(ANUNCIO_LIST_FAVS))
                            .commit();
                    btnToAddAnuncio.hide();
                    return true;
                case R.id.navigation_mis_anuncios:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, AnuncioFragment.newInstance(ANUNCIO_LIST_MINE))
                            .commit();
                    btnToAddAnuncio.hide();
                    return true;
                case R.id.navigation_ajustes:
                    btnToAddAnuncio.hide();
                    return true;
                case R.id.navigation_chat:
                    btnToAddAnuncio.hide();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new AnuncioFragment())
                .commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btnToAddAnuncio = findViewById(R.id.btnToAddAnuncio);
        btnToAddAnuncio.show();

        btnToAddAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAnuncioDialog();
                /*lanzarViewModel(DashboardActivity.this);*/
            }
        });

    }

    @Override
    public void verAnuncio(String anuncio) {

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        // Defino qué quiero hacer cuando el usuario pulse el botón
        // volver o atrás del móvil

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2.1. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

        // 2.2. Añadir botones al diálogo
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                UtilUser.clearSharedPreferences(DashboardActivity.this);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void showAddAnuncioDialog() {
        DialogFragment dialog = new AddAnuncioFragment();
        dialog.show(getSupportFragmentManager(), "AddAnuncioFragment");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String contenido) {

        AnuncioService service = ServiceGenerator.createService(AnuncioService.class, UtilToken.getToken(DashboardActivity.this), TipoAutenticacion.JWT);
        Call<ResponseContainer<Anuncio>> call = service.addAnuncio(new AnuncioDto(contenido));

        call.enqueue(new Callback<ResponseContainer<Anuncio>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Anuncio>> call, Response<ResponseContainer<Anuncio>> response) {
                if (!response.isSuccessful()) {
                    // error
                    Log.e("RequestError", response.message());
                    Toast.makeText(DashboardActivity.this, "Error al subir anuncio", Toast.LENGTH_SHORT).show();

                } else {
                    getAnuncios(DashboardActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Anuncio>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(DashboardActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
        /*lanzarViewModel(DashboardActivity.this);*/

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void getAnuncios(final Context ctx){
        AnuncioService service = ServiceGenerator.createService(AnuncioService.class);
        Call<ResponseContainer<Anuncio>> call = service.getAnuncios();

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
                Toast.makeText(DashboardActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
