import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ua.kiev.prog.automation.framework.App;
import ua.kiev.prog.automation.framework.core.product.component.driver.Session;

public class SessionTestNGTest extends Assert{
    private String _url = "https://www.google.com.ua/";

    @BeforeTest
    public void setup ()   {
        // Устанавливаем системную переменную для хром-драйвера, если она еще не установлена
              App.setUpChromeDriver();  }

      @Test(priority = 0)
      public void smokeTest() {
        // Создаем новую сессию
          Session session = new Session();
          // Загружаем URL
               session.driver().get(this._url);
               // Проверяем что загруженный URL соответствует ожидаемому
              assertEquals(this._url, session.driver().getCurrentUrl());
              // Закрываем сессию
                session.close();    }

                @Test(priority = 1)
                public void closeTest() {
                 // Создаем новую сессию
                    Session session = new Session();
                 // Загружаем URL
                     session.driver().get(this._url);
                // Закрываем сессию
                    session.close();
                 // Проверяем что загруженный URL не соответствует ожидаемому
                 // При вызове session.driver() драйвер откроется заново
                 assertNotEquals(this._url, session.driver().getCurrentUrl());
                // Закрываем сессию
                        session.close();
    }
}

