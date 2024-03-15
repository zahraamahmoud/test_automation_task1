import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Swaglabs {


    WebDriver driver;
    WebDriverManager driverManager;
    WebDriverWait wait;
    String url="https://www.saucedemo.com/v1/";
    String username="standard_user";
    String password="secret_sauce";
    String itemName="Sauce Labs Backpack";
    By usernamelocator=By.id("user-name");
    By passwordlocator=By.id("password");
    By loginbtnlocator=By.id("login-button");
//    By title=By.className("title");
    By itemslocator=By.className("inventory_item");
    By shoppingcartlocator=By.xpath("//a[contains(@class,\"shopping_cart_link\")]");

    By itemlocatorincart=By.className("inventory_item_name");
    @BeforeClass
    public void setup() {
        driverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.navigate().to(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.manage().window().maximize();
    }
    @Test
    public void test1(){

        login(username,password);
        List<WebElement> itemslist=driver.findElements(itemslocator);
        assertEquals(itemslist.size(),6,"number of items is wrong");
        driver.navigate().back();

    }

    @Test
    public void test2(){
        login(username,password);
        addtocart(itemName).click();
        WebElement shoppingcartlink=driver.findElement(shoppingcartlocator);
        shoppingcartlink.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(itemlocatorincart));
        assertTrue(driver.findElement(itemlocatorincart).getText().contains(itemName),"the element is not added to the cart");
        driver.navigate().back();
    }
    @AfterClass
    public void teardown(){

        driver.close();
    }


    public WebElement addtocart(String itemname){

        By addtocartbtnlocator=By.xpath("//div[text()=\""+itemname+"\"]/ancestor::div[@class=\"inventory_item_label\"]/following-sibling::div[@class=\"pricebar\"]/button[contains(@class,\"btn_primary\")]");

        return driver.findElement(addtocartbtnlocator);

    }
    public void login(String username,String password){
        WebElement usernameelement= driver.findElement(usernamelocator);
        WebElement passwordelement= driver.findElement(passwordlocator);
        WebElement loginbtn=driver.findElement(loginbtnlocator);
        usernameelement.clear();
        usernameelement.sendKeys(username);
        passwordelement.clear();
        passwordelement.sendKeys(password);
        loginbtn.click();
    }
}
