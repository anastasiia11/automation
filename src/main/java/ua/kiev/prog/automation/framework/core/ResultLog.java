package ua.kiev.prog.automation.framework.core;

import org.apache.commons.lang3.exception.ExceptionUtils;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
// Объект-одиночка (Singleton)
public class ResultLog
{
    // Приватный конструктор
    private ResultLog () {}

    // Приватное статическое свойство для объекта-одиночки
    static private ResultLog _instance;

    // Метод-фабрика для создания и возврата объекта-одиночки
    static public ResultLog getInstance ()
    {
        if (_instance == null)
            _instance = new ResultLog();
        return _instance;
    }

    // Переключатель для режима отладки
    private boolean     _debugLog = true;

    // HTML лог
    private PrintWriter _writer;
    // TXT лог
    private PrintWriter _writerTxt;
    // Идентификатор инициализации логов
    private boolean     _isInitialized = false;

    /**
     * Метод инициализации и создания файлов логов
     * @param product
     */
    private void create (Product product)
    {
        // Если еще не инициализированы логи, то
        if (!this._isInitialized) {
            // Пытаемся создать файлы логов,
            // в случае возникновения ошибки выводим их в консоль
            try {
                this._writer        = new PrintWriter( product.name().toUpperCase() + " Result Log.html", "UTF-8");
                this._writerTxt     = new PrintWriter( product.name().toUpperCase() + " Result Log.txt", "UTF-8");
                this._isInitialized = true;
            } catch (Exception e) {
                // Print exception stack trace to STDOUT
                e.printStackTrace(System.out);
            }
            // Записываем TXT Header
            this.writeTXT("Test result log for " + product.name().toUpperCase());
            // Записываем HTML Header
            this.writeHTML("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>" + product.name().toUpperCase() + " :: Result Log</title>\n" +
                    "    <meta charset=\"utf-8\">\n" +
                    "<style>\n" +
                    // CSS //
                    "body{margin:0;background:#000;color:#fff;font-size:12px;font-family:Consolas,Tahoma,Arial,Verdana;}" +
                    "a{color:#FF8800}" +
                    "table.result{background:#222;border-top:#555 1px solid;}" +
                    "table.result th,table.result td{padding:3px 5px;border-left:#555 1px solid;border-bottom:#555 1px dotted}" +
                    "table.result th{padding:7px 5px;background:#111;}" +
                    ".debug{color:#777}" +

                    ".test.step td{background:#203B57}" +
                    ".test.test_info td{background:#480075}" +
                    ".test.success td{background:#197300}" +
                    ".test.failed td,.exception td{background:#730000}" +
                    ".comp,.actn{text-transform:uppercase;}" +
                    ".date,.comp,.actn{vertical-align:top}" +
                    ".date{color:#777}" +
                    // --- //
                    "</style>\n" +
                    "</head>\n" +
                    "<body>");
            // Заголовок
            this.writeHTML("<h1>Test result log for " + product.name().toUpperCase() + "</h1>");
            // Таблица
            this.writeHTML("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" class=\"result\">");
            // Название полей таблицы
            this.writeHTML(
                    "<tr>" +
                            "<th width=\"150\">Time</td>" +
                            "<th width=\"200\">Component</td>" +
                            "<th width=\"200\">Action</td>" +
                            "<th>Message</td>" +
                            "</tr>");
            // DEBUG запись, что продукт создан
            this._debugWrite("product", "create", product.name().toUpperCase());
        }
    }

    /**
     * Метод который записывает строку в HTML файл и возвращает статус записи
     *
     * @param data - строка для записи
     * @return - Статус записи (инициализации), TRUE если записал, FALSE если нет
     */
    private boolean writeHTML(String data)
    {
        if (this._isInitialized) {
            this._writer.println(data);
            this._writer.flush();
        }

        return this._isInitialized;
    }

    /**
     * Метод который записывает строку в TXT файл и возвращает статус записи
     *
     * @param data - строка для записи
     * @return - Статус записи (инициализации), TRUE если записал, FALSE если нет
     */
    private boolean writeTXT (String data)
    {
        if (this._isInitialized) {
            this._writerTxt.println(data);
            this._writerTxt.flush();
        }

        return this._isInitialized;
    }

    /**
     * Возвращает строку с текущей датой и временем в формате [dd.MM.yyyy] HH:mm:ss
     *
     * @return Строка даты и времени
     */
    private String _getDateTime()
    {
        // Создаем шаблон для формата [dd.MM.yyyy] HH:mm:ss
        SimpleDateFormat dateFormat = new SimpleDateFormat("[dd.MM.yyyy] HH:mm:ss");
        // Возвращаем отформатированную текущую дату
        return dateFormat.format( new Date() );
    }

    /**
     * Этот метод записывает действие в режиме DEBUG, если флаг записи DEBUG сообщений установлен в TRUE
     *
     * @param component - Компонент для логирования
     * @param action - Событие
     * @param message - Сообщение
     * @return Возвращает значение, что запись произошла
     */
    private boolean _debugWrite(String component, String action, String message)
    {
        // Если режим DEBUG включен, то пишем в логи
        if (_debugLog) {
            // TXT
            writeTXT(_getDateTime() + ": DEBUG | " + component + " " + action + " " + message);
            // HTML
            return this.writeHTML(
                    "<tr class=\"debug\">" +
                            "<td class=\"date\">"+ _getDateTime()+"</td>" +
                            "<td class=\"comp\">"+component+"</td>" +
                            "<td class=\"actn\">"+action+"</td>" +
                            "<td class=\"mess\">"+this.messageToHTML(message)+"</td>" +
                            "</tr>");

        }
        // Возвращем FALSE если не попали в блок записи
        return false;
    }

    /**
     * Этот метод записывает в логи информацию по тесту
     *
     * @param component - Компонент для логирования
     * @param action - Событие
     * @param message - Сообщение
     * @param result - Результат теста
     * @return Возвращает значение, что запись произошла
     */
    private boolean _testWrite(String component, String action, String message, TestResultType result)
    {
        // TXT
        writeTXT(_getDateTime() + ": " + result.toString() + " | " + component + " " + action + " " + message);
        // HTML
        return this.writeHTML(
                "<tr class=\"test "+result.toString().toLowerCase()+"\">" +
                        "<td class=\"date\">"+ _getDateTime()+"</td>" +
                        "<td class=\"comp\">"+component+"</td>" +
                        "<td class=\"actn\">"+action+"</td>" +
                        "<td class=\"mess\">"+this.messageToHTML(message)+"</td>" +
                        "</tr>");
    }

    /**
     * Этот метод преобразовывет сообщение в HTML формат,
     * Переносы строк на тэг </br>
     * Ссылки на HTML ссылки
     *
     * @param message текстовое сообщение
     * @return преобразованное сообщение
     */
    private String messageToHTML (String message)
    {
        // Переносы
        message = message.replaceAll("(\r*?\n)","<br/>");
        // Ссылки
        message = message.replaceAll("(http[s]*?://.+?)\\s", "<a href=\"\1\">\1</a>");

        return message;
    }

    /**
     * Прокси-метод, для читаемости, вызывает метод create
     *
     * @param product
     */
    public void writeProduct (Product product)
    {
        this.create(product);
    }

    /**
     * Этот метод логирует смену URL для компонента
     *
     * @param component
     * @param url
     */
    public void setComponentURL (Component component, String url)
    {
        this._debugWrite("component", "set url", component.name() + " = " + url);
    }

    // Приватное свойство, которое хранит ссылку на последний объект компонента
    // Это нужно для того, что бы не записывать смену компонента в лог при каждом действии.
    // Запись будет сделана, только тогда, когда в методе switchComponent на вход придёт компонент отлиный от того,
    // который в этой переменной
    private Component _lastComponent = null;

    /**
     * Метод логирует переключение компонента,
     * если компонент отличается от того, который в _lastComponent
     *
     * @param component компонент на котрый переключаемся
     */
    public void switchComponent (Component component)
    {
        // Проверяем компонент на соответствие условиям
        if (component.getURL() != null && (_lastComponent == null || !_lastComponent.equals(component))) {
            // Пытаемся записать и если все ок, то устанавливаем _lastComponent
            if (this._debugWrite("component", "switch", component.name() + " - URL: " + component.getURL())) {
                _lastComponent = component;
            }
        }
    }

    /**
     * Метод логирует запуск теста
     *
     * @param test
     */
    public void runTest(Test test)
    {
        this._testWrite("test", "run", test.name(), TestResultType.TEST_INFO);
    }

    /**
     * Метод логирует окончание теста
     *
     * @param test
     */
    public void endTest(Test test)
    {
        this._testWrite("test", "end", test.name(), TestResultType.TEST_INFO);
    }

    /**
     * Метод логирует фазу теста
     *
     * @param phaseName
     */
    public void writeTestPhase (String phaseName)
    {
        this._debugWrite("phase", "run", phaseName);
    }

    /**
     * Метод логирует передачу управления объекту страници
     *
     * @param object
     */
    public void writePageObject (PageObject object)
    {
        this._debugWrite("page object", "manage", object.getClass().getSimpleName() + " | status - " + (object.success() ? "SUCCESS" : "FAILED") );
    }

    public void writeAssert (String name, TestResultType result)
    {
        this.writeAssert("assert", name, result);
    }

    /**
     * Метод записывает результат проверки
     *
     * @param assertName Название операции
     * @param name Название проверки
     * @param result Результат проверки
     */
    public void writeAssert (String assertName, String name, TestResultType result)
    {
        this._testWrite(assertName, "check", name, result);
    }

    /**
     * Записывает информацию о шагах теста
     *
     * @param name Название шага
     */
    public void writeStep (String name)
    {
        this._testWrite("step", "info", name, TestResultType.STEP);
    }

    /**
     * Записывает отладочную информацию по виджетам
     *
     * @param widgetName Имя виджета
     * @param action Действие виджета
     * @param message Сообщение
     */
    public void writeWidget (String widgetName, String action, String message)
    {
        this._debugWrite("widget " + widgetName, action, message);
    }

    /**
     * Записывает информацию о скриншоте
     *
     * @param filename
     */
    public void writeScreenshot (String filename)
    {
        this._debugWrite("screenshot", "", filename);
    }

    /**
     * Записывает исключение
     * @param e Исключение
     */
    public void writeException (Exception e)
    {
        String stackTrace = ExceptionUtils.getStackTrace(e);
        this.writeHTML("<tr class=\"exception\">" +
                "<td class=\"date\">"+ _getDateTime()+"</td>" +
                "<td class=\"comp\">EXCEPTION</td>" +
                "<td colspan=\"2\" style=\"white-space:pre-wrap;\">"+stackTrace+"</td>" +
                "</tr>");
    }

    /**
     * Записывает данные по текущему кейсу
     *
     * @param testCase
     */
    public void writeTestCaseData (Map<String, String> testCase)
    {
        String dataMessage = "";
        for (Map.Entry<String, String> pair: testCase.entrySet()) {
            dataMessage += pair.getKey() + " = " + pair.getValue() + "\r\n";
        }
        this._testWrite("test case", "data", dataMessage, TestResultType.TEST_INFO);
    }

    /**
     * Закрывает все логи и обнуляет переменные
     */
    public void close ()
    {
        if (this._isInitialized) {
            // Print footer
            this.writeHTML("</table></body></html>");
            this._writer.close();
            this._writerTxt.close();
            this._writer        = null;
            this._writerTxt     = null;
            this._isInitialized = false;
        }
    }
}
