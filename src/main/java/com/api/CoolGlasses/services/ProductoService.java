package com.api.CoolGlasses.services;

import com.api.CoolGlasses.models.CarritoModels;
import com.api.CoolGlasses.models.ProductoModels;
import com.api.CoolGlasses.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoService carritoService;

    // Guardar un nuevo producto
    public ProductoModels saveProducto(ProductoModels producto) {
        // Asegurarse de que el ID sea nulo para que MongoDB genere uno nuevo automáticamente
        producto.setId(null);
        return productoRepository.save(producto);
    }

    // Obtener todos los productos
    public List<ProductoModels> getProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public ProductoModels getProductosById(String id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Obtener un producto por código único
    public ProductoModels getProductoByCodUnico(String codUnico) {
        return productoRepository.findByCodUnico(codUnico);
    }

    // Actualizar un producto existente
    public ProductoModels updateProducto(String id, ProductoModels producto) {
        if (!productoRepository.existsById(id)) {
            return null;
        }
        producto.setId(id);
        ProductoModels updatedProducto = productoRepository.save(producto);

        // Actualizar el producto en todos los carritos
        updateProductInCarritos(updatedProducto);

        return updatedProducto;
    }

    // Eliminar un producto por código único
    public boolean deleteProducto(String id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }

    // Obtener productos por género
    public List<ProductoModels> getProductosByGenero(String genero) {
        return productoRepository.findByGenero(genero);
    }

    // Método para actualizar el producto en todos los carritos
    private void updateProductInCarritos(ProductoModels updatedProducto) {
        try {
            CompletableFuture<List<CarritoModels>> futureCarritos = carritoService.getAllCarritos();
            List<CarritoModels> carritos = futureCarritos.get();

            for (CarritoModels carrito : carritos) {
                boolean updated = false;
                for (CarritoModels.Producto producto : carrito.getProducto()) {
                    if (producto.getCodUnico().equals(updatedProducto.getCodUnico())) {
                        producto.setNombre(updatedProducto.getNombre());
                        producto.setPrecio(updatedProducto.getPrecio());
                        producto.setCategoria(updatedProducto.getCategoria());
                        producto.setImagenes(updatedProducto.getImagenes());
                        producto.setCaracteristicas(updatedProducto.getCaracteristicas());
                        producto.setGenero(updatedProducto.getGenero());
                        producto.setOferta(updatedProducto.isOferta());
                        producto.setStock(updatedProducto.getStock());
                        producto.setEstado(updatedProducto.getEstado());
                        producto.setDescripcion(updatedProducto.getDescripcion());
                        producto.setDescuento(updatedProducto.getDescuento());
                        updated = true;
                    }
                }
                if (updated) {
                    carritoService.updateCarrito(carrito.getId_carrito(), carrito);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}