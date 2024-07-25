package com.api.CoolGlasses.controllers;

import com.api.CoolGlasses.models.Cliente;
import com.api.CoolGlasses.services.clienteservicefirebase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/clientesfirebase")
public class ClienteControllerFirebase {

    @Autowired
    private clienteservicefirebase clienteService;


    @GetMapping("/pruebas")
    public ResponseEntity<String> pruebas() {
        return ResponseEntity.ok("Ruta de prueba de mi API Restful con Spring Boot y Firebase");
    }

    @PostMapping
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) throws ExecutionException, InterruptedException {
        Cliente savedCliente = clienteService.saveCliente(cliente).get();
        return ResponseEntity.ok(savedCliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() throws ExecutionException, InterruptedException {
        List<Cliente> clientes = clienteService.getAllClientes().get();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable String id) throws ExecutionException, InterruptedException {
        Cliente cliente = clienteService.getClienteById(id).get();
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String id, @RequestBody Cliente cliente) throws ExecutionException, InterruptedException {
        cliente.setId(id);
        clienteService.updateCliente(id, cliente).get();
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) throws ExecutionException, InterruptedException {
        clienteService.deleteCliente(id).get();
        return ResponseEntity.noContent().build();
    }
}
