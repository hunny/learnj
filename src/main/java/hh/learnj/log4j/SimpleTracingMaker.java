package hh.learnj.log4j;

import java.util.UUID;

/**
 * TracingId的生成算法实现类
 * <p>使用UUID.randomUUID()简单实现</p>
 * @author huzexiong
 * @since 0.3
 */
public class SimpleTracingMaker implements TracingMaker {

  @Override
  public String make() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

}
