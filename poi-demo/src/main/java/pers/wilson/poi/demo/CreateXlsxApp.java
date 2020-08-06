package pers.wilson.poi.demo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 创建xlsx文件
 */
public class CreateXlsxApp {

    public static final int DEFAULT_COLUMN_WIDTH = 255;

    public static void main(String[] args) {
        File file = new File("C:\\Users\\liu\\Desktop\\导出模板-test.xlsx");

        // 创建一个新的XSSFWorkbook对象
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

        // 创建sheet
        XSSFSheet dataListSheet = xssfWorkbook.createSheet("数据列表");

        Row firstRow = dataListSheet.createRow(0);

        Cell firstRowCell0 = firstRow.createCell(0);
        firstRowCell0.setCellValue("字段");

        Cell firstRowCell1 = firstRow.createCell(1);
        firstRowCell1.setCellValue("状态");

        Row row1 = dataListSheet.createRow(1);
        Cell row1Cell0 = row1.createCell(0);
        row1Cell0.setCellValue("name-name-name");

        Cell row1Cell1 = row1.createCell(1);
        row1Cell1.setCellValue("危险");

        Row row2 = dataListSheet.createRow(2);
        Cell row2Cell0 = row2.createCell(0);
        row2Cell0.setCellValue("value-value-value-value-value");

        Cell row2Cell1 = row2.createCell(1);
        row2Cell1.setCellValue("安全");

        String[] strings = {"a", "b", "c"};

        // 创建单元格下拉框
        createExplicitListConstraint(xssfWorkbook, dataListSheet, strings, 4, 9, 0, 4, false);

        // 设置过滤
        dataListSheet.setAutoFilter(CellRangeAddress.valueOf("B1:D1"));

        // 设置自动列宽
        setExcelColumnsWidth(dataListSheet, 2);

        // 输出
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
            xssfWorkbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置excel的列宽
     *
     * @param sheet     工作表
     * @param maxColumn 最大列数
     */
    public static void setExcelColumnsWidth(Sheet sheet, int maxColumn) {
        // 设置自动列宽
        for (int i = 0; i < maxColumn; i++) {
            sheet.autoSizeColumn(i);
        }
        //获取当前列的宽度，然后对比本列的长度，取最大值
        for (int columnNum = 0; columnNum < maxColumn; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / (DEFAULT_COLUMN_WIDTH + 1);
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    continue;
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    //int length = Encoding.Default.GetBytes(currentCell.ToString()).Length;
                    byte[] uUtf8;
                    uUtf8 = currentCell.getStringCellValue().getBytes(StandardCharsets.UTF_8);
                    int length = uUtf8.length;
                    if (columnWidth < length) {
                        columnWidth = length;
                    }
                }
            }
            // 如果超过255，就设置为最大为255
            if (columnWidth >= DEFAULT_COLUMN_WIDTH) {
                columnWidth = DEFAULT_COLUMN_WIDTH;
            }
            sheet.setColumnWidth(columnNum, columnWidth * (DEFAULT_COLUMN_WIDTH + 1));
        }
    }

    /**
     * 创建单元格下拉框
     *
     * @param xssfWorkbook XSSFWorkbook
     * @param sheet        XSSFSheet
     * @param strings      下拉框数据
     * @param firstRow     起始行
     * @param lastRow      结束行
     * @param firstCol     其实列
     * @param lastCol      结束列
     * @param isHidden     【下拉框数据】大于301（数组长度超过255？）则需要生成隐藏sheet
     */
    public static void createExplicitListConstraint(XSSFWorkbook xssfWorkbook,
                                                    XSSFSheet sheet,
                                                    String[] strings,
                                                    int firstRow,
                                                    int lastRow,
                                                    int firstCol,
                                                    int lastCol,
                                                    boolean isHidden) {

        XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
        // 设置为下拉框的范围
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);

        if (isHidden) {
            String hiddenName = "hidden";
            XSSFSheet hidden = xssfWorkbook.createSheet(hiddenName);
            xssfWorkbook.setSheetHidden(1, true);
            for (int i = 0, length = strings.length; i < length; i++) {
                String string = strings[i];
                XSSFRow row = hidden.createRow(i);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(string);
            }
            DataValidationConstraint constraint = validationHelper.createFormulaListConstraint(hiddenName + "!$A$1:$A$" + strings.length);
            DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
            dataValidation.setShowErrorBox(true);
            sheet.addValidationData(dataValidation);
        } else {
            // 设置下拉框
            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) validationHelper.createExplicitListConstraint(strings);
            // 创建 DataValidation 对象
            XSSFDataValidation validation = (XSSFDataValidation) validationHelper.createValidation(dvConstraint, addressList);
            validation.setShowErrorBox(true);
            // 指定工作表
            sheet.addValidationData(validation);
        }
    }
}
