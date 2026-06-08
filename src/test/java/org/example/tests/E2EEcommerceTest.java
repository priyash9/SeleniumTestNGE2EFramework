package org.example.tests;

import org.example.pageobject.*;
import org.example.services.ReadJsonFile;
import org.example.services.WebDriverBaseClass;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class E2EEcommerceTest extends WebDriverBaseClass {

    @Test(groups = "order_checkout", dataProvider = "getData")
    public void checkOutItems(HashMap<String, String> data) {
        final String username = data.get("username");
        final String loginPassword = data.get("loginPassword");
        final String productToBeAdded = data.get("product");
        final String EXPECTED_STATUS = "Thank you";
        String productAddedToShoppingCart;

        MenuPage menuPage = loginPage.logInWithValidUserName(username, loginPassword);
        ShoppingCartPage shoppingCartPage;
        CheckoutPage checkoutPage;
        Assert.assertEquals(menuPage.getUserDetails(), username);
        menuPage.clearCart();
        itemsPage.addItemToCart(productToBeAdded);
        Assert.assertEquals(itemsPage.validateMessage(), "The product has been added to your shopping cart");
        shoppingCartPage = menuPage.clickShoppingCartOption();
        shoppingCartPage.verifyItemsAdded(productToBeAdded);
        productAddedToShoppingCart = shoppingCartPage.getItemText();
        Assert.assertEquals(productAddedToShoppingCart, productToBeAdded, "Incorrect Item added");
        checkoutPage = shoppingCartPage.checkOutTheAddedProducts();
        Assert.assertEquals(checkoutPage.validateCheckOutPage(),EXPECTED_STATUS);;
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        String file = "src/test/java/org/example/resources/Order.json";
        List<HashMap<String, String>> data = ReadJsonFile.getJsonData(file);
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }

    @DataProvider
    public Object[][] getDataFromHashMap() throws IOException {
        HashMap<String, String> order1 = new HashMap<>();
        order1.put("username", "nick@abc.com");
        order1.put("loginPassword", "swordfish");
        order1.put("product", "Blue Jeans");
        return new Object[][]{{order1}};
    }

}
