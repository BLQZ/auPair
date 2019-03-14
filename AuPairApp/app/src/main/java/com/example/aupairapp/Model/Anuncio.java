package com.example.aupairapp.Model;

import java.util.Date;

public class Anuncio {

    private String id;
    private String contenido;
    private User ownerId;
    private Date createdAt;

    public Anuncio() {
    }

    public Anuncio(String id, String contenido, User ownerId, Date createdAt) {
        this.id = id;
        this.contenido = contenido;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Anuncio anuncio = (Anuncio) o;

        if (id != null ? !id.equals(anuncio.id) : anuncio.id != null) return false;
        if (contenido != null ? !contenido.equals(anuncio.contenido) : anuncio.contenido != null)
            return false;
        if (ownerId != null ? !ownerId.equals(anuncio.ownerId) : anuncio.ownerId != null)
            return false;
        return createdAt != null ? createdAt.equals(anuncio.createdAt) : anuncio.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (contenido != null ? contenido.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "id='" + id + '\'' +
                ", contenido='" + contenido + '\'' +
                ", ownerId=" + ownerId +
                ", createdAt=" + createdAt +
                '}';
    }
}