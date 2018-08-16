package com.tenkent.infrastructure.utility.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tenkent.infrastructure.exception.InfrastructureException;
import com.tenkent.infrastructure.log.LoggerManager;
import com.tenkent.infrastructure.utility.CollectionUtility;
import com.tenkent.infrastructure.utility.MapUtility;

/**
 * EXCEL导出
 * 
 * @author  秦正亮
 * @version  [版本号, 2016年11月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelExportUtil
{
    /**
     * 私有构造函数
     */
    private ExcelExportUtil()
    {
    }
    
    /**
     * 构造EXCEL,并响应客户端
     * <功能详细描述>
     * @param listValues
     * @param fileName
     * @param sheetName
     * @param cellTitle
     * @param cellValue
     * @param res
     * @see [类、类#方法、类#成员]
     */
    public static void buildXSLXExcel(List<Map<String, Object>> listValues, String fileName, String sheetName,
        String[] cellTitle, String[] cellValue, HttpServletResponse res)
    {
        byte[] bytes = buildXSLXExcelWithSheetName(listValues, sheetName, cellTitle, cellValue);
        if (0 == bytes.length)
        {
            return;
        }
        writeDocResponse(res, bytes, fileName);
    }
    
    public static void buildXSLXExcel(ExcelParam<ExportTypeUtility> data, HttpServletResponse res)
    {
        byte[] bytes = buildXSLXExcelWithSpecialColumn(data);
        if (0 == bytes.length)
        {
            return;
        }
        writeDocResponse(res, bytes, data.getFileName());
    }
    
    /**
     * 工作部转换字节流
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static byte[] buildXSLXExcelWithSpecialColumn(ExcelParam<ExportTypeUtility> data)
    {
        try (
            XSSFWorkbook workBook = createExcelBook(data.getListValues(),
                data.getFileName(),
                data.getCellTitle(),
                data.getCellValue(),
                data.getSpecialColumns());
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();)
        {
            workBook.write(outStream);
            outStream.flush();
            return outStream.toByteArray();
        }
        catch (Exception e)
        {
            LoggerManager.error(ExcelExportUtil.class, e, "文件导出发生异常！");
            return new byte[0];
        }
    }
    
    /**
     * 构建EXCEL
     * <功能详细描述>
     * @param listValues
     * @param response
     * @param fileName
     * @param sheetName
     * @param cellTitle
     * @param cellValue
     * @see [类、类#方法、类#成员]
     */
    public static byte[] buildXSLXExcelWithSheetName(List<Map<String, Object>> listValues, String sheetName,
        String[] cellTitle, String[] cellValue)
    {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            XSSFWorkbook workBook = createExcelBook(listValues, sheetName, cellTitle, cellValue, null);)
        {
            workBook.write(outStream);
            outStream.flush();
            return outStream.toByteArray();
        }
        catch (Exception e)
        {
            LoggerManager.error(ExcelExportUtil.class, e, "文件导出发生异常！");
            return new byte[0];
        }
    }
    
    /**
     * 生成WorkBook
     * <功能详细描述>
     * @param listValues
     * @param sheetName
     * @param cellTitle
     * @param cellValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static XSSFWorkbook createExcelBook(List<Map<String, Object>> listValues, String sheetName,
        String[] cellTitle, String[] cellValue, Map<Integer, ExportTypeUtility> specialColumns)
    {
        //创建工作薄
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet();
        
        //工作簿名称
        workBook.setSheetName(0, sheetName);
        
        //字体设置
        XSSFFont font = workBook.createFont();
        font.setColor(XSSFFont.COLOR_NORMAL);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        
        //创建格式
        XSSFCellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        //创建第一行标题
        XSSFRow titleRow = sheet.createRow((short)0);
        for (int i = 0; i < cellTitle.length; i++)
        {
            //创建第1行标题单元格
            sheet.setColumnWidth(i, 20 * 256);
            XSSFCell cell = titleRow.createCell(i, 0);
            cell.setCellStyle(cellStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(cellTitle[i]);
        }
        
        //第二行开始写入数据
        if (CollectionUtility.isNotEmpty(listValues))
        {
            //创建格式
            XSSFCellStyle style = workBook.createCellStyle();
            //遍历列表数据
            for (int i = 0; i < listValues.size(); i++)
            {
                XSSFRow row = sheet.createRow((short)i + 1);
                setRow(cellTitle, cellValue, specialColumns, workBook, sheet, style, listValues.get(i), i, row);
            }
        }
        return workBook;
    }
    
    private static void setRow(String[] cellTitle, String[] cellValue, Map<Integer, ExportTypeUtility> specialColumns,
        XSSFWorkbook workBook, XSSFSheet sheet, XSSFCellStyle style, Map<String, Object> map, int line, XSSFRow row)
    {
        ExportTypeUtility exportTypeUtility;
        for (int j = 0; j < cellTitle.length; j++)
        {
            if (MapUtility.isNotEmpty(specialColumns) && specialColumns.containsKey(j))
            {
                row.setHeight((short)2000);
                exportTypeUtility = specialColumns.get(j);
                exportTypeUtility.generateColumn(workBook, sheet, line, j, MapUtility.getMapString(map, cellValue[j]));
            }
            else
            {
                // 在上面行索引0的位置创建单元格
                XSSFCell cell = row.createCell(j, 0);
                // 定义单元格为字符串类型
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                //取出列表值
                cell.setCellValue(MapUtility.getMapString(map, cellValue[j]));
                style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                cell.setCellStyle(style);
            }
        }
    }
    
    /**
     * 文件拷贝
     * <功能详细描述>
     * @param fromPath
     * @param toPath
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public static void copyFile(String fromPath, String toPath)
    {
        try (FileInputStream fis = new FileInputStream(fromPath);
            BufferedInputStream bufis = new BufferedInputStream(fis);
            
            FileOutputStream fos = new FileOutputStream(toPath);
            BufferedOutputStream bufos = new BufferedOutputStream(fos);)
        {
            int len = 0;
            while ((len = bufis.read()) != -1)
            {
                bufos.write(len);
            }
        }
        catch (Exception e)
        {
            LoggerManager.error(ExcelExportUtil.class, e, "文件移动异常！");
        }
    }
    
    /**
     * 文件压缩
     * <功能详细描述>
     * @param folderPath
     * @param zipFileName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String compressFolder(String folderPath, String zipFileName)
    {
        String zipFilePath =
            folderPath.substring(0, folderPath.substring(0, folderPath.length() - 1).lastIndexOf('/') + 1);
        File zipFile = new File(zipFilePath + zipFileName + ".zip");
        
        try (ZipArchiveOutputStream zipOutput = (ZipArchiveOutputStream)new ArchiveStreamFactory()
            .createArchiveOutputStream(ArchiveStreamFactory.ZIP, new FileOutputStream(zipFile)))
        {
            zipOutput.setEncoding("UTF-8");
            zipOutput.setUseZip64(Zip64Mode.AsNeeded);
            File[] files = new File(folderPath).listFiles();
            for (File file : files)
            {
                zipPutEntry(zipOutput, file);
            }
            zipOutput.finish();
            zipOutput.close();
            return zipFile.getPath();
        }
        catch (Exception e)
        {
            throw new InfrastructureException("compress folder exception", e);
        }
    }
    
    private static void zipPutEntry(ZipArchiveOutputStream zipOutput, File file)
    {
        try (InputStream in = new FileInputStream(file);)
        {
            ZipArchiveEntry entry = new ZipArchiveEntry(file, file.getName());
            zipOutput.putArchiveEntry(entry);
            IOUtils.copy(in, zipOutput);
            zipOutput.closeArchiveEntry();
        }
        catch (Exception e)
        {
            throw new InfrastructureException("compress folder exception", e);
        }
    }
    
    /**
     * 输出文件到客户端
     * <功能详细描述>
     * @param rsp http响应对象
     * @param bytes
     * @param fileName
     * @see [类、类#方法、类#成员]
     */
    public static void writeDocResponse(HttpServletResponse rsp, byte[] bytes, String fileName)
    {
        try (OutputStream out = rsp.getOutputStream();)
        {
            //扩展名获取ContentType
            rsp.setContentType("application/vnd.ms-excel");
            String contentHeader = "attachment; filename=\"" + fileName + "\"";
            rsp.setHeader("Content-disposition", contentHeader);
            //文件写入
            out.write(bytes, 0, bytes.length);
        }
        catch (Exception e)
        {
            LoggerManager.error(ExcelExportUtil.class, e, "文件写入流发生异常！");
        }
    }
    
    /**
     * 方法名称： getFileExt 内容摘要： 获取文档扩展名
     * 
     * @param fileName
     *            文件名
     * @return 文档扩展名
     */
    public static String getFileExt(String fileName)
    {
        if (StringUtils.isEmpty(fileName))
        {
            return StringUtils.EMPTY;
        }
        else
        {
            return StringUtils.substringAfterLast(fileName, ".");
        }
    }
    
    /**
     * 方法名称： getDocContentType 内容摘要： 获取文档类型
     * 
     * @param fileExt
     *            文档扩展名
     * @return 文档类型
     * @throws Exception
     */
    public static String getDocContentType(String fileExt)
    {
        String contentType = StringUtils.EMPTY;
        if ("doc".equals(fileExt))
        {
            contentType = "application/msword";
        }
        else if ("xls".equals(fileExt))
        {
            contentType = "application/vnd.ms-excel";
        }
        else if ("pdf".equals(fileExt))
        {
            contentType = "application/pdf";
        }
        else if ("jpg".equals(fileExt))
        {
            contentType = "image/jpeg";
        }
        else if ("gif".equals(fileExt))
        {
            contentType = "image/gif";
        }
        else if ("bmp".equals(fileExt))
        {
            contentType = "image/bmp";
        }
        else if ("txt".equals(fileExt))
        {
            contentType = "text/plain";
        }
        return contentType;
    }
}
