package com.example.aupairapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.aupairapp.Fragments.AnuncioFragment;
import com.example.aupairapp.Generator.UtilUser;
import com.example.aupairapp.Listener.AnuncioListener;

public class MainActivity extends AppCompatActivity implements AnuncioListener {

    private TextView benvenu;
    private FrameLayout f;
    private static final int ANUNCIO_LIST_ALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UtilUser.clearSharedPreferences(MainActivity.this);

        /*f = findViewById(R.id.contenedor);*/

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, AnuncioFragment.newInstance(ANUNCIO_LIST_ALL))
                .commit();

        f = findViewById(R.id.contenedor);

        /*benvenu = findViewById(R.id.benvenu);*/

        /*benvenu.setText("Benvenu");*/
    }

    @Override
    public void verAnuncio(String anuncio) {

    }
}
