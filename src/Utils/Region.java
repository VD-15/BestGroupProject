package Utils;

public final class Region implements Cloneable
{
	public float x;
	public float y;
	public float width;
	public float height;
	
	public Region()
	{
		this.x = 0f;
		this.y = 0f;
		this.width = 0f;
		this.height = 0f;
	}
	
	public Region(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Region(Vector2 location, Vector2 size, boolean centered)
	{
		if (centered)
		{
			this.x = location.x - (size.x / 2f);
			this.y = location.y - (size.y / 2f);
			this.width = size.x;
			this.height = size.y;
		}
		else
		{
			this.x = location.x;
			this.y = location.y;
			this.width = size.x;
			this.height = size.y;
		}
	}
	
	public Vector2 getLocation()
	{
		return new Vector2(this.x, this.y);
	}
	
	public void setLocation(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vector2 getSize()
	{
		return new Vector2(this.width, this.height);
	}
	
	public void setSize(Vector2 v)
	{
		this.width = v.x;
		this.height = v.y;
	}
	
	public Vector2 getCenter()
	{
		return new Vector2(this.x + (this.width / 2), (this.y + this.height / 2));
		
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o.getClass() == Region.class)
		{
			Region r = (Region)o;
			return (r.x == this.x && r.y == this.y && r.width == this.width && r.height == this.height);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public Region clone()
	{
		return new Region(this.x, this.y, this.width, this.height);
	}
}
