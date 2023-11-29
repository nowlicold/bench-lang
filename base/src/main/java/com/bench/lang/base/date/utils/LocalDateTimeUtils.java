package com.bench.lang.base.date.utils;

import java.time.*;
import java.util.Date;

/**
 * <p>
 * LocalDateTimeHelper
 * </p>
 *
 * @author Karl
 * @since 2023/10/10 10:03
 */
public class LocalDateTimeUtils {

    public static LocalDateTime convert(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static OffsetDateTime convert(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        return localDateTime.atOffset(offset);
    }

    public static LocalDateTime convert(Long timestamp) {
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        return Instant.ofEpochMilli(timestampInMilliseconds(timestamp))
                .atOffset(offset)
                .toLocalDateTime();
    }

    public static long timestampInMilliseconds(long timestamp) {
        String strTimestamp = String.valueOf(timestamp);
        if (strTimestamp.length() == 10) {
            // 如果是秒级时间戳，则转换为毫秒级时间戳
            return timestamp * 1000;
        } else if (strTimestamp.length() == 13) {
            // 如果已经是毫秒级时间戳，则原样返回
            return timestamp;
        }

        // 抛出异常，因为我们无法正确处理这种长度的时间戳
        throw new IllegalArgumentException("Invalid timestamp.");
    }

}
