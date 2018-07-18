package ua.kiev.prog.automation.framework.product.test;

import ua.kiev.prog.automation.framework.core.Test;
import ua.kiev.prog.automation.framework.core.TestResultType;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.WidgetObject;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.LoginPage;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.MainPage;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.MainPageLoggedIn;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.UsersPage;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.blocks.TopLinksBlock;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.dz.*;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.widgets.UserWidget;

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
public class LoginTest extends Test
{

    @Override
    public String name()
    {
        return "Login to forum test";
    }

    @Override
    public void data(List<Map<String, String>> testCases)
    {
        Map<String, String> testCase1 = new HashMap<>();
        testCase1.put("username", "Anastasia1234");
        testCase1.put("password", "sannacode");
        testCase1.put("result",   TestResultType.SUCCESS.toString());
        testCases.add(testCase1);

        Map<String, String> testCase2 = new HashMap<>();
        testCase2.put("username", "user2");
        testCase2.put("password", "pass2");
        testCase2.put("result",    TestResultType.FAILED.toString());
        testCases.add(testCase2);
    }

    @Override
    public void beforeTest()
    {
        // Вывод в консоль
        System.out.println("TEST: " + this.name() + " | PHASE: BEFORE RUN");
    }

    @Override
    public void test(Map<String, String> testCase) {
        String username = testCase.get("username");
        String password = testCase.get("password");
        String result   = testCase.get("result");

        // Вывод в консоль
        System.out.println("TEST: " + this.name() + " | PHASE: TEST");

        // Получаем главную страницу
        MainPage mainPage   = Component.getSingleton(Forum.class).mainPage();
        // Переходим на страницу логина
        LoginPage loginPage = mainPage.getLoginPage();
        // Заходим на форум
        MainPageLoggedIn dashboard = loginPage.login(username, password);
        dashboard.takeScreenshot();
        this.assertEquals(result, dashboard.getResult());

        if (dashboard.getResult() == TestResultType.SUCCESS) {
            // Подтверждаем что вход осуществлен
            //this.assertSuccess(dashboard, "Login");

            /** // Выводим в консоль имя пользователя на форуме
             System.out.println("Name: " + dashboard.getUsername());

             // TODO домашнее задание
             BoardPage board = dashboard.getBoardPage("QA Automation");
             TopicPage topic = board.getTopicPage("QA Automation Tetris 14 02 2018");
             List<String> authors = topic.getAuthors();
             for (String author : authors) {
             System.out.println("Author: " + author);
             }
             System.out.println();
             */
            TopLinksBlock menu = dashboard.topLinks();
/**
 //Переход на экран Начало
 menu.getSource();

 //Переход на экран Помощь. Вывод 1-го параграфа текста помощи в консоль
 HelpPage goToHelp = menu.getHelp();
 goToHelp.getHelpText();
 */
            //Переход на экран Поиска. Поиск по ключевым словам
            SearchPage goToSearch = menu.getSearch();
            goToSearch.search("Automation");
            List<String> searchTopics = goToSearch.getSearchTopicList();
            for (String searchTopic : searchTopics) {
                System.out.println("Automation Topic: " +  searchTopic);
            }
            System.out.println();
/**        //Переход на экран Профиль. Отображение статистики Профиля
 ProfilePage goToProfile = menu.getProfile();
 goToProfile.showStatistic();

 //Переход на экран Личные сообщения. Вывод личных сообщений в консоль
 MessagesPage goToMessages = menu.getMessages();
 goToMessages.getMessagesText();

 //Переход на страницу Пользователи. Вывод списка пользовталей на 1 стрн. в консоль
 UsersPage goToUsers = menu.getUsers();
 //System.out.println(goToUsers.findUser("AIR").getRegistrationDate());

 UserWidget user1 = goToUsers.findUser("AIR");
 System.out.println("AIR registration date: " + user1.getRegistrationDate());

 List<String> userlist = topic.topLinks().getUsers().getUsersList();

 for (String user : userlist){
 System.out.println("User: "+ user);
 }

 //Клик по кнопке Выход
 menu.getLogout();
 */
            // Ждём 10 сек, с перехватом исключения на прерывание выполнения потока и игнорируем его
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) { /* Игнорируем */ }

        }

    }


    @Override
    public void afterTest()
    {
        // Вывод в консоль
        System.out.println("TEST: " + this.name() + " | PHASE: AFTER RUN");
    }
}
