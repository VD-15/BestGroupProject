package Game;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import Graphics.Renderer;

/**
 * <h1>GameWindow</h1>
 * Provides a window frame and an OpenGL context to draw to.
 * 
 * @author Group 26
 * @version 1.0
 * @since 28-02-2019
 */
public class GameWindow implements GLEventListener
{	
	/**
	 * OpenGL rendering target and actual application window
	 */
	private GLWindow window;
	
	/**
	 * Facilitates drawing every frame
	 */
	private Animator animator;
	
	private Renderer renderer;

	/**
	 * Creates a GameWindow object of size 640x480 pixels with the title of "Game Window"
	 */
	public GameWindow()
	{
		this(640, 480, "Game Window");
	}
	
	/**
	 * Creates a GameWindow object with the given dimensions and title
	 * @param _width The width of the window in pixels.
	 * @param _height The height of the window in pixels.
	 * @param _title The title of the window.
	 */
	public GameWindow(int _width, int _height, String _title)
	{
		//Create an OpenGL context and create the window
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities capabilities = new GLCapabilities(profile);
		window = GLWindow.create(capabilities);
		//window.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);
		
		//Setup the window
		window.setTitle(_title);
		window.setSize(_width, _height);
		window.setVisible(false);
		window.setResizable(true);
		
		window.addGLEventListener(this);
		window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
		
		//Create the animator
		animator = new Animator(window);
		animator.start();
	}
	
	/**
	 * Called once before window is displayed.
	 */
	public void run()
	{
		System.out.println("Run");
		window.setVisible(true);
		window.setUpdateFPSFrames(15, null);
	}
	
	/**
	 * Called once when the window is first displayed
	 */	
	@Override
	public void init(GLAutoDrawable drawable)
	{
		GL4 gl = drawable.getGL().getGL4();
		
		renderer = new Renderer(gl);
	}
	
	/**
	 * Called once every frame
	 */
	@Override
	public void display(GLAutoDrawable drawable)
	{
		GL4 gl = drawable.getGL().getGL4();
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT | GL4.GL_STENCIL_BUFFER_BIT);
	}

	/**
	 * Called when the window is closed
	 */
	@Override
	public void dispose(GLAutoDrawable drawable)
	{
		System.out.println("Disposed.");
		animator.stop();
		System.exit(1);
	}

	/**
	 * Called whenever the window is resized
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		GL4 gl = drawable.getGL().getGL4();
		gl.glViewport(x, y, width, height);
	}
	
}
