package hh.learnj.enhance.thread.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Java 并发 API
 * 提供了一种允许2个并发任务间相互交换数据的同步应用。更具体的说，Exchanger类允许在2个线程间定义同步点，当2个线程到达这个点
 * ，他们相互交换数据类型，使用第一个线程的数据类型变成第二个的，然后第二个线程的数据类型变成第一个的。
 * 
 * @author hunnyhu
 *
 */
public class ExchangerTest {
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final Exchanger<String> exchanger = new Exchanger<String>();
		service.execute(new Runnable() {
			public void run() {
				try {
					String data1 = "零食";
					System.out.println("线程" + Thread.currentThread().getName()
							+ "正在把数据" + data1 + "换出去");
					Thread.sleep((long) (Math.random() * 1000));
					String data2 = exchanger.exchange(data1);
					System.out.println("线程" + Thread.currentThread().getName()
							+ "换回的数据为" + data2);
				} catch (Exception e) {
				}
			}
		});

		service.execute(new Runnable() {
			public void run() {
				try {
					String data1 = "钱";
					System.out.println("线程" + Thread.currentThread().getName()
							+ "正在把数据" + data1 + "换出去");
					Thread.sleep((long) (Math.random() * 1000));
					String data2 = exchanger.exchange(data1);
					System.out.println("线程" + Thread.currentThread().getName()
							+ "换回的数据为" + data2);
				} catch (Exception e) {
				}
			}
		});
	}

}
