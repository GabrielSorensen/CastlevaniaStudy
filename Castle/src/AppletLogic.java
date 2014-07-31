import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Enemies.En1;
import Entities.Entity;
import Entities.Player;
import static org.lwjgl.opengl.GL11.*;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class AppletLogic implements Runnable {

	private static enum State {
		Loading, Main_Menu, Game, Test
	}

	private State state = State.Loading;

	private int A;
	private int B;
	private int fps;
	private int result;	
	private long lastFPS;
	private long lastframe = 1;
	private int MONITOR_MAX_WIDTH = 0;
	private int MONITOR_MAX_HEIGHT = 0;
	private float MONITOR_ASPECT_RATIO = 0;
	private final int MONITOR_REFRESH_RATE = Display.getDesktopDisplayMode().getFrequency();

	private boolean isRunning = false;
	private boolean isPaused = false;
	private boolean debugging = true;

	@SuppressWarnings("unused")
	private Canvas display_parent;

	private static ArrayList<DisplayMode> modes;
	private DisplayMode MedDisplayMode;
	private static DisplayMode mode = new DisplayMode(800, 600);

	private static InputStream in;

	private String MONITOR_MAX_RESOLUTION_String = "not currently set";
	
	private Player player = null; //NOTE!!!!!!!!!!!!!!!!!
	//Refernces to other OpenGL Objects (any with openGL calls)
	//require openGL/Display 

	private ArrayList<point> points = new ArrayList<point>();	
	private ArrayList<Entity> entities = new ArrayList<Entity>();




	public AppletLogic(Canvas display_parent) {
		//dont run anything on instantiate object, just grab passed values and set up the thread.
		try {
			Display.setParent(display_parent);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	public AppletLogic() {
		//dont run anything on instantiate object, just grab passed values and set up the thread.
	}
	@Override
	public void run() {
		try {
			//parse prefs and create them if non existant
			lastFPS = getTime();
			initDisplay();
			initGL();
			setRunning(true);
			state = State.Main_Menu;
			//game Loop
			resume:
				while (isRunning) {
					while (isPaused) {
						//do stuff in a loop until ready to continue
						break resume;
					}
					frame(); // one round of logic and input
					Display.update(); //note, This also polls/updates the input but does not process it.
					render();
					Display.setTitle("Fps:  " + result);
					fps++;
					FPS();
					Display.sync(70);//get this framereate from the preferences file.
					if (!Display.isFullscreen() && Display.wasResized()) {
						checkWIndowSize();
					}
					if (Display.isCloseRequested()) {
						//we can save and clean things up here if we want to,
						//and we probably should.
						System.exit(0);
					}
				}
			//eventually
			Display.destroy();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(e.hashCode());
		}
	}
	private void frame() {
		try {
			switch (state) {
			case Loading:
				//we cant do much while loading, in fact nothing?
				break;
			case Main_Menu:
				//we are doing input polls to update values and then logic on those and other values.
				if (Mouse.next() ) {
					if (Mouse.isButtonDown(0)) {
						int x = Mouse.getX();
						int y = Mouse.getY();
						if (debugging) {
							System.out.println("Left Mouse down at: " + x + ","
									+ y);
							System.err.println("entities size: " + entities.size());
						}
						En1 e = new En1(x, y, 20, 20);
						entities.add(e);
					}
					if (Mouse.isButtonDown(1)) {
						int x = Mouse.getX();
						int y = Mouse.getY();
						if (debugging) {
							System.out.println("Right mouse down at:  " + x
									+ "," + y);
							System.err.println("points size: " + points.size());
						}
						point p = new point(x, y, 10);
						points.add(p);
					}

				}
				if (Keyboard.next()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_F && !Keyboard.isRepeatEvent() && Keyboard.getEventKeyState()) {
						if (!Display.isFullscreen()) {
							Display.setDisplayMode(MedDisplayMode);
							Display.setFullscreen(true);
							System.err.println("trying to enable fullscreen");
						} else {
							Display.setFullscreen(false);
							System.out.println("disabling fullscreen");
						}
					}
				}
				break;
			case Game:

				break;
			case Test:

				break;
			}
		} catch (LWJGLException e) {
			System.out.println("Crashed! Error: " + e.getMessage());
			System.exit(e.hashCode());
		}
	}
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//backgroundRender
		switch (state) {
		case Loading:
			glColor3f(1.0f, 0f, 0f);
			glRectf(0, 0, Display.getHeight(), Display.getWidth());
			break;
		case Main_Menu:
			glColor3f(0.0f, 1.0f, 0.0f);
			glRectf(0, 0, Display.getWidth(), Display.getHeight());
			break;
		case Game:
			glColor3f(0.0f, 0.0f, 1.0f);
			glRectf(0, 0, Display.getHeight(), Display.getWidth());
			break;
		case Test:
			glColor3f(1.0f, 0.0f, 1.0f);
			glRectf(0, 0, Display.getHeight(), Display.getWidth());
			break;
		}
		for (Entity e : entities) {
			if (e instanceof En1) {
				((En1) e).deltaDraw(getDelta());
			}
		}
		for (point p : points) {
			p.draw();
		}

	}
	private void checkWIndowSize() {
		try {
			int a = Display.getWidth();
			int b = Display.getHeight();
			if (a != A || b != B) {
				A = a;
				B = b;
				if (A < 400) {
					A = 400;
					mode = new DisplayMode(A, B);
					Display.setDisplayMode(mode);
				} else if (A > MONITOR_MAX_WIDTH) {
					A = 400;
					mode = new DisplayMode(A, B);
					Display.setDisplayMode(mode);
				}
				if (B < 300) {
					B = 300;
					mode = new DisplayMode(A, B);
					Display.setDisplayMode(mode);
				} else if (B > MONITOR_MAX_HEIGHT) {
					B = 300;
					mode = new DisplayMode(A, B);
					Display.setDisplayMode(mode);
				}
				if (debugging) {
					System.out.println(A + ":" + B);
				}
				DisplayMode displaymode = null;
				for (DisplayMode m : modes) {
					if (m.getWidth() == Display.getWidth() && m.getHeight() == Display.getHeight() && m.isFullscreenCapable()) {
						displaymode = m;
						Display.setDisplayMode(displaymode);
					}
				}
				if (displaymode == null) {
					mode = new DisplayMode(A, B);
					Display.setDisplayMode(mode);
				}
			}
		} catch (LWJGLException e) {
			System.err.println(e.getMessage());
			System.exit(e.hashCode());
		}
	}
	private void initDisplay () {
		try {
			//setup and finalize display
			Display.setResizable(true);
			Display.setDisplayMode(new DisplayMode(800, 600));
			//set our icon, idea credit: Chris Molini
			ByteBuffer [] imgs = new ByteBuffer[3];
			BufferedImage icon = ImageIO.read(AppletLogic.class.getResource("/assets/bat16.png"));
			imgs[0]= imageToByteBuffer(icon);
			icon = ImageIO.read(AppletLogic.class.getResource("/assets/bat32.png"));
			imgs[1]= imageToByteBuffer(icon);
			icon = ImageIO.read(AppletLogic.class.getResource("/assets/bat64.png"));
			imgs[2]= imageToByteBuffer(icon);
			Display.setIcon(imgs);
			Display.create();
			System.out.println("Open GL version: " + GL11.glGetString(GL11.GL_VERSION));
			modes = new ArrayList<DisplayMode>();
			MONITOR_MAX_RESOLUTION_String = "0";

			point resolution = new point(0, 0, 0);
			for (DisplayMode m : Display.getAvailableDisplayModes()) {
				if (m.toString().contains("32 @"+MONITOR_REFRESH_RATE+"Hz")) {
					if (debugging) {
						System.out.println(m.toString());
					}
					modes.add(m);
					String [] s = m.toString().split(" ");
					resolution = new point(Integer.parseInt(s[0]), Integer.parseInt(s[2]), 0);
					if (resolution.getX() > MONITOR_MAX_WIDTH) {
						MONITOR_MAX_RESOLUTION_String = m.toString();
						MONITOR_MAX_HEIGHT = Integer.parseInt(s[2]);
						MONITOR_MAX_WIDTH = Integer.parseInt(s[0]);
						MONITOR_ASPECT_RATIO = (float) MONITOR_MAX_WIDTH / MONITOR_MAX_HEIGHT;
					}
				}
			}
			MedDisplayMode = modes.get(3);
			checkWIndowSize();
			if (debugging) {
				System.out.println("Max rez is: "
						+ MONITOR_MAX_RESOLUTION_String
						+ ", and Monitor Aspect ratio is: "
						+ MONITOR_ASPECT_RATIO);
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(e.hashCode());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(e.hashCode());
		}
	}
	private void initGL() {
		//start our GL stuff here
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), MONITOR_ASPECT_RATIO, -1 * MONITOR_ASPECT_RATIO);
		glMatrixMode(GL_MODELVIEW);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	public ByteBuffer loadPNG(String location) {
		try {
			in = new FileInputStream(location);
			PNGDecoder decoder =  new PNGDecoder(in);
			if (debugging) {
				System.out.println("width= " + decoder.getWidth());
				System.out.println("height= " + decoder.getHeight());
			}
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, 4 * decoder.getWidth(), Format.BGRA);
			buffer.flip();
			in.close();
			return buffer;
		} catch (Exception e) {
			System.out.println("Crashed! Error: " + e.getMessage());
			System.exit(e.hashCode());
		}
		//if the buffer fails, return null. Hopefully because it will crash the app
		return null;
	}
	public ByteBuffer imageToByteBuffer(BufferedImage icon) {
		byte [] buffer = new byte[icon.getWidth() * icon.getHeight() * 4];
		int count = 0;
		for (int i = 0; i < icon.getHeight(); i++) {
			for (int j = 0; j < icon.getWidth(); j++) {
				int color = icon.getRGB(j, i);
				buffer [count + 0] = (byte) ((color << 8) >> 24);
				buffer [count + 1] = (byte) ((color << 16) >> 24);
				buffer [count + 2] = (byte) ((color << 24) >> 24);
				buffer [count + 3] = (byte) (color >> 24);
				count += 4;
			}
		}
		return ByteBuffer.wrap(buffer);
	}
	public long getTime() {
		return (Sys.getTime() * 1000 / Sys.getTimerResolution() );
	} 
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastframe );
		lastframe = time;
		return delta;
	}
	public void FPS() {
		if (getTime() - lastFPS > 1000) {
			result = fps;
			fps = 0;
			lastFPS += 1000;
		} 
	}
	public int getFPS() {
		return result;
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
	public void killSwitch() {
		Display.destroy();
		System.exit(666);
	}
}
