package markup;

import java.util.List;

public class Code extends AbstractTextDecoratedElement {

    public Code(List<MarkdownElement> listInnerElems) {
        super(listInnerElems, "`", "`", "<code>", "</code>");
    }
}
