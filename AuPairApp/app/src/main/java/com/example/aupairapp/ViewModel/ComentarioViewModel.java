package com.example.aupairapp.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import com.example.aupairapp.Model.Comentario;

import java.util.List;

public class ComentarioViewModel extends ViewModel {

    private final MutableLiveData<String> idComentario = new MutableLiveData<>();
    private final MutableLiveData<List<Comentario>> comentarios = new MutableLiveData<>();

    public void selectIdComentario(String id) { idComentario.setValue(id);}

    public void selectComentarioList(List<Comentario> comentarioList) {
        comentarios.setValue(comentarioList);
    }

    public MutableLiveData<String> getSelectedIdComentario() {
        return idComentario;
    }


    public MutableLiveData<List<Comentario>> getAll() { return comentarios; }
}
