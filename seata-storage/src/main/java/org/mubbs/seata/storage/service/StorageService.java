package org.mubbs.seata.storage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.mubbs.seata.storage.dao.storage.StorageMapper;
import org.mubbs.seata.storage.domain.entity.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    @Autowired
    private StorageMapper storageMapper;

    public void decStorage(String goodsName, Integer storage) {
        QueryWrapper<Storage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods", goodsName);
        Storage goods = storageMapper.selectOne(queryWrapper);

        if (goods.getStorageCount() < storage) {
            throw new RuntimeException("goods inventory shortage");
        }
        goods.setStorageCount(goods.getStorageCount() - storage);
        storageMapper.updateById(goods);
    }

    public Storage storageDetailByName(String goodsName) {
        QueryWrapper<Storage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods", goodsName);
        Storage goodsDetail = storageMapper.selectOne(queryWrapper);
        return goodsDetail;
    }
}
