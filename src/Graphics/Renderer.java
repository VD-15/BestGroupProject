package Graphics;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.GLBuffers;

public class Renderer
{
	private VertexBuffer vertexBuffer;
	private int indexBuffer;
	private int vertexShader;
	
	private static final float[] VERTICIES = 
	{
		-0.5f, -0.5f,  0.0f,
		 0.0f,  0.5f,  0.0f,
		 0.5f, -0.5f,  0.0f
	};
	
	public Renderer(GL4 gl)
	{
		gl.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		
		IntBuffer vbo = BufferObject.CreateBuffers(gl, 2);
		vertexBuffer = new VertexBuffer(gl, vbo.get(0));
		//indexBuffer = vbo.get(1);
		
		
		vertexBuffer.bind();
		vertexBuffer.data(FloatBuffer.wrap(VERTICIES), VERTICIES.length * 4);
	}
}
