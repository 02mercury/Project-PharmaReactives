package com.pharma.reactives.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pharma.reactives.models.Reactive;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ReactivePDF {
    private List<Reactive> reactiveList;

    public ReactivePDF(List<Reactive> reactiveList) {
        this.reactiveList = reactiveList;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(68, 140, 194));
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Reactive ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Formula", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Stock", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table){
        for (Reactive reactive : reactiveList){
            table.addCell(String.valueOf(reactive.getId()));
            table.addCell(String.valueOf(reactive.getName()));
            table.addCell(String.valueOf(reactive.getFormula()));
            table.addCell(String.valueOf(reactive.getStock()));
            table.addCell(String.valueOf(reactive.getPrice()));
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(new Color(68, 140, 194));
        font.setSize(18);

        Paragraph title = new Paragraph("List of All Reactives", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 1.5f});


        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
