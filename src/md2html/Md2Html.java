package md2html;

import markup.*;

import java.beans.Encoder;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.spec.EncodedKeySpec;
import java.util.*;

public class Md2Html {
    public static void main(String[] args) {
        final String inputFileName = "input-files/" + args[0];
        final String outputFileName = "input-files/" + args[1];
        List<MarkdownElement> data = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(inputFileName),
                            StandardCharsets.UTF_8
                    )
            );
            try {
                ParagraphHandler pHandler = new ParagraphHandler();
                while (in.ready()) {
                    String line = in.readLine();
                    if (line.isEmpty()) {
                        MarkdownElement addElem = pHandler.handleParagraph();
                        if (addElem.getCountOfInnerElements() != 0) {
                            data.add(addElem);
                        }
                        pHandler.clear();
                    } else {
                        pHandler.add(line);
                    }
                }
                MarkdownElement addElem = pHandler.handleParagraph();
                if (addElem.getCountOfInnerElements() != 0) {
                    data.add(addElem);
                }
            } catch (IOException e) {
                System.err.println("Something went wrong " + e.getMessage());
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Something went wrong " + e.getMessage());
        }
        try {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(outputFileName),
                            StandardCharsets.UTF_8
                    )
            );
            try {
                StringBuilder str = new StringBuilder();
                for (MarkdownElement el : data) {
                    el.toHtml(str);
                    str.append(System.lineSeparator());
                }
                out.write(str.toString());
            } catch (IOException e) {
                System.err.println("Something went wrong " + e.getMessage());
            } finally {
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Something went wrong " + e.getMessage());
        }
    }
}
