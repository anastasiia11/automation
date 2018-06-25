package ua.kiev.prog.automation.framework;

import ua.kiev.prog.automation.framework.product.app.ProgKievUa;

public class App
{
    static public void main(String[] args)
    {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
        /*ChromeDriver drv = null;
        try {
            drv = new ChromeDriver();
            drv.get("http://google.com/");
            drv.findElement(By.xpath("//input[@id='lst-ib']")).setValue("Test run");
            drv.findElement(By.xpath("//input[@id='lst-ib']")).setValue(Keys.ENTER);
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        if (drv != null)
            drv.quit();*/
        ProgKievUa product = new ProgKievUa();
        product.forum().setURL("https://prog.kiev.ua/forum");
        product.runTests();

    }
}
