package com.leo.platform.upms.dao;

import java.util.Date;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.upms.entity.calendar.Calendar;

/**
 * xxDao主要是实现了对应实体的Dao，具有业务针对性
 * 
 * @author Administrator
 * 
 */
public interface CalendarDao extends BaseDao<Calendar, Long> {
    /* @Query("select count(id) from Calendar where userId=?1 and ((startDate=?2 and (startTime is null or startTime<?3)) or (startDate > ?2 and startDate<=(?2+?4)) or (startDate<?2 and (startDate+length)>?2) or ((startDate+length)=?2 and (endTime is null or endTime>?3)))")*/
    public Long countRecentlyCalendar(Long userId, Date nowDate, Date nowTime, Integer interval);
}
