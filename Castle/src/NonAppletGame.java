

public class NonAppletGame {
	
	private static Thread gamethread;
	private static boolean running = false;
	
	public static void main (String [] args) {
		startLWJGL();
		while (running) {
			
		}
		stopLWJGL();
	}
	public static void startLWJGL() {
		gamethread = new Thread(new AppletLogic()); 
		running  = true;
		gamethread.start();
	}
	private static void stopLWJGL() {
		running = false;
		try {
			gamethread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}