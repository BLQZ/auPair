package com.example.aupairapp.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.aupairapp.Model.Anuncio;

import java.util.List;

public class AnuncioViewModel extends ViewModel {

    private final MutableLiveData<String> idAnuncio = new MutableLiveData<>();
    private final MutableLiveData<List<Anuncio>> anuncios = new MutableLiveData<>();

    public void selectIdAnuncio(String id) { idAnuncio.setValue(id);}

    public void selectAnuncioList(List<Anuncio> anuncioList) {
        anuncios.setValue(anuncioList);
    }

    public MutableLiveData<String> getSelectedIdAnuncio() {
        return idAnuncio;
    }


    public MutableLiveData<List<Anuncio>> getAll() { return anuncios; }


}
