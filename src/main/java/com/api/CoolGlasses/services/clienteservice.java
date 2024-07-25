package com.api.CoolGlasses.services;

import com.api.CoolGlasses.models.Cliente;
import com.api.CoolGlasses.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class clienteservice {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    public Cliente getClienteByDNI(String dni) {
        return clienteRepository.findByDni(dni);
    }

    public Cliente updateCliente(String id, Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            return null;
        }
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public boolean deleteCliente(String id) {
        if (!clienteRepository.existsById(id)) {
            return false;
        }
        clienteRepository.deleteById(id);
        return true;
    }




}
