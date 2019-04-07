package utils;

public class Point implements Cloneable
{
	public int x;
	public int y;
	
	public Point()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public Point(int xy)
	{
		this.x = xy;
		this.y = xy;
	}
	
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
