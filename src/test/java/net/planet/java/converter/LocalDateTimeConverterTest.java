package net.planet.java.converter;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
public class LocalDateTimeConverterTest {
    final static int YEAR = 2016;
    final static int MONTH = Calendar.AUGUST;
    final static int DAY_OF_MONTH = 2;
    final static int HOUR_OF_DAY = 14;
    final static int MINUTE = 59;
    final static int SECOND = 33;
    final static int NANOSECOND = 233;

    @Test
    public void givenLocalDateTime_convertToDate_shouldWork() throws Exception {
        LocalDateTime fromTime = LocalDateTime.of(YEAR, MONTH + 1, DAY_OF_MONTH, HOUR_OF_DAY,
                MINUTE, SECOND, NANOSECOND);
        LocalDateTimeConverter converter = new LocalDateTimeConverter();
        Date date = converter.convertToDatabaseColumn(fromTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        assertThat(calendar.get(Calendar.YEAR), is(YEAR));
        assertThat(calendar.get(Calendar.MONTH), is(MONTH));
        assertThat(calendar.get(Calendar.DAY_OF_MONTH), is(DAY_OF_MONTH));
        assertThat(calendar.get(Calendar.AM_PM), is(Calendar.PM));
        assertThat(calendar.get(Calendar.HOUR_OF_DAY), is(HOUR_OF_DAY));
        assertThat(calendar.get(Calendar.MINUTE), is(MINUTE));
        assertThat(calendar.get(Calendar.SECOND), is(SECOND));
    }

    @Test
    public void givenDate_convertToLocalDateTime_shouldWork() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, MONTH, DAY_OF_MONTH, HOUR_OF_DAY, MINUTE, SECOND);
        Date date = calendar.getTime();

        LocalDateTimeConverter converter = new LocalDateTimeConverter();
        LocalDateTime ldt = converter.convertToEntityAttribute(date);

        assertThat(ldt.getYear(), is(YEAR));
        assertThat(ldt.getMonthValue(), is(MONTH + 1));
        assertThat(ldt.getMinute(), is(MINUTE));
        assertThat(ldt.getSecond(), is(SECOND));
    }

}