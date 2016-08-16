package net.planet.java.util;

import com.rometools.rome.feed.synd.SyndEntry;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
public class DateUtils {

	public static LocalDateTime convertTo(Date date) {

		return date != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()) : null;
	}

	public static Date convertTo(LocalDateTime date) {

		return (date != null) ? Date.from(ZonedDateTime.of(date, ZoneId.systemDefault()).toInstant()) : null;
	}

	public static Date getLastModifiedDate(SyndEntry entry) {

		return (entry.getUpdatedDate() != null) ? entry.getUpdatedDate() : entry.getPublishedDate();
	}
}
