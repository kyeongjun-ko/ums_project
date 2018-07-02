package com.bccns.umsserviceweb.qa.service;

import java.io.InputStream;
import java.util.List;

import com.bccns.umsserviceweb.qa.vo.ContentVO;
import com.bccns.umsserviceweb.qa.vo.FileSizeVO;
import com.bccns.umsserviceweb.qa.vo.QaItemVO;
import com.bccns.umsserviceweb.qa.vo.QaResultImportResultVO;
import com.bccns.umsserviceweb.qa.vo.QaVO;
import com.bccns.umsserviceweb.qa.vo.SearchQaVO;


public interface QaService {

    public List<QaVO> getQaRequestList(SearchQaVO searchQaVO);
    
    public Integer getQaRequestListCount(SearchQaVO searchQaVO);
    
    public List<QaVO> getQaContentsPopupList(SearchQaVO searchQaVO);
    
    public QaVO getQaContent(QaVO qaVO);
    
    public Integer createQaContents(QaVO qaVO);
    
    public Integer createQaHistory(QaVO qaVO);
    
    public Integer createQaItems(QaVO qaVO);
    
    public List<QaItemVO> getQaItemList(QaItemVO qaVO);
    
    public List<QaItemVO> getQaItemHisList(QaItemVO qaVO);
    
    public Integer modifyNewQaContents(QaVO qaVO);
    
    public Integer modifyResultQaContents(QaVO qaVO);
    
    
    public Integer createQaHisTransaction(QaVO qaHisVO, QaVO qaNewVO, int PassPart);
    
    public Integer createQaTransaction(QaVO qaVO);
    
    public Integer modifyQaUpdateResultTransaction(QaVO qaVO);
    
    public Integer removeQaItem(QaVO qaVO);
    
    public ContentVO getContentInfo(QaVO qaVO);
    
    public List<FileSizeVO> getFileSizeInfo(QaVO qaVO);
    
    public QaResultImportResultVO qaResultListExcelUpload(InputStream inputStream) throws Exception;
    
    //public QaResultImportResultVO qaResultListTextUpload(InputStream inputStream) throws Exception;
    
    public void createQaContentsMulti(QaVO qaVO);
    
    public String createQaResultMulti(QaVO qaVO);
    
}
