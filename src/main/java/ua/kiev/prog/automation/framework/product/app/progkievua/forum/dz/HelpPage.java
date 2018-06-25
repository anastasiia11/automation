package ua.kiev.prog.automation.framework.product.app.progkievua.forum.dz;

import org.openqa.selenium.By;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.base.ForumPageObject;

public class HelpPage extends ForumPageObject {

    private By _helpText = By.xpath("//*[@id=\"helpmain\"]/p[1]");

    @Override
    protected Class<? extends Component> componentClass() {
        return Forum.class;
    }

    @Override
    protected By readyLocator() {
         return By.xpath("//div[@id = 'content_section']");
    }
    final public String getHelpText ()

    {
        String helptext = this.component().session().driver().findElement(_helpText).getText();
        System.out.println("Help text: " + helptext);
        return  helptext;
    }

}
