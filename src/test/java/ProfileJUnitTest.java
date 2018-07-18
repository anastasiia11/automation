import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;
import ua.kiev.prog.automation.framework.App;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.driver.Session;
import ua.kiev.prog.automation.framework.product.app.ProgKievUa;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.LoginPage;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.MainPage;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.MainPageLoggedIn;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.blocks.TopLinksBlock;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.dz.ProfilePage;


public class ProfileJUnitTest extends Assert {
   // private String _url = "https://www.google.com.ua/";

    @Before
    public void setup ()    {
        // Устанавливаем системную переменную для хром-драйвера, если она еще не установлена
        App.setUpChromeDriver();
        ProgKievUa product = new ProgKievUa();
        product.forum().setURL("https://prog.kiev.ua/forum");
    }


    @Test
    public void profileTest() {

        MainPage mainPage   = Component.getSingleton(Forum.class).mainPage();
        LoginPage loginPage = mainPage.getLoginPage();
        // Заходим на форум
        MainPageLoggedIn dashboard = loginPage.login("Anastasia1234", "sannacode");
        // Подтверждаем что вход осуществлен
        //this.assertSuccess(dashboard, "Login");
        //assertEquals(this._url, session.driver().getCurrentUrl());
        TopLinksBlock menu = dashboard.topLinks();
        ProfilePage goToProfile = menu.getProfile();
        String prName = goToProfile.getProfileName();
        System.out.println(prName);
        assertEquals(prName, "Anastasia1234");
        System.out.println("Profile name = User name");


    }

    @After
            public void closeTest() {
        Component.closeAll();
    }
}
