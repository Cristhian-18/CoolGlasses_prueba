package com.api.CoolGlasses.controllers;
import com.api.CoolGlasses.models.UsuarioModels;
import com.api.CoolGlasses.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    // Crear un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<UsuarioModels> registerUsuario(@RequestBody UsuarioModels usuario) {
        UsuarioModels savedUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(savedUsuario);
    }

    // Autenticar un usuario
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        UsuarioModels usuario = usuarioService.authenticate(credentials.get("correo"), credentials.get("contrasena"));
        if (usuario != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("usuario", usuario);
            response.put("rol", usuario.getRol());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // Crear un nuevo usuario
    //@PostMapping
   // public ResponseEntity<UsuarioModels> saveUsuario(@RequestBody UsuarioModels usuario) {
    //    UsuarioModels savedUsuario = usuarioService.saveUsuario(usuario);
    //    return ResponseEntity.ok(savedUsuario);
    //}

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioModels>> getUsuarios() {
        List<UsuarioModels> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModels> getUsuarioByID(@PathVariable String id) {
        UsuarioModels usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // Actualizar un usuario por ID
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModels> updateUsuario(@PathVariable String id, @RequestBody UsuarioModels usuario) {
        UsuarioModels updatedUsuario = usuarioService.updateUsuario(id, usuario);
        if (updatedUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUsuario);
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
        boolean deleted = usuarioService.deleteUsuario(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
