package com.libedi.test.pdf.view.abstractview;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.libedi.test.pdf.model.PdfModel;

/**
 * 
 * @author libedi
 *
 */
public abstract class AbstractPdfView extends AbstractView {
	
	private static final Logger logger = Logger.getLogger(AbstractPdfView.class);
	
	protected final String PDF_CONTENT_TYPE = "application/pdf";
	
	public AbstractPdfView(){
		this.setContentType(PDF_CONTENT_TYPE);
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("AbstractPdfView.renderMergedOutputModel() :: PDF Build START");
		
		PdfModel pdfModel = (PdfModel) model.get("model");
		if(StringUtils.isEmpty(pdfModel.getDownloadedFileName())){
			throw new IllegalArgumentException("AbstractPdfView.renderMergedOutputModel() :: PDF Build ERROR - No downloaded file name.");
		}
		
		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();
		
		// Apply preferences and build metadata.
		Document document = new Document(pdfModel.getPageSize(), pdfModel.getMarginLeft(), pdfModel.getMarginRight(), 
				pdfModel.getMarginTop(), pdfModel.getMarginBottom());
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);
		
		// Build PDF document.
		writer.setInitialLeading(12.5f);
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();
		
		// Flush to HTTP response.
		this.writeToResponse(response, baos, pdfModel.getDownloadedFileName());
		logger.debug("AbstractPdfView.renderMergedOutputModel() :: PDF Build END");
	}

	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request) throws DocumentException {
		writer.setViewerPreferences(getViewPreferences());
	}
	
	protected int getViewPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		
	}

	protected abstract void buildPdfDocument(Map<String, Object> model, Document document,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	private void writeToResponse(HttpServletResponse response, ByteArrayOutputStream baos, String filename) throws IOException {
		if(filename.lastIndexOf(".") > 0 && StringUtils.equals(".pdf", filename.substring(filename.lastIndexOf(".")).toLowerCase())){
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(filename, "UTF-8") + ".pdf\";");
		}
		this.writeToResponse(response, baos);
	}
}
