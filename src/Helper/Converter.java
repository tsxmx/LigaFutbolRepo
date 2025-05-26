package Helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converter {

    public static LocalDate datetimteToLocalDate(String dateTime)
    {
        String formato = "yyyy-MM-dd";
        DateTimeFormatter formater = DateTimeFormatter.ofPattern(formato);

        return LocalDate.parse(dateTime, formater);
    }

    public static int SqlMinutosToInt(String datetime)
    {
        return 0;
    }
}
