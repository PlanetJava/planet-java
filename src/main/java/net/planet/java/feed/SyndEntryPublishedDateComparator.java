package net.planet.java.feed;

import com.rometools.rome.feed.synd.SyndEntry;

import java.util.Comparator;
import java.util.Date;

import static net.planet.java.util.DateUtils.getLastModifiedDate;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/16/16.
 */
public class SyndEntryPublishedDateComparator implements Comparator<SyndEntry> {

    @Override
    public int compare(SyndEntry entry1, SyndEntry entry2) {
        Date date1 = getLastModifiedDate(entry1);
        Date date2 = getLastModifiedDate(entry2);

        if (date1 != null && date2 != null) {
            return date1.compareTo(date2);
        }

        if (date1 == null && date2 == null) {
            return 0;
        }
        return (date2 == null) ? 1 : 0;
    }

}
