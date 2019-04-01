package com.leo.platform.upms.service.calendar;

import com.leo.platform.server.BaseService;
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
 * @date 2017年1月2日 上午8:37:45
 * 
 */
public interface CalendarService extends BaseService<Calendar, Long> {

    public void copyAndRemove(Calendar calendar);

    // 2013 10 11 10-20 -3 > now
    // 10 11 10-19
    public Long countRecentlyCalendar(Long userId, Integer interval);
}
