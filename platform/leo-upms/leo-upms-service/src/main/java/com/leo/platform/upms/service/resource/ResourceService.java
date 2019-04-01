package com.leo.platform.upms.service.resource;

import java.util.List;
import java.util.Set;

import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.resource.Resource;
import com.leo.platform.upms.entity.resource.tmp.Menu;
import com.leo.platform.upms.entity.user.User;

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
 * @date 2016年11月1日 上午9:56:36
 * 
 */
public interface ResourceService extends BaseService<Resource, Long> {

    /**
     * 得到真实的资源标识 即 父亲:儿子
     * 
     * @param resource
     * @return
     */
    public String findActualResourceIdentity(Resource resource);

    public List<Menu> findMenus(User user);

    public boolean hasPermission(Resource resource, Set<String> userPermissions);

    public boolean hasPermission(String permission, String actualResourceIdentity);

}
