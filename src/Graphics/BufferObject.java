package Graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.jogamp.opengl.GL4;

public abstract class BufferObject
{
	protected int buffer;
	protected GL4 gl;
	
	public final static IntBuffer CreateBuffers(GL4 gl, int count)
	{
		IntBuffer buffers = IntBuffer.allocate(count);
		gl.glGenBuffers(count, buffers);
		return buffers;
	}
	
	/**
	 * Binds the buffer to the GL_ARRAY_BUFFER.
	 * @param gl the GL machine to which the buffer belongs.
	 */
	public final void bind()
	{
		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, buffer);
	}
	
	/**
	 * Fills the buffer with data provided by the generic buffer.
	 * The buffer must have been bound first before it can be
	 * filled. By default, usage is GL_STATIC_DRAW, but this can
	 * be overridden.
	 * @param gl The GL machine to which the buffer belongs.
	 * @param data The data to fill the buffer with.
	 * @param size The size of the data in bytes.
	 * @see {@link Graphics.BufferObject#bind(GL4)}
	 */
	protected void data(Buffer data, long size, int usage)
	{
		gl.glBufferData(GL4.GL_ARRAY_BUFFER, size, data, usage);
	}
	
	/**
	 * Gets the internal GL array buffer object.
	 * @return The internal GL array buffer object.
	 */
	public final int getBuffer()
	{
		return buffer;
	}
}
