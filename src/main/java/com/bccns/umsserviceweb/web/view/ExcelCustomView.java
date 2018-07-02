package com.bccns.umsserviceweb.web.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelCustomView extends AbstractExcelView {
    
    private static final Logger logger = LoggerFactory.getLogger(ExcelCustomView.class);
    
    /*
     * Map 객체에 viewName 은 엑셀파일의 이름.
     * label 은 표현 하고자 하는 셀의 Full 네임
     * label key 는 각 셀에 표현 하고자 하는 데이터의 key
     * dataList 는 각 셀에 표현 하고자 하는 데이터.
     * (non-Javadoc)
     * @see
     * org.springframework.web.servlet.view.document.AbstractExcelView#buildExcelDocument(java.util
     * .Map, org.apache.poi.hssf.usermodel.HSSFWorkbook, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub

        String excelViewName = model.get("viewName").toString();

        @SuppressWarnings("unchecked")
        List<Map> dataList = (List<Map>) model.get("dataList");
        List<String> label = (List<String>) model.get("label");
        List<String> labelKey = (List<String>) model.get("labelKey");

        createExcel(workbook, excelViewName, label, labelKey, dataList);

        response.setContentType("Application/Msexcel");
        response.setHeader("Content-Disposition", "ATTachment; Filename=" + excelViewName);

    }

    private void createExcel(HSSFWorkbook workbook, String excelViewName, List<String> label,
            List<String> labelKey, List<Map> dataList) {

        // create a new sheet
        HSSFSheet eSheet = workbook.createSheet(excelViewName);

        HSSFRow eRow = eSheet.createRow(0);

        // Create Hearder Row
        Map<String, CellStyle> cellStyles = createStyle(workbook);
        createHeaderRow(eRow, label, cellStyles.get("label"));

        for ( int rownum = 1 ; rownum < dataList.size() + 1 ; rownum++ ) {
            eRow = eSheet.createRow(rownum);

            Map<String, Object> data = dataList.get(rownum - 1);

            createCell(eRow, labelKey, data, cellStyles.get("data"));

        }

    }

    private void createHeaderRow(HSSFRow eRow, List<String> excelHeader, CellStyle cellStyle) {
        createCell(eRow, excelHeader, cellStyle);
    }

    private void createCell(HSSFRow eRow, List<String> cellData, CellStyle cellStyle) {
        HSSFCell cell = null;
        for ( int cellNum = 0 ; cellNum < cellData.size() ; cellNum++ ) {
            cell = eRow.createCell(cellNum);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(cellData.get(cellNum));
        }
    }

    private void createCell(HSSFRow eRow, List<String> keyValues, Map<String, Object> cellData,
            CellStyle cellStyle) {
        HSSFCell cell = null;
        for ( int cellNum = 0 ; cellNum < keyValues.size() ; cellNum++ ) {
            cell = eRow.createCell(cellNum);
            cell.setCellStyle(cellStyle);
            if (logger.isDebugEnabled()) {
                logger.debug(" Cell Data Key Value : " + keyValues.get(cellNum));
            }
            cell.setCellValue(objToString(cellData.get(keyValues.get(cellNum))));
        }
    }

    private Map<String, CellStyle> createStyle(HSSFWorkbook workbook) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        CellStyle cellStyle = null;

        cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        cellStyle.setFont(font);
        cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
        styles.put("label", cellStyle);

        cellStyle = workbook.createCellStyle();
        Font dataFont = workbook.createFont();
        dataFont.setFontHeightInPoints((short) 10);

        cellStyle.setFont(dataFont);
        cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
        styles.put("data", cellStyle);

        return styles;
    }

    public static String objToString(final Object object) { 
        if ( object == null ) {
            return "";
        }

        try {
            final StringBuilder sb = new StringBuilder(100);

            final Class clazz = object.getClass();
                
            if ( clazz.isPrimitive()
                    || clazz.getPackage().getName().equals("java.lang")
                    || clazz.equals(Date.class) ) {
                // we have a primitive, String or similar:
                if ( clazz == Long.class || clazz == long.class
                        || clazz == Integer.class || clazz == int.class
                        || clazz == Short.class || clazz == short.class
                        || clazz == Byte.class || clazz == byte.class
                        || clazz == Double.class || clazz == double.class
                        || clazz == Float.class || clazz == float.class || clazz == String.class ) {
                    // we have a number, dont use ''
                    sb.append(object);
                }
            }
            
            return sb.toString();
        } catch ( final Exception e ) {
            // this should never happen
            throw new RuntimeException(e);
        }
    }
}