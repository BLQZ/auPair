package com.example.aupairapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aupairapp.Fragments.LoginFragment;
import com.example.aupairapp.Fragments.SignupFragment;

public class SessionActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, SignupFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new LoginFragment())
                .commit();
    }

    @Override
    public void navegarRegistro() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, new SignupFragment())
                .commit();
    }

    @Override
    public void navegarLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, new LoginFragment())
                .commit();
    }
}
