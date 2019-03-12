package Graphics;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL3;

public class Texture
{
	private BufferedImage image;
	private GL3 gl;
	private int id;
	
	public Texture(GL3 gl, BufferedImage image)
	{
		this.image = image;
		
		//Create a GL texture
		IntBuffer b = IntBuffer.allocate(1);
		gl.glGenTextures(1, b);
		this.id = b.get(0);
		
		//Read BufferedImage data
		int[] imageData = new int[this.image.getWidth() * this.image.getHeight() * 4];
		this.image.getRGB(0, 0, this.image.getWidth(), this.image.getHeight(), imageData, 0, this.image.getWidth());
		
		gl.glActiveTexture(GL3.GL_TEXTURE0);
		gl.glBindTexture(GL3.GL_TEXTURE_2D, this.id);
		gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, GL3.GL_RGBA8, this.image.getWidth(), this.image.getHeight(), 0, GL3.GL_BGRA, GL3.GL_UNSIGNED_BYTE, IntBuffer.wrap(imageData));
		gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_BORDER);
		gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_BORDER);
		gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR_MIPMAP_LINEAR);
		gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
		gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);
	}
	
	public int getID()
	{
		return id;
	}
	
	public int getWidth()
	{
		return image.getWidth();
	}
	
	public int getHeight()
	{
		return image.getHeight();
	}
	
	public void delete()
	{
		gl.glDeleteTextures(1, IntBuffer.wrap(new int[] {id}));
	}
}
