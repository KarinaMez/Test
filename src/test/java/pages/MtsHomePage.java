package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

public class MtsHomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы для элементов на домашней странице
    private By blockTitle = By.xpath("//section/div/h2");
    private By paymentSystemLogos = By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul");
    private By moreInfoLink = By.linkText("Подробнее о сервисе");
    private By phoneNumberField = By.xpath("//*[@id='connection-phone']");
    private By amountField = By.id("amount-input");
    private By continueButton = By.xpath("//*[@id='pay-connection']/button");
    private By emptyFieldErrorMessage = By.xpath("//div[@class='error-message']");



    // Локаторы для полей ввода реквизитов карты
    private By cardNumberField = By.xpath("//input[@id='card-number']");
    private By cardExpiryField = By.xpath("//input[@id='card-expiry']");
    private By cardCVCField = By.xpath("//input[@id='card-cvc']");
    private By cardHolderNameField = By.xpath("//input[@id='card-holder-name']");
    private By paymentSystemIcons = By.xpath("//div[@class='card-icons']/img");

    // Локаторы для элементов, появляющихся после нажатия кнопки "Продолжить"
    private By displayedPhoneNumber = By.xpath("//div[@class='summary-phone-number']");
    private By displayedAmount = By.xpath("//div[@class='summary-amount']");
    private By paymentButton = By.xpath("//button[@id='pay-button']");

    // Локаторы для полей формы "Услуги связи"
    private By commServicesPhoneField = By.xpath("//input[@id='comm-services-phone']");
    private By commServicesAmountField = By.xpath("//input[@id='comm-services-amount']");
    private By commServicesEmailField = By.xpath("//input[@id='comm-services-email']");

    // Локаторы для полей формы "Интернет услуги"
    private By internetServicesAccountField = By.xpath("//input[@id='internet-services-account']");
    private By internetServicesAmountField = By.xpath("//input[@id='internet-services-amount']");
    private By internetServicesEmailField = By.xpath("//input[@id='internet-services-email']");

    // Локаторы для полей формы "Рассрочка"
    private By installmentPlanDropdown = By.xpath("//select[@id='form-selector']");
    private By installmentPlanOption = By.xpath("//option[text()='Рассрочка']");
    private By installmentPlanContractField = By.xpath("//input[@id='installment-plan-contract']");
    private By installmentPlanAmountField = By.xpath("//input[@id='installment-plan-amount']");
    private By installmentPlanEmailField = By.xpath("//input[@id='installment-plan-email']");

    // Локаторы для полей формы "Домашний интернет"
    private By homeInternetOption = By.xpath("//option[text()='Домашний интернет']");
    private By homeInternetAccountField = By.xpath("//input[@id='home-internet-account']");
    private By homeInternetAmountField = By.xpath("//input[@id='home-internet-amount']");
    private By homeInternetEmailField = By.xpath("//input[@id='home-internet-email']");

    // Локаторы для полей формы "Оплата задолженности"
    private By debtPaymentOption = By.xpath("//option[text()='Оплата задолженности']");
    private By debtPaymentContractField = By.xpath("//input[@id='debt-payment-contract']");
    private By debtPaymentAmountField = By.xpath("//input[@id='debt-payment-amount']");
    private By debtPaymentEmailField = By.xpath("//input[@id='debt-payment-email']");

    public MtsHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    // Метод для закрытия куки
    private void closeCookies() {
        try {
            WebElement cookieCloseButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Принять')]")));
            // Кликаем по кнопке, если она видима
            if (cookieCloseButton.isDisplayed()) {
                cookieCloseButton.click();
                System.out.println("Cookies успешно закрыты.");
            } else {
                System.out.println("Кнопка закрытия куки не видима.");
            }
        } catch (TimeoutException e) {
            System.out.println("Не удалось найти кнопку закрытия куки в течение ожидаемого времени.");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при попытке закрыть куки: " + e.getMessage());
        }
    }

    // Получение текста заголовка блока
    public String getBlockTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(blockTitle)).getText();
    }

    // Получение количества логотипов платежных систем
    public int getPaymentSystemLogosCount() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paymentSystemLogos)).size();
    }

    // Клик по ссылке "Подробнее о сервисе"
    public void clickMoreInfoLink() {
        wait.until(ExpectedConditions.elementToBeClickable(moreInfoLink)).click();
    }

    // Ввод номера телефона
    public void enterPhoneNumber(String phoneNumber) {
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneNumberField));
        phoneInput.clear();
        phoneInput.sendKeys(phoneNumber);
    }

    // Ввод суммы
    public void enterAmount(String amount) {
        WebElement amountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(amountField));
        amountInput.clear();
        amountInput.sendKeys(amount);
    }

    // Клик по кнопке "Продолжить"
    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    // Получение текста кнопки "Продолжить"
    public String getContinueButtonText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton)).getText();
    }

    // Получение текста сообщения об ошибке пустого поля
    public String getEmptyFieldErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emptyFieldErrorMessage)).getText();
    }

    // Получение отображаемого номера телефона после нажатия кнопки "Продолжить"
    public String getDisplayedPhoneNumber() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(displayedPhoneNumber)).getText();
    }

    // Получение отображаемой суммы после нажатия кнопки "Продолжить"
    public String getDisplayedAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(displayedAmount)).getText();
    }

    // Получение текста кнопки "Оплатить"
    public String getPaymentButtonText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(paymentButton)).getText();
    }

    // Универсальный метод для получения плейсхолдера
    private String getPlaceholderForField(By fieldLocator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(fieldLocator)).getAttribute("placeholder");
    }

    // Получение плейсхолдера для полей формы "Услуги связи"
    public String getPlaceholderForCommServicesPhone() {
        return getPlaceholderForField(commServicesPhoneField);
    }

    public String getPlaceholderForCommServicesAmount() {
        return getPlaceholderForField(commServicesAmountField);
    }

    public String getPlaceholderForCommServicesEmail() {
        return getPlaceholderForField(commServicesEmailField);
    }

    // Получение плейсхолдера для полей формы "Интернет услуги"
    public String getPlaceholderForInternetServicesAccount() {
        return getPlaceholderForField(internetServicesAccountField);
    }

    public String getPlaceholderForInternetServicesAmount() {
        return getPlaceholderForField(internetServicesAmountField);
    }

    public String getPlaceholderForInternetServicesEmail() {
        return getPlaceholderForField(internetServicesEmailField);
    }

    // Получение плейсхолдера для полей формы "Рассрочка"
    public String getPlaceholderForInstallmentPlanContract() {
        return getPlaceholderForField(installmentPlanContractField);
    }

    public String getPlaceholderForInstallmentPlanAmount() {
        return getPlaceholderForField(installmentPlanAmountField);
    }

    public String getPlaceholderForInstallmentPlanEmail() {
        return getPlaceholderForField(installmentPlanEmailField);
    }

    // Получение плейсхолдера для полей формы "Домашний интернет"
    public String getPlaceholderForHomeInternetAccount() {
        return getPlaceholderForField(homeInternetAccountField);
    }

    public String getPlaceholderForHomeInternetAmount() {
        return getPlaceholderForField(homeInternetAmountField);
    }

    public String getPlaceholderForHomeInternetEmail() {
        return getPlaceholderForField(homeInternetEmailField);
    }

    // Получение плейсхолдера для полей формы "Оплата задолженности"
    public String getPlaceholderForDebtPaymentContract() {
        return getPlaceholderForField(debtPaymentContractField);
    }

    public String getPlaceholderForDebtPaymentAmount() {
        return getPlaceholderForField(debtPaymentAmountField);
    }

    public String getPlaceholderForDebtPaymentEmail() {
        return getPlaceholderForField(debtPaymentEmailField);
    }

    // Получение плейсхолдера для полей ввода реквизитов карты
    public String getPlaceholderForCardNumber() {
        return getPlaceholderForField(cardNumberField);
    }

    public String getPlaceholderForCardExpiry() {
        return getPlaceholderForField(cardExpiryField);
    }

    public String getPlaceholderForCardCVC() {
        return getPlaceholderForField(cardCVCField);
    }

    public String getPlaceholderForCardHolderName() {
        return getPlaceholderForField(cardHolderNameField);
    }

    // Получение количества иконок платежных систем
    public int getPaymentSystemIconsCount() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paymentSystemIcons)).size();
    }

    // Выбор формы из выпадающего списка
    private void selectForm(String formName) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(installmentPlanDropdown));
        dropdown.click();
        WebElement option = driver.findElement(By.xpath("//option[text()='" + formName + "']"));
        option.click();
    }

    // Выбор формы "Рассрочка"
    public void selectInstallmentPlanForm() {
        selectForm("Рассрочка");
    }

    // Выбор формы "Домашний интернет"
    public void selectHomeInternetForm() {
        selectForm("Домашний интернет");
    }

    // Выбор формы "Задолженность"
    public void selectDebtPaymentForm() {
        selectForm("Оплата задолженности");
    }
}