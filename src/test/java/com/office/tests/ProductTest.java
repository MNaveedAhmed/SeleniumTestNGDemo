package com.office.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import com.office.models.Product;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.office.pages.ProductPage;
import com.office.utils.JsonReader;

import java.util.List;

@Epic("E-Commerce")
@Feature("Shopping Cart")
public class ProductTest extends BaseTest {
    
    @DataProvider(name = "productData")
    public Object[][] getProductData() {
        String filePath = "src/test/resources/testdata/products.json";
        List<Product> products = JsonReader.readProductsFromJson(filePath);
        
        Object[][] data = new Object[products.size()][1];
        for (int i = 0; i < products.size(); i++) {
            data[i][0] = products.get(i);
        }
        return data;
    }
    
    @Test(dataProvider = "productData")
    @Story("Add Product to Cart")
    @Description("Add product to cart with data from JSON file")
    public void testAddProductToCart(Product product) {
        ProductPage productPage = new ProductPage(driver);
        
        productPage.navigateToProduct(product.getProductId());
        
        String actualTitle = productPage.getProductTitle();
        Assert.assertEquals(actualTitle, product.getName(), 
            "Product title mismatch!");
        
        productPage.setQuantity(product.getQuantity());
        productPage.addToCart();
        
        Assert.assertTrue(productPage.isProductAddedToCart(), 
            "Product was not added to cart: " + product.getName());
    }
    
    @Test
    @Story("Add Multiple Products to Cart")
    @Description("Add all products from JSON file to cart in one session")
    public void testAddMultipleProductsToCart() {
        String filePath = "src/test/resources/testdata/products.json";
        List<Product> products = JsonReader.readProductsFromJson(filePath);
        
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
