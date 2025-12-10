package com.office.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static List<Map<String, String>> readExcelData(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            // Debug: Print all available sheet names
            System.out.println("Available sheets in Excel file:");
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                System.out.println("  - Sheet " + i + ": '" + workbook.getSheetName(i) + "'");
            }
            
            // Try to get sheet by name
            Sheet sheet = workbook.getSheet("Users");
            
            // If not found, try the first sheet
            if (sheet == null) {
                System.out.println("Sheet 'Users' not found. Using first sheet instead.");
                sheet = workbook.getSheetAt(0);
            }
            
            if (sheet == null) {
                throw new RuntimeException("No sheets found in Excel file");
            }
            
            System.out.println("Using sheet: '" + sheet.getSheetName() + "'");
            
            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found");
            }
            
            // Read headers
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                String header = getCellValueAsString(cell).trim();
                headers.add(header);
                System.out.println("Header found: '" + header + "'");
            }
            
            // Read data rows (starting from row 1)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = getCellValueAsString(cell);
                    rowData.put(headers.get(j), value);
                }
                
                System.out.println("Row " + i + " data: " + rowData);
                data.add(rowData);
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + filePath, e);
        }
        
        System.out.println("Total rows read: " + data.size());
        return data;
    }
    
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Format number as string without scientific notation
                    return String.format("%.0f", cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
