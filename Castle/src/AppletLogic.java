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
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;




public class AppletLogic implements Runnable {

	private boolean isRunning = false;
	private boolean isPaused = false;
	private Canvas display_parent;
//	private boolean debugging = true;
	int A;
	int B;
	private static DisplayMode mode = new DisplayMode(800, 600);
	private static InputStream in;
	private final int MONITOR_REFRESH_RATE = Display.getDesktopDisplayMode().getFrequency();
	private String MONITOR_MAX_RESOLUTION = "not currently set";
	private long lastframe = 1; 

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
			//setup and finalize display
			Display.setResizable(true);
			Display.setDisplayMode(mode);
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
			ArrayList<DisplayMode> modes = new ArrayList<DisplayMode>();
			MONITOR_MAX_RESOLUTION = "0";
			int maxWidth = 0;
			cartesianCordinates resolution = new cartesianCordinates(0, 0);
			for (DisplayMode m : Display.getAvailableDisplayModes()) {
				if (m.toString().contains("32 @"+MONITOR_REFRESH_RATE+"Hz")) {
					System.out.println(m.toString());
					modes.add(m);
					String [] s = m.toString().split(" ");
					resolution = new cartesianCordinates(Integer.parseInt(s[0]), Integer.parseInt(s[2]));
					if (resolution.getX() > maxWidth) {
						MONITOR_MAX_RESOLUTION = m.toString();
						maxWidth = Integer.parseInt(s[0]);
					}
				}
			}
			System.out.println("Max rezolution is: " + MONITOR_MAX_RESOLUTION);
			initGL();
			setRunning(true);
			//game Loop
			resume:
				while (isRunning) {
					frame(); // one round of logic and input
					Display.update(); //note, This also polls/updates the input but does not process it.
					Display.sync(MONITOR_REFRESH_RATE);//get this framereate from the preferences file.
					if (!Display.isFullscreen()) {
						checkWIndowSize();
					}

					while (isPaused) {
						//do stuff in a loop until ready to continue
						break resume;
					}

					if (Display.isCloseRequested()) {
						//we can save and clean things up here if we want to,
						//we probably should.
						System.exit(0);
					}
				}




			//eventually
			Display.destroy();
		} catch (LWJGLException e) {
			System.err.println("error creating display... \n" + e.getMessage());
			System.exit(e.hashCode());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(e.hashCode());
		}
	}

	private void frame() {
		//we are doing input polls to update values and then logic on those and other values.
		//INPUT FIRST!
		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = Mouse.getY();
			System.out.println("Mouse down at: " + x + "," + y);
		}
		
		
		
		
		
		//THEN LOGIC!
		
		
		
		
		
		
		
	
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
	public ByteBuffer loadPNG(String location) {
		try {
			in = new FileInputStream(location);
			PNGDecoder decoder =  new PNGDecoder(in);
			System.out.println("width= " + decoder.getWidth());
			System.out.println("height= " + decoder.getHeight());
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
				} else if (A > 3000) {
					A = 400;
					mode = new DisplayMode(A, B);
					Display.setDisplayMode(mode);
				}
				if (B < 300) {
					B = 300;
					mode = new DisplayMode(A, B);
					Display.setDisplayMode(mode);
				} else if (B > 3000) {
					B = 300;
					mode = new DisplayMode(A, B);
					Display.setDisplayMode(mode);
				}
				System.out.println(A + ":" + B);
			}
		} catch (LWJGLException e) {
			System.err.println(e.getMessage());
			System.exit(e.hashCode());
		}
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
