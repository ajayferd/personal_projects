import java.util.Random;


public class Elf implements Runnable {

	enum ElfState {
		WORKING, TROUBLE, AT_SANTAS_DOOR
	};

	private ElfState state;
	/**
	 * The number associated with the Elf
	 */
	private int number;
	private Random rand = new Random();
	private SantaScenario scenario;
	private boolean TERMINATE;
	public static boolean ThreeTroubledElves = false;
	private int originalPermits;

	public Elf(int number, SantaScenario scenario) {
		this.number = number;
		this.scenario = scenario;
		this.state = ElfState.WORKING;
		TERMINATE = false;
	}

	public ElfState getState() {
		return state;
	}


	/**
	 * Santa might call this function to fix the trouble
	 * @param state
	 */
	public void setState(ElfState state) {
		this.state = state;
	}


	@Override
	public void run() {
		while (!TERMINATE) {
      // wait a day
  		try {
  			Thread.sleep(100);
  		} catch (InterruptedException e) {
  			e.printStackTrace();
  		}
			switch (state) {
			case WORKING: {
				// at each day, there is a 1% chance that an elf runs into
				// trouble.
				if (rand.nextDouble() < 0.01) {
					state = ElfState.TROUBLE;

					try{
						scenario.sem.acquire();
						scenario.waitingElves++;
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}


				// if any elves run into trouble they wait for santa
				break;
			}
			case TROUBLE:
			{
				if(scenario.waitingElves < 3)
				{

				}
				else
				{
					// if (numOfTroubledElves >= 3)
					state = (ElfState.AT_SANTAS_DOOR);
				}
				
				break;

			}
			case AT_SANTAS_DOOR:
			{
				if (scenario.santa.isSantaSleeping() && scenario.waitingElves == 3)
					scenario.santa.awakenSanta();
				
				break;

			}
			}
		}
	}

	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Elf " + number + " : " + state);
	}

	public void stop()
	{
		TERMINATE = true;
	}
}
