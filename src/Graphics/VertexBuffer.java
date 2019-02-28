package Graphics;

import java.nio.Buffer;

import com.jogamp.opengl.GL4;

public class VertexBuffer extends BufferObject
{	
	public VertexBuffer(GL4 gl, int bufferObject)
	{
		this.buffer = bufferObject;
		this.gl = gl;
	}
	
	public void data(Buffer data, long size)
	{
		super.data(data, size, GL4.GL_STATIC_DRAW);
	}
}
