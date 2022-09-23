
import java.util.*;


public class Substitution 
{
	public static void doSubstitution()
	{
		//gets text from ciphertext entry box
		String ciphertext = Cryptool.ngramsText.getText();
		ciphertext = ciphertext.toUpperCase();
			
		String subs[] = new String[26];
		
		//takes anything input by the user and puts the letter in the
		//corresponding index of the subs array
		for (int i = 0; i < 26; i++){
			subs[i] = Cryptool.guessBoxes[i].getText();
		}	
						
		String objPlaintext = "";

		//creates the string with the deciphered text
		for (int i = 0;  i < ciphertext.length(); i++)
		{
			//makes sure its substituting characters of the alphabet
			if (ciphertext.codePointAt(i) >= 65 && ciphertext.codePointAt(i) <= 90)
				objPlaintext += subs[ciphertext.codePointAt(i) - 65];
			else
				objPlaintext += ciphertext.charAt(i);
		}
		//sets the final text onto the screen for the user to view
		Cryptool.decipheredText.setText(objPlaintext);
	}
	
	/* Finds the frequencies of each letter within the cipher-text */
	public static void doFreq()
	{
		String text= Cryptool.ngramsText.getText();
		text = text.toUpperCase();
		double freq[] = new double[26];
		double total = 0;
		double percentage = 0;
		int temp = 0;
		
		
		for(int k=0;k<26;k++){
			freq[k] = 0;
		}
		
		//scans in each letter into an array
		for(int i=0;i<text.length();i++)
		{
			for(int j=0;j<26;j++)
			{
				if(Cryptool.alphabet[j].charAt(0) == (text.charAt(i)))
				{
					total++;
					freq[j]++;
				}
			}	
		}
		
		//calculates and rounds off the frequency based on the total number of letters
		for(int i=0;i<26;i++)
		{				
			percentage = ( 1000 * (freq[i]/total) );
			temp = (int)percentage;
			Cryptool.frequencies[i].setText("" + (((float)temp)/10) + "%"  );
		}
		
	}/* End of doFreq()*/
	
	/* Finds the repeated NGrams within the cipher-text */
	public static void doNGrams()
	{
		//takes in text from the nGramsText text area
		Cryptool.nGramsText.setText(" ");
		String text = Cryptool.ngramsText.getText();
		text = text.toUpperCase();
		
		String  fixedtext = "";
		
		//turns the input ciphertext into one single string with no spaces
		for (int i = 0; i < text.length(); i++){
			if (text.codePointAt(i) >= 65 && text.codePointAt(i) <= 90)
				fixedtext += text.charAt(i);
		}
		
		String nGrams = "";
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		TreeSet<String>[] lists = new TreeSet[text.length()];
		for (int i=0; i<lists.length; i++) lists[i] = new TreeSet<String>();
		int count = 0;
		
		//for loops parse through each combination of strings within the fixed
		//text string to find out if they are repeated
		for (int i = 0; i < fixedtext.length() - 2; i++)
		{
			//makes sure that repeated letters are not considered repeated Ngrams
			for (int j = i+2; j < fixedtext.length() - 2; j++)
			{
				String str = "";

				for (int k = 0; k < fixedtext.length()-j; k++)
				{
					if (fixedtext.charAt(i+k) != fixedtext.charAt(j+k))
						break;
					str += fixedtext.charAt(i+k);
				}
				
				//the repeated Ngrams have to be of size 3 or greater
				if (str.length() >= 3)
				{
					if(map.containsKey(str))
					{
						count = map.get(str);
						lists[count].remove(str);
						lists[count+1].add(str);
						map.put(str, count+1);
					}
					else {
						lists[2].add(new String(str));
						map.put(new String(str), 2);
					}
				}
			}
		}
		
		for (int i=lists.length-1; i>=2; i--) {
			if (lists[i].size() == 0) continue;
			for (String s: lists[i]) {
				nGrams += (s + ": "+i+", ");
			}
		}
		
		// chop off last ', ' from string.
		nGrams = nGrams.substring(0, nGrams.length()-2);
		
		Cryptool.nGramsText.setText(nGrams);		
	}/*end of doNGrams()*/
}
