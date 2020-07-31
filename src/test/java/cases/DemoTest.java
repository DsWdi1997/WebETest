package cases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webtest.core.TestCase;
import webtest.core.page.Baidupage;

public class DemoTest extends TestCase{

    @Parameters({"baidu_url","Inputbox"})
    @Test
    public void VisitBaidu1(String url ,String Inputbox)  {
        String handle = DriverManager.getDriver().getWindowHandle();
        System.out.println("当前页面的句柄(1):"+handle);
        System.out.println("当前访问的地址(1):"+url);
        DriverManager.getDriver().get(url);
        Baidupage baidupage = new Baidupage();
        baidupage.Inputbox.sendKeys(Inputbox);
        baidupage.BaiduButton.click();
    }

    @Parameters({"baidu_url","Inputbox"})
    @Test
    public void VisitBaidu2(String url ,String Inputbox)  {
        String handle = DriverManager.getDriver().getWindowHandle();
        System.out.println("当前页面的句柄(2):"+handle);
        System.out.println("当前访问的地址(2):"+url);
        DriverManager.getDriver().get(url);
        Baidupage baidupage = new Baidupage();
        baidupage.Inputbox.sendKeys(Inputbox+"123");
        baidupage.BaiduButton.click();
    }
}
