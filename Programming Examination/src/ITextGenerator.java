//2252709 Xuanhe Yang
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;

public class ITextGenerator implements PDFGenerator {

    @Override
    public void generatePDF(Order order, String outputPath) throws IOException {
        PdfWriter writer = new PdfWriter(outputPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Order Summary:"));

        for (FoodItem item : order.getOrderedItems()) {
            document.add(new Paragraph(item.toString()));
        }

        document.add(new Paragraph("Total Price: $" + order.getTotalPrice()));
        document.close();
    }
}
