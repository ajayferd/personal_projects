import java.awt.Color;

import javax.swing.BorderFactory;


public class Quantum {
	public static void doQuantumSim()
	{	
		//sets borders around boxes
		for( int iterator8 = 0; iterator8<25;iterator8++){
			Cryptool.simIndexNumbersLabels[iterator8].setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			Cryptool.aliceMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			Cryptool.aliceSchemeBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			Cryptool.alicePolMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			Cryptool.eveSchemeBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			Cryptool.bobSchemeBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			Cryptool.bobPolMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			Cryptool.bobMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
		}
		
		
		for( int iterator8 = 0;iterator8<20;iterator8++){
			Cryptool.guessIndexNumberLabels[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			Cryptool.guessIndexNumberLabels[iterator8].setText("");
			Cryptool.correctGuessAMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			Cryptool.correctGuessAMessageBoxes[iterator8].setText("");
			Cryptool.correctGuessBMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			Cryptool.correctGuessBMessageBoxes[iterator8].setText("");                          
		}
		
		for(int i=0;i<25;i++){
			int value1 = (int)(Math.random() * 10);
			value1 = value1 % 2;
			int value2 = (int)(Math.random() * 10);
			value2 = value2 % 2;
			int value3 = (int)(Math.random() * 10);
			value3 = value3 % 2;
			int value4 = (int)(Math.random() * 10);
			value4 = value4 % 2;
			int value5 = (int)(Math.random() * 10);
			value5 = value5 % 2;
			
			//sets [AM] field
			Cryptool.aliceMessageBoxes[i].setText(" "+ value1);
			
			//sets [AS] field
			if(value2 == 1)
				Cryptool.aliceSchemeBoxes[i].setText("  +");
			else 
				Cryptool.aliceSchemeBoxes[i].setText("  x");
			
			//sets [APM] field
			if(value1 == 1){
				if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  +"))
					Cryptool.alicePolMessageBoxes[i].setText("  |");
				else
					Cryptool.alicePolMessageBoxes[i].setText("  /");
			}
			else{
				if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  +"))
					Cryptool.alicePolMessageBoxes[i].setText(" --");
				else
					Cryptool.alicePolMessageBoxes[i].setText("  \\");
			}
		
		
			//sets [ES] field
			if(value3 == 1)
				Cryptool.eveSchemeBoxes[i].setText("  +");
			else 
				Cryptool.eveSchemeBoxes[i].setText("  x");
		
			//sets [BS] field
			if(value4 == 1)
				Cryptool.bobSchemeBoxes[i].setText("  +");
			else 
				Cryptool.bobSchemeBoxes[i].setText("  x");
			
			
			//set [BPM] field
			if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  x") && 
				(Cryptool.eveSchemeBoxes[i].getText()).equals("  x") &&	
				(Cryptool.bobSchemeBoxes[i].getText()).equals("  x")      ){
				
				Cryptool.bobPolMessageBoxes[i].setText(Cryptool.alicePolMessageBoxes[i].getText());
			}
			
			if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  +") && 
					(Cryptool.eveSchemeBoxes[i].getText()).equals("  x") &&	
					(Cryptool.bobSchemeBoxes[i].getText()).equals("  x")      ){
				
				if(value5 == 1)
					Cryptool.bobPolMessageBoxes[i].setText("  /");
				else
					Cryptool.bobPolMessageBoxes[i].setText("  \\");
			}
		
		
			if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  x") && 
					(Cryptool.eveSchemeBoxes[i].getText()).equals("  +") &&	
					(Cryptool.bobSchemeBoxes[i].getText()).equals("  x")      ){
				
				int value6 = (int)(Math.random() * 10);
				value6 = value6 % 2;
				
				if(value6 == 1)
					Cryptool.bobPolMessageBoxes[i].setText("  /");
				else
					Cryptool.bobPolMessageBoxes[i].setText("  \\");
			}
		
			if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  x") && 
					(Cryptool.eveSchemeBoxes[i].getText()).equals("  x") &&	
					(Cryptool.bobSchemeBoxes[i].getText()).equals("  +")      ){
				
				int value7 = (int)(Math.random() * 10);
				value7 = value7 % 2;
				
				if(value7 == 1)
					Cryptool.bobPolMessageBoxes[i].setText("  |");
				else
					Cryptool.bobPolMessageBoxes[i].setText("  --");
			
			}	
			
			if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  x") && 
					(Cryptool.eveSchemeBoxes[i].getText()).equals("  +") &&	
					(Cryptool.bobSchemeBoxes[i].getText()).equals("  +")      ){
				
				int value7 = (int)(Math.random() * 10);
				value7 = value7 % 2;
				
				if(value7 == 1)
					Cryptool.bobPolMessageBoxes[i].setText("  |");
				else
					Cryptool.bobPolMessageBoxes[i].setText("  --");
			
			}
		
			if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  +") && 
					(Cryptool.eveSchemeBoxes[i].getText()).equals("  x") &&	
					(Cryptool.bobSchemeBoxes[i].getText()).equals("  +")      ){
				
				int value7 = (int)(Math.random() * 10);
				value7 = value7 % 2;
				
				if(value7 == 1)
					Cryptool.bobPolMessageBoxes[i].setText("  |");
				else
					Cryptool.bobPolMessageBoxes[i].setText("  --");
			
			}
		
			if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  +") && 
					(Cryptool.eveSchemeBoxes[i].getText()).equals("  +") &&	
					(Cryptool.bobSchemeBoxes[i].getText()).equals("  x")      ){
				
				int value7 = (int)(Math.random() * 10);
				value7 = value7 % 2;
				
				if(value7 == 1)
					Cryptool.bobPolMessageBoxes[i].setText("  /");
				else
					Cryptool.bobPolMessageBoxes[i].setText("  \\");
			
			}
		
			if((Cryptool.aliceSchemeBoxes[i].getText()).equals("  +") && 
					(Cryptool.eveSchemeBoxes[i].getText()).equals("  +") &&	
					(Cryptool.bobSchemeBoxes[i].getText()).equals("  +")      ){
				
				int value7 = (int)(Math.random() * 10);
				value7 = value7 % 2;
				
				if(value7 == 1)
					Cryptool.bobPolMessageBoxes[i].setText("  |");
				else
					Cryptool.bobPolMessageBoxes[i].setText("  --");
			
			}
		
		
			if((Cryptool.bobPolMessageBoxes[i].getText()).equals("  |"))
				Cryptool.bobMessageBoxes[i].setText(" 1");
			if((Cryptool.bobPolMessageBoxes[i].getText()).equals("  --"))
				Cryptool.bobMessageBoxes[i].setText(" 0");
			if((Cryptool.bobPolMessageBoxes[i].getText()).equals("  /"))
				Cryptool.bobMessageBoxes[i].setText(" 1");
			if((Cryptool.bobPolMessageBoxes[i].getText()).equals("  \\"))
				Cryptool.bobMessageBoxes[i].setText(" 0");
		}
		
		int counter = 0;
		for(int iterator9=0;iterator9<25;iterator9++){
			if(  !(Cryptool.aliceSchemeBoxes[iterator9].getText()).equals(Cryptool.bobSchemeBoxes[iterator9].getText())      ){
				Cryptool.aliceSchemeBoxes[iterator9].setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
				Cryptool.bobSchemeBoxes[iterator9].setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
			}
			else{
				Cryptool.simIndexNumbersLabels[iterator9].setBorder(BorderFactory.createLineBorder(Color.blue, 2));
				Cryptool.guessIndexNumberLabels[counter].setText(""+iterator9);
				Cryptool.correctGuessAMessageBoxes[counter].setText(Cryptool.aliceMessageBoxes[iterator9].getText());
				Cryptool.correctGuessBMessageBoxes[counter].setText(Cryptool.bobMessageBoxes[iterator9].getText());
				counter++;
			}
			
			if(!(Cryptool.aliceMessageBoxes[iterator9].getText()).equals(Cryptool.bobMessageBoxes[iterator9].getText())  ){
				Cryptool.aliceMessageBoxes[iterator9].setBorder(BorderFactory.createLineBorder(Color.red, 2));
				Cryptool.bobMessageBoxes[iterator9].setBorder(BorderFactory.createLineBorder(Color.red, 2));
			}
		}
		
		for(int iterator10=0;iterator10<counter;iterator10++){
			Cryptool.guessIndexNumberLabels[iterator10].setBorder(BorderFactory.createLineBorder(Color.blue, 2));
			if(!(Cryptool.correctGuessAMessageBoxes[iterator10].getText()).equals(Cryptool.correctGuessBMessageBoxes[iterator10].getText())  ){
				Cryptool.correctGuessAMessageBoxes[iterator10].setBorder(BorderFactory.createLineBorder(Color.red, 2));
				Cryptool.correctGuessBMessageBoxes[iterator10].setBorder(BorderFactory.createLineBorder(Color.red, 2));
			}
		}
	}
}
