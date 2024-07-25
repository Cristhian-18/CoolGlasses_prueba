package com.api.CoolGlasses.models;

import java.util.List;
import java.util.Map;


public class ComprobanteDetalleVentaModels {

    private String id_comprobante_detalle;
    private Carrito carrito;
    private String fecha_venta;
    private double subtotal;
    private double IVA;
    private double total;

    public static class Carrito {
        private String id_carrito;
        private Usuario usuario;
        private List<Producto> producto;
        private String estado;

        public static class Usuario {
            private String cedula;
            private String nombre;
            private String apellido;

            // Getters y setters
            public String getCedula() {
                return cedula;
            }

            public void setCedula(String cedula) {
                this.cedula = cedula;
            }

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
        }

        public static class Producto {
            private String codUnico;
            private String nombre;
            private double precio;
            private String categoria;
            private List<String> imagenes;
            private Map<String, String> caracteristicas;
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

    // Getters y setters
    public String getId_comprobante_detalle() {
        return id_comprobante_detalle;
    }

    public void setId_comprobante_detalle(String id_comprobante_detalle) {
        this.id_comprobante_detalle = id_comprobante_detalle;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIVA() {
        return IVA;
    }

    public void setIVA(double IVA) {
        this.IVA = IVA;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}