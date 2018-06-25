package ua.kiev.prog.automation.framework.product.app.progkievua.forum.dz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.base.ForumPageObject;

import java.util.ArrayList;
import java.util.List;

public class TopicPage extends ForumPageObject
{
    @Override
    protected Class<? extends Component> componentClass() {
        return Forum.class;
    }

    @Override
    protected By readyLocator() {
        return By.xpath("//div[@id='forumposts']");
    }

    public List<String> getAuthors ()
    {
        List<String> result = new ArrayList<>();
        List<WebElement> list = this.driver().findElements(By.xpath("//div[@class='poster']//h4/a"));
        for (WebElement elem: list) {
            result.add(elem.getText());
        }
        return result;
    }
}
