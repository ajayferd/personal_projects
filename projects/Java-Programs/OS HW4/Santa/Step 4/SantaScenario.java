import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.Queue;

// Problem summary:
// Santa sleeps in his shop at the North Pole, can only be woken by
// All nine reindeer back from the beach in the south pacific || at least 3 elves having trouble with toys
// At least 3 elves must be in trouble for santa to help, all others must wait their turn
// Only the last reindeer back from the beach can awaken santa

public class SantaScenario {

	public Santa santa;
	public List<Elf> elves;
	public List<Reindeer> reindeers;
	
	public boolean isDecember;
	public int waitingElves = 0;
	public int MaxElvesWithSanta = 3;
	Semaphore sem = new Semaphore(MaxElvesWithSanta, true);
	
	public static void main(String args[]) {
		SantaScenario scenario = new SantaScenario();
		scenario.isDecember = false;

		// create the participants
		// Santa
		scenario.santa = new Santa(scenario);
		Thread th = new Thread(scenario.santa);
		th.start();
		
		// The elves: in this case: 10
		scenario.elves = new ArrayList<>();
		for(int i = 0; i != 10; i++) {
			Elf elf = new Elf(i+1, scenario);
			scenario.elves.add(elf);
			th = new Thread(elf);
			th.start();
		}

		// The reindeer: in this case: 9
		scenario.reindeers = new ArrayList<>();
		for(int i = 0; i != 9; i++) {
			Reindeer reindeer = new Reindeer(i+1, scenario);
			scenario.reindeers.add(reindeer);
			th = new Thread(reindeer);
			th.start();
		}

		// now, start the passing of time
		for(int day = 1; day < 500; day++)
		{
			// wait a day
			// if we get to day 370, make all the threads stop
			if (day == 370)
			{
				scenario.santa.stop();
				for(Reindeer r : scenario.reindeers)
				{
					r.stop();
				}
				
				for(Elf e : scenario.elves)
				{
					e.stop();
				}
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// turn on December
			if (day > (365 - 31)) {
				scenario.isDecember = true;
			}
			


			// print out the state:
			System.out.println("***********  Day " + day + " *************************");
			scenario.santa.report();

			for(Elf elf: scenario.elves) {
				elf.report();
			}
			for(Reindeer reindeer: scenario.reindeers) {
				reindeer.report();
			}
		}
	}
	
	
	
}
