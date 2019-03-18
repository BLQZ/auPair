package com.example.aupairapp.Model;

public class AnuncioDto {

    private String contenido;

    public AnuncioDto() {
    }

    public AnuncioDto(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnuncioDto that = (AnuncioDto) o;

        return contenido != null ? contenido.equals(that.contenido) : that.contenido == null;
    }

    @Override
    public int hashCode() {
        return contenido != null ? contenido.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AnuncioDto{" +
                "contenido='" + contenido + '\'' +
                '}';
    }
}
