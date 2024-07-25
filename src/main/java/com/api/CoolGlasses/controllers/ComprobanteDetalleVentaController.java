package com.api.CoolGlasses.controllers;


import com.api.CoolGlasses.models.ComprobanteDetalleVentaModels;
import com.api.CoolGlasses.services.ComprobanteDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
@RestController
@RequestMapping("/api/comprobantedv")

public class ComprobanteDetalleVentaController {
    @Autowired
    private ComprobanteDetalleVentaService comprobanteService;



    // Crear un nuevo comprobante detalle venta
    @PostMapping
    public ResponseEntity<ComprobanteDetalleVentaModels> saveComprobanteDetalleVenta(@RequestBody ComprobanteDetalleVentaModels comprobante) throws ExecutionException, InterruptedException {
        ComprobanteDetalleVentaModels savedComprobante = comprobanteService.saveComprobanteDetalleVenta(comprobante).get();
        return ResponseEntity.ok(savedComprobante);
    }

    // Obtener todos los comprobantes detalle venta
    @GetMapping
    public ResponseEntity<List<ComprobanteDetalleVentaModels>> getAllComprobanteDetalleVentas() throws ExecutionException, InterruptedException {
        List<ComprobanteDetalleVentaModels> comprobantes = comprobanteService.getAllComprobanteDetalleVentas().get();
        return ResponseEntity.ok(comprobantes);
    }

    // Obtener un comprobante detalle venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteDetalleVentaModels> getComprobanteDetalleVentaById(@PathVariable String id) throws ExecutionException, InterruptedException {
        ComprobanteDetalleVentaModels comprobante = comprobanteService.getComprobanteDetalleVentaById(id).get();
        if (comprobante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comprobante);
    }

    // Actualizar un comprobante detalle venta por ID
    @PutMapping("/{id}")
    public ResponseEntity<ComprobanteDetalleVentaModels> updateComprobanteDetalleVenta(@PathVariable String id, @RequestBody ComprobanteDetalleVentaModels comprobante) throws ExecutionException, InterruptedException {
        comprobante.setId_comprobante_detalle(id);
        comprobanteService.updateComprobanteDetalleVenta(id, comprobante).get();
        return ResponseEntity.ok(comprobante);
    }

    // Eliminar un comprobante detalle venta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComprobanteDetalleVenta(@PathVariable String id) throws ExecutionException, InterruptedException {
        comprobanteService.deleteComprobanteDetalleVenta(id).get();
        return ResponseEntity.noContent().build();
    }
}
