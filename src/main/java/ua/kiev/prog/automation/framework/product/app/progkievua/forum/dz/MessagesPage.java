package ua.kiev.prog.automation.framework.product.app.progkievua.forum.dz;

import org.openqa.selenium.By;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.base.ForumPageObject;

public class MessagesPage extends ForumPageObject {

    private By _messagesText = By.xpath("//*[@id=\"personal_messages\"]/form/table/tbody/tr/td");

    @Override
    protected Class<? extends Component> componentClass() {
        return Forum.class;
    }

    @Override
    protected By readyLocator() {
        return By.xpath("//div[@id = 'main_content_section']");
    }

    final public String getMessagesText ()

    {
        String messagesText = this.component().session().driver().findElement(_messagesText).getText();
        System.out.println("Personal messages: " + messagesText);
        return  messagesText;
    }
}
