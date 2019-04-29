package graphics;

/**
 * Represents a color with 4 floating-point channels
 * @ author Vee
 */
public final class Color implements Cloneable
{ 
	/**
	 * Red component
	 */
	public float r;
	
	/**
	 * Green component
	 */
	public float g;
	
	/**
	 * Blue component
	 */
	public float b;
	
	/**
	 * Alpha component
	 */
	public float a;
	
	/**
	 * Creates an opaque color representing black
	 */
	public Color()
	{
		this.r = 0f;
		this.g = 0f;
		this.b = 0f;
		this.a = 1f;
	}
	
	/**
	 * Creates an opaque color with the given RGB values
	 * @param _r the red component of this color
	 * @param _g the green component of this color
	 * @param _b the blue component of this color
	 */
	public Color(float _r, float _g, float _b)
	{
		this.r = _r;
		this.g = _g;
		this.b = _b;
		this.a = 1f;
	}
	
	/**
	 * Creates a color with the given RGBA values
	 * @param _r the red component of this color
	 * @param _g the green component of this color
	 * @param _b the blue component of this color
	 * @param _a the alpha component of this color
	 */
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
	
	/**
	 * Multiplies two colors together
	 * @param c the color to multiply this object by
	 * @return the product of the color multiplication
	 */
	public Color multiply(Color c)
	{
		return new Color(this.r * c.r, this.g * c.g, this.b * c.b, this.a * c.a);
	}
	
	/**
	 * Multiplies the alpha component of this color by the given float
	 * @param f the float to multiply the alpha by
	 * @return the product of the multiplication
	 */
	public Color multiply(float f)
	{
		return new Color(this.r, this.g, this.b, this.a * f);
	}
	
	/**
	 * Clones this color object
	 */
	@Override
	public Color clone()
	{
		return new Color(this.r, this.g, this.b, this.a);
	}
	
	/**
	 * Creates an object representing the color white
	 * @return a color object representing the color white
	 */
	public static Color WHITE()
	{
		return new Color(1f, 1f, 1f, 1f);
	}

	/**
	 * Creates an object representing the color black
	 * @return a color object representing the color black
	 */
	public static Color BLACK()
	{
		return new Color(0f, 0f, 0f, 1f);
	}

	/**
	 * Creates an object representing a transparent color
	 * @return a color object representing a transparent color
	 */
	public static Color ALPHA()
	{
		return new Color (0f, 0f, 0f, 0f);
	}

	/**
	 * Creates an object representing the color red
	 * @return a color object representing the color red
	 */
	public static Color RED()
	{
		return new Color(1f, 0f, 0f, 1f);
	}

	/**
	 * Creates an object representing the color green
	 * @return a color object representing the color green
	 */
	public static Color GREEN()
	{
		return new Color(0f, 1f, 0f, 1f);
	}

	/**
	 * Creates an object representing the color blue
	 * @return a color object representing the color blue
	 */
	public static Color BLUE()
	{
		return new Color(0f, 0f, 1f, 1f);
	}
}
