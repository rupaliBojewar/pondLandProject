package com.pondland;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LogInPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WishListVerification {
    WebDriver driver;
    WebDriverWait wait;
    LogInPage logInPage;
    Actions actions1;


    @BeforeSuite
    public void launchBrowser() {
        driver = new ChromeDriver();
    }

    @BeforeTest
    public void launchApplication() {
        driver.get("https://www.poundland.co.uk/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//button[text()='Accept All Cookies']")).click();

    }

    @AfterSuite
    public void killSession() {
        //driver.quit();
    }

    @BeforeClass
    public void initializePages() {
        actions1 = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void sighIn() {


        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#email"))).sendKeys("rupali.bojewar@gmail.com");

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#pass"))).sendKeys("Abc@1234");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='send3']"))).click();
    }

    @Test(dependsOnMethods = "sighIn")
    public void verifyAddedProductInWishList() throws InterruptedException {

        //class="action towishlist addtowish fav"
        //class="action towishlist addtowish"

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Household']"))).click();

        WebElement productToSelect = driver.findElement(By.cssSelector("#list_456617"));

        String classNameBeforeClick = productToSelect.getAttribute("class");
        System.out.println(classNameBeforeClick);

        wait.until(ExpectedConditions.elementToBeClickable(productToSelect)).click();

        Thread.sleep(5000);

        String classNameAfterClick = productToSelect.getAttribute("class");
        System.out.println(classNameAfterClick);

        Assert.assertTrue(classNameAfterClick.contains("fav"));

    }

    @Test(dependsOnMethods = "sighIn")
    public void verifyDetailsOfAddedProductsInWishList() throws InterruptedException {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Household']"))).click();

        WebElement productToSelect1 = driver.findElement(By.cssSelector("#list_456617"));
        productToSelect1.click();

        WebElement productToSelect2 = driver.findElement(By.cssSelector("#list_456602"));
        productToSelect2.click();

        Thread.sleep(5000);

        List<WebElement> addedItemsInWishList=driver.findElements(By.xpath("//a[@class='action towishlist addtowish fav']/ancestor::div[@class='product-item-info']/descendant::a[@class='product-item-link']"));

        List<String> addedItemsInWishListText=new ArrayList<>();

        for (WebElement w:addedItemsInWishList) {
            addedItemsInWishListText.add(w.getText());
        }
        Collections.sort(addedItemsInWishListText);
        System.out.println(addedItemsInWishListText);

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".top-nav__link")))).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='top-subnav']/ul/li/a[contains(@href,'wishlist')]")))).click();

        Thread.sleep(5000);

        List<WebElement> foundItemInWishList = driver.findElements(By.cssSelector("p>.product-name"));

        List<String> foundItemInWishListText = new ArrayList<>();

        for (WebElement w : foundItemInWishList) {

            foundItemInWishListText.add(w.getText().trim());

        }
        Collections.sort(foundItemInWishListText);

        Thread.sleep(5000);

//       foundItemInWishListText.removeAll(Arrays.asList(null));
      // assert(addedItemsInWishListText.equals(foundItemInWishListText));

        System.out.println(foundItemInWishListText);

        Assert.assertEquals(foundItemInWishListText,addedItemsInWishListText);

        //Assert.assertTrue(foundItemInWishListText.contains(addedItemsInWishListText));
    }

    @Test(dependsOnMethods = "sighIn")
    public void verifyWishlistFromMyAccount() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Household']"))).click();

        WebElement productToSelect1 = driver.findElement(By.cssSelector("#list_456617"));
        productToSelect1.click();

        WebElement productToSelect2 = driver.findElement(By.cssSelector("#list_456602"));
        productToSelect2.click();

        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".top-nav__link")))).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='top-subnav']/ul/li/a[contains(@href,'wishlist')]")))).click();

        Thread.sleep(5000);

        List<WebElement> foundItemInWishList = driver.findElements(By.cssSelector(".product-name"));

        Assert.assertFalse(foundItemInWishList.isEmpty());

    }

    @Test(dependsOnMethods = "sighIn")
    public void verifyWishlistAfterLoginBack() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Household']"))).click();

        WebElement productToSelect1 = driver.findElement(By.cssSelector("#list_456617"));
        productToSelect1.click();

        WebElement productToSelect2 = driver.findElement(By.cssSelector("#list_456602"));
        productToSelect2.click();

        //Thread.sleep(5000);

//        List<String> addedItemsInWishListText=new ArrayList<>();
//
//        for (WebElement w:addedItemsInWishList) {
//            addedItemsInWishListText.add(w.getText());
//        }
//        System.out.println(addedItemsInWishListText);

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".top-nav__link")))).click();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@class='top-subnav']/ul/li/a[contains(@href,'logout')]")))).click();

        Thread.sleep(5000);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#email"))).sendKeys("rupali.bojewar@gmail.com");

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#pass"))).sendKeys("Abc@1234");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='send3']"))).click();
        } catch (Exception e) {
            // Refresh the page
            driver.navigate().refresh();
            // Find the element again
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#email"))).sendKeys("rupali.bojewar@gmail.com");

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#pass"))).sendKeys("Abc@1234");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='send3']"))).click();
        }

        //sighIn();

        Thread.sleep(5000);

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".top-nav__link")))).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='top-subnav']/ul/li/a[contains(@href,'wishlist')]")))).click();

        Thread.sleep(5000);

        List<WebElement> foundItemInWishList = driver.findElements(By.cssSelector(".product-name"));

        List<String> foundItemInWishListText = new ArrayList<>();

        for (WebElement w : foundItemInWishList) {
            foundItemInWishListText.add(w.getText());
        }

        System.out.println(foundItemInWishListText);

        //Assert.assertEquals(foundItemInWishListText,addedItemsInWishListText);

    }
    @Test(dependsOnMethods = "sighIn")
    public void verifyRemoveItemFromWishlist() throws InterruptedException {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Household']"))).click();

        WebElement productToSelect1 = driver.findElement(By.cssSelector("#list_456617"));
        productToSelect1.click();

        WebElement productToSelect2 = driver.findElement(By.cssSelector("#list_456602"));
        productToSelect2.click();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".top-nav__link")))).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='top-subnav']/ul/li/a[contains(@href,'wishlist')]")))).click();

        List<WebElement> removeBtns=driver.findElements(By.xpath("//a[@title='Remove Item']"));

        for (WebElement w:removeBtns) {
            w.click();
            Thread.sleep(5000);
        }


        List<WebElement> foundItemInWishList = driver.findElements(By.cssSelector(".product-name"));

        Assert.assertTrue(foundItemInWishList.isEmpty());

        //System.out.println(removeBtns);
        //Assert.assertTrue(removeBtns.isEmpty());

    }
}
