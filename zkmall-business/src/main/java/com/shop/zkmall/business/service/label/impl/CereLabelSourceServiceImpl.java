/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.label.impl;

import com.github.pagehelper.PageHelper;
import com.shop.zkmall.business.dao.label.CereLabelSourceDAO;
import com.shop.zkmall.business.dao.label.CereShopLabelDAO;
import com.shop.zkmall.business.page.shop.LabelSource;
import com.shop.zkmall.business.param.label.LabelDeleteSourceParam;
import com.shop.zkmall.business.param.label.LabelGetSourceParam;
import com.shop.zkmall.business.param.label.LabelSaveSourceParam;
import com.shop.zkmall.business.service.label.CereLabelSourceService;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.label.CereLabelSource;
import com.shop.zkmall.commons.domain.label.CereShopLabel;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.StringUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereLabelSourceServiceImpl implements CereLabelSourceService {

    private final CereLabelSourceDAO cereLabelSourceDAO;

    private final CereShopLabelDAO cereShopLabelDAO;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    public List<CereLabelSource> findByLabelId(Long labelId) {
        return cereLabelSourceDAO.findByLabelId(labelId);
    }

    @Override
    public void deleteByLabelId(Long labelId) {
        cereLabelSourceDAO.deleteByLabelId(labelId);
    }

    @Override
    public void insertBatch(List<CereLabelSource> list) {
        cereLabelSourceDAO.insertBatch(list);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void saveSource(LabelSaveSourceParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        List<CereLabelSource> list = new ArrayList<>();
        if (!EmptyUtils.isEmpty(param.getImages())) {
            param.getImages().forEach((image -> {
                if (StringUtils.isNotEmpty(image)) {
                    CereLabelSource cereLabelSource = new CereLabelSource();
                    cereLabelSource.setLabelId(param.getLabelId());
                    cereLabelSource.setImage(image);
                    if (image.contains("mp4")) {
                        cereLabelSource.setLabelType(IntegerEnum.SOURCE_TYPE_VIDEO.getCode());
                    } else {
                        cereLabelSource.setLabelType(IntegerEnum.SOURCE_TYPE_IMAGE.getCode());
                    }
                    list.add(cereLabelSource);
                }
            }));
        }
        cereLabelSourceDAO.insertBatch(list);
        //新增日志
        cerePlatformLogService.addLog(user, "素材管理", "商户端操作", "上传素材", null, time);
    }

    @Override
    public List<LabelSource> getAllByLabel(LabelGetSourceParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        return cereLabelSourceDAO.getAllByLabel(param);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void deleteSource(LabelDeleteSourceParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //删除素材图片
        cereLabelSourceDAO.deleteByImageAndLabelId(param.getLabelId(), param.getImage());
        //查询店铺所属未分组标签
        CereShopLabel cereShopLabel = cereShopLabelDAO.findByShopIdNotGroup(param.getShopId());
        if (cereShopLabel == null) {
            //新增一个未分组标签
            cereShopLabel = new CereShopLabel();
            cereShopLabel.setShopId(param.getShopId());
            cereShopLabel.setLabelName("未分组");
            cereShopLabel.setCreateTime(time);
            cereShopLabelDAO.insert(cereShopLabel);
        }
        //把当前删除标签所属图片数据挂到未分组标签
        CereLabelSource cereLabelSource = new CereLabelSource();
        cereLabelSource.setLink(param.getLink());
        cereLabelSource.setImage(param.getImage());
        if (param.getImage().contains("jpg") || param.getImage().contains("png")) {
            cereLabelSource.setLabelType(IntegerEnum.SOURCE_IMAGE.getCode());
        } else {
            cereLabelSource.setLabelType(IntegerEnum.SOURCE_REDIO.getCode());
        }
        cereLabelSource.setLabelId(cereShopLabel.getLabelId());
        cereLabelSourceDAO.insert(cereLabelSource);
        //新增日志
        cerePlatformLogService.addLog(user, "素材管理", "商户端操作", "删除素材图片", null, time);
    }

    @Override
    public List<CereLabelSource> findByLabelIdAndType(Long labelId, Integer labelType) {
        return cereLabelSourceDAO.findByLabelIdAndType(labelId, labelType);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void updateSource(LabelSaveSourceParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        //根据素材地址查询之前的数据
        CereLabelSource labelSource = new CereLabelSource();
        labelSource.setImage(param.getImage());
        labelSource.setLabelId(param.getLabelId());
        cereLabelSourceDAO.updateData(labelSource);
        //新增日志
        cerePlatformLogService.addLog(user, "素材管理", "商户端操作", "素材更换绑定标签", labelSource.getLabelId(), time);
    }
}
