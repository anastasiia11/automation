package ua.kiev.prog.automation.framework.core.product.component.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

/**
 * ////////////////////////////////////////////////////////// *
 * Automation Framework                                       *
 * Automation Course for https://prog.kiev.ua/                *
 * ---------------------------------------------------------- *
 * Created by Yurii Voronenko                                 *
 * Email: yurii.voronenko@gmail.com                           *
 * ////////////////////////////////////////////////////////// *
 */
public class WidgetObject
{
    private RemoteWebDriver _driver;
    private By              _locator;

    public WidgetObject (RemoteWebDriver driver, By locator)
    {
        _driver     = driver;
        _locator    = locator;
    }

    protected WebElement element ()
    {
        try {
            WebDriverWait wait = new WebDriverWait(this._driver, 30);
            wait.until(new Function<WebDriver, Object>() {
                @Override
                public Object apply(WebDriver webDriver) {
                    return webDriver.findElements(_locator).size() > 0;
                }
            });
        } catch (Throwable e) {
            throw new RuntimeException("Element not  found!");
        }
        return this._driver.findElement(_locator);
    }

    public void click ()
    {
        this.element().click();//comment
    }

    public String getText ()
    {
        return this.element().getText();
    }


}