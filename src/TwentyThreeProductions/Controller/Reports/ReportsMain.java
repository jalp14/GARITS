package TwentyThreeProductions.Controller.Reports;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class ReportsMain {

    @FXML
    private StackPane partsMainStackPane;

    @FXML
    private JFXButton newReportBtn;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton existingReportBtn;

    @FXML
    private JFXButton editAutoReportsBtn;

    @FXML
    void backBtnClicked(ActionEvent event) {

    }

    @FXML
    void editAutoReportsBtnClicked(ActionEvent event) {

    }

    @FXML
    void existingReportBtnClicked(ActionEvent event) {

    }

    @FXML
    void newReportBtnClicked(ActionEvent event) {

    }

    public void initialize() {
        System.out.println("Creating PDF");
        createPDF();
        System.out.println("PDF created");
    }


    public void createPDF() {

        try {
            // Load PDF
            File pdfFile = new File("src/TwentyThreeProductions/PDFs/test.pdf");
            System.out.println("Loading PDF");
            PDDocument pdf = new PDDocument().load(pdfFile);
            System.out.println("PDF Loaded");
            // Add page to PDF
            PDPage page = pdf.getPage(1);
            // Add Text to page 1
            PDPageContentStream contentStream = new PDPageContentStream(pdf,page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 20);
            contentStream.newLineAtOffset(20, 450);
            String message = "LAng PrOc suCkS!!!!";
            contentStream.showText(message);
            contentStream.endText();
            contentStream.close();
            // Save it
            pdf.save(new File("src/TwentyThreeProductions/PDFs/test.pdf"));
            // Close
            pdf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
