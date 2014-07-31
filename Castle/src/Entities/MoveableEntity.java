package Entities;


public interface MoveableEntity extends Entity {

    public float getDX();

    public float getDY();

    public void setDX(float dx);

    public void setDY(float dy);
}