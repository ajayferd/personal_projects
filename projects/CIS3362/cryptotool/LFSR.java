
public class LFSR {
	// LFSR is relatively simple, we have created a JXOR panel that we use to
	// let the user select which bits are used in the XOR'ing, which is input
	// back into the beginning of the 8 bit array (hence the name 'feedback')
	// for the linear feedback shift register.
	public static void doLfsrStep()
	{
		boolean getbits[] = new boolean[8];
		int bits[] = new int[8];
		int j = 0;
		
		// get a true or false value for bits 0..7 selected by the user to XOR
		for(int i=0;i<8;++i)
			getbits[i] = Cryptool.xorDrawing.getBit(i);
		
		// it is possible for the user to XOR all 8 bits, however, they may have chosen
		// only three. so, we keep a separate counter, j, to count how many actual
		// XORs will take place.
		// while looping through bits i = 0..7, if getbits[i] is true, that tells us the user
		// wants the bit stored at the location to be XORed, so add it to our bits array.
		for(int i=0;i<8;++i)
			if(getbits[i])
				bits[j++] = Integer.valueOf(Cryptool.lfsrBits[i].getText()); 
		
		// now, we have all of the bits that need to be XOR'd together that the user has requested.
		// we want to start at the back, and XOR the last two bits. for instance, if we are XORing
		// bits 6 and 7, we will place the result into bit 6. on the next iteration, bits 6 and 5
		// will be XOR'd and placed into bit 5. this continues until the final result is stored
		// in bit 0.
		for(int i=j-1;i>0;--i)
			bits[i-1] = bits[i] ^ bits[i-1];
				
		String randomBits = Cryptool.lfsrRandomBits.getText();
		
		// push the 7th bit onto the random bits text area.
		randomBits = randomBits.concat(Cryptool.lfsrBits[7].getText());
		
		Cryptool.lfsrRandomBits.setText(randomBits);
		
		// shift all of the bits to the right make room for the new one.
		for(int i=7;i>0;--i)
			Cryptool.lfsrBits[i].setText(Cryptool.lfsrBits[i-1].getText());
		
		// set the first bit to the one we just computed.
		Cryptool.lfsrBits[0].setText(String.valueOf(bits[0]));
	}
}
