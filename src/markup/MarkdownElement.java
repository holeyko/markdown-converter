package markup;

public interface MarkdownElement {
    void toMarkdown(StringBuilder str);

    void toHtml(StringBuilder str);

    int getCountOfInnerElements();
}
