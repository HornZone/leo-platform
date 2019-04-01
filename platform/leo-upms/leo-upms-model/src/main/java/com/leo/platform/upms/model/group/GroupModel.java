package com.leo.platform.upms.model.group;

import com.leo.platform.common.model.page.BasePageModel;

public class GroupModel extends BasePageModel {
    private String name;
    private String type;
    private Boolean isShow;
    private Boolean defaultGroup;

    public GroupModel(Boolean isShow, Boolean defaultGroup) {
        this.isShow = isShow;
        this.defaultGroup = defaultGroup;
    }

    public GroupModel() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Boolean getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(Boolean defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

}
