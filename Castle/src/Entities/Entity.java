package Entities;



public interface Entity {

    public void draw();

    public void update(int delta);

    public void setLocation(float x, float y);

    public void setX(float x);

    public void setY(float y);

    public void setWidth(float width);

    public void setHeight(float height);

    public float getX();
    
    public float getCenterX();

    public float getY();
    
    public float getCenterY();

    public float getHeight();

    public float getWidth();

    public boolean intersects(Entity other);
    
    public float getIntersectAt(Entity other, Entity primary);

}