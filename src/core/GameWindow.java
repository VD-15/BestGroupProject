package core;

import java.util.Timer;
import java.util.TimerTask;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import graphics.RenderBatch;
import graphics.Renderer;
import input.KeyBinding;
import input.Keyboard;
import input.Mouse;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;
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
	public GLWindow window;

	/**
	 * Facilitates drawing every frame
	 */
	private Animator animator;
	private Renderer renderer;
	
	private Keyboard keyboard;
	private Mouse mouse;

	private Vector2 viewport;

	/**
	 * Creates a GameWindow object of size 1100x700 pixels with the title of "Game Window"
	 */
	public GameWindow()
	{
		this(1100, 700, "Robo Game");
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

		this.keyboard = new Keyboard();
		this.mouse = new Mouse();
		
		//Setup the window
		this.window.setTitle(_title);
		this.window.setSize(_width, _height);
		this.window.setVisible(false);
		this.window.setResizable(true);
		this.window.addGLEventListener(this);
		this.window.addKeyListener(this.keyboard);
		this.window.addMouseListener(this.mouse);
		//this.window.setFullscreen(true);
		
		this.window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);

		//Create the animator
		this.animator = new Animator(this.window);
		this.viewport = new Vector2(_width, _height);
		this.renderer = new Renderer();
	}

	/**
	 * Called once before window is displayed.
	 */
	public void run()
	{
		Logger.log(this, LogSeverity.INFO, "Running.");
		window.setVisible(true);
		window.setUpdateFPSFrames(15, null);
	}
	 /**
	  * Used to close the window at runtime
	  */
	public void close()
	{
		this.animator.stop();
		this.window.destroy();
	}

	public Vector2 getViewport()
	{
		return this.viewport;
	}
	
	public Keyboard getKeyboard()
	{
		return this.keyboard;
	}
	
	public Mouse getMouse()
	{
		return this.mouse;
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

		//Load textures
		ContentManager.setRootDirectory("content/");		
		ContentManager.loadImage(gl, "textures/TilePit.bmp", 				"tilePit", 			64, 	64);
		ContentManager.loadImage(gl, "textures/TileNormal.bmp", 			"tileNormal", 		64, 	64);
		
		ContentManager.loadImage(gl, "textures/NewBelts/BeltNorth.gif", 	"tileBeltNorth", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltEast.gif", 		"tileBeltEast", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltSouth.gif", 	"tileBeltSouth", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltWest.gif", 		"tileBeltWest", 	64, 	64);
		
		ContentManager.loadImage(gl, "textures/NewBelts/BeltNorthC.gif", 	"tileBeltNorthC", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltNorthCC.gif", 	"tileBeltNorthCC", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltEastC.gif", 	"tileBeltEastC", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltEastCC.gif", 	"tileBeltEastCC", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltSouthC.gif", 	"tileBeltSouthC", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltSouthCC.gif", 	"tileBeltSouthCC", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltWestC.gif", 	"tileBeltWestC", 	64, 	64);
		ContentManager.loadImage(gl, "textures/NewBelts/BeltWestCC.gif", 	"tileBeltWestCC", 	64, 	64);
		
		ContentManager.loadImage(gl, "textures/TileGearC.bmp", 				"tileGearC", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileGearCC.bmp", 			"tileGearCC", 		64, 	64);
		
		ContentManager.loadImage(gl, "textures/TileFlag1.bmp", 				"tileFlag1", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag2.bmp", 				"tileFlag2", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag3.bmp", 				"tileFlag3", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag4.bmp", 				"tileFlag4", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag5.bmp", 				"tileFlag5", 		64, 	64);
		ContentManager.loadImage(gl, "textures/TileFlag6.bmp", 				"tileFlag6", 		64, 	64);

		ContentManager.loadImage(gl, "textures/Robot1.gif", 				"robot1", 			64, 	64);
		ContentManager.loadImage(gl, "textures/Robot2.gif", 				"robot2", 			64, 	64);
		ContentManager.loadImage(gl, "textures/Robot3.gif", 				"robot3", 			64, 	64);
		ContentManager.loadImage(gl, "textures/Robot4.gif", 				"robot4", 			64, 	64);

		ContentManager.loadImage(gl, "textures/UI/button_edge.png",			"buttonEdge", 		15, 	64);
		ContentManager.loadImage(gl, "textures/UI/button_middle.png",		"buttonCenter",		15, 	64);
		
		ContentManager.loadImage(gl, "textures/UI/icon_back.png",			"iconBackward",		64, 	64);
		ContentManager.loadImage(gl, "textures/UI/icon_forward.png",		"iconForward",		64, 	64);
		ContentManager.loadImage(gl, "textures/UI/icon_turnLeft.png",		"iconLeft",			64, 	64);
		ContentManager.loadImage(gl, "textures/UI/icon_turnRight.png",		"iconRight",		64, 	64);
		ContentManager.loadImage(gl, "textures/UI/icon_uturn.png",			"iconUturn",		64, 	64);
		ContentManager.loadImage(gl, "textures/UI/icon_wait.png",			"iconWait",			64, 	64);
		ContentManager.loadImage(gl, "textures/UI/icon_delete.png",			"iconDelete",			64, 	64);
		
		ContentManager.loadImage(gl, "textures/UI/ui_blank.png", 			"uiBlank", 			64, 	64);
		
		ContentManager.loadImage(gl, "textures/UI/font_small.png",			"fontSmall", 		2000, 	50);
		ContentManager.createFont("fontSmall", new Point(20, 50));
		
		//Load boards
		ContentManager.loadText("boards/testboard.brd", "testBoard");
		ContentManager.loadText("boards/conveyor-loops.brd", "testBoard2");
		ContentManager.loadText("boards/example-board.brd", "testBoard3");
		ContentManager.loadText("boards/bigboard.brd", "testBoard4");


		//Load programs
		ContentManager.loadText("programs/4players.prg", "4players");
		
		//Bindings for the MainCamera
		//Up & down are inverted here due to the way handle the Y-axis, remember, +ve is down.
		this.keyboard.addBinding("cameraDown", 	new KeyBinding(KeyEvent.VK_W, KeyEvent.VK_UP));
		this.keyboard.addBinding("cameraLeft", 	new KeyBinding(KeyEvent.VK_A, KeyEvent.VK_LEFT));
		this.keyboard.addBinding("cameraRight", new KeyBinding(KeyEvent.VK_D, KeyEvent.VK_RIGHT));
		this.keyboard.addBinding("cameraUp", 	new KeyBinding(KeyEvent.VK_S, KeyEvent.VK_DOWN));
		
		//Binding to quit the game (remember to unbind on final release)
		this.keyboard.addBinding("gameQuit", 	new KeyBinding(KeyEvent.VK_ESCAPE));
		
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

		if (!Game.isRunning()) this.animator.stop();
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

		//Rectangle r = new Rectangle(x, y, width, height);
		viewport.set(width, height);
	}

}
