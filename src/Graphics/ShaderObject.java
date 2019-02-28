package Graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ShaderObject
{
	/**
	 * Loads shader code from the given file path.
	 * @param path The path to the file to read.
	 * @return An ArrayList containing the text in the file selerated by line.
	 */
	protected final ArrayList<String> loadShader(String path)
	{
		ArrayList<String> lines = new ArrayList<String>();
		
		//Load the shader from the given path
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			String line;
			while ((line = reader.readLine()) != null)
			{
				lines.add(line);
			}
			
			reader.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		return lines;
	}
}
