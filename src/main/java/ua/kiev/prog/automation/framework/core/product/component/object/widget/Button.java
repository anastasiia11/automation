package ua.kiev.prog.automation.framework.core.product.component.object.widget;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import ua.kiev.prog.automation.framework.core.ResultLog;
import ua.kiev.prog.automation.framework.core.product.component.object.WidgetObject;

public class Button extends WidgetObject
{
    public Button(RemoteWebDriver driver, By locator) {
        super(driver, locator);
    }

    public void push ()
    {
        ResultLog.getInstance().writeWidget("Button","push", " ");
        this.click();
    }

    public String getValue()
    {
        return this.element().getAttribute("value");
    }
}
