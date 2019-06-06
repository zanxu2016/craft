package info.luckydog.craft.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;

/**
 * CommTools
 *
 * @author eric
 * @since 2019/6/6
 */
public class CommTools {

    public static String objectToJson(Object o) {
        return objectToJson(o, false);
    }

    public static String objectToJson(Object o, boolean escapeNonAscii) {
        StringWriter str = new StringWriter();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (escapeNonAscii) {
                objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            }
            objectMapper.writeValue(str, o);
            return str.toString();
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }
}
