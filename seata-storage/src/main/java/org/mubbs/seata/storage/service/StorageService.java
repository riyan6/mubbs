package org.mubbs.seata.storage.service;

import org.mubbs.seata.storage.dao.storage.StorageMapper;
import org.mubbs.seata.storage.domain.entity.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    @Autowired
    private StorageMapper storageMapper;

    public void decStorage(String goodsName, Integer storage) {
        Storage goods = storageMapper.selectOne(Storage.builder()
                .goods(goodsName)
                .build());
        if (goods.getStorageCount() < storage) {
            throw new RuntimeException("goods inventory shortage");
        }
        goods.setStorageCount(goods.getStorageCount() - storage);
        storageMapper.updateByPrimaryKeySelective(goods);
    }

}
