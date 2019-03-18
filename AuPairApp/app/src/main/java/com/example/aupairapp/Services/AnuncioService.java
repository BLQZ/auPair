package com.example.aupairapp.Services;

import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.Model.AnuncioDto;
import com.example.aupairapp.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AnuncioService {

    @GET("/anuncios")
    Call<ResponseContainer<Anuncio>> getAnuncios();

    @POST("/anuncios")
    Call<ResponseContainer<Anuncio>> addAnuncio(@Body AnuncioDto newAnuncio);

    @GET("anuncios/mine")
    Call<ResponseContainer<Anuncio>> getMineAnuncios();
}
