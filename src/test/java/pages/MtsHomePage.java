package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.qameta.allure.Step;

public class MtsHomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы для элементов
    private By blockTitle = By.xpath("//section/div/h2");
    private By paymentSystemLogos = By.xpath("//*[@id=\"pay-section\"]//ul");
    private By moreInfoLink = By.linkText("Подробнее о сервисе");
    private By phoneNumberField = By.xpath("//*[@id='connection-phone']");
    private By amountField = By.id("connection-sum");
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
    private By displayedAmount = By.xpath("xpath=//span[contains(.,'10.00 BYN')]");
    private By paymentButton = By.xpath("//div[2]/span");

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

    @Step("Переключение на iframe по локатору {iframeLocator}")
    public void switchToFrame(By iframeLocator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
    }

    @Step("Возвращение к основному контенту")
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    @Step("Проверка наличия iframe")
    private boolean isIframePresent(By iframeLocator) {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
            driver.switchTo().defaultContent(); // Возвращаемся обратно к основному контенту после проверки
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Закрытие баннера с куки")
    public void closeCookies() {
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

    @Step("Получение заголовка блока")
    public String getBlockTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(blockTitle)).getText();
    }

    @Step("Получение количества логотипов платёжных систем")
    public int getPaymentSystemLogosCount() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paymentSystemLogos)).size();
    }

    @Step("Нажатие на ссылку 'Подробнее о сервисе'")
    public void clickMoreInfoLink() {
        wait.until(ExpectedConditions.elementToBeClickable(moreInfoLink)).click();
    }

    @Step("Ввод номера телефона: {phoneNumber}")
    public void enterPhoneNumber(String phoneNumber) {
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneNumberField));
        phoneInput.clear();
        phoneInput.sendKeys(phoneNumber);
    }

    @Step("Ввод суммы: {amount}")
    public void enterAmount(String amount) {
        WebElement amountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(amountField));
        amountInput.clear();
        amountInput.sendKeys(amount);
    }

    @Step("Нажатие на кнопку 'Продолжить'")
    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    @Step("Получение текста кнопки 'Продолжить'")
    public String getContinueButtonText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton)).getText();
    }

    @Step("Получение сообщения об ошибке для пустого поля")
    public String getEmptyFieldErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emptyFieldErrorMessage)).getText();
    }

    @Step("Получение отображаемого номера телефона после нажатия 'Продолжить'")
    public String getDisplayedPhoneNumber() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(displayedPhoneNumber)).getText();
    }

    @Step("Получение отображаемой суммы после нажатия 'Продолжить'")
    public String getDisplayedAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(displayedAmount)).getText();
    }

    @Step("Получение текста кнопки оплаты")
    public String getPaymentButtonText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(paymentButton)).getText();
    }

    @Step("Получение плейсхолдера для поля")
    private String getPlaceholderForField(By fieldLocator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(fieldLocator)).getAttribute("placeholder");
    }

    @Step("Получение плейсхолдера для телефона в форме 'Услуги связи'")
    public String getPlaceholderForCommServicesPhone() {
        return getPlaceholderForField(commServicesPhoneField);
    }

    @Step("Получение плейсхолдера для суммы в форме 'Услуги связи'")
    public String getPlaceholderForCommServicesAmount() {
        return getPlaceholderForField(commServicesAmountField);
    }

    @Step("Получение плейсхолдера для email в форме 'Услуги связи'")
    public String getPlaceholderForCommServicesEmail() {
        return getPlaceholderForField(commServicesEmailField);
    }

    @Step("Получение плейсхолдера для лицевого счёта в форме 'Интернет услуги'")
    public String getPlaceholderForInternetServicesAccount() {
        return getPlaceholderForField(internetServicesAccountField);
    }

    @Step("Получение плейсхолдера для суммы в форме 'Интернет услуги'")
    public String getPlaceholderForInternetServicesAmount() {
        return getPlaceholderForField(internetServicesAmountField);
    }

    @Step("Получение плейсхолдера для email в форме 'Интернет услуги'")
    public String getPlaceholderForInternetServicesEmail() {
        return getPlaceholderForField(internetServicesEmailField);
    }

    @Step("Получение плейсхолдера для договора в форме 'Рассрочка'")
    public String getPlaceholderForInstallmentPlanContract() {
        return getPlaceholderForField(installmentPlanContractField);
    }

    @Step("Получение плейсхолдера для суммы в форме 'Рассрочка'")
    public String getPlaceholderForInstallmentPlanAmount() {
        return getPlaceholderForField(installmentPlanAmountField);
    }

    @Step("Получение плейсхолдера для email в форме 'Рассрочка'")
    public String getPlaceholderForInstallmentPlanEmail() {
        return getPlaceholderForField(installmentPlanEmailField);
    }

    @Step("Получение плейсхолдера для лицевого счёта в форме 'Домашний интернет'")
    public String getPlaceholderForHomeInternetAccount() {
        return getPlaceholderForField(homeInternetAccountField);
    }

    @Step("Получение плейсхолдера для суммы в форме 'Домашний интернет'")
    public String getPlaceholderForHomeInternetAmount() {
        return getPlaceholderForField(homeInternetAmountField);
    }

    @Step("Получение плейсхолдера для email в форме 'Домашний интернет'")
    public String getPlaceholderForHomeInternetEmail() {
        return getPlaceholderForField(homeInternetEmailField);
    }

    @Step("Получение плейсхолдера для договора в форме 'Оплата задолженности'")
    public String getPlaceholderForDebtPaymentContract() {
        return getPlaceholderForField(debtPaymentContractField);
    }

    @Step("Получение плейсхолдера для суммы в форме 'Оплата задолженности'")
    public String getPlaceholderForDebtPaymentAmount() {
        return getPlaceholderForField(debtPaymentAmountField);
    }

    @Step("Получение плейсхолдера для email в форме 'Оплата задолженности'")
    public String getPlaceholderForDebtPaymentEmail() {
        return getPlaceholderForField(debtPaymentEmailField);
    }

    @Step("Выбор формы 'Рассрочка'")
    public void selectInstallmentPlanForm() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(installmentPlanDropdown));
        dropdown.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(installmentPlanOption));
        option.click();
    }

    @Step("Выбор формы 'Оплата задолженности'")
    public void selectDebtPaymentForm() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(installmentPlanDropdown));
        dropdown.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(debtPaymentOption));
        option.click();
    }

    @Step("Получение плейсхолдера для поля номера карты")
    public String getPlaceholderForCardNumber() {
        return getPlaceholderForField(cardNumberField);
    }

    @Step("Получение плейсхолдера для поля срока действия карты")
    public String getPlaceholderForCardExpiry() {
        return getPlaceholderForField(cardExpiryField);
    }

    @Step("Получение плейсхолдера для поля CVC карты")
    public String getPlaceholderForCardCVC() {
        return getPlaceholderForField(cardCVCField);
    }

    @Step("Получение плейсхолдера для поля имени держателя карты")
    public String getPlaceholderForCardHolderName() {
        return getPlaceholderForField(cardHolderNameField);
    }

    @Step("Получение количества иконок платёжных систем в поле номера карты")
    public int getPaymentSystemIconsCount() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paymentSystemIcons)).size();
    }
}


