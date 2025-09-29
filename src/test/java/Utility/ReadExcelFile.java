package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadExcelFile {
    public static FileInputStream fileinputstream;
    public static XSSFWorkbook workbook;
    public static XSSFSheet excelsheet;
    public static XSSFRow row;
    public static XSSFCell cell;

    public static String getcellvalue(String filename, String sheetname, int rowNo, int cellNo) {
        String cellValue = "";
        try {
            // Open the Excel file
            fileinputstream = new FileInputStream(filename);
            workbook = new XSSFWorkbook(fileinputstream);
            excelsheet = workbook.getSheet(sheetname);
            
            if (excelsheet == null) {
                throw new IOException("Sheet not found: " + sheetname);
            }
            
            // Get the cell from the sheet
            row = excelsheet.getRow(rowNo);
            if (row == null) {
                throw new IOException("Row " + rowNo + " not found.");
            }
            cell = row.getCell(cellNo);
            
            if (cell == null) {
                throw new IOException("Cell " + cellNo + " not found.");
            }

            // Handle different cell types
            switch (cell.getCellType()) {
                case STRING:
                    cellValue = cell.getStringCellValue(); // Handle STRING type
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // If the cell contains a date, format it as a string
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        cellValue = sdf.format(date); // Format date as MM/dd/yyyy
                    } else {
                        // Otherwise, treat the cell as a number (e.g., card number, price)
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue()); // Handle BOOLEAN type
                    break;
                case FORMULA:
                    // Handle formula cells (if necessary)
                    cellValue = cell.getCellFormula();
                    break;
                default:
                    cellValue = ""; // Default to empty string for other types
            }

            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return cellValue;
    }

    public static int getrowcount(String filename, String sheetname) {
        try {
            fileinputstream = new FileInputStream(filename);
            workbook = new XSSFWorkbook(fileinputstream);
            excelsheet = workbook.getSheet(sheetname);
            
            if (excelsheet == null) {
                System.out.println("Sheet " + sheetname + " not found!");
                throw new IOException("Sheet not found: " + sheetname);
            }
            
            int rowCount = excelsheet.getPhysicalNumberOfRows();
            System.out.println("Row count for sheet " + sheetname + ": " + rowCount);
            workbook.close();
            return rowCount;
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Error value
        }
    }

    public static int getcolumncount(String filename, String sheetname) {
        try {
            fileinputstream = new FileInputStream(filename);
            workbook = new XSSFWorkbook(fileinputstream);
            excelsheet = workbook.getSheet(sheetname);
            
            if (excelsheet == null) {
                System.out.println("Sheet " + sheetname + " not found!");
                throw new IOException("Sheet not found: " + sheetname);
            }

            int totalcolumns = excelsheet.getRow(0).getLastCellNum();
            workbook.close();
            return totalcolumns;
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Error value
        }
    }

    public static String getStringData(int sheetindex, int row, int column) {
        return workbook.getSheetAt(sheetindex).getRow(row).getCell(column).getStringCellValue();
    }

    public static String getStringData(String sheetname, int row, int column) {
        return workbook.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
    }

    public static double getNumericData(String sheetname, int row, int column) {
        return workbook.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
    }
}
