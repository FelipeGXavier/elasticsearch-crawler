package busca.infra;

import org.json.JSONObject;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        var response = new JSONObject();
        response.put("success", false);
        response.put("message", "Invalid arguments");
        return Response.status(400).entity(response.toString()).build();
    }
}
