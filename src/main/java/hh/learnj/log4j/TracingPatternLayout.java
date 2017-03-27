/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2016，所有权利保留。
 * 
 * 项目名： dcommons-log
 * 文件名： TracingPatternLayout.java
 * 模块说明：    
 * 修改历史：
 * 2016-8-3 - Hu ZeXiong - 创建。
 */
package hh.learnj.log4j;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
   Extends {@link org.apache.log4j.PatternLayout} class, add new conversion pattern: z.
   <p>For example: Let the conversion pattern be <b>"%-5p [%z]: %m%n"</b> and assume
   that the log4j environment was set to use a TracingPatternLayout 
   and the tracingId is 1234567890 in current thread. Then the
   statements
   <pre>
   Category root = Category.getRoot();
   root.debug("Message 1");
   root.warn("Message 2");
   </pre>
   would yield the output
   <pre>
   DEBUG [1234567890]: Message 1
   WARN  [1234567890]: Message 2
   </pre>
   </p>
 * @author huzexiong
 * @since 0.3
 */
public class TracingPatternLayout extends PatternLayout {

  @Override
  protected PatternParser createPatternParser(String pattern) {
    return new TracingPatternParser(pattern);
  }

}
