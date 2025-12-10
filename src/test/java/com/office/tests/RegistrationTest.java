package com.office.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import com.office.models.User;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.office.pages.RegistrationPage;

import com.office.utils.ExcelUtils;

import java.util.List;
import java.util.Map;

@Epic("User Management")
@Feature("Registration")
public class RegistrationTest extends BaseTest {
    
    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() {
        String filePath = "src/test/resources/testdata/Users.xlsx";
        List<Map<String, String>> excelData = ExcelUtils.readExcelData(filePath);
        
        Object[][] data = new Object[excelData.size()][1];
        for (int i = 0; i < excelData.size(); i++) {
            Map<String, String> row = excelData.get(i);
            User user = new User(
                row.get("FirstName"),
                row.get("LastName"),
                row.get("Email"),
                row.get("Telephone"),
                row.get("Password")
            );
            data[i][0] = user;
        }
        return data;
    }
    
    @Test(dataProvider = "registrationData")
    @Story("User Registration with Valid Data")
    @Description("Register a new user with data from Excel file")
    public void testUserRegistration(User user) {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        
        registrationPage.navigateToRegistration();
        registrationPage.registerUser(user);
        
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(), 
            "User registration failed for: " + user.getEmail());
    }
}
