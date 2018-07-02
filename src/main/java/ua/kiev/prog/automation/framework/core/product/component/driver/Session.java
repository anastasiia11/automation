package ua.kiev.prog.automation.framework.core.product.component.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/**
 * ////////////////////////////////////////////////////////// *
 * Automation Framework                                       *
 * Automation Course for https://prog.kiev.ua/                *
 * ---------------------------------------------------------- *
 * Created by Yurii Voronenko                                 *
 * Email: yurii.voronenko@gmail.com                           *
 * ////////////////////////////////////////////////////////// *
 */
public class Session
{
    /**
     * Это приватное свойство с объектом драйвера Selenium RemoteWebDriver
     */
    private RemoteWebDriver _driver;

    /**
     * Конструктор объекта
     */
    public Session ()
    {

    }

    /**
     * Этот метод возвращает Selenium RemoteWebDriver
     * Если драйвер еще не был создан, то создаем его
     *
     * @return RemoteWebDriver - драйвер сессии
     */
    final public RemoteWebDriver driver ()
    {
        if (this._driver == null) {
            //this._driver = new ChromeDriver();
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setBrowserName("chrome");
            try {
                this._driver = new RemoteWebDriver(new URL("http://localhost:4545/wd/hub"), cap);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return this._driver;
    }

    /**
     * Этот метод закрывает драйвер и сессию
     */
    final public void close ()
    {
        if (this._driver != null) {
            this._driver.quit();
        }
        this._driver = null;
    }
}
