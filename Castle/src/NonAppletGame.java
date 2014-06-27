import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;




public class NonAppletGame {

	private static Thread gamethread;
	private static boolean running = false;
	private static boolean paused = false;

	public static void main (String [] args) {
		try {
			startLWJGL();
			while (running) {

			}
			stopLWJGL();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void startLWJGL() {
		try {
			gamethread = new Thread(new AppletLogic()); 
			running  = true;
			gamethread.start();
			while (running) {
				
			}
		} catch (Exception inter) {
			System.out.println("Something went wrong with the main logic thread! \nExiting: " + inter.hashCode());
		}
	}
	private static void stopLWJGL() {
		running = false;
		try {
			gamethread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(e.hashCode());
		}
	}
}