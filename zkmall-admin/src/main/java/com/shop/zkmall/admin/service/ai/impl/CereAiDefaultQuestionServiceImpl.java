package com.shop.zkmall.admin.service.ai.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.zkmall.admin.dao.ai.CereAiDefaultQuestionDAO;
import com.shop.zkmall.admin.service.ai.CereAiDefaultQuestionService;
import com.shop.zkmall.commons.domain.ai.CereAiDefaultQuestion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereAiDefaultQuestionServiceImpl extends ServiceImpl<CereAiDefaultQuestionDAO, CereAiDefaultQuestion> implements CereAiDefaultQuestionService {
    
    private final CereAiDefaultQuestionDAO cereAiDefaultQuestionDAO;
    
    public CereAiDefaultQuestionServiceImpl(CereAiDefaultQuestionDAO cereAiDefaultQuestionDAO) {
        this.cereAiDefaultQuestionDAO = cereAiDefaultQuestionDAO;
    }
    
    @Override
    public List<CereAiDefaultQuestion> listAll() {
        return list();
    }
    
    @Override
    public CereAiDefaultQuestion getById(Long id) {
        return super.getById(id);
    }
    
    @Override
    public boolean addQuestion(CereAiDefaultQuestion question) {
        return save(question);
    }
    
    @Override
    public boolean updateQuestion(CereAiDefaultQuestion question) {
        return updateById(question);
    }
    
    @Override
    public boolean deleteById(Long id) {
        return removeById(id);
    }
}