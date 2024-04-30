package com.pondland;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;


import java.time.Duration;

public class CartVerification {

    WebDriver driver;
    WebDriverWait wait;

    JavascriptExecutor executor;

    @BeforeSuite
    public void launchBrowser(){
        driver = new ChromeDriver();
    }

    @BeforeTest
    public void openApplication(){
        driver.get("https://www.poundland.co.uk/");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }



    @Test
    public void signIn(){
        driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
        driver.findElement(By.xpath("//button[starts-with(@id,'onetrust-accept')]")).click();
        driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']")).click();
        WebElement siginlink = driver.findElement(By.xpath("//li[contains(@class,'customer-account')]"));
        siginlink.click();
        WebElement emailinputfeild = driver.findElement(By.xpath("//input[@id='email']"));
        emailinputfeild.sendKeys("chandralekhachakka555@gmail.com");
        WebElement passwordinputfeild  = driver.findElement(By.xpath("//input[@name='login[password]']"));
        passwordinputfeild.sendKeys("Sweety44.");
        WebElement siginbtn = driver.findElement(By.xpath("//button[@id='send3']"));
        siginbtn.click();


    }

    @Test(dependsOnMethods = "signIn")
    public void verifyCartIsEmpty(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        String Expecteditemstxt = "You have no items in your basket.";

       WebElement itemstxt =  driver.findElement(By.xpath("//p[contains(text(),'You have no items')]"));
       String actualitemstxt = itemstxt.getText();
       //Assert.assertTrue(itemstxt.isDisplayed());
        Assert.assertEquals(actualitemstxt,Expecteditemstxt);
    }

    @Test(dependsOnMethods = "signIn")
    public void addProducts() throws InterruptedException{
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebElement priceElement = driver.findElement(By.xpath("//li[contains(@class,'parent top-level')]/a[text()='Price Pounders']"));
        priceElement.click();
        WebElement addelement = driver.findElement(By.xpath("//*[contains(@class, 'product-item-name')]//a[@href='https://www.poundland.co.uk/domestos-thick-bleach-original-750-ml']"));
       String addedtxt = addelement.getText();
        System.out.println(addedtxt);
        Actions actions = new Actions(driver);
        actions.scrollToElement(addelement).perform();
        addelement.click();
        WebElement addtobasketbtn = driver.findElement(By.xpath("//button[@title='Add to Basket']"));
        addtobasketbtn.click();
        Thread.sleep(5000);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        WebElement basketelementtxt = driver.findElement(By.xpath("//div[@class='product-item-details']/strong/a"));
       String cartProducttxt = basketelementtxt.getText();
        System.out.println(cartProducttxt);
        Thread.sleep(5000);
        Assert.assertEquals(cartProducttxt,addedtxt);

    }

    @Test(dependsOnMethods = "signIn")
    public void verifyQuantityAndPrice() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        WebElement qtyvalue = driver.findElement(By.xpath("( //a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-qty']/descendant::span[@class='minus']/following::input[@type='number'])[1]"));
        String typevalue = qtyvalue.getAttribute("value");
        System.out.println("Number of the quantity " +typevalue);
        WebElement addedelementprice = driver.findElement(By.xpath("//a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-price']/descendant::span[@class='price']"));
        String addedelementpricetxt = addedelementprice.getText();
        System.out.println(addedelementpricetxt);
        addedelementpricetxt = addedelementpricetxt.replace("£"," ");

        int qutyvalue = Integer.parseInt(typevalue);
        System.out.println("Integer quantity value " + qutyvalue);

        float pricevalue = Float.parseFloat(addedelementpricetxt);
        System.out.println("float price value " + pricevalue);

        float totalprice = qutyvalue * pricevalue;
        System.out.println("Total price is " + totalprice);

       WebElement subtotalprice = driver.findElement(By.xpath("//a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-subtotal']/descendant::span[@class='price']"));
       String subtotalpricetxt = subtotalprice.getText();
        System.out.println(subtotalpricetxt);
        subtotalpricetxt = subtotalpricetxt.replace("£", " ");

        float subtotalprice1 = Float.parseFloat(subtotalpricetxt);
        System.out.println("float subtotal price value " + subtotalprice1);

        Assert.assertEquals(subtotalprice1,totalprice);
    }

    @Test(dependsOnMethods = "signIn")
    public void verifyNavigateToCartPage(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebElement homepage =  driver.findElement(By.xpath("//a[@href='https://www.poundland.co.uk/home']/ancestor::li[contains(@class,'parent top-level')]"));
        homepage.click();
        Actions actions = new Actions(driver);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        String actualtitle = driver.getTitle();
        String expectedtitle = "Basket";
        Assert.assertEquals(actualtitle,expectedtitle);

    }

    @Test(dependsOnMethods = "signIn")
    public void loginAndLogOut() throws InterruptedException{
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        WebElement myaccount = driver.findElement(By.xpath("//a[@class='top-nav__link']"));
        myaccount.click();
        WebElement logoutbtn = driver.findElement(By.xpath("//a[@class='top-subnav__link' and text()='Log out']"));
        logoutbtn.click();
        WebElement siginlink = driver.findElement(By.xpath("//li[contains(@class,'customer-account')]"));
        siginlink.click();
        WebElement emailinputfeild = driver.findElement(By.xpath("//input[@id='email']"));
        emailinputfeild.sendKeys("chandralekhachakka555@gmail.com");
        WebElement passwordinputfeild  = driver.findElement(By.xpath("//input[@name='login[password]']"));
        passwordinputfeild.sendKeys("Sweety44.");
        WebElement siginbtn = driver.findElement(By.xpath("//button[@id='send3']"));
        siginbtn.click();
        Actions actions1 = new Actions(driver);
        WebElement Basketicon1 = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions1.moveToElement(Basketicon1).click().perform();
        WebElement addedelement = driver.findElement(By.xpath("//div[@class='product-item-details']/strong/a"));
        System.out.println(addedelement.getText());
        Assert.assertTrue(addedelement.isDisplayed());
    }


    @Test(dependsOnMethods = "signIn")
    public void quantityIncreased() {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        WebElement qtyvalue = driver.findElement(By.xpath("( //a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-qty']/descendant::span[@class='minus']/following::input[@type='number'])[1]"));
        System.out.println("Before adding the quantity");
        String typevalue = qtyvalue.getAttribute("value");
        System.out.println("Number of the quantity " +typevalue);
        int qutyvalue1 = Integer.parseInt(typevalue);
        System.out.println("Integer quantity value " + qutyvalue1);
        qutyvalue1 = qutyvalue1 + 1;
        System.out.println(qutyvalue1);
        WebElement updatedqtyvalue= driver.findElement(By.xpath("//a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-qty']/descendant::span[@class='plus']"));
        updatedqtyvalue.click();
        WebElement updatedqtyvalue1 = driver.findElement(By.xpath("( //a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-qty']/descendant::span[@class='minus']/following::input[@type='number'])[1]"));
        System.out.println("After updated the quantity");
        String typevalue1 = updatedqtyvalue1.getAttribute("value");
        System.out.println("Number of the quantity " +typevalue1);
        int updatedqtyvalue2 = Integer.parseInt(typevalue1);
        System.out.println("Integer quantity value " + updatedqtyvalue2);
       Assert.assertEquals(updatedqtyvalue2,qutyvalue1);

    }

    @Test(dependsOnMethods = "signIn")
    public void verifyUpdatedQuantityAndPrice()throws InterruptedException{
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        WebElement updatedqtyvalue= driver.findElement(By.xpath("//a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-qty']/descendant::span[@class='plus']"));
        updatedqtyvalue.click();
        WebElement qtyvalue = driver.findElement(By.xpath("( //a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-qty']/descendant::span[@class='minus']/following::input[@type='number'])[1]"));
        String typevalue = qtyvalue.getAttribute("value");
        int  qtyvalue1 = Integer.parseInt(typevalue);
        System.out.println("Interger value of the quantity " + qtyvalue1);
        WebElement addedelementprice = driver.findElement(By.xpath("//a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-price']/descendant::span[@class='price']"));
        String addedelementpricetxt = addedelementprice.getText();
        addedelementpricetxt = addedelementpricetxt.replace("£", " ");
        System.out.println(addedelementpricetxt);
        float pricevalue = Float.parseFloat(addedelementpricetxt);
        System.out.println("float value of the price value " + pricevalue);
        float totalprice = pricevalue * qtyvalue1;
        System.out.println("total price of the items " + totalprice);
        Thread.sleep(7000);
        WebElement subtotalprice = driver.findElement(By.xpath("//a[contains(text(),'Domestos  Thick Bleach')]/ancestor::div[@class='product-item-details']/ancestor::td/following-sibling::td[@class='col col-subtotal']/descendant::span[@class='price']"));
        String subtotalpricetxt = subtotalprice.getText();
        subtotalpricetxt = subtotalpricetxt.replace("£", " ");
        System.out.println(subtotalpricetxt);
        float subtotalprice1 = Float.parseFloat(subtotalpricetxt);
        System.out.println("float subtotal price value " + subtotalprice1);
        Assert.assertEquals(subtotalprice1,totalprice);

    }

    @Test(dependsOnMethods = "signIn")
    public void removeProduct()  {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        WebElement removeItem = driver.findElement(By.xpath("//a[contains(text(),'Domestos  Thick Bleach')]/ancestor::strong/following-sibling::ul[@class='actions']//li[@class='action-delete']"));
        removeItem.click();
        WebElement itemstxt =  driver.findElement(By.xpath("//p[contains(text(),'You have no items')]"));
        Assert.assertTrue(itemstxt.isDisplayed());

    }

    @Test(dependsOnMethods = "signIn")
    public void addProductsFromSearchBar() throws InterruptedException{
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebElement searchbarbtn = driver.findElement(By.cssSelector("#search_mini_form"));
        searchbarbtn.click();
        List<WebElement>listofitems = driver.findElements(By.xpath("//div[@class='klevu-pt-rs-keywords']/descendant::a"));
        System.out.println(listofitems.size());
        for(int i=0; i<listofitems.size(); i++){
            if(listofitems.get(i).getText().equals("Shampoo")){
                listofitems.get(i).click();
                break;
            }
        }
        WebElement item = driver.findElement(By.xpath("//*[contains(@class, 'product-item-name')]//a[@href='https://www.poundland.co.uk/sunday-hair-smooth-shampoo-350ml']"));
        String itemtxt = item.getText();
        System.out.println(itemtxt);
        Actions actions = new Actions(driver);
        actions.scrollToElement(item).perform();
        item.click();
        WebElement addtobasketbtn = driver.findElement(By.xpath("//button[@title='Add to Basket']"));
        addtobasketbtn.click();
        Thread.sleep(5000);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        WebElement basketelementtxt = driver.findElement(By.xpath("//div[@class='product-item-details']/strong/a"));
        String carttxt = basketelementtxt.getText();
        System.out.println(carttxt);
        Thread.sleep(5000);
        //Assert.assertEquals(carttxt,addedtxt);
        Assert.assertTrue(carttxt.contains(itemtxt));

    }













}
