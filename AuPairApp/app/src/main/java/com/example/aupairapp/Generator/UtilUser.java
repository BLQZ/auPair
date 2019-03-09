package com.example.aupairapp.Generator;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aupairapp.Model.User;

public class UtilUser {

    static SharedPreferences sharedPreferences;

    static void setSharedPreferences(Context mContext){
        SharedPreferences loginSharedPreferences =
                mContext.getSharedPreferences(
                        "login",
                        Context.MODE_PRIVATE);
        sharedPreferences = loginSharedPreferences;
    };

    public static void setEmail(Context mContext, String email) {
        setSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.commit();
    }


    public static String getEmail(Context mContext) {
        setSharedPreferences(mContext);

        String email = sharedPreferences
                .getString("Email", null);

        return email;
    }

    public static void setNombre(Context mContext, String nombre) {
        setSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Nombre", nombre);
        editor.commit();
    }


    public static String getNombre(Context mContext) {
        setSharedPreferences(mContext);
        String nombre = sharedPreferences
                .getString("Nombre", null);

        return nombre;
    }

    public static void setImagen(Context mContext, String imagen) {
        setSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Imagen", imagen);
        editor.commit();
    }


    public static String getImagen(Context mContext) {
        setSharedPreferences(mContext);
        String imagen = sharedPreferences
                .getString("Imagen", null);

        return imagen;
    }

    public static void setRol(Context mContext, String rol) {
        setSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Rol", rol);
        editor.commit();
    }


    public static String getRol(Context mContext) {
        setSharedPreferences(mContext);
        String rol = sharedPreferences
                .getString("Rol", null);

        return rol;
    }

    public static String getId(Context mContext) {
        setSharedPreferences(mContext);
        String id = sharedPreferences
                .getString("Id", null);

        return id;
    }

    public static void setId(Context mContext, String id) {
        setSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Id", id);
        editor.commit();
    }

    public static boolean getEncuesta(Context mContext) {
        setSharedPreferences(mContext);
        Boolean encuesta = sharedPreferences
                .getBoolean("Encuesta", false);

        return encuesta;
    }

    public static void setEncuesta(Context mContext, Boolean encuesta) {
        setSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Encuesta", encuesta);
        editor.commit();
    }

    public static void clearSharedPreferences(Context mContext){
        setSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static  void setUserInfo(Context mContext, User user){
        setNombre(mContext, user.getName());
        setEmail(mContext, user.getEmail());
        setImagen(mContext, user.getPicture());
        setRol(mContext, user.getRole());
        setId(mContext, user.getId());
    }

    public static void setIdComentario(Context mContext, String id) {
        setSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IdComentario", id);
        editor.commit();
    }


    public static String getIdComentario(Context mContext) {
        setSharedPreferences(mContext);
        String id = sharedPreferences
                .getString("IdComentario", null);

        return id;
    }


}
