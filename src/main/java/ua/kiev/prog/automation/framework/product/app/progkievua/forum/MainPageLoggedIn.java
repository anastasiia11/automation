package ua.kiev.prog.automation.framework.product.app.progkievua.forum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.base.ForumPageObject;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.dz.BoardPage;

/**
 * ////////////////////////////////////////////////////////// *
 * Automation Framework                                       *
 * Automation Course for https://prog.kiev.ua/                *
 * ---------------------------------------------------------- *
 * Created by Yurii Voronenko                                 *
 * Email: yurii.voronenko@gmail.com                           *
 * ////////////////////////////////////////////////////////// *
 */
public class MainPageLoggedIn  extends ForumPageObject
{
    /**
     * Этот метод должен возвращать класс компонета для объекта страницы
     * Определяет к какому компоненту привязан этот объект страницы
     *
     * @return Class
     */
    @Override
    protected Class<? extends Component> componentClass()
    {
        return Forum.class;
    }

    /**
     * Єтот метод должен возвращать локатор готовности страницы
     *
     * @return By - locator
     */
    @Override
    protected By readyLocator()
    {
        return By.xpath("//div[@id='boardindex_table']");
    }

    private By _usernameText = By.xpath("//div[@class='user_block']//a[contains(@href,'?action=profile')]/span");

    final public String getUsername ()
    {
        return this.component().session().driver().findElement(_usernameText).getText();
    }

    final public BoardPage getBoardPage (String boardName)
    {
        By boardLink = By.xpath("//table[@class='table_list']//a[@class='subject' and contains(text(), '"+boardName+"')]");
        WebElement board = this.driver().findElement(boardLink);
        board.click();

        return new BoardPage();
    }
}
