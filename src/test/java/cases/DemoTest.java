package cases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webtest.core.TestCase;
import webtest.core.page.Baidupage;

public class DemoTest extends TestCase{

    @Parameters({"baidu_url","Inputbox",})
    @Test
    public void VisitBaidu1(String url,String Inputbox)  {
        System.out.println("访问的地址"+url);
        DriverManager.getDriver().get(url);
        Baidupage baidupage = new Baidupage();
        baidupage.Inputbox.sendKeys(Inputbox);
        baidupage.BaiduButton.click();
        long id = Thread.currentThread().getId();
        System.out.println("VisitBaidu1:"+id);
    }

    @Parameters({"baidu_url","Inputbox",})
    @Test
    public void VisitBaidu2(String url,String Inputbox)  {
        System.out.println("访问的地址"+url);
        DriverManager.getDriver().get(url);
        Baidupage baidupage = new Baidupage();
        baidupage.Inputbox.sendKeys(Inputbox);
        baidupage.BaiduButton.click();
        long id = Thread.currentThread().getId();
        System.out.println("VisitBaidu2:"+id);
    }



}
