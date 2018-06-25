package ua.kiev.prog.automation.framework.core.product.component.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

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
    private WebElement      _element;

    public WidgetObject (RemoteWebDriver driver, By locator)
    {
        _driver     = driver;
        _locator    = locator;
    }

    protected WebElement element ()
    {
        if (_element == null)
            _element = _driver.findElement(_locator);
        return _element;
    }

    public void click ()
    {
        this.element().click();
    }

    public String getText ()
    {
        return this.element().getText();
    }


}
