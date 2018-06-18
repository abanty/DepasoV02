package com.example.vavi.depasov02.Models;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class AnuncioModel {

    public String id_usuario, url_imagen, descripcion, renderizados, titulo_anuncio, descripcion_larga, telefono_anuncio, ubicacion, precio, modalidad;
    public Date tiempo_marcado;

    public AnuncioModel() {
    }

    public AnuncioModel(String id_usuario, String url_imagen, String descripcion, String renderizados, String titulo_anuncio, String descripcion_larga, String telefono_anuncio, String ubicacion, String precio, String modalidad, Date tiempo_marcado) {
        this.id_usuario = id_usuario;
        this.url_imagen = url_imagen;
        this.descripcion = descripcion;
        this.renderizados = renderizados;
        this.titulo_anuncio = titulo_anuncio;
        this.descripcion_larga = descripcion_larga;
        this.telefono_anuncio = telefono_anuncio;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.modalidad = modalidad;
        this.tiempo_marcado = tiempo_marcado;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRenderizados() {
        return renderizados;
    }

    public void setRenderizados(String renderizados) {
        this.renderizados = renderizados;
    }

    public String getTitulo_anuncio() {
        return titulo_anuncio;
    }

    public void setTitulo_anuncio(String titulo_anuncio) {
        this.titulo_anuncio = titulo_anuncio;
    }

    public String getDescripcion_larga() {
        return descripcion_larga;
    }

    public void setDescripcion_larga(String descripcion_larga) {
        this.descripcion_larga = descripcion_larga;
    }

    public String getTelefono_anuncio() {
        return telefono_anuncio;
    }

    public void setTelefono_anuncio(String telefono_anuncio) {
        this.telefono_anuncio = telefono_anuncio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public Date getTiempo_marcado() {
        return tiempo_marcado;
    }

    public void setTiempo_marcado(Date tiempo_marcado) {
        this.tiempo_marcado = tiempo_marcado;
    }
}
