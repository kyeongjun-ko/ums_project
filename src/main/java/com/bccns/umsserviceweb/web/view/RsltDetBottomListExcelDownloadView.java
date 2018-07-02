package com.bccns.umsserviceweb.web.view;

import java.io.IOException;
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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bccns.umsserviceweb.mgr.vo.RsltDetBottomVO;

public class RsltDetBottomListExcelDownloadView extends AbstractExcelView {

    //private static final Logger logger = LoggerFactory.getLogger(AvailExcelDownloadView.class);
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String excelViewName = model.get("viewName").toString();
        excelViewName = new String(excelViewName.getBytes("MS949"),"ISO8859_1");
 
        @SuppressWarnings("unchecked")
        List<RsltDetBottomVO> availList = (List<RsltDetBottomVO>) model.get("rsltDetBottomlist");
        
        createExcel(workbook, excelViewName, availList);
        
        response.setContentType("Application/Msexcel");
        response.setHeader("Content-Disposition", "Attachment; Filename=" + excelViewName);
        
    }

    private void createExcel(HSSFWorkbook workbook, String excelViewName, List<RsltDetBottomVO> availList) throws InvalidFormatException, IOException {
        
        // create a new sheet
        HSSFSheet eSheet = workbook.createSheet("Avail List");

        //header title 
        CellStyle cellStyle = createHeaderStyle(workbook);
        createHeaderRow(eSheet, cellStyle);
        
        //cell 
        cellStyle = createCellStyle(workbook);
     
        int rowNum = 3;             //title 3줄을 포함하여 컨테츠는 3번째 줄부터 시작함
        for(RsltDetBottomVO content : availList) {
            
            HSSFRow row = eSheet.createRow(rowNum++);            
            //
            createCell(row, cellStyle, content);
        }
    }
    
    private void createCell(HSSFRow row, CellStyle cellStyle, RsltDetBottomVO vo) {
        int cellNum = 0;
        
        createCell(row, cellStyle, cellNum++, vo.getRowNumber());
        createCell(row, cellStyle, cellNum++, vo.getDestName());
        createCell(row, cellStyle, cellNum++, vo.getPhoneNumber());
        createCell(row, cellStyle, cellNum++, vo.getSendDate());
        createCell(row, cellStyle, cellNum++, vo.getResult());
        createCell(row, cellStyle, cellNum++, vo.getTcsResult());
        createCell(row, cellStyle, cellNum++, vo.getResultDesc());
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
    
    private CellStyle createHeaderStyle(HSSFWorkbook workbook) throws InvalidFormatException, IOException {

        CellStyle cellStyle = workbook.createCellStyle();
        
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        cellStyle.setWrapText(true);
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
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        
        return cellStyle;
    }
    
    private void createHeaderRow(HSSFSheet sheet, CellStyle cellStyle) {
        
        List<AvailExcelHeader> headerList = new ArrayList<AvailExcelHeader>();
        headerList.add(new AvailExcelHeader(0, 0, 2, 0, "순번"));
        headerList.add(new AvailExcelHeader(0, 1, 2, 0, "받는 사람"));
        headerList.add(new AvailExcelHeader(0, 2, 2, 0, "수신전화번호"));
        headerList.add(new AvailExcelHeader(0, 3, 2, 0, "전송일시"));
        headerList.add(new AvailExcelHeader(0, 4, 2, 0, "결과코드"));
        headerList.add(new AvailExcelHeader(0, 5, 2, 0, "결과상세코드"));
        headerList.add(new AvailExcelHeader(0, 6, 2, 0, "결과상세코드명"));
            
        createHeaderCell(sheet, cellStyle, headerList);
        
    }

    private void createHeaderCell(HSSFSheet sheet, CellStyle cellStyle, List<AvailExcelHeader> headerList) {
        
        for(AvailExcelHeader header : headerList) {
            HSSFRow row = sheet.getRow(header.getRowNum());
            if(row == null) {
                row = sheet.createRow(header.getRowNum());
            }
            HSSFCell cell = row.createCell(header.getCellNum());
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(header.getTitle());
            //셀병합
            sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum()+header.getRowHeight(), header.getCellNum(), header.getCellNum()+header.getCellWidth()));
            sheet.autoSizeColumn(header.getCellNum(), true);
            sheet.setColumnWidth(header.getCellNum(), sheet.getColumnWidth(header.getCellNum())+512);
            
        }
        
    }

    
    class AvailExcelHeader {
        String title;
        int rowNum;
        int cellNum;
        int rowHeight;
        int cellWidth;
        
        public AvailExcelHeader(int rowNum, int cellNum, int rowHeight, int cellWidth, String title) {
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
