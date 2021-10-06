package com.project.utility.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDate;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Service
public class CustomDateSerializer extends StdSerializer<Date> {

    private DateConverter dateConverter = new DateConverter();
    private Calendar cal = Calendar.getInstance();

    public CustomDateSerializer() {
        this(null);
    }

    public CustomDateSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException {
        cal.setTime(value);
        JalaliDate jalaliDate = dateConverter.gregorianToJalali(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        gen.writeString(jalaliDate.format(new JalaliDateFormatter("yyyy/mm/dd", JalaliDateFormatter.FORMAT_IN_ENGLISH)));
    }
}