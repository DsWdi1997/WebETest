package cases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webtest.core.TestCase;

public class DemoTest {
    @Parameters({"driverName","baidu_url"})
    @Test
    public void baidu(String driverName ,String url ){
        System.out.println(driverName+url);
        WebDriver driver =TestCase.DriverManager.getDriver();
        driver.get(url);

    }
}
