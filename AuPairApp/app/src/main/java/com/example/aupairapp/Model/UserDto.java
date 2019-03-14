package com.example.aupairapp.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Date;
import java.util.Objects;

public class UserDto {

    private String email;
    private String password;
    private String name;
    private String picture;
    private String role;
    private String address;
    private String city;
    private String province;
    private String country;
    private boolean male;
    private Date date;
    private int nHijos;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(email, userDto.email) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(name, userDto.name) &&
                Objects.equals(picture, userDto.picture) &&
                Objects.equals(role, userDto.role) &&
                Objects.equals(address, userDto.address) &&
                Objects.equals(city, userDto.city) &&
                Objects.equals(country, userDto.province) &&
                Objects.equals(male, userDto.male) &&
                Objects.equals(date, userDto.date) &&
                Objects.equals(nHijos, userDto.nHijos);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(email, password, name, picture, role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getnHijos() {
        return nHijos;
    }

    public void setnHijos(int nHijos) {
        this.nHijos = nHijos;
    }

    public UserDto(String email, String password, String name, String picture, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.picture = picture;
        this.role = role;
    }

    public UserDto(String email, String password, String name, String picture, String role,
                   String address, String city, String province, String country, boolean male, int nHijos, Date date) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.picture = picture;
        this.role = role;
        this.address = address;
        this.city = city;
        this.province = province;
        this.country = country;
        this.male = male;
        this.nHijos = nHijos;
        this.date = date;
    }

    public UserDto(String email, String password, String name, String role,
                   String address, String city, String province, String country, boolean male, int nHijos, Date date) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.address = address;
        this.city = city;
        this.province = province;
        this.country = country;
        this.male = male;
        this.nHijos = nHijos;
        this.date = date;
    }

    public UserDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", role='" + role + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", male=" + male +
                ", date=" + date +
                ", nHijos=" + nHijos +
                '}';
    }
}
