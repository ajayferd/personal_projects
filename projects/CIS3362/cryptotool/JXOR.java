import java.awt.Graphics;

import javax.swing.JPanel;

// This is a small extension of JPanel used to create the XOR on the LFSR tab.
// It keeps track of the bits that are used in the XOR as well.

public class JXOR extends JPanel
{
	boolean xorBits[] = new boolean[8];
	
	public JXOR()
	{
		for(int i=0;i<8;++i)
			xorBits[i] = false;
	}
	
	public void setBit(int i)
	{
		xorBits[i] = !xorBits[i];
		this.repaint();
	}
	
	public boolean getBit(int i)
	{
		return xorBits[i];
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int w = getWidth();
		int h = getHeight();
		
		g.drawLine(0, h-20, w, h-20);
		g.drawOval(0, h-30, 20, 20);
		g.drawLine(10, 0, 10, h-10);
		
		for(int i=0;i<8;++i)
		{
			if(xorBits[i])
			{
				g.drawLine(54+(i*25), 0, 54+(i*25), h-20);
				//g.drawLine(0,0,5,5);
			}
		}
	}
}