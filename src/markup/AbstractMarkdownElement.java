package markup;

import java.util.List;

public abstract class AbstractMarkdownElement implements MarkdownElement {
    private List<MarkdownElement> innerElems;

    public AbstractMarkdownElement(List<MarkdownElement> innerElems) {
        this.innerElems = innerElems;
    }

    public void toMarkdown(StringBuilder str) {
        for (MarkdownElement el : innerElems) {
            el.toMarkdown(str);
        }
    }

    public void toHtml(StringBuilder str) {
        for (MarkdownElement el : innerElems) {
            el.toHtml(str);
        }
    }

    public int getCountOfInnerElements() {
        if (innerElems != null) {
            return innerElems.size();
        }
        return 0;
    }
}
