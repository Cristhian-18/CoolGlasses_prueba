package com.api.CoolGlasses.services;

import com.api.CoolGlasses.models.Cliente;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.core.ApiFutureCallback;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class clienteservicefirebase {

    private final DatabaseReference databaseReference;

    @Autowired
    public clienteservicefirebase(FirebaseDatabase firebaseDatabase) {
        this.databaseReference = firebaseDatabase.getReference("clientes");
    }

    public CompletableFuture<Cliente> saveCliente(Cliente cliente) {
        CompletableFuture<Cliente> future = new CompletableFuture<>();
        String id = databaseReference.push().getKey();
        cliente.setId(id);
        ApiFuture<Void> apiFuture = databaseReference.child(id).setValueAsync(cliente);
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                future.complete(cliente);
            }

            @Override
            public void onFailure(Throwable t) {
                future.completeExceptionally(t);
            }
        }, Runnable::run);
        return future;
    }

    public CompletableFuture<Cliente> getClienteById(String id) {
        CompletableFuture<Cliente> future = new CompletableFuture<>();
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Cliente cliente = snapshot.getValue(Cliente.class);
                    future.complete(cliente);
                } else {
                    future.completeExceptionally(new Exception("Cliente no encontrado"));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });
        return future;
    }

    public CompletableFuture<Void> updateCliente(String id, Cliente cliente) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        ApiFuture<Void> apiFuture = databaseReference.child(id).setValueAsync(cliente);
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                future.complete(null);
            }

            @Override
            public void onFailure(Throwable t) {
                future.completeExceptionally(t);
            }
        }, Runnable::run);
        return future;
    }

    public CompletableFuture<Void> deleteCliente(String id) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        ApiFuture<Void> apiFuture = databaseReference.child(id).removeValueAsync();
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                future.complete(null);
            }

            @Override
            public void onFailure(Throwable t) {
                future.completeExceptionally(t);
            }
        }, Runnable::run);
        return future;
    }

    public CompletableFuture<List<Cliente>> getAllClientes() {
        CompletableFuture<List<Cliente>> future = new CompletableFuture<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Cliente> clientes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Cliente cliente = dataSnapshot.getValue(Cliente.class);
                    clientes.add(cliente);
                }
                future.complete(clientes);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });
        return future;
    }
}
