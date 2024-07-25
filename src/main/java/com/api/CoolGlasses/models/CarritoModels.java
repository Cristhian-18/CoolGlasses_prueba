package com.api.CoolGlasses.models;

import java.util.List;
import java.util.Map;

public class CarritoModels {

    private String id_carrito;
    private Usuario usuario;
    private List<Producto> producto;
    private String estado;

    public static class Usuario {
        private String nombre;
        private String apellido;
        private String correo;
        private String rol;
        private String estado;

        // Getters y setters
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }

    public static class Producto {
        private String codUnico;
        private String nombre;
        private double precio;
        private double descuento;
        private String categoria;
        private List<String> imagenes;
        private Map<String, String> caracteristicas;
        private String descripcion;
        private String genero;
        private boolean oferta;
        private int stock;
        private String estado;
        private int cantidad_seleccionada;

        // Getters y setters
        public String getCodUnico() {
            return codUnico;
        }

        public void setCodUnico(String codUnico) {
            this.codUnico = codUnico;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double precio) {
            this.precio = precio;
        }

        public double getDescuento() {
            return descuento;
        }

        public void setDescuento(double descuento) {
            this.descuento = descuento;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public List<String> getImagenes() {
            return imagenes;
        }

        public void setImagenes(List<String> imagenes) {
            this.imagenes = imagenes;
        }

        public Map<String, String> getCaracteristicas() {
            return caracteristicas;
        }

        public void setCaracteristicas(Map<String, String> caracteristicas) {
            this.caracteristicas = caracteristicas;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getGenero() {
            return genero;
        }

        public void setGenero(String genero) {
            this.genero = genero;
        }

        public boolean isOferta() {
            return oferta;
        }

        public void setOferta(boolean oferta) {
            this.oferta = oferta;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public int getCantidad_seleccionada() {
            return cantidad_seleccionada;
        }

        public void setCantidad_seleccionada(int cantidad_seleccionada) {
            this.cantidad_seleccionada = cantidad_seleccionada;
        }
    }

    // Getters y setters
    public String getId_carrito() {
        return id_carrito;
    }

    public void setId_carrito(String id_carrito) {
        this.id_carrito = id_carrito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Producto> getProducto() {
        return producto;
    }

    public void setProducto(List<Producto> producto) {
        this.producto = producto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
