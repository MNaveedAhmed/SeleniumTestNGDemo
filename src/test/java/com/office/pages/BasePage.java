package com.office.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.office.utils.WaitHelper;

public class BasePage {
    
    protected WebDriver driver;
    protected WaitHelper waitHelper;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void navigateTo(String url) {
        driver.get(url);
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    protected void clickElement(WebElement element) {
        waitHelper.waitForElementClickable(element);
        element.click();
    }
    
    protected void enterText(WebElement element, String text) {
        waitHelper.waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }
    
    protected String getText(WebElement element) {
        waitHelper.waitForElementVisible(element);
        return element.getText();
    }
    
    protected boolean isElementDisplayed(WebElement element) {
        try {
            waitHelper.waitForElementVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
