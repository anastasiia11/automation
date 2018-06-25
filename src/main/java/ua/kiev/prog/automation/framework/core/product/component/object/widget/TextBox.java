package ua.kiev.prog.automation.framework.core.product.component.object.widget;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import ua.kiev.prog.automation.framework.core.product.component.object.WidgetObject;

public class TextBox extends WidgetObject
{
    public TextBox(RemoteWebDriver driver, By locator) {
        super(driver, locator);
    }

    public void setValue(String value)
    {
        this.element().sendKeys(value);
    }

    public String getValue()
    {
        return this.element().getAttribute("value");
    }
}
