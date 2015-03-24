import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GameClient extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener
{

	private Socket socket;
	// The streams we communicate to the server; these come
	// from the socket
	private ObjectOutputStream dout;
	private ObjectInputStream din;
	private Player player;
	private ArrayList<Player> mp = new ArrayList<Player>();
	
	public GameClient(String host,int port,String username)
	{
		player = new Player(200,200,username) ;
		try {
			// Initiate the connection
			socket = new Socket(host, port);
			// We got a connection! Tell the world
			System.out.println("connected to " + socket);
			// Let's grab the streams and create DataInput/Output streams
			// from them
			din = new ObjectInputStream(socket.getInputStream());
			dout = new ObjectOutputStream(socket.getOutputStream());
			// Start a background thread for receiving messages
			//new Thread(this).start();
		} catch (IOException ie) {
			System.out.println(ie);
		}	
	setFocusable( true );
	//setVisible(true);
	new Thread(this).start();
	addKeyListener(this);
	addMouseListener(this);
	addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 400, 400);
		
		
		g.setColor(Color.CYAN);
		player.drawPlayer(g);
		for(int i=0;i<mp.size();i++)
		{
			System.out.println("hi"+mp.get(i).getX()+" "+mp.size());
			mp.get(i).drawPlayer(g);
			
		}
		
		
	}
	

	@Override
	public void run() {
		try {
			// Receive messages one-by-one, forever
			while (true) {
				// Get the next message
				//System.out.println("blah");
				Player message = (Player) din.readObject();
				//System.out.println(message.getX());
				//if(!mp.contains(message)&&!message.getUserName().equals(player.getUserName()))
				boolean add=true;
				for(int i=0;i<mp.size();i++)
				{
					if(mp.get(i).getUserName().equals(message.getUserName()))
					{
						add=false;
						if(mp.get(i).getX()!=message.getX()||mp.get(i).getY()!=message.getY())
						{
							mp.remove(i);
							add=true;
						}
							
					}
				}
				if(add==true)
					mp.add(message);
				//System.out.println(message);
				// Print it to our text window
				//ta.append(message + "\n");
				
				/*Scanner scan = new Scanner(message);
				String getuser=scan.next();
				x=scan.nextInt();
				scan.close();*/
				repaint();
				
				try {
					Thread.currentThread();
					Thread.sleep(8);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException ie) {
			System.out.println(ie);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void sendInfo() {
		try {
			// Send it to the server
			
			dout.writeObject(player);
			dout.reset();
			// Clear out text input field
		} catch (IOException ie) {
			System.out.println(ie);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) 
			{
				player.moveRight();
				System.out.println(player.getX());
			}
			else if(e.getKeyCode()==KeyEvent.VK_LEFT) 
			{
				player.moveLeft();
			}
	
			if(e.getKeyCode()==KeyEvent.VK_UP)
			{
				player.moveUp();
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN) 
			{
				player.moveDown();
			}
			
			if(e.getKeyCode()==KeyEvent.VK_SPACE) 
			{
				//sendInfo();
			}
			
			sendInfo();
		
		//System.out.println(Variables.directionHeldX+" "+Variables.directionHeldY);
	}

	@Override
	public void keyReleased(KeyEvent e) {
			
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//grabbed = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	

}
