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

import com.bccns.umsserviceweb.qa.vo.QaItemVO;
import com.bccns.umsserviceweb.qa.vo.QaVO;
import com.bccns.umsserviceweb.qa.vo.SearchQaVO;

public class QaRequestListExcelView extends AbstractExcelView {

    //private static final Logger logger = LoggerFactory.getLogger(QaRequestListExcelView.class);
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String excelViewName = model.get("viewName").toString();
        excelViewName = new String(excelViewName.getBytes("MS949"),"ISO8859_1");
        
        List<QaVO> qaList = (List<QaVO>) model.get("qaList");
        
        SearchQaVO searchQaVO = (SearchQaVO) model.get("searchQaVO");

        createExcel(workbook, excelViewName, qaList, searchQaVO);
        
        response.setContentType("Application/Msexcel");
        response.setHeader("Content-Disposition", "ATTachment; Filename=" + excelViewName);
        
    }

    private void createExcel(HSSFWorkbook workbook, String excelViewName, List<QaVO> list, SearchQaVO searchQaVO) {
        
        // create a new sheet
        HSSFSheet eSheet = workbook.createSheet(excelViewName);

        //header title 
        CellStyle cellStyle = createHeaderStyle(workbook);
        createHeaderRow(eSheet, cellStyle);
        
        //cell 
        cellStyle = createCellStyle(workbook);
     
        int rowNum = 4; //title 4줄을 포함하여 컨테츠는 5번째 줄부터 시작함
        int No = 1;
        for(QaVO content : list) {
            HSSFRow row = eSheet.createRow(rowNum++);
            createCell(row, cellStyle, content, No, searchQaVO);
            No++;
        }

    }
    
    private void createCell(HSSFRow row, CellStyle cellStyle, QaVO vo, int No, SearchQaVO searchQaVO) {
        
        int cellNum = 0;
        String var = "";
        String R_TRAILER = "", R_H264_SD = "", R_H264_HD = "", R_H264_FHD = "", R_HEVC_ST_SD = "", R_HEVC_ST_HD = "", R_HEVC_ST_FHD = "",
                R_HEVC_DL_SD = "", R_HEVC_DL_HD = "", R_HEVC_DL_FHD = "", R_HOME_SYNC = "", R_TV_SD = "", R_TV_HD = "", R_TV_FHD = "", R_PC_SD = "", R_PC_HD = "", R_PC_FHD = ""
                , M_ALL = "", M_Title = "",  M_Poster = "", M_Genre = "", M_Directors ="",  M_Actors ="", M_Synopsis = "", M_AudioLang = "", M_SubTitles = "", M_RunTime =""
                , M_ReleaseYD = "", M_Rating = "", M_Price = "";
        if("compl".equals(searchQaVO.getQaReqComplFlag())  
            && vo.getItemList() != null && vo.getItemList().size() > 0) {
                for(QaItemVO thisvo : vo.getItemList()) {
                    if("R_TRAILER".equals(thisvo.getItem())) {
                        R_TRAILER = "V_FAIL";
                    }
                    if("R_H264_SD".equals(thisvo.getItem())) {
                        R_H264_SD = "V_FAIL";
                    }
                    if("R_H264_HD".equals(thisvo.getItem())) {
                        R_H264_HD = "V_FAIL";
                    }
                    if("R_H264_FHD".equals(thisvo.getItem())) {
                        R_H264_FHD = "V_FAIL";
                    }
                    if("R_HEVC_ST_SD".equals(thisvo.getItem())) {
                        R_HEVC_ST_SD = "V_FAIL";
                    }
                    if("R_HEVC_ST_HD".equals(thisvo.getItem())) {
                        R_HEVC_ST_HD = "V_FAIL";
                    }
                    if("R_HEVC_ST_FHD".equals(thisvo.getItem())) {
                        R_HEVC_ST_FHD = "V_FAIL";
                    }
                    if("R_HEVC_DL_SD".equals(thisvo.getItem())) {
                        R_HEVC_DL_SD = "V_FAIL";
                    }
                    if("R_HEVC_DL_HD".equals(thisvo.getItem())) {
                        R_HEVC_DL_HD = "V_FAIL";
                    }
                    if("R_HEVC_DL_FHD".equals(thisvo.getItem())) {
                        R_HEVC_DL_FHD  = "V_FAIL";
                    }
                    if("R_HOME_SYNC".equals(thisvo.getItem())) {
                        R_HOME_SYNC = "V_FAIL";
                    }
                    if("R_TV_SD".equals(thisvo.getItem())) {
                        R_TV_SD = "V_FAIL";
                    }
                    if("R_TV_HD".equals(thisvo.getItem())) {
                        R_TV_HD = "V_FAIL";
                    }
                    if("R_TV_FHD".equals(thisvo.getItem())) {
                        R_TV_FHD = "V_FAIL";
                    }
                    if("R_PC_SD".equals(thisvo.getItem())) {
                        R_PC_SD = "V_FAIL";
                    }
                    if("R_PC_HD".equals(thisvo.getItem())) {
                        R_PC_HD = "V_FAIL";
                    }
                    if("R_PC_FHD".equals(thisvo.getItem())) {
                        R_PC_FHD = "V_FAIL";
                    }
                    if("R_PC_FHD".equals(thisvo.getItem())) {
                        R_PC_FHD = "V_FAIL";
                    }
                    
                    if("M_ALL".equals(thisvo.getItem())) {
                        M_ALL = "M_FAIL";
                    }
                    if("M_Title".equals(thisvo.getItem())) {
                        M_Title  = "M_FAIL";
                    }
                    if("M_Poster".equals(thisvo.getItem())) {
                        M_Poster  = "M_FAIL";
                    }
                    if("M_Genre".equals(thisvo.getItem())) {
                        M_Genre  = "M_FAIL";
                    }
                    if("M_Directors".equals(thisvo.getItem())) {
                        M_Directors  = "M_FAIL";
                    }
                    if("M_Actors".equals(thisvo.getItem())) {
                        M_Actors  = "M_FAIL";
                    }    
                    if("M_Synopsis".equals(thisvo.getItem())) {
                        M_Synopsis  = "M_FAIL";
                    }
                    if("M_AudioLang".equals(thisvo.getItem())) {
                        M_AudioLang  = "M_FAIL";
                    }
                    if("M_SubTitles".equals(thisvo.getItem())) {
                        M_SubTitles  = "M_FAIL";
                    }
                    if("M_RunTime".equals(thisvo.getItem())) {
                        M_RunTime  = "M_FAIL";
                    }
                    if("M_ReleaseYD".equals(thisvo.getItem())) {
                        M_ReleaseYD  = "M_FAIL";
                    }
                    if("M_Rating".equals(thisvo.getItem())) {
                        M_Rating  = "M_FAIL";
                    }
                    if("M_Price".equals(thisvo.getItem())) {
                        M_Price = "M_FAIL";
                    }
                
            }
            
        } 
        
        createCell(row, cellStyle, cellNum++, No);
        createCell(row, cellStyle, cellNum++, vo.getTerritory());
        createCell(row, cellStyle, cellNum++, vo.getOrderId());
        createCell(row, cellStyle, cellNum++, vo.getCid());
        createCell(row, cellStyle, cellNum++, vo.getProductId());
        createCell(row, cellStyle, cellNum++, vo.getContTitle());
        createCell(row, cellStyle, cellNum++, " "); //ep no
        createCell(row, cellStyle, cellNum++, vo.getCpNm());
        createCell(row, cellStyle, cellNum++, vo.getContTypeNm());
        createCell(row, cellStyle, cellNum++, vo.getEst_start_date()); //est start date
        createCell(row, cellStyle, cellNum++, vo.getVod_start_date()); //vod start date
        createCell(row, cellStyle, cellNum++, vo.getReqDate());
        createCell(row, cellStyle, cellNum++, R_TRAILER); //trailer
        createCell(row, cellStyle, cellNum++, R_H264_SD); //h264 sd
        createCell(row, cellStyle, cellNum++, R_H264_HD); //hd
        createCell(row, cellStyle, cellNum++, R_H264_FHD); //fhd
        
        createCell(row, cellStyle, cellNum++, R_HEVC_ST_SD); //stream sd
        createCell(row, cellStyle, cellNum++, R_HEVC_ST_HD); //hd
        createCell(row, cellStyle, cellNum++, R_HEVC_ST_FHD); //fhd
        
        createCell(row, cellStyle, cellNum++, R_HEVC_DL_SD); //down sd
        createCell(row, cellStyle, cellNum++, R_HEVC_DL_HD); //hd
        createCell(row, cellStyle, cellNum++, R_HEVC_DL_FHD); //fhd
        createCell(row, cellStyle, cellNum++, R_HOME_SYNC); //home sync
        
        createCell(row, cellStyle, cellNum++, R_TV_SD); //tv sd
        createCell(row, cellStyle, cellNum++, R_TV_HD); //hd
        createCell(row, cellStyle, cellNum++, R_TV_FHD); //fhd
        
        createCell(row, cellStyle, cellNum++, R_PC_SD); //pc sd
        createCell(row, cellStyle, cellNum++, R_PC_HD); //hd
        createCell(row, cellStyle, cellNum++, R_PC_FHD); //fhd
        
        createCell(row, cellStyle, cellNum++, M_ALL); //meta data
        createCell(row, cellStyle, cellNum++, M_Title); //meta 
        createCell(row, cellStyle, cellNum++, M_Poster); //meta
        createCell(row, cellStyle, cellNum++, M_Genre); //meta
        createCell(row, cellStyle, cellNum++, M_Directors); //meta
        createCell(row, cellStyle, cellNum++, M_Actors); //meta
        createCell(row, cellStyle, cellNum++, M_Synopsis); //meta
        createCell(row, cellStyle, cellNum++, M_AudioLang); //meta
        createCell(row, cellStyle, cellNum++, M_SubTitles); //meta
        createCell(row, cellStyle, cellNum++, M_RunTime); //meta
        createCell(row, cellStyle, cellNum++, M_ReleaseYD); //meta
        createCell(row, cellStyle, cellNum++, M_Rating); //meta
        createCell(row, cellStyle, cellNum++, M_Price); //meta price
        
        createCell(row, cellStyle, cellNum++, vo.getReqDate()); //검증의뢰일
        createCell(row, cellStyle, cellNum++, " "); //검증항목
        createCell(row, cellStyle, cellNum++, vo.getReqCont()); //상세내용
        
        createCell(row, cellStyle, cellNum++, vo.getVerifyDate()); //검증완료일
        
        if("compl".equals(searchQaVO.getQaReqComplFlag())) {
            if("PASS".equals(vo.getQaResult())) {
                var = "Pass";
            } else if("PASS_REVIS".equals(vo.getQaResult())) {
                var = "Pass(Revised)";
            } else {
                var = "FAIL";
            }
            createCell(row, cellStyle, cellNum++, var); //검증결과
        } else {
            createCell(row, cellStyle, cellNum++, " "); //검증결과
        }
        
        
        if("compl".equals(searchQaVO.getQaReqComplFlag())) {
            if(Integer.parseInt(vo.getQaFailMeta()) > 0) {
                var = "MF";
            }
            if(Integer.parseInt(vo.getQaFailVideo()) > 0) {
                var += " VF";
            }
            createCell(row, cellStyle, cellNum++, var); //검증상세
        } else {
            createCell(row, cellStyle, cellNum++, " "); //검증상세
        }
        
        createCell(row, cellStyle, cellNum++, vo.getDescription()); //비고
        
        createCell(row, cellStyle, cellNum++, " "); //배포완료일
        createCell(row, cellStyle, cellNum++, " "); //비고
        
        createCell(row, cellStyle, cellNum++, " "); //정보
        createCell(row, cellStyle, cellNum++, " "); //결과
        createCell(row, cellStyle, cellNum++, " "); //비고
        
        createCell(row, cellStyle, cellNum++, " "); //suspend date
        createCell(row, cellStyle, cellNum++, " "); //비고
        createCell(row, cellStyle, cellNum++, " "); //report date
        
        createCell(row, cellStyle, cellNum++, " "); //인코딩 feed back date
        createCell(row, cellStyle, cellNum++, " "); //인코딩 feed back
        
        /*
        createCell(row, cellStyle, cellNum++, vo.getProdCurState());
        createCell(row, cellStyle, cellNum++, vo.getContTitle());
        createCell(row, cellStyle, cellNum++, vo.getOrderId());
        createCell(row, cellStyle, cellNum++, vo.getCid());
        createCell(row, cellStyle, cellNum++, vo.getContTypeNm());
        createCell(row, cellStyle, cellNum++, vo.getCpNm());
        createCell(row, cellStyle, cellNum++, vo.getEncodeVendor());
        createCell(row, cellStyle, cellNum++, vo.getTerritoryNm());
        if("compl".equals(searchQaVO.getQaReqComplFlag())) {
            createCell(row, cellStyle, cellNum++, vo.getVerifyUserId());
        } else {
            createCell(row, cellStyle, cellNum++, vo.getReqUserId());
        }
        if("compl".equals(searchQaVO.getQaReqComplFlag())) {
            createCell(row, cellStyle, cellNum++, vo.getVerifyDate());
        } else {
            createCell(row, cellStyle, cellNum++, vo.getReqDate());
        }
        */
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
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        
        return cellStyle;
    }
    
    private void createHeaderRow(HSSFSheet sheet, CellStyle cellStyle) {
        
        List<ExcelHeader> headerList = new ArrayList<ExcelHeader>();
        
        headerList.add(new ExcelHeader(0, 0, 0, 41, "Contents Information"));
        headerList.add(new ExcelHeader(1, 0, 2, 0, "NO"));
        headerList.add(new ExcelHeader(1, 1, 2, 0, "Country"));
        headerList.add(new ExcelHeader(1, 2, 2, 0, "Integration ID"));
        headerList.add(new ExcelHeader(1, 3, 2, 0, "Content ID"));
        headerList.add(new ExcelHeader(1, 4, 2, 0, "Product ID"));
        headerList.add(new ExcelHeader(1, 5, 2, 0, "Title"));
        headerList.add(new ExcelHeader(1, 6, 2, 0, "EP NO"));
        headerList.add(new ExcelHeader(1, 7, 2, 0, "CP"));
        headerList.add(new ExcelHeader(1, 8, 2, 0, "Type"));
        headerList.add(new ExcelHeader(1, 9, 2, 0, "EST start date"));
        headerList.add(new ExcelHeader(1, 10, 2, 0, "VOD start date"));
        headerList.add(new ExcelHeader(1, 11, 2, 0, "QA 요청일"));
        headerList.add(new ExcelHeader(1, 12, 2, 0, "Trailer"));
        
        headerList.add(new ExcelHeader(1, 13, 0, 8, "Mobile"));
        headerList.add(new ExcelHeader(2, 13, 0, 2, "H264"));
        headerList.add(new ExcelHeader(3, 13, 0, 0, "SD"));
        headerList.add(new ExcelHeader(3, 14, 0, 0, "HD"));
        headerList.add(new ExcelHeader(3, 15, 0, 0, "FHD"));
        
        headerList.add(new ExcelHeader(2, 16, 0, 2, "Stream"));
        headerList.add(new ExcelHeader(3, 16, 0, 0, "SD"));
        headerList.add(new ExcelHeader(3, 17, 0, 0, "HD"));
        headerList.add(new ExcelHeader(3, 18, 0, 0, "FHD"));
        
        headerList.add(new ExcelHeader(2, 19, 0, 2, "Download"));
        headerList.add(new ExcelHeader(3, 19, 0, 0, "SD"));
        headerList.add(new ExcelHeader(3, 20, 0, 0, "HD"));
        headerList.add(new ExcelHeader(3, 21, 0, 0, "FHD"));
        
        headerList.add(new ExcelHeader(1, 22, 2, 0, "Home Sync"));
        
        headerList.add(new ExcelHeader(1, 23, 1, 2, "TV"));
        headerList.add(new ExcelHeader(3, 23, 0, 0, "SD"));
        headerList.add(new ExcelHeader(3, 24, 0, 0, "HD"));
        headerList.add(new ExcelHeader(3, 25, 0, 0, "FHD"));
        
        headerList.add(new ExcelHeader(1, 26, 1, 2, "PC"));
        headerList.add(new ExcelHeader(3, 26, 0, 0, "SD"));
        headerList.add(new ExcelHeader(3, 27, 0, 0, "HD"));
        headerList.add(new ExcelHeader(3, 28, 0, 0, "FHD"));
        
        headerList.add(new ExcelHeader(1, 29, 2, 0, "Meta ALL"));
        headerList.add(new ExcelHeader(1, 30, 2, 0, "Title"));
        headerList.add(new ExcelHeader(1, 31, 2, 0, "Poster"));
        headerList.add(new ExcelHeader(1, 32, 2, 0, "Genre"));
        headerList.add(new ExcelHeader(1, 33, 2, 0, "Directors"));
        headerList.add(new ExcelHeader(1, 34, 2, 0, "Actors"));
        headerList.add(new ExcelHeader(1, 35, 2, 0, "Synopsis"));
        headerList.add(new ExcelHeader(1, 36, 2, 0, "AudioLang"));
        headerList.add(new ExcelHeader(1, 37, 2, 0, "SubTitles"));
        headerList.add(new ExcelHeader(1, 38, 2, 0, "RunTime"));
        headerList.add(new ExcelHeader(1, 39, 2, 0, "ReleaseYD"));
        headerList.add(new ExcelHeader(1, 40, 2, 0, "Rating"));
        headerList.add(new ExcelHeader(1, 41, 2, 0, "Price"));
        
        headerList.add(new ExcelHeader(0, 42, 0, 2, "검증요청사항"));
        headerList.add(new ExcelHeader(1, 42, 2, 0, "검증의뢰일"));
        headerList.add(new ExcelHeader(1, 43, 2, 0, "검증항목"));
        headerList.add(new ExcelHeader(1, 44, 2, 0, "상세내용"));
        
        headerList.add(new ExcelHeader(0, 45, 0, 3, "QA"));
        headerList.add(new ExcelHeader(1, 45, 2, 0, "검증완료일"));
        headerList.add(new ExcelHeader(1, 46, 2, 0, "검증결과"));
        headerList.add(new ExcelHeader(1, 47, 2, 0, "검증상세"));
        headerList.add(new ExcelHeader(1, 48, 2, 0, "비고"));
        
        headerList.add(new ExcelHeader(0, 49, 0, 1, "Real Publish"));
        headerList.add(new ExcelHeader(1, 49, 2, 0, "배포완료일"));
        headerList.add(new ExcelHeader(1, 50, 2, 0, "비고"));
        
        headerList.add(new ExcelHeader(0, 51, 0, 2, "Price 확인"));
        headerList.add(new ExcelHeader(1, 51, 2, 0, "정보"));
        headerList.add(new ExcelHeader(1, 52, 2, 0, "결과"));
        headerList.add(new ExcelHeader(1, 53, 2, 0, "비고"));
        
        headerList.add(new ExcelHeader(0, 54, 0, 2, "Suspend"));
        headerList.add(new ExcelHeader(1, 54, 2, 0, "suspend date"));
        headerList.add(new ExcelHeader(1, 55, 2, 0, "비고"));
        headerList.add(new ExcelHeader(1, 56, 2, 0, "report date"));
        
        headerList.add(new ExcelHeader(0, 57, 0, 1, "Video Fail Feed Back"));
        headerList.add(new ExcelHeader(1, 57, 2, 0, "인코딩 Feed Back date"));
        headerList.add(new ExcelHeader(1, 58, 2, 0, "인코딩 Feed Back"));
  
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
