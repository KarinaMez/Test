package tests;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MtsHomePage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class MtsTests {

    private WebDriver driver;
    private MtsHomePage mtsHomePage;

    // Метод для настройки тестового окружения
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
    }

    // Метод для очистки тестового окружения после каждого теста
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            // Закрытие браузера
            driver.quit();
        }
    }

    // Тестирование заголовка блока
    @Test
    public void testBlockTitle() {
        String expectedTitle = "Онлайн пополнение\nбез комиссии";
        // Проверка соответствия заголовка блока ожидаемому значению
        assertEquals(expectedTitle, mtsHomePage.getBlockTitle(), "Название блока не совпадает");
    }

    // Тестирование наличия логотипов платежных систем
    @Test
    public void testPaymentSystemLogos() {
        // Проверка, что количество логотипов платежных систем больше нуля
        assertTrue(mtsHomePage.getPaymentSystemLogosCount() > 0, "Логотипы платёжных систем должны быть видны");
    }

    // Тестирование перехода по ссылке "Подробнее о сервисе"
    @Test
    public void testMoreInfoLink() {
        mtsHomePage.clickMoreInfoLink();
        String expectedUrlPart = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
        // Проверка, что текущий URL содержит ожидаемую часть
        assertTrue(driver.getCurrentUrl().contains(expectedUrlPart), "Должна открыться страница с информацией о сервисе");
    }


    // Тестирование пополнения с валидными данными
    @Test
    public void testServicePaymentWithValidData() {
        // Заполнение обязательных полей
        mtsHomePage.enterPhoneNumber("297777777");
        mtsHomePage.enterAmount("10");

        // Нажатие на кнопку "Продолжить"
        mtsHomePage.clickContinueButton();

        // Проверка текста кнопки "Продолжить"
        String buttonText = mtsHomePage.getContinueButtonText();
        assertTrue(buttonText.contains("Продолжить"), "Текст на кнопке должен быть 'Продолжить'");

        String displayedAmount = mtsHomePage.getDisplayedAmount();
        assertEquals("10.00 BYN", displayedAmount, "Отображаемая сумма некорректна");

        String displayedPhoneNumber = mtsHomePage.getDisplayedPhoneNumber();
        assertEquals("+375297777777", displayedPhoneNumber, "Отображаемый номер телефона некорректен");
    }

    // Тестирование плейсхолдеров для полей ввода в форме "Услуги связи"
    @Test
    public void testPlaceholderForCommunicationServices() {
        String expectedPhonePlaceholder = "Номер телефона";
        String expectedAmountPlaceholder = "Сумма";
        String expectedEmailPlaceholder = "E-mail для отправки чека";

        assertEquals(expectedPhonePlaceholder, mtsHomePage.getPlaceholderForCommServicesPhone(), "Плейсхолдер для номера телефона некорректен");
        assertEquals(expectedAmountPlaceholder, mtsHomePage.getPlaceholderForCommServicesAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals(expectedEmailPlaceholder, mtsHomePage.getPlaceholderForCommServicesEmail(), "Плейсхолдер для email некорректен");
    }

    // Тестирование плейсхолдеров для полей ввода в форме "Домашний интернет"
    @Test
    public void testPlaceholderForInternetServices() {
        String expectedAccountPlaceholder = "Номер абонента";
        String expectedAmountPlaceholder = "Сумма";
        String expectedEmailPlaceholder = "E-mail для отправки чека";

        assertEquals(expectedAccountPlaceholder, mtsHomePage.getPlaceholderForInternetServicesAccount(), "Плейсхолдер для номера лицевого счета некорректен");
        assertEquals(expectedAmountPlaceholder, mtsHomePage.getPlaceholderForInternetServicesAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals(expectedEmailPlaceholder, mtsHomePage.getPlaceholderForInternetServicesEmail(), "Плейсхолдер для email некорректен");
    }

    // Тестирование плейсхолдеров для полей ввода в форме "Рассрочка"
    @Test
    public void testPlaceholderForInstallmentPlan() {
        mtsHomePage.selectInstallmentPlanForm();

        String expectedContractPlaceholder = "Номер счета на 44";
        String expectedAmountPlaceholder = "Сумма";
        String expectedEmailPlaceholder = "E-mail для отправки чека";

        assertEquals(expectedContractPlaceholder, mtsHomePage.getPlaceholderForInstallmentPlanContract(), "Плейсхолдер для номера договора некорректен");
        assertEquals(expectedAmountPlaceholder, mtsHomePage.getPlaceholderForInstallmentPlanAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals(expectedEmailPlaceholder, mtsHomePage.getPlaceholderForInstallmentPlanEmail(), "Плейсхолдер для email некорректен");
    }

    // Тестирование плейсхолдеров для полей ввода в форме "Задолженность"
    @Test
    public void testPlaceholderForDebtPayment() {
        mtsHomePage.selectDebtPaymentForm();

        String expectedContractPlaceholder = "Номер счета на 2073";
        String expectedAmountPlaceholder = "Сумма";
        String expectedEmailPlaceholder = "E-mail для отправки чека";

        assertEquals(expectedContractPlaceholder, mtsHomePage.getPlaceholderForDebtPaymentContract(), "Плейсхолдер для номера договора некорректен");
        assertEquals(expectedAmountPlaceholder, mtsHomePage.getPlaceholderForDebtPaymentAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals(expectedEmailPlaceholder, mtsHomePage.getPlaceholderForDebtPaymentEmail(), "Плейсхолдер для email некорректен");
    }

    // Тестирование итогов пополнения и плейсхолдеров для реквизитов карты
    @Test
    public void testServicePaymentSummaryAndCardPlaceholders() {
        // Заполнение обязательных полей для "Услуги связи"
        mtsHomePage.enterPhoneNumber("297777777");
        mtsHomePage.enterAmount("10");
        mtsHomePage.clickContinueButton();

        // Проверка корректности отображаемой суммы
        String displayedAmount = mtsHomePage.getDisplayedAmount();
        assertEquals("10.00 BYN", displayedAmount, "Отображаемая сумма некорректна");

        // Проверка номера телефона
        String displayedPhoneNumber = mtsHomePage.getDisplayedPhoneNumber();
        assertEquals("+375297777777", displayedPhoneNumber, "Отображаемый номер телефона некорректен");

        // Проверка плейсхолдеров в полях для ввода реквизитов карты
        assertEquals("Номер карты", mtsHomePage.getPlaceholderForCardNumber(), "Плейсхолдер для номера карты некорректен");
        assertEquals("Срок действия", mtsHomePage.getPlaceholderForCardExpiry(), "Плейсхолдер для срока действия карты некорректен");
        assertEquals("CVC", mtsHomePage.getPlaceholderForCardCVC(), "Плейсхолдер для CVC некорректен");
        assertEquals("Имя держателя (как на карте)", mtsHomePage.getPlaceholderForCardHolderName(), "Плейсхолдер для имени держателя карты некорректен");

        // Проверка наличия иконок платежных систем
        int iconCount = mtsHomePage.getPaymentSystemIconsCount();
        assertTrue(iconCount > 0, "Иконки платежных систем должны быть видны в поле 'номер карты'");
    }
}