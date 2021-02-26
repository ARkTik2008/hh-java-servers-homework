import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

//  Использует Jersey и JAX-RS аннотации, в т.ч для чтения cookie и заголовков.
//
//  Делает то же самое, что приложение 1, изменен только формат ответа для запроса
//  GET: /counter:
//  {
//    "date": "текущая дата-время в формате ISO",
//      "value": "значение счетчика"
//  }
//  Для сериализации должен использоваться Jackson
//  В лекции в примере с Jersey для конфигурирования ресурсов
//  мы использовали способ не из стандарта jax-rs
//  (мы использовали параметр jersey.config.server.provider.packages)
//  В домашке jersey-приложение должно использовать класс javax.ws.rs.core.Application


@ApplicationPath("/*")
public class JerseyApplication extends Application {

  final private static int PORT = 8081;

  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> classes = new HashSet<>();
    // register root resource
    classes.add(api.CounterResource.class);
    return classes;
  }

  private static Server createServer(int port) {
    Server server = new Server(port);



    ServletContextHandler ctx = new ServletContextHandler();
    server.setHandler(ctx);

    ServletHolder servletHolder = ctx.addServlet(ServletContainer.class, "/*");
    servletHolder.setInitOrder(0);
    servletHolder.setInitParameter("javax.ws.rs.Application", "JerseyApplication");
    return server;
  }

  public static void main(String[] args) throws Exception {
    // run, Jetty, run!
    Server server = createServer(PORT);
    server.start();
    server.join();


  }
}
