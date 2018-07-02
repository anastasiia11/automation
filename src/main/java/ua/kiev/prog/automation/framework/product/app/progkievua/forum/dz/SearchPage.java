package ua.kiev.prog.automation.framework.product.app.progkievua.forum.dz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.kiev.prog.automation.framework.core.product.Component;
import ua.kiev.prog.automation.framework.core.product.component.object.widget.Button;
import ua.kiev.prog.automation.framework.core.product.component.object.widget.TextBox;
import ua.kiev.prog.automation.framework.product.app.progkievua.Forum;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.base.ForumPageObject;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends ForumPageObject {

    private TextBox _search = new TextBox(this.driver(), By.xpath("//*[@id=\"advanced_search\"]/div/span/input"));
    private Button _searchBtn = new Button(this.driver(), By.xpath("//*[@id=\"searchform\"]/fieldset[2]/div/div[3]/input[2]"));

    @Override
    protected Class<? extends Component> componentClass() {
        return Forum.class;
    }

    @Override
    protected By readyLocator() {
        return By.xpath("//div[@id = 'main_content_section']");
    }

    final public SearchPage search (String SearchText)
    {
        _search.setValue(SearchText);
       System.out.println(_search.getValue());
        _searchBtn.push();

        return new SearchPage();
    }
    public List<String> getSearchTopicList ()
    {
        List<String> result = new ArrayList<>();
        List<WebElement> list = this.driver().findElements(By.xpath("//div[@class='topic_details floatleft']//h5/a[2]"));
        for (WebElement elem: list) {
            result.add(elem.getText());
        }
        return result;
    }

}
