package ua.kiev.prog.automation.framework.product.app.progkievua;

import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.MainPage;

import java.util.Map;

/**
 * ////////////////////////////////////////////////////////// *
 * Automation Framework                                       *
 * Automation Course for https://prog.kiev.ua/                *
 * ---------------------------------------------------------- *
 * Created by Yurii Voronenko                                 *
 * Email: yurii.voronenko@gmail.com                           *
 * ////////////////////////////////////////////////////////// *
 */
public class Forum extends Component
{
    @Override
    public String name()
    {
        return "Forum"; // Имя для отображения
    }

    /**
     * Этот метод возвращает объект главной страницы при переходе на компонент
     *
     * @return MainPage
     */
    final public MainPage mainPage()
    {
        return new MainPage();
    }
}
