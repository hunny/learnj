package hh.learnj.log4j;

/**
 * 持有tracingId的线程池
 * @author huzexiong
 * @since 0.3
 */
public class TracingPool {    
  
  private static final ThreadLocal<String> TRACING_THREAD_LOCAL = new ThreadLocal<String>();

  public static void setTracingId(String tracingId) {
    TRACING_THREAD_LOCAL.set(tracingId);
  }

  public static String getTracingId() {
    return TRACING_THREAD_LOCAL.get();
  }
  
}
