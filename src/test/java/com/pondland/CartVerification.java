package com.pondland;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class CartVerification {

    WebDriver driver;
    WebDriverWait wait;

    JavascriptExecutor executor;

    @BeforeTest
    public void openApplication(){
        driver = new ChromeDriver();
        driver.get("https://www.poundland.co.uk/");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    @Test
    public void verifyCardisEmpty(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.xpath("//button[starts-with(@id,'onetrust-accept')]")).click();
        driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']")).click();
        Actions actions = new Actions(driver);
        WebElement Basketicon = driver.findElement(By.xpath("//*[name()='svg']//*[name()='g' and @id='Icon-/-Basket']"));
        actions.moveToElement(Basketicon).click().perform();
        String Expecteditemstxt = "You have no items in your basket.";

       WebElement itemstxt =  driver.findElement(By.xpath("//p[contains(text(),'You have no items')]"));
       String actualitemstxt = itemstxt.getText();
       //Assert.assertTrue(itemstxt.isDisplayed());
        Assert.assertEquals(actualitemstxt,Expecteditemstxt);
    }

    public void sample(){
        System.out.println();
    }


    @AfterTest
    public void closeBrowser(){
        driver.quit();
    }



}
