//2252709 Xuanhe Yang
import java.io.IOException;

public interface PDFGenerator {
    void generatePDF(Order order, String outputPath) throws IOException;
}
