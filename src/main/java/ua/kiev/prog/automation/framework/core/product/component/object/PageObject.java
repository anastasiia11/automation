package ua.kiev.prog.automation.framework.core.product.component.object;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ua.kiev.prog.automation.framework.core.ResultLog;
import ua.kiev.prog.automation.framework.core.TestResultType;
import ua.kiev.prog.automation.framework.core.product.Component;

import java.io.File;
import java.io.IOException;
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
abstract public class PageObject<T extends PageObject>
{
    static private PageObject _lastObject;

    static public PageObject getLastObject ()
    {
        return _lastObject;
    }

    /**
     * Компонент объекта страницы
     */
    private Component _component;

/*    protected PageObject ()
    {
        this(true);
    }*/

    /**
     * Конструктор объекта
     */
    protected PageObject ()
    {
        // Считываем результат метода componentClass() (абстрактный, определён в наследнике) в переменную
        Class<? extends Component> componentClass = this.componentClass();
        // Если метод вернул null, то бросаем исключение с описанием ошибки
        if (componentClass == null)
            throw new RuntimeException("Method componentClass returns null. Class: " + this.getClass().getName());

        // Получаем объект-одиночку(Singleton) из списка, в статических свойствах класса Component по имени класса
        this._component = Component.getSingleton(componentClass);

/*        // Денлаем искуственную задержку на 1 сек для наглядности
        try {
            Thread.sleep(1000);
        } catch (Exception e) { *//* Ignore *//* }*/
        // Ждем подтверждения загрузки страницы
        /*if (checkReadyLocator)
           */
        // Пишем в лог статус страницы
        this.waitReadyLocator();
        ResultLog.getInstance().writePageObject(this);
        _lastObject = this;
    }

    /**
     * Этот метод возвращает компонент объекта страницы
     *
     * @return Component - page component
     */
    public Component component()
    {
        return this._component;
    }

    /**
     * Этот метод должен возвращать класс компонета для объекта страницы
     * Определяет к какому компоненту привязан этот объект страницы
     *
     * @return Class
     */
    abstract protected Class<? extends Component> componentClass();

    /**
     * Єтот метод должен возвращать локатор готовности страницы
     *
     * @return By - locator
     */
    abstract protected By readyLocator ();

    /**
     * Этот метод ждет, пока єлемент (по локатору готовности) не будет виден
     */
    private void waitReadyLocator ()
    {
        try {
            // Для читаемости, перенесем данные в переменные
            RemoteWebDriver driver  = this.component().session().driver();  // Получаем драйвер сессии компонента
            By readyLocator         = this.readyLocator();                  // Получаем локатор готовности объекта страницы
            // Создаем объект, который будет работать через наш драйвер с таймаутом 30 сек
            WebDriverWait wait      = new WebDriverWait(driver, 30);
            // Запускаем ожидание
            wait.until(ExpectedConditions.visibilityOfElementLocated(readyLocator));
        } catch (Exception e) { /* Ignore */ }
    }

    final protected void assertLoaded ()
    {
        this.waitReadyLocator();
        if (!this.success())
            throw new RuntimeException("Page is not loaded");
    }

    final protected void logStep (String name)
    {
        ResultLog.getInstance().writeStep(name);
    }

    /**
     * Этот метод возвращает статус удачной загрузки страницы
     *
     * @return boolean - true если страница корректна и подтверждена локатором готовности
     */
    final public boolean success ()
    {
        boolean result;
        try {
            result = this.component().session().driver().findElement(this.readyLocator()).isDisplayed();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    final public TestResultType getResult ()
    {
        return this.success() ? TestResultType.SUCCESS : TestResultType.FAILED;
    }

    final public void takeScreenshot ()
    {
        File scrFile = this.component().session().driver().getScreenshotAs(OutputType.FILE);
        try {
            String filename = "./screenshot_"+ Math.random() + ".png";
            FileUtils.copyFile(scrFile, new File(filename));
            ResultLog.getInstance().writeScreenshot(filename);
        } catch (IOException e) {
            ResultLog.getInstance().writeException(e);
        }
    }
    final public RemoteWebDriver driver()
    {
        return this.component().session().driver();
    }
}
