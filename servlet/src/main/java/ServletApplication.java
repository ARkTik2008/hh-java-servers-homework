import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class ServletApplication {

  //  Использует только сервлеты. Нужно написать приложение-счетчик – хранить в памяти состояние,
//  котороое изменяется через HTTP походы.
//  Используйте подходящие коды HTTP ответов из лекции по HTTP
//
//  GET: /counter - возвращает счетчик
//
//  POST: /counter - увеличивает счетчик на 1
//
//  DELETE: /counter - уменьшает счетчик на значение,
//  которое передается в заголовке «Subtraction-Value»
//
//  POST: /counter/clear - сбрасывает счетчик. Для выполнения этого запроса должна
//  быть cookie - «hh-auth», единственное условие - она должна быть длиннее 10 символов.
  final private static int PORT = 8081;

  private static Server createServer(int port) {
    Server server = new Server(port);
    ServletHandler servletHandler = new ServletHandler();
    servletHandler.addServletWithMapping(CounterServlet.class, "/");
    servletHandler.addFilterWithMapping(AuthFilter.class, "/counter/clear", EnumSet.of(DispatcherType.REQUEST));
    server.setHandler(servletHandler);
    return server;
  }


  public static void main(String[] args) throws Exception {
    // run, Jetty, run!
    Server server = createServer(PORT);
    server.start();
    server.join();
  }
}
