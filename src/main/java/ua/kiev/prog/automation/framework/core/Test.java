package ua.kiev.prog.automation.framework.core;

import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;

/**
 * ////////////////////////////////////////////////////////// *
 * Automation Framework                                       *
 * Automation Course for https://prog.kiev.ua/                *
 * ---------------------------------------------------------- *
 * Created by Yurii Voronenko                                 *
 * Email: yurii.voronenko@gmail.com                           *
 * ////////////////////////////////////////////////////////// *
 */
abstract public class Test
{
    /**
     * Этот метод подтверждает что объект страницы корректный
     *
     * @param obj - объект страницы
     * @param name - имя проверки
     */
    final protected void assertSuccess (PageObject obj, String name)
    {
        boolean result = obj.success();
        String message = name + " " + (result ? "SUCCESS" : "FAILED");
        System.out.println(message);
        if(!result)
            throw new RuntimeException(message);
    }

    /**
     * Этот метод должен возвращать имя теста для дальнейшего вывода в логи или консоль (для отладки)
     *
     * @return String - Имя теста
     */
    abstract public String name ();

    /**
     * Этот метод будет вызван до теста
     */
    abstract public void beforeTest ();

    /**
     * Это метод сценария
     */
    abstract public void test ();

    /**
     * Этот метод будет вызван после теста
     */
    abstract public void afterTest ();
}
