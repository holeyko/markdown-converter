package markup;

import java.util.List;

public class ParagraphPars extends AbstractTextDecoratedElement {

    public ParagraphPars(List<MarkdownElement> listInnerElems) {
        super(listInnerElems, "", "", "<p>", "</p>");
    }
}
