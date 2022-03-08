package markup;

import java.util.List;

public class Text extends AbstractMarkdownElement {
    private String innerStringValue;

    public Text(String innerValue) {
        super(null);
        this.innerStringValue = innerValue;
    }

    public Text(char innerValue) {
        super(null);
        this.innerStringValue = Character.toString(innerValue);
    }

    public Text(List<MarkdownElement> listInnerTexts) {
        super(null);
        StringBuilder tmp = new StringBuilder();
        for (MarkdownElement text : listInnerTexts) {
            text.toMarkdown(tmp);
        }
        innerStringValue = tmp.toString();
    }

    @Override
    public void toMarkdown(StringBuilder str) {
        str.append(innerStringValue);
    }

    @Override
    public void toHtml(StringBuilder str) {
        str.append(innerStringValue);
    }
}
