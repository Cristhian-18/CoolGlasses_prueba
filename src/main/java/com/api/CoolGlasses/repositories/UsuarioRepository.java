package com.api.CoolGlasses.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.api.CoolGlasses.models.UsuarioModels;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioModels, String> {
    UsuarioModels findByCorreo(String correo);
}
