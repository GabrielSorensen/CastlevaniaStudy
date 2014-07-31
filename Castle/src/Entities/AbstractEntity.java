package Entities;


import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

public abstract class AbstractEntity implements Entity {

	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected float AT;
	protected Texture texture;
	private final Rectangle hitbox = new Rectangle();


	public AbstractEntity(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	public float getCenterX() {
		return x+(width/2);
	}

	@Override
	public float getCenterY() {
		return y+(height/2);
	}
	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public boolean intersects(Entity other) {
		hitbox.setBounds((int) x, (int) y, (int) width, (int) height);
		return hitbox.intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
	}
	public float getIntersectAt(Entity other, Entity primary) {
		if (intersects(other)) {
			AT= other.getY() - primary.getY();
			return AT;
		}
		return AT;
	}

}