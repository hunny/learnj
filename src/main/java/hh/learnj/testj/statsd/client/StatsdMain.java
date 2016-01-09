package hh.learnj.testj.statsd.client;

import java.util.Random;

import org.apache.log4j.Logger;

public class StatsdMain {

	private static final Logger logger = Logger.getLogger(StatsdClient.class.getName());

	public static void main(String[] args) throws Exception {
		logger.info("Get started.");
		StatsdClient client = new StatsdClient("192.168.102.75", 8125);
		run(client);
		logger.info("Exe over.");
	}
	
	public static void run(final StatsdClient client) {
		for (int i = 0; i < 200 * 6000; i++) {
//			client.increment("mtcounting", 3);
			client.timing("mtesttiming", 1);
			// To enable multi metrics (aka more than 1 metric in a UDP packet) (disabled by default)
			client.enableMultiMetrics(true); // disable by passing in false
			// To fine-tune udp packet buffer size (default=1500)
			client.setBufferSize((short) 1500);
			// To force flush the buffer out (good idea to add to your shutdown path)
			client.flush();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void runByThread(final StatsdClient client) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					int c = new Random().nextInt(10);
					client.increment("mtcounting", c);
					// To enable multi metrics (aka more than 1 metric in a UDP packet) (disabled by default)
					client.enableMultiMetrics(true); // disable by passing in false
					// To fine-tune udp packet buffer size (default=1500)
					client.setBufferSize((short) 1500);
					// To force flush the buffer out (good idea to add to your shutdown path)
					client.flush();
					try {
						int i = new Random().nextInt(10) * 1000;
						logger.info("next:" + i);
						Thread.sleep(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
