package com.api.CoolGlasses.controllers;

import com.api.CoolGlasses.models.Cliente;
import com.api.CoolGlasses.services.clienteservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private clienteservice clienteService;

    // Endpoint de prueba
    @GetMapping("/pruebas")
    public ResponseEntity<String> pruebas() {
        return ResponseEntity.ok("Ruta de prueba de mi API Restful con Spring Boot y MongoDB");
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteService.saveCliente(cliente);
        return ResponseEntity.ok(savedCliente);
    }
    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        return ResponseEntity.ok(clientes);
    }

    // Obtener un cliente por DNI
    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> getClienteByDNI(@PathVariable String dni) {
        Cliente cliente = clienteService.getClienteByDNI(dni);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    // Actualizar un cliente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String id, @RequestBody Cliente cliente) {
        Cliente updatedCliente = clienteService.updateCliente(id, cliente);
        if (updatedCliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCliente);
    }

    // Eliminar un cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {
        boolean deleted = clienteService.deleteCliente(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


}
