package com.example.aupairapp.Model;

import java.util.Date;

public class Comentario {

    private String id;
    private String contenido;
    private User userId;
    private String anuncioId;
    private Date createdAt;

    public Comentario() {
    }

    public Comentario(String id, String contenido, User ownerId, String anuncioId) {
        this.id = id;
        this.contenido = contenido;
        this.userId = ownerId;
        this.anuncioId = anuncioId;
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
        return userId;
    }

    public void setOwnerId(User ownerId) {
        this.userId = ownerId;
    }

    public String getAnuncioId() {
        return anuncioId;
    }

    public void setAnuncioId(String anuncioId) {
        this.anuncioId = anuncioId;
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

        Comentario that = (Comentario) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (contenido != null ? !contenido.equals(that.contenido) : that.contenido != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return anuncioId != null ? anuncioId.equals(that.anuncioId) : that.anuncioId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (contenido != null ? contenido.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (anuncioId != null ? anuncioId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id='" + id + '\'' +
                ", contenido='" + contenido + '\'' +
                ", ownerId=" + userId +
                ", anuncioId='" + anuncioId + '\'' +
                '}';
    }
}
