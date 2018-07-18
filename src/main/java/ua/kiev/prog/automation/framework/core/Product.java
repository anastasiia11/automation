package ua.kiev.prog.automation.framework.core;

import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;

import java.util.ArrayList;
import java.util.HashMap;
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
abstract public class
Product
{
    /**
     * Список объектов тестов, для данного продукта
     */
    final private List<Test> _tests = new ArrayList<>();

    /**
     * Конструктор объекта
     */
    public Product ()
    {
        // Создаем временный список классов тестов
        List<Class<? extends Test>> testClasses = new ArrayList<>();
        // Передаём временный список дочернему объекту для заполнения
        this.describeTests(testClasses);
        // Проходим по каждому классу теста и создаём объект, помещаем его в список _tests
        for (Class<? extends Test> testClass: testClasses) {
            try {
                this._tests.add(testClass.newInstance());
            } catch (Exception e) {
                // Игнорируем исключения, у нас тип (<? extends Test>) определяет точность наследования
                // Т.е. исключение не должно возникнуть
            }
        }

        // Write to log
        ResultLog.getInstance().writeProduct(this);
    }

    /**
     * Этот метод запускает все тесты по продукту
     */
    final public void runTests ()
    {
        ResultLog log = ResultLog.getInstance();

        for (Test test: this._tests) {
            log.runTest(test);
            try {
                log.writeTestPhase("BEFORE");
                test.beforeTest();

                log.writeTestPhase("TEST");
                List<Map<String, String>> testCases = new ArrayList<>();
                test.data(testCases);
                int i = 0;
                for (Map<String, String> testCase : testCases) {
                    Component.resetAll();
                    i ++;
                    log.writeTestPhase("TEST CASE #" + i);
                    log.writeTestCaseData(testCase);
                    try {
                        test.test(testCase);
                    } catch (Exception e) {
                        this.handleException(e);
                    }

                }
            } catch (Exception e) {
                this.handleException(e);
            }
            try {
                log.writeTestPhase("AFTER");
                test.afterTest();
            } catch (Exception e) {
                this.handleException(e);
            }
            log.endTest(test);

        }

        // Close all components
        Component.closeAll();

        // Close result log
        log.close();
    }

    public void handleException (Exception e)
    {
        PageObject obj = PageObject.getLastObject();
        if (obj != null)
            obj.takeScreenshot();
        e.printStackTrace(System.out);
        ResultLog.getInstance().writeException(e);
    }

    /**
     * Этот метод должен возвращать имя продукта для дальнейшего вывода в логи или консоль (для отладки)
     *
     * @return String - Имя продукта
     */
    abstract public String name ();

    /**
     * Этот метод должен описывать список классов теста для этого продукта
     *
     * @param tests - Пустой список классов тестов, необходимо заполнить при реализации
     */
    abstract public void describeTests(List<Class<? extends Test>> tests);
}
