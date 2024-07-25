package com.app.common.helper;

import com.app.Application;
import com.app.utils.QrCodeUtils;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jnafilechooser.api.JnaFileChooser;
import org.apache.poi.xssf.usermodel.XSSFColor;

/**
 *
 * @author inuHa
 */
public class ExcelHelper {
    
    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
	
    private static List<String[]> readFile(File file, boolean skipHeader) {
        List<String[]> data = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            
            if (skipHeader && rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    String cellValue = getCellValueAsString(cell);
                    rowData.add(cellValue);
                }
                data.add(rowData.toArray(new String[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
	
    private static void writeExcel(File file, List<String[]> headers, List<String[]> rows) throws IOException { 
        List<String[]> data = headers != null ? new ArrayList<>(headers) : new ArrayList<>();
	data.addAll(rows);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = (Sheet) workbook.createSheet("Data");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = (Font) workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(new XSSFColor(java.awt.Color.decode("#8bc34a"), null));
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int rowNum = 0;
        for (String[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(rowData[i]);
                if (headers != null && rowNum == 1) {
                    cell.setCellStyle(headerStyle);
                }
            }
        }
        int numberOfColumns = rows.size();
        for (int col = 0; col <= numberOfColumns; col++) {
            int maxWidth = 0;

            for (int row = 0; row <= sheet.getLastRowNum(); row++) {
                Row currentRow = sheet.getRow(row);
                if (currentRow != null) {
                    Cell cell = currentRow.getCell(col);
                    if (cell != null) {
                        int cellWidth = cell.toString().length();
                        if (cellWidth > maxWidth) {
                            maxWidth = cellWidth;
                        }
                    }
                }
            }

            sheet.setColumnWidth(col, maxWidth * 256 + 2048);
        }
	
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
    
    public static void writeFile(String fileName, List<String[]> rows) {
	writeFile(fileName, null, rows);
    }
    
    public static void writeFile(String fileName, List<String[]> headers, List<String[]> rows) {
        JnaFileChooser ch = new JnaFileChooser();
        ch.setMode(JnaFileChooser.Mode.Directories);
        boolean act = ch.showOpenDialog(Application.app);
        if (act) {
            File folder = ch.getSelectedFile();
            File file = new File(folder, fileName + ".xlsx");
            try {
                writeExcel(file, headers, rows);
                MessageToast.success("Xuất dữ liệu sang Excel thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                MessageToast.error("Không thể xuất dữ liệu sang Excel!!!!");
            }
        }
    }
    
    public static List<String[]> readFile() {
	return readFile(false);
    }
    
    public static List<String[]> readFile(boolean skipHeader) {
        JnaFileChooser ch = new JnaFileChooser();
        ch.addFilter("Tệp Excel (*.xlsx, *.xls)", "xlsx", "xls");
        boolean act = ch.showOpenDialog(Application.app);
        if (act) {
            File file = ch.getSelectedFile();
	    return readFile(file, skipHeader);
        }
	return null;
    }
}
