public class Vigenere
{
	static double eng_freq[] = { .084966, .020720, .045388,.033844,.111607,.018121,.024705,.030034,.075448,.001965,
		  .011016,.054893,.030129,.066544,.071635,.031671,.001962,.075809,.057351,.069509,
		  .036308,.010074,.012899,.002902,.017779,.002722 };
	
	static String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"
		,"P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public static void doEngFreq()
	{
		double freqMax[] = new double[26];
		double max = 0;
		
		//calculates and rounds off the frequency based on the total number of letters
		for(int i=0;i<26;i++)
		{			
			if(eng_freq[i] > max)
				max = eng_freq[i];
		}
		
		// calculate the frequency in relation to the maximum frequency.
		// for instance if we have a frequency of 4 As (which is the max) and
		// a frequency of 2 Bs, then 4 As will have a frequency of 100%, and
		// 2 Bs will have a frequency of 50%.
		for(int i=0;i<26;++i)
			freqMax[i] = (double) eng_freq[i]/max;
		
		// call our custom JGraph panel and set the frequencies for each letter
		// so that it may draw them.
		for(int i=0;i<2;++i)
			Cryptool.engFrequencyGraph[i].setFreqs(freqMax);
	}
	
	// The following function determines the frequency of letters in our plaintext, and accounts for if we split the text into
	// one or more bins (up to 14)
	public static void doVigFreq()
	{
		String text= Cryptool.vigenereText.getText();
		text = text.toUpperCase();
		double freq[][] = new double[Cryptool.MAX_GRAPHS][26];
		double freqPercent[][] = new double[Cryptool.MAX_GRAPHS][26];
		double freqMax[][] = new double[Cryptool.MAX_GRAPHS][26];
		double total[] = new double[Cryptool.MAX_GRAPHS];
		double percentage = 0;
		double max[] = new double[Cryptool.MAX_GRAPHS];
		int temp = 0;
		
		String bins = Cryptool.numBins.getText();
		
		for(int i=0;i<Cryptool.MAX_GRAPHS;++i)
		{
			total[i] = 0;
			max[i] = 0;
		}
		
		int numBins = Integer.parseInt(bins);
		
		// each row in freq[][] tells us the frequency of letters for each bin.
		// for instance, if we have five bins, every fifth letter will belong to the same bin. 0, 4, and the 9th character
		// will be in the same bin and 1, 5 and 10th character will be in the second bin, and so forth. from these bins,
		// their frequency is going to be calculated.
		for(int i=0;i<numBins;++i)
		{
			for(int k=0;k<26;k++){
				freq[i][k] = 0;
			}
		}
		
		// scans in each letter into an array
		
		// for each bin we want to fill,
		for(int i=0;i<numBins;++i)
		{
			// start in the plaintext where that bin should start,
			// and capture every numBins'th character
			for(int j=i;j<text.length();j+=numBins)
			{
				// loop through the alphabet, to match our letter to its position in the alphabet.
				for(int k=0;k<26;k++)
				{
					// if we've found a match,
					if(  Cryptool.alphabet[k].charAt(0) == (text.charAt(j)) )
					{
						// increment the total # of chars for the bin
						total[i]++;
						
						// and increment the number of the particular character we are looking at for that bin
						freq[i][k]++;
					}
				}	
			}
		}
		
		//calculates and rounds off the frequency based on the total number of letters
		
		// for each bin,
		for(int j=0;j<numBins;++j)
		{
			// loop through the alphabet,
			for(int i=0;i<26;i++)
			{
				// and determine the frequency of each letter based on the total number of letters for the bin.
				freqPercent[j][i] = (double) freq[j][i]/total[j];
				
				// if this frequency is the max so far, set it for that particular bin.
				if(freqPercent[j][i] > max[j])
					max[j] = freqPercent[j][i];
			}
		}
		
		// for each bin,
		for(int j=0;j<numBins;++j)
			// loop through the alphabet,
			for(int i=0;i<26;++i)
				// and determine the final frequency related to the maximum frequency.
				freqMax[j][i] = (double) freqPercent[j][i]/max[j];
		
		// finally, set the frequency graphs we've customly created, based on the 
		// frequencies we just computed for the plaintext.
		for(int i=0;i<Cryptool.MAX_GRAPHS;++i)
			Cryptool.vigFrequencyGraphs[i].setFreqs(freqMax[i]);
	}
	
	/* Finds the index of coincidence based upon the keywords length input by the user */
	public static void doCoincidence()
	{
		int keylength = 0;
		//gets the key length from the user
		String kl = Cryptool.indexCoincidenceText.getText();
		if( kl.equals("")){
			keylength = 1;
		}
		else{
		//makes sure the users key length remains in the domain: 1<= keylength <= 20
		keylength = Integer.parseInt(kl);
		keylength = Math.min(keylength, 20);
		keylength = Math.max(keylength, 1);
		}
		String ciphertext = Cryptool.vigenereText.getText();
		ciphertext = ciphertext.toUpperCase();
		
		double index[] = new double[keylength];
		String outPut[] = new String[keylength];
		double oP = 0;
		int temp = 0;
		float temp2 = 0;
		String fixedtext = "";
		String finalText = "";
		int total = 0;
		
		//fixes the ciphertext into a single string
		for (int i = 0; i < ciphertext.length(); i++)
			if (ciphertext.codePointAt(i) >= 65 && ciphertext.codePointAt(i) <= 90)
				fixedtext += ciphertext.charAt(i);

		
		for (int i = 0; i < keylength; i++)
		{
			double freq[] = new double[26];
			total = (fixedtext.length() - 1 - i) / keylength;

			for (int j = 0; j < 26; j++)
				freq[j] = 0;
			
			for (int j = i; j < fixedtext.length(); j += keylength){
				if (fixedtext.codePointAt(j) >= 65 && fixedtext.codePointAt(j) <= 90)
					freq[fixedtext.codePointAt(j)-65]++;
			}	

			//calculates using index of coincidence algorithm, setting it to the coressponding
			//index of the keylength repeated throughout the text
			for (int j = 0; j < 26; j++)
			{
				if (freq[j] < 2)
					continue;
				index[i] += (freq[j] * (freq[j]-1.0)) / (total * (total-1.0));

			}
			//takes care of rounding
			oP = (index[i]* 10000);
			temp = (int)(oP);
			temp2 = (float)temp;
			outPut[i] = "Index " + i + ": " + Float.toString(temp2/100) + "      ";
			
			
		}
		//combines the different index of coincidences into one single string 
		for(int k=0;k<keylength;k++){
			finalText += outPut[k];
		}
		//sets the final text in the indexCoincidence2Text text box for the users viewing
		Cryptool.indexCoincidence2Text.setText(finalText);
		
	}
}