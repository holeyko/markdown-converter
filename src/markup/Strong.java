package markup;

import java.util.List;

public class Strong extends AbstractTextDecoratedElement {

    public Strong(List<MarkdownElement> listInnerElems) {
        super(listInnerElems,"__", "__", "<strong>", "</strong>");
    }
}
