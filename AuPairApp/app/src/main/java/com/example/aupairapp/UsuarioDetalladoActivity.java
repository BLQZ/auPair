package com.example.aupairapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aupairapp.Fragments.AnuncioFragment;
import com.example.aupairapp.Generator.ServiceGenerator;
import com.example.aupairapp.Listener.AnuncioListener;
import com.example.aupairapp.Model.UserDto;
import com.example.aupairapp.Services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioDetalladoActivity extends AppCompatActivity implements AnuncioListener {

    private static final int ANUNCIO_LIST_OWNER = 5;
    private TextView tvName, tvEmail, tvCity, tvHijos, tvGender, tvDate;
    private ImageView imgUser, imgHijos;
    private String email;
    private UserDto user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detallado);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvCity = findViewById(R.id.tvCity);
        tvHijos = findViewById(R.id.tvHijos);
        tvGender = findViewById(R.id.tvGender);
        tvHijos.setVisibility(View.GONE);
        imgUser = findViewById(R.id.imgUserProfile);

        imgHijos = findViewById(R.id.imgHijos);
        imgHijos.setVisibility(View.GONE);

        tvDate = findViewById(R.id.date);

        Bundle extras = getIntent().getExtras();
        email = extras.getString("emailOwner");

        getUsuario(email);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.anunciosOwner, AnuncioFragment.newInstance(ANUNCIO_LIST_OWNER, email))
                .commit();


    }

    @Override
    public void verAnuncio(String anuncio) {

    }

    public void getUsuario(String e){
        UserService service = ServiceGenerator.createService(UserService.class);
        Call<UserDto> call = service.getOneUser(e);

        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(UsuarioDetalladoActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
                } else {
                    user = response.body();

                    tvName.setText(user.getName() + " (" + user.getRole() + ")");
                    tvEmail.setText(user.getEmail());
                    tvDate.setText(user.getDate().toLocaleString());
                    tvCity.setText(user.getAddress() + ", " + user.getCity() + " (" + user.getCountry() + ")");

                    if(user.isMale()){
                        tvGender.setText("Hombre");
                    } else {
                        tvGender.setText("Mujer");
                    }

                    if(user.getRole()=="family"){
                        tvHijos.setVisibility(View.VISIBLE);
                        imgHijos.setVisibility(View.VISIBLE);
                        tvHijos.setText(String.valueOf(user.getnHijos()));
                    }

                    Glide
                            .with(UsuarioDetalladoActivity.this)
                            .load(user.getPicture())
                            .into(imgUser);

                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {

            }
        });
    }
}
