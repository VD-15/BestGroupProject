package core;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL3;

import UI.Font;
import graphics.Texture;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;

/**
 * Facilitates the loading of game content in a streamlined 
 * manor that also keeps resources loaded throughout runtime
 * @author Vee
 *
 */
public class ContentManager
{
	/**
	 * Loaded textures accessed by name
	 */
	private static HashMap<String, Texture> TEXTURES;
	
	/**
	 * Loaded plain text files accessed by name
	 */
	private static HashMap<String, String[]> PLAINTEXT;
	
	private static HashMap<String, Font> FONTS;
	
	/**
	 * Relative or absolute root directory to search for content in
	 */
	private static String ROOT_DIR;
	
	/**
	 * Sets the root directory for the content manager
	 * @param root the path to the new directory
	 */
	public static void setRootDirectory(String root)
	{
		if (!root.endsWith("/"))
		{
			System.out.println(
					"[Content Manager]: (WARN) Root directory does not end with a trailing '/'. Loading content may fail.");
		}
		
		ContentManager.ROOT_DIR = root;
		ContentManager.TEXTURES = new HashMap<String, Texture>();
		ContentManager.PLAINTEXT = new HashMap<String, String[]>();
		ContentManager.FONTS = new HashMap<String, Font>();
	}
	
	/**
	 * Creates a font from a texture and stores it in the content manager
	 * @param textureName the name of the texture to load
	 * @param letterSize the size of each font letter
	 * @return
	 */
	public static boolean createFont(String textureName, Point letterSize)
	{
		if (ContentManager.TEXTURES.containsKey(textureName))
		{
			ContentManager.FONTS.put(textureName, new Font(ContentManager.TEXTURES.get(textureName), letterSize));
			return true;
		}
		
		return false;
	}
	
	/**
	 * Loads an image and creates a copy of it on the GPU for access with the renderer
	 * @param gl the GL context to create the texture in
	 * @param path the path to load the texture from
	 * @param name the name the texture will be addressed with
	 * @param width the width of the texture
	 * @param height the height of the texture
	 * @return whether the load was successful
	 */
	public static boolean loadImage(GL3 gl, String path, String name, int width, int height)
	{
		try
		{
			BufferedImage img = ImageIO.read(new File(ContentManager.ROOT_DIR + path));
			
			if (!ContentManager.TEXTURES.containsKey(name))
			{
				ContentManager.TEXTURES.put(name, new Texture(gl, img));
				Logger.log(ContentManager.class, LogSeverity.INFO, "Loaded texture {" + path + "} as {" + name + "}");
				return true;
			}
		} 
		catch (IOException ex)
		{
			Logger.log(ContentManager.class, LogSeverity.ERROR, "Failed to load texture {" + path + "}");
			ex.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Loads a plain text file
	 * @param path the path to the plain text file
	 * @param name the name that the file will be addressed by
	 * @return
	 */
	public static boolean loadText(String path, String name)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(ContentManager.ROOT_DIR + path));
			ArrayList<String> lines = new ArrayList<String>();
			
			for (String line = reader.readLine(); line != null; line = reader.readLine())
			{
				lines.add(line);
			}
			
			String[] array = new String[lines.size()];
			lines.toArray(array);
			
			ContentManager.PLAINTEXT.put(name, array);
			Logger.log(ContentManager.class, LogSeverity.INFO, "Loaded plain text file {" + path + "} as {" + name + "}");
			reader.close();
			return true;			
		} 
		catch (IOException ex)
		{
			Logger.log(ContentManager.class, LogSeverity.ERROR, "Failed to load plain text file {" + path + "}");
			ex.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Removes a texture from the content manager and destroys it
	 * @param name the name of the texture to destroy
	 */
	public static void destroyImage(String name)
	{
		if (ContentManager.TEXTURES.containsKey(name))
		{
			ContentManager.TEXTURES.remove(name).delete();
		}
		else
		{
			Logger.log(ContentManager.class, LogSeverity.ERROR, "Failed to find texture with name: {" + name + "}");
		}
	}

	/**
	 * Removes a plain text file from the content manager
	 * @param name the plain text file to destroy
	 */
	public static void destroyText(String name)
	{
		if (ContentManager.PLAINTEXT.containsKey(name))
		{
			ContentManager.PLAINTEXT.remove(name);
		}
		else
		{
			Logger.log(ContentManager.class, LogSeverity.ERROR, "Failed to find plain text with name: {" + name + "}");
		}
	}
	
	public static void destroyFont(String name)
	{
		if (ContentManager.FONTS.containsKey(name))
		{
			ContentManager.FONTS.remove(name);
		}
		else
		{
			Logger.log(ContentManager.class, LogSeverity.ERROR, "Failed to find font with name: {" + name + "}");
		}
	}
	
	/**
	 * Retrieves a plain text file of the given name
	 * @param name the name of the file
	 * @return the plain text file as a string array, or an empty string if loading fails
	 */
	public static String[] getTextByName(String name)
	{
		if (ContentManager.PLAINTEXT.containsKey(name))
		{
			return ContentManager.PLAINTEXT.get(name);
		}
		else
		{
			Logger.log(ContentManager.class, LogSeverity.ERROR, "Failed to find plain text with name: {" + name + "}");
			return new String[] { "" };
		}
	}

	/**
	 * Retrieves a texture of the given name
	 * @param name the name of the texture
	 * @return the texture with that name, or null is loading fails
	 */
	public static Texture getImageByName(String name)
	{
		if (ContentManager.TEXTURES.containsKey(name))
		{
			return ContentManager.TEXTURES.get(name);
		}
		else
		{
			Logger.log(ContentManager.class, LogSeverity.ERROR, "Failed to find texture with name: {" + name + "}");
			return null;
		}
	}
	
	public static Font getFontByName(String name)
	{
		if (ContentManager.FONTS.containsKey(name))
		{
			return ContentManager.FONTS.get(name);
		}
		else
		{
			Logger.log(ContentManager.class, LogSeverity.ERROR, "Failed to find font with name: {" + name + "}");
			return null;
		}
	}
}
