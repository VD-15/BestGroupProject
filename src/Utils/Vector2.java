package Utils;

public final class Vector2 implements Cloneable
{
	public float x;
	public float y;
	
	public Vector2()
	{
		this.x = 0f;
		this.y = 0f;
	}
	
	public Vector2(float xy)
	{
		this.x = xy;
		this.y = xy;
	}
	
	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float[] getArray()
	{
		return new float[] {x, y};
	}
	
	public Vector2 rotate(float a)
	{
		float cosa = (float)Math.cos(a);
		float sina = (float)Math.sin(a);
		
		float newX = (x * cosa) - (y * sina);
		float newY = (y * cosa) + (x * sina);
		
		return new Vector2(newX, newY);
	}
	
	public Vector2 rotateAround(float a, Vector2 o)
	{
		float cosa = (float)Math.cos(a);
		float sina = (float)Math.sin(a);
		
		float newX = ((x - o.x) * cosa) - ((o.y - y) * sina) + o.x;
		float newY = ((o.y - y) * cosa) - ((x - o.x) * sina) + o.y;
		
		return new Vector2(newX, newY);
	}
	
	public Vector2 scale(float s)
	{
		return new Vector2(x * s, y * s);
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o.getClass() ==  Vector2.class)
		{
			Vector2 v = (Vector2)o;
			return (v.x == this.x && v.y == this.y);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public Vector2 clone()
	{
		return new Vector2(this.x, this.y);
	}
}
