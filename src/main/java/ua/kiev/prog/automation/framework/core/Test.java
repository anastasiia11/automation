package ua.kiev.prog.automation.framework.core;

import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;

import java.util.List;
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
        //boolean result = obj.success();
        TestResultType result = obj.success() ? TestResultType.SUCCESS : TestResultType.FAILED;
        String message = name + " " + result;

        ResultLog.getInstance().writeAssert("assert success", name, result);
        System.out.println(message);

        if(result == TestResultType.FAILED)
            throw new RuntimeException(message);
    }


    final protected void assertEquals (Object expected, Object actual)
    {
        if (expected.getClass() == String.class)
            actual = actual.toString();
        //boolean result = obj.success();
        boolean cond = expected.equals(actual);
        TestResultType result = cond ? TestResultType.SUCCESS : TestResultType.FAILED;
        String message = "Expected value '" + expected + "' actual value '" + actual + "'";

        ResultLog.getInstance().writeAssert("assert equals", message, result);
        System.out.println(message);
        if(result == TestResultType.FAILED)
            throw new RuntimeException(message);
    }

    /**
     * Этот метод должен возвращать имя теста для дальнейшего вывода в логи или консоль (для отладки)
     *
     * @return String - Имя теста
     */
    abstract public String name ();

    /**
     * Это метод должен описывать данные для теста
     */
    abstract public void data (List<Map<String, String>> testCases);

    /**
     * Этот метод будет вызван до теста
     */
    abstract public void beforeTest ();

    /**
     * Это метод сценария
     */
    abstract public void test (Map<String, String> testCase);

    /**
     * Этот метод будет вызван после теста
     */
    abstract public void afterTest ();
}
