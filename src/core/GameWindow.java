package core;

import java.util.Timer;
import java.util.TimerTask;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import graphics.RenderBatch;
import graphics.Renderer;
import utils.LogSeverity;
import utils.Logger;
import utils.Vector2;

/**
 * <h1>GameWindow</h1>
 * Provides a window frame and an OpenGL context to draw to.
 * 
 * @author Group 26
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
	
	private Vector2 viewport;

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
		GLProfile profile = GLProfile.get(GLProfile.GL3);
		GLCapabilities capabilities = new GLCapabilities(profile);
		this.window = GLWindow.create(capabilities);
		
		//Setup the window
		this.window.setTitle(_title);
		this.window.setSize(_width, _height);
		this.window.setVisible(false);
		this.window.setResizable(true);
		this.window.addGLEventListener(this);
		this.window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
		
		//Create the animator
		this.animator = new Animator(this.window);
		this.viewport = new Vector2(640, 480);
		this.renderer = new Renderer();
	}
	
	/**
	 * Called once before window is displayed.
	 */
	public void run()
	{
		Logger.log(this, LogSeverity.INFO, "Running.");
		window.setVisible(true);
		//window.setUpdateFPSFrames(15, null);
		
		//Run the garbage collector every 5 seconds to avoid big boi memory leaks.
		{
			Timer timer = new Timer();
			TimerTask task = new TimerTask()
			{
				@Override
				public void run() 
				{
					Runtime.getRuntime().gc();
				}
			};
			
			timer.schedule(task, 0, 5000);
		}
	}
	
	public Vector2 getViewport()
	{
		return this.viewport;
	}
	
	/**
	 * Called once when the window is first displayed
	 * 
	 * ### HOPE NOBODY'S PARKED HERE BECAUSE THIS###
	 * ###     IS A DESIGNATED LOADING ZONE!     ###
	 */	
	@Override
	public void init(GLAutoDrawable drawable)
	{
		GL3 gl = drawable.getGL().getGL3();

		ContentManager.setRootDirectory("content/");
		ContentManager.loadImage(gl, "testImage.png", 				"testImage", 		1024, 	1024);
		ContentManager.loadImage(gl, "testImage2.png", 				"testImage2", 		631, 	270);
		ContentManager.loadImage(gl, "textures/TilePit.bmp", 		"tilePit", 			64, 	64);
		ContentManager.loadImage(gl, "textures/TileNormal.bmp", 	"tileNormal", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileBeltNorth.bmp", 	"tileBeltNORTH", 	64, 	64);
		ContentManager.loadImage(gl, "textures/TileBeltEast.bmp", 	"tileBeltEAST", 	64, 	64);
		ContentManager.loadImage(gl, "textures/TileBeltSouth.bmp", 	"tileBeltSOUTH", 	64, 	64);
		ContentManager.loadImage(gl, "textures/TileBeltWest.bmp", 	"tileBeltWEST", 	64, 	64);
		ContentManager.loadImage(gl, "textures/TileGearC.bmp", 		"tileGearC", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileGearCC.bmp", 	"tileGearCC", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag1.bmp", 		"tileFlag1", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag2.bmp", 		"tileFlag2", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag3.bmp", 		"tileFlag3", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag4.bmp", 		"tileFlag4", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag5.bmp", 		"tileFlag5", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag6.bmp", 		"tileFlag6", 		64, 	64);
		ContentManager.loadImage(gl, "textures/robotUp.png", 		"robotNORTH", 		60, 	60);
		ContentManager.loadImage(gl, "textures/robotDown.png", 		"robotSOUTH", 		60, 	60);
		ContentManager.loadImage(gl, "textures/robotLeft.png", 		"robotWEST", 		60, 	60);
		ContentManager.loadImage(gl, "textures/robotRight.png", 	"robotEAST", 		60, 	60);
		
		ContentManager.loadText("boards/testboard.brd", "testBoard");
		
		animator.start();
		renderer.init(gl);
		Game.init(this);
	}
	
	/**
	 * Called once every frame
	 */
	@Override
	public void display(GLAutoDrawable drawable)
	{
		GL3 gl = drawable.getGL().getGL3();
		Game.update();
		
		RenderBatch renderBatch = new RenderBatch();
		Game.draw(renderBatch);
		renderer.draw(gl, renderBatch);
	}

	/**
	 * Called when the window is closed
	 */
	@Override
	public void dispose(GLAutoDrawable drawable)
	{
		animator.stop();		
		Logger.log(this, LogSeverity.INFO, "Disposed.");
		System.exit(0);
	}

	/**
	 * Called whenever the window is resized
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		GL3 gl = drawable.getGL().getGL3();
		gl.glViewport(x, y, width, height);
		
		Rectangle r = new Rectangle(x, y, width, height);
		viewport.set(width, height);
		renderer.setViewport(r);
	}
	
}
