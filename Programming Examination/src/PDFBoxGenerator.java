//2252709 Xuanhe Yang
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFBoxGenerator implements PDFGenerator {
    private static final float MARGIN = 50;
    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth() - 2 * MARGIN;
    private static final float PAGE_HEIGHT = PDRectangle.A4.getHeight() - 2 * MARGIN;
    private static final float FONT_SIZE = 12;
    private static final float LEADING = 14.5f; // Line spacing

    @Override
    public void generatePDF(Order order, String outputPath) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
        contentStream.setLeading(LEADING);
        contentStream.newLineAtOffset(MARGIN, PAGE_HEIGHT);

        // Title
        contentStream.showText("Order Summary:");
        contentStream.newLine();

        List<FoodItem> orderedItems = order.getOrderedItems();
        float yPosition = PAGE_HEIGHT - LEADING;

        // Loop through ordered items
        for (FoodItem item : orderedItems) {
            String itemText = item.toString();
            yPosition = addWrappedText(contentStream, itemText, yPosition);

            // If yPosition is too low for the next line, add a new page
            if (yPosition <= MARGIN) {
                contentStream.endText();
                contentStream.close();
                page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
                contentStream.setLeading(LEADING);
                contentStream.newLineAtOffset(MARGIN, PAGE_HEIGHT);
                yPosition = PAGE_HEIGHT;
            }
        }

        // Add total price at the end
        contentStream.showText("Total Price: $" + order.getTotalPrice());
        contentStream.endText();
        contentStream.close();

        document.save(outputPath);
        document.close();
    }

    private float addWrappedText(PDPageContentStream contentStream, String text, float yPosition) throws IOException {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        float lineWidth = 0;

        for (String word : words) {
            String testLine = line + word + " ";
            float testWidth = FONT_SIZE * PDType1Font.HELVETICA_BOLD.getStringWidth(testLine) / 1000;

            // Check if adding this word would exceed the page width
            if (lineWidth + testWidth > PAGE_WIDTH) {
                // Print the line and move to the next line
                contentStream.showText(line.toString());
                contentStream.newLine();
                yPosition -= LEADING;

                line = new StringBuilder(word + " ");
                lineWidth = FONT_SIZE * PDType1Font.HELVETICA_BOLD.getStringWidth(word + " ") / 1000;
            } else {
                line.append(word).append(" ");
                lineWidth = testWidth;
            }
        }

        // Print the remaining text
        contentStream.showText(line.toString());
        contentStream.newLine();

        return yPosition - LEADING; // Update yPosition after each line
    }
}
