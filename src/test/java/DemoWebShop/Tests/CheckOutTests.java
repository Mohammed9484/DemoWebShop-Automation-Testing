package DemoWebShop.Tests;

import DemoWebShop.Data.DataDriven;
import DemoWebShop.PageObjects.CheckOutPage;
import DemoWebShop.PageObjects.LoginPage;
import DemoWebShop.PageObjects.ProductPage;
import DemoWebShop.PageObjects.ShoppingCart;
import DemoWebShop.TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class CheckOutTests extends BaseTest {


    @Test(dataProvider = "validCheckout", enabled = true, priority = 1)
    public void validCheckout(String productName, String firstName, String lastName, String country, String email, String password, String userCity, String userAddress, String zipCode, String phoneNumber) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        LoginPage loginPage = homePage.goToLogin();
        loginPage.loginApplication(email, password);
        ProductPage productPage = homePage.addToCartFromHome(productName);
        Assert.assertEquals(productPage.getConfirmationAddToCartMessage(), "The product has been added to your shopping cart");
        ShoppingCart shoppingCart = homePage.goToShoppingCart();
        shoppingCart.checkOut();
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        checkOutPage.checkoutForm(firstName, lastName, country, email, userCity, userAddress, zipCode, phoneNumber);
        Assert.assertEquals(checkOutPage.getConfirmMessgae(), "Your order has been successfully processed!");
//        System.out.println(checkOutPage.getConfirmMessgae());

    }

    @Test(dataProvider = "invalidCheckout", enabled = true, priority = 1)
    public void invalidCheckoutWithEmptyFields(String productName, String email, String password) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        LoginPage loginPage = homePage.goToLogin();
        loginPage.loginApplication(email, password);
        ProductPage productPage = homePage.addToCartFromHome(productName);
        Assert.assertEquals(productPage.getConfirmationAddToCartMessage(), "The product has been added to your shopping cart");
        ShoppingCart shoppingCart = homePage.goToShoppingCart();
        shoppingCart.checkOut();
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        checkOutPage.invlaidForm();
        Assert.assertEquals(checkOutPage.getErrorMessageForField("firstname"), "First name is required.");
        Assert.assertEquals(checkOutPage.getErrorMessageForField("lastname"), "Last name is required.");
        Assert.assertEquals(checkOutPage.getErrorMessageForField("email"), "Email is required.");
        Assert.assertEquals(checkOutPage.getErrorMessageForField("country"), "Country is required.");
        Assert.assertEquals(checkOutPage.getErrorMessageForField("city"), "City is required");
        Assert.assertEquals(checkOutPage.getErrorMessageForField("street address"), "Street address is required");
        Assert.assertEquals(checkOutPage.getErrorMessageForField("postal code"), "Zip / postal code is required");
        //Assert.assertEquals(checkOutPage.getErrorMessageForField("phone"),"Phone is required");
        System.out.println(checkOutPage.getErrorMessageForField("phone"));


    }


    @DataProvider
    public Object[][] validCheckout() throws IOException {

        DataDriven d = new DataDriven();
        List<List<String>> testData = d.getData("Checkout");
        // Ensure we remove the "TestCase" column from the extracted data
        Object[][] data = new Object[testData.size()][];
        for (int i = 0; i < testData.size(); i++) {
            // Skip the first column (TestCase Name)
            data[i] = testData.get(i).subList(1, testData.get(i).size()).toArray(new String[0]);
        }
        return data;
    }

    @DataProvider
    public Object[][] invalidCheckout() throws IOException {

        DataDriven d = new DataDriven();
        List<List<String>> testData = d.getData("invalid checkout");
        Object[][] data = new Object[testData.size()][];
        for (int i = 0; i < testData.size(); i++) {
            // Skip the first column (TestCase Name)
            data[i] = testData.get(i).subList(1, testData.get(i).size()).toArray(new String[0]);
        }
        return data;
    }


}
