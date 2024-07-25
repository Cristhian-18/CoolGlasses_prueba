package com.api.CoolGlasses.services;

import com.api.CoolGlasses.models.UsuarioModels;
import com.api.CoolGlasses.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    // Guardar un nuevo usuario
    public UsuarioModels saveUsuario(UsuarioModels usuario) {
        // Asegurarse de que el ID sea nulo para que MongoDB genere uno nuevo automáticamente
        usuario.setId(null);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<UsuarioModels> getUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por ID
    public UsuarioModels getUsuarioById(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Actualizar un usuario existente
    public UsuarioModels updateUsuario(String id, UsuarioModels usuario) {
        if (!usuarioRepository.existsById(id)) {
            return null;
        }
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    // Método de autenticación
    public UsuarioModels authenticate(String correo, String contrasena) {
        logger.info("Attempting to authenticate user with email: " + correo);
        UsuarioModels usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null) {
            logger.info("User found: " + correo);
            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                logger.info("Authentication successful for user: " + correo);
                return usuario;
            } else {
                logger.info("Authentication failed for user: " + correo);
            }
        } else {
            logger.info("User not found: " + correo);
        }
        return null;
    }
    // Eliminar un usuario por ID
    public boolean deleteUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }
}
