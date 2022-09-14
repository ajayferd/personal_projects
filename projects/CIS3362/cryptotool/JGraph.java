import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

/* This is an extension to the JPanel class that draws the frequency bar graphs used for Vigenere. */

public class JGraph extends JPanel
{
	double freqs[] = new double[26];
	int pos;
	String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public JGraph()
	{
		for(int i=0;i<26;++i)
			freqs[i] = 0;
		pos = 0;
	}
	
	public void setFreqs(double freq[])
	{
		for(int i=0;i<26;++i)
			freqs[i] = freq[i];
		this.repaint();
	}
	
	public void setPos(int _pos)
	{
		pos = _pos;
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    FontMetrics fm = g.getFontMetrics();
	    int font_h = fm.getHeight();
	    
	    int w = getWidth();
	    int h = getHeight()-font_h+3;
	    
	    for(int i=0;i<26;++i)
	    {  	
	    	int bar_w = (w/52);
	    	int bar_h = (int) (h*freqs[transi(i+pos)]);
	    	
	    	g.setColor(Color.black);
	    	g.drawString(alphabet[transi(i+pos)], (bar_w*2)*i, (h)+font_h-5);
	    	
	    	g.setColor(Color.red);
	    	g.fillRect((bar_w*2)*i, (h-bar_h), bar_w, bar_h);
	    }
	}
	
	private int transi(int i)
	{
		return (i < 26 ? i : i - 26); 
	}
}