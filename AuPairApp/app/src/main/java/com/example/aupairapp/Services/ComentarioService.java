package com.example.aupairapp.Services;

import com.example.aupairapp.Model.Comentario;
import com.example.aupairapp.Model.ComentarioDto;
import com.example.aupairapp.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComentarioService {

    @GET("/comentarios/anuncio/{id}")
    Call<ResponseContainer<Comentario>> getComentariosAnuncio(@Path("id") String anuncioId);

    @POST("/comentarios")
    Call<Comentario> addComentario(@Body ComentarioDto newComentario);

    @DELETE("/comentarios/{id}")
    Call<ResponseContainer<Comentario>> deleteComentario(@Path("id") String id);
}
