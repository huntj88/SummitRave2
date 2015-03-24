
import java.awt.Graphics;
import java.io.Serializable;


public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private String userName;
	
	public Player(int x, int y, String userName)
	{
		this.x=x;
		this.y=y;
		this.userName=userName;
	}
	
	public void drawPlayer(Graphics g)
	{
		g.fillRect(x, y, 10, 10);
		g.drawString(userName,x, y-10);
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String toString()
	{
		return userName;
	}
	
	public int getX()
	{
		return x;
	}

}
