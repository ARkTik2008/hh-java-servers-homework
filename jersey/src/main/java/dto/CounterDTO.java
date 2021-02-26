package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import domain.Counter;
import java.util.Date;

public class CounterDTO {

  int value;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  Date date;

  public CounterDTO() {
    this.value = Counter.getInstance().getCounter();
    this.date = new Date(System.currentTimeMillis());
  }

  public int getValue() {
    return value;
  }

  public Date getDate() {
    return date;
  }
}
