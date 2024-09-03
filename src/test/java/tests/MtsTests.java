package tests;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
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

        // Закрытие куки
        mtsHomePage.closeCookies();
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
        // Переключение на фрейм
        driver.switchTo().frame(1);

        String displayedAmount = mtsHomePage.getDisplayedAmount();
        assertEquals("10.00 BYN", displayedAmount, "Отображаемая сумма некорректна");

        String displayedPhoneNumber = mtsHomePage.getDisplayedPhoneNumber();
        assertEquals("375297777777", displayedPhoneNumber, "Отображаемый номер телефона некорректен");

        // Проверка плейсхолдеров в полях для ввода реквизитов карты
        assertEquals("Номер карты", mtsHomePage.getPlaceholderForCardNumber(), "Плейсхолдер для номера карты некорректен");
        assertEquals("Срок действия", mtsHomePage.getPlaceholderForCardExpiry(), "Плейсхолдер для срока действия карты некорректен");
        assertEquals("CVC", mtsHomePage.getPlaceholderForCardCVC(), "Плейсхолдер для CVC некорректен");
        assertEquals("Имя держателя (как на карте)", mtsHomePage.getPlaceholderForCardHolderName(), "Плейсхолдер для имени держателя карты некорректен");

        // Проверка наличия иконок платежных систем
        int iconCount = mtsHomePage.getPaymentSystemIconsCount();
        assertTrue(iconCount > 0, "Иконки платежных систем должны быть видны в поле 'номер карты'");
        // Возврат к основному контенту
        mtsHomePage.switchToDefaultContent();
    }

    @Test
    public void testPlaceholders() {
        // Проверка плейсхолдеров для полей формы "Услуги связи"
        assertEquals("Номер телефона", mtsHomePage.getPlaceholderForCommServicesPhone(), "Плейсхолдер для номера телефона некорректен");
        assertEquals("Сумма", mtsHomePage.getPlaceholderForCommServicesAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals("E-mail для отправки чека", mtsHomePage.getPlaceholderForCommServicesEmail(), "Плейсхолдер для email некорректен");

        // Проверка плейсхолдеров для полей формы "Интернет услуги"
        assertEquals("Номер абонента", mtsHomePage.getPlaceholderForInternetServicesAccount(), "Плейсхолдер для номера лицевого счета некорректен");
        assertEquals("Сумма", mtsHomePage.getPlaceholderForInternetServicesAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals("E-mail для отправки чека", mtsHomePage.getPlaceholderForInternetServicesEmail(), "Плейсхолдер для email некорректен");

        // Выбор формы и проверка плейсхолдеров для формы "Рассрочка"
        mtsHomePage.selectInstallmentPlanForm();
        assertEquals("Номер счета на 44", mtsHomePage.getPlaceholderForInstallmentPlanContract(), "Плейсхолдер для номера договора некорректен");
        assertEquals("Сумма", mtsHomePage.getPlaceholderForInstallmentPlanAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals("E-mail для отправки чека", mtsHomePage.getPlaceholderForInstallmentPlanEmail(), "Плейсхолдер для email некорректен");

        // Выбор формы и проверка плейсхолдеров для формы "Задолженность"
        mtsHomePage.selectDebtPaymentForm();
        assertEquals("Номер счета на 2073", mtsHomePage.getPlaceholderForHomeInternetAmount(), "Плейсхолдер для номера договора некорректен");
        assertEquals("Сумма", mtsHomePage.getPlaceholderForDebtPaymentAmount(), "Плейсхолдер для суммы некорректен");
        assertEquals("E-mail для отправки чека", mtsHomePage.getPlaceholderForDebtPaymentEmail(), "Плейсхолдер для email некорректен");
    }
}