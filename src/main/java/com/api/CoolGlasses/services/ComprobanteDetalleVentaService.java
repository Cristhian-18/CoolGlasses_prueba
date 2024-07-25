package com.api.CoolGlasses.services;

import com.api.CoolGlasses.models.ComprobanteDetalleVentaModels;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.core.ApiFutureCallback;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
@Service
public class ComprobanteDetalleVentaService {
    private final DatabaseReference databaseReference;

    @Autowired
    public ComprobanteDetalleVentaService(FirebaseDatabase firebaseDatabase) {
        this.databaseReference = firebaseDatabase.getReference("comprobante_detalle_ventas");
    }

    public CompletableFuture<ComprobanteDetalleVentaModels> saveComprobanteDetalleVenta(ComprobanteDetalleVentaModels comprobante) {
        CompletableFuture<ComprobanteDetalleVentaModels> future = new CompletableFuture<>();
        String id = databaseReference.push().getKey();
        comprobante.setId_comprobante_detalle(id);
        ApiFuture<Void> apiFuture = databaseReference.child(id).setValueAsync(comprobante);
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                future.complete(comprobante);
            }

            @Override
            public void onFailure(Throwable t) {
                future.completeExceptionally(t);
            }
        }, Runnable::run);
        return future;
    }

    public CompletableFuture<ComprobanteDetalleVentaModels> getComprobanteDetalleVentaById(String id) {
        CompletableFuture<ComprobanteDetalleVentaModels> future = new CompletableFuture<>();
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ComprobanteDetalleVentaModels comprobante = snapshot.getValue(ComprobanteDetalleVentaModels.class);
                    future.complete(comprobante);
                } else {
                    future.completeExceptionally(new Exception("Comprobante no encontrado"));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });
        return future;
    }

    public CompletableFuture<Void> updateComprobanteDetalleVenta(String id, ComprobanteDetalleVentaModels comprobante) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        ApiFuture<Void> apiFuture = databaseReference.child(id).setValueAsync(comprobante);
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

    public CompletableFuture<Void> deleteComprobanteDetalleVenta(String id) {
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

    public CompletableFuture<List<ComprobanteDetalleVentaModels>> getAllComprobanteDetalleVentas() {
        CompletableFuture<List<ComprobanteDetalleVentaModels>> future = new CompletableFuture<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<ComprobanteDetalleVentaModels> comprobantes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ComprobanteDetalleVentaModels comprobante = dataSnapshot.getValue(ComprobanteDetalleVentaModels.class);
                    comprobantes.add(comprobante);
                }
                future.complete(comprobantes);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });
        return future;
    }
}
