package com.gymsupp.store;

import com.gymsupp.store.controller.ProductController;
import com.gymsupp.store.model.Product;
import com.gymsupp.store.service.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del controlador de productos")
class ProductControllerTest {

    @Mock
    private ProductService service;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController controller;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Whey Protein", "Proteína", 150000.0, 5, "Proteína");
    }

    @Test
    @DisplayName("Debe retornar la vista index con lista de productos")
    void testListProducts() {
        when(service.findAll()).thenReturn(List.of(product));

        String view = controller.listProducts(model);

        assertEquals("index", view);
        verify(model).addAttribute("products", List.of(product));
        verify(service, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe guardar producto y redirigir al inicio")
    void testSaveProduct() {
        when(service.save(product)).thenReturn(product);

        String view = controller.saveProduct(product);

        assertEquals("redirect:/", view);
        verify(service, times(1)).save(product);
    }

    @Test
    @DisplayName("Debe eliminar producto y redirigir al inicio")
    void testDeleteProduct() {
        doNothing().when(service).delete(1L);

        String view = controller.deleteProduct(1L);

        assertEquals("redirect:/", view);
        verify(service, times(1)).delete(1L);
    }

    @Test
    @DisplayName("Debe retornar la vista edit con el producto a editar")
    void testEditForm() {
        when(service.findById(1L)).thenReturn(product);

        String view = controller.editForm(1L, model);

        assertEquals("edit", view);
        verify(model).addAttribute("product", product);
    }

    @Test
    @DisplayName("Debe actualizar producto y redirigir al inicio")
    void testUpdateProduct() {
        when(service.save(product)).thenReturn(product);

        String view = controller.updateProduct(product);

        assertEquals("redirect:/", view);
        verify(service, times(1)).save(product);
    }
}
