package utils;

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
	
	public Vector2 add(Vector2 v)
	{
		return new Vector2(x + v.x, y + v.y);
	}
	
	public Vector2 multiply(float s)
	{
		return new Vector2(this.x * s, this.y * s);
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
		float xdiff = this.x - o.x;
		float ydiff = this.y - o.y;
		
		float newX = cosa * xdiff - sina * ydiff + o.x;
		float newY = sina * xdiff + cosa * ydiff + o.y;
		
		return new Vector2(newX, newY);
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
	
	public void translate(Vector2 v)
	{
		this.x += v.x;
		this.y += v.y;
	}
	
	public void set(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public float length()
	{
		return (float)Math.sqrt((this.x * this.x) + (this.y * this.y));
	}
	
	public void normalize()
	{
		float magnitude = length();
		
		this.x /= magnitude;
		this.y /= magnitude;
	}
	
	public void scale(float s)
	{
		this.x *= s;
		this.y *= s;
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
	public String toString()
	{
		return "Vector2 {X:" + this.x + ", Y:" + this.y + "}";
	}
	
	@Override
	public Vector2 clone()
	{
		return new Vector2(this.x, this.y);
	}
	
	public static Vector2 UP()
	{
		return new Vector2(0f, 1f);
	}

	public static Vector2 DOWN()
	{
		return new Vector2(0f, -1f);
	}

	public static Vector2 LEFT()
	{
		return new Vector2(-1f, 0f);
	}

	public static Vector2 RIGHT()
	{
		return new Vector2(1f, 0f);
	}
}
