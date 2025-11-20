//2252709 Xuanhe Yang
public class PDFGeneratorFactory {

    public static PDFGenerator getPDFGenerator(String library) {
        switch (library.toLowerCase()) {
            case "pdfbox":
                return new PDFBoxGenerator();
            case "itext":
                return new ITextGenerator();
            default:
                throw new IllegalArgumentException("Unsupported PDF library: " + library);
        }
    }
}
