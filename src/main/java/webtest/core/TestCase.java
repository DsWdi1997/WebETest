package webtest.core;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.annotations.*;
import webtest.core.listenner.LogEventListener;
import webtest.core.util.Mysql;

import static org.testng.Assert.assertEquals;
/**
 * 所有TestNG TestCase都继承这个类。 这个类的功能是： 1，让testNG可以设置测试执行的浏览器类型
 * 2，提供一个log变量可以在每个case里直接用 3，在测试用例开始和结束时打log标明case名称 4，在测试方法开始和结束时启动和销毁浏览器
 * 5，由内部嵌套类DriverManager来负责创建和分配具体的driver
 */
                    public class TestCase {
    /**
     * 打log用的对象,this表示具体的子类。
     */
    protected Log log = LogFactory.getLog(this.getClass());
    private Mysql sql = new Mysql();

    /**
     * 决定这个TestCase是用什么浏览器的driver来执行。
     * 由于设置了BeforeMethod标签，这个方法将由TestNG在每个TestMethod被执行前调用。
     * 他将接收一个从TestNG的xml文件传入的参数表示浏览器种类。 告诉manager我要新建的driver的类型。
     *
     * @param driverName  浏览器名字	默认启动firefox
     * @param chromrPath  chromeDriver目录	默认使用resources下的driver启动默认安装路径的浏览器
     * @param iePath      ieServerDriver目录	默认使用resources下的driver启动默认安装路径的浏览器
     * @param firefoxPath firefox.exe目录	默认启动默认安装路径的浏览器
     */
    @BeforeMethod (alwaysRun = true)
    @Parameters({"DriverName", "ChromeDriverPath", "IeDriverPath", "FirefoxDriverPath"})
    protected void testMethodStart(
            @Optional("firefox") String driverName,
            @Optional("chrome") String chromrPath,
            @Optional("IE") String iePath,
            @Optional("moren") String firefoxPath) {
        DriverManager.setupDriver(driverName, chromrPath, iePath, firefoxPath);
    }

    /**
     * 在一个测试方法结束时关闭
     */

    @AfterClass(alwaysRun = true)
    protected void testMethodEnd() throws InterruptedException {
        Thread.sleep(10000);
        DriverManager.quitDriver();
    }

    /**
     * 打印类名。建议一个CASE只放一个方法
     */
    @BeforeClass(alwaysRun = true)
    protected void testCaseStart() {
        //打印分隔符
        log.info("####################################################");
        //打印类名
        log.info("\\/\\/\\/\\/\\/\\/---TestCase = "
                + this.getClass().getSimpleName() + "---\\/\\/\\/\\/\\/\\/");
    }

    /**
     * 再次打印类名
     */
    @AfterClass(alwaysRun = true)
    protected void testCaseEnd() {
        // 打印类名
        log.info("/\\/\\/\\/\\/\\/\\---TestCase = "
                + this.getClass().getSimpleName() + "---/\\/\\/\\/\\/\\/\\");
        // 打印分隔符
        log.info("#####################################################");
    }

    /**
     * 初始化mysql配置信息
     */

    /**
    @Parameters({"mysql_url", "mysql_user", "mysql_pwd"})
    @BeforeClass(alwaysRun = true)
    protected void mysqlInit(String url, String user, String pwd) {
        System.out.println(url + user + pwd);
        sql.setUrl(url);
        sql.setUserName(user);
        sql.setPassWord(pwd);
    }
*/
    /**
     * 使用testNG.xml 里的数据库配置连接数据库并
     * 用当前执行的测试类 完整包名+类名 作为用例名称为条件去数据库查找 测试用例数据
     * 如需要求其它用例名称的数据等待特殊情况请子类重写次方法
     *
     * @return mysql.getJDBCData(String caseName)返回的Object[][]对象数组
     */
    @DataProvider
    public Object[][] getData() {

        return sql.getJDBCData(this.getClass().getName());
    }

    /**
     * 静态内部类。因为如果把这些driver相关的东西直接放在TestCase类里，从逻辑上说不通。引入一个静态内部类来解决。
     */

    public static class DriverManager {
        /**
         * 每个DriverManager只管理一个driver，所以他是static的，但是引入ThreadLocal来处理多线程
         */

        public static ThreadLocal<WebDriver> ThreadDriver = new ThreadLocal<WebDriver>();

        /**
         * 当TestCase要设置浏览器类型时其实只是设置了browserType这个字段。
         * 而真正的新建driver是在Page类需要用到driver时
         * Page的构造方法里调用getDriver，然后getDriver检测发现当前线程没有driver时才会真正新建一个driver。
         */
        public static String browserType = null ;

        /**
         * 如果当前进程没有绑定driver,创建一个然后绑定上，然后已经有了就直接返回。
         */
        //private  static WebDriver driver ;
        private  static  WebDriver driver = DriverManager.ThreadDriver.get();
        public static WebDriver getDriver() {
            // WebDriver driver = DriverManager.ThreadDriver.get();
               if (driver == null) {
                 switch (browserType) {
                    case "ie":
                        driver = new EventFiringWebDriver(new InternetExplorerDriver()).register(new LogEventListener());
                        ThreadDriver.set(driver);
                        break;
                    case "chrome":
                        driver = new EventFiringWebDriver(new ChromeDriver()).register(new LogEventListener());
                        ThreadDriver.set(driver);
                        break;
                    case "firefox":
                        driver = new EventFiringWebDriver(new FirefoxDriver()).register(new LogEventListener());
                        ThreadDriver.set(driver);
                        break;
                }
                //找东西前等待3秒
                DriverManager.getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
           }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            //String handle = driver.getWindowHandle();
            //System.out.println(handle);
            return driver;
        }


        /**
         * 根据TestCase的要求来指定浏览器类型但不创建他
         *
         * @param driverName  浏览器名字
         * @param chromrPath  chromeDriver目录
         * @param iePath      ieServerDriver目录
         * @param firefoxPath firefox.exe目录
         */

        public static void setupDriver(String driverName, String chromrPath, String iePath, String firefoxPath) {
            browserType = driverName;
            switch (browserType) {
                case "ie":
                    System.out.println("IE浏览器Iedriver驱动地址:"+iePath);
                    System.setProperty("webdriver." + browserType + ".driver", iePath);
                    break;
                case "chrome":
                    System.out.println("谷歌ChromeDriver驱动地址:"+chromrPath);
                    System.setProperty("webdriver." + browserType + ".driver", chromrPath);
                    break;
                case "firefox":
                    if (!firefoxPath.equals("moren")) {
                        System.setProperty("webdriver" + browserType + ".bin", firefoxPath);
                    }
                    break;
            }
        }

        public static void quitDriver() {
            driver.quit();
            //getDriver().quit();
            //DriverManager.ThreadDriver.set(null);
        }
    }


}
