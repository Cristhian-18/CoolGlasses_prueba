package com.api.CoolGlasses.services;

import com.api.CoolGlasses.models.CarritoModels;
import com.api.CoolGlasses.models.CarritoModels.Producto;
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
public class CarritoService {

    private final DatabaseReference databaseReference;

    @Autowired
    public CarritoService(FirebaseDatabase firebaseDatabase) {
        this.databaseReference = firebaseDatabase.getReference("carritos");
    }

    public CompletableFuture<CarritoModels> saveCarrito(CarritoModels carrito) {
        CompletableFuture<CarritoModels> future = new CompletableFuture<>();

        // Verificar si el usuario ya tiene un carrito
        getCarritoByCorreo(carrito.getUsuario().getCorreo()).thenAccept(existingCarrito -> {
            if (existingCarrito != null) {
                // Verificar si el producto ya está en el carrito
                boolean productoExiste = existingCarrito.getProducto().stream()
                        .anyMatch(p -> p.getCodUnico().equals(carrito.getProducto().get(0).getCodUnico()));

                if (productoExiste) {
                    future.completeExceptionally(new Exception("Producto ya existe en el carrito"));
                } else {
                    // Agregar el nuevo producto al carrito existente
                    existingCarrito.getProducto().add(carrito.getProducto().get(0));
                    updateCarrito(existingCarrito.getId_carrito(), existingCarrito).thenAccept(result -> {
                        future.complete(existingCarrito);
                    }).exceptionally(ex -> {
                        future.completeExceptionally(ex);
                        return null;
                    });
                }
            } else {
                // Crear un nuevo carrito
                String id = databaseReference.push().getKey();
                carrito.setId_carrito(id);
                ApiFuture<Void> apiFuture = databaseReference.child(id).setValueAsync(carrito);
                ApiFutures.addCallback(apiFuture, new ApiFutureCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        future.complete(carrito);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        future.completeExceptionally(t);
                    }
                }, Runnable::run);
            }
        }).exceptionally(ex -> {
            future.completeExceptionally(ex);
            return null;
        });

        return future;
    }

    public CompletableFuture<CarritoModels> addOrUpdateCarrito(CarritoModels carrito) {
        CompletableFuture<CarritoModels> future = new CompletableFuture<>();

        getCarritoByCorreo(carrito.getUsuario().getCorreo()).thenAccept(existingCarrito -> {
            if (existingCarrito != null) {
                // Verificar si el producto ya está en el carrito
                Producto newProduct = carrito.getProducto().get(0);
                boolean productoExiste = false;
                for (Producto p : existingCarrito.getProducto()) {
                    if (p.getCodUnico().equals(newProduct.getCodUnico())) {
                        productoExiste = true;
                        int nuevaCantidad = p.getCantidad_seleccionada() + newProduct.getCantidad_seleccionada();
                        if (nuevaCantidad > p.getStock()) {
                            future.completeExceptionally(new Exception("Cantidad seleccionada supera el stock disponible"));
                        } else {
                            p.setCantidad_seleccionada(nuevaCantidad);
                            updateCarrito(existingCarrito.getId_carrito(), existingCarrito).thenAccept(result -> {
                                future.complete(existingCarrito);
                            }).exceptionally(ex -> {
                                future.completeExceptionally(ex);
                                return null;
                            });
                        }
                        break;
                    }
                }
                if (!productoExiste) {
                    // Agregar el nuevo producto al carrito existente
                    existingCarrito.getProducto().add(newProduct);
                    updateCarrito(existingCarrito.getId_carrito(), existingCarrito).thenAccept(result -> {
                        future.complete(existingCarrito);
                    }).exceptionally(ex -> {
                        future.completeExceptionally(ex);
                        return null;
                    });
                }
            } else {
                // Crear un nuevo carrito
                String id = databaseReference.push().getKey();
                carrito.setId_carrito(id);
                ApiFuture<Void> apiFuture = databaseReference.child(id).setValueAsync(carrito);
                ApiFutures.addCallback(apiFuture, new ApiFutureCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        future.complete(carrito);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        future.completeExceptionally(t);
                    }
                }, Runnable::run);
            }
        }).exceptionally(ex -> {
            future.completeExceptionally(ex);
            return null;
        });

        return future;
    }

    public CompletableFuture<CarritoModels> updateProductQuantity(CarritoModels carrito) {
        CompletableFuture<CarritoModels> future = new CompletableFuture<>();

        getCarritoByCorreo(carrito.getUsuario().getCorreo()).thenAccept(existingCarrito -> {
            if (existingCarrito != null) {
                // Verificar si el producto ya está en el carrito
                Producto newProduct = carrito.getProducto().get(0);
                boolean productoExiste = false;
                for (Producto p : existingCarrito.getProducto()) {
                    if (p.getCodUnico().equals(newProduct.getCodUnico())) {
                        productoExiste = true;
                        if (newProduct.getCantidad_seleccionada() > p.getStock()) {
                            future.completeExceptionally(new Exception("Cantidad seleccionada supera el stock disponible"));
                        } else {
                            p.setCantidad_seleccionada(newProduct.getCantidad_seleccionada());
                            updateCarrito(existingCarrito.getId_carrito(), existingCarrito).thenAccept(result -> {
                                future.complete(existingCarrito);
                            }).exceptionally(ex -> {
                                future.completeExceptionally(ex);
                                return null;
                            });
                        }
                        break;
                    }
                }
                if (!productoExiste) {
                    future.completeExceptionally(new Exception("Producto no encontrado en el carrito"));
                }
            } else {
                future.completeExceptionally(new Exception("Carrito no encontrado para el correo proporcionado"));
            }
        }).exceptionally(ex -> {
            future.completeExceptionally(ex);
            return null;
        });

        return future;
    }


    public CompletableFuture<Void> removeProductFromCarrito(String correo, String codUnico) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        getCarritoByCorreo(correo).thenAccept(existingCarrito -> {
            if (existingCarrito != null) {
                List<Producto> productos = existingCarrito.getProducto();
                boolean productoEliminado = productos.removeIf(p -> p.getCodUnico().equals(codUnico));

                if (productoEliminado) {
                    updateCarrito(existingCarrito.getId_carrito(), existingCarrito).thenAccept(result -> {
                        future.complete(null);
                    }).exceptionally(ex -> {
                        future.completeExceptionally(ex);
                        return null;
                    });
                } else {
                    future.completeExceptionally(new Exception("Producto no encontrado en el carrito"));
                }
            } else {
                future.completeExceptionally(new Exception("Carrito no encontrado para el correo proporcionado"));
            }
        }).exceptionally(ex -> {
            future.completeExceptionally(ex);
            return null;
        });

        return future;
    }

    public CompletableFuture<CarritoModels> getCarritoByCorreo(String correo) {
        CompletableFuture<CarritoModels> future = new CompletableFuture<>();
        databaseReference.orderByChild("usuario/correo").equalTo(correo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        CarritoModels carrito = dataSnapshot.getValue(CarritoModels.class);
                        future.complete(carrito);
                        return;
                    }
                }
                future.complete(null);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });
        return future;
    }

    public CompletableFuture<CarritoModels> getCarritoById(String id) {
        CompletableFuture<CarritoModels> future = new CompletableFuture<>();
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    CarritoModels carrito = snapshot.getValue(CarritoModels.class);
                    future.complete(carrito);
                } else {
                    future.completeExceptionally(new Exception("Carrito no encontrado"));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });
        return future;
    }

    public CompletableFuture<Void> updateCarrito(String id, CarritoModels carrito) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        ApiFuture<Void> apiFuture = databaseReference.child(id).setValueAsync(carrito);
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

    public CompletableFuture<Void> deleteCarrito(String id) {
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

    public CompletableFuture<List<CarritoModels>> getAllCarritos() {
        CompletableFuture<List<CarritoModels>> future = new CompletableFuture<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<CarritoModels> carritos = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CarritoModels carrito = dataSnapshot.getValue(CarritoModels.class);
                    carritos.add(carrito);
                }
                future.complete(carritos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });
        return future;
    }
}
