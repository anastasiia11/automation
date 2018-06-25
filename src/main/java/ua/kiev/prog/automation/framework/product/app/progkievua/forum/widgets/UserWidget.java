package ua.kiev.prog.automation.framework.product.app.progkievua.forum.widgets;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import ua.kiev.prog.automation.framework.core.product.component.object.WidgetObject;

public class UserWidget extends WidgetObject{
    public UserWidget(RemoteWebDriver driver, By locator) {
        super(driver, locator);
    }
 public  String getUserName (){
     return this.element().findElement(By.xpath("./td[2]")).getText();
 }
    public  String getRegistrationDate (){
        return this.element().findElement(By.xpath("./td[10]")).getText();
    }
}
