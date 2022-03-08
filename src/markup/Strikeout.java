package markup;

import java.util.List;

public class Strikeout extends AbstractTextDecoratedElement {

    public Strikeout(List<MarkdownElement> listInnerElems) {
        super(listInnerElems, "~", "~", "<s>", "</s>");
    }
}
