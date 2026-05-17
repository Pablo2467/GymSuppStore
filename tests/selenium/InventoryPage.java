package com.gymsupp.store.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model - Página de Inventario (/)
 * Encapsula todos los selectores e interacciones del dashboard principal
 */
public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameField        = By.name("name");
    private final By descriptionField = By.name("description");
    private final By priceField       = By.name("price");
    private final By stockField       = By.name("stock");
    private final By categoryField    = By.name("category");
    private final By submitButton     = By.cssSelector("button[type='submit']");
    private final By tableRows        = By.cssSelector("tbody tr[data-product]");
    private final By tableElement     = By.cssSelector("table");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("http://localhost:8080/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton));
    }

    public void addProduct(String name, String description,
                           String price, String stock, String category) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).clear();
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(descriptionField).sendKeys(description);
        driver.findElement(priceField).sendKeys(price);
        driver.findElement(stockField).sendKeys(stock);
        driver.findElement(categoryField).sendKeys(category);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        // Espera a que la página recargue mostrando el producto
        wait.until(ExpectedConditions.urlContains("localhost:8080"));
    }

    public List<WebElement> getTableRows() {
        return driver.findElements(tableRows);
    }

    public boolean isTableVisible() {
        return !driver.findElements(tableElement).isEmpty();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isProductInTable(String productName) {
        return driver.getPageSource().contains(productName);
    }
}