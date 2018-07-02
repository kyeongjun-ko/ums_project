package com.bccns.umsserviceweb.ums.service.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bccns.umsserviceweb.mgr.vo.AddrVO;
import com.bccns.umsserviceweb.ums.service.UmsServiceImpl;
import com.bccns.umsserviceweb.ums.vo.AddrExcelInsertVO;
import com.bccns.umsserviceweb.ums.vo.AddrExcelVO;
import com.bccns.umsserviceweb.ums.vo.AddrExcelVO2;

public class umsExcelParsingUtil2 {
   private static final Logger log = LoggerFactory.getLogger(umsExcelParsingUtil2.class);
   
   public static List<AddrExcelVO2> parsingAddrExcelFile(File file) throws IOException, InvalidFormatException {
        
        FileInputStream fis = null;
        
        try {
          fis = new FileInputStream(file);
            
          return parsingAddrExcelFile(fis);
            
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }
   
   public static List<AddrExcelVO2> parsingAddrExcelFile(InputStream inputStream) throws IOException, InvalidFormatException {
       
       List<AddrExcelVO2> addrList = new ArrayList<AddrExcelVO2>();
       
       Workbook workbook = WorkbookFactory.create(inputStream);
       //Workbook workbook  = new HSSFWorkbook(inputStream);
   
       int numSheets = workbook.getNumberOfSheets();
       
       for(int i=0 ; i<numSheets ; i++) {
           
           Sheet sheet = workbook.getSheetAt(i);
           
           int sheetRowCount = sheet.getLastRowNum() + 1;
           
           //타이틀 3줄 제외
           //Row firstRow = sheet.getRow(0);
           //Row secondRow = sheet.getRow(1);
           //Row thirdRow = sheet.getRow(2);
           
           //excel format validation
           //if(firstRow.getLastCellNum() < 40) {
           //    //첫 라인을 읽어 정의된 포멧이 아니라면 해당 sheet는 읽지 않는다. 
           //    continue;
           //}
           
           //avail content parsing
           //excel의 첫 3라인은 타이틀 영역, 타이틀을 제외한 컨텐트 영역만 read
           for(int rowIndex=0 ; rowIndex<sheetRowCount ; rowIndex++) {               
               try {               
                   Row row = sheet.getRow(rowIndex);
                   
                   AddrExcelVO2 addrVo = new AddrExcelVO2();
                   int cellIndex = 0;
                   //row index + 1 (0부터 시작하므로 실제 row indexs는 +1 함)
                   addrVo.setRowIndex(rowIndex+1);
                   //phoneNo
                   String phoneNo = getCellValue(row.getCell(cellIndex++));
                   addrVo.setPhoneNo(phoneNo); 
                   //addrName
                   String addrName = getCellValue(row.getCell(cellIndex++));
                   addrVo.setAddrName(addrName); 
                   //content
                   String content = getCellValue(row.getCell(cellIndex++));
                   addrVo.setContent(content);
                   
                   addrList.add(addrVo);
       
               } catch (Exception e) {
                   //exception 발생시 log만 처리하고 다음 컨텐츠를 진행
                   System.out.println("excel row read fail :: " + e.getMessage());
                   continue;
               }
           }                          
       }  
       log.debug("parsingAddrExcelFile : addrList = " + addrList.toString() );
       return addrList;
       
   }
   
   public static List<AddrExcelInsertVO> parsingAddrExcelInsertFile(InputStream inputStream,String userId, String grpNo) throws IOException, InvalidFormatException {
        
        List<AddrExcelInsertVO> addrList = new ArrayList<AddrExcelInsertVO>();
        
        Workbook workbook = WorkbookFactory.create(inputStream);
        //Workbook workbook  = new HSSFWorkbook(inputStream);
    
        int numSheets = workbook.getNumberOfSheets();
        
        for(int i=0 ; i<numSheets ; i++) {
            
            Sheet sheet = workbook.getSheetAt(i);
            
            int sheetRowCount = sheet.getLastRowNum() + 1;
            
            //타이틀 3줄 제외
            //Row firstRow = sheet.getRow(0);
            //Row secondRow = sheet.getRow(1);
            //Row thirdRow = sheet.getRow(2);
            
            //excel format validation
            //if(firstRow.getLastCellNum() < 40) {
            //    //첫 라인을 읽어 정의된 포멧이 아니라면 해당 sheet는 읽지 않는다. 
            //    continue;
            //}
            
            //avail content parsing
            //excel의 첫 3라인은 타이틀 영역, 타이틀을 제외한 컨텐트 영역만 read
            for(int rowIndex=0 ; rowIndex<sheetRowCount ; rowIndex++) {               
                try {               
                    Row row = sheet.getRow(rowIndex);
                    
                    AddrExcelInsertVO addrVo = new AddrExcelInsertVO();
                    int cellIndex = 0;
                    //row index + 1 (0부터 시작하므로 실제 row indexs는 +1 함)
                    addrVo.setRowIndex(rowIndex+1);
                    
                    addrVo.setUserId(userId);
                    addrVo.setGrpNo(grpNo);

                    //Name
                    String name = getCellValue(row.getCell(cellIndex++));
                    addrVo.setName(name);
                    //sms_no
                    String sms_no = getCellValue(row.getCell(cellIndex++));
                    addrVo.setSmsNo(sms_no);
                    
                    //vms_no
                    String vms_no = getCellValue(row.getCell(cellIndex++));
                    addrVo.setVmsNo(vms_no);
                    
                    //fms_no
                    String fms_no = getCellValue(row.getCell(cellIndex++));
                    addrVo.setFmsNo(fms_no);
                    
                    //note
                    String note = getCellValue(row.getCell(cellIndex++));
                    addrVo.setNote(note);
                    
                    addrList.add(addrVo);
                } catch (Exception e) {
                    //exception 발생시 log만 처리하고 다음 컨텐츠를 진행
                    System.out.println("excel row read fail :: " + e.getMessage());
                    continue;
                }
            }                          
        }  
        log.debug("parsingAddrExcelInsertFile : addrList = " + addrList.toString() );
        return addrList;
        
    }
    
    private static String getCellValue(Cell cell) {
        String str = "";
        
        if(cell == null) {
            return str;
        }      
        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                str = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    str = format.format(date);
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    str = cell.getStringCellValue();
                    //str = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                str = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                str = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_ERROR:
                str = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        
        return str;
    }
}
