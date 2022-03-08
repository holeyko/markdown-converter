package markup;

import java.util.List;

public class Emphasis extends AbstractTextDecoratedElement {

    public Emphasis(List<MarkdownElement> listInnerElems) {
        super(listInnerElems, "*", "*", "<em>", "</em>");
    }
}
