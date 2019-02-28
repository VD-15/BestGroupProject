package Graphics;

import javax.swing.JFrame;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

/**
 * <h1>GameWindow</h1>
 * Provides a window frame and an OpenGL context to draw to.
 * 
 * @author Group 26
 * @version 1.0
 * @since 28-02-2019
 */
public class GameWindow extends JFrame implements GLEventListener
{

	/**
	 * Generated serial ID. I have no clue what this does,
	 * but apparently java uses it to identify what an object
	 * actually is when deserializing it from a bunch of bytes.
	 * No touchie.
	 */
	private static final long serialVersionUID = 1659548270683760352L;

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
		super(_title);
		
		//Create an OpenGL context and register the window
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities capabilities = new GLCapabilities(profile);
		GLCanvas canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		
		//Setup the window
		this.setName(_title);
		this.setSize(_width, _height);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
		this.setResizable(false);
		this.getContentPane().add(canvas);
		
		//Finally, focus the window.
		canvas.requestFocusInWindow();
	}
	
	@Override
	public void init(GLAutoDrawable arg0)
	{
		
	}
	
	public void run()
	{
		this.setVisible(true);
	}
	
	@Override
	public void display(GLAutoDrawable arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(GLAutoDrawable arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4)
	{
		// TODO Auto-generated method stub
		
	}
	
}
