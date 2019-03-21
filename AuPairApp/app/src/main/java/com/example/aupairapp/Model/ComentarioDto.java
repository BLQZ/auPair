package com.example.aupairapp.Model;

public class ComentarioDto {

    private String anuncioId;
    private String contenido;

    public ComentarioDto() {
    }

    public ComentarioDto(String anuncioId, String contenido) {
        this.anuncioId = anuncioId;
        this.contenido = contenido;
    }

    public String getAnuncioId() {
        return anuncioId;
    }

    public void setAnuncioId(String anuncioId) {
        this.anuncioId = anuncioId;
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

        ComentarioDto that = (ComentarioDto) o;

        if (anuncioId != null ? !anuncioId.equals(that.anuncioId) : that.anuncioId != null)
            return false;
        return contenido != null ? contenido.equals(that.contenido) : that.contenido == null;
    }

    @Override
    public int hashCode() {
        int result = anuncioId != null ? anuncioId.hashCode() : 0;
        result = 31 * result + (contenido != null ? contenido.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ComentarioDto{" +
                "anuncioId='" + anuncioId + '\'' +
                ", contenido='" + contenido + '\'' +
                '}';
    }
}
