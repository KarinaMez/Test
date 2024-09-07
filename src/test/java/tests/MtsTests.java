package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MtsHomePage;
import static org.junit.jupiter.api.Assertions.*;
import io.qameta.allure.*;

import java.time.Duration;


public class MtsTests {

    private WebDriver driver;
    private MtsHomePage mtsHomePage;

    @BeforeEach
    public void setUp() {
        // Установка и запуск драйвера Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        // Установка ожидания в 10 секунд
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Максимизация окна браузера
        driver.manage().window().maximize();
        // Открытие страницы для тестирования
        driver.get("https://mts.by");

        // Инициализация страницы с помощью драйвера
        mtsHomePage = new MtsHomePage(driver);

        // Закрытие куки
        mtsHomePage.closeCookies();
    }
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Epic("MTS Home Page Tests")
    @Feature("Header Tests")
    @Story("Проверка заголовка блока")
    @Description("Этот тест проверяет, что заголовок блока на главной странице совпадает с ожидаемым значением.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testBlockTitle() {
        String expectedTitle = "Онлайн пополнение\nбез комиссии";
        assertEquals(expectedTitle, mtsHomePage.getBlockTitle(), "Название блока не совпадает");
    }

    @Epic("MTS Home Page Tests")
    @Feature("Payment System Logos")
    @Story("Проверка наличия логотипов платежных систем")
    @Description("Этот тест проверяет, что на странице отображаются логотипы платежных систем.")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testPaymentSystemLogos() {
        assertTrue(mtsHomePage.getPaymentSystemLogosCount() > 0, "Логотипы платёжных систем должны быть видны");
    }

    @Epic("MTS Home Page Tests")
    @Feature("More Info Link")
    @Story("Проверка перехода по ссылке 'Подробнее о сервисе'")
    @Description("Этот тест проверяет корректность перехода по ссылке 'Подробнее о сервисе'.")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testMoreInfoLink() {
        mtsHomePage.clickMoreInfoLink();
        String expectedUrlPart = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
        assertTrue(driver.getCurrentUrl().contains(expectedUrlPart), "Должна открыться страница с информацией о сервисе");
    }

    @Epic("MTS Home Page Tests")
    @Feature("Service Payment")
    @Story("Проверка пополнения с валидными данными")
    @Description("Этот тест проверяет, что пользователь может успешно выполнить пополнение с валидными данными.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testServicePaymentWithValidData() {
        mtsHomePage.enterPhoneNumber("297777777");
        mtsHomePage.enterAmount("10");
        mtsHomePage.clickContinueButton();
        assertTrue(mtsHomePage.getContinueButtonText().contains("Продолжить"), "Текст на кнопке должен быть 'Продолжить'");
        mtsHomePage.switchToFrame();
        assertEquals("10.00 BYN", mtsHomePage.getDisplayedAmount(), "Отображаемая сумма некорректна");
        assertEquals("Оплата: Услуги связи Номер:375297777777", mtsHomePage.getDisplayedPhoneNumber(), "Отображаемый номер телефона некорректен");
        assertEquals("Номер карты", mtsHomePage.getCardNumberLabelText(), "Текст для номера карты некорректен");
        assertEquals("Срок действия", mtsHomePage.getCardExpiryLabelText(), "Текст для срока действия карты некорректен");
        assertEquals("CVC", mtsHomePage.getCardCVCLabelText(), "Текст для CVC некорректен");
        assertEquals("Имя держателя (как на карте)", mtsHomePage.getCardHolderNameLabelText(), "Текст для имени держателя карты некорректен");
        assertTrue(mtsHomePage.getPaymentSystemIconsCount() > 0, "Иконки платежных систем должны быть видны в поле 'номер карты'");
        mtsHomePage.switchToDefaultContent();
    }

    @Epic("MTS Home Page Tests")
    @Feature("Placeholders")
    @Story("Проверка плейсхолдеров в формах")
    @Description("Этот тест проверяет корректность плейсхолдеров в различных формах на странице.")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testPlaceholders() {
        assertEquals("Номер телефона", mtsHomePage.getPlaceholderForCommServicesPhone(), "Плейсхолдер для номера телефона некорректен");
        assertEquals("Сумма", mtsHomePage.getPlaceholderForCommServicesAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals("E-mail для отправки чека", mtsHomePage.getPlaceholderForCommServicesEmail(), "Плейсхолдер для email некорректен");
        mtsHomePage.selectPaymentForm();
        assertEquals("Номер абонента", mtsHomePage.getPlaceholderForInternetServicesAccount(), "Плейсхолдер для номера лицевого счета некорректен");
        assertEquals("Сумма", mtsHomePage.getPlaceholderForInternetServicesAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals("E-mail для отправки чека", mtsHomePage.getPlaceholderForInternetServicesEmail(), "Плейсхолдер для email некорректен");
        mtsHomePage.selectInstallmentPlanForm();
        assertEquals("Номер счета на 44", mtsHomePage.getPlaceholderForInstallmentPlanContract(), "Плейсхолдер для номера договора некорректен");
        assertEquals("Сумма", mtsHomePage.getPlaceholderForInstallmentPlanAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals("E-mail для отправки чека", mtsHomePage.getPlaceholderForInstallmentPlanEmail(), "Плейсхолдер для email некорректен");
        mtsHomePage.selectDebtPaymentForm();
        assertEquals("Номер счета на 2073", mtsHomePage.getPlaceholderForDebtPaymentContract(), "Плейсхолдер для номера договора некорректен");
        assertEquals("Сумма", mtsHomePage.getPlaceholderForDebtPaymentAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals("E-mail для отправки чека", mtsHomePage.getPlaceholderForDebtPaymentEmail(), "Плейсхолдер для email некорректен");
    }
}
