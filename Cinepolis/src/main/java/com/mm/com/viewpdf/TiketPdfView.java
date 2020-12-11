package com.mm.com.viewpdf;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.Phaser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mm.com.model.Compra;
import com.mm.com.model.Tiket;

@Component("tiket/ver")
public class TiketPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Tiket tiket = (Tiket) model.get("tiket");

		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);

		PdfPCell cell = null;

		cell = new PdfPCell(new Phrase("Datos del Cliente"));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		tabla.addCell(cell);

		tabla.addCell("Datos del cliente");
		tabla.addCell(tiket.getCliente().getNombre() + " " + tiket.getCliente().getApellido());
		tabla.addCell(tiket.getCliente().getEmail());

		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);

		cell = new PdfPCell(new Phrase("Datos del Tiket"));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);

		tabla2.addCell(cell);
		tabla2.addCell("Nº Tiket: " + tiket.getId());
		tabla2.addCell("Descripción: " + tiket.getDescripcion());
		tabla2.addCell("Fecha: " + tiket.getFechaEmision());

		document.add(tabla);
		document.add(tabla2);

		PdfPTable tabla3 = new PdfPTable(4);
		tabla3.setWidths(new float[] { 3.5f, 1, 1, 1 });
		tabla3.addCell("Producto");
		tabla3.addCell("Precio");
		tabla3.addCell("Cantidad");
		tabla3.addCell("Total");

		for (Compra compra : tiket.getItems()) {
			tabla3.addCell(compra.getProducto().getNombre());
			tabla3.addCell(compra.getProducto().getPrecio().toString());
//			tabla3.addCell(compra.getProducto().getPrecio());
			
			
			cell = new PdfPCell(new Phrase(compra.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tabla3.addCell(cell);
			tabla3.addCell(compra.calcularImporte().toString());

		}

		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		tabla3.addCell(tiket.getTotal().toString());

		document.add(tabla3);

	}

}
