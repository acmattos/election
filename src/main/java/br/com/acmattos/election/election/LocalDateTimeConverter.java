package br.com.acmattos.election.election;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author acmattos
 * @since 15/02/2018
 * TODO JAVADOC, UNIT TEST
 */
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
   @Override
   public Timestamp convertToDatabaseColumn(LocalDateTime datetime) {
      return null != datetime ? Timestamp.valueOf(datetime) : null;
   }

   @Override
   public LocalDateTime convertToEntityAttribute(Timestamp dbDatetime) {
      return null != dbDatetime ? dbDatetime.toLocalDateTime() : null;
   }
}
