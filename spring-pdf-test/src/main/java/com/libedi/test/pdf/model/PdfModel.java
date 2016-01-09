package com.libedi.test.pdf.model;

import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

/**
 * PDF Model for PDF building
 * @author libedi
 *
 */
public class PdfModel {
	private String downloadedFileName;
	private String viewFileName;
	private Map<String, Object> htmlDataMap;
	private String textStr;
	private Rectangle pageSize;
	private float marginLeft;
	private float marginRight;
	private float marginTop;
	private float marginBottom;
	private float fontSize;
	private boolean useHtml;
	
	/**
	 * Constructor
	 */
	public PdfModel() {
		this.htmlDataMap = new HashMap<String, Object>();
		this.pageSize = PageSize.A4;
		this.marginLeft = 25f;
		this.marginRight = 25f;
		this.marginTop = 25f;
		this.marginBottom = 25f;
		this.fontSize = Font.DEFAULTSIZE;
		this.useHtml = true;
	}

	/**
	 * Constructor
	 * @param downloadedFileName
	 * @param viewFileName
	 */
	public PdfModel(String downloadedFileName, String viewFileName) {
		this.downloadedFileName = downloadedFileName;
		this.viewFileName = viewFileName;
		this.htmlDataMap = new HashMap<String, Object>();
		this.pageSize = PageSize.A4;
		this.marginLeft = 25f;
		this.marginRight = 25f;
		this.marginTop = 25f;
		this.marginBottom = 25f;
		this.fontSize = Font.DEFAULTSIZE;
		this.useHtml = true;
	}

	/**
	 * Constructor
	 * @param downloadedFileName
	 * @param viewFileName
	 * @param htmlDataMap
	 * @param pageSize
	 * @param marginLeft
	 * @param marginRight
	 * @param marginTop
	 * @param marginBottom
	 */
	public PdfModel(String downloadedFileName, String viewFileName,	Map<String, Object> htmlDataMap, 
			Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom) {
		this.downloadedFileName = downloadedFileName;
		this.viewFileName = viewFileName;
		this.htmlDataMap = htmlDataMap;
		this.pageSize = pageSize;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
		this.useHtml = true;
	}
	
	/**
	 * Constructor
	 * @param downloadedFileName
	 * @param textStr
	 * @param pageSize
	 * @param marginLeft
	 * @param marginRight
	 * @param marginTop
	 * @param marginBottom
	 * @param fontSize
	 */
	public PdfModel(String downloadedFileName, String textStr,
			Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom, float fontSize) {
		this.downloadedFileName = downloadedFileName;
		this.textStr = textStr;
		this.pageSize = pageSize;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
		this.fontSize = fontSize;
		this.useHtml = false;
	}
	
	/**
	 * Constructor
	 * @param downloadedFileName
	 * @param pageSize
	 * @param marginLeft
	 * @param marginRight
	 * @param marginTop
	 * @param marginBottom
	 * @param fontSize
	 * @param useHtml
	 */
	public PdfModel(String downloadedFileName, Rectangle pageSize,
			float marginLeft, float marginRight, float marginTop, float marginBottom, float fontSize, boolean useHtml) {
		this.htmlDataMap = new HashMap<String, Object>();
		this.downloadedFileName = downloadedFileName;
		this.pageSize = pageSize;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
		this.fontSize = fontSize;
		this.useHtml = useHtml;
	}

	public String getDownloadedFileName() {
		return downloadedFileName;
	}

	public void setDownloadedFileName(String downloadedFileName) {
		this.downloadedFileName = downloadedFileName;
	}

	public String getViewFileName() {
		return viewFileName;
	}

	public void setViewFileName(String viewFileName) {
		this.viewFileName = viewFileName;
	}

	public Map<String, Object> getHtmlDataMap() {
		return htmlDataMap;
	}

	public void setHtmlDataMap(Map<String, Object> htmlDataMap) {
		this.htmlDataMap = htmlDataMap;
	}

	public String getTextStr() {
		return textStr;
	}

	public void setTextStr(String textStr) {
		this.textStr = textStr;
	}

	public boolean isUseHtml() {
		return useHtml;
	}

	public void setUseHtml(boolean useHtml) {
		this.useHtml = useHtml;
	}

	public Rectangle getPageSize() {
		return pageSize;
	}

	public void setPageSize(Rectangle pageSize) {
		this.pageSize = pageSize;
	}

	public float getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(float marginLeft) {
		this.marginLeft = marginLeft;
	}

	public float getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(float marginRight) {
		this.marginRight = marginRight;
	}

	public float getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(float marginTop) {
		this.marginTop = marginTop;
	}

	public float getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(float marginBottom) {
		this.marginBottom = marginBottom;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	
}
