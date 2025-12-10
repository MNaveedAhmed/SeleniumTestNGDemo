package com.office.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import com.office.models.Product;
import com.office.models.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.office.pages.ProductPage;
import com.office.pages.RegistrationPage;
import com.office.utils.ExcelUtils;
import com.office.utils.JsonReader;

import java.util.List;
import java.util.Map;

@Epic("E-Commerce End-to-End Flow")
@Feature("Complete User Journey")
public class EndToEndTest extends BaseTest {
    
    @Test(priority = 1)
    @Story("User Registration and Shopping")
    @Description("Complete E2E: Register user from Excel, then add products from JSON to cart")
    public void testCompleteUserJourney() {
        // Step 1: Register a new user (from Excel)
        String excelPath = "src/test/resources/testdata/Users.xlsx";
        List<Map<String, String>> userData = ExcelUtils.readExcelData(excelPath);
        Map<String, String> userRow = userData.get(0); // Get first user
        
        User user = new User(
            userRow.get("FirstName"),
            userRow.get("LastName"),
            userRow.get("Email"),
            userRow.get("Telephone"),
            userRow.get("Password")
        );
        
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.navigateToRegistration();
        registrationPage.registerUser(user);
        
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(), 
            "User registration failed");
        
        // Step 2: Add products to cart (from JSON)
        String jsonPath = "src/test/resources/testdata/products.json";
        List<Product> products = JsonReader.readProductsFromJson(jsonPath);
        
        ProductPage productPage = new ProductPage(driver);
        
        for (Product product : products) {
            productPage.navigateToProduct(product.getProductId());
            productPage.setQuantity(product.getQuantity());
            productPage.addToCart();
            
            Assert.assertTrue(productPage.isProductAddedToCart(), 
                "Failed to add product: " + product.getName());
        }
    }
}
