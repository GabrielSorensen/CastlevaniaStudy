import java.util.EventListener;

import javax.swing.event.EventListenerList;


public interface stringListener extends EventListener{
	
	public String heySomethingHappened(stringEvent event);

}
