package com.gohealth.webapp;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertTrue;

public class SeleniumTests {

    private WebDriver driver;
    private final String fname = "Janko";
    private final String lname = "Hrasko";
    private final String email = "Janko.Hrasko@gmail.com";

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\liagi\\Downloads\\Drivers\\geckodriver.exe");
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
                System.getProperty("java.io.tmpdir") + "geckodriverlogs.txt");
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void success() {
        fillForm();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        assertTrue(wait.until(ExpectedConditions.urlToBe("http://localhost:8080/add")));
    }

    @Test
    public void emptyFirstNameError() {
        emptyFieldError("lname", "email",
                lname, email, "fnameerror", "first");
    }

    @Test
    public void emtyLastNameError() {
        emptyFieldError("fname", "email",
                fname, email, "lnameerror", "last");
    }

    @Test
    public void emptyEmailError() {
        emptyFieldError("fname", "lname",
                fname, lname, "emailerror", "email");
    }

    @Test
    public void emailExistsError() {
        fillForm();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("emailerror")));
        String message = driver.findElement(By.id("emailerror")).getText();
        assertTrue(message.contains("exists"));

    }

    private void fillForm(){
        driver.get("http://localhost:8080/form");
        driver.findElement(By.id("fname")).sendKeys(fname);
        driver.findElement(By.id("lname")).sendKeys(lname);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("submit")).submit();
    }

    private void emptyFieldError(String elementName1, String elementName2,
                                 String value1, String value2, String missingElementName, String errorWord) {

        driver.get("http://localhost:8080/form");
        driver.findElement(By.id(elementName1)).sendKeys(value1);
        driver.findElement(By.id(elementName2)).sendKeys(value2);
        driver.findElement(By.id("submit")).submit();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(missingElementName)));
        String message = driver.findElement(By.id(missingElementName)).getText();
        assertTrue(message.contains(errorWord));
    }
}
