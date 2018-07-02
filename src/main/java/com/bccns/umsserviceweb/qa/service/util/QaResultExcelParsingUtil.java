package com.bccns.umsserviceweb.qa.service.util;

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
import org.springframework.stereotype.Component;

import com.bccns.umsserviceweb.qa.vo.QaItemVO;
import com.bccns.umsserviceweb.qa.vo.QaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class QaResultExcelParsingUtil {
    
    private static final Logger log = LoggerFactory.getLogger(QaResultExcelParsingUtil.class);
    
    public QaResultExcelParsingUtil() {
        
    }

    public List<QaVO> parsingExcelFile(File file) throws IOException, InvalidFormatException {
        
        FileInputStream fis = null;
        
        try {
          fis = new FileInputStream(file);
            
          return parsingExcelFile(fis);
            
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }
    
    public List<QaVO> parsingExcelFile(InputStream inputStream) throws IOException, InvalidFormatException {
        
        List<QaVO> qaList = new ArrayList<QaVO>();
        
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
            //excel의 첫 4라인은 타이틀 영역, 타이틀을 제외한 컨텐트 영역만 read
            for(int rowIndex=4 ; rowIndex<sheetRowCount ; rowIndex++) {               
                try {               
                    Row row = sheet.getRow(rowIndex);
                    
                    QaVO qavo = new QaVO();
                    int cellIndex = 0;
                    //row index + 1 (0부터 시작하므로 실제 row indexs는 +1 함)
                    qavo.setRowIndex(rowIndex+1);
                    cellIndex++;
                    
                    String territory = getCellValue(row.getCell(cellIndex++));
                    qavo.setTerritory(territory);
                    log.debug("territory:" + territory);
                    
                    String masterId = getCellValue(row.getCell(cellIndex++));
                    qavo.setOrderId(masterId);
                    log.debug("masterId:" + masterId);
                    
                    String cid = getCellValue(row.getCell(cellIndex++));
                    qavo.setCid(cid);
                    
                    String productId = getCellValue(row.getCell(cellIndex++));
                    qavo.setProductId(productId);
                    
                    String title = getCellValue(row.getCell(cellIndex++));
                    qavo.setContTitle(title);
                    
                    
                    String epno = getCellValue(row.getCell(cellIndex++));
                    log.debug(epno);
                    
                    String cp = getCellValue(row.getCell(cellIndex++));
                    log.debug(cp);
                    
                    String type = getCellValue(row.getCell(cellIndex++));
                    log.debug(type);
                    
                    String est_start_date = getCellValue(row.getCell(cellIndex++));
                    log.debug(est_start_date);
                    
                    String vod_start_date = getCellValue(row.getCell(cellIndex++));
                    log.debug(vod_start_date);
                    
                    String reqDate = getCellValue(row.getCell(cellIndex++));
                    log.debug(reqDate);
                    
                    List<QaItemVO> newItemList = new ArrayList<QaItemVO>();
                    List<QaItemVO> newMetaList = new ArrayList<QaItemVO>();
                                        
                    String trailer = getCellValue(row.getCell(cellIndex++));
                    if(trailer != null && trailer.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_TRAILER");
                        newItemList.add(qi);
                    }
                    
                    String h264_sd  = getCellValue(row.getCell(cellIndex++));
                    if(h264_sd != null && h264_sd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_H264_SD");
                        newItemList.add(qi);
                    }
                    
                    String h264_hd  = getCellValue(row.getCell(cellIndex++));
                    if(h264_hd != null &&  h264_hd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_H264_HD");
                        newItemList.add(qi);
                    }
                    
                    String h264_fhd  = getCellValue(row.getCell(cellIndex++));
                    if(h264_fhd != null &&  h264_fhd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_H264_FHD");
                        newItemList.add(qi);
                    }
                    
                    String st_sd  = getCellValue(row.getCell(cellIndex++));
                    if(st_sd != null &&  st_sd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_HEVC_ST_SD");
                        newItemList.add(qi);
                    }
                    
                    String st_hd  = getCellValue(row.getCell(cellIndex++));
                    if(st_hd != null &&  st_hd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_HEVC_ST_HD");
                        newItemList.add(qi);
                    }
                    
                    String st_fhd  = getCellValue(row.getCell(cellIndex++));
                    if(st_fhd != null &&  st_fhd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_HEVC_ST_FHD");
                        newItemList.add(qi);
                    }
                    
                    String dn_sd  = getCellValue(row.getCell(cellIndex++));
                    if(dn_sd != null &&  dn_sd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_HEVC_DL_SD");
                        newItemList.add(qi);
                    }
                    
                    String dn_hd  = getCellValue(row.getCell(cellIndex++));
                    if(dn_hd != null &&  dn_hd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_HEVC_DL_HD");
                        newItemList.add(qi);
                    }
                    
                    String dn_fhd  = getCellValue(row.getCell(cellIndex++));
                    if(dn_fhd != null &&  dn_fhd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_HEVC_DL_FHD");
                        newItemList.add(qi);
                    }
                    
                    String homeSync = getCellValue(row.getCell(cellIndex++));
                    if(homeSync != null &&  homeSync.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_HOME_SYNC");
                        newItemList.add(qi);
                    }
                    
                    String tv_sd  = getCellValue(row.getCell(cellIndex++));
                    if(tv_sd != null &&  tv_sd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_TV_SD");
                        newItemList.add(qi);
                    }
                    
                    String tv_hd  = getCellValue(row.getCell(cellIndex++));
                    if(tv_hd != null &&  tv_hd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_TV_HD");
                        newItemList.add(qi);
                    }
                    
                    String tv_fhd  = getCellValue(row.getCell(cellIndex++));
                    if(tv_fhd != null &&  tv_fhd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_TV_FHD");
                        newItemList.add(qi);
                    }
                    
                    String pc_sd  = getCellValue(row.getCell(cellIndex++));
                    if(pc_sd != null && pc_sd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_PC_SD");
                        newItemList.add(qi);
                    }
                    
                    String pc_hd  = getCellValue(row.getCell(cellIndex++));
                    if(pc_hd != null && pc_hd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_PC_HD");
                        newItemList.add(qi);
                    }
                    
                    String pc_fhd  = getCellValue(row.getCell(cellIndex++));
                    if(pc_fhd != null && pc_fhd.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("R_PC_FHD");
                        newItemList.add(qi);
                    }
                    
                    if(newItemList.size() > 0) {
                        String[] arrRItem = new String[newItemList.size()];
                        int itemp = 0;
                        for(QaItemVO thisvo : newItemList) {
                            arrRItem[itemp] = thisvo.getItem(); 
                            itemp++;
                        }
                        qavo.setArrRItem(arrRItem);
                    }

                    String meta  = getCellValue(row.getCell(cellIndex++));
                    if(meta != null && meta.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("M_ALL");
                        newMetaList.add(qi);
                    }
                    
                    String M_Title  = getCellValue(row.getCell(cellIndex++));
                    if(M_Title != null && M_Title.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("M_Title");
                        newMetaList.add(qi);
                    }
                    
                    String M_Poster  = getCellValue(row.getCell(cellIndex++));
                    if(M_Poster != null && M_Poster.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("M_Poster");
                        newMetaList.add(qi);
                    }
                    
                    String M_Genre  = getCellValue(row.getCell(cellIndex++));
                    if(M_Genre != null && M_Genre.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); 
                        qi.setItem("M_Genre");
                        newMetaList.add(qi);
                    }
                    
                    String M_Directors  = getCellValue(row.getCell(cellIndex++));
                    if(M_Directors != null && M_Directors.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); 
                        qi.setItem("M_Directors");
                        newMetaList.add(qi);
                    }
                    
                    String M_Actors  = getCellValue(row.getCell(cellIndex++));
                    if(M_Actors != null && M_Actors.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); 
                        qi.setItem("M_Actors");
                        newMetaList.add(qi);
                    }
                    String M_Synopsis  = getCellValue(row.getCell(cellIndex++));
                    if(M_Synopsis != null && M_Synopsis.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); 
                        qi.setItem("M_Synopsis");
                        newMetaList.add(qi);
                    }
                    String M_AudioLang  = getCellValue(row.getCell(cellIndex++));
                    if(M_AudioLang != null && M_AudioLang.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); 
                        qi.setItem("M_AudioLang");
                        newMetaList.add(qi);
                    }
                    String M_SubTitles  = getCellValue(row.getCell(cellIndex++));
                    if(M_SubTitles != null && M_SubTitles.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); 
                        qi.setItem("M_AudioLang");
                        newMetaList.add(qi);
                    }
                    String M_RunTime  = getCellValue(row.getCell(cellIndex++));
                    if(M_RunTime != null && M_RunTime.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); 
                        qi.setItem("M_RunTime");
                        newMetaList.add(qi);
                    }
                    String M_ReleaseYD  = getCellValue(row.getCell(cellIndex++));
                    if(M_ReleaseYD != null && M_ReleaseYD.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); 
                        qi.setItem("M_ReleaseYD");
                        newMetaList.add(qi);
                    }
                    String M_Rating  = getCellValue(row.getCell(cellIndex++));
                    if(M_Rating != null && M_Rating.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); 
                        qi.setItem("M_Rating");
                        newMetaList.add(qi);
                    }
                    
                    String price  = getCellValue(row.getCell(cellIndex++));
                    if(price != null && price.toUpperCase().indexOf("FAIL") > 0) {
                        QaItemVO qi = new QaItemVO();
                        qi.setReqRstType("2"); //결과등록
                        qi.setItem("M_Price");
                        newMetaList.add(qi);
                    }
                    
                    if(newMetaList.size() > 0) {
                        String[] arrRMeta = new String[newMetaList.size()];
                        int itemp = 0;
                        for(QaItemVO thisvo : newMetaList) {
                            arrRMeta[itemp] = thisvo.getItem(); 
                            itemp++;
                        }
                        qavo.setArrRMeta(arrRMeta);
                    }
                    
                    //qavo.setItemList(newItemList);
                    
                    
                    String temp = getCellValue(row.getCell(cellIndex++)); //검증의뢰일
                    log.debug(temp);
                    
                    temp = getCellValue(row.getCell(cellIndex++)); //검증항목
                    
                    temp = getCellValue(row.getCell(cellIndex++)); //상세내용
                    
                    temp = getCellValue(row.getCell(cellIndex++)); //검증완료일
                    
                    String qaResult = getCellValue(row.getCell(cellIndex++)); //검증결과
                    if(qaResult != null && qaResult.toUpperCase().indexOf("PASS") > 0 && qaResult.toUpperCase().indexOf("REVISED") > 0)  {
                        qaResult = "PASS_REVIS";
                    } else if(qaResult != null && qaResult.toUpperCase().indexOf("PASS") > 0)  {
                        qaResult = "PASS";
                    } else {
                        qaResult = "FAIL";
                    }
                    qavo.setQaResult(qaResult);
                    
                    temp = getCellValue(row.getCell(cellIndex++)); //검증상세
                    
                    String desc = getCellValue(row.getCell(cellIndex++)); //비고
                    qavo.setDescription(desc);
                    
                    
                    
                    qaList.add(qavo);
                } catch (Exception e) {
                    //exception 발생시 log만 처리하고 다음 컨텐츠를 진행
                    System.out.println(e.getMessage());
                    continue;
                }
            }               
            
        }  
        
        return qaList;
        
    }
    
    private String getCellValue(Cell cell) {
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
