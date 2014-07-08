import org.lwjgl.opengl.GL11;

public class point implements Comparable<point> {

		public int X;
		public int Y;
		public int size;

		public point (int x, int y, int size) {
			this.X = x;
			this.Y = y;
			this.size = size;
		}
		public boolean equals (Object o) {
			if (!(o instanceof point)) { //check to make sure we are actually using an ordered pair,
				return false;
			}
			point op = (point) o; //if true then check if the ordered pairs are equal.
			return op.Y==this.Y;
		}
		public int hashCode () {
			return Y;
		}
		public void setX (int x) {
			this.X = x;
		}
		public int getX () {
			return this.X;
		}
		public void setY (int y) {
			this.Y = y;
		}
		public int getY () {
			return this.Y;
		}
		public void setCords (int x, int y) {
			this.X = x;
			this.Y = y;
		} 
		public String getCordsSpaceSeperated () {
			return (this.X + " " + this.Y);	
		}
		public String getCordsCommaSeperated () {
			return (this.X + "," + this.Y);	
		}
		public String toString () {
			return ("\n[" + this.X +" , "+ this.Y + "]");
		}
		@Override
		public int compareTo(point o) {
			return (this.Y-o.Y);
		}
		public void draw () {
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(0,0,0);
			GL11.glVertex2f(X, Y);
			GL11.glVertex2f(X+size, Y);
			GL11.glVertex2f(X+size, Y+size);
			GL11.glVertex2f(X, Y+size);
			GL11.glEnd();
		}
	}