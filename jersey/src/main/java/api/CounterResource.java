package api;

import domain.Counter;
import dto.CounterDTO;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/counter")
public class CounterResource {

  boolean isAuthCookieValid(String authCookie) {
    return authCookie.length() > 10;
  }

  @GET
  @Path("/")
  @Produces("application/json")
  public Response getCounter() {
    return Response.ok(new CounterDTO()).build();
  }

  @POST
  @Path("/")
  @Produces("application/json")
  public Response incrementCounter() {
    Counter.getInstance().incrementCounter();
    return Response.ok().build();
  }

  @DELETE
  @Path("/")
  @Produces("application/json")
  public Response decrementCounter(@HeaderParam("Subtraction-Value") int decrement) {
    Counter.getInstance().decrementCounter(decrement);
    return Response.ok().build();
  }

  @POST
  @Path("/clear")
  @Produces("application/json")
  public Response clearCounter(@CookieParam("hh-auth") String hhAuth) {
    if (hhAuth == null || !isAuthCookieValid(hhAuth)) {
      return Response.status(400).build();
    }

    Counter.getInstance().clearCounter();
    return Response.ok().build();
  }
}