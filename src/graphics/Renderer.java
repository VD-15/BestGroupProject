package graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.math.Matrix4;

import core.Camera;
import utils.LogSeverity;
import utils.Logger;
import utils.Region;
import utils.Vector4;

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
 * To the unfortunate soul tasked with combing
 * through all of this, I wish you luck.
 * @author Vee
 */
public class Renderer
{		
	/**
	 * The vertex array object that specifies how OpenGL will read the vertex data from the buffer
	 */
	private int arrayObject;
	
	/**
	 * The shader program to rasterize an image on screen
	 */
	private int shaderProgram;
	
	/**
	 * A reference to a buffer on the GPU that will store all the vertex data used to render a frame.
	 * Vertex data consists of three floats making up a position, four floats making up a color,
	 * (translucent colors are not currently supported, so don't ask me why we have an alpha channel)
	 * and two floats making up texture coordinates.
	 */
	private int vertexBuffer;
	
	/**
	 * A reference to a buffer on the GPU that will store the what vertices to draw in what order.
	 * Vertex element data is just a list of indices pointing to vertices in the vertex buffer so
	 * we can save memory when using a vertex multiple times, which we do when drawing squares out
	 * of two adjacent triangles. One might think to use GL_QUADS for this but GL_QUADS is abysmal
	 * in just about every sense so we won't be using it.
	 */
	private int elementBuffer;
	
	/**
	 * The size, in bytes, of the vertex buffer in GPU memory. Since the vertex buffer must be
	 * able to store the vertex data we will be passing to it, and it cannot resize dynamically
	 * like an ArrayList, we must keep track of it ourselves. Since re-allocating the memory a
	 * buffer occupies takes A LOT of work, we only want to do it when we absolutely have to.
	 * Because of this, my vertex buffer will only grow in size and never shrink. This isn't too
	 * much of a concern as more often than not, textures will take up the overwhelming majority
	 * of memory. So as long as we're not drawing anything too crazy, it'll be fine.
	 */
	private int vertexBufferAllocation;
	
	/**
	 * The size, in bytes, of the element buffer in GPU memory. Since the element buffer must be
	 * able to store the element data we will be passing to it, and it cannot resize dynamically
	 * like an ArrayList, we must keep track of it ourselves. Since re-allocating the memory a
	 * buffer occupies takes A LOT of work, we only want to do it when we absolutely have to.
	 * Because of this, my element buffer will only grow in size and never shrink. This isn't too
	 * much of a concern as element buffers don't take up too much space by themselves so the
	 * vertex buffer will almost always be what breaks the memory limit.
	 */
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
	public Renderer()
	{
		this.arrayObject = 0;
		this.vertexBuffer = 0;
		this.elementBuffer = 0;
		this.shaderProgram = 0;
		this.vertexBufferAllocation = 0;
		this.elementBufferAllocation = 0;
	}
	
	public void init(GL3 gl)
	{
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

		//Set the clear color to black
		gl.glClearColor(0f, 0f, 0f, 1f);
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
	
	public void draw(GL3 gl, RenderBatch batch)
	{
		ArrayList<RenderInstance> instances = batch.getInstances();
		HashMap<Integer, Camera> cameras = batch.getCameras();

		HashMap<Integer, HashMap<Texture, ArrayList<RenderInstance>>> renderPasses = new HashMap<Integer, HashMap<Texture, ArrayList<RenderInstance>>>();

		gl.glBindVertexArray(arrayObject);
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
		
		for (RenderInstance r : instances)
		{
			if (renderPasses.containsKey(r.layer))
			{
				if (renderPasses.get(r.layer).containsKey(r.texture))
				{
					renderPasses.get(r.layer).get(r.texture).add(r);
				}
				else
				{
					ArrayList<RenderInstance> l = new ArrayList<RenderInstance>();
					l.add(r);
					
					renderPasses.get(r.layer).put(r.texture, l);
				}
			}
			else
			{
				HashMap<Texture, ArrayList<RenderInstance>> h = new HashMap<Texture, ArrayList<RenderInstance>>();
				ArrayList<RenderInstance> l = new ArrayList<RenderInstance>();
				l.add(r);
				h.put(r.texture, l);
				
				renderPasses.put(r.layer, h);
			}
		}
		
		for (Integer layer : renderPasses.keySet())
		{
			if (layer.equals(-1)) continue;
			
			Region view = cameras.get(layer).getViewport();
			
			Matrix4 viewport = new Matrix4();
			viewport.loadIdentity();
			viewport.makeOrtho(view.x, view.x + view.width, view.y + view.height, view.y, -100, 100);
			
			gl.glUniformMatrix4fv(1, 1, false, viewport.getMatrix(), 0);
			
			for (Texture t : renderPasses.get(layer).keySet())
			{
				ArrayList<RenderInstance> renderPass = renderPasses.get(layer).get(t);
				ByteBuffer renderData = ByteBuffer.allocateDirect(renderPass.size() * VERTEX_SIZE * 4);
				renderData.order(ByteOrder.nativeOrder());
				ByteBuffer renderElements = ByteBuffer.allocateDirect(renderPass.size() * 2 * 6);
				renderElements.order(ByteOrder.nativeOrder());
				
				Matrix4 texMatrix = new Matrix4();
				texMatrix.loadIdentity();
				texMatrix.makeOrtho(-t.getWidth(), t.getWidth(), -t.getHeight(), t.getHeight(), -1f, 1f);
				gl.glUniformMatrix4fv(2, 1, false, texMatrix.getMatrix(), 0);

				for (short j = 0; j < renderPass.size(); j++)
				{
					RenderInstance r = renderPass.get(j);
					
					Vector4[] points = new Vector4[]
					{
						new Vector4(r.destination.x, r.destination.y, 0f, 1f),
						new Vector4(r.destination.x + r.destination.width, r.destination.y, 0f, 1f),
						new Vector4(r.destination.x + r.destination.width, r.destination.y + r.destination.height, 0f, 1f),
						new Vector4(r.destination.x, r.destination.y + r.destination.height, 0f, 1f)
					};
					
					if (r.rotation != 0f)
					{
						Matrix4 rotator = new Matrix4();
						rotator.loadIdentity();
						rotator.translate(r.rotationOrigin.x, r.rotationOrigin.y, 0);
						rotator.rotate(r.rotation, 0, 0, 1);
						rotator.translate(-r.rotationOrigin.x, -r.rotationOrigin.y, 0);
						
						//rotator.multMatrix(viewport);
						
						for (int i = 0; i < 4; i++)
						{
							float[] arr_out = new float[4];
							rotator.multVec(points[i].getArray(), arr_out);
							points[i] = new Vector4(arr_out);
						}
					}
					
					//Put top-left vertex
					renderData.putFloat(points[0].x);
					renderData.putFloat(points[0].y);
					renderData.putFloat(r.depth);
					renderData.putFloat(r.color.r);
					renderData.putFloat(r.color.g);
					renderData.putFloat(r.color.b);
					renderData.putFloat(r.color.a);
					renderData.putFloat(r.source.x);
					renderData.putFloat(r.source.y);
					
					//Put top-right vertex
					renderData.putFloat(points[1].x);
					renderData.putFloat(points[1].y);
					renderData.putFloat(r.depth);
					renderData.putFloat(r.color.r);
					renderData.putFloat(r.color.g);
					renderData.putFloat(r.color.b);
					renderData.putFloat(r.color.a);
					renderData.putFloat(r.source.x + r.source.width);
					renderData.putFloat(r.source.y);
					
					//Put bottom-right vertex
					renderData.putFloat(points[2].x);
					renderData.putFloat(points[2].y);
					renderData.putFloat(r.depth);
					renderData.putFloat(r.color.r);
					renderData.putFloat(r.color.g);
					renderData.putFloat(r.color.b);
					renderData.putFloat(r.color.a);
					renderData.putFloat(r.source.x + r.source.width);
					renderData.putFloat(r.source.y + r.source.height);
					
					//Put bottom-left vertex
					renderData.putFloat(points[3].x);
					renderData.putFloat(points[3].y);
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
				
				gl.glBindTexture(GL3.GL_TEXTURE_2D, t.getID());
				gl.glDrawElements(GL3.GL_TRIANGLES, renderPass.size() * 6, GL3.GL_UNSIGNED_SHORT, 0);
			}
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
