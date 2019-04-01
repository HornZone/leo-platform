package com.leo.platform.upms.service.calendar.impl;

import java.sql.Time;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.CalendarDao;
import com.leo.platform.upms.entity.calendar.Calendar;

/**
 * Caven_Zhou
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
 * @date 2017年1月2日 上午8:39:41
 * 
 */
@Service
public class CalendarServiceImpl extends BaseServiceImpl<Calendar, Long> {

    @Autowired
    @Qualifier("calendarDaoImpl")
    private CalendarDao calendarDao;

    public void copyAndRemove(Calendar calendar) {
        delete(calendar);

        Calendar copyCalendar = new Calendar();
        BeanUtils.copyProperties(calendar, copyCalendar);
        copyCalendar.setId(null);
        save(copyCalendar);
    }

    // 2013 10 11 10-20 -3 > now
    // 10 11 10-19
    public Long countRecentlyCalendar(Long userId, Integer interval) {
        Date nowDate = new Date();
        Date nowTime = new Time(nowDate.getHours(), nowDate.getMinutes(), nowDate.getSeconds());
        nowDate.setHours(0);
        nowDate.setMinutes(0);
        nowDate.setSeconds(0);

        return calendarDao.countRecentlyCalendar(userId, nowDate, nowTime, interval);
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(calendarDao);
    }
}
