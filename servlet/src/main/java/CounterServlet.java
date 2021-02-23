import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@WebServlet(urlPatterns = "/counter")
public class CounterServlet extends HttpServlet {

  private static final String SERLVET_COUNTER = "/counter";
  private static final String SERVLET_COUNTER_CLEAR = "/counter/clear";

  private static int counter = 0;

  public void incrementCounter() {
    counter++;
  }

  public void decrementCounter(int decrement) {
    counter -= decrement;
  }

  public void clearCounter() {
    counter = 0;
  }

  public static boolean isNumeric(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(200);
    PrintWriter writer = resp.getWriter();
    writer.print(counter);
    writer.flush();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    processRequest(req, resp);
  }

  protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getServletPath();

    switch (path) {
      case SERLVET_COUNTER:
        this.incrementCounter();
        resp.setStatus(200);
        break;
      case SERVLET_COUNTER_CLEAR:
        this.clearCounter();
        resp.setStatus(200);
        break;
      default:
        break;
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (req.getHeader("Subtraction-Value") != null && isNumeric(req.getHeader("Subtraction-Value"))) {
      this.decrementCounter(req.getIntHeader("Subtraction-Value"));
      resp.setStatus(200);
    } else {
      resp.sendError(400);
    }
  }
}
