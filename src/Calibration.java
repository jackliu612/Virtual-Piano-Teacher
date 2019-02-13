import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;

public class Calibration {

	static final int TIME = 1;
	static final int DYNM = 5;

	public static void main(String args[]) throws FileNotFoundException {
		Scanner infile = new Scanner(new File(args[0] + ".txt"));
		System.setOut(new PrintStream(new File("cal.txt")));

		/*************************************************************************
		 * Tempo
		 *************************************************************************/
		// Calculate sum
		int sum = 0;
		int t1 = Integer.parseInt(infile.nextLine().split("[\t ,]{1,}")[TIME]);
		int temp = 0;
		for (int x = 0; x < 9; x++) {
			temp = Integer.parseInt(infile.nextLine().split("[\t ,]{1,}")[TIME]);
			sum += temp - t1;
			t1 = temp;
		}
		// Average and round to the nearest 5
		System.out.println((sum / 9.0) - (sum / 9.0) % 5);

		/*************************************************************************
		 * Dynamics
		 *************************************************************************/
		// Similar process to above for tempo
		// calculate averages of different dynamic levels.
		// Repeats until there are no more lines in the input file
		while (infile.hasNextLine()) {
			sum = 0;
			for (int x = 0; x < 5; x++) {
				sum += Integer.parseInt(infile.nextLine().split("[\t ,]{1,}")[DYNM]);
			}
			System.out.println(Math.round((sum / 10.0)));
		}

		infile.close();
	}
}
