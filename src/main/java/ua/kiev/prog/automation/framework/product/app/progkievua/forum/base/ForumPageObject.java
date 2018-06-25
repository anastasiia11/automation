package ua.kiev.prog.automation.framework.product.app.progkievua.forum.base;

import ua.kiev.prog.automation.framework.core.product.component.object.PageObject;
import ua.kiev.prog.automation.framework.product.app.progkievua.forum.blocks.TopLinksBlock;

abstract public class ForumPageObject extends PageObject
{
    public TopLinksBlock topLinks ()
    {
        return new TopLinksBlock();
    }
}
