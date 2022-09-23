
public class Playfair {
	public static void doPfCopy()
	{
		char letters[][] = Cryptool.pfMatrix.getLetters();
		
		for(int i=0;i<25;++i)
			Cryptool.pfCustMatrix[i].setText(String.valueOf(letters[i/5][i%5]));
	}
	
	// The following function generates the 5x5 matrix, given a keyword from the user.
	public static void doPfGenerate()
	{
		int i,j;
		
		char ref[] = { 'A','B','C','D','E','F','G','H','I','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		
		String keyword = Cryptool.pfKeyword.getText();
		keyword = keyword.replace('J','I');
		keyword = keyword.replace('j','i');
		
		char letters[] = new char[25];
		
		// Begin by inserting all unique characters into the 5x5 array.
		// Check to make sure that the character being added hasn't been added yet.
		for(i=0,j=0;i<keyword.length();++i)
			if(String.valueOf(letters).indexOf(keyword.charAt(i)) == -1)
				letters[j++] = keyword.charAt(i);
		
		String newKeyword = new String(letters,0,j);
		
		// convert our keyword to uppercase. for example, a keyword of 
		// mississippi would be MISP.
		newKeyword = newKeyword.toUpperCase();
		
		// fill in the blanks to the rest of the 5x5 matrix, any character that isn't
		// in the keyword.
		for(i=0;i<25;++i)
		{
			if(newKeyword.indexOf(ref[i]) == -1)
			{
				letters[j++] = ref[i];
			}
		}
		
		String strLetters = new String(letters);
		
		strLetters = strLetters.toUpperCase();
		
		// set the letters of the Matrix class we wrote (that draws the 5x5 matrix)
		Cryptool.pfMatrix.setLetters(strLetters.toCharArray());
	}
	
	// the following function encrypts a plaintext to ciphertext using playfair.
	public static void doPfEncrypt()
	{
		String plaintext = Cryptool.pfPlainText.getText();
		
		// convert plaintext to comply with playfair rules
		plaintext = ppPfPlainText(plaintext);
		
		// conver to uppercase
		plaintext = plaintext.toUpperCase();
		
		String ciphertext = "";
		
		// go through the plaintext two chars at a time, calling the 'decodePf' function with the matrix, the two characters, and a boolean set to true signifying we're encrypting.
		for(int i=0;i<plaintext.length();i+=2)
		{
			ciphertext = ciphertext.concat(decodePf(Cryptool.pfMatrix.getLetters(), plaintext.charAt(i), plaintext.charAt(i+1), true));
		}
		
		// set ciphertext to screen so user can see.
		Cryptool.pfCipherText.setText(ciphertext);
	}
	
	public static void doPfDecrypt()
	{
		String ciphertext = Cryptool.custCipherText.getText();
		
		ciphertext = ciphertext.toUpperCase();
		
		String plaintext = "";
		
		char letters[][] = new char[5][5];
		
		for(int i=0;i<25;++i)
		{
			// when decrypting, check the custom matrix. if a letter is empty, add a dash to 
			// the 5x5 matrix, for the decryption function to use.
			if(Cryptool.pfCustMatrix[i].getText().equals(""))
				letters[i/5][i%5] = '-';
			else
				letters[i/5][i%5] = Cryptool.pfCustMatrix[i].getText().charAt(0);
		}		
		
		for(int i=0;i<ciphertext.length();i+=2)
		{
			// call decodePf with the matrix, the letters, and false signifying that we want to decrypt (the rules are used in the reverse way)
			plaintext = plaintext.concat(decodePf(letters, ciphertext.charAt(i), ciphertext.charAt(i+1), false));
		}
		
		// set the plaintext so the user can see.
		Cryptool.custPlainText.setText(plaintext);		
	}
	
	// The following function converts the plaintext to comply with the rules of Playfair.
	// For instance, the letter j is removed and replaced with i, and a dummy character
	// is placed between any double letters. finally, if the number of plaintext characters
	// is odd, add a dummy character to the end to make it even.
	public static String ppPfPlainText(String plaintext)
	{
		String newPlaintext = "";
		
		for(int i=0;i<plaintext.length();++i)
		{
			// if char is j, add i to new plaintext
			if(plaintext.charAt(i) == 'j')
				newPlaintext = newPlaintext.concat("i");
			// else if the current char equals the next char, then add a q between the two.
			else if((i != (plaintext.length()-1)) && plaintext.charAt(i) == plaintext.charAt(i+1))
			{
				String conversion = String.valueOf(plaintext.charAt(i));
				conversion = conversion.concat("q");
				
				newPlaintext = newPlaintext.concat(conversion);
			}
			// else add the car to the new plaintext like usual
			else
				newPlaintext = newPlaintext.concat(String.valueOf(plaintext.charAt(i)));			
		}
		
		// add dummy char is length is odd
		if(newPlaintext.length()%2!=0)
			newPlaintext = newPlaintext.concat(String.valueOf("q"));
		
		return newPlaintext;
	}
	
	
	// The following function determines the decoded and encoded (it works both ways)
	// string given the 5x5 matrix.
	public static String decodePf(char[][] matrix, char a, char b, boolean encrypt)
	{
		int row_a=-1, row_b=-1, col_a=-1, col_b=-1;
		int row_c, row_d, col_c, col_d;
		
		char result[] = new char[2];
		
		// Find out the positions of characters A and B.
		for(int i=0;i<5;++i)
		{
			for(int j=0;j<5;++j)
			{
				if(matrix[i][j] == a)
				{
					row_a = i;
					col_a = j;
				}
				
				if(matrix[i][j] == b)
				{
					row_b = i;
					col_b = j;
				}
			}
		}
		
		// If one of the characters cannot be found (if we have an imcomplete matrix), then return '--'
		// to note that we can't find the matching pair of letters.
		if((row_a == -1 && col_a == -1) ||
		   (row_b == -1 && col_b == -1))
		{
			result[0] = '-';
			result[1] = '-';
		}
		else
		{
			// if the characters are on the same row, then select the two characters to the right of each (if encrypting)
			// or to the left of each (if decrypting)
			if(row_a == row_b)
			{
				row_c = row_a;
				col_c = (encrypt ? (col_a+1)%5 : (col_a == 0 ? 4 : (col_a-1)));
				row_d = row_b;
				col_d = (encrypt ? (col_b+1)%5 : (col_b == 0 ? 4 : (col_b-1)));
			}
			// else if the characters are on the same column, then select the two characters below if encrypting
			// or above if decrypting
			else if(col_a == col_b)
			{
				row_c = (encrypt ? (row_a+1)%5 : (row_a == 0 ? 4 : (row_a-1)));
				col_c = col_a;
				row_d = (encrypt ? (row_b+1)%5 : (row_b == 0 ? 4 : (row_b-1)));
				col_d = col_b;
			}
			// else the two characters are in different rows and columns, so create a square using the two points,
			// and select the two characters that create the two other points.
			else
			{
				row_c = row_a;
				col_c = col_b;
				row_d = row_b;
				col_d = col_a;
			}
			
			result[0] = matrix[row_c][col_c];
			result[1] = matrix[row_d][col_d];
		}
		
		return new String(result);
	}
}
