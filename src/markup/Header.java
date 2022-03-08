package markup;

import java.util.List;

public class Header extends AbstractTextDecoratedElement{

    public Header(List<MarkdownElement> listInnerElems, int levelHead) {
        super(listInnerElems, "#".repeat(levelHead), "", String.format("<h%s>", levelHead), String.format("</h%s>", levelHead));
    }
}
