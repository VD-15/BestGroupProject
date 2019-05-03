package core;

/**
 * Main entry point for the application
 * @author Vee
 *
 */
public class Program
{
	
	public static String[] arguments;
	
	public static void main(String[] args)
	{
		arguments = args;
		GameWindow g = new GameWindow();
		g.run();
	}
}
