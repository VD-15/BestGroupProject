package Game.Core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL3;

import Graphics.Texture;
import Utils.LogSeverity;
import Utils.Logger;

public class ContentManager
{
	private static HashMap<String, Texture> TEXTURES;
	private static String ROOT_DIR;
	
	public static void setRootDirectory(String root)
	{
		if (!root.endsWith("/"))
		{
			System.out.println("[Content Manager]: (WARN) Root directory does not end with a trailing '/'. Loading content may fail.");
		}
		
		ContentManager.ROOT_DIR = root;
		ContentManager.TEXTURES = new HashMap<String, Texture>();
	}
	
	public static boolean loadImage(GL3 gl, String path, String name, int width, int height)
	{
		BufferedImage img = null;
	
		try
		{
			img = ImageIO.read(new File(ContentManager.ROOT_DIR + path));
			
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
	
	public static void destroyImage(String name)
	{
		Texture t = ContentManager.TEXTURES.remove(name);
		
		if (t != null)
		{
			t.delete();
		}
	}
	
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
}
