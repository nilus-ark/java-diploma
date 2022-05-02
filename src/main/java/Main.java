import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Canvas;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        LinksSuggester linksSuggester = new LinksSuggester(new File("data/config"));
        var dir = new File("data/pdfs");

        for (var fileIn : dir.listFiles()) {
            List<Suggest> suggestConfiguration = linksSuggester.suggestConfiguration();

            var fileOut = new File("data/converted/" + fileIn.getName());
            var doc = new PdfDocument(new PdfReader(fileIn), new PdfWriter(fileOut));
            var allPages = doc.getNumberOfPages();

            for (int i = 1; i <= allPages; i++) {
                var text = PdfTextExtractor.getTextFromPage(doc.getPage(i));
                List<Suggest> suggestList = linksSuggester.suggest(suggestConfiguration,text);

                if (!suggestList.isEmpty() && !suggestConfiguration.isEmpty()) {
                    for (Suggest suggest : suggestList) {
                        suggestConfiguration.removeIf(n -> (n.equals(suggest)));
                    }

                    var newPage = doc.addNewPage(i + 1);
                    var rect = new Rectangle(newPage.getPageSize()).moveRight(10).moveDown(10);
                    Canvas canvas = new Canvas(newPage, rect);
                    Paragraph paragraph = new Paragraph("Suggestions:\n");
                    paragraph.setFontSize(25);

                    for (Suggest suggest : suggestList) {
                        PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);
                        PdfAction action = PdfAction.createURI(suggest.getUrl());

                        annotation.setAction(action);

                        Link link = new Link(suggest.getTitle(), annotation);

                        paragraph.add(link.setUnderline());
                        paragraph.add("\n");
                    }

                    canvas.add(paragraph);
                    canvas.close();
                    i++;
                }
            }

            doc.close();
        }
    }
}
