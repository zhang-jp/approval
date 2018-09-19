package com.tenkent.infrastructure.utility.excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExportTypeUtility {
    /**
     * 生成单元格内容
     * 
     * @param workBook excel对象
     * @param sheet sheet对象
     * @param row 行号 
     * @param column 列号
     * @param content 待处理的文本内容
     * @return 字节内容
     */
    public abstract byte[] generateColumn(XSSFWorkbook workBook, XSSFSheet sheet, int row, int column, String content);
}
