package ua.kiev.prog.automation.framework.product.app;

import ua.kiev.prog.automation.framework.core.Product;
import ua.kiev.prog.automation.framework.core.Test;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.test.LoginTest;

import java.util.List;

/**
 * ////////////////////////////////////////////////////////// *
 * Automation Framework                                       *
 * Automation Course for https://prog.kiev.ua/                *
 * ---------------------------------------------------------- *
 * Created by Yurii Voronenko                                 *
 * Email: yurii.voronenko@gmail.com                           *
 * ////////////////////////////////////////////////////////// *
 */
public class ProgKievUa extends Product
{
    final public Forum forum ()
    {
        return Component.getSingleton(Forum.class);
    }

    @Override
    public String name()
    {
        return "prog.kiev.ua";
    }

    @Override
    public void describeTests(List<Class<? extends Test>> tests)
    {
        tests.add(LoginTest.class);
    }
}
