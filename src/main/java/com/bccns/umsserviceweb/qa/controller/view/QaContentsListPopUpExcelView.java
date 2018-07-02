package com.bccns.umsserviceweb.qa.controller.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bccns.umsserviceweb.qa.vo.QaVO;

public class QaContentsListPopUpExcelView extends AbstractExcelView {

    //private static final Logger logger = LoggerFactory.getLogger(QaContentsListPopUpExcelView.class);
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String excelViewName = model.get("viewName").toString();
        //excelViewName = new String(excelViewName.getBytes("KSC5601"),"8859_1");
        excelViewName = new String(excelViewName.getBytes("MS949"),"ISO8859_1");

        List<QaVO> qaContList = (List<QaVO>) model.get("qaContList");

        createExcel(workbook, excelViewName, qaContList);
        
        response.setContentType("Application/Msexcel");
        response.setHeader("Content-Disposition", "ATTachment; Filename=" + excelViewName);
        
    }

    private void createExcel(HSSFWorkbook workbook, String excelViewName, List<QaVO> list) {
        
        // create a new sheet
        HSSFSheet eSheet = workbook.createSheet(excelViewName);

        //header title 
        CellStyle cellStyle = createHeaderStyle(workbook);
        createHeaderRow(eSheet, cellStyle);
        
        //cell 
        cellStyle = createCellStyle(workbook);
     
        int rowNum = 1; //title 1줄을 포함하여 컨테츠는 2번째 줄부터 시작함
        for(QaVO content : list) {
            HSSFRow row = eSheet.createRow(rowNum++);
            createCell(row, cellStyle, content);
        }
    }
    
    private void createCell(HSSFRow row, CellStyle cellStyle, QaVO vo) {
        
        int cellNum = 0;
        createCell(row, cellStyle, cellNum++, vo.getContTitle());
        createCell(row, cellStyle, cellNum++, vo.getOrderId());
        createCell(row, cellStyle, cellNum++, vo.getCid());
        createCell(row, cellStyle, cellNum++, vo.getContTypeNm());
        createCell(row, cellStyle, cellNum++, vo.getCpNm());
        createCell(row, cellStyle, cellNum++, vo.getEncodeVendorNm());
        createCell(row, cellStyle, cellNum++, vo.getTerritoryNm());
    }
    
    private void createCell(HSSFRow row, CellStyle style, int cellNum, Object value) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellStyle(style);
        setValue(cell, value);
    }
 
    private void setValue(HSSFCell cell, Object value) {
        
        if(value == null) {
            return;
        }
        
        if(value instanceof Integer) {
            cell.setCellValue((Integer)value);
        } else if(value instanceof Boolean) {
            cell.setCellValue((Boolean)value);
        } else if(value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if(value instanceof Double) {
            cell.setCellValue((Double)value);
        } else if(value instanceof String){
            cell.setCellValue(new HSSFRichTextString(value.toString()));
        } else {
            cell.setCellValue(value.toString());
        }               
    }
    
    private CellStyle createHeaderStyle(HSSFWorkbook workbook) {

        CellStyle cellStyle = workbook.createCellStyle();
        
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        cellStyle.setFont(font);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        //cellStyle.setBorderBottom(cellStyle.BORDER_THIN);
        //cellStyle.setBorderLeft(cellStyle.BORDER_THIN);
        //cellStyle.setBorderRight(cellStyle.BORDER_THIN);
        //cellStyle.setBorderTop(cellStyle.BORDER_THIN);
        cellStyle.setFillBackgroundColor(new HSSFColor.AUTOMATIC().getIndex());
        cellStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        
        return cellStyle;
    }
    
    private CellStyle createCellStyle(HSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        
        cellStyle.setFont(font);
        cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
        
        return cellStyle;
    }
    
    private void createHeaderRow(HSSFSheet sheet, CellStyle cellStyle) {
        
        List<ExcelHeader> headerList = new ArrayList<ExcelHeader>();
        headerList.add(new ExcelHeader(0, 0, 0, 0, "Title"));
        headerList.add(new ExcelHeader(0, 1, 0, 0, "Integration ID"));
        headerList.add(new ExcelHeader(0, 2, 0, 0, "CID"));
        headerList.add(new ExcelHeader(0, 3, 0, 0, "Type"));
        headerList.add(new ExcelHeader(0, 4, 0, 0, "CP"));
        headerList.add(new ExcelHeader(0, 5, 0, 0, "Encoding House"));
        headerList.add(new ExcelHeader(0, 6, 0, 0, "Territory"));
            
        createHeaderCell(sheet, cellStyle, headerList);
        
    }

    private void createHeaderCell(HSSFSheet sheet, CellStyle cellStyle, List<ExcelHeader> headerList) {
        
        for(ExcelHeader header : headerList) {
            HSSFRow row = sheet.getRow(header.getRowNum());
            if(row == null) {
                row = sheet.createRow(header.getRowNum());
            }
            HSSFCell cell = row.createCell(header.getCellNum());
            cell.setCellStyle(cellStyle);
            cell.setCellValue(header.getTitle());
            //셀병합
            sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum()+header.getRowHeight(), header.getCellNum(), header.getCellNum()+header.getCellWidth()));
            sheet.autoSizeColumn(header.getCellNum(), true);
            
        }
        
    }

    
    class ExcelHeader {
        String title;
        int rowNum;
        int cellNum;
        int rowHeight;
        int cellWidth;
        
        public ExcelHeader(int rowNum, int cellNum, int rowHeight, int cellWidth, String title) {
            this.title = title;
            this.rowNum = rowNum;
            this.cellNum = cellNum;
            this.rowHeight = rowHeight;
            this.cellWidth = cellWidth;
        }

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public int getRowNum() {
            return rowNum;
        }
        public void setRowNum(int rowNum) {
            this.rowNum = rowNum;
        }
        public int getCellNum() {
            return cellNum;
        }
        public void setCellNum(int cellNum) {
            this.cellNum = cellNum;
        }
        public int getRowHeight() {
            return rowHeight;
        }
        public void setRowHeight(int rowHeight) {
            this.rowHeight = rowHeight;
        }
        public int getCellWidth() {
            return cellWidth;
        }
        public void setCellWidth(int cellWidth) {
            this.cellWidth = cellWidth;
        }     
    }
}
