package com.api.CoolGlasses.repositories;

import com.api.CoolGlasses.models.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
    Cliente findByDni(String dni);
}
