package webtest.core.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webtest.core.Page;

public class Chandaopage extends Page {

    @FindBy(id = "account")
    public WebElement user ;

    @FindBy(name="password")
    public WebElement pw ;
}
