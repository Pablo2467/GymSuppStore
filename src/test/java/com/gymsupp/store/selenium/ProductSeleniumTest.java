package com.gymsupp.store.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas funcionales Selenium - GymSupp Store
 * Compatibles con Linux (headless), CI/CD y ejecución local Windows/Mac.
 * Patrón: Page Object Model (POM)
 * Navegadores: Chrome y Firefox (controlado por variable de entorno BROWSER)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Pruebas funcionales Selenium - GymSupp Store")
public class ProductSeleniumTest {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static InventoryPage inventoryPage;

    // ---------------------------------------------------------------
    // CONFIGURACIÓN: detecta navegador por variable de entorno BROWSER
    // Por defecto usa Chrome. Para Firefox: export BROWSER=firefox
    // ---------------------------------------------------------------
    @BeforeAll
    static void setUp() {
        String browser = System.getenv("BROWSER");
        if (browser == null) browser = System.getProperty("browser", "chrome");

        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");          // headless en CI/Linux
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
            driver = new FirefoxDriver(options);
        } else {
            WebDriverManager.chromedriver().setup();     // descarga automática, sin path hardcodeado
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");      // headless en CI/Linux
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);
        }

        loginPage     = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ---------------------------------------------------------------
    // PRUEBA 1: Login exitoso con credenciales válidas (admin)
    // ---------------------------------------------------------------
    @Test
    @Order(1)
    @DisplayName("TC-01: Debe iniciar sesión correctamente con credenciales válidas")
    void testLoginExitoso() {
        loginPage.login("admin", "gym123");
        String url = driver.getCurrentUrl();
        assertFalse(url.contains("login"),
                "Tras login exitoso no debe permanecer en /login");
        assertTrue(url.contains("localhost:8080"),
                "Debe redirigir a la aplicación");
    }

    // ---------------------------------------------------------------
    // PRUEBA 2: La página de inventario carga correctamente
    // ---------------------------------------------------------------
    @Test
    @Order(2)
    @DisplayName("TC-02: Debe mostrar la página de inventario correctamente")
    void testPaginaPrincipalCarga() {
        inventoryPage.open();
        String title = inventoryPage.getPageTitle();
        assertTrue(title.contains("GymSupp Store"),
                "El título debe contener 'GymSupp Store'");
        assertTrue(inventoryPage.isTableVisible(),
                "La tabla de productos debe ser visible");
    }

    // ---------------------------------------------------------------
    // PRUEBA 3: Agregar un producto nuevo
    // ---------------------------------------------------------------
    @Test
    @Order(3)
    @DisplayName("TC-03: Debe agregar un producto nuevo correctamente")
    void testAgregarProducto() {
        inventoryPage.open();
        inventoryPage.addProduct(
                "Creatina Monohidrato",
                "Aumenta fuerza y rendimiento",
                "85000",
                "20",
                "Fuerza"
        );
        assertTrue(inventoryPage.isProductInTable("Creatina Monohidrato"),
                "El producto recién creado debe aparecer en la tabla");
    }

    // ---------------------------------------------------------------
    // PRUEBA 4: Tabla de productos tiene al menos un registro
    // ---------------------------------------------------------------
    @Test
    @Order(4)
    @DisplayName("TC-04: Debe mostrar la tabla con al menos un producto registrado")
    void testListarProductos() {
        inventoryPage.open();
        int rows = inventoryPage.getTableRows().size();
        assertTrue(rows > 0,
                "La tabla debe contener mínimo 1 producto");
    }

    // ---------------------------------------------------------------
    // PRUEBA 5: Login con credenciales incorrectas muestra error
    // ---------------------------------------------------------------
    @Test
    @Order(5)
    @DisplayName("TC-05: Debe mostrar error con credenciales incorrectas")
    void testLoginInvalido() {
        loginPage.open();
        loginPage.enterUsername("usuariofalso");
        loginPage.enterPassword("clavefalsa");
        loginPage.clickLogin();
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("error") || url.contains("login"),
                "Debe permanecer en la página de login indicando error");
    }
}