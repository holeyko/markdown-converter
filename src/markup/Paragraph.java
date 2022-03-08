package markup;

import java.util.List;

public class Paragraph extends AbstractTextDecoratedElement {

    public Paragraph(List<MarkdownElement> listInnerElems) {
        super(listInnerElems, "", "", "", "");
    }
}
