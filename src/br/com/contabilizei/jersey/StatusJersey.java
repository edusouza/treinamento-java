package br.com.contabilizei.jersey;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/status")
public class StatusJersey {

    @GET
    @Path("/ping")
    @Produces(
        { MediaType.APPLICATION_JSON })
    public Response ping() {

        Map<String, String> resp = new HashMap<>();
        resp.put("name", "Treinamento");
        resp.put("version", "1.0.0-SNAPSHOT");
        resp.put("environment", "PROD");
        resp.put("creation time", "");

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
		try {
			json = mapper.writeValueAsString(resp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        

        return createResponse(json);
    }

    @GET
    @Path("/pingAuth")
    @Produces(
        { MediaType.APPLICATION_JSON })
    public Response pingAuth() {

        return ping();
    }
    
    private Response createResponse(final String json) {

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
                .allow("OPTIONS")
                .build();
    }

}
