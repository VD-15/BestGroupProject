package Game.Core;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import Graphics.RenderBatch;
import Graphics.Renderer;
import Utils.LogSeverity;
import Utils.Logger;
import Utils.Vector2;

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
	public static GameWindow INSTANCE = null;
	
	/**
	 * OpenGL rendering target and actual application window
	 */
	private GLWindow window;
	
	/**
	 * Facilitates drawing every frame
	 */
	private Animator animator;
	private Renderer renderer;
	
	private Game game;
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
		if (GameWindow.INSTANCE != null)
		{
			System.out.println("[Game Window]: (ERROR) Multiple windows running, application may not function correctly.");
		}
		else
		{
			GameWindow.INSTANCE = this;
		}
		
		//Create an OpenGL context and create the window
		GLProfile profile = GLProfile.get(GLProfile.GL4);
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
		
		//Create the game
		this.viewport = new Vector2(640, 480);
		this.game = new Game();
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

		//Set the clear color to black
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ContentManager.setRootDirectory("content/");
		ContentManager.loadImage(gl, "testImage.png", "testImage", 1024, 1024);
		ContentManager.loadImage(gl, "testImage2.png", "testImage2", 631, 270);
		ContentManager.loadImage(gl, "TestTextures/TilePit.bmp", "tilePit", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileNormal.bmp", "tileNormal", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileBelt.bmp", "tileBelt", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileGearC.bmp", "tileGearC", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileGearCC.bmp", "tileGearCC", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileFlag1.bmp", "tileFlag1", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileFlag2.bmp", "tileFlag2", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileFlag3.bmp", "tileFlag3", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileFlag4.bmp", "tileFlag4", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileFlag5.bmp", "tileFlag5", 64, 64);
		ContentManager.loadImage(gl, "TestTextures/TileFlag6.bmp", "tileFlag6", 64, 64);
		animator.start();
		renderer.init(gl);
		game.init();
	}
	
	/**
	 * Called once every frame
	 */
	@Override
	public void display(GLAutoDrawable drawable)
	{
		GL3 gl = drawable.getGL().getGL3();
		game.update();
		
		RenderBatch renderBatch = new RenderBatch();
		game.draw(renderBatch);
		renderer.draw(gl, renderBatch);
	}

	/**
	 * Called when the window is closed
	 */
	@Override
	public void dispose(GLAutoDrawable drawable)
	{
		Logger.log(this, LogSeverity.INFO, "Disposed.");
		animator.stop();
		System.exit(1);
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
		//System.out.println(r.toString());
	}
	
}
