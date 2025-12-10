package com.office.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.office.models.User;

public class RegistrationPage extends BasePage {
    
    @FindBy(id = "input-firstname")
    private WebElement firstNameInput;
    
    @FindBy(id = "input-lastname")
    private WebElement lastNameInput;
    
    @FindBy(id = "input-email")
    private WebElement emailInput;
    
    @FindBy(id = "input-telephone")
    private WebElement telephoneInput;
    
    @FindBy(id = "input-password")
    private WebElement passwordInput;
    
    @FindBy(id = "input-confirm")
    private WebElement confirmPasswordInput;
    
    @FindBy(name = "agree")
    private WebElement privacyPolicyCheckbox;
    
    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement continueButton;
    
    @FindBy(css = ".alert-danger")
    private WebElement errorMessage;
    
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Navigate to registration page")
    public void navigateToRegistration() {
        navigateTo("https://awesomeqa.com/ui/index.php?route=account/register");
    }
    
    @Step("Register user: {user.firstName} {user.lastName}")
    public void registerUser(User user) {
        enterText(firstNameInput, user.getFirstName());
        enterText(lastNameInput, user.getLastName());
        enterText(emailInput, user.getEmail());
        enterText(telephoneInput, user.getTelephone());
        enterText(passwordInput, user.getPassword());
        enterText(confirmPasswordInput, user.getPassword());
        clickElement(privacyPolicyCheckbox);
        clickElement(continueButton);
    }
    
    @Step("Check if registration was successful")
    public boolean isRegistrationSuccessful() {
        return driver.getCurrentUrl().contains("account/success");
    }
    
    @Step("Get error message")
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}

