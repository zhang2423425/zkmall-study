package com.shop.zkmall.admin.service.ai;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.zkmall.commons.domain.ai.CereAiDefaultQuestion;

import java.util.List;

public interface CereAiDefaultQuestionService extends IService<CereAiDefaultQuestion> {
    
    /**
     * 查询所有默认问题
     * @return 问题列表
     */
    List<CereAiDefaultQuestion> listAll();
    
    /**
     * 根据ID查询问题
     * @param id 问题ID
     * @return 问题详情
     */
    CereAiDefaultQuestion getById(Long id);
    
    /**
     * 添加问题
     * @param question 问题对象
     * @return 是否成功
     */
    boolean addQuestion(CereAiDefaultQuestion question);
    
    /**
     * 更新问题
     * @param question 问题对象
     * @return 是否成功
     */
    boolean updateQuestion(CereAiDefaultQuestion question);
    
    /**
     * 删除问题
     * @param id 问题ID
     * @return 是否成功
     */
    boolean deleteById(Long id);
}