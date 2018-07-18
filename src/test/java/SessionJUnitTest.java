import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.kiev.prog.automation.framework.App;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.driver.Session;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.LoginPage;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.MainPage;

public class SessionJUnitTest extends Assert{
    private String _url = "https://www.google.com.ua/";

    @Before
    public void setup ()    {
        // Устанавливаем системную переменную для хром-драйвера, если она еще не установлена
          App.setUpChromeDriver();
    }

    @Test
    public void smokeTest() {
        // Создаем новую сессию
             Session session = new Session();
             // Загружаем URL
           session.driver().get(this._url);
           // Проверяем что загруженный URL соответствует ожидаемому
               assertEquals(this._url, session.driver().getCurrentUrl());
               // Закрываем сессию
               session.close();
    }

         @Test
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
    }}
