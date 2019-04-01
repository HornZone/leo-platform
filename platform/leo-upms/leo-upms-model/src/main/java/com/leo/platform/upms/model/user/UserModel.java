package com.leo.platform.upms.model.user;

import java.util.Date;
import java.util.List;

import com.leo.platform.common.model.page.BasePageModel;
import com.leo.platform.upms.model.userorganizationjob.UserOrganizationJobModel;

/**
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
 * @date 2016年10月29日 下午6:16:06
 * 
 */
public class UserModel extends BasePageModel {
    private String username;
    private String email;
    private String mobilePhoneNumber;
    private String password;
    private String salt;
    private Date createDate;
    private String status;
    private Boolean deleted;
    private Boolean admin;
    private List<UserOrganizationJobModel> organizationJobModels;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<UserOrganizationJobModel> getOrganizationJobModels() {
        return organizationJobModels;
    }

    public void setOrganizationJobModels(List<UserOrganizationJobModel> organizationJobModels) {
        this.organizationJobModels = organizationJobModels;
    }

}
