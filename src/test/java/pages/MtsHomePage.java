package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.qameta.allure.Step;

public class MtsHomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы для элементов
    private final By blockTitle = By.xpath("//section/div/h2");
    private final By paymentSystemLogos = By.xpath("*//section/div/div[2]/ul");
    private final By moreInfoLink = By.linkText("Подробнее о сервисе");
    private final By phoneNumberField = By.xpath("//*[@id='connection-phone']");
    private final By amountField = By.id("connection-sum");
    private final By continueButton = By.xpath("//*[@id='pay-connection']/button");
    private final By emptyFieldErrorMessage = By.xpath("//div[@class='error-message']");
    // Локатор iframe
    private final By iFrame = By.xpath(".//iframe[@class='bepaid-iframe']");

    // Локаторы для полей ввода реквизитов карты
    private final By cardNumberLabel = By.xpath("//label[text()='Номер карты']");
    private final By cardExpiryLabel = By.xpath("//label[text()='Срок действия']");
    private final By cardCVCLabel = By.xpath("//label[text()='CVC']");
    private final By cardHolderNameLabel = By.xpath("//label[text()='Имя держателя (как на карте)']");
    private final By paymentSystemIcons = By.xpath("(//input[@value=''])[4]");

    // Локаторы для элементов, появляющихся после нажатия кнопки "Продолжить"
    private final By displayedPhoneNumber = By.xpath("//div[@class='pay-description__text']/span[contains(text(), 'Оплата: Услуги связи')]");
    private final By displayedAmount = By.xpath("//span[contains(text(), '10.00 BYN')]");
    private final By paymentButton = By.xpath("//div[2]/span");

    // Локаторы для полей формы "Услуги связи"
    private final By commServicesPhoneField = By.xpath("//input[@id='connection-phone']");
    private final By commServicesAmountField = By.xpath("//input[@id='connection-sum']");
    private final By commServicesEmailField = By.xpath("//input[@id='connection-email']");

    // Локаторы для полей формы "Домашний интернет"
    private final By homeInternetOption = By.cssSelector(".select__item:nth-child(2) .select__option");
    private final By internetServicesAccountField = By.xpath("//input[@id='internet-phone']");
    private final By internetServicesAmountField = By.xpath("//input[@id='internet-sum']");
    private final By internetServicesEmailField = By.xpath("//input[@id='internet-email']");

    // Локатор выпадающего списка
    private final By installmentPlanDropdown = By.xpath("//section/div/div/div/div[2]/button");

    // Локаторы для полей формы "Рассрочка"
    private final By installmentPlanOption = By.cssSelector(".select__item:nth-child(3) .select__option");
    private final By installmentPlanContractField = By.xpath("//input[@id='score-instalment']");
    private final By installmentPlanAmountField = By.xpath("//input[@id='instalment-sum']");
    private final By installmentPlanEmailField = By.xpath("//input[@id='instalment-email']");

    // Локаторы для полей формы "Оплата задолженности"
    private final By debtPaymentOption = By.cssSelector(".select__item:nth-child(4) .select__option");
    private final By debtPaymentContractField = By.xpath("//input[@id='score-arrears']");
    private final By debtPaymentAmountField = By.xpath("//input[@id='arrears-sum']");
    private final By debtPaymentEmailField = By.xpath("//input[@id='arrears-email']");

    public MtsHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Переключение на iframe по локатору")
    public void switchToFrame() {
        try {
            WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iFrame));
            driver.switchTo().frame(iframeElement);
        } catch (TimeoutException e) {
            System.out.println("Не удалось найти iframe в течение ожидаемого времени.");
        }
    }

    @Step("Возвращение к основному контенту")
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
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

    @Step("Получение плейсхолдера номера телефона в форме 'Домашний интернет'")
    public String getPlaceholderForInternetServicesAccount() {
        return getPlaceholderForField(internetServicesAccountField);
    }

    @Step("Получение плейсхолдера для суммы в форме 'Домашний интернет'")
    public String getPlaceholderForInternetServicesAmount() {
        return getPlaceholderForField(internetServicesAmountField);
    }

    @Step("Получение плейсхолдера для email в форме 'Домашний интернет'")
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

    @Step("Выбор формы 'Оплата задолженности'")
    public void selectPaymentForm() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(installmentPlanDropdown));
        dropdown.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(homeInternetOption));
        option.click();
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
    @Step("Получение текста метки для номера карты")
    public String getCardNumberLabelText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberLabel)).getText();
    }

    @Step("Получение текста метки для срока действия карты")
    public String getCardExpiryLabelText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cardExpiryLabel)).getText();
    }

    @Step("Получение текста метки для CVC карты")
    public String getCardCVCLabelText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cardCVCLabel)).getText();
    }

    @Step("Получение текста метки для имени держателя карты")
    public String getCardHolderNameLabelText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cardHolderNameLabel)).getText();
    }

    @Step("Получение количества иконок платёжных систем в поле номера карты")
    public int getPaymentSystemIconsCount() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paymentSystemIcons)).size();
    }
}


