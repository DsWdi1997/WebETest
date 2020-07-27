package webtest.core;
import java.sql.DriverManager;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
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
     * @param driverName	浏览器名字	默认启动firefox
     * @param chromrPath	chromeDriver目录	默认使用resources下的driver启动默认安装路径的浏览器
     * @param iePath	ieServerDriver目录	默认使用resources下的driver启动默认安装路径的浏览器
     * @param firefoxPath	firefox.exe目录	默认启动默认安装路径的浏览器
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({"DriverName","ChromeDriverPath","IeDriverPath","FirefoxDriverPath"})
    protected void  testMethodStart(
            @Optional("firefox") String driverName,
            @Optional("chrome") String chromrPath,
            @Optional("IE") String iePath,
            @Optional("moren") String firefoxPath){
        DriverManager.setupDriver(driverName,chromrPath,iePath,firefoxPath);
    }

    /**
     * 在一个测试方法结束时关闭
     */
    @AfterMethod(alwaysRun =  true)
    protected void testMethodEnd(){DriverManager.quitDriver();}

    /**
     * 打印类名。建议一个CASE只放一个方法
     */
    @BeforeClass(alwaysRun =  true)
    protected void testCaseStart(){
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


    public static  class  DriverManager{

        public static void setupDriver(String driverName, String chromrPath, String iePath, String firefoxPath) {

        }

        public static void quitDriver() {
        }
    }






























}
