package com.api.CoolGlasses.controllers;

import com.api.CoolGlasses.models.ProductoModels;
import com.api.CoolGlasses.models.UsuarioModels;
import com.api.CoolGlasses.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<ProductoModels> saveProducto(@RequestBody ProductoModels producto) {
        ProductoModels savedProducto = productoService.saveProducto(producto);
        return ResponseEntity.ok(savedProducto);
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoModels>> getProductos() {
        List<ProductoModels> productos = productoService.getProductos();
        return ResponseEntity.ok(productos);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoModels> getProductoByID(@PathVariable String id) {
        ProductoModels producto = productoService.getProductosById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    // Obtener un producto por código único
    @GetMapping("/codigo/{codUnico}")
    public ResponseEntity<ProductoModels> getProductoByCodUnico(@PathVariable String codUnico) {
        ProductoModels producto = productoService.getProductoByCodUnico(codUnico);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    // Actualizar un producto por id
    @PutMapping("/{id}")
    public ResponseEntity<ProductoModels> updateProducto(@PathVariable String id, @RequestBody ProductoModels producto) {
        ProductoModels updatedProducto = productoService.updateProducto(id, producto);
        if (updatedProducto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProducto);
    }

    // Eliminar un producto por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable String id) {
        boolean deleted = productoService.deleteProducto(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


    //Filtros adicionales para filtrado por backend y frontend//

    // Obtener productos por género
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<ProductoModels>> getProductosByGenero(@PathVariable String genero) {
        List<ProductoModels> productos = productoService.getProductosByGenero(genero);
        return ResponseEntity.ok(productos);
    }
}
