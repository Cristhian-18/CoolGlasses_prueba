package com.api.CoolGlasses.repositories;

import com.api.CoolGlasses.models.ProductoModels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends MongoRepository<ProductoModels, String> {
    ProductoModels findByCodUnico(String codUnico);
    List<ProductoModels> findByGenero(String genero);
}

