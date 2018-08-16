package com.tenkent.infrastructure.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.tenkent.infrastructure.log.LoggerManager;

/**
 * Excel帮助类
 * 
 * @author  qinzhengliang
 * @version  [版本号, 2017年3月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PoiUtils
{
    private static final String XLS_LOWER_CASE = "xls";
    
    private static final String XLSX_LOWER_CASE = "xlsx";
    
    private static final String XLS_UPPER_CASE = "XLS";
    
    private static final String XLSX_UPPER_CASE = "XLSX";
    
    private static final String PARSE_ERROR = "excel解析异常";
    
    private PoiUtils()
    {
    }
    
    /**
     * 通过文件路径获取Excel读取
     * @param path 文件路径，只接受xls或xlsx结尾
     * @param isHeader 是否表头
     * @param headerCount 表头行数
     * @return count 如果文件路径为空，返回0；
     */
    public static Map<String, List<String[]>> readRecordsInputPath(String path, boolean isHeader, int headerCount)
    {
        if (isNotExcelPath(path))
        {
            return new HashMap<>();
        }
        
        File file = new File(path);
        try (InputStream inputStream = new FileInputStream(file))
        {
            if (path.endsWith(XLS_LOWER_CASE) || path.endsWith(XLS_UPPER_CASE))
            {
                return readXLSRecords(inputStream, isHeader, headerCount);
            }
            else if (path.endsWith(XLSX_LOWER_CASE) || path.endsWith(XLSX_UPPER_CASE))
            {
                return readXLSXRecords(inputStream, isHeader, headerCount);
            }
        }
        catch (Exception e)
        {
            LoggerManager.error(PoiUtils.class, e, PARSE_ERROR);
        }
        return new HashMap<>();
    }
    
    /**
     * 通过文件file获取Excel读取
     * @param file 文件，只接受xls或xlsx结尾
     * @param isHeader 是否表头
     * @param headerCount 表头行数
     * @return count 如果文件路径为空，返回0；
     */
    public static Map<String, List<String[]>> readRecordsInputPath(MultipartFile file, boolean isHeader,
        int headerCount)
    {
        if (file == null)
        {
            return new HashMap<>();
        }
        
        //获取后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (isNotExcelPath(suffix))
        {
            return new HashMap<>();
        }
        
        try (InputStream inputStream = file.getInputStream())
        {
            if (suffix.endsWith(XLS_LOWER_CASE) || suffix.endsWith(XLS_UPPER_CASE))
            {
                return readXLSRecords(inputStream, isHeader, headerCount);
            }
            else if (suffix.endsWith(XLSX_LOWER_CASE) || suffix.endsWith(XLSX_UPPER_CASE))
            {
                return readXLSXRecords(inputStream, isHeader, headerCount);
            }
        }
        catch (Exception e)
        {
            LoggerManager.error(PoiUtils.class, e, PARSE_ERROR);
        }
        return new HashMap<>();
    }
    
    /**
     * 解析EXCEL2003文件流
     * 如果一行记录的行中或行尾出现空格，POI工具类可能会跳过空格不做处理，所以默认第一行是表头，所有待解析的记录都以表头为准
     * @param inputStream  输入流
     * @param isHeader  是否要跳过表头
     * @param headerCount  表头占用行数
     * @return 返回一个字符串数组List
     */
    public static Map<String, List<String[]>> readXLSRecords1(InputStream inputStream, boolean isHeader,
        int headerCount)
    {
        try (HSSFWorkbook wbs = new HSSFWorkbook(inputStream);)
        {
            Map<String, List<String[]>> records = new HashMap<>();
            int sheetNum = wbs.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++)
            {
                HSSFSheet childSheet = wbs.getSheetAt(i);
                List<String[]> poiList = readXLSSheet(isHeader, headerCount, childSheet);
                records.put(childSheet.getSheetName(), poiList);
            }
            
            return records;
        }
        catch (Exception e)
        {
            LoggerManager.error(PoiUtils.class, e, PARSE_ERROR);
            return new HashMap<>();
        }
    }
    
    /**
     * 解析EXCEL2003文件流
     * 如果一行记录的行中或行尾出现空格，POI工具类可能会跳过空格不做处理，所以默认第一行是表头，所有待解析的记录都以表头为准
     * @param inputStream  输入流
     * @param isHeader  是否要跳过表头
     * @param headerCount  表头占用行数
     * @return 返回一个字符串数组List
     */
    public static Map<String, List<String[]>> readXLSRecords(InputStream inputStream, boolean isHeader, int headerCount)
    {
        Map<String, List<String[]>> records = new HashMap<>();
        List<String[]> poiList = null;
        HSSFWorkbook wbs = null;
        try
        {
            wbs = new HSSFWorkbook(inputStream);
            int sheetNum = wbs.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++)
            {
                HSSFSheet childSheet = wbs.getSheetAt(i);
                poiList = readXLSSheet(isHeader, headerCount, childSheet);
                records.put(childSheet.getSheetName(), poiList);
            }
        }
        catch (Exception e)
        {
            LoggerManager.error(PoiUtils.class, e, "excel解析异常");
            return null;
        }
        finally
        {
            try
            {
                if (wbs != null)
                {
                    wbs.close();
                }
            }
            catch (IOException e)
            {
                LoggerManager.error(PoiUtils.class, e, "excel关闭异常");
            }
        }
        return records;
    }
    
    /**
     * 读取sheet
     * @param isHeader
     * @param headerCount
     * @param childSheet
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static List<String[]> readXLSSheet(boolean isHeader, int headerCount, HSSFSheet childSheet)
    {
        List<String[]> poiList = new ArrayList<>();
        //获取表头
        int begin = childSheet.getFirstRowNum();
        HSSFRow firstRow = childSheet.getRow(begin);
        int cellTotal = firstRow.getPhysicalNumberOfCells();
        //是否跳过表头解析数据
        if (isHeader)
        {
            begin += headerCount;
        }
        //逐行获取单元格数据
        for (int i = begin; i <= childSheet.getLastRowNum(); i++)
        {
            //获取行数据
            HSSFRow row = childSheet.getRow(i);
            if (null != row)
            {
                String[] cells = getRowData(cellTotal, row);
                //判断是否空行  功能：过滤空行情况
                if (CollectionUtility.isNotEmpty(cells))
                {
                    poiList.add(cells);
                }
            }
        }
        return poiList;
    }
    
    /**
     * 解析EXCEL2007文件流
     * 如果一行记录的行中或行尾出现空格，POI工具类可能会跳过空格不做处理，所以默认第一行是表头，所有待解析的记录都以表头为准
     * 该处理方法中，表头对应都占用一行
     * @param inputStream 输入流
     * @param isHeader 是否要跳过表头
     * @param headerCount 表头占用行数
     * @return 返回一个字符串数组List
     */
    public static Map<String, List<String[]>> readXLSXRecords(InputStream inputStream, boolean isHeader,
        int headerCount)
    {
        try (XSSFWorkbook wbs = new XSSFWorkbook(inputStream))
        {
            Map<String, List<String[]>> records = new HashMap<>();
            int sheetNum = wbs.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++)
            {
                XSSFSheet childSheet = wbs.getSheetAt(i);
                List<String[]> poiList = readXLSXSheet(isHeader, headerCount, childSheet);
                records.put(childSheet.getSheetName(), poiList);
            }
            return records;
        }
        catch (Exception e)
        {
            LoggerManager.error(PoiUtils.class, e, PARSE_ERROR);
            return new HashMap<>();
        }
    }
    
    /**
     * 读取sheet
     * @param isHeader
     * @param headerCount
     * @param childSheet
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static List<String[]> readXLSXSheet(boolean isHeader, int headerCount, XSSFSheet childSheet)
    {
        List<String[]> poiList = new ArrayList<>();
        //获取表头
        int begin = childSheet.getFirstRowNum();
        XSSFRow firstRow = childSheet.getRow(begin);
        int cellTotal = firstRow.getPhysicalNumberOfCells();
        
        //是否跳过表头解析数据
        if (isHeader)
        {
            begin += headerCount;
        }
        for (int i = begin; i <= childSheet.getLastRowNum(); i++)
        {
            //一行
            XSSFRow row = childSheet.getRow(i);
            if (null != row)
            {
                String[] cells = getRowData(cellTotal, row);
                //判断是否空行  功能：过滤空行情况
                if (CollectionUtility.isNotEmpty(cells))
                {
                    poiList.add(cells);
                }
            }
        }
        
        return poiList;
    }
    
    /**
     * 获取XLS行数据
     * @param poiList
     * @param cellTotal
     * @param row
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static String[] getRowData(int cellTotal, HSSFRow row)
    {
        String[] cells = new String[cellTotal];
        for (int k = 0; k < cellTotal; k++)
        {
            HSSFCell cell = row.getCell(k);
            String tempValue = getStringXLSCellValue(cell);
            if (!StringUtility.isEmpty(tempValue.trim()))
            {
                return new String[] {};
            }
            cells[k] = getStringXLSCellValue(cell);
        }
        return cells;
    }
    
    /**
     * 获取XLS行数据
     * @param poiList
     * @param cellTotal
     * @param row
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static String[] getRowData(int cellTotal, XSSFRow row)
    {
        //是否空行标志 true:空行 false:非空行
        boolean flagEmptyRow = true;
        String[] cells = new String[cellTotal];
        for (int k = 0; k < cellTotal; k++)
        {
            XSSFCell cell = row.getCell(k);
            String tempValue = getStringXLSXCellValue(cell);
            if (!StringUtility.isEmpty(tempValue.trim()))
            {
                flagEmptyRow = false;
            }
            cells[k] = getStringXLSXCellValue(cell);
        }
        //判断是否空行  功能：过滤空行情况
        if (flagEmptyRow)
        {
            return new String[] {};
        }
        return cells;
    }
    
    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringXLSCellValue(HSSFCell cell)
    {
        if (cell == null)
        {
            return StringUtils.EMPTY;
        }
        
        //将数值型参数转成文本格式，该算法不能保证1.00这种类型数值的精确度
        DecimalFormat df = (DecimalFormat)NumberFormat.getPercentInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("0");
        df.applyPattern(sb.toString());
        
        String strCell;
        switch (cell.getCellType())
        {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                strCell = StringUtils.EMPTY;
                break;
        }
        return strCell;
    }
    
    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringXLSXCellValue(XSSFCell cell)
    {
        if (cell == null)
        {
            return StringUtils.EMPTY;
        }
        
        //将数值型参数转成文本格式，该算法不能保证1.00这种类型数值的精确度
        DecimalFormat df = (DecimalFormat)NumberFormat.getPercentInstance();
        StringBuffer sb = new StringBuffer();
        sb.append("0");
        df.applyPattern(sb.toString());
        
        String strCell;
        switch (cell.getCellType())
        {
            case XSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                double value = cell.getNumericCellValue();
                while (Double.parseDouble(df.format(value)) != value)
                {
                    if ("0".equals(sb.toString()))
                    {
                        sb.append(".0");
                    }
                    else
                    {
                        sb.append("0");
                    }
                    df.applyPattern(sb.toString());
                }
                strCell = df.format(value);
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                strCell = StringUtils.EMPTY;
                break;
        }
        return strCell;
    }
    
    /**
     * excel路劲检查
     * @param path
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static boolean isNotExcelPath(String path)
    {
        if (StringUtility.isEmpty(path))
        {
            return true;
        }
        return !path.endsWith(XLS_LOWER_CASE) && !path.endsWith(XLSX_LOWER_CASE) && !path.endsWith(XLS_UPPER_CASE)
            && !path.endsWith(XLSX_UPPER_CASE);
    }
}