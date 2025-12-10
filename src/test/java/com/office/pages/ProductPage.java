package com.office.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {
    
    @FindBy(css = "h1")
    private WebElement productTitle;
    
    @FindBy(css = "h2")
    private WebElement productPrice;
    
    @FindBy(id = "input-quantity")
    private WebElement quantityInput;
    
    @FindBy(id = "button-cart")
    private WebElement addToCartButton;
    
    @FindBy(css = ".alert-success")
    private WebElement successMessage;
    
    @FindBy(linkText = "shopping cart")
    private WebElement shoppingCartLink;
    
    @FindBy(css = ".btn-inverse")
    private WebElement cartButton;
    
    public ProductPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Navigate to product: {productId}")
    public void navigateToProduct(String productId) {
        navigateTo("https://awesomeqa.com/ui/index.php?route=product/product&product_id=" + productId);
    }
    
    @Step("Get product title")
    public String getProductTitle() {
        return getText(productTitle);
    }
    
    @Step("Get product price")
    public String getProductPrice() {
        return getText(productPrice);
    }
    
    @Step("Set quantity to: {quantity}")
    public void setQuantity(int quantity) {
        enterText(quantityInput, String.valueOf(quantity));
    }
    
    @Step("Add product to cart")
    public void addToCart() {
        clickElement(addToCartButton);
    }
    
    @Step("Verify product added to cart")
    public boolean isProductAddedToCart() {
        return isElementDisplayed(successMessage);
    }
    
    @Step("Get success message")
    public String getSuccessMessage() {
        return getText(successMessage);
    }
    
    @Step("Go to shopping cart")
    public void goToCart() {
        clickElement(shoppingCartLink);
    }
}

