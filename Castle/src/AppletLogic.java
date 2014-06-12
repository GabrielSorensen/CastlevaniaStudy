import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class AppletLogic implements Runnable {

	private boolean isRunning = false;
	private boolean isPaused = false;
	private Canvas display_parent;

	public AppletLogic(Canvas display_parent) {

	}
	@Override
	public void run() {
		try {
			//start game


			//parse prefs and create them if non existant

			Display.sync(60);//get this framereate from the preferences file.
			Display.setParent(display_parent);
			Display.create();
			initGL();

			setRunning(true);

			//game Loop
			resume:
				while (isRunning) {
					Display.setDisplayMode(new DisplayMode(display_parent.getWidth(), display_parent.getHeight()));

					Display.update();
					if (isPaused) {
						break resume;
					}
					if (Display.isCloseRequested()) {
						break;
					}
				}




			//eventually
			Display.destroy();
		} catch (LWJGLException e) {
			System.err.println("error creating display..");
		}
	}

	public void pause() {

	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
	public void initGL() {

	}
	public void killSwitch() {
		Display.destroy();
	}
}
