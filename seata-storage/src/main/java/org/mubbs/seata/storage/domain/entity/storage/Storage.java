package org.mubbs.seata.storage.domain.entity.storage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("storage")
public class Storage {

    @TableId(value = "storage_id", type = IdType.AUTO)
    private Integer storageId;

    private String goods;

    @TableField("storage_count")
    private Integer storageCount;
}