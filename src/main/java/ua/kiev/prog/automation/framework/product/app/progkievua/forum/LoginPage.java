package ua.kiev.prog.automation.framework.product.app.progkievua.forum;

import org.openqa.selenium.By;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;
import ua.kiev.prog.automation.framework.core.product.component.object.WidgetObject;
import ua.kiev.prog.automation.framework.core.product.component.object.widget.Button;
import ua.kiev.prog.automation.framework.core.product.component.object.widget.TextBox;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.base.ForumPageObject;

/**
 * ////////////////////////////////////////////////////////// *
 * Automation Framework                                       *
 * Automation Course for https://prog.kiev.ua/                *
 * ---------------------------------------------------------- *
 * Created by Yurii Voronenko                                 *
 * Email: yurii.voronenko@gmail.com                           *
 * ////////////////////////////////////////////////////////// *
 */
public class LoginPage extends ForumPageObject
{
    private TextBox _username = new TextBox(this.driver(), By.xpath("//form[@id='frmLogin']//input[@name='user']"));
    private TextBox _password = new TextBox(this.driver(), By.xpath("//form[@id='frmLogin']//input[@name='passwrd']"));
    private Button _loginBtn = new Button(this.driver(), By.xpath("//form[@id='frmLogin']//input[@type='submit']"));
    @Override
    protected Class<? extends Component> componentClass()
    {
        return Forum.class; // Привязываем к компоненту Forum
    }

    @Override
    protected By readyLocator()
    {
        return By.xpath("//form[@id='frmLogin']"); // Локатор готовности страницы
    }

    final public MainPageLoggedIn login (String username, String password)
    {
        _username.setValue(username);
        _password.setValue(password);
        System.out.println(_username.getValue());
        System.out.println(_password.getValue());
        _loginBtn.push();

        return new MainPageLoggedIn();
    }
}
