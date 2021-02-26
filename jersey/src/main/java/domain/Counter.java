package domain;

public class Counter {

  private static Counter instance;

  private Counter(){}

  private int counter = 10;

  public static Counter getInstance(){ // #3
    if(instance == null){
      instance = new Counter();
    }
    return instance;
  }

  public int getCounter() {
    return counter;
  }

  public void incrementCounter() {
    counter++;
  }

  public void decrementCounter(int decrement) {
    counter -= decrement;
  }

  public void clearCounter() {
    counter = 0;
  }
}
