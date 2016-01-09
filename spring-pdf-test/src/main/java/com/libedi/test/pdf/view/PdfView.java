package com.libedi.test.pdf.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.libedi.test.pdf.model.PdfModel;
import com.libedi.test.pdf.view.abstractview.AbstractPdfView;

/**
 * 
 * @author libedi
 *
 */
public class PdfView extends AbstractPdfView {
	private static final Logger logger = Logger.getLogger(PdfView.class);
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Value("${resources.file.path.css}")
	private String cssFilePath;
	
	@Value("${resources.file.path.font}")
	private String fontFilePath;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		PdfModel pdfModel = (PdfModel) model.get("model");
		
		if(pdfModel.isUseHtml()){
			// CSS
			CSSResolver cssResolver = new StyleAttrCSSResolver();
			CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(request.getServletContext().getRealPath(cssFilePath) + File.separator + "common.css"));
			cssResolver.addCss(cssFile);
			
			// Font
			XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontProvider.register(request.getServletContext().getRealPath(fontFilePath) + File.separator + "MALGUN.TTF", "MalgunGothic");
			fontProvider.register(request.getServletContext().getRealPath(fontFilePath) + File.separator + "MALGUNBD.TTF", "MalgunGothicBold");
			CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
			
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
			
			// Pipeline
			PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
			HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
			CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
			
			XMLWorker worker = new XMLWorker(css, true);
			XMLParser parser = new XMLParser(worker);
			
			String htmlStr = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, pdfModel.getViewFileName(), "UTF-8", pdfModel.getHtmlDataMap());
			logger.debug("PdfView.buildPdfDocument() :: HTML STRING\n" + htmlStr);
			
			parser.parse(new ByteArrayInputStream(htmlStr.getBytes()));
			
		} else {
			BaseFont baseFont = BaseFont.createFont(request.getServletContext().getRealPath(fontFilePath) + File.separator + "MALGUN.TTF"
					, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font paragraphFont = new Font(baseFont, pdfModel.getFontSize());
			logger.debug("PdfView.buildPdfDocument() :: TEXT STRING - " + pdfModel.getTextStr());
			document.add(new Paragraph(pdfModel.getTextStr(), paragraphFont));
		}
	}

}
