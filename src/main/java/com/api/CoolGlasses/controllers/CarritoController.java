package com.api.CoolGlasses.controllers;

import com.api.CoolGlasses.models.CarritoModels;
import com.api.CoolGlasses.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Crear un nuevo carrito
    @PostMapping
    public ResponseEntity<?> saveCarrito(@RequestBody CarritoModels carrito) throws ExecutionException, InterruptedException {
        try {
            CarritoModels savedCarrito = carritoService.saveCarrito(carrito).get();
            return ResponseEntity.ok(savedCarrito);
        } catch (ExecutionException e) {
            if (e.getCause().getMessage().equals("Producto ya existe en el carrito")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Producto ya existe en el carrito");
            }
            throw e;
        }
    }

    // Agregar o actualizar un producto en el carrito
    @PostMapping("/addOrUpdate")
    public ResponseEntity<?> addOrUpdateCarrito(@RequestBody CarritoModels carrito) throws ExecutionException, InterruptedException {
        try {
            CarritoModels updatedCarrito = carritoService.addOrUpdateCarrito(carrito).get();
            return ResponseEntity.ok(updatedCarrito);
        } catch (ExecutionException e) {
            if (e.getCause().getMessage().equals("Cantidad seleccionada supera el stock disponible")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cantidad seleccionada supera el stock disponible");
            }
            throw e;
        }
    }

    // Actualizar solo la cantidad seleccionada de un producto en el carrito
    @PutMapping("/updateQuantity")
    public ResponseEntity<?> updateProductQuantity(@RequestBody CarritoModels carrito) throws ExecutionException, InterruptedException {
        try {
            CarritoModels updatedCarrito = carritoService.updateProductQuantity(carrito).get();
            return ResponseEntity.ok(updatedCarrito);
        } catch (ExecutionException e) {
            if (e.getCause().getMessage().equals("Cantidad seleccionada supera el stock disponible") ||
                    e.getCause().getMessage().equals("Producto no encontrado en el carrito") ||
                    e.getCause().getMessage().equals("Carrito no encontrado para el correo proporcionado")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().getMessage());
            }
            throw e;
        }
    }

    // Obtener todos los carritos
    @GetMapping
    public ResponseEntity<List<CarritoModels>> getAllCarritos() throws ExecutionException, InterruptedException {
        List<CarritoModels> carritos = carritoService.getAllCarritos().get();
        return ResponseEntity.ok(carritos);
    }

    // Obtener un carrito por ID
    @GetMapping("/{id}")
    public ResponseEntity<CarritoModels> getCarritoById(@PathVariable String id) throws ExecutionException, InterruptedException {
        CarritoModels carrito = carritoService.getCarritoById(id).get();
        if (carrito == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carrito);
    }

    // Obtener un carrito por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<CarritoModels> getCarritoByCorreo(@PathVariable String correo) throws ExecutionException, InterruptedException {
        CarritoModels carrito = carritoService.getCarritoByCorreo(correo).get();
        if (carrito == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carrito);
    }

    // Actualizar un carrito por ID
    @PutMapping("/{id}")
    public ResponseEntity<CarritoModels> updateCarrito(@PathVariable String id, @RequestBody CarritoModels carrito) throws ExecutionException, InterruptedException {
        carrito.setId_carrito(id);
        carritoService.updateCarrito(id, carrito).get();
        return ResponseEntity.ok(carrito);
    }

    // Eliminar un carrito por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable String id) throws ExecutionException, InterruptedException {
        carritoService.deleteCarrito(id).get();
        return ResponseEntity.noContent().build();
    }

    // Eliminar un producto del carrito por correo y código de producto
    @DeleteMapping("/removeProduct/{correo}/{codUnico}")
    public ResponseEntity<?> removeProductFromCarrito(@PathVariable String correo, @PathVariable String codUnico) throws ExecutionException, InterruptedException {
        try {
            carritoService.removeProductFromCarrito(correo, codUnico).get();
            return ResponseEntity.noContent().build();
        } catch (ExecutionException e) {
            if (e.getCause().getMessage().equals("Producto no encontrado en el carrito") ||
                    e.getCause().getMessage().equals("Carrito no encontrado para el correo proporcionado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCause().getMessage());
            }
            throw e;
        }
    }
}
