package com.bccns.umsserviceweb.qa.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bccns.umsserviceweb.qa.vo.ContentVO;
import com.bccns.umsserviceweb.qa.vo.FileSizeVO;
import com.bccns.umsserviceweb.qa.vo.QaItemVO;
import com.bccns.umsserviceweb.qa.vo.QaVO;
import com.bccns.umsserviceweb.qa.vo.SearchQaVO;

@Repository
public class QaDAO {
	private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
    public List<QaVO> getQaRequestList(SearchQaVO searchQaVO) {
        return sqlSession.selectList("com.bccns.umsserviceweb.qa.dao.QaDAO.selectQaRequestList", searchQaVO);
    }
    
    public Integer getQaRequestListCount(SearchQaVO searchQaVO){
        return sqlSession.selectOne("com.bccns.umsserviceweb.qa.dao.QaDAO.selectQaRequestListCount", searchQaVO);
    }
    
    public List<QaVO> getQaContentsPopupList(SearchQaVO searchQaVO) {
        return sqlSession.selectList("com.bccns.umsserviceweb.qa.dao.QaDAO.selectQaContentsPopupList", searchQaVO);
    }
    
    public QaVO getQaContent(QaVO qaVO){
        return sqlSession.selectOne("com.bccns.umsserviceweb.qa.dao.QaDAO.selectQaContent", qaVO);
    }
    
    public Integer getQaItemMaxSeq(QaVO qaVO){
        return sqlSession.selectOne("com.bccns.umsserviceweb.qa.dao.QaDAO.selectQaItemMaxSeq", qaVO);
    }
    
    public Integer createQaContents(QaVO qaVO){
        return sqlSession.insert("com.bccns.umsserviceweb.qa.dao.QaDAO.insertQaContents", qaVO);
    }
    
    public Integer createQaHistory(QaVO qaVO){
        return sqlSession.insert("com.bccns.umsserviceweb.qa.dao.QaDAO.insertQaHistory", qaVO);
    }
    
    public Integer createQaItems(QaVO qaVO){
        return sqlSession.insert("com.bccns.umsserviceweb.qa.dao.QaDAO.insertQaItems", qaVO);
    }
    
    public Integer createQaItemsHis(QaVO qaVO){
        return sqlSession.insert("com.bccns.umsserviceweb.qa.dao.QaDAO.insertQaItemsHis", qaVO);
    }
    
    public List<QaItemVO> getQaItemList(QaItemVO qaVO){
        return sqlSession.selectList("com.bccns.umsserviceweb.qa.dao.QaDAO.selectQaItemList",  qaVO);
    }
    
    public List<QaItemVO> getQaItemHisList(QaItemVO qaVO){
        return sqlSession.selectList("com.bccns.umsserviceweb.qa.dao.QaDAO.selectQaItemHisList",  qaVO);
    }
    
    public Integer modifyNewQaContents(QaVO qaVO){
        return sqlSession.update("com.bccns.umsserviceweb.qa.dao.QaDAO.updateNewQaContents", qaVO);
    }
    
    public Integer modifyResultQaContents(QaVO qaVO){
        return sqlSession.update("com.bccns.umsserviceweb.qa.dao.QaDAO.updateResultQaContents", qaVO);
    }
    
    public Integer removeQaItem(QaVO qaVO){
        return sqlSession.delete("com.bccns.umsserviceweb.qa.dao.QaDAO.deleteQaItem", qaVO);
    }
    
    public ContentVO getContentInfo(QaVO qaVO){
        return sqlSession.selectOne("com.bccns.umsserviceweb.qa.dao.QaDAO.selectContentInfo", qaVO);
    }
    
    public List<FileSizeVO> getFileSizeInfo(QaVO qaVO){
        return sqlSession.selectList("com.bccns.umsserviceweb.qa.dao.QaDAO.selectFileSizeInfo", qaVO);
    }    
}
