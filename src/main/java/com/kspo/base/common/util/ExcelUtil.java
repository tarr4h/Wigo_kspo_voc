package com.kspo.base.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.IExcelVo;
import com.kspo.voc.comn.util.Utilities;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <pre>
 * com.kspo.base.common.util - ExcelUtil.java
 * </pre>
 *
 * @ClassName : ExcelUtil
 * @Description : 엑셀 유틸리티
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
public class ExcelUtil {
	private static final int _TITLE_SIZE = 20;
	private static final int _BODY_SIZE = 12;

	public static boolean downloadExcel(IExcelVo vo, String fileName, HttpServletResponse response) throws Exception {

		if (response == null)
			return false;
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition",
				"attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
		String ext = Utilities.getFileExtension(fileName);
		if ("xls".equalsIgnoreCase(ext))
			return createWorkbookH(vo, response.getOutputStream());
		else
			return createWorkbookX(vo, response.getOutputStream());
	}

	public static boolean downloadExcel(List<IExcelVo> list, String fileName, HttpServletResponse response)
			throws Exception {
		if (response == null)
			return false;
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition",
				"attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
		String ext = Utilities.getFileExtension(fileName);
		if ("xls".equalsIgnoreCase(ext))
			return createWorkbookH(list, response.getOutputStream());
		else
			return createWorkbookX(list, response.getOutputStream());
	}

	public static boolean createWorkbook(IExcelVo vo, String fileName) {
		if (vo == null || Utilities.isEmpty(fileName))
			return false;
		String ext = Utilities.getFileExtension(fileName);

		if ("xls".equalsIgnoreCase(ext))
			return createWorkbookH(vo, fileName);
		else
			return createWorkbookX(vo, fileName);
	}

	public static boolean createWorkbookH(List<IExcelVo> list, String fileName) {
		File file = new File(fileName);
		if (!file.isFile())
			return false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			return createWorkbookH(list, fos);
		} catch (Exception e) {
			return false;
		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (Exception ex) {
				log.warn(ex.getMessage());
			}
		}
	}

	public static boolean createWorkbookX(List<IExcelVo> list, String fileName) {
		File file = new File(fileName);
		if (!file.isFile())
			return false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);

			return createWorkbookX(list, fos);
		} catch (Exception e) {
			return false;
		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (IOException ex) {
				log.warn(ex.getMessage());
			}
		}
	}

	public static boolean createWorkbookH(IExcelVo vo, String fileName) {
		File file = new File(fileName);
		if (!file.isFile())
			return false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			return createWorkbookH(vo, fos);
		} catch (Exception e) {
			return false;
		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (IOException ex) {
				log.warn(ex.getMessage());
			}
		}
	}

	public static boolean createWorkbookX(IExcelVo vo, String fileName) {
		File file = new File(fileName);
		if (!file.isFile())
			return false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);

			return createWorkbookX(vo, fos);
		} catch (Exception e) {
			return false;
		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (IOException ex) {
				log.warn(ex.getMessage());
			}
		}
	}

	public static boolean createWorkbookH(List<IExcelVo> list, OutputStream os) {
		HSSFWorkbook workbook = null;

		try {
			workbook = new HSSFWorkbook();
			for (int i = 0; i < list.size(); i++) {
				if (!createWorkbookH(list.get(i), workbook))
					return false;
			}
			workbook.write(os);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (IOException ex) {
				log.warn(ex.getMessage());
			}

		}
	}

	public static boolean createWorkbookX(List<IExcelVo> list, OutputStream os) {
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();

			for (int i = 0; i < list.size(); i++) {
				if (!createWorkbookX(list.get(i), workbook))
					return false;
			}
			workbook.write(os);
			return true;
		} catch (Exception e) {
			Utilities.trace(e);
			return false;
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (Exception ex) {
				Utilities.trace(ex);
			}

		}
	}

	public static boolean createWorkbookH(IExcelVo vo, OutputStream os) {
		HSSFWorkbook workbook = null;

		try {
			workbook = new HSSFWorkbook();

			if (!createWorkbookH(vo, workbook))
				return false;
			workbook.write(os);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (IOException ex) {
				log.warn(ex.getMessage());
			}

		}
	}

	public static boolean createWorkbookX(IExcelVo vo, OutputStream os) {
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();

			if (!createWorkbookX(vo, workbook))
				return false;
			workbook.write(os);
			return true;
		} catch (Exception e) {
			Utilities.trace(e);
			return false;
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (Exception ex) {
				Utilities.trace(ex);
			}

		}
	}

	private static boolean createWorkbookH(IExcelVo vo, HSSFWorkbook workbook) {
		if (vo == null || workbook == null)
			return false;

		List<Entry<String, String>> cols = vo.getColumns();
		if (Utilities.isEmpty(cols))
			return false;
		List<EzMap> list = vo.getData();
		String title = vo.getTitle();

		try {
			HSSFSheet sheet = workbook.createSheet(vo.getTitle());
			int totalCols = cols.size();
			createTitle(sheet, title, totalCols, 0);
			creatHeader(sheet, cols, 2);
			creatData(sheet, list, cols, 3);

			return true;
		} catch (Exception e) {
			Utilities.trace(e);
			return false;
		}
	}

	public static boolean createWorkbookX(IExcelVo vo, XSSFWorkbook workbook) {
		if (vo == null || workbook == null)
			return false;

		List<Entry<String, String>> cols = vo.getColumns();
		if (Utilities.isEmpty(cols))
			return false;
		List<EzMap> list = vo.getData();
		String title = vo.getTitle();

		try {
			XSSFSheet sheet = workbook.createSheet(vo.getTitle());
			int totalCols = cols.size();
			createTitle(sheet, title, totalCols, 0);
			creatHeader(sheet, cols, 2);
			creatData(sheet, list, cols, 3);

			return true;
		} catch (Exception e) {
			Utilities.trace(e);
			return false;
		}
	}

	private static boolean creatData(HSSFSheet sheet, List<EzMap> list, List<Entry<String, String>> cols, int rownum) {
		if (Utilities.isEmpty(list))
			return true;
		try {
			HSSFFont font = sheet.getWorkbook().createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) _BODY_SIZE);
			CellStyle cs = sheet.getWorkbook().createCellStyle();
			cs.setTopBorderColor((short) 0);
			cs.setRightBorderColor((short) 0);
			cs.setBottomBorderColor((short) 0);
			cs.setBorderRight(BorderStyle.THIN);
			cs.setBorderTop(BorderStyle.THIN);
			cs.setBorderBottom(BorderStyle.THIN);
			cs.setBorderLeft(BorderStyle.THIN);
			cs.setAlignment(HorizontalAlignment.CENTER);
			cs.setVerticalAlignment(VerticalAlignment.CENTER);
			cs.setFont(font);
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = sheet.createRow(rownum);
				for (int j = 0; j < cols.size(); j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cs);
					cell.setCellValue(cols.get(i).getValue());
				}
			}
			return true;
		} catch (Exception e) {
			Utilities.trace(e);
			return false;
		}
	}

	private static boolean creatHeader(HSSFSheet sheet, List<Entry<String, String>> cols, int rownum) {
		try {
			HSSFFont font = sheet.getWorkbook().createFont();
			font.setBold(false);
			font.setFontHeightInPoints((short) _BODY_SIZE);
			CellStyle cs = sheet.getWorkbook().createCellStyle();
			cs.setTopBorderColor((short) 0);
			cs.setRightBorderColor((short) 0);
			cs.setBottomBorderColor((short) 0);
			cs.setBorderRight(BorderStyle.THIN);
			cs.setBorderTop(BorderStyle.THIN);
			cs.setBorderBottom(BorderStyle.THIN);
			cs.setBorderLeft(BorderStyle.THIN);
			cs.setAlignment(HorizontalAlignment.CENTER);
			cs.setVerticalAlignment(VerticalAlignment.CENTER);
			cs.setFont(font);
			HSSFRow row = sheet.createRow(rownum);
			for (int i = 0; i < cols.size(); i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(cs);
				cell.setCellValue(cols.get(i).getKey());
			}
			return true;
		} catch (Exception e) {
			Utilities.trace(e);
			return false;
		}

	}

	private static boolean createTitle(HSSFSheet sheet, String title, int totalCols, int rownum) {
		try {
			mergeRange(sheet, rownum, rownum, 0, totalCols - 1);
			HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints(30);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(title);
			CellStyle cs = cell.getCellStyle();
			if (cs == null) {
				cs = sheet.getWorkbook().createCellStyle();
			}
			HSSFFont font = sheet.getWorkbook().createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) _TITLE_SIZE);
			cs.setTopBorderColor((short) 0);
			cs.setRightBorderColor((short) 0);
			cs.setBottomBorderColor((short) 0);
			cs.setBorderRight(BorderStyle.NONE);
			cs.setBorderTop(BorderStyle.NONE);
			cs.setBorderBottom(BorderStyle.NONE);
			cs.setBorderLeft(BorderStyle.NONE);
			cs.setAlignment(HorizontalAlignment.CENTER);
			cs.setVerticalAlignment(VerticalAlignment.CENTER);
			cs.setFont(font);
			cell.setCellStyle(cs);
			return true;

		} catch (Exception ex) {
			Utilities.trace(ex);
			return false;
		}

	}

	private static boolean creatHeader(XSSFSheet sheet, List<Entry<String, String>> cols, int rownum) {
		try {
			XSSFFont font = sheet.getWorkbook().createFont();
			font.setBold(false);
			font.setFontHeightInPoints((short) _BODY_SIZE);
			CellStyle cs = sheet.getWorkbook().createCellStyle();
			cs.setTopBorderColor((short) 0);
			cs.setRightBorderColor((short) 0);
			cs.setBottomBorderColor((short) 0);
			cs.setBorderRight(BorderStyle.THIN);
			cs.setBorderTop(BorderStyle.THIN);
			cs.setBorderBottom(BorderStyle.THIN);
			cs.setBorderLeft(BorderStyle.THIN);
			cs.setAlignment(HorizontalAlignment.CENTER);
			cs.setVerticalAlignment(VerticalAlignment.CENTER);
			cs.setFont(font);
			XSSFRow row = sheet.createRow(rownum);
			for (int i = 0; i < cols.size(); i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(cs);
				cell.setCellValue(cols.get(i).getValue());
			}
			return true;
		} catch (Exception e) {
			Utilities.trace(e);
			return false;
		}

	}

	private static boolean creatData(XSSFSheet sheet, List<EzMap> list, List<Entry<String, String>> cols, int rownum) {
		if (Utilities.isEmpty(list))
			return true;
		try {
			XSSFFont font = sheet.getWorkbook().createFont();
			font.setBold(false);
			font.setFontHeightInPoints((short) _BODY_SIZE);
			CellStyle cs = sheet.getWorkbook().createCellStyle();
			cs.setTopBorderColor((short) 0);
			cs.setRightBorderColor((short) 0);
			cs.setBottomBorderColor((short) 0);
			cs.setBorderRight(BorderStyle.THIN);
			cs.setBorderTop(BorderStyle.THIN);
			cs.setBorderBottom(BorderStyle.THIN);
			cs.setBorderLeft(BorderStyle.THIN);
			cs.setAlignment(HorizontalAlignment.CENTER);
			cs.setVerticalAlignment(VerticalAlignment.CENTER);
			cs.setFont(font);
			for (int i = 0; i < list.size(); i++) {
				XSSFRow row = sheet.createRow(rownum + i);
				EzMap data = list.get(i);
				for (int j = 0; j < cols.size(); j++) {
					XSSFCell cell = row.createCell(j);
					cell.setCellStyle(cs);
					cell.setCellValue(Utilities.nullCheck(data.get(cols.get(j).getKey())));
				}
			}
			return true;
		} catch (Exception e) {
			Utilities.trace(e);
			return false;
		}
	}

	private static boolean createTitle(XSSFSheet sheet, String title, int totalCols, int rownum) {
		try {
			mergeRange(sheet, rownum, rownum, 0, totalCols - 1);

			XSSFRow row = sheet.createRow(0);
			row.setHeightInPoints(30);
			XSSFCell cell = row.createCell(0);

			CellStyle cs = sheet.getWorkbook().createCellStyle();
			XSSFFont font = sheet.getWorkbook().createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 20);

			cs.setTopBorderColor((short) 0);
			cs.setRightBorderColor((short) 0);
			cs.setBottomBorderColor((short) 0);
			cs.setBorderRight(BorderStyle.NONE);
			cs.setBorderTop(BorderStyle.NONE);
			cs.setBorderBottom(BorderStyle.NONE);
			cs.setBorderLeft(BorderStyle.NONE);
			cs.setAlignment(HorizontalAlignment.CENTER);
			cs.setVerticalAlignment(VerticalAlignment.CENTER);
			cs.setFont(font);
			cell.setCellStyle(cs);
			cell.setCellValue(title);
			return true;

		} catch (Exception ex) {
			Utilities.trace(ex);
			return false;
		}

	}

	private static void mergeRange(XSSFSheet sheet, int rowStart, int rowEnd, int colStart, int colEnd) {
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd, colStart, colEnd));
	}

	private static void mergeRange(HSSFSheet sheet, int rowStart, int rowEnd, int colStart, int colEnd) {
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd, colStart, colEnd));
	}

	public static List<List<Object>> excelToList(File file) throws Exception {
		return excelToList(file, 0);
	}

	public static List<EzMap> excelToList(File file, List<String> header) throws Exception {
		return excelToList(file, header, 0);
	}

	public static List<EzMap> excelToList(File file, List<String> header, int sheetIndex) throws Exception {
		if (file == null || !file.isFile())
			return null;
		String ext = Utilities.getFileExtension(file.getAbsolutePath());
		if ("xls".equalsIgnoreCase(ext))
			return excelToListH(file, header, sheetIndex);
		else
			return excelToListX(file, header, sheetIndex);
	}

	public static List<EzMap> excelToListX(File file, List<String> header, int sheetIndex) throws Exception {
		XSSFWorkbook workbook = null;
		List<EzMap> list = new ArrayList<EzMap>();
		try {
			workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			if (sheet == null)
				return null;
			int empRow = 0;
			int index = 0;
			while (empRow < 5) {
				XSSFRow row = sheet.getRow(index++);
				if (row == null) {
					empRow++;
					continue;
				} else
					empRow = 0;
				EzMap map = new EzMap();
				boolean emptyCell = true;
				int cIndex = 0;
				int headerIndex = 0;
				while (headerIndex < header.size()) {
					XSSFCell cell = row.getCell(cIndex++);
					if (!emptyCell && cell == null)
						break;
					emptyCell = false;
					if (emptyCell && cIndex > 10)
						break;

					map.put(header.get(headerIndex++), getCellValue(cell));

				}
				list.add(map);

			}
			return list;
		} catch (Exception ex) {
			Utilities.trace(ex);
		} finally {
			if (workbook != null)
				workbook.close();
		}
		return null;
	}

	public static List<EzMap> excelToListH(File file, List<String> header, int sheetIndex) throws Exception {
		HSSFWorkbook workbook = null;
		List<EzMap> list = new ArrayList<EzMap>();
		InputStream is = new FileInputStream(file);
		try {
			workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			if (sheet == null)
				return null;
			int empRow = 0;
			int index = 0;
			while (empRow < 5) {
				HSSFRow row = sheet.getRow(index++);
				if (row == null) {
					empRow++;
					continue;
				} else
					empRow = 0;
				EzMap map = new EzMap();
				boolean emptyCell = true;
				int cIndex = 0;
				int headerIndex = 0;
				while (headerIndex < header.size()) {
					HSSFCell cell = row.getCell(cIndex++);
					if (!emptyCell && cell == null)
						break;
					emptyCell = false;
					if (emptyCell && cIndex > 10)
						break;
					map.put(header.get(headerIndex++), getCellValue(cell));

				}
				list.add(map);

			}
			return list;
		} catch (Exception ex) {
			log.warn(ex.getMessage());
		} finally {
			if (is != null)
				is.close();
			if (workbook != null)
				workbook.close();
		}
		return null;
	}

	public static List<List<Object>> excelToList(File file, int sheet) throws Exception {
		if (file == null || !file.isFile())
			return null;
		String ext = Utilities.getFileExtension(file.getAbsolutePath());
		if ("xls".equalsIgnoreCase(ext))
			return excelToListH(file, sheet);
		else
			return excelToListX(file, sheet);
	}

	public static List<List<Object>> excelToListX(File file, int sheetIndex) throws Exception {
		XSSFWorkbook workbook = null;
		List<List<Object>> list = new ArrayList<List<Object>>();
		try {
			workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			if (sheet == null)
				return null;
			int empRow = 0;
			int index = 0;
			while (empRow < 5) {
				XSSFRow row = sheet.getRow(index++);
				if (row == null) {
					empRow++;
					continue;
				} else
					empRow = 0;
				List<Object> rlist = new ArrayList<Object>();
				boolean emptyCell = true;
				int cIndex = 0;
				while (true) {
					XSSFCell cell = row.getCell(cIndex++);
					if (!emptyCell && cell == null)
						break;
					emptyCell = false;
					if (emptyCell && cIndex > 10)
						break;

					rlist.add(getCellValue(cell));
				}
				list.add(rlist);

			}
			return list;
		} catch (Exception ex) {
			log.warn(ex.getMessage());
		} finally {
			if (workbook != null)
				workbook.close();
		}
		return null;
	}

	public static List<List<Object>> excelToListH(File file, int sheetIndex) throws Exception {
		HSSFWorkbook workbook = null;
		List<List<Object>> list = new ArrayList<List<Object>>();
		InputStream is = new FileInputStream(file);
		try {
			workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			if (sheet == null)
				return null;
			int empRow = 0;
			int index = 0;
			while (empRow < 5) {
				HSSFRow row = sheet.getRow(index++);
				if (row == null) {
					empRow++;
					continue;
				} else
					empRow = 0;
				List<Object> rlist = new ArrayList<Object>();
				boolean emptyCell = true;
				int cIndex = 0;
				while (true) {
					HSSFCell cell = row.getCell(cIndex++);
					if (!emptyCell && cell == null)
						break;
					emptyCell = false;
					if (emptyCell && cIndex > 10)
						break;
					rlist.add(getCellValue(cell));
				}
				list.add(rlist);

			}
			return list;
		} catch (Exception ex) {
			log.warn(ex.getMessage());
		} finally {
			if (is != null)
				is.close();
			if (workbook != null)
				workbook.close();
		}
		return null;
	}

	private static Object getCellValue(HSSFCell cell) {
		CellType type = cell.getCellType();
		Object value = null;
		if (type == CellType.NUMERIC)
			value = cell.getNumericCellValue();
		else if (type == CellType.FORMULA) {
			try {
				value = cell.getNumericCellValue();
			} catch (Exception e) {
				try {
					value = cell.getStringCellValue();
					if (Utilities.isNotEmpty(value))
						value = value.toString().trim();
				} catch (Exception ex) {
					log.warn(ex.getMessage());
				}
			}
		} else {
			value = cell.getStringCellValue();
			if (Utilities.isNotEmpty(value))
				value = value.toString().trim();
		}
		return value;
	}

	private static Object getCellValue(XSSFCell cell) {
		if (cell == null)
			return null;
//		cell.setCellType(CellType.STRING);
		CellType type = cell.getCellType();
		Object value = null;
		if (type == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(date);
			} else
				value = cell.getNumericCellValue();

		} else if (type == CellType.FORMULA) {
			try {

				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(date);
				} else
					value = cell.getNumericCellValue();

			} catch (Exception e) {
				try {
					value = cell.getStringCellValue();
					if (Utilities.isNotEmpty(value))
						value = value.toString().trim();
				} catch (Exception ex) {
					log.warn(ex.getMessage());
				}
			}
		} else {
			value = cell.getStringCellValue();
			if (Utilities.isNotEmpty(value))
				value = value.toString().trim();
		}
		return value;
	}
}
