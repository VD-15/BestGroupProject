package Graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.math.Matrix4;

import Game.Core.Game;
import Utils.LogSeverity;
import Utils.Logger;

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
	private GL3 gl;
	private Matrix4 viewportMatrix;
	
	private int arrayObject;
	private int shaderProgram;
	
	private int vertexBuffer;
	private int elementBuffer;
	private int vertexBufferAllocation;
	private int elementBufferAllocation;
	
	//Position:	vec3	3 * 4
	//Color:	vec4	4 * 4
	//UV:		vec2	2 * 4
	//Size		=		9 * 4 = 36
	private static int VERTEX_SIZE = 36;
	
	/**
	 * Creates a renderer with the given GL context.
	 * @param gl
	 */
	//TODO: move this to an init() method
	public Renderer()
	{
		this.gl = null;
		this.viewportMatrix = new Matrix4();
		this.arrayObject = 0;
		this.vertexBuffer = 0;
		this.elementBuffer = 0;
		this.shaderProgram = 0;
		this.vertexBufferAllocation = 0;
		this.elementBufferAllocation = 0;
	}
	
	public void init(GL3 gl)
	{
		this.gl = gl;
		
		viewportMatrix = new Matrix4();
		
		//TODO: move these to their own functions
		{
			IntBuffer vao = IntBuffer.allocate(1);
			IntBuffer vbo = IntBuffer.allocate(2);
			
			gl.glGenVertexArrays(1, vao);
			gl.glGenBuffers(2,  vbo);
			
			arrayObject = vao.get(0);
			vertexBuffer = vbo.get(0);
			elementBuffer = vbo.get(1);
			
			IntBuffer units = IntBuffer.allocate(1);
			gl.glGetIntegerv(GL3.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, units);
			Logger.log(this, LogSeverity.VERBOSE, "Found {" + units.get(0) + "} available texture units.");
		}
		
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepthf(1000);
		
		gl.glEnable(GL3.GL_BLEND);
		gl.glBlendFunc(GL3.GL_SRC_ALPHA, GL3.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnable(GL3.GL_DEPTH_TEST);
		gl.glDepthFunc(GL3.GL_LESS);
		
		gl.glBindVertexArray(arrayObject);
		
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
		 * The vertex buffer contains attributes about the vertices we
		 * want to draw, e.g. color, position & texture, but the GL
		 * machine cannot discern any of these attributes from the
		 * data alone. See glVertexAttribPointer.
		 */
		this.vertexBufferAllocation = 1024;
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBuffer);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, vertexBufferAllocation, null, GL3.GL_DYNAMIC_DRAW);

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
		this.elementBufferAllocation = 1024;
		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, elementBuffer);
		gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, elementBufferAllocation, null, GL3.GL_DYNAMIC_DRAW);
		
		/**
		 * Vertex shaders are called per-vertex and dictate where the
		 * vertex will end up on-screen.
		 */
		int vertexShader = createShader(gl, GL3.GL_VERTEX_SHADER, vertSource);
		
		/**
		 * Fregment shaders are called per-pixel and dictate what color
		 * that pixel will be. This can be done via vertex colors or
		 * via texture samplers.
		 */
		int fragmentShader = createShader(gl, GL3.GL_FRAGMENT_SHADER, fragSource);
				
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

		//Position:	vec3	3 * 4
		//Color:	vec4	4 * 4
		//UV:		vec2	2 * 4
		gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 9 * 4, 0 * 4);
		gl.glVertexAttribPointer(1, 4, GL3.GL_FLOAT, false, 9 * 4, 3 * 4);
		gl.glVertexAttribPointer(2, 2, GL3.GL_FLOAT, false, 9 * 4, 7 * 4);
		gl.glEnableVertexAttribArray(0);
		gl.glEnableVertexAttribArray(1);
		gl.glEnableVertexAttribArray(2);
		
		/**
		 * Unbind vertex array just to be safe
		 */
		gl.glBindVertexArray(0);
	}
	
	public void setViewport(Rectangle v)
	{
		//viewportMatrix = new Matrix4();
		viewportMatrix.loadIdentity();
		viewportMatrix.makeOrtho(0, v.getWidth(), v.getHeight(), 0, -100f, 100f);
	}
	
	public void draw(GL3 gl, RenderBatch batch)
	{
		ArrayList<RenderInstance> instances = batch.getInstances();

		HashMap<Texture, ArrayList<RenderInstance>> renderPasses = new HashMap<Texture, ArrayList<RenderInstance>>();

		gl.glBindVertexArray(arrayObject);
		gl.glUniformMatrix4fv(1, 1, false, viewportMatrix.getMatrix(), 0);
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
		
		for (RenderInstance r : instances)
		{
			if (renderPasses.containsKey(r.texture))
			{
				renderPasses.get(r.texture).add(r);
			}
			else
			{
				ArrayList<RenderInstance> l = new ArrayList<RenderInstance>();
				l.add(r);
				renderPasses.put(r.texture, l);
			}
		}
		
		for (Texture t : renderPasses.keySet())
		{
			ArrayList<RenderInstance> renderPass = renderPasses.get(t);
			ByteBuffer renderData = ByteBuffer.allocateDirect(renderPass.size() * VERTEX_SIZE * 4);
			renderData.order(ByteOrder.nativeOrder());
			ByteBuffer renderElements = ByteBuffer.allocateDirect(renderPass.size() * 2 * 6);
			renderElements.order(ByteOrder.nativeOrder());

			for (short j = 0; j < renderPass.size(); j++)
			{
				RenderInstance r = renderPass.get(j);
				
				//Put top-left vertex
				renderData.putFloat(r.destination.x);
				renderData.putFloat(r.destination.y);
				renderData.putFloat(r.depth);
				renderData.putFloat(r.color.r);
				renderData.putFloat(r.color.g);
				renderData.putFloat(r.color.b);
				renderData.putFloat(r.color.a);
				renderData.putFloat(r.source.x);
				renderData.putFloat(r.source.y);
				
				//Put top-right vertex
				renderData.putFloat(r.destination.x + r.destination.width);
				renderData.putFloat(r.destination.y);
				renderData.putFloat(r.depth);
				renderData.putFloat(r.color.r);
				renderData.putFloat(r.color.g);
				renderData.putFloat(r.color.b);
				renderData.putFloat(r.color.a);
				renderData.putFloat(r.source.x + r.source.width);
				renderData.putFloat(r.source.y);
				
				//Put bottom-right vertex
				renderData.putFloat(r.destination.x + r.destination.width);
				renderData.putFloat(r.destination.y + r.destination.height);
				renderData.putFloat(r.depth);
				renderData.putFloat(r.color.r);
				renderData.putFloat(r.color.g);
				renderData.putFloat(r.color.b);
				renderData.putFloat(r.color.a);
				renderData.putFloat(r.source.x + r.source.width);
				renderData.putFloat(r.source.y + r.source.height);
				
				//Put bottom-left vertex
				renderData.putFloat(r.destination.x);
				renderData.putFloat(r.destination.y + r.destination.height);
				renderData.putFloat(r.depth);
				renderData.putFloat(r.color.r);
				renderData.putFloat(r.color.g);
				renderData.putFloat(r.color.b);
				renderData.putFloat(r.color.a);
				renderData.putFloat(r.source.x);
				renderData.putFloat(r.source.y + r.source.height);
				
				//Put element data
				renderElements.putShort((short) ((j * 4) + 0));
				renderElements.putShort((short) ((j * 4) + 1));
				renderElements.putShort((short) ((j * 4) + 2));
				renderElements.putShort((short) ((j * 4) + 0));
				renderElements.putShort((short) ((j * 4) + 2));
				renderElements.putShort((short) ((j * 4) + 3));
			}
			
			renderData.position(0);
			renderElements.position(0);
			
			gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBuffer);
			if (renderData.capacity() > vertexBufferAllocation)
			{
				gl.glBufferData(GL3.GL_ARRAY_BUFFER, renderData.capacity(), renderData, GL3.GL_DYNAMIC_DRAW);
				vertexBufferAllocation = renderData.capacity();
			}
			else
			{
				gl.glBufferSubData(GL3.GL_ARRAY_BUFFER, 0, renderData.capacity(), renderData);
			}
			
			gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, elementBuffer);
			if (renderElements.capacity() > elementBufferAllocation)
			{
				gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, renderElements.capacity(), renderElements, GL3.GL_DYNAMIC_DRAW);
				elementBufferAllocation = renderElements.capacity();
			}
			else
			{
				gl.glBufferSubData(GL3.GL_ELEMENT_ARRAY_BUFFER, 0, renderElements.capacity(), renderElements);
			}
			
			Matrix4 texMatrix = new Matrix4();
			texMatrix.loadIdentity();
			texMatrix.makeOrtho(-t.getWidth(), t.getWidth(), -t.getHeight(), t.getHeight(), -1f, 1f);
			gl.glUniformMatrix4fv(2, 1, false, texMatrix.getMatrix(), 0);
			//gl.glClear(GL3.GL_DEPTH_BUFFER_BIT);
			gl.glBindTexture(GL3.GL_TEXTURE_2D, t.getID());
			gl.glDrawElements(GL3.GL_TRIANGLES, renderPass.size() * 6, GL3.GL_UNSIGNED_SHORT, 0);
		}
	}
	
	private static int createShader(GL3 gl, int shaderType, String shaderSource)
	{
		int vertexShader = gl.glCreateShader(shaderType);
		gl.glShaderSource(vertexShader, 1, new String[] {shaderSource}, IntBuffer.wrap(new int[] {shaderSource.length()}));
		gl.glCompileShader(vertexShader);
		
		//Check compile status
		{
			IntBuffer shaderSuccess = IntBuffer.allocate(1);
			gl.glGetShaderiv(vertexShader, GL3.GL_COMPILE_STATUS, shaderSuccess);
			
			if (shaderSuccess.get(0) != GL3.GL_TRUE)
			{
				ByteBuffer b = ByteBuffer.allocate(512);
				gl.glGetShaderInfoLog(vertexShader, 512, null, b);
				Logger.log(Renderer.class, LogSeverity.ERROR, "Shader compilation failed: \n" + new String(b.array(), StandardCharsets.UTF_8));
			}
		}
		
		return vertexShader;
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
