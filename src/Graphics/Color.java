package Graphics;

public final class Color implements Cloneable
{
	public float r;
	public float g;
	public float b;
	public float a;
	
	public Color()
	{
		this.r = 0f;
		this.g = 0f;
		this.b = 0f;
		this.a = 1f;
	}
	
	public Color(float _r, float _g, float _b)
	{
		this.r = _r;
		this.g = _g;
		this.b = _b;
		this.a = 1f;
	}
	
	public Color(float _r, float _g, float _b, float _a)
	{
		this.r = _r;
		this.g = _g;
		this.b = _b;
		this.a = _a;
	}
	
	public float[] getArray()
	{
		return new float[] {r, g, b, a};
	}
	
	public Color multiply(Color c)
	{
		return new Color(this.r * c.r, this.g * c.g, this.b * c.b, this.a * c.a);
	}
	
	public Color multiply(float f)
	{
		return new Color(this.r, this.g, this.b, this.a * f);
	}
	
	@Override
	public Color clone()
	{
		return new Color(this.r, this.g, this.b, this.a);
	}
	
	public static Color WHITE()
	{
		return new Color(1f, 1f, 1f, 1f);
	}
	
	public static Color BLACK()
	{
		return new Color(0f, 0f, 0f, 1f);
	}
	
	public static Color ALPHA()
	{
		return new Color (0f, 0f, 0f, 0f);
	}
	
	public static Color RED()
	{
		return new Color(1f, 0f, 0f, 1f);
	}
	
	public static Color GREEN()
	{
		return new Color(0f, 1f, 0f, 1f);
	}
	
	public static Color BLUE()
	{
		return new Color(0f, 0f, 1f, 1f);
	}
}
