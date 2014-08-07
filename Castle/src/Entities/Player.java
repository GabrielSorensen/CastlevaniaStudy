package Entities;

import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Player  {

	//	private int [] stats = {10, 10, 10, 10, 10, 10}; //figure this out later.
	float[] vertices;
	private int vaoId = 0;
	private int vboId = 0;
	private int vboiId = 0;
	private int indicesCount = 0;
	private int delta;
	private float x;
	private float y;
	private float height;
	private float width;
	private int dx;
	private int dy;
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}
	public void MoveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void setDimentions (float width, float height) {
		this.width = width;
		this.height = height;
	}

	public Player(float x, float y, float width, float height) {
//		super(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		vertices = new float [] {
				x, y+height, 0f, 1f, 	// Left top			ID: 0
				x, y, 0f, 1f,	// Left bottom		ID: 1
				x+width, y, 0f, 1f,	// Right bottom		ID: 2
				x+width, y+height, 0f, 1f		// Right left		ID: 3
		};
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices);
		verticesBuffer.flip();
		byte[] indices = {
				// Left bottom triangle
				0, 3, 2,
				0, 2, 1
				// Right top triangle
				
		};
		indicesCount = indices.length;
		ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
		indicesBuffer.put(indices);
		indicesBuffer.flip();
		vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
		vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		vboiId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void draw() {
		update(delta);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL30.glBindVertexArray(vaoId);
		GL20.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
		GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	private void update(int delta2) {
		this.x += delta * dx;
        this.y += delta * dy;
        vertices = new float [] {
				x, y+height, 0f, 1f, 	// Left top			ID: 0
				x, y, 0f, 1f,	// Left bottom		ID: 1
				x+width, y, 0f, 1f,	// Right bottom		ID: 2
				x+width, y+height, 0f, 1f		// Right left		ID: 3
		};
	}

	public void destroyMe() {
		GL20.glDisableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboId);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboiId);
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoId);
		glDeleteVertexArrays(vaoId);
	}
	public void deltaDraw(int delta) {
		this.delta = delta;
		this.draw();
	}
}
