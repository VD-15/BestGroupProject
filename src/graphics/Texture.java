package graphics;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL3;

/**
 * Stores a reference to a texture image on the GPU
 * @author Vee
 */
public class Texture
{
	/**
	 * CPU-side copy of image
	 */
	private BufferedImage image;
	
	/**
	 * GL context to which the image belongs
	 */
	private GL3 gl;
	
	/**
	 * Texture ID of the image 
	 */
	private int id;
	
	/**
	 * Creates a Texture object for the given GL context
	 * @param gl the context using the texture
	 * @param image the image to make up the texture
	 */
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
		gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_NEAREST);
		gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_NEAREST);
		gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);
	}
	
	/**
	 * Get the internal texture ID
	 * @return the ID of the texture assigned by the GL
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Get the width of the texture
	 * @return the width of the texture, in pixels
	 */
	public int getWidth()
	{
		return image.getWidth();
	}
	
	/**
	 * Get the height of the texture
	 * @return the height of the texture, in pixels
	 */
	public int getHeight()
	{
		return image.getHeight();
	}
	
	/**
	 * Deletes the texture
	 */
	public void delete()
	{
		gl.glDeleteTextures(1, IntBuffer.wrap(new int[] {id}));
	}
}
