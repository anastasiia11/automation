package ua.kiev.prog.automation.framework.core;

import ua.kiev.prog.automation.framework.core.product.Component;

import java.util.ArrayList;
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
abstract public class Product
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
    }

    /**
     * Этот метод запускает все тесты по продукту
     */
    final public void runTests ()
    {
        for (Test test: this._tests) {
            Component.resetAll();
            try {
                test.beforeTest();
                // TODO cycling run with data
                test.test();
                test.afterTest();
            } catch (Exception e) {
                // TODO log exception
                e.printStackTrace(System.out);
            }
        }
        // Close all components
        Component.closeAll();
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
