package ua.kiev.prog.automation.framework.product.app.progkievua.forum.dz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.base.ForumPageObject;

public class BoardPage extends ForumPageObject
{
    @Override
    protected Class<? extends Component> componentClass() {
        return Forum.class;
    }

    @Override
    protected By readyLocator() {
        return By.xpath("//table[@class='table_grid']");
    }

    public TopicPage getTopicPage (String topicName)
    {
        By topicLink = By.xpath("//td[contains(@class,'subject')]//span/a[contains(text(), '"+topicName+"')]");
        WebElement topic = this.driver().findElement(topicLink);
        topic.click();

        return new TopicPage();
    }
}
