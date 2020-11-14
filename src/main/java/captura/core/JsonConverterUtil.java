package captura.core;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonConverterUtil {

    public static JSONArray toJsonArray(String json) {
        return new JSONArray(json);
    }

    public static JSONObject toJson(String json) {
        return new JSONObject(json);
    }
}
