package ua.kiev.prog.automation.framework.core.product;

import ua.kiev.prog.automation.framework.core.ResultLog;
import ua.kiev.prog.automation.framework.core.product.component.driver.Session;

import java.util.HashMap;
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
abstract public class Component
{
    //////////////////////////////////////////////////////////////////////////////////////
    // ------------------------------------ Static ------------------------------------ //
    //////////////////////////////////////////////////////////////////////////////////////
    // В этом блоке описано кеширование объектов(наследников Component)                 //
    // и возвращение одиночных экземпляров для данного потока                           //
    //////////////////////////////////////////////////////////////////////////////////////
    /**
     * Объект Map с данными: КЛЮЧ => ЗНАЧЕНИЕ
     * Хранит в себе список "имён" классов(наследников этого класса) и их объектов
     * Представление:
     * Класс(Наследник от Component) => Объект (Component)
     * Тут, мы объекты наследников определяем как родительский объект - тип Component (полиморфизм)
     */
    final static private Map<Class<? extends Component>, Component> _components = new HashMap<>();

    /**
     * Этот метод возвращает объект по классу
     * Если объект еще не был создан, то метод создаст его
     *
     * @param componentClass - Класс для поиска и кеширования
     * @param <T> - Возвращаемый тип определяется типом в аргументе, т.е. описывает,
     *           что тип(Класс) T наследуется от єтого класса(Component)
     *           Оббертка Class<T> говорит о том, что на вход(в аргумент) будет подана системная оббертка єтого класса
     *           Такую оббертку можно получить, например, для Component через системное свойство Component.class
     * @return T - Возвращает объект
     */
    static public <T extends Component> T getSingleton(Class<T> componentClass)
    {
        // Если класс componentClass еще не создан в списке _components, то создаём его
        if (!_components.containsKey(componentClass)) {
            try {
                // Создаём объект по классу
                T object = componentClass.newInstance();
                // Добавляем в список для кеширования
                _components.put(componentClass, object);
            } catch (Exception e) {
                /*
                 * Игнорируем любые исключения, т.к. мы определили типом аргумента,
                 * что любой класс на входе будет наследоватся от этого класса и использовать этот конструктор
                 */
            }
        }
        // Указываем, через инструкцию (T) - (Называется casting), что объект будет приведён к типу T
        // Вызов метода  _components.get(componentClass) - На выходе вернёт тип Component, но мы то знаем
        // что создавали именно объект T(подкласс Component), т.к. на входе получили этот тип через дженерик T
        T component = (T)_components.get(componentClass);
        ResultLog.getInstance().switchComponent(component);
        return component;
        // Дополнительную инфу можете поискать по ключевым словам:
        // Java generic (Java дженерики)
        // Java generic methods (Java универсальные методы)
        // Java casting (Java приведение типов)
    }

    /**
     * Этот метод вызывает метод reset, для всех компонентов
     */
    static public void resetAll ()
    {

        // Дословно:
        // Для каждой пары(Map.Entry) с типом в <> (класс, объект), в переменной "с" из списка пар в _components
        for (Map.Entry<Class<? extends Component>, Component> c: _components.entrySet()) {
            // Получаем значение(объект) и вызываем метод reset
            c.getValue().reset();
            // Дополнительно: в паре ключ => значение (key => value)
            // c.getKey - возвращает ключ, в нашем случае с типом Class<? extends Component>
            // c.getValue - возвращает значение, в нашем случае с типом Component и мы с ним работаем как с интерфейсом,
            // вызываем метод reset(), который доступен во всех подклассах
        }
    }

    /**
     * Этот метод вызывает метод close, для всех компонентов
     */
    static public void closeAll ()
    {
        // Описание аналогично методу resetAll ()
        for (Map.Entry<Class<? extends Component>, Component> c: _components.entrySet()) {
            c.getValue().close();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////// Object //////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /**
     * Объект сессии(обертка Selenium RemoteWebDriver) для компонента
     */
    final private Session   _session = new Session();
    /**
     * Строковое значение URL для компонента
     */
    private String          _url;

    /**
     * Пустой конструктор объекта
     */
    protected Component ()
    {

    }

    /**
     * Этот метод устанавливает свойство объекта _url из аргумента url
     * и делает перезагрузку страницы
     * @param url - Строковое значение URL
     */
    final public void setURL (String url)
    {
        this._url = url;
        ResultLog.getInstance().setComponentURL(this, url);
        this.reset();
    }

    final public String getURL ()
    {
        return this._url;
    }

    /**
     * Этот метод возвращает объект сессии из свойства _session
     *
     * @return Session - объект сессии
     */
    final public Session session()
    {
        return this._session;
    }

    /**
     * Этот метод загружает страницу _url через Selenium RemoteWebDriver если свойство _url определено (!= null)
     */
    final public void reset ()
    {
        this._session.close();
        if (this._url != null)
            this._session.driver().get(this._url);
    }

    /**
     * Этот метод закрывает сессию и обнуляет _url
     */
    private void close ()
    {
        this._session.close();
        this._url = null;
    }

    /**
     * Этот метод должен возвращать имя компонента для дальнейшего вывода в логи или консоль (для отладки)
     *
     * @return String - Имя компонента
     */
    abstract public String name();
}
