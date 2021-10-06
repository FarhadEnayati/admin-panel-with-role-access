package com.project.utility.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.eloyzone.jalalicalendar.DateConverter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class CustomDateDeserializer extends StdDeserializer<Date> {

    private DateConverter dateConverter = new DateConverter();
    private ZoneId zoneId = ZoneId.of("Asia/Tehran");

    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException {
        String[] date = jsonparser.getText().split("/");
        LocalDate localDate = dateConverter.jalaliToGregorian
                (Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));

        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }
}