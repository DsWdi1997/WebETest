package webtest.core.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webtest.core.Page;

/**
 * 百度页面
 */


public class Baidupage extends Page {


    //输入框
    @FindBy(id = "kw")
    public WebElement Inputbox ;

    //按钮<百度一下>
    @FindBy(id = "su")
    public WebElement BaiduButton ;
}
