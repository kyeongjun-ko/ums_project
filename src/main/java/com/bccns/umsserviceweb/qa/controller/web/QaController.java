package com.bccns.umsserviceweb.qa.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.bccns.umsserviceweb.common.code.config.CommonCodeConfig;
//import com.bccns.umsserviceweb.common.code.service.CommonCodeService;
//import com.bccns.umsserviceweb.common.code.vo.CommonCodeVO;
import com.bccns.umsserviceweb.common.util.StringUtil;
import com.bccns.umsserviceweb.common.util.UtilStatic;
import com.bccns.umsserviceweb.common.vo.JsonVO;
import com.bccns.umsserviceweb.qa.service.QaService;
import com.bccns.umsserviceweb.qa.vo.ContentVO;
import com.bccns.umsserviceweb.qa.vo.FileSizeVO;
import com.bccns.umsserviceweb.qa.vo.QaItemVO;
import com.bccns.umsserviceweb.qa.vo.QaResultImportResultVO;
import com.bccns.umsserviceweb.qa.vo.QaVO;
import com.bccns.umsserviceweb.qa.vo.SearchQaVO;

@Controller
@RequestMapping(value = "/qa")
public class QaController {

    private static final Logger log = LoggerFactory.getLogger(QaController.class);
    
    @Autowired
    QaService qaService;
    
//    @Autowired
//    CommonCodeService commonCodeService;
    
//    @Value("${target.server.url}") String currentURL;
    
    
    @Value("${property.pagination.recordcountperpage}")
    private int recordCountPerPage;
    
    @Value("${property.pagination.pagesize}")
    private int pageSize;
    
    
    @ModelAttribute("backTrackType")
    public Map<String, String> backTrackType() {
        Map<String, String> comboKey = new LinkedHashMap<String, String>();
        
        //comboKey.put("", "Do not Change");
        comboKey.put("01", "ENCODING");
        comboKey.put("02", "CMS");
        /*
        comboKey.put("03", "PRODUCT REGISTER");
        comboKey.put("04", "PRODUCT READY FOR CHANGE");
        comboKey.put("05", "PRODUCT READY TO PUBLISH");
        comboKey.put("06", "PRODUCT PUBLISHED");
        */
        return comboKey;
    }
    
    
//    @ModelAttribute(value="encodingCodeList") 
//    private List<CommonCodeVO> getEncodingCodeList() {
//        
//        List<CommonCodeVO> encodingCodeList = new ArrayList<CommonCodeVO>();
//        encodingCodeList.add(new CommonCodeVO(CommonCodeConfig.ENCODING_HOUSE_QPM_CODE, CommonCodeConfig.ENCODING_HOUSE_QPM_NAME));
//        encodingCodeList.add(new CommonCodeVO(CommonCodeConfig.ENCODING_HOUSE_DELUXE_CODE, CommonCodeConfig.ENCODING_HOUSE_DELUXE_NAME));
//        encodingCodeList.add(new CommonCodeVO(CommonCodeConfig.ENCODING_HOUSE_GALCOM_CODE, CommonCodeConfig.ENCODING_HOUSE_GALCOM_NAME));
//        
//        return encodingCodeList;
//    }
    

    @RequestMapping(value = {"/qaRequestList", "/qaCompleteList", "/qaRequestListExcel", "/qaCompleteListExcel"})
    public String getQaRequestList(
            @ModelAttribute("searchQaVO") SearchQaVO searchQaVO
            , ModelMap model, HttpServletRequest request, String csvName) {
        
        String mav = "";
        if(request.getRequestURI().indexOf("Excel") > 0) {
            searchQaVO.setCurrentPageNo(1);
            searchQaVO.setRecordCountPerPage(10000);
            
            mav = "qaRequestListExcelView";
            model.put("viewName", csvName);
        } else {
            searchQaVO.setRecordCountPerPage(this.recordCountPerPage);
            mav = "/qa/qaRequestList";
        }
        
        if(request.getRequestURI().indexOf("qaCompleteList") > 0) {
            searchQaVO.setQaReqComplFlag("compl");
        } else {
            searchQaVO.setQaReqComplFlag("req");
        }
        
        /*
        if(searchQaVO.getUiSearchFlag() == null) {
            if(searchQaVO.getArrTerritory() == null) {
                Map reqMap = new HashMap();
                reqMap.put("group", "TERRITORY");
                List<CommonCodeVO> selectCommonCodeList = commonCodeService.selectCommonCodeList(reqMap);
                String[] arrTerritory = new String[selectCommonCodeList.size()];
                int i = 0;
                for(CommonCodeVO thisvo : selectCommonCodeList) {
                    arrTerritory[i] = thisvo.getCode();
                    i++;
                }
                searchQaVO.setArrTerritory(arrTerritory);
            }
        }
        */
        
        if(searchQaVO.getSrchCpNm() != null && searchQaVO.getSrchCpNm().length() > 0) {
            searchQaVO.setArrCpNm(searchQaVO.getSrchCpNm().split(";"));
        }
        /*
        if(searchQaVO.getSrchVendorNm() != null && searchQaVO.getSrchVendorNm().length() > 0) {
            searchQaVO.setArrVendorNm(searchQaVO.getSrchVendorNm().split(";"));
        }
        */
        
        int cnt = searchQaVO.getArrQaResult() == null ? 0 : searchQaVO.getArrQaResult().length;
        String[] oriArrQaResult = new String[cnt]; 
        String[] newArrQaResult = searchQaVO.getArrQaResult();
        List<String> resultOther = new ArrayList<String>();
        
        if(searchQaVO.getArrQaResult() != null && searchQaVO.getArrQaResult().length > 0) {
            int i =0;
            for(String fail : searchQaVO.getArrQaResult()) {
                if("FAIL_M".equals(fail)) {
                    resultOther.add("FAIL_M");
                    newArrQaResult[i] = "FAIL";
                    oriArrQaResult[i] = fail;
                } else if("FAIL_V".equals(fail)) {
                    resultOther.add("FAIL_V");
                    newArrQaResult[i] = "FAIL";
                    oriArrQaResult[i] = fail;
                } else if("FAIL_P".equals(fail)) {
                    resultOther.add("FAIL_P");
                    newArrQaResult[i] = "FAIL";
                    oriArrQaResult[i] = fail;
                } else if("FAIL_O".equals(fail)) {
                    resultOther.add("FAIL_O");
                    newArrQaResult[i] = "FAIL";
                    oriArrQaResult[i] = fail;    
                } else {
                    newArrQaResult[i] = fail;
                    oriArrQaResult[i] = fail;
                }
                i++;
            }
            searchQaVO.setArrQaResult(newArrQaResult);
            searchQaVO.setArrQaResultOther(resultOther);
        }
        
        searchQaVO.setPageSize(this.pageSize);
        
        model.put("firstIndex",         searchQaVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchQaVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchQaVO.getRecordCountPerPage());
        
        int totCount = qaService.getQaRequestListCount(searchQaVO);
        
        // 전체 게시물 건 수
        searchQaVO.setTotalRecordCount(totCount);

        List<QaVO> qaList = qaService.getQaRequestList(searchQaVO);
        
        if(request.getRequestURI().indexOf("Excel") <= 0) {
            for(QaVO thisvo : qaList) {
                QaItemVO reqvo = new QaItemVO();
                reqvo.setQaId(thisvo.getQaId());
                reqvo.setItem("A_"); //요청항목만 조회
                List<QaItemVO> qaItemList = qaService.getQaItemList(reqvo);
                thisvo.setItemList(qaItemList);
                //for(QaItemVO qavo :qaItemList) {
                //}
            }
        }
        
        if(request.getRequestURI().indexOf("qaCompleteListExcel") > 0) {
            for(QaVO thisvo : qaList) {
                
                QaItemVO reqvo = new QaItemVO();
                reqvo.setQaId(thisvo.getQaId());
                List<QaItemVO> qaItemList = qaService.getQaItemList(reqvo);
                thisvo.setItemList(qaItemList);
                
                /*
                QaItemVO reqvo = new QaItemVO();
                reqvo.setQaId(thisvo.getQaId());
                reqvo.setItem("R_"); //결과항목만 조회
                List<QaItemVO> qaItemListR = qaService.getQaItemList(reqvo);
                thisvo.setItemList(qaItemListR);
                
                
                reqvo = new QaItemVO();
                reqvo.setQaId(thisvo.getQaId());
                reqvo.setItem("M_"); //메타항목만 조회
                List<QaItemVO> qaItemListM = qaService.getQaItemList(reqvo);
                thisvo.setItemList(qaItemListM);
                               
                if(qaItemListR != null && qaItemListR.size() > 0) {
                    String[] arrItemR = new String[qaItemListR.size()];
                    int i=0;
                    for(QaItemVO vo : qaItemListR) {
                        arrItemR[i] = vo.getItem();
                        i++; 
                    }
                    thisvo.setArrRItem(arrItemR);
                }
                if(qaItemListM != null && qaItemListM.size() > 0) {
                    String[] arrItemM = new String[qaItemListM.size()];
                    int i=0;
                    for(QaItemVO vo : qaItemListM) {
                        arrItemM[i] = vo.getItem();
                        i++; 
                    }
                    thisvo.setArrRMeta(arrItemM);
                }
                */
            }
        }
        
//        model.put("qaList", qaList);
//        
//        searchQaVO.setArrQaResult(oriArrQaResult);
//        model.put("searchQaVO", searchQaVO);
//        
//        model.put("encodingCodeList", getEncodingCodeList());
        
        return mav;
    }
    
    
    private int checkUploadFileExtension(String fileName) {
        
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")) {
            return 1;
        } else if(ext.equalsIgnoreCase("txt")) {
            return 2;
        } else {
            return -1;
        }
    }
    
    
    @RequestMapping(value="/qaResultUploadAjax", method=RequestMethod.POST)
    public void qaResultUploadAjax(ModelMap model,
            final HttpServletRequest request, final HttpServletResponse response) {
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        
        List<MultipartFile> files = multipartRequest.getFiles("uploadFile");
        MultipartFile file = files.get(0);
        
        log.debug("avail upload file name = " + file.getOriginalFilename());
        
        QaResultImportResultVO resultVO = new QaResultImportResultVO();
        try {
            //파일 확장자 체크
            int fileExt = checkUploadFileExtension(file.getOriginalFilename());
            if(fileExt == -1) {
                resultVO.setResult(false);
                resultVO.setErrMsg("Invalid file format. Please Check file name.");
            } else {
                if(fileExt == 1) {
                    //excel file upload
                    resultVO = qaService.qaResultListExcelUpload(file.getInputStream());
                    resultVO.setResult(true);
                } else {
                    resultVO.setResult(false);
                    resultVO.setErrMsg("Invalid file format. Please Check file name.");
                }
            }
  
        } catch (Exception e) {
            resultVO.setResult(false);
            resultVO.setErrMsg(e.getMessage());
        }
        
        //response
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(resultVO.toJsonStr());
            out.flush();
            out.close();
        } catch(Exception e) {
            log.error("",e);
        }
    }
      
    
    @RequestMapping(value = {"/qaContentsPopUp", "/qaContentsListPopUp", "/qaContentsListPopUpExcel"})
    public String qaWriteForm(
            @ModelAttribute("qaVO") QaVO qaVO
            , BindingResult result, SessionStatus status, ModelMap model, HttpServletRequest request, HttpServletResponse response, String csvName) {
               
        if(qaVO.getArrProductId() == null) {
            response.setContentType("text/html;charset=euc-kr");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.println("<script>");
                out.println("alert('Product ID does not exist. Can not be registered.');");
                out.println("self.close();");
                out.println("</script>");
                out.flush();
                out.close();               
            }
            catch ( IOException e ) {
                log.debug("qa content create failure = "+e.getMessage());
            }
            //default return
            return "/qa/qaContentPopUp";
        }
        
        log.debug("qa request arrProductId = " + qaVO.getArrProductId().length);
        for(String productId : qaVO.getArrProductId()) {
            log.debug("qa request productId = " + productId);
        }
        
        String mav = "";
        SearchQaVO searchQaVO = new SearchQaVO();
        
        if(request.getRequestURI().indexOf("qaContentsPopUp") > 0) {
            searchQaVO.setArrProductId(qaVO.getArrProductId());
            mav = "/qa/qaContentPopUp";
            
        } else if(request.getRequestURI().indexOf("qaContentsListPopUpExcel") > 0) {
            mav = "qaContentsListPopUpExcelView";
            
            String arrProductId[] = new String[qaVO.getArrProductId().length];
            int i =0;
            for(String comProdId : qaVO.getArrProductId()) {
                String[] arrProdId = comProdId.split("!@");
                arrProductId[i] = arrProdId[0];
                i++;
            }
            searchQaVO.setArrProductId(arrProductId);
            
            model.put("viewName", csvName);
        } else {
            searchQaVO.setArrProductId(qaVO.getArrProductId());
            mav = "/qa/qaContentsPopUp";
        }
        
        List<QaVO> qaContList = qaService.getQaContentsPopupList(searchQaVO);
        model.put("qaContList", qaContList);
        
        return mav;
    }
    
    @RequestMapping(value = {"/qaResultsWritePopUp", "/qaResultsWritePopUpExcel", "/qaResultsChangePopUp"})
    public String qaResultsWritePopUp(
            @ModelAttribute("searchQaVO") SearchQaVO searchQaVO
            , @ModelAttribute("qaVO") QaVO qaVO
            , BindingResult result, SessionStatus status, ModelMap model, HttpServletRequest request, String csvName) {
        
        String mav = "";
        if(request.getRequestURI().indexOf("qaResultsWritePopUpExcel") > 0) {
            mav = "qaContentsListPopUpExcelView";
            model.put("viewName", csvName);
        } else if(request.getRequestURI().indexOf("qaResultsChangePopUp") > 0) {
            mav = "/qa/qaResultsChangePopUp";
        } else {
            mav = "/qa/qaResultsWritePopUp";
        }
        
        String flag = searchQaVO.getQaReqComplFlag() == null ? "req" : searchQaVO.getQaReqComplFlag();   
        searchQaVO.setQaReqComplFlag(flag);
        
        searchQaVO.setCurrentPageNo(1);
        searchQaVO.setRecordCountPerPage(1000);
        searchQaVO.setPageSize(10);
        model.put("firstIndex",         searchQaVO.getFirstRecordIndex());
        model.put("lastRecordIndex",    searchQaVO.getLastRecordIndex());
        model.put("recordCountPerPage", searchQaVO.getRecordCountPerPage());
        List<QaVO> qaList = qaService.getQaRequestList(searchQaVO);
        model.put("qaContList", qaList);
        
        return mav;
    }
    
    @RequestMapping(value = "/qaContentsWrite")
    public String createQaContents(
            @ModelAttribute("qaVO") QaVO qaVO
        , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs) {
        
        String msg = "success!";
        try {
            qaService.createQaContentsMulti(qaVO);
        } catch(Exception e) {
            log.error("createQaContents error", e);
            msg = "fail!";
        }
        
        redirectAttrs.addFlashAttribute("msg", msg);
        
        String mav = "redirect:/qa/qaRequestList"; 
        return mav;
    }
    
    @RequestMapping(value = "/qaContentsWriteAjax")
    public @ResponseBody JsonVO createQaContentsAjax(@ModelAttribute("qaVO") QaVO qaVO, ModelMap model) {
        
        JsonVO result = new JsonVO();
        
        try {
            qaService.createQaContentsMulti(qaVO);
        } catch(Exception e) {
            log.error("createQaContents error :: ", e);
            result.setResult(false);
            result.setErrMsg("Qa Content create fail.");
        }
        
        return result;
    }
    
    /*
    @RequestMapping(value = "/qaContentsWrite")
    public String createQaContents(
            @ModelAttribute("qaVO") QaVO qaVO
        , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs) {
        
        for(String comProdId : qaVO.getArrProductId()) {
            String[] arrProdId = comProdId.split("!@");
            qaVO.setProductId(arrProdId[0]);
            qaVO.setCid(arrProdId[1]);
            qaVO.setTerritory(arrProdId[2]);
            qaVO.setState(arrProdId[3]);
            try {
                qaVO.setOrderId(arrProdId[4]);
            } catch(Exception e) {
                qaVO.setOrderId(null);
            }
            ////////////////////////////////
            //qaVO.setOrderId("test");  // 테스트 용
            ////////////////////////////////
            qaVO.setQaId(null); //중복 qa 여부 확인은 product_id 와 territory 로 한다.
            QaVO thisVo = qaService.getQaContent(qaVO);
            if(thisVo != null && thisVo.getQaId() != null) {
                
                log.debug("thisVo.getCurLibType():" + thisVo.getCurLibType());
                log.debug("thisVo.getOrderId():" + thisVo.getOrderId());
                log.debug("thisVo.getCpId():" + thisVo.getCpId());
                log.debug("thisVo.getEncodeVendor():" + thisVo.getEncodeVendor());
                log.debug("thisVo.getContType():" + thisVo.getContType());
                log.debug("thisVo.getContTitle():" + thisVo.getContTitle());
                log.debug("thisVo.getTerritory():" + thisVo.getTerritory());
                log.debug("thisVo.getUrgentFlag():" + thisVo.getUrgentFlag());
                
                qaService.createQaHisTransaction(thisVo, qaVO);
            } else {
                qaService.createQaTransaction(qaVO);
            }
        }
        
        String msg = "success!";
        redirectAttrs.addFlashAttribute("msg", msg);
        
        String mav = "redirect:/qa/qaRequestList"; 
        return mav;
    }
    */
    
    @RequestMapping(value = "/qaResultWrite")
    public String createQaResult(
            @ModelAttribute("searchQaVO") SearchQaVO searchQaVO 
           , @ModelAttribute("qaVO") QaVO qaVO
        , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs) {
        
        String msg = ""; 
        
        try {
            msg = qaService.createQaResultMulti(qaVO);
        } catch(Exception e) {
            log.error("createQaResult error", e);
            msg = "fail!";
        }
               
        if("".equals(msg)) {
            msg = "success!";
        }
        
        //Map<String, Object> forwardMap = UtilStatic.getMap(searchQaVO);
        //redirectAttrs.addAllAttributes(forwardMap);
        //redirectAttrs.addAttribute("currentPageNo", searchQaVO.getCurrentPageNo());
        redirectAttrs.addAttribute("currentPageNo", 1);
        redirectAttrs.addFlashAttribute("msg", msg);
        
        String mav = "redirect:/qa/qaRequestList"; 
        return mav;
    }
    
    @RequestMapping(value = "/qaResultWriteAjax")
    public @ResponseBody JsonVO createQaResultAjax(@ModelAttribute("qaVO") QaVO qaVO, ModelMap model) {

        JsonVO result = new JsonVO();
        
        try {
            String msg = qaService.createQaResultMulti(qaVO);
            if(!msg.equals("")) {
                result.setResult(false);
                result.setErrMsg(msg);
            }
        } catch(Exception e) {
            log.error("createQaResultAjax error :: ", e);
            result.setResult(false);
            result.setErrMsg("Qa result create fail.");
        }
        
        return result;
    }    
    
    /*
    @RequestMapping(value = "/qaResultWrite")
    public String createQaResult(
            @ModelAttribute("searchQaVO") SearchQaVO searchQaVO 
           , @ModelAttribute("qaVO") QaVO qaVO
        , BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs) {
        
        String msg = ""; 
        
        for(String qaId : qaVO.getArrQaId()) {
            qaVO.setQaId(qaId);
            
            //필요데이타 조회
            QaVO thisVo = qaService.getQaContent(qaVO);
            qaVO.setCurLibType(thisVo.getCurLibType());
            qaVO.setOrderId(thisVo.getOrderId());
            qaVO.setCpId(thisVo.getCpId());
            qaVO.setEncodeVendor(thisVo.getEncodeVendor());
            qaVO.setContType(thisVo.getContType());
            qaVO.setContTitle(thisVo.getContTitle());
            qaVO.setTerritory(thisVo.getTerritory());
            qaVO.setUrgentFlag(thisVo.getUrgentFlag());
            
            log.debug("thisVo.getCurLibType():" + thisVo.getCurLibType());
            log.debug("thisVo.getOrderId():" + thisVo.getOrderId());
            log.debug("thisVo.getCpId():" + thisVo.getCpId());
            log.debug("thisVo.getEncodeVendor():" + thisVo.getEncodeVendor());
            log.debug("thisVo.getContType():" + thisVo.getContType());
            log.debug("thisVo.getContTitle():" + thisVo.getContTitle());
            log.debug("thisVo.getTerritory():" + thisVo.getTerritory());
            log.debug("thisVo.getUrgentFlag():" + thisVo.getUrgentFlag());
            
            //이미 결과가 있는 경우
            if(!"".equals(StringUtil.nvl(thisVo.getQaResult())) && !"PASS_PART".equals(StringUtil.nvl(qaVO.getQaResult()))) {
                msg += "\n qa id:" + thisVo.getQaId() + " already has qa result.";
            } else if("PASS_PART".equals(StringUtil.nvl(qaVO.getQaResult()))) {
                qaService.createQaHisTransaction(thisVo, qaVO);
            } else {
                qaService.modifyQaUpdateResultTransaction(qaVO);
            }
        }
               
        if("".equals(msg)) {
            msg = "success!";
        }
        
        //Map<String, Object> forwardMap = UtilStatic.getMap(searchQaVO);
        //redirectAttrs.addAllAttributes(forwardMap);
        //redirectAttrs.addAttribute("currentPageNo", searchQaVO.getCurrentPageNo());
        redirectAttrs.addAttribute("currentPageNo", 1);
        redirectAttrs.addFlashAttribute("msg", msg);
        
        String mav = "redirect:/qa/qaRequestList"; 
        return mav;
    }
    */
    
    
    @RequestMapping(value = {"/qaDetailView", "/qaComplDetailView"})
    public String getQaDeatail(
            @ModelAttribute("searchQaVO") SearchQaVO searchQaVO
            , @ModelAttribute("qaVO") QaVO qaVO
            , BindingResult result, SessionStatus status, ModelMap model, HttpServletRequest request) {
                    
                
        qaVO = qaService.getQaContent(qaVO);
        QaItemVO reqvo = new QaItemVO();
        reqvo.setQaId(qaVO.getQaId());
        List<QaItemVO> qaItemList = qaService.getQaItemList(reqvo);
        qaVO.setItemList(qaItemList);
        
        
        
        String[] arrItem = new String[qaItemList.size()];
        int i=0;
        for(QaItemVO vo : qaItemList) {
            arrItem[i] = vo.getItem();
            i++; 
        }
        qaVO.setArrRItem(arrItem);
        qaVO.setArrCItem(arrItem);
        qaVO.setArrRMeta(arrItem);
        qaVO.setArrItem(arrItem);
        qaVO.setCmbBackTrackType(backTrackType());
        
        searchQaVO.setSrchQaId(qaVO.getQaId());
        searchQaVO.setQaHisFlag("history");
        searchQaVO.setQaReqComplFlag("");
        List<QaVO> qaHistoryList = qaService.getQaRequestList(searchQaVO);
        for(QaVO thisvo : qaHistoryList) {
            
            QaItemVO reqsubvo = new QaItemVO();
            reqsubvo.setQaHisSeq(thisvo.getQaHisSeq());
            List<QaItemVO> qaItemHisList = qaService.getQaItemHisList(reqsubvo);
            thisvo.setItemList(qaItemHisList);
            
            String[] arrHisItem = new String[qaItemHisList.size()];
            i=0;
            for(QaItemVO vo : qaItemHisList) {
                arrHisItem[i] = vo.getItem();
                i++; 
            }
            thisvo.setArrRItem(arrHisItem);
            thisvo.setArrCItem(arrHisItem);
            thisvo.setArrRMeta(arrHisItem);
            thisvo.setArrItem(arrHisItem);
        }
        
        
        //System.out.println("qaHistoryList.get(0).getShopId() ================== "+qaHistoryList.get(0).getShopId());
        //qaVO.setShopId(qaHistoryList.get(0).getShopId());
        
        if(request.getRequestURI().indexOf("qaComplDetailView") > 0) {
            searchQaVO.setQaReqComplFlag("compl");
        } else {
            searchQaVO.setQaReqComplFlag("req");
        }
        
        model.put("qaHistoryList", qaHistoryList);
        model.put("qaVO", qaVO);
//        model.put("currentURL", currentURL);

        String mav = "/qa/qaDetailView";
        return mav;
    }
    
    @RequestMapping(value = "/qaDetailInfo")
    public String getQaDeatailInfo(
            @ModelAttribute("searchQaVO") SearchQaVO searchQaVO
            , @ModelAttribute("qaVO") QaVO qaVO
            , BindingResult result, SessionStatus status, ModelMap model) {
        
        qaVO = qaService.getQaContent(qaVO);
        
        System.out.println("qaVO.getQaId() =========== "+qaVO.getQaId());
        System.out.println("qaVO.getOrderId() ============== "+qaVO.getOrderId());

        model.put("qaVO", qaVO);
        
        ContentVO contentVO = qaService.getContentInfo(qaVO);
        List<FileSizeVO> fileSizeList = qaService.getFileSizeInfo(qaVO);
        
        searchQaVO.setSrchQaId(qaVO.getQaId());
        
        model.put("contentVO", contentVO);
        model.put("fileSizeList", fileSizeList);

        String mav = "/qa/qaDetailInfo";
        return mav;
    }    
    
    
    /**
     * 임시 사용 - 개발용
     * @param model
     */
    public void modelPrint(Object model) {
        if(!log.isDebugEnabled()) return;
        
        Map map = UtilStatic.getMap(model);
        if (map != null){
            Iterator keys = map.keySet().iterator();
            while(keys.hasNext()){
                String key = (String) keys.next();
                log.debug(key + "=" + map.get(key));
            }
        }
        
    }
}
