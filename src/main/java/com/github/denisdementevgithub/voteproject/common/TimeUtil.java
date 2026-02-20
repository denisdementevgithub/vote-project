package com.github.denisdementevgithub.voteproject.common;

import java.time.*;

public class TimeUtil {
    public static Clock getClock(int hour, int minute, ZoneId zoneId) {
        LocalDateTime dateTime = LocalDateTime.now(zoneId);
        LocalDateTime fixDateTime = dateTime.withHour(hour).withMinute(minute);
        Instant fixInstant = fixDateTime.atZone(zoneId).toInstant();
        return Clock.fixed(fixInstant, zoneId);
    }
}
