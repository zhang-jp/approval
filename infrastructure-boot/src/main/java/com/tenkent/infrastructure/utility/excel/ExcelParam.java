package com.tenkent.infrastructure.utility.excel;

import java.util.List;
import java.util.Map;

import com.tenkent.infrastructure.utility.MapUtility;

public abstract class ExcelParam<T>
{
    /**
     * excel文件名
     */
    private String fileName;
    
    /**
     * sheet名
     */
    private String sheetName;
    
    /**
     * excel标题行
     */
    private String[] cellTitle;
    
    /**
     * excel内容
     */
    private List<Map<String, Object>> listValues;
    
    /**
     * excel内容Map对应的key
     */
    private String[] cellValue;
    
    private Map<Integer, T> specialColumns;
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public String getSheetName()
    {
        return sheetName;
    }
    
    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }
    
    public String[] getCellTitle()
    {
        return cellTitle;
    }
    
    public void setCellTitle(String[] cellTitle)
    {
        this.cellTitle = cellTitle;
    }
    
    public List<Map<String, Object>> getListValues()
    {
        return listValues;
    }
    
    public void setListValues(List<Map<String, Object>> listValues)
    {
        this.listValues = listValues;
    }
    
    public String[] getCellValue()
    {
        return cellValue;
    }
    
    public void setCellValue(String[] cellValue)
    {
        this.cellValue = cellValue;
    }
    
    public Map<Integer, T> getSpecialColumns()
    {
        return MapUtility.isEmpty(specialColumns) ? formSpecialColumns() : specialColumns;
    }
    
    public void setSpecialColumns(Map<Integer, T> specialColumns)
    {
        this.specialColumns = specialColumns;
    }
    
    /**
     * 构造特殊列
     * @return
     */
    public abstract Map<Integer, T> formSpecialColumns();
}
