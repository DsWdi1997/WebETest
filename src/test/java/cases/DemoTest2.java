package cases;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webtest.core.TestCase;
import webtest.core.page.Baidupage;
import webtest.core.page.Chandaopage;

public class DemoTest2 extends TestCase{

    @DataProvider(name = "range-provider",parallel = true)
    public Object[][] provideNumbers(){
        return new Object[][]  {
                {"http://172.20.52.101/zentao/user-login.html","miaokeyang",""},
                {"http://172.20.52.101/zentao/user-login.html","","sailing@123"}
        };
    }
    @Test(dataProvider = "range-provider")
    public void testIsBetween(String url ,String user,String pw)
    {
        String handle = DriverManager.getDriver().getWindowHandle();
        System.out.println("当前页面的句柄(1):"+handle);
        System.out.println("当前访问的地址(1):"+url);
        TestCase.DriverManager.getDriver().get(url);
        Chandaopage chandaopage = new Chandaopage();
        chandaopage.user.sendKeys(user);
        chandaopage.pw.sendKeys(pw);



    }

}

