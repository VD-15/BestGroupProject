package Graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import com.jogamp.opengl.GL3;

/**
 * <h1>Renderer</h1>
 * Used to render geometry using an OpenGL context.
 * We're using OpenGL 3.3 for compatibility reasons,
 * it was released WAY back in 2010, so it's safe 
 * to assume most devices made in the last decade
 * can use it. The last thing I want is for us to
 * submit this and get marked down because someone
 * has an obscure hardware configuration.
 * <p>
 * 
 * @author Group 26
 * @version 1.0
 * @since 28-02-2019
 */
public class Renderer
{
	/**
	 * The OpenGL context the renderer will use for all its operations.
	 */
	//private GL3 gl;
	private VertexArrayObject vao;
	
	/**
	 * Creates a renderer with the given GL context.
	 * @param gl
	 */
	public Renderer(GL3 _gl)
	{
		//gl = _gl;
		
		vao = new VertexArrayObject(_gl);
	}
	
	public void draw(GL3 gl)
	{
		gl.glBindVertexArray(vao.getHandle());
		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, vao.getElementBuffer());
		gl.glBindTexture(GL3.GL_TEXTURE_2D, vao.getTexture());
		gl.glDrawElements(GL3.GL_TRIANGLES, 6, GL3.GL_UNSIGNED_INT, 0);
		gl.glBindVertexArray(0);
	}
}
