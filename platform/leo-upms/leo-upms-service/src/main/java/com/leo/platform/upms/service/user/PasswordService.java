package com.leo.platform.upms.service.user;

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
 * @date 2016年10月15日 下午10:26:22
 * 
 */
public interface PasswordService {

    public void validate(User user, String password);

    public boolean matches(User user, String newPassword);

    public void clearLoginRecordCache(String username);

    public String encryptPassword(String username, String password, String salt);

}
