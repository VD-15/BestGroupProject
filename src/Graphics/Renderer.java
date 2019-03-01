package Graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import com.jogamp.opengl.GL4;

public class Renderer
{	
	private static final float[] VERTICIES = 
	{
		-0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
		 0.0f,  0.5f, 0.0f, 1.0f, 0.0f,
		 0.5f, -0.5f, 0.0f, 0.0f, 1.0f
	};
	
	public Renderer(GL4 gl)
	{
		//Set the clear color to black
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		//Generate vertex buffer object
		IntBuffer vbo = IntBuffer.allocate(1);
		gl.glGenBuffers(1,  vbo);
		
		//Fill vertex buffer with data
		int vertexBuffer = vbo.get(0);
		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexBuffer);
		gl.glBufferData(GL4.GL_ARRAY_BUFFER, VERTICIES.length * 4, FloatBuffer.wrap(VERTICIES), GL4.GL_STATIC_DRAW);
		
		String fragSource = loadFile("shaders/shader.frag");
		String vertSource = loadFile("shaders/shader.vert");
		
		//create vertex shader object
		int vertexShader = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
		gl.glShaderSource(vertexShader, 1, new String[] {vertSource}, IntBuffer.wrap(new int[] {vertSource.length()}));
		gl.glCompileShader(vertexShader);
		
		//Check compile status
		{
			IntBuffer shaderSuccess = IntBuffer.allocate(1);
			gl.glGetShaderiv(vertexShader, GL4.GL_COMPILE_STATUS, shaderSuccess);
			
			if (shaderSuccess.get(0) != GL4.GL_TRUE)
			{
				System.out.println("Vertex shader compilation failed: ");
				ByteBuffer b = ByteBuffer.allocate(512);
				gl.glGetShaderInfoLog(vertexShader, 512, null, b);
				System.out.println(new String(b.array(), StandardCharsets.UTF_8));
			}
		}
		
		int fragmentShader = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);
		gl.glShaderSource(fragmentShader, 1, new String[] {fragSource}, IntBuffer.wrap(new int[] {fragSource.length()}));
		gl.glCompileShader(fragmentShader);
		
		//Check compile status
		{
			IntBuffer shaderSuccess = IntBuffer.allocate(1);
			gl.glGetShaderiv(fragmentShader, GL4.GL_COMPILE_STATUS, shaderSuccess);
			
			if (shaderSuccess.get(0) != GL4.GL_TRUE)
			{
				System.out.println("Fragment shader compilation failed: ");
				ByteBuffer b = ByteBuffer.allocate(512);
				gl.glGetShaderInfoLog(fragmentShader, 512, null, b);
				System.out.println(new String(b.array(), StandardCharsets.UTF_8));
			}
		}
		
		int shaderProgram = gl.glCreateProgram();
		gl.glAttachShader(shaderProgram, vertexShader);
		gl.glAttachShader(shaderProgram, fragmentShader);
		gl.glLinkProgram(shaderProgram);
		gl.glUseProgram(shaderProgram);

		IntBuffer vao = IntBuffer.allocate(1);
		gl.glGenVertexArrays(1, vao);
		gl.glBindVertexArray(vao.get(0));
		
		gl.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, 5 * 4, 0);
		gl.glVertexAttribPointer(1, 3, GL4.GL_FLOAT, false, 5 * 4, 2 * 4);
		gl.glEnableVertexAttribArray(0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glDrawArrays(GL4.GL_TRIANGLES, 0, 3);
	}
	
	/**
	 * Loads a file from the given path
	 * @param path The path to the file resource
	 * @return A String containing the text representation of the data in the file
	 */
	private String loadFile(String path)
	{
		String data = "";
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			String line;
			while ((line = reader.readLine()) != null)
			{
				data += line;
				data += "\n";
			}
			
			reader.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		return data;
	}
}
