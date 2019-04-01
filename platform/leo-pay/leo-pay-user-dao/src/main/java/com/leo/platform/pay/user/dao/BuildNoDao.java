package com.leo.platform.pay.user.dao;

import com.leo.platform.pay.common.dao.BaseDao;
import com.leo.platform.pay.user.entity.SeqBuild;

/**
 * 生成编号dao
 */
public interface BuildNoDao extends BaseDao<SeqBuild> {

    public String getSeqNextValue(SeqBuild seqBuild);

}
