package com.leo.platform.upms.service.resource.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.leo.platform.common.model.page.FieldOrder;
import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.ResourceDao;
import com.leo.platform.upms.entity.resource.Resource;
import com.leo.platform.upms.entity.resource.tmp.Menu;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.model.resource.ResourceModel;
import com.leo.platform.upms.service.auth.UserAuthService;
import com.leo.platform.upms.service.resource.ResourceService;

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
 * @date 2016年11月22日 下午10:04:24
 * 
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Long> implements ResourceService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    @Qualifier("resourceDaoImpl")
    private ResourceDao resourceDao;

    @Autowired
    @Qualifier("userAuthServiceImpl")
    private UserAuthService userAuthService;

    /**
     * 得到真实的资源标识 即 父亲:儿子
     * 
     * @param resource
     * @return
     */
    public String findActualResourceIdentity(Resource resource) {

        if (resource == null) {
            return null;
        }

        StringBuilder s = new StringBuilder(resource.getIdentity());

        boolean hasResourceIdentity = !StringUtils.isEmpty(resource.getIdentity());

        /* 直系父资源
         * 不停查找当前资源的父直系元素
         * 如果存在一个父直系元素有资源标志id（identity）就插入到stringbuilder中的第一列 */
        Resource parent = findOne(resource.getParentId());
        while (parent != null) {
            if (!StringUtils.isEmpty(parent.getIdentity())) {

                /* String参数的字符按顺序以指示的偏移量插入该StringBuilder中，向上移动最初高于该位置的任何字符，
                 * 并将该StringBuilder的长度增加该参数的长度。如果STR为NULL，则将“NULL”插入到该序列中。即 多生植物属： 水果：苹果：*/
                s.insert(0, parent.getIdentity() + ":");
                hasResourceIdentity = true;
            }
            parent = findOne(parent.getParentId());
        }

        // 如果用户没有声明 资源标识 且父也没有，那么就为空
        if (!hasResourceIdentity) {
            return "";
        }

        // 如果最后一个字符是: 因为不需要，所以删除之
        int length = s.length();
        if (length > 0 && s.lastIndexOf(":") == length - 1) {
            s.deleteCharAt(length - 1);
        }

        // 如果有儿子 最后拼一个*，只用来表示该资源有儿子，至于有多少儿子不得而知
        boolean hasChildren = false;
        for (Resource r : findAll()) {
            if (resource.getId().equals(r.getParentId())) {
                hasChildren = true;
                break;
            }
        }

        /*  多生植物属： 水果：苹果：* （苹果下有儿子资源青苹果）*/
        if (hasChildren) {
            s.append(":*");
        }

        return s.toString();
    }

    public List<Menu> findMenus(User user) {
        ResourceModel resourceModel = new ResourceModel();
        resourceModel.setIsShow(true);
        FieldOrder fo1 = new FieldOrder("parentId");
        FieldOrder fo2 = new FieldOrder("weight");
        List<FieldOrder> fos = new ArrayList<FieldOrder>();
        fos.add(fo1);
        fos.add(fo2);

        resourceModel.setOrderFields(fos);
        /*
        Searchable searchable =
            Searchable.newSearchable().addSearchFilter("show", SearchOperator.eq, true)
                .addSort(new Sort(Sort.Direction.DESC, "parentId", "weight"));*/

        List<Resource> resources = this.findAll(resourceModel);

        Set<String> userPermissions = userAuthService.findStringPermissions(user);

        Iterator<Resource> iter = resources.iterator();
        while (iter.hasNext()) {
            if (!hasPermission(iter.next(), userPermissions)) {
                iter.remove();
            }
        }

        return convertToMenus(resources);
    }

    public boolean hasPermission(Resource resource, Set<String> userPermissions) {
        String actualResourceIdentity = findActualResourceIdentity(resource);
        if (StringUtils.isEmpty(actualResourceIdentity)) {
            return true;
        }

        for (String permission : userPermissions) {
            if (hasPermission(permission, actualResourceIdentity)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasPermission(String permission, String actualResourceIdentity) {

        // 得到权限字符串中的 资源部分，如a:b:create --->资源是a:b
        String permissionResourceIdentity = permission.substring(0, permission.lastIndexOf(":"));

        // 如果权限字符串中的资源 是 以资源为前缀 则有权限 如a:b 具有a:b的权限
        if (permissionResourceIdentity.startsWith(actualResourceIdentity)) {
            return true;
        }

        /**
         * 模式匹配 Shiro对权限字符串缺失部分的处理 如“user:view”等价于“user:view:*”；而“organization”等价于“organization:*”或者“organization:*:*”。
         * 可以这么理解，这种方式实现了前缀匹配。 另外如“user:*”可以匹配如“user:delete”、 “user:delete”可以匹配如“user:delete:1”、
         * “user:*:1”可以匹配如“user:view:1”、 “user”可以匹配“user:view”或“user:view:1”等。 即*可以匹配所有，不加*可以进行前缀匹配；
         * 但是如“*:view”不能匹配“system:user:view”，需要使用“*:*:view”， 即后缀匹配必须指定前缀（多个冒号就需要多个*来匹配）。 wildcardPermission就是用于解决类似权限匹配的
         */
        WildcardPermission p1 = new WildcardPermission(permissionResourceIdentity);
        WildcardPermission p2 = new WildcardPermission(actualResourceIdentity);

        return p1.implies(p2) || p2.implies(p1);
    }

    @SuppressWarnings("unchecked")
    public static List<Menu> convertToMenus(List<Resource> resources) {

        if (resources.size() == 0) {
            return Collections.EMPTY_LIST;
        }

        /* 先转换第size()-1个资源成为一个menu
         * list.remove:
         * Removes the element at the specified position in this list (optional operation).
         * 从指定位置删除该元素（本例为size()-1位置） 
         * Shifts any subsequent elements to the left (subtracts one from their indices). 
         * 将任何后续元素向左移动（索引减一）
         * Returns the element that was removed from the list.
         * 返回该删除的元素 */
        Menu root = convertToMenu(resources.remove(resources.size() - 1));

        recursiveMenu(root, resources);
        List<Menu> menus = root.getChildren();
        removeNoLeafMenu(menus);

        return menus;
    }

    private static void removeNoLeafMenu(List<Menu> menus) {
        if (menus.size() == 0) {
            return;
        }
        for (int i = menus.size() - 1; i >= 0; i--) {
            Menu m = menus.get(i);
            removeNoLeafMenu(m.getChildren());

            /* 要删除的是，menus中，不存在儿子菜单资源，并且，当前菜单资源没有指向一个资源url的菜单，因为如果有儿子菜单资源，那么父菜单可以没有url，
             * 或者当前菜单资源有资源url但是没有儿子菜单资源，不能即没有儿子菜单资源，自己本身也没有指向资源url，这肯定是不对的 */
            if (!m.isHasChildren() && StringUtils.isEmpty(m.getUrl())) {
                menus.remove(i);
            }
        }
    }

    /* 遍历资源列表，将所有属于当前菜单资源的儿子资源，构造成儿子菜单资源放入到menu的children中 */
    private static void recursiveMenu(Menu menu, List<Resource> resources) {
        for (int i = resources.size() - 1; i >= 0; i--) {
            Resource resource = resources.get(i);
            if (resource.getParentId().equals(menu.getId())) {
                menu.getChildren().add(convertToMenu(resource));
                resources.remove(i);
            }
        }

        for (Menu subMenu : menu.getChildren()) {
            recursiveMenu(subMenu, resources);
        }
    }

    private static Menu convertToMenu(Resource resource) {
        return new Menu(resource.getId(), resource.getName(), resource.getIcon(), resource.getUrl());
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(resourceDao);
    }

}
