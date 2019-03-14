package com.example.aupairapp.Services;

import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AnuncioService {

    @GET("/anuncios")
    Call<ResponseContainer<Anuncio>> getAnuncios();
}
