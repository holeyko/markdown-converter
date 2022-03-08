package markup;

import java.util.List;

public class Quote extends AbstractTextDecoratedElement {

    public Quote(List<MarkdownElement> listInnerElems) {
        super(listInnerElems, "''", "''", "<q>", "</q>");
    }
}
