/* James Cobb Summer 2007
   Professor Arup Guha
   Written in Java
   UCF Independent Study
   1028 lines
*/

/* Matt Schaller
 * Revised/Added to James's independent study
 * Work done: Split into separate files, 
              Added pages for: Vigenere, Playfair, RSA and LFSR.
              Edited Substitution Analysis: Changed n-gram computation
              Moved Index of Coincidence to Vigenere page.
              
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;
import java.math.*;


public class Cryptool extends JFrame implements ActionListener, MouseListener {
	
	private		JTabbedPane tabbedPane;
	private		JPanel		panel1;
	private		JPanel		panel2;
	private		JPanel		panel3;
	private		JPanel		panel4;
	private		JPanel		panel5; // playfair
	private		JPanel		panel6; // lfsr
	private		JPanel		panel7; // rsa
	
	/************************** BEGINNGING OF GLOBAL VARIABLS FOR VIGENERE ********************/
	/* Creates all the global variables that can be accessed by any functions 
	 * using them throughout the Vigenere tab
	 */
	
	static int MAX_GRAPHS = 13;
	
	static String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"
			,"P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	/****** Ciphertext Area **********/
	static JLabel ngramsTextLabel = new JLabel("Enter Text:");
	
	static JTextArea ngramsText = new JTextArea(10,20);
	{
		ngramsText.setEditable(true);
		ngramsText.setWrapStyleWord(true);
		ngramsText.setLineWrap(true);
	}
	static JScrollPane ngramsTextScroll = new JScrollPane(ngramsText,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
	static JLabel vigenereTextLabel = new JLabel("Enter Text:");
	
	static JTextArea vigenereText = new JTextArea(10,20);
	{
		vigenereText.setEditable(true);
		vigenereText.setWrapStyleWord(true);
		vigenereText.setLineWrap(true);
	}
	static JScrollPane vigenereTextScroll = new JScrollPane(vigenereText,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	/******* Frequency Section of Vigenere *************/
	static JLabel frequencyLabel = new JLabel("Frequency of Letters");
	static JLabel letters[]= new JLabel[26];
	static JTextArea frequencies[] = new JTextArea[26];
	
	//block of code to set up text boxes to display all the frequencies
	{
		//first row
		for(int iterator1=0;iterator1<9;iterator1++){
			letters[iterator1] = new JLabel(alphabet[iterator1]);
			letters[iterator1].setBounds (40+(iterator1*60),201,25,20);
			frequencies[iterator1] = new JTextArea();
			frequencies[iterator1].setBounds(50+(iterator1*60), 202, 35, 15);
			frequencies[iterator1].setEditable(false);
		}
	
		//second row
		for(int iterator2=0;iterator2<9;iterator2++){
			letters[iterator2+9] = new JLabel(alphabet[iterator2+9]);
			letters[iterator2+9].setBounds (40+(iterator2*60),221,25,20);
			frequencies[iterator2+9] = new JTextArea();
			frequencies[iterator2+9].setBounds(50+(iterator2*60), 222, 35, 15);
			frequencies[iterator2+9].setEditable(false);
		}
	
		//third row
		for(int iterator3=0;iterator3<8;iterator3++){
			letters[iterator3+18] = new JLabel(alphabet[iterator3+18]);
			letters[iterator3+18].setBounds (40+(iterator3*60),241,25,20);
			frequencies[iterator3+18] = new JTextArea();
			frequencies[iterator3+18].setBounds(50+(iterator3*60), 243, 35, 15);
			frequencies[iterator3+18].setEditable(false);
		}
	
	}
	static JButton freqButton = new JButton("Compute");	
	
	/******** Repeated NGrams section of Vigenere **************/
	static JLabel repeatedNGramsLabel = new JLabel("Repeated NGrams");
	static JTextArea nGramsText = new JTextArea(10,20);
	{
		nGramsText.setEditable(false);
		nGramsText.setLineWrap(true);
		nGramsText.setWrapStyleWord(true);
	}
	//makes the ngrams scrollable
	static JScrollPane nGramsScroll = new JScrollPane(nGramsText,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	static JButton nGramsButton = new JButton("Compute");
	
	/********  Substitution section of Vigenere **************/
	static JLabel guessLabel = new JLabel("Your Guess");
	static JTextArea alphaBoxes[] = new JTextArea[26];
	static JTextArea guessBoxes[] = new JTextArea[26];
	{
		//sets up boxes so user knows which letter they are substituting
		for(int iterator = 0; iterator<26 ;iterator++){
			alphaBoxes[iterator] = new JTextArea(alphabet[iterator],1,1);
			alphaBoxes[iterator].setBounds(40+(iterator*15), 440, 10, 15);
			alphaBoxes[iterator].setEditable(false);
			alphaBoxes[iterator].setLineWrap(false);
		}
		
		//sets up boxes for input from the user as to what they want to substitute the corresponding
		//box with
		for(int iterator = 0; iterator<26 ;iterator++){
			guessBoxes[iterator] = new JTextArea("-",1,1);
			guessBoxes[iterator].setBounds(40+(iterator*15), 460, 10, 15);
			guessBoxes[iterator].setLineWrap(false);
		}
	}
	//sets up area for deciphered text from the substitutions made by user
	static JTextArea decipheredText = new JTextArea(10,20);
	{	
		decipheredText.setEditable(false);
		decipheredText.setLineWrap(true);
		decipheredText.setWrapStyleWord(true);
	}
	static JScrollPane decipheredScroll = new JScrollPane(decipheredText,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	static JButton decipherButton = new JButton("Decipher");
	
	/*** Frequency Graph section of Vigenere ****/
	static JLabel numBinsLabel = new JLabel("Bins:");
	static JTextField numBins = new JTextField(1);
	
	static JLabel startAtLabel[] = new JLabel[MAX_GRAPHS];
	{
		for(int i=0;i<MAX_GRAPHS;++i)
			startAtLabel[i] = new JLabel("Start at:");
	}
	
	static JTextField startAt[] = new JTextField[MAX_GRAPHS];
	
	{
		for(int i=0;i<MAX_GRAPHS;++i)
			startAt[i] = new JTextField("A", 1);
	}
	
	static JButton startAtButton[] = new JButton[MAX_GRAPHS];
	
	{
		for(int i=0;i<MAX_GRAPHS;++i)
			startAtButton[i] = new JButton("Update");
	}
	
	static JLabel engFrequencyLabel = new JLabel("Letter Frequency of the English Language");
	static JGraph engFrequencyGraph[] = new JGraph[2];
	{
		for(int i=0;i<2;++i)
			engFrequencyGraph[i] = new JGraph();
	}
	
	static JLabel vigFrequencyLabels[] = new JLabel[MAX_GRAPHS];
	static JGraph vigFrequencyGraphs[] = new JGraph[MAX_GRAPHS];
	static JButton vigFreqButton = new JButton("Compute");
	
	{
		for(int i=0;i<MAX_GRAPHS;++i)
		{
			vigFrequencyLabels[i] = new JLabel("Letter Frequency Graph - Bin " + (i+1));
			vigFrequencyGraphs[i] = new JGraph();
		}
	}

	/********** Index of Coincidence section of Vigenere *************/
	
	static JLabel indexCoincidenceLabel = new JLabel("Index of Coincidence");
	static JTextArea indexCoincidenceText = new JTextArea(10,20);
	static JButton findButton = new JButton("Find");
	static JTextArea indexCoincidence2Text = new JTextArea(10,10);
	{
		indexCoincidence2Text.setEditable(true);
		indexCoincidence2Text.setWrapStyleWord(true);
		indexCoincidence2Text.setLineWrap(true);
	}
	static JScrollPane indexCoincidence2TextScroll = new JScrollPane(indexCoincidence2Text,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	/*************  END OF GLOBAL VARIBALS FOR VIGENERE ****************************************/
	
	/*************  BEGINNING OF GLOBAL VARIABLS FOR QUANTUM **********************************/
	
	static JLabel aliceMessageLabel = new JLabel("AM = ALICE'S MESSAGE");
	static JLabel aliceSchemeLabel = new JLabel("AS = ALICE'S SCHEME");
	static JLabel alicePolMessageLabel = new JLabel ("APM = ALICE'S POLARIZED MESSAGE");
	static JLabel eveSchemeLabel = new JLabel("ES = EVE'S SCHEME");
	static JLabel bobSchemeLabel = new JLabel("BS = BOB'S SCHEME");
	static JLabel bobPolMessageLabel = new JLabel ("BPM = BOB'S POLARIZED MESSAGE");
	static JLabel bobMessageLabel = new JLabel("BM = BOB'S MESSAGE");
	
	static JLabel plusLabel = new JLabel(" + SCHEME ");
	static JLabel onePLabel =  new JLabel(" '|' = 1 ");
	static JLabel zeroPLabel =  new JLabel(" '--' = 0 ");
	static JLabel xLabel =  new JLabel(" x SCHEME ");
	static JLabel oneXLabel =  new JLabel(" '/ ' = 1 ");
	static JLabel zeroXLabel =  new JLabel(" '\\' = 0 ");

	static JLabel simIndexNumbersLabels[] = new JLabel[25];
	static JLabel tbLabel = new JLabel("TESTING BITS             =  ");
	static JLabel ibLabel = new JLabel("INCORRECT BITS       =  ");
	static JLabel igLabel = new JLabel(" INCORRECT GUESS   =  ");	
	
	static JTextArea clearBox = new JTextArea(10,20);
	static JTextArea yellowBox = new JTextArea(10,20);
	static JTextArea redBox = new JTextArea(10,20);
	
	static JLabel simIndexLabel = new JLabel("Index");
	static JLabel AMLabel = new JLabel("[AM]");
	static JLabel ASLabel = new JLabel("[AS]");
	static JLabel APMLabel = new JLabel("[APM]");
	static JLabel ESLabel = new JLabel("[ES]");
	static JLabel BSLabel = new JLabel("[BS]");
	static JLabel BPMLabel = new JLabel("[BPM]");
	static JLabel BMLabel = new JLabel("[BM]");
	
	static JTextArea aliceMessageBoxes[] = new JTextArea[25];
	static JTextArea aliceSchemeBoxes[] = new JTextArea[25];
	static JTextArea alicePolMessageBoxes[] = new JTextArea[25];
	static JTextArea eveSchemeBoxes[] = new JTextArea[25];
	static JTextArea bobSchemeBoxes[] = new JTextArea[25];
	static JTextArea bobPolMessageBoxes[] = new JTextArea[25];
	static JTextArea bobMessageBoxes[] = new JTextArea[25];
	
	static JButton aebSimButton = new JButton("Simulate");
	
	static JLabel correctGuessLabel = new JLabel("CORRECT GUESSES");
	static JLabel correctGuessIndexLabel = new JLabel ("Index");
	static JLabel correctGuessAMessageLabel = new JLabel("Alice's Message");
	static JLabel correctGuessBMessageLabel = new JLabel("Bob's Message");
	static JTextArea guessIndexNumberLabels[] = new JTextArea[20];
	static JTextArea correctGuessAMessageBoxes[] = new JTextArea[20];
	static JTextArea correctGuessBMessageBoxes[] = new JTextArea[20];
		
	//block of code to set up text boxes to display all the frequencies
	{
		//index row
		for(int iterator1 =0;iterator1<25;iterator1++){
			simIndexNumbersLabels[iterator1] = new JLabel(""+iterator1);
			simIndexNumbersLabels[iterator1].setBounds (40+(iterator1*22),141,18,20);
		}
		//first row
		for(int iterator1 =0;iterator1<25;iterator1++){
			aliceMessageBoxes[iterator1] = new JTextArea("");
			aliceMessageBoxes[iterator1].setBounds (40+(iterator1*22),171,18,20);
			aliceMessageBoxes[iterator1].setEditable(false);
		}
	
		//second row
		for(int iterator2=0;iterator2<25;iterator2++){
			aliceSchemeBoxes[iterator2] = new JTextArea();
			aliceSchemeBoxes[iterator2].setBounds (40+(iterator2*22),201,18,20);
			aliceSchemeBoxes[iterator2].setEditable(false);
		}
		//third row
		for(int iterator2=0;iterator2<25;iterator2++){
			alicePolMessageBoxes[iterator2] = new JTextArea();
			alicePolMessageBoxes[iterator2].setBounds (40+(iterator2*22),231,18,20);
			alicePolMessageBoxes[iterator2].setEditable(false);
		}
		//fourth row
		for(int iterator3=0;iterator3<25;iterator3++){
			eveSchemeBoxes[iterator3] = new JTextArea();
			eveSchemeBoxes[iterator3].setBounds (40+(iterator3*22),261,18,20);
			eveSchemeBoxes[iterator3].setEditable(false);
		}
		
		//fifth row
		for(int iterator4=0;iterator4<25;iterator4++){
			bobSchemeBoxes[iterator4] = new JTextArea();
			bobSchemeBoxes[iterator4].setBounds (40+(iterator4*22),291,18,20);
			bobSchemeBoxes[iterator4].setEditable(false);
		}
		//sixth row
		for(int iterator4=0;iterator4<25;iterator4++){
			bobPolMessageBoxes[iterator4] = new JTextArea();
			bobPolMessageBoxes[iterator4].setBounds (40+(iterator4*22),321,18,20);
			bobPolMessageBoxes[iterator4].setEditable(false);
		}
		
		//seventh row
		for(int iterator4=0;iterator4<25;iterator4++){
			bobMessageBoxes[iterator4] = new JTextArea();
			bobMessageBoxes[iterator4].setBounds (40+(iterator4*22),351,18,20);
			bobMessageBoxes[iterator4].setEditable(false);
		}
		
		//index compare row
		for(int iterator4=0;iterator4<20;iterator4++){
			guessIndexNumberLabels[iterator4] = new JTextArea();
			guessIndexNumberLabels[iterator4].setBounds (150+(iterator4*22),440,18,20);
			guessIndexNumberLabels[iterator4].setEditable(false);
		}
		
		//alice compare row
		for(int iterator4=0;iterator4<20;iterator4++){
			correctGuessAMessageBoxes[iterator4] = new JTextArea();
			correctGuessAMessageBoxes[iterator4].setBounds (150+(iterator4*22),470,18,20);
			correctGuessAMessageBoxes[iterator4].setEditable(false);
		}
		
		//bob compare row
		for(int iterator4=0;iterator4<20;iterator4++){
			correctGuessBMessageBoxes[iterator4] = new JTextArea();
			correctGuessBMessageBoxes[iterator4].setBounds (150+(iterator4*22),500,18,20);
			correctGuessBMessageBoxes[iterator4].setEditable(false);
		}
	}
	
	/*************  END OF GLOBAL VARIABLES FOR QUANTUM ****************************************/
	
	/************* START OF VARIABLES FOR PLAYFAIR ************************/
	static JLabel keywordLabel = new JLabel("Enter keyword to generate matrix:");
	static JMatrix pfMatrix = new JMatrix();
	static JTextField pfKeyword = new JTextField();
	static JButton pfGenerate = new JButton();
	
	static JLabel pfPlainTextLabel = new JLabel("Enter plaintext:");
	static JTextArea pfPlainText = new JTextArea();
	static JLabel pfCipherTextLabel = new JLabel("Ciphertext:");
	static JTextArea pfCipherText = new JTextArea();
	static JButton pfEncryptButton = new JButton("Encrypt");
	
	static JTextField pfCustMatrix[] = new JTextField[25];
	{
		for(int i=0;i<25;++i)
			pfCustMatrix[i] = new JTextField();
	}
	
	static JButton custDecryptButton = new JButton("Decrypt");
	
	static JLabel custCipherTextLabel = new JLabel("Enter ciphertext:");
	static JLabel custMatrixLabel = new JLabel("Custom Matrix");
	static JLabel custPlainTextLabel = new JLabel("Plaintext:");
	
	static JTextArea custCipherText = new JTextArea(5, 30);
	static JTextArea custPlainText = new JTextArea(5, 30);
	
	static JButton custCopyButton = new JButton("Copy");
	
	/******************** END OF VARIABLES FOR PLAYFAIR **************************/
	
	/******************** BEGINNING OF VARIABLES FOR LFSR ************************/
	
	static JLabel lfsrLabel = new JLabel("Line Feedback Shift Register");
	
	static JTextField lfsrBits[] = new JTextField[8];
	{
		for(int i=0;i<8;++i)
			lfsrBits[i] = new JTextField();
	}
	
	static JLabel lfsrBitLabels[] = new JLabel[8];
	{
		for(int i=0;i<8;++i)
		{
			lfsrBitLabels[i] = new JLabel(String.valueOf(i));
			lfsrBitLabels[i].addMouseListener(this);
		}
	}
	
	static JTextArea lfsrRandomBits = new JTextArea(5, 30);
	
	static JButton lfsrStepButton = new JButton("Step");
	
	static JLabel lfsrXorLabel = new JLabel("XOR:");
	static JTextField lfsrXor = new JTextField();
	
	static JXOR xorDrawing = new JXOR();
	
	/******************** END OF VARIABLES FOR LFSR **************************/
	
	/******************** BEGINNING OF VARIABLES FOR RSA ************************/
	
	static JLabel rsaPLabel = new JLabel("p:");
	static JLabel rsaQLabel = new JLabel("q:");
	static JLabel rsaNLabel = new JLabel("n:");
	static JLabel rsaDLabel = new JLabel("d:");
	static JLabel rsaELabel = new JLabel("e:");
	
	static JTextField rsaP = new JTextField();
	static JTextField rsaQ = new JTextField();
	static JTextField rsaD = new JTextField();
	
	static JButton rsaPGen = new JButton("Generate");
	static JButton rsaQGen = new JButton("Generate");
	static JButton rsaDGen = new JButton("Generate");
	
	static JLabel rsaEx[] = new JLabel[4];
	{
		for(int i=0;i<4;++i)
		{
			rsaEx[i] = new JLabel();
		}
	}
	
	static JLabel rsaEq = new JLabel();
	
	static JLabel rsaExC[] = new JLabel[4];
	{
		for(int i=0;i<4;++i)
		{
			rsaExC[i] = new JLabel();
		}
	}
	
	static JLabel rsaEqC[] = new JLabel[4];
	{
		for(int i=0;i<4;++i)
		{
			rsaEqC[i] = new JLabel();
		}
	}
	
	static JLabel rsaPlainTextLabel = new JLabel("Plaintext:");
	static JLabel rsaCipherTextLabel = new JLabel("Ciphertext:");
	
	static JTextArea rsaPlainText = new JTextArea();
	static JTextArea rsaCipherText = new JTextArea();
	
	static JButton rsaEncrypt = new JButton("Encrypt");
	
	/************************* 
	*CONSTRUCTOR FOR CRYPTOOL*
	*************************/
	public Cryptool()
	{
		
		setTitle( "Cryptography Tool" );
		setSize( 630, 800 );
		setBackground( Color.gray );

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );
	
		/************************** BEGINNING OF VIGENERE CONSTRUCTION *******************************/
		/*Here is where all the buttons, text-boxes and labels bound's are set and 
		 * each are added to the Vigenere panel in their corresponding positions 
		 */
		
		panel1 = new JPanel();
		panel1.setLayout( null );	
		
		/******* Adding cipher-text area to Vigenere Panel and sets bounds *********/
		
		ngramsTextLabel.setBounds( 40, 35, 150, 10 );
		panel1.add( ngramsTextLabel );
		ngramsTextScroll.setBounds( 40, 50, 530, 100 );
		panel1.add( ngramsTextScroll );	
		
		/******* Adding frequency section to Vignere Panel and sets bounds ********/
		
		frequencyLabel.setBounds( 40, 155, 150, 60 );
		panel1.add(frequencyLabel);
		for( int iterator1 = 0;iterator1<9;iterator1++){
			panel1.add(letters[iterator1]);
			panel1.add(frequencies[iterator1]);
		}
		for(int iterator2=0;iterator2<9;iterator2++){
			panel1.add(letters[iterator2+9]);
			panel1.add(frequencies[iterator2+9]);
		}
		for(int iterator3=0;iterator3<8;iterator3++){
			panel1.add(letters[iterator3+18]);
			panel1.add(frequencies[iterator3+18]);
		}
		//changes letter M and W to position correctly
		letters[22].setBounds( 275, 241, 20, 20 );
		letters[12].setBounds( 218, 220, 20, 20 );
		panel1.add(letters[23]);
		panel1.add(letters[23]);
		freqButton.setBounds(215, 265, 153, 20);
		freqButton.addActionListener(this);
		panel1.add(freqButton);
			
		/******* Adding repeated NGrams section to Vigenere Panel and sets bounds ********/
		
		repeatedNGramsLabel.setBounds( 40, 290, 500, 50 );
		panel1.add(repeatedNGramsLabel);
		nGramsScroll.setBounds(40, 328, 530, 50);
		panel1.add(nGramsScroll);
		nGramsButton.setBounds(215, 383, 153, 20);
		nGramsButton.addActionListener(this);
		panel1.add(nGramsButton);
			
		/******* Adding subsitution section to Vigenere Panle and sets bounds *******/
		
		for(int iterator4=0;iterator4<26;iterator4++){
			panel1.add(alphaBoxes[iterator4]);
		}
		for(int iterator5=0;iterator5<26;iterator5++){
			panel1.add(guessBoxes[iterator5]);
		}
		guessLabel.setBounds( 40, 400, 500, 50 );
		panel1.add(guessLabel);
		decipheredScroll.setBounds(40, 480, 530, 100);
		panel1.add(decipheredScroll);
		decipherButton.setBounds(215, 585, 153, 20);
		decipherButton.addActionListener(this);
		panel1.add(decipherButton);
	
	/*************  END OF VIGENERE CONSTRUCTION ***************************************/
	
	/************* BEGINNING OF NEW VIGENERE CONSTRUCTION ******************************/
	
	panel3 = new JPanel();
	panel3.setLayout( null );
	
	vigenereTextLabel.setBounds( 40, 35, 150, 10 );
	panel3.add( vigenereTextLabel );
	vigenereTextScroll.setBounds( 40, 50, 530, 100 );
	panel3.add( vigenereTextScroll );
	
	/******* Adding index of coincidence section to Vigenere Panle and sets bounds *******/
	indexCoincidenceLabel.setBounds( 40, 660, 500, 50 );
	panel3.add(indexCoincidenceLabel);
	
	indexCoincidenceText.setEditable(true);
	indexCoincidenceText.setLineWrap(false);
	indexCoincidenceText.setBounds(165,675,30,18);
	panel3.add(indexCoincidenceText);

	findButton.setBounds(40, 700, 60, 20);
	findButton.addActionListener(this);
	panel3.add(findButton);
	
	indexCoincidence2TextScroll.setBounds(108,700,462,20);
	panel3.add(indexCoincidence2TextScroll);
	
	engFrequencyLabel.setBounds(40, 130, 400, 60);
	panel3.add(engFrequencyLabel);
	
	engFrequencyGraph[0].setBounds(40, 170, 530, 50);
	panel3.add(engFrequencyGraph[0]);
	
	for(int i=0;i<5;++i)
	{
		vigFrequencyLabels[i].setBounds(40, 230 + (i*80), 200, 20);
		panel3.add(vigFrequencyLabels[i]);
		
		vigFrequencyGraphs[i].setBounds(40, 255 + (i*80), 530, 45);
		panel3.add(vigFrequencyGraphs[i]);
		
		startAtLabel[i].setBounds(250, 230 + (i*80), 45, 20);
		panel3.add(startAtLabel[i]);
		
		startAt[i].setBounds(305, 230 + (i*80), 20, 20);
		panel3.add(startAt[i]);
		
		startAtButton[i].setBounds(340, 230 + (i*80), 75, 20);
		startAtButton[i].addActionListener(this);
		panel3.add(startAtButton[i]);
	}
	
	numBinsLabel.setBounds(140, 630, 40, 20);
	panel3.add(numBinsLabel);
	
	numBins.setBounds(175, 630, 20, 20);
	numBins.setText("1");
	panel3.add(numBins);
	
	vigFreqButton.setBounds(215, 630, 153, 20);
	vigFreqButton.addActionListener(this);
	panel3.add(vigFreqButton);
	
	Vigenere.doEngFreq();
	
	/*** part 2 of vigenere ****/
	
	panel4 = new JPanel();
	panel4.setLayout(null);
	
	for(int i=5;i<MAX_GRAPHS;++i)
	{
		vigFrequencyLabels[i].setBounds(40, 100 + ((i-5)*80), 200, 20);
		panel4.add(vigFrequencyLabels[i]);
		
		vigFrequencyGraphs[i].setBounds(40, 125 + ((i-5)*80), 530, 45);
		panel4.add(vigFrequencyGraphs[i]);
		
		startAtLabel[i].setBounds(250, 100 + ((i-5)*80), 45, 20);
		panel4.add(startAtLabel[i]);
		
		startAt[i].setBounds(305, 100 + ((i-5)*80), 20, 20);
		panel4.add(startAt[i]);
		
		startAtButton[i].setBounds(340, 100 + ((i-5)*80), 75, 20);
		startAtButton[i].addActionListener(this);
		panel4.add(startAtButton[i]);
	}
	
	engFrequencyGraph[1].setBounds(40, 20, 530, 45);
	panel4.add(engFrequencyGraph[1]);
			
	/************* BEGINNING OF QUANTUM CONSTRUCTION ***********************************/
		panel2 = new JPanel();
		panel2.setLayout( null );
		
		aliceMessageLabel.setBounds(40,15,140,20);
		panel2.add(aliceMessageLabel);
		aliceSchemeLabel.setBounds(40,30,140,20);
		panel2.add(aliceSchemeLabel);
		alicePolMessageLabel.setBounds(40,45,240,20);
		panel2.add(alicePolMessageLabel);
		eveSchemeLabel.setBounds(40,60,140,20);
		panel2.add(eveSchemeLabel);
		bobSchemeLabel.setBounds(40,75,140,20);
		panel2.add(bobSchemeLabel);
		bobPolMessageLabel.setBounds(40,90,240,20);
		panel2.add(bobPolMessageLabel);
		bobMessageLabel.setBounds(40,105,140,20);
		panel2.add(bobMessageLabel);
		
		plusLabel.setBounds(285,15,100,20);
		panel2.add(plusLabel);
		onePLabel.setBounds(300,30,100,20);
		panel2.add(onePLabel);
		zeroPLabel.setBounds(300,45,100,20);
		panel2.add(zeroPLabel);
		
		xLabel.setBounds(285,65,100,20);
		panel2.add(xLabel);
		oneXLabel.setBounds(300,80,100,20);
		panel2.add(oneXLabel);
		zeroXLabel.setBounds(300,95,100,20);
		panel2.add(zeroXLabel);
		
	
		simIndexLabel.setBounds(5,140,40,20);
		panel2.add(simIndexLabel);
		AMLabel.setBounds(7,169,40,20);
		panel2.add(AMLabel);
		ASLabel.setBounds(7,199,40,20);
		panel2.add(ASLabel);
		APMLabel.setBounds(4,229,40,20);
		panel2.add(APMLabel);
		ESLabel.setBounds(7,259,40,20);
		panel2.add(ESLabel);
		BSLabel.setBounds(7,289,40,20);
		panel2.add(BSLabel);
		BPMLabel.setBounds(4,319,40,20);
		panel2.add(BPMLabel);
		BMLabel.setBounds(7,349,40,20);
		panel2.add(BMLabel);	
		
		tbLabel.setBounds(425,30,150,20);
		panel2.add(tbLabel);
		igLabel.setBounds(422,60,150,20);
		panel2.add(igLabel);
		ibLabel.setBounds(425,90,150,20);
		panel2.add(ibLabel);
		
		clearBox.setBounds(565,30,18,20);
		clearBox.setBorder(BorderFactory.createLineBorder(Color.blue,2));
		panel2.add(clearBox);
		yellowBox.setBounds(565, 60, 18, 20);
		yellowBox.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
		panel2.add(yellowBox);
		redBox.setBounds(565,90,18,20);
		redBox.setBorder(BorderFactory.createLineBorder(Color.red, 2));
		panel2.add(redBox);
				
		//sets borders around boxes
		for( int iterator8 = 0; iterator8<25;iterator8++){
			aliceMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			aliceSchemeBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			alicePolMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			eveSchemeBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			bobSchemeBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			bobPolMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			bobMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
		}
		
		//adds boxes to panel2
		for( int iterator8 = 0;iterator8<25;iterator8++){
			panel2.add(simIndexNumbersLabels[iterator8]);
			panel2.add(alicePolMessageBoxes[iterator8]);
			panel2.add(aliceMessageBoxes[iterator8]);
			panel2.add(aliceSchemeBoxes[iterator8]);
			panel2.add(eveSchemeBoxes[iterator8]);
			panel2.add(bobSchemeBoxes[iterator8]);
			panel2.add(bobPolMessageBoxes[iterator8]);
			panel2.add(bobMessageBoxes[iterator8]);
		}
		/*JLabel correctGuessLabel = new JLabel("CORRECT GUESSES");
		JLabel correctGuessIndexLabel = new JLabel ("Index");
		JLabel correctGuessAMessageLabel = new JLabel("Alice's Message");
		JLabel correctGuessBMessageLabel = new JLabel("Bob's Message");*/
		
		
		correctGuessLabel.setBounds(250,415,170,20);
		correctGuessIndexLabel.setBounds(50,440,120,20);
		correctGuessAMessageLabel.setBounds(50,470,180,20);
		correctGuessBMessageLabel.setBounds(50,500,180,20);
		panel2.add(correctGuessLabel);
		panel2.add(correctGuessIndexLabel);
		panel2.add(correctGuessAMessageLabel);
		panel2.add(correctGuessBMessageLabel);	
		
		for( int iterator8 = 0;iterator8<20;iterator8++){
			guessIndexNumberLabels[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			correctGuessAMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			correctGuessBMessageBoxes[iterator8].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			panel2.add(guessIndexNumberLabels[iterator8]);
			panel2.add(correctGuessAMessageBoxes[iterator8]);
			panel2.add(correctGuessBMessageBoxes[iterator8]);	                          
		}
		aebSimButton.setBounds(230,580,150,50);
		aebSimButton.addActionListener(this);
		panel2.add(aebSimButton);	
		
		/************* END OF QUANTUM CONSTRUCTION ****************************************/	
		
		/*********** BEGIN PLAYFAIR CONSTRUCTION **************/
		
		panel5 = new JPanel(null);
		panel5.setLayout(null);
		
		keywordLabel.setBounds(100, 20, 200, 20);
		panel5.add(keywordLabel);
		
		pfMatrix.setBounds(10, 20, 75, 100);
		panel5.add(pfMatrix);
		
		pfKeyword.setBounds(100, 40, 200, 20);
		panel5.add(pfKeyword);
		
		pfGenerate.setText("Generate Matrix");
		pfGenerate.setBounds(305, 40, 125, 20);
		pfGenerate.addActionListener(this);
		panel5.add(pfGenerate);
		
		pfPlainTextLabel.setBounds(100, 60, 200, 20);
		panel5.add(pfPlainTextLabel);
		
		pfPlainText.setBounds(100, 80, 400, 75);
		panel5.add(pfPlainText);
		
		pfCipherTextLabel.setBounds(100, 165, 200, 20);
		panel5.add(pfCipherTextLabel);
		
		pfCipherText.setBounds(100, 185, 400, 75);
		panel5.add(pfCipherText);
		
		pfEncryptButton.setBounds(250, 270, 100, 20);
		pfEncryptButton.addActionListener(this);
		panel5.add(pfEncryptButton);
		
		for(int i=0;i<25;++i)
		{
			pfCustMatrix[i].setBounds(10+((i%5)*20), 350+((i/5)*25), 15, 20);
			panel5.add(pfCustMatrix[i]);
		}
		
		custMatrixLabel.setBounds(15, 330, 200, 20);
		panel5.add(custMatrixLabel);
		
		custCipherTextLabel.setBounds(115, 330, 200, 20);
		panel5.add(custCipherTextLabel);
		
		custCipherText.setBounds(115, 350, 400, 100);
		custCipherText.setEditable(true);
		custCipherText.setLineWrap(true);
		panel5.add(custCipherText);
		
		custPlainTextLabel.setBounds(115, 470, 200, 20);
		panel5.add(custPlainTextLabel);
		
		custPlainText.setBounds(115, 490, 400, 100);
		custPlainText.setEditable(true);
		custPlainText.setLineWrap(true);
		panel5.add(custPlainText);
		
		custDecryptButton.setBounds(265, 595, 100, 20);
		custDecryptButton.addActionListener(this);
		panel5.add(custDecryptButton);
		
		custCopyButton.setBounds(20, 475, 75, 20);
		custCopyButton.addActionListener(this);
		panel5.add(custCopyButton);
		
		
		/*********** END PLAYFAIR CONSTRUCTION ***************/
		
		/*********** BEGIN LFSR CONSTRUCTION **************/
		
		panel6 = new JPanel(null);
		panel6.setLayout(null);
		
		lfsrLabel.setBounds(220, 20, 250, 20);
		panel6.add(lfsrLabel);
		
		for(int i=0;i<8;++i)
		{
			lfsrBitLabels[i].setBounds(213+(i*25), 50, 20, 20);
			panel6.add(lfsrBitLabels[i]);
		}
		
		for(int i=0;i<8;++i)
		{
			lfsrBits[i].setBounds(205+(i*25), 70, 20, 20);
			panel6.add(lfsrBits[i]);
		}
		
		xorDrawing.setBounds(160, 90, 230, 40);
		panel6.add(xorDrawing);
		
		lfsrStepButton.setBounds(265, 140, 75, 20);
		lfsrStepButton.addActionListener(this);
		panel6.add(lfsrStepButton);
		
		lfsrRandomBits.setBounds(125, 175, 350, 100);
		lfsrRandomBits.setEditable(true);
		lfsrRandomBits.setLineWrap(true);
		panel6.add(lfsrRandomBits);
		
		/*********** END LFSR CONSTRUCTION ***************/
		
		/*********** BEGIN RSA CONSTRUCTION **************/
		
		panel7 = new JPanel(null);
		panel7.setLayout(null);
		
		rsaPLabel.setBounds(20, 20, 20, 20);
		panel7.add(rsaPLabel);
		
		rsaQLabel.setBounds(20, 45, 20, 20);
		panel7.add(rsaQLabel);
		
		rsaNLabel.setBounds(20, 70, 600, 20);
		panel7.add(rsaNLabel);
		
		rsaDLabel.setBounds(20, 95, 20, 20);
		panel7.add(rsaDLabel);
		
		rsaELabel.setBounds(20, 120, 400, 20);
		panel7.add(rsaELabel);
		
		rsaP.setBounds(40, 20, 250, 20);
		rsaQ.setBounds(40, 45, 250, 20);
		rsaD.setBounds(40, 95, 250, 20);
		
		rsaPGen.setBounds(300, 20, 100, 20);
		rsaQGen.setBounds(300, 45, 100, 20);
		rsaDGen.setBounds(300, 95, 100, 20);
		
		rsaPGen.addActionListener(this);
		rsaQGen.addActionListener(this);
		rsaDGen.addActionListener(this);
		
		panel7.add(rsaP);
		panel7.add(rsaQ);
		panel7.add(rsaD);
		panel7.add(rsaPGen);
		panel7.add(rsaQGen);
		panel7.add(rsaDGen);
		
		rsaPlainTextLabel.setBounds(20, 140, 100, 20);
		panel7.add(rsaPlainTextLabel);
		
		rsaPlainText.setBounds(20, 160, 400, 100);
		rsaPlainText.setEditable(true);
		rsaPlainText.setLineWrap(true);
		panel7.add(rsaPlainText);
		
		rsaEncrypt.setBounds(20, 270, 100, 20);
		rsaEncrypt.addActionListener(this);
		panel7.add(rsaEncrypt);
		
		for(int i=0;i<4;++i)
		{
			rsaEx[i].setBounds(95+(i*60), 310, 20, 20);
			panel7.add(rsaEx[i]);
		}
		
		rsaEq.setBounds(50, 330, 400, 20);
		panel7.add(rsaEq);
		
		for(int i=0;i<4;++i)
		{
			rsaExC[i].setBounds(95+(i*60), 400, 20, 20);
			panel7.add(rsaExC[i]);
		}
		
		for(int i=0;i<4;++i)
		{
			rsaEqC[i].setBounds(50, 360+(i*20), (i!=2?400:50), 20);
			panel7.add(rsaEqC[i]);
		}
		
		rsaCipherTextLabel.setBounds(20, 440, 100, 20);
		panel7.add(rsaCipherTextLabel);
		
		rsaCipherText.setBounds(20, 460, 400, 100);
		rsaCipherText.setEditable(true);
		rsaCipherText.setLineWrap(true);
		panel7.add(rsaCipherText);
		
		
		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Substitution", panel1);
		tabbedPane.addTab("Vignere", panel3 );
		tabbedPane.addTab("Vignere 2", panel4);
		tabbedPane.addTab("Quantum Cryptography", panel2 );
		tabbedPane.addTab("Playfair", panel5);
		tabbedPane.addTab("LFSR", panel6);
		tabbedPane.addTab("RSA", panel7);
		topPanel.add( tabbedPane, BorderLayout.CENTER );	
	}
	
	/******************************* 
	*END OFCONSTRUCTOR FOR CRYPTOOL*
	*******************************/

	public void mouseReleased(MouseEvent evt)
	{
		
	}
	
	public void mouseClicked(MouseEvent evt)
	{
		Object source = evt.getSource();
		
		for(int i=0;i<8;++i)
		{
			// this is used to check to see if the user has selected a particular bit to be XOR'd in our LFSR demonstration.
			if(source == lfsrBitLabels[i])
			{
				xorDrawing.setBit(i);
			}
		}
	}
	
	public void mouseEntered(MouseEvent evt)
	{
		
	}
	
	public void mouseExited(MouseEvent evt)
	{
		
	}
	
	public void mousePressed(MouseEvent evt)
	{
		
	}
	
	/* Action performed listener used for all the buttons in the 
	 * cryptool application.  It listens for button clicks and then
	 * calls corresponding functions to carry out actions specified by 
	 * the user.
	 */
	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		if(source == freqButton){
			Substitution.doFreq();
		}
		else if(source == nGramsButton){
			Substitution.doNGrams();
		}
		else if(source == decipherButton){
			Substitution.doSubstitution();
		}
		else if(source == findButton){
			Vigenere.doCoincidence();
		}
		else if(source == aebSimButton){
			Quantum.doQuantumSim();
		}
		else if(source == vigFreqButton){
			Vigenere.doVigFreq();
		}
		else if(source == pfGenerate){
			Playfair.doPfGenerate();
		}
		else if(source == pfEncryptButton){
			Playfair.doPfEncrypt();
		}
		else if(source == custDecryptButton){
			Playfair.doPfDecrypt();
		}
		else if(source == custCopyButton){
			Playfair.doPfCopy();
		}
		else if(source == lfsrStepButton){
			LFSR.doLfsrStep();
		}
		else if(source == rsaPGen || source == rsaQGen){
			RSA.doRSAGen(source);
		}
		else if(source == rsaDGen){
			RSA.doRSADGen();
		}
		else if(source == rsaEncrypt)
		{
			RSA.doRSAEncrypt();
		}
		else
		{
			for(int i=0; i<MAX_GRAPHS; ++i)
			{
				if(source == startAtButton[i])
				{
					char c = Character.toUpperCase(startAt[i].getText().charAt(0));
					int pos = (int) (c-'A');
					vigFrequencyGraphs[i].setPos(pos);
					vigFrequencyGraphs[i].repaint();
				}
			}
		}
	}	
	
    // Main method to get things started
	public static void main( String args[] )
	{
		// Create an instance of the test application
		Cryptool mainFrame	= new Cryptool();
		mainFrame.setVisible( true );
	}
}//end of class

