package net.planet.java.converter;

import net.planet.java.util.DateUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDateTime attribute) {

        return DateUtils.convertTo(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {

        return DateUtils.convertTo(dbData);
    }
}
