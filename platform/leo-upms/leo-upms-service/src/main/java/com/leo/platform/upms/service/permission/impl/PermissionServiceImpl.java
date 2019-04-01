package com.leo.platform.upms.service.permission.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.PermissionDao;
import com.leo.platform.upms.entity.permission.Permission;
import com.leo.platform.upms.service.permission.PermissionService;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2016年11月1日 上午9:38:32
 * 
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long> implements PermissionService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    @Qualifier("permissionDaoImpl")
    private PermissionDao permissionDao;

    @Override
    public void afterConstruct() {
        super.setBaseDao(permissionDao);
    }
}
