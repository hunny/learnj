package hh.learnj.log4j;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 扩展{@link org.apache.log4j.helpers.PatternParser}类，增加Conversion pattern: z.
 * <p>
 * 例如：log4j.appender.ERRFILE.layout.ConversionPattern=[%z]
 * </p>
 * @author huzexiong
 * @since 0.3
 */
public class TracingPatternParser extends PatternParser {

  public TracingPatternParser(String pattern) {
    super(pattern);
  }
  
  protected void finalizeConverter(char c) {
    if (c == 'z' || c == 'Z') {
      super.addConverter(new TracingPatternConverter());
    } else {
      super.finalizeConverter(c);
    }
  }

  private static class TracingPatternConverter extends PatternConverter {
    
    private TracingMaker tracingMaker;
    
    public TracingPatternConverter() {
      tracingMaker = new SimpleTracingMaker();
    }
    
    /**
     * 如果没有TracingId就创建一个
     */
    public final void format(StringBuffer sbuf, LoggingEvent event) {
      String tracingId = TracingPool.getTracingId();
      if (null == tracingId) {
        tracingId = tracingMaker.make();
        TracingPool.setTracingId(tracingId);
      }
      sbuf.append(tracingId);
    }

    @Override
    protected String convert(LoggingEvent event) {
      return null;
    }
    
  }
  
}
