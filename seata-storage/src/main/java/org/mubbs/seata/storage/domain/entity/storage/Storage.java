package org.mubbs.seata.storage.domain.entity.storage;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "storage")
public class Storage {
    @Id
    @Column(name = "storage_id")
    private Integer storageId;

    private String goods;

    @Column(name = "storage_count")
    private Integer storageCount;
}