import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

/* This class is a small extension of the matrix used for Playfair... it draws a 5x5 matrix */

public class JMatrix extends JPanel
{
	char ref[] = { 'A','B','C','D','E','F','G','H','I','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	char letters[][] = new char[5][5];
	
	public JMatrix()
	{
		for(int i=0;i<5;++i)
			for(int j=0;j<5;++j)
				letters[i][j] = ref[(i*5)+j];
	}
	
	public void setLetters(char _letters[][])
	{
		for(int i=0;i<5;++i)
			for(int j=0;j<5;++j)
				letters[i][j] = _letters[i][j];
		this.repaint();
	}
	
	public void setLetters(char _letters[])
	{
		for(int i=0;i<25;++i)
			letters[i/5][i%5] = _letters[i];
		this.repaint();
	}
	
	public char[][] getLetters()
	{
		return letters;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		FontMetrics fm = g.getFontMetrics();
		int font_h = fm.getHeight();
		
		int w = getWidth();
		int h = getHeight();
		
		for(int i=0;i<5;++i)
		{
			for(int j=0;j<5;++j)
			{
				g.setColor(Color.black);
				g.drawChars(letters[i], j, 1, j*15, (font_h)+(font_h*i));
			}
		}
	}
}