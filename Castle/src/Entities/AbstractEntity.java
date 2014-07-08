package Entities;


import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

public abstract class AbstractEntity implements Entity {

	protected double x;
	protected double y;
	protected double width;
	protected double height;
	protected double AT;
	protected Texture texture;
	private final Rectangle hitbox = new Rectangle();


	public AbstractEntity(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	public double getCenterX() {
		return x+(width/2);
	}

	@Override
	public double getCenterY() {
		return y+(height/2);
	}
	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public boolean intersects(Entity other) {
		hitbox.setBounds((int) x, (int) y, (int) width, (int) height);
		return hitbox.intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
	}
	public double getIntersectAt(Entity other, Entity primary) {
		if (intersects(other)) {
			AT= other.getY() - primary.getY();
			return AT;
		}
		return AT;
	}

}