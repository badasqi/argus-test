package com.argus.test.service.interfaces;

import java.time.format.DateTimeFormatter;

public interface ServiceConstants {
    interface DateFormats {
        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }
}
