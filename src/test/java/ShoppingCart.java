import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;


public class ShoppingCart {

    private WebDriver driver;
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://shop.pragmatic.bg/index.php?route=product/product&product_id=43");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void Test() {
        WebElement addToCart = driver.findElement(By.xpath("//*[@id=\"button-cart\"]"));
        Actions builder = new Actions(driver);
        builder.click(addToCart).perform();
        WebElement shoppingCart = driver.findElement(By.xpath("//*[@id=\"cart-total\"]"));
        String titleMainWindow =  driver.getTitle();
        System.out.println("Main = " + titleMainWindow);
        builder.click(shoppingCart).perform();

        Set<String> windowHandles = driver.getWindowHandles();
        String mainWindowHandle = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                String popupTitle = driver.getTitle();
                System.out.println("popup = " + popupTitle);
                //driver.switchTo().window("dropdown-menu pull-right");
                //String titleShoppingCart = driver.getTitle();
                //System.out.println(titleShoppingCart);
                //if(driver.getPageSource().contains("Checkout"))
                break;
            }
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cart\"]/ul/li[2]/div/p/a[2]/strong")));
        WebElement checkOutButton = driver.findElement(By.xpath("//*[@id=\"cart\"]/ul/li[2]/div/p/a[2]/strong"));
        checkOutButton.click();


        //WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"button-login\"]"));
        //Assert.assertTrue(loginButton.isDisplayed(), "Nqma login buton");
        //String loginText = loginButton.getText();
        //System.out.println(loginText);
        //Assert.assertEquals( loginText,"Login", "Greshka");

       //ASSERT
        WebElement checkOuttext = driver.findElement(By.xpath("//*[@id=\"content\"]/h1"));
        String actualText = checkOuttext.getText();
        Assert.assertEquals(actualText, "Checkout", "Greshka");

        //moje i s page source na vidim tekst

    }
}
