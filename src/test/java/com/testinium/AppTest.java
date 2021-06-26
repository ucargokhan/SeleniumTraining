package com.testinium;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AppTest 
{
    //global driver instance
    WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-features=TranslateUI");

        driver = new ChromeDriver(options);
    }

    @Test
    public void navigateBetweenWebSites() {
        driver.get("https://www.google.com/");
        driver.navigate().to("https://www.youtube.com");
    }

    @Test
    public void otherTest() throws InterruptedException {
        driver.navigate().to("https://www.youtube.com");
        WebElement element1 = driver.findElement(By.name("search_query"));
        TimeUnit.SECONDS.sleep(2);
        element1.sendKeys("Cem Karaca Emrah");
        TimeUnit.SECONDS.sleep(2);
        element1.submit();
        TimeUnit.SECONDS.sleep(2);
        element1 = driver.findElement(By.name("search_query"));
        //element1.sendKeys(Keys.LEFT, Keys.BACK_SPACE);
        element1.clear();
        TimeUnit.SECONDS.sleep(2);


        WebElement element2 = driver.findElement(By.xpath("//*[@id=\"search-icon-legacy\"]"));
        element2.click();
        TimeUnit.SECONDS.sleep(5);

        List<WebElement> elements = driver.findElements(By.name("search_query"));
        System.out.println("elements size: " + elements.size());
    }

    @Test
    public void googleSearchTest() throws InterruptedException {
        driver.navigate().to("https://www.google.com");

        WebElement element = driver.findElement(By.name("q"));
        TimeUnit.SECONDS.sleep(2);
        element.sendKeys("Barış Manço");
        element.submit();

        element = driver.findElement(By.name("q"));
        element.sendKeys(Keys.PAGE_DOWN);

        TimeUnit.SECONDS.sleep(3);

        WebElement barisMancoClick = driver.findElement(By.cssSelector("a[href=\"https://tr.wikipedia.org/wiki/Bar%C4%B1%C5%9F_Man%C3%A7o\"]"));
        barisMancoClick.click();
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void selectionTest() throws InterruptedException {
        driver.navigate().to("https://www.seleniumeasy.com/test/basic-select-dropdown-demo.html");

        Select select = new Select(driver.findElement(By.id("select-demo")));

        select.selectByVisibleText("Sunday");
        TimeUnit.SECONDS.sleep(3);

        System.out.println("Selected Option: " + select.getFirstSelectedOption().getText());
        System.out.println("Selectable Options Size: " + select.getOptions().size());
    }

    @Test
    public void actionsTest() throws InterruptedException {
        Actions actions = new Actions(driver);

        driver.navigate().to("https://www.gittigidiyor.com/");

        //HOVER ELEMENT
        WebElement element = driver.findElement(By.cssSelector("div[data-cy=\"header-user-menu\"]"));
        actions.moveToElement(element).build().perform();

        TimeUnit.SECONDS.sleep(3);

        actions.contextClick().build().perform();
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void switchToTest() throws InterruptedException {
        driver.navigate().to("https://www.seleniumeasy.com/test/window-popup-modal-demo.html");

        String currentWindow = driver.getWindowHandle();

        driver.findElement(By.linkText("Follow On Twitter")).click();

        Set<String> windowHandles = driver.getWindowHandles();

        for (String newWindow: windowHandles
             ) {
            if(!newWindow.equals(currentWindow)){
                driver.switchTo().window(newWindow);
            }
        }

        WebElement clickCancel = driver.findElement(By.name("session[username_or_email]"));
        clickCancel.sendKeys("Testinium");
        TimeUnit.SECONDS.sleep(3);
        driver.close();

        driver.switchTo().window(currentWindow);
        driver.findElement(By.id("followall")).click();

    }

    @After
    public void tearDown(){
        //driver.quit();
    }
}
