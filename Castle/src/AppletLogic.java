import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class AppletLogic implements Runnable {

	private boolean isRunning = false;
	private boolean isPaused = false;
	private Canvas display_parent;
	private boolean debugging = true;
	int A;
	int B;

	public AppletLogic(Canvas display_parent) {
		//dont run anything on instantiate object, just grab passed values and set up the thread.
	}
	public AppletLogic() {
		//dont run anything on instantiate object, just grab passed values and set up the thread.
	}
	@Override
	public void run() {
		try {
			//parse prefs and create them if non existant

			if (display_parent != null) {
				Display.setParent(display_parent);
			} else {
				Display.setParent(null);
			}
			Display.setResizable(true);
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
			initGL();
			setRunning(true);
			//game Loop
			resume:
				while (isRunning) {
					Display.update();
					Display.sync(60);//get this framereate from the preferences file.
					if (debugging) {
						int a = Display.getWidth();
						int b = Display.getHeight();
						if (a != A || b != B) {
							A = a;
							B = b;
							System.out.println(A + ":" + B);
						}
					}
					while (isPaused) {
						//do stuff in a loop until ready to continue
						break resume;
					}
					
					if (Display.isCloseRequested()) {
						System.exit(0);
					}
				}




			//eventually
			Display.destroy();
		} catch (LWJGLException e) {
			System.err.println("error creating display..");
			System.exit(-1);
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
		//start our GL stuff here
	}
	public void killSwitch() {
		Display.destroy();
	}
}
