package com.bccns.umsserviceweb.qa.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.bccns.umsserviceweb.common.code.dao.CommonCodeDAO;
import com.bccns.umsserviceweb.common.util.StringUtil;
//import com.bccns.umsserviceweb.connector.log.exception.SqsTrackLogException;
//import com.bccns.umsserviceweb.connector.log.service.SqsTrackLogConnector;
import com.bccns.umsserviceweb.qa.dao.QaDAO;
import com.bccns.umsserviceweb.qa.service.util.QaResultExcelParsingUtil;
import com.bccns.umsserviceweb.qa.vo.ContentVO;
import com.bccns.umsserviceweb.qa.vo.FileSizeVO;
import com.bccns.umsserviceweb.qa.vo.QaItemVO;
import com.bccns.umsserviceweb.qa.vo.QaResultImportResultVO;
import com.bccns.umsserviceweb.qa.vo.QaVO;
import com.bccns.umsserviceweb.qa.vo.SearchQaVO;
//import com.bccns.umsserviceweb.suspension.service.SuspensionService;
//import com.bccns.umsserviceweb.suspension.vo.SuspensionVO;

@Service
public class QaServiceImpl implements QaService {
    
    private static final Logger log = LoggerFactory.getLogger(QaServiceImpl.class);
    
    @Autowired
    QaDAO qaDAO;
    
//    @Autowired
//    CommonCodeDAO commonCodeDAO;
//    
//    @Autowired
//    SuspensionService suspensionService;    
//    
//    @Autowired
//    QaResultExcelParsingUtil qaResultExcelParsingUtil;
//    
//    @Autowired
//    SqsTrackLogConnector sqsTrackLogConnector;
    
    @Override
    public List<QaVO> getQaRequestList(SearchQaVO searchQaVO) {
        return qaDAO.getQaRequestList(searchQaVO);
    }

    @Override
    public Integer getQaRequestListCount(SearchQaVO searchQaVO) {
        return qaDAO.getQaRequestListCount(searchQaVO);
    }


    @Override
    public List<QaVO> getQaContentsPopupList(SearchQaVO searchQaVO) {
        return qaDAO.getQaContentsPopupList(searchQaVO);
    }

    @Override
    public QaVO getQaContent(QaVO qaVO) {
        return qaDAO.getQaContent(qaVO);
    }

    @Override
    public Integer createQaContents(QaVO qaVO) {
        return qaDAO.createQaContents(qaVO);
    }

    @Override
    public Integer createQaHistory(QaVO qaVO) {
        return qaDAO.createQaHistory(qaVO);
    }

    @Override
    public Integer createQaItems(QaVO qaVO) {
        return qaDAO.createQaItems(qaVO);
    }

    @Override
    public List<QaItemVO> getQaItemList(QaItemVO qaVO) {
        return qaDAO.getQaItemList(qaVO);
    }

    @Override
    public Integer modifyNewQaContents(QaVO qaVO) {
        return qaDAO.modifyNewQaContents(qaVO);
    }
    
    @Override
    public Integer modifyResultQaContents(QaVO qaVO) {
        return qaDAO.modifyResultQaContents(qaVO);
    }
    
    @Override
    public Integer createQaHisTransaction(QaVO qaHisVO, QaVO qaNewVO, int PassPart) {
        
        String qaId = qaHisVO.getQaId();
        qaNewVO.setQaId(qaId);
        log.debug("=========>qaId :" + qaId);
        
//        if(PassPart == 0) {
//            try {
//                QaVO reqVo = new QaVO();
//                reqVo.setQaId(qaId);
//                QaVO thisVo = qaDAO.getQaContent(reqVo);
//                
//                String svID = commonCodeDAO.getServiceID(thisVo.getTerritory());
//                log.debug("serviceID ======================= "+svID);
//                qaHisVO.setServiceId(svID);
//    
//                sqsTrackLogConnector.transferQaStartLog(qaHisVO);
//            }
//            catch ( SqsTrackLogException e ) {
//                log.debug("Tracking Log failure to send");
//            }
//        }
        
        //히스토리 입력 
        qaDAO.createQaHistory(qaHisVO);
        String qaHisSeq = qaHisVO.getQaHisSeq(); 
        log.debug("=========>qaHisSeq :" + qaHisSeq);
        
        //본 컨텐츠 아이템 조회
        QaItemVO reqvo = new QaItemVO();
        reqvo.setQaId(qaId);
        List<QaItemVO> qaItemList = qaDAO.getQaItemList(reqvo);
        if(qaItemList != null && qaItemList.size() > 0) {
            for(QaItemVO thisvo : qaItemList) {
                thisvo.setQaHisSeq(qaHisSeq);
            }
            qaHisVO.setItemList(qaItemList);
            //히스토리 아이템 입력
            qaDAO.createQaItemsHis(qaHisVO);
        }
        
        //본 컨텐츠 수정
        qaNewVO.setQaId(qaId);
        //QaVO tempVo = getQaContent(qaNewVO);
        //qaNewVO.setState(tempVo.getProdCurState()); //앞에서 구함.
        qaDAO.modifyNewQaContents(qaNewVO);
        
        //pass part일 경우는 삭제하지 않고 추가해야 한다.
        if(PassPart == 0) {
            //본 컨텐츠 아이템 삭제
            qaDAO.removeQaItem(qaHisVO);
        }
        
        //본 컨텐츠 아이템 입력
        List<QaItemVO> newItemList = new ArrayList<QaItemVO>();
        String[] arrItem = qaNewVO.getArrItem();
        int itemSeq = 0;
        if(arrItem != null) {
            for(String thisItem : arrItem) {
                itemSeq++;
                QaItemVO qi = new QaItemVO();
                qi.setQaId(qaId);
                qi.setReqRstType("1"); //qa요청
                qi.setItem(thisItem);
                qi.setItemSeq(itemSeq + "");
                newItemList.add(qi);
            }
        }
        if(newItemList.size() > 0) {
            qaNewVO.setItemList(newItemList);
            qaDAO.createQaItems(qaNewVO);
        }
        
        return 1;
    }

    @Override
    public Integer removeQaItem(QaVO qaVO) {
        return qaDAO.removeQaItem(qaVO);
    }

    
    @Override
    public Integer createQaTransaction(QaVO qaVO) {
        
        //컨텐츠 입력
        qaDAO.createQaContents(qaVO);
        String qaId = qaVO.getQaId();
        
        
//        try {
//            QaVO reqVo = new QaVO();
//            reqVo.setQaId(qaId);
//            QaVO thisVo = getQaContent(reqVo);
//            
//            String svID = commonCodeDAO.getServiceID(thisVo.getTerritory());
//            log.debug("serviceID ======================= "+svID);
//            thisVo.setServiceId(svID);
//            sqsTrackLogConnector.transferQaStartLog(thisVo);
//        }
//        catch ( SqsTrackLogException e ) {
//            log.debug("Tracking Log failure to send");
//        }
        
        
        
        //컨텐츠 아이템 입력
        List<QaItemVO> newItemList = new ArrayList<QaItemVO>();
        String[] arrItem = qaVO.getArrItem();
        int itemSeq = 0;
        if(arrItem != null) {
            for(String thisItem : arrItem) {
                itemSeq++;
                QaItemVO qi = new QaItemVO();
                qi.setQaId(qaId);
                qi.setReqRstType("1"); //qa요청
                qi.setItem(thisItem);
                qi.setItemSeq(itemSeq + "");
                newItemList.add(qi);
            }
        }
        if(newItemList.size() > 0) {
            qaVO.setItemList(newItemList);
            qaDAO.createQaItems(qaVO);
        }
        
        return 1;
    }
    
    
    @Override
    public Integer modifyQaUpdateResultTransaction(QaVO qaVO) {
        
        //컨텐츠 입력
        if(qaVO.getQaResult() == null || "".equals(qaVO.getQaResult())) {
            qaVO.setQaResult("FAIL");
        }
        qaDAO.modifyResultQaContents(qaVO);
        String qaId = qaVO.getQaId();
        
        int maxseq = qaDAO.getQaItemMaxSeq(qaVO);
        //컨텐츠 아이템 입력
        List<QaItemVO> newItemList = new ArrayList<QaItemVO>();
        
        String[] arrCItem = qaVO.getArrCItem();
        String[] arrRItem = qaVO.getArrRItem();
        String[] arrRMeta = qaVO.getArrRMeta();
        
        int itemSeq = maxseq;
        if(arrCItem != null) {
            for(String thisItem : arrCItem) {
                itemSeq++;
                QaItemVO qi = new QaItemVO();
                qi.setQaId(qaId);
                qi.setReqRstType("2"); //결과등록
                qi.setItem(thisItem);
                qi.setItemSeq(itemSeq + "");
                newItemList.add(qi);
            }
        }
        if(arrRItem != null) {
            for(String thisItem : arrRItem) {
                itemSeq++;
                QaItemVO qi = new QaItemVO();
                qi.setQaId(qaId);
                qi.setReqRstType("2");
                qi.setItem(thisItem);
                qi.setItemSeq(itemSeq + "");
                newItemList.add(qi);
            }
        }
        if(arrRMeta != null) {
            for(String thisItem : arrRMeta) {
                itemSeq++;
                QaItemVO qi = new QaItemVO();
                qi.setQaId(qaId);
                qi.setReqRstType("2");
                qi.setItem(thisItem);
                qi.setItemSeq(itemSeq + "");
                newItemList.add(qi);
            }
        }
        
        if(newItemList.size() > 0) {
            qaVO.setItemList(newItemList);
            qaDAO.createQaItems(qaVO);
        }
        
        
        String svID="";
        //Item이나 Meta 검증 실패 항목이 존재하면 보류등록을 하고 Tracking Log를 전송한다.
//        SuspensionVO suspensionVO = new SuspensionVO();
        if((arrRItem != null && arrRItem.length != 0) || (arrRMeta != null && arrRMeta.length != 0)) {
            String category = "QA Fail";
            
            if(arrRItem != null && arrRItem.length != 0) {
                qaVO.setVideoErrorCode("ERR-FILM-000");
            }
            
            if(arrRMeta != null && arrRMeta.length != 0) {
                qaVO.setMetaErrorCode("ERR-META-000");
            }
            qaVO.setErrorDescription(qaVO.getDescription());
            
            QaVO thisVo = qaDAO.getQaContent(qaVO);
            
//            svID = commonCodeDAO.getServiceID(thisVo.getTerritory());
//            log.debug("serviceID ======================= "+svID);
//            qaVO.setServiceId(svID);
//            qaVO.setProductId(thisVo.getProductId());
//            qaVO.setCid(thisVo.getCid());
//            
//            try {
//                sqsTrackLogConnector.transferQaFailLog(qaVO);
//            }
//            catch ( SqsTrackLogException e ) {
//                log.debug("Tracking Log failure to send");
//            }
//            
            String backTrackType = "";
            //Back-Track 처리
            if(qaVO.getBackTrackType() != null && !"".equals(qaVO.getBackTrackType())) {
                if("01".equals(qaVO.getBackTrackType())) {
                    backTrackType = "Encoding";
                }else if("02".equals(qaVO.getBackTrackType())) {
                    backTrackType = "CMS";
                }
            }
            
            log.debug("backTrackType ============== "+backTrackType);
            log.debug("category ============= "+category);
            log.debug("OrderId ================ "+qaVO.getOrderId());
            log.debug("qaId ================ "+qaId);
            
//            suspensionVO.setServiceID(svID);
//            suspensionVO.setTargetSystem(backTrackType);
//            suspensionVO.setCate_depth1(category);
//            suspensionVO.setOrderID(qaVO.getOrderId());
//            suspensionVO.setQa_id(qaId);
//            suspensionVO.setCreateUser(qaVO.getLoginUserId());
//            suspensionVO.setReason(qaVO.getDescription());
//            suspensionVO.setCountry(qaVO.getTerritory());
//            
//            Integer result = suspensionService.createSuspension(suspensionVO);
//            
//            if(result == 2) {
//                log.debug("Content for orderID information that does not exist.");
//            }
        }
        
        if(qaVO.getQaResult() != null && !"".equals(qaVO.getQaResult())) {
            //필요데이타 조회
            QaVO thisVo = getQaContent(qaVO);
            qaVO.setOrderId(thisVo.getOrderId());
            qaVO.setUrgentFlag(thisVo.getUrgentFlag());
            qaVO.setProductId(thisVo.getProductId());
            qaVO.setCid(thisVo.getCid());
            qaVO.setQaId(thisVo.getQaId());
            qaVO.setServiceId(svID);
            
//            if("PASS".equals(qaVO.getQaResult())) {
//                try {
//                    sqsTrackLogConnector.transferQaPassLog(qaVO);
//                }
//                catch ( SqsTrackLogException e ) {
//                    log.debug("Tracking Log failure to send");
//                }
//            }else if("PASS_REVIS".equals(qaVO.getQaResult())) {
//                try {
//                    sqsTrackLogConnector.transferQaRpassLog(qaVO);
//                }
//                catch ( SqsTrackLogException e ) {
//                    log.debug("Tracking Log failure to send");
//                }
//            }
        }
        
        
        return 1;
    }
    
    public ContentVO getContentInfo(QaVO qaVO) {
        
        return qaDAO.getContentInfo(qaVO);
    }
    
    public List<FileSizeVO> getFileSizeInfo(QaVO qaVO) {
        
        return qaDAO.getFileSizeInfo(qaVO);
    }

    
    @Override
    public List<QaItemVO> getQaItemHisList(QaItemVO qaVO) {
        return qaDAO.getQaItemHisList(qaVO);
    }    
        
    
    @Override
    public QaResultImportResultVO qaResultListExcelUpload(InputStream inputStream) throws Exception {
        
        QaResultImportResultVO resultVo = new QaResultImportResultVO();
        
        //excel inputStream parsing
//        List<QaVO> qaUploadList = qaResultExcelParsingUtil.parsingExcelFile(inputStream);
        
        //각 avail content validation check
//        for(QaVO content : qaUploadList) {
//            
//            QaVO dbvo = qaDAO.getQaContent(content);
//            if(dbvo == null || dbvo.getQaId() == null) {
//                content.setReturnMsg("qa id not exists.");
//                resultVo.addValidFailList(content);
//            } else {
//                //suspension에 필요한 데이터 채움.
//                content.setCurLibType(dbvo.getCurLibType());
//                content.setOrderId(dbvo.getOrderId());
//                content.setCpId(dbvo.getCpId());
//                content.setEncodeVendor(dbvo.getEncodeVendor());
//                content.setContType(dbvo.getContType());
//                content.setContTitle(dbvo.getContTitle());
//                content.setTerritory(dbvo.getTerritory());
//                content.setUrgentFlag(dbvo.getUrgentFlag());
//                log.debug("content.getCurLibType():" + content.getCurLibType());
//                log.debug("content.getOrderId():" + content.getOrderId());
//                log.debug("content.getCpId():" + content.getCpId());
//                log.debug("content.getEncodeVendor():" + content.getEncodeVendor());
//                log.debug("content.getContType():" + content.getContType());
//                log.debug("content.getContTitle():" + content.getContTitle());
//                log.debug("content.getTerritory():" + content.getTerritory());
//                log.debug("content.getUrgentFlag():" + content.getUrgentFlag());
//                
//                if(!"".equals(StringUtil.nvl(dbvo.getQaResult()))) {
//                    content.setReturnMsg("already qa processed. please re qa request first.");
//                    resultVo.addValidFailList(content);
//                } else {
//                    content.setQaId(dbvo.getQaId());
//                    resultVo.addValidSuccessList(content);
//                }
//            }
//        }
        
        //validation check success list db 등록
        for(QaVO content : resultVo.getValidSuccessList()) {
            try {
                modifyQaUpdateResultTransaction(content);
            } catch (Exception e) {
                content.setReturnMsg("system error");
                resultVo.addDbFailList(content);
            }            
        }
        
        log.debug("######################## excel file upload end!!");
        
        return resultVo;
    }

    
    @Override
    public void createQaContentsMulti(QaVO qaVO) {
        
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
            QaVO thisVo = getQaContent(qaVO);
            if(thisVo != null && thisVo.getQaId() != null) {
                
                log.debug("thisVo.getCurLibType():" + thisVo.getCurLibType());
                log.debug("thisVo.getOrderId():" + thisVo.getOrderId());
                log.debug("thisVo.getCpId():" + thisVo.getCpId());
                log.debug("thisVo.getEncodeVendor():" + thisVo.getEncodeVendor());
                log.debug("thisVo.getContType():" + thisVo.getContType());
                log.debug("thisVo.getContTitle():" + thisVo.getContTitle());
                log.debug("thisVo.getTerritory():" + thisVo.getTerritory());
                log.debug("thisVo.getUrgentFlag():" + thisVo.getUrgentFlag());
                
                createQaHisTransaction(thisVo, qaVO, 0);
            } else {
                createQaTransaction(qaVO);
            }
        }
        
    }

    @Override
    public String createQaResultMulti(QaVO qaVO) {
        
        String msg = "";
        
        for(String qaId : qaVO.getArrQaId()) {
            qaVO.setQaId(qaId);
            
            //필요데이타 조회
            QaVO thisVo = getQaContent(qaVO);
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
                createQaHisTransaction(thisVo, qaVO, 1);
            } else {
                modifyQaUpdateResultTransaction(qaVO);
            }
        }
        
        return msg;
    }
    
    

}
