package Graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL4;

public class VertexArrayObject
{
	//TODO: remove
	private static final float[] VERTICIES = 
	{
	    -0.5f,  0.5f, 1.0f, 0.0f, 0.0f, // Top-left
	     0.5f,  0.5f, 0.0f, 1.0f, 0.0f, // Top-right
	     0.5f, -0.5f, 0.0f, 0.0f, 1.0f, // Bottom-right
	    -0.5f, -0.5f, 1.0f, 1.0f, 0.0f  // Bottom-left
	};
	
	//TODO: remove
	private static final int[] ELEMENTS =
	{
		0, 1, 2,
		3, 2, 0
	};
	
	private int arrayObject;
	private int vertexBuffer;
	private int elementBuffer;
	private int shaderProgram;
	
	public VertexArrayObject(GL3 gl)
	{
		/**
		 * Vertex Array Objects store the configuration of the GL
		 * machine so that it can be loaded later. This is important
		 * because the machine needs to be configured differently
		 * depending on what we're drawing: primitives, sprites, etc.
		 * Instead of rebinding everything ourselves, we can just
		 * use a VAO and do it all at once. The VAO needs to be bound
		 * before it can record any other bindings we set, so bind it
		 * before doing anything else. Failure to do so can actually
		 * crash the Java VM and no amount of catch statements can
		 * salvage that shit.
		 */
		IntBuffer vao = IntBuffer.allocate(1);
		gl.glGenVertexArrays(1, vao);
		
		arrayObject = vao.get(0);
		gl.glBindVertexArray(arrayObject);
		
		/**
		 * Virtual buffer objects allow the CPU to write data to the GPU
		 * in the form of various buffers. The GPU can then use these to
		 * execute draw commands based on the data inside the buffers.
		 */
		IntBuffer vbo = IntBuffer.allocate(2);
		gl.glGenBuffers(2,  vbo);

		/**
		 * The vertex buffer contains attributes about the vertices we
		 * want to draw, e.g. color, position & texture, but the GL
		 * machine cannot discern any of these attributes from the
		 * data alone. See glVertexAttribPointer.
		 */
		vertexBuffer = vbo.get(0);
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBuffer);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, VERTICIES.length * 4, FloatBuffer.wrap(VERTICIES), GL3.GL_STATIC_DRAW);

		/**
		 * The element buffer contains the order we want to draw our
		 * vertices in. Since OpenGL can only draw basic shapes like
		 * triangles and lines, in order to draw more advanced
		 * geometry like quads, we have to draw with the same vertex
		 * multiple times. Instead of defining one vertex multiple
		 * times, we can define it once and reference it multiple
		 * times using an element buffer and calling glDrawElements
		 * as oppose to glDrawArrays. This saves memory and reduces
		 * the bottleneck that exists when writing data to the GPU.
		 */
		elementBuffer = vbo.get(1);
		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, elementBuffer);
		gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, ELEMENTS.length * 4, IntBuffer.wrap(ELEMENTS), GL3.GL_STATIC_DRAW);

		/**
		 * Shaders are programs that get executed on the GPU instead 
		 * of the CPU. Shaders are written in GLSL (OpenGL Shader 
		 * Language) and in this particular case, they are loaded as
		 * source files then compiled at runtime. If shader
		 * compilation fails, then there's a sizable chance that
		 * nothing will get drawn at all.
		 */
		String fragSource = loadFile("shaders/shader.frag");
		String vertSource = loadFile("shaders/shader.vert");

		/**
		 * Vertex shaders are called per-vertex and dictate where the
		 * vertex will end up on-screen.
		 */
		int vertexShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
		gl.glShaderSource(vertexShader, 1, new String[] {vertSource}, IntBuffer.wrap(new int[] {vertSource.length()}));
		gl.glCompileShader(vertexShader);
		
		//Check compile status
		{
			IntBuffer shaderSuccess = IntBuffer.allocate(1);
			gl.glGetShaderiv(vertexShader, GL3.GL_COMPILE_STATUS, shaderSuccess);
			
			if (shaderSuccess.get(0) != GL3.GL_TRUE)
			{
				System.out.println("Vertex shader compilation failed: ");
				ByteBuffer b = ByteBuffer.allocate(512);
				gl.glGetShaderInfoLog(vertexShader, 512, null, b);
				System.out.println(new String(b.array(), StandardCharsets.UTF_8));
			}
		}
		
		/**
		 * Vertex shaders are called per-pixel and dictate what color
		 * that pixel will be. This can be done via vertex colors or
		 * via texture samplers.
		 */
		int fragmentShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
		gl.glShaderSource(fragmentShader, 1, new String[] {fragSource}, IntBuffer.wrap(new int[] {fragSource.length()}));
		gl.glCompileShader(fragmentShader);
		
		//Check compile status
		{
			IntBuffer shaderSuccess = IntBuffer.allocate(1);
			gl.glGetShaderiv(fragmentShader, GL3.GL_COMPILE_STATUS, shaderSuccess);
			
			if (shaderSuccess.get(0) != GL3.GL_TRUE)
			{
				System.out.println("Fragment shader compilation failed: ");
				ByteBuffer b = ByteBuffer.allocate(512);
				gl.glGetShaderInfoLog(fragmentShader, 512, null, b);
				System.out.println(new String(b.array(), StandardCharsets.UTF_8));
			}
		}
				
		/**
		 * Shader programs take the compiled GLSL code from the
		 * shaders specified and link them together into a program
		 * that can run on the GPU to draw our image. Since we're not
		 * doing anything horrendously fancy, we're fine only giving
		 * it a vertex and fragment shader, as that will allow us to
		 * do more-or-less everything we could ever want to do.
		 */
		shaderProgram = gl.glCreateProgram();
		gl.glAttachShader(shaderProgram, vertexShader);
		gl.glAttachShader(shaderProgram, fragmentShader);
		gl.glLinkProgram(shaderProgram);
		gl.glUseProgram(shaderProgram);
		
		/**
		 * After the shaders have been linked into a program, we don't
		 * need the individual shader objects any more, so we can
		 * delete them.
		 */		
		gl.glDeleteShader(vertexShader);
		gl.glDeleteShader(fragmentShader);
		
		/**
		 * Vertex attribute pointers tell the shader programs how to
		 * interpret the raw vertex data we give it so that it can
		 * be matched up to the variables declared in the shaders.
		 * These need to be enabled before they can be used.
		 */
		gl.glVertexAttribPointer(0, 2, GL3.GL_FLOAT, false, 5 * 4, 0);
		gl.glVertexAttribPointer(1, 3, GL3.GL_FLOAT, false, 5 * 4, 2 * 4);
		gl.glEnableVertexAttribArray(0);
		gl.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Gets the internal handle for this Array Object.
	 * @return An int representing a handle to an OpenGL Virtual Array Object.
	 */
	public int getHandle()
	{
		return arrayObject;
	}
	
	/**
	 * Gets the buffer object that will be bound to GL_ARRAY_BUFFER
	 * when this Array Object is bound.
	 * @return An int representing a handle to an OpenGL buffer.
	 */
	public int getVertexBuffer()
	{
		return vertexBuffer;
	}

	/**
	 * Gets the buffer object that will be bound to GL_ELEMENT_ARRAY_BUFFER when this Array Object is bound.
	 * @return An int representing a handle to an OpenGL buffer.
	 */
	public int getElementBuffer()
	{
		return elementBuffer;
	}

	/**
	 * Gets the shaderProgram that will be in use when this Array Object is bound.
	 * @return An int representing a handle to an OpenGL buffer.
	 */
	public int getShaderProgram()
	{
		return shaderProgram;
	}
	
	/**
	 * Loads a file from the given path.
	 * @param path The path to the file resource.
	 * @return A String containing the text representation of the data in the file.
	 */
	private static String loadFile(String path)
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
