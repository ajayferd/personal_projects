import java.math.BigInteger;

import javax.swing.JTextField;

public class RSA 
{
	// this function returns a binary representation of a character.
	public static String getBinary(char c)
	{
		char b[] = new char[8];
		int j = (int) c;
		
		for(int i=0;i<8;++i)
			b[i] = (((j >> (7-i)) & 0x1) == 1 ? '1' : '0');
		
		return String.valueOf(b);
	}
	
	// this function pads the string to the left if it is less than 4 characters (or 32 bits),
	// which is the block size for our particular RSA implementation.
	// for instance, we do not want a two character string's 32 bit  binary representation
	// to be in the high order bits but rather in the low order bits. for example, we want
	// a two character string's 32 bit binary representation to be
	//
	//    00000000 00000000 xxxxxxxx xxxxxxxx
	//
	// and not
	//
	//    xxxxxxxx xxxxxxxx 00000000 00000000
	// 
	// where xxxxxxxx is the bits in the character.
	public static String padString(String s)
	{
		int diff = (s.length()>4?s.length()%4:4-s.length());
		
		char olds[] = s.toCharArray();
		char news[] = new char[s.length()+(4-diff)];
		
		if(diff != 0)
		{
			for(int i=0;i<news.length-4;++i)
				news[i] = olds[i];
			for(int i=news.length-4,j=0;i<news.length;++i,j++)
				news[i] = (j<4-diff?((char)0):olds[i-j]);
		}
		else
		{
			for(int i=0;i<olds.length;++i)
				news[i] = olds[i];
		}
		
		return String.valueOf(news);
	}
	
	// this function performs the RSA encryption.
	public static void doRSAEncrypt()
	{
		BigInteger p = new BigInteger(Cryptool.rsaP.getText());
		BigInteger q = new BigInteger(Cryptool.rsaQ.getText());
		BigInteger d = new BigInteger(Cryptool.rsaD.getText());
		
		// compute N.
		BigInteger n = getRSAN();
		
		BigInteger e,C;
		
		int c;
		
		// compute the mod, which is (p - 1)(q - 1)
		BigInteger mod = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		
		// e is the inverse mod of (p - 1)(q - 1)
		e = d.modInverse(mod);
		
		// pad the string, in case it's length%4 != 0 (see function for explanation)
		char pt[] = padString(Cryptool.rsaPlainText.getText()).toCharArray();
		
		String str = "P = ";
		
		int P=0;
		
		// we're doing an example loop for the first 32 bits.
		for(int i=0;i<4;++i)
		{
			// display the character.
			Cryptool.rsaEx[i].setText(String.valueOf(pt[i]));
			
			// get the binary representation of this character and add a space.
			str += getBinary(pt[i]) + " ";
			
			// whilst going through this loop, compute our P value.
			P |= ((pt[i] << ((3-i)*8)) & (0xff << ((3-i)*8)));
		}
		
		// here we are showing that the 4 8-bit portions of our integer is equal to P, where each 4
		// parts is the 8-bit representation of our character.
		str += " = " + P;
		
		Cryptool.rsaEq.setText(str);
		
		// here is where the encryption is being done. we take our P value,
		// and take it to the e power and mod it by n.
		C = BigInteger.valueOf((long)P).modPow(e, n);
		
		// here we are showing the formula being used, and the numbers being created.
		str = "C = P^e mod n = " + P + " ^ e mod n = " + C;
		
		// show the user the C equation.
		Cryptool.rsaEqC[0].setText(str);
		
		// grab C as an integer value, because now we are going to use the reverse process
		// and decrypt it.
		c = C.intValue();
		
		str = "    = ";
		
		
		for(int i=0;i<4;++i)
		{
			// grab the 4 8-bit portions of the example ciphertext value.
			str += getBinary(((char)((c >> ((3-i)*8))&0xff))) + " ";
		}
		
		// show the user this 32 binary string.
		Cryptool.rsaEqC[1].setText(str);
		
		str = "    =";
		
		Cryptool.rsaEqC[2].setText(str);
		
		// now we want to show the user that the P value can be retrieved again
		// by taking C and taking it to the d power, and modding it by n.
		str = "P = C^d mod n = " + C + " ^ d mod n = " + C.modPow(d, n);
		
		// show user this equation.
		Cryptool.rsaEqC[3].setText(str);
		
		// show the user the character representations of the example ciphertext (these will be weird characters)
		for(int i=0;i<4;++i)
		{
			Cryptool.rsaExC[i].setText(String.valueOf((char)((c >> ((3-i)*8))&0xff)));
		}
		
		String ct = "";
		
		// now do the full encryption for the whole plaintext.
		// do it in blocks of 4 characters.
		for(int i=0;i<pt.length;i+=4)
		{
			// grab the 4 char block.
			char block[] = String.valueOf(pt).substring(i, i+4).toCharArray();
			
			// doFullRSAEncrypt returns a string, which is the ciphertext representation of the plaintext. add it to our ciphertext.
			ct += doFullRSAEncrypt(block, e, n);
		}
		
		// show the user the whole ciphertext.
		Cryptool.rsaCipherText.setText(ct);
	}
	
	// this function encrypts the entire plaintext and produces the ciphertext.
	public static String doFullRSAEncrypt(char pt[], BigInteger e, BigInteger n)
	{
		int P=0;
		
		// calculate our P value, given the 4 characters. start with the first
		// and place it in the lowermost 8 bits. then, the next goes into the
		// next 8 bits, etc.
		for(int i=0;i<4;++i)		
			P |= ((pt[i] << ((3-i)*8)) & (0xff << ((3-i)*8)));
		
		// encrypt our plaintext. take P to the e power, and mod it by n.
		int C = BigInteger.valueOf((long)P).modPow(e, n).intValue();
		
		// now we want to generate our ciphertext.
		char ct[] = new char[4];
		
		// do the reverse of the P value, grab 8 bits at a time and cast to a character.
		for(int i=0;i<4;++i)
			ct[i] = ((char)((C >> ((3-i)*8))&0xff));
		
		// return ciphertext.
		return String.valueOf(ct);
	}
	
	// this function is simple in that it calculates n.
	// n = pq
	public static BigInteger getRSAN()
	{
		BigInteger p = new BigInteger(Cryptool.rsaP.getText());
		BigInteger q = new BigInteger(Cryptool.rsaQ.getText());
		
		return p.multiply(q);
	}
	
	// this function helps generate d, the decryption key.
	public static void doRSADGen()
	{
		BigInteger d;
		
		// if P or Q haven't been set yet, don't do anything.
		if(Cryptool.rsaP.getText().length() == 0 || Cryptool.rsaQ.getText().length() == 0)
			return;
		
		// if the user has set a value for d, we will want to grab the next
		// available number that meets our conditions.
		if(Cryptool.rsaD.getText().length() != 0)
			d = new BigInteger(Cryptool.rsaD.getText());
		// otherwise, start with d.
		else
			d = new BigInteger("3");
		
		BigInteger p = new BigInteger(Cryptool.rsaP.getText());
		BigInteger q = new BigInteger(Cryptool.rsaQ.getText());
		
		p = p.subtract(BigInteger.ONE);
		q = q.subtract(BigInteger.ONE);
		
		// calculate n, which is (p - 1)(q - 1).
		BigInteger n = p.multiply(q);
		
		// we want to select a d that is not only a prime, but is also
		// coprime with n. we will continually add one to d until 
		// both conditions are met.
		while(d.gcd(n).intValue() != 1 || !d.isProbablePrime(99))
		{
			d = d.add(BigInteger.ONE);
		}
		
		// show the user what d is.
		Cryptool.rsaD.setText(d.toString());
		
		BigInteger mod = p.multiply(q);
		
		// calculate e, which is the inverse mod of (p - 1)(q - 1).
		BigInteger e = d.modInverse(mod);
		
		// show user what e is.
		Cryptool.rsaELabel.setText("e: " + e);
	}
	
	public static void doRSAGen(Object o)
	{
		// see if this event is for P or Q.
		JTextField t = (o == Cryptool.rsaPGen ? Cryptool.rsaP : Cryptool.rsaQ);
		
		String num = t.getText();
	
		// find the closest prime to what the user has entered for either P or Q (the check is done above)
		BigInteger n  = getNextPrime(num);
		
		// set the prime number to either P or Q's text box.
		t.setText(n.toString());
		
		// check to see if this now means both P and Q have something entered. if so,
		// then calculate n automatically.
		if(Cryptool.rsaP.getText().length() != 0 && Cryptool.rsaQ.getText().length() != 0)
		{
			BigInteger p = new BigInteger(Cryptool.rsaP.getText());
			BigInteger q = new BigInteger(Cryptool.rsaQ.getText());
			n = p.multiply(q);
			
			num = "n: " + n + " = " + p + " * " + q;
			
			Cryptool.rsaNLabel.setText(num);
		}
	}
	
	// this function continually adds one to a number until it finds that it is a prime.
	public static BigInteger getNextPrime(String ans) {
		
		BigInteger test = new BigInteger(ans);
		while (!test.isProbablePrime(99))
			test = test.add(BigInteger.ONE);
		return test;		
	}
}
