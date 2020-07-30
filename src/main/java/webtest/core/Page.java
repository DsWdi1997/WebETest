package webtest.core;

import org.openqa.selenium.support.PageFactory;
import webtest.core.TestCase.DriverManager;

public class Page {

	/**
	 * 构造方法，被所有Page的子类继承，所以每个页面都可以通过自动调用这个方法来初始化页面对象 
	 */
	public Page() {
		this.init();
		PageFactory.initElements(DriverManager.getDriver(), this);
	}
	
	/**
	 * 初始化页面的前置条件
	 */
	protected void init(){
		DriverManager.getDriver().switchTo().defaultContent();
	}
	
}
