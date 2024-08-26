import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtsAutotest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Используем WebDriver Manager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://mts.by");

        // Обработка окна куки
        closeCookies();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void closeCookies() {
        try {
            // Замените "cookie-accept-button" на реальный идентификатор кнопки принятия куки
            WebElement cookieCloseButton = driver.findElement(By.xpath("//button[contains(text(),'Принять')]"));
            if (cookieCloseButton.isDisplayed()) {
                cookieCloseButton.click();
                System.out.println("Cookies успешно закрыты.");
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("Элемент не найден");
        } catch (TimeoutException e) {
            System.out.println("Не удалось закрыть Cookies");
        }
    }

    @Test
    public void testBlockTitle() {
        try {
            WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section/div/h2")));
            assertEquals("Онлайн пополнение без комиссии", blockTitle.getText(), "Название блока не совпадает");
        } catch (Exception e) {
            throw new AssertionError("Не удалось найти элемент блока с названием", e);
        }
    }


    @Test
    public void testPaymentSystemLogos() {
        try {
            List<WebElement> logos = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".payment-systems img")));
            assertTrue(logos.size() > 0, "Логотипы платёжных систем должны быть видны");
        } catch (Exception e) {
            throw new AssertionError("Не удалось найти логотипы платёжных систем", e);
        }
    }
}



