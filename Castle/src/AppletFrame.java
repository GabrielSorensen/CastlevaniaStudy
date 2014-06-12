import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;


public class AppletFrame extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Thread main;
	private Canvas display_parent;
	private AppletLogic logic = new AppletLogic(display_parent);

	public static void main(String[] args) {

	}
	public void init() {
		setLayout(new BorderLayout());
		try {
			display_parent = new Canvas() {
				public final void addNotify() {
					super.addNotify();
					startLWJGL();
				}
				public final void removeNotify() {
					stopLWJGL();
					super.removeNotify();
				}
			};
			display_parent.setSize(800,600);
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			setVisible(true);
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException("Error: " + e);
		}
	}
	public void startLWJGL() {
		logic = new AppletLogic(display_parent);
		main = new Thread(logic);
		main.start();
		logic.setRunning(true);	
	}


	/**
	 * Tell game loop to stop running, after which the LWJGL Display will 
	 * be destoryed. The main thread will wait for the Display.destroy().
	 */
	private void stopLWJGL() {
		logic.setRunning(false);
		try {
			main.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void start() {
		init();
	}
	public void stop() {
		destroy();
	} 
	public void destroy() {
		remove(display_parent);
		super.destroy();
	}
	public void pauseGame() {
		logic.pause();
	}
	public void stopGame() {
		logic.killSwitch();
	}


}
