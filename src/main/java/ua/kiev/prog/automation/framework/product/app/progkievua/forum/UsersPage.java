package ua.kiev.prog.automation.framework.product.app.progkievua.forum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.base.ForumPageObject;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.widgets.UserWidget;

import java.util.ArrayList;
import java.util.List;

public class UsersPage extends ForumPageObject {
    @Override
    protected Class<? extends Component> componentClass() {
        return Forum.class;
    }

    @Override
    protected By readyLocator() {
        return By.xpath("//div[@id = 'memberlist']");
    }

    public List<String> getUsersList ()
    {
        List<String> result = new ArrayList<>();
        List<WebElement> list = this.driver().findElements(By.xpath("//td[@class='windowbg lefttext']//a"));
        for (WebElement elem: list) {
            result.add(elem.getText());
        }
        return result;
    }
    public UserWidget findUser (String userName){
        // Collect records
        List<WebElement> list = this.driver().findElements(By.xpath("//div[@id='mlist']/table//tbody/tr"));
        List<UserWidget> usersList = new ArrayList<>();
        for (int i = 1; i <= list.size(); i ++) {
            usersList.add(new UserWidget(this.driver(), By.xpath("//div[@id='mlist']/table//tbody/tr["+i+"]")));
        }
        // Find user by name
        UserWidget resultUser = null;
        for (UserWidget user: usersList) {
            if (userName.equals(user.getUserName())) {
                resultUser = user;
                break;
            }
        }
        // Recursion if not found
        if (resultUser == null) {
            WebElement nextPage = this.driver().findElement(By.xpath("(//div[contains(@class, 'pagelinks')]//strong/following-sibling::a)[1]"));
            nextPage.click();
            return this.findUser(userName);
        }
        return resultUser;
    }
}
