package junit;

import static org.junit.Assert.assertTrue;

import java.nio.IntBuffer;

import org.junit.jupiter.api.*;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import graphics.Renderer;

public class GlTest implements GLEventListener
{
	private static GLWindow window;
	private static Renderer renderer;
	private static GL3 gl;
	
	@BeforeAll
	public static void beforeAll()
	{
		GLProfile profile = GLProfile.get(GLProfile.GL3);
		GLCapabilities capabilities = new GLCapabilities(profile);
		GlTest.window = GLWindow.create(capabilities);
		GlTest.window.setVisible(true);
		GlTest.renderer = new Renderer();
		GlTest.gl = GlTest.window.getGL().getGL3();
	}
	
	@Override
	public void display(GLAutoDrawable drawable){}

	@Override
	public void dispose(GLAutoDrawable drawable){}

	@Override
	public void init(GLAutoDrawable drawable)
	{
		GL3 gl = drawable.getGL().getGL3();
		renderer.init(gl);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){}

	/*
	 * This is basically going to be one long essay on why we can't really test the renderer:
	 * 
	 *### WHY WE CAN'T TEST THE RENDERER ###
	 * 
	 * The renderer requires an OpenGL context in order to really do anything. In order to do
	 * this, we need a window. For the purposes of this demonstration, we've created one, but
	 * the GL context it provides is weird and JUnit doesn't appear to like it. We can see
	 * this because the maximum available texture units has been 0 on all machines we've
	 * tested on. ANY OpenGL context MUST have at least one of these in order to work with
	 * anything more advanced than gradients. During normal operation, this number is 
	 * around about 200 on all of our machines. This, to us means that there is something
	 * going wrong between JUnit and Jogl, but we can't diagnose or fix it, as an extension,
	 * we can't test the renderer with JUnit. To demonstrate this, the test below will always
	 * fail even though by all accounts we've seen, they really shouldn't.
	 * 
	 * ### WHY WE PROBABLY WOULDN'T ANYWAY ###
	 * 
	 * OpenGL is effectively a black box. Any GPU operations occur outside of the view of the 
	 * CPU and by extension, Java. Even if Jogl worked well with JUnit, then we would still
	 * be limited to checking things via glGet [http://docs.gl/gl3/glGet] which is equivalent
	 * to just testing getters and setters and wouldn't be very helpful anyway. The only
	 * alternative we can think of would be to create a bunch of test examples to test
	 * aspects of the renderer. These would have to be inspected by eye and in my opinion
	 * would negate the entire point of creating unit tests, that being automation of tests.
	 */
	@Test
	public void test1()
	{
		IntBuffer i = IntBuffer.allocate(1);
		GlTest.gl.glGetIntegerv(GL3.GL_MAX_TEXTURE_IMAGE_UNITS, i);
		System.out.print("READ THE COMMENT ABOVE THIS BEFORE RUNNING THE TEST!");
		assertTrue(i.get(0) > 0);
	}
	
}
