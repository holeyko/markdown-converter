package md2html;

import markup.*;

import java.util.*;

public class ParagraphHandler {
    private class stackOfMarkdownElems {
        private MarkdownElement[] stack = new MarkdownElement[10];
        private int head = -1;

        public void add(MarkdownElement el) {
            head++;
            if (head == stack.length) {
                stack = Arrays.copyOf(stack, stack.length * 2);
            }
            stack[head] = el;
        }

        public MarkdownElement pop() {
            head--;
            return stack[head + 1];
        }

        public List<MarkdownElement> pop(int startPos) {
            List<MarkdownElement> ret = new ArrayList<>();
            for (int i = startPos; i <= head; i++) {
                ret.add(stack[i]);
            }
            head = startPos - 1;
            return ret;
        }

        public int getHead() {
            return head;
        }
    }

    private stackOfMarkdownElems stackElems = new stackOfMarkdownElems();
    private Map<String, Integer> decorsPositions = new HashMap<>();
    private int levelHead = 0;
    private boolean isFirstLine = true;
    private boolean isSkip = false;
    private final int MAX_LEN_DECOR = 2;
    private final String[][] decors = new String[][]{new String[]{"*", "_", "`"}, new String[]{"**", "__", "--", "''"}};
    private final char[] beginsOfDecors = new char[]{'*', '_', '`', '-', '\''};

    public void add(String line) {
        if (!isFirstLine) {
            stackElems.add(new Text(System.lineSeparator()));
        }
        handlingLine(line);
    }

    public MarkdownElement handleParagraph() {
        if (levelHead > 0) {
            return new Header(stackElems.pop(0), levelHead);
        }
        return new ParagraphPars(stackElems.pop(0));
    }

    private int checkLevelHead(String line) {
        int start = 0;
        while (start < line.length() && line.charAt(start) == '#') {
            levelHead++;
            start++;
        }
        if (start == line.length() || levelHead == 0 || line.charAt(start) != ' ') {
            levelHead = 0;
            return 0;
        } else {
            return start + 1;
        }
    }

    private void handlingLine(String line) {
        int linePos = 0;
        if (isFirstLine) {
            linePos = checkLevelHead(line);
            isFirstLine = false;
        }
        StringBuilder simpleText = new StringBuilder();
        for (; linePos < line.length(); linePos++) {
            char ch = line.charAt(linePos);
            if (isBeginOfDecor(ch)) {
                if (isSkip && isBeginOfDecor(ch)) {
                    stackElems.add(new Text(simpleText.substring(0, simpleText.length() - 1) + ch));
                    simpleText = new StringBuilder();
                    isSkip = false;
                    continue;
                }
                isSkip = false;

                String curDecor = whatKindOfDecors(line, linePos);
                if (curDecor.equals("")) {
                    simpleText.append(ch);
                    continue;
                }

                if (simpleText.length() > 0) {
                    stackElems.add(new Text(simpleText.toString()));
                    simpleText = new StringBuilder();
                }
                linePos += curDecor.length() - 1;

                int beginDecorPos = decorsPositions.getOrDefault(curDecor, -1);
                if (beginDecorPos == -1) {
                    stackElems.add(new Text(curDecor));
                    beginDecorPos = stackElems.getHead();
                    decorsPositions.put(curDecor, beginDecorPos);
                } else {
                    List<MarkdownElement> elemsCurDecor = stackElems.pop(beginDecorPos + 1);
                    stackElems.pop();
                    decorsPositions.put(curDecor, -1);
                    switch (curDecor) {
                        case "**", "__" -> stackElems.add(new Strong(elemsCurDecor));
                        case "*", "_" -> stackElems.add(new Emphasis(elemsCurDecor));
                        case "`" -> stackElems.add(new Code(elemsCurDecor));
                        case "--" -> stackElems.add(new Strikeout(elemsCurDecor));
                        case "''" -> stackElems.add(new Quote(elemsCurDecor));
                    }
                }
            } else {
                switch (ch) {
                    case '<' -> simpleText.append("&lt;");
                    case '>' -> simpleText.append("&gt;");
                    case '&' -> simpleText.append("&amp;");
                    default -> simpleText.append(ch);
                }
                if (ch == '\\') {
                    isSkip = true;
                }
            }
        }
        if (simpleText.length() > 0) {
            stackElems.add(new Text(simpleText.toString()));
        }
    }

    private String whatKindOfDecors(String line, int indexStart) {
        int lenDecor = 0;
        String validDecor = "";
        StringBuilder curDecor = new StringBuilder();
        while (lenDecor < MAX_LEN_DECOR && indexStart + lenDecor < line.length()) {
            curDecor.append(line.charAt(indexStart + lenDecor));
            if (stringArrayContains(decors[lenDecor], curDecor.toString())) {
                validDecor = curDecor.toString();
            } else if (!validDecor.isEmpty()) {
                break;
            }
            lenDecor++;
        }
        return validDecor;
    }

    private boolean stringArrayContains(String[] array, String str) {
        for (String el : array) {
            if (str.equals(el)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBeginOfDecor(char ch) {
        for (char el : beginsOfDecors) {
            if (ch == el) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        decorsPositions = new HashMap<>();
        levelHead = 0;
        isFirstLine = true;
        isSkip = false;
    }
}
