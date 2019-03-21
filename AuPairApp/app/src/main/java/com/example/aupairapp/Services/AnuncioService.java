package com.example.aupairapp.Services;

import com.example.aupairapp.Model.Anuncio;
import com.example.aupairapp.Model.AnuncioDto;
import com.example.aupairapp.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnuncioService {

    @GET("/anuncios")
    Call<ResponseContainer<Anuncio>> getAnuncios();

    @POST("/anuncios")
    Call<ResponseContainer<Anuncio>> addAnuncio(@Body AnuncioDto newAnuncio);

    @GET("anuncios/mine")
    Call<ResponseContainer<Anuncio>> getMineAnuncios();

    @DELETE("anuncios/{id}")
    Call<ResponseContainer<Anuncio>> removeAnuncio(@Path("id") String id);

    @POST("anuncios/fav/{id}")
    Call<ResponseContainer<Anuncio>> addFavAnuncio(@Path("id") String id);

    @DELETE("anuncios/fav/{id}")
    Call<ResponseContainer<Anuncio>> removeFavAnuncio(@Path("id") String id);

    @GET("anuncios/favs")
    Call<ResponseContainer<Anuncio>> getFavAnuncios();

    @GET("anuncios/auth")
    Call<ResponseContainer<Anuncio>> getAnunciosAuth();

    @GET("/anuncios/{id}/")
    Call<Anuncio> getOneAnuncio(@Path("id") String id);
}
