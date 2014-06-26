public class cartesianCordinates implements Comparable<cartesianCordinates> {

		public int X;
		public int Y;

		public cartesianCordinates (int x, int y) {
			this.X = x;
			this.Y = y;
		}
		public boolean equals (Object o) {
			if (!(o instanceof cartesianCordinates)) { //check to make sure we are actually using an ordered pair,
				return false;
			}
			cartesianCordinates op = (cartesianCordinates) o; //if true then check if the ordered pairs are equal.
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
		public int compareTo(cartesianCordinates o) {
			return (this.Y-o.Y);
		}
	}