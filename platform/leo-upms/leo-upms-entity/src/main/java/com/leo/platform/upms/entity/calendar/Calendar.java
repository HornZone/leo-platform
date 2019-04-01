package com.leo.platform.upms.entity.calendar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;

@Entity
@Table(name = "personal_calendar")
public class Calendar extends BaseBizEntity<Long> {
    /**
     * @Fields serialVersionUID : 序列号
     */
    private static final long serialVersionUID = 1L;

    /* 所属人 */
    @Column(name = "user_id")
    private Long userId;

    /* 标题 */
    @Column(name = "title")
    private String title;

    /* 详细信息 */
    @Column(name = "details")
    private String details;

    /* 开始日期 */
    @Column(name = "start_date")
    private Date startDate;

    /* 持续时间 */
    @Column(name = "length")
    private Integer length;

    /* 开始时间 */
    @Column(name = "start_time")
    private Date startTime;

    /* 结束时间 */
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "background_color")
    private String backgroundColor;
    @Column(name = "text_color")
    private String textColor;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
