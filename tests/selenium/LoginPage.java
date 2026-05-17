package com.gymsupp.store.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model - Página de Login
 * Encapsula todos los selectores e interacciones de /login
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton   = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("http://localhost:8080/login");
    }

    public void enterUsername(String username) {
        WebDriver d = driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    /** Método conveniente: abre, escribe credenciales y hace clic */
    public void login(String username, String password) {
        open();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        // Espera a que la URL cambie (login procesado)
        wait.until(d -> !d.getCurrentUrl().equals("http://localhost:8080/login"));
    }

    public String getTitle() {
        return driver.getTitle();
    }
}