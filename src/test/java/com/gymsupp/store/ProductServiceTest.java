package com.gymsupp.store;

import com.gymsupp.store.model.Product;
import com.gymsupp.store.repository.ProductRepository;
import com.gymsupp.store.service.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del servicio de productos")
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Whey Protein", "Proteína de suero", 150000.0, 5, "Proteína");
    }

    // ─── PRUEBA 1: Guardar producto ───────────────────────────────
    @Test
    @DisplayName("Debe guardar un producto correctamente")
    void testSaveProduct() {
        when(repository.save(product)).thenReturn(product);

        Product result = service.save(product);

        assertNotNull(result);
        assertEquals("Whey Protein", result.getName());
        assertEquals(150000.0, result.getPrice());
        verify(repository, times(1)).save(product);
    }

    // ─── PRUEBA 2: Listar todos los productos ─────────────────────
    @Test
    @DisplayName("Debe retornar la lista de todos los productos")
    void testFindAllProducts() {
        Product product2 = new Product(2L, "Creatina", "Monohidrato", 80000.0, 10, "Fuerza");
        when(repository.findAll()).thenReturn(List.of(product, product2));

        List<Product> result = service.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Creatina", result.get(1).getName());
        verify(repository, times(1)).findAll();
    }

    // ─── PRUEBA 3: Buscar producto por ID existente ───────────────
    @Test
    @DisplayName("Debe encontrar un producto por su ID")
    void testFindByIdSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        Product result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Whey Protein", result.getName());
    }

    // ─── PRUEBA 4: Buscar producto con ID inexistente ─────────────
    @Test
    @DisplayName("Debe lanzar excepción si el producto no existe")
    void testFindByIdNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(99L));
    }

    // ─── PRUEBA 5: Eliminar producto ──────────────────────────────
    @Test
    @DisplayName("Debe eliminar un producto por su ID")
    void testDeleteProduct() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    // ─── PRUEBA 6: Guardar producto con precio cero ───────────────
    @Test
    @DisplayName("Debe guardar producto aunque el precio sea cero")
    void testSaveProductWithZeroPrice() {
        Product freeProduct = new Product(3L, "Muestra gratis", "Sample", 0.0, 1, "Promo");
        when(repository.save(freeProduct)).thenReturn(freeProduct);

        Product result = service.save(freeProduct);

        assertEquals(0.0, result.getPrice());
        assertNotNull(result.getName());
    }

    // ─── PRUEBA 7: Lista vacía ────────────────────────────────────
    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay productos")
    void testFindAllEmpty() {
        when(repository.findAll()).thenReturn(List.of());

        List<Product> result = service.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}