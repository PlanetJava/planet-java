package net.planet.java.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {


    @Override
    public Date convertToDatabaseColumn(LocalDateTime attribute) {

        return (attribute != null) ? Date.from(ZonedDateTime.of(attribute, ZoneId.systemDefault()).toInstant()) : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {

        return dbData != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(dbData.getTime()), ZoneId.systemDefault()) : null;
    }
}
