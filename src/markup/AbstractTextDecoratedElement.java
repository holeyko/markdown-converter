package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTextDecoratedElement extends AbstractMarkdownElement {
    private String beginMarkdown;
    private String endMarkdown;
    private String beginHtml;
    private String endHtml;

    public AbstractTextDecoratedElement(
            List<MarkdownElement> listInnerElems,
            String beginMarkdown,
            String endMarkdown,
            String beginHtml,
            String endHtml
    ) {
        super(listInnerElems);
        this.beginMarkdown = beginMarkdown;
        this.endMarkdown = endMarkdown;
        this.beginHtml = beginHtml;
        this.endHtml = endHtml;
    }

    @Override
    public void toMarkdown(StringBuilder str) {
        str.append(beginMarkdown);
        super.toMarkdown(str);
        str.append(endMarkdown);
    }

    @Override
    public void toHtml(StringBuilder str) {
        str.append(beginHtml);
        super.toHtml(str);
        str.append(endHtml);
    }
}
