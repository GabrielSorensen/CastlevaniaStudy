import java.util.Date;
import java.util.EventObject;


public class stringEvent extends EventObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6008136013607470040L;
	private String date;
	private String whatHappened;

	public stringEvent(Object source, String thisHappened) {
		super(source);
		this.whatHappened = thisHappened;
		this.date = new Date().toString() + System.currentTimeMillis();
	}
	
	public String getTimeOfCreation() {
		return this.date;
	}
	public String whatHappened() {
		return this.whatHappened;
		
	}

}
