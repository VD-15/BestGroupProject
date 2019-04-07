package robotGame.Core;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL3;

import graphics.Texture;
import utils.LogSeverity;
import utils.Logger;

public class ContentManager
{
	private static HashMap<String, Texture> TEXTURES;
	private static HashMap<String, String[]> PLAINTEXT;
	private static String ROOT_DIR;
	
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
	}
	
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
