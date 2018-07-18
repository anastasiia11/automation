package ua.kiev.prog.automation.framework.product.app.progkievua.forum.dz;

import org.openqa.selenium.By;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.widget.Button;
import ua.kiev.prog.automation.framework.core.product.component.object.widget.TextBox;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.base.ForumPageObject;

public class ProfilePage extends ForumPageObject {

    private Button _statisticBtn = new Button(this.driver(), By.xpath("//*[@id=\"infolinks\"]/a[2]"));
    private By _profileName = By.xpath("//*[@id=\"detailedinfo\"]/div/div/dl[1]/dd[1]");

    @Override
    protected Class<? extends Component> componentClass() {
        return Forum.class;
    }

    @Override
    protected By readyLocator() {
        return By.xpath("//div[@id= 'admin_content']");
    }

    final public ProfilePage showStatistic(){

        _statisticBtn.push();
        return new ProfilePage();
    }
   final public String getProfileName (){
       String profileName = this.component().session().driver().findElement(_profileName).getText();
       return  profileName;
    }
}
