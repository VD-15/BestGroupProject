package utils;

/**
 * Stores two floating-point values ( x, y )
 * Used for the tile coordinates and anything where atomic values are required
 * @author Vee
 */
public class Point implements Cloneable
{
	/**
	 * X-component of this point
	 */
	public int x;
	
	/**
	 * Y-component of this point
	 */
	public int y;
	
	/**
	 * Creates a new Point with zero values
	 */
	public Point()
	{
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Creates a new Point with equal x and y value
	 * use Point(int x, int y) for specifying separate values
	 * @param xy - The value of x and y
	 */
	public Point(int xy)
	{
		this.x = xy;
		this.y = xy;
	}
	
	/**
	 * Creates a new Point with specified x, y values
	 * @param x
	 * @param y
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Point clone()
	{
		return new Point(this.x, this.y);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Point)
		{
			Point p = (Point)o;
			
			return (p.x == this.x && p.y == this.y);
		}
		
		return false;
	}
}
