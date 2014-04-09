import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicolás A. Ortega
 * @copyright (C) Nicolás A. Ortega
 * @license GNU General Public License 3.0 (GPLv3)
 * @year 2014
 * 
 */
public class MMMCalc {
	private static boolean verbose = false;
	private static float[] numArray;
	private static float mean = 0;
	private static float median = 0;
	private static float mode = 0;
	private static float range = 0;
	private static float stdDev = 0;
	private static float varience = 0;

	public static void main(String[] args) {
		System.out.println("MMMCalc v0.2, Copyright (C) 2014 Nicolás A. Ortega\n" +
					"This program comes with ABSOLUTELY NO WARRANTY; for details use '-w'\n" +
					"This is free software, and you are welcome to redistribute it\n" +
					"under certain conditions; use '-c' for details.\n");
		if(args.length > 0) {
			if(args[0].equals("-h")) {
				System.out.println("Usage:\n" +
					"  MMMCalc [options] [variables]\n\n" +
					"Options:\n" +
					"  -v | -V -- Be verbose (show the work).\n" +
					"  -w -- Show warranty information.\n" +
					"  -c -- Show copyright information.\n" +
					"  -h -- Show this help information.\n");
			} else if(args[0].equals("-w")) {
				System.out.println("THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY\n" +
					"APPLICABLE LAW.  EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT\n" +
					"HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM 'AS IS' WITHOUT WARRANTY\n" +
					"OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO,\n" +
					"THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR\n" +
					"PURPOSE.  THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM\n" +
					"IS WITH YOU.  SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF\n" +
					"ALL NECESSARY SERVICING, REPAIR OR CORRECTION.\n");
			} else if(args[0].equals("-c")) {
				System.out.println("MMMCalc, a very basic statistics calculator.\n" +
					"Copyright (C) 2014 Nicolás A. Ortega\n\n" +
					"This program is free software: you can redistribute it and/or modify\n" +
					"it under the terms of the GNU General Public License as published by\n" +
					"the Free Software Foundation, either version 3 of the License, or\n" +
					"(at your option) any later version.\n\n" +
					"This program is distributed in the hope that it will be useful,\n" +
					"but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
					"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
					"GNU General Public License for more details.\n\n" +
					"You should have received a copy of the GNU General Public License\n" +
					"along with this program.  If not, see <www.gnu.org/licenses/>.");
			} else if(args[0].equals("-v") || args[0].equals("-V")) {
				verbose = true;
				numArray = new float[args.length - 1];

				for(int i = 0; i < numArray.length; i++) {
					numArray[i] = Float.parseFloat(args[i+1]);
				}
				sortArray();

				calcMean();
				calcMedian();
				calcMode();
				calcRange();
				calcStdDev();
				calcVarience();
			} else {
				numArray = new float[args.length];

				for(int i = 0; i < args.length; i++) {
					numArray[i] = Float.parseFloat(args[i]) - 0f;
				}
				sortArray();

				calcMean();
				calcMedian();
				calcMode();
				calcRange();
				calcStdDev();
				calcVarience();
			}
		} else {
			System.out.println("You did not mention any variables. Use the -h argument for help.");
		}
	}

	private static void sortArray() {
		int nL = numArray.length;
		float tmp = 0;
		for(int i = 0; i < nL; i++) {
			for(int j = (nL-1); j >= (i+1); j--) {
				if(numArray[j] < numArray[j-1]) {
					tmp = numArray[j];
					numArray[j] = numArray[j-1];
					numArray[j-1] = tmp;
				}
			}
		}
	}

	private static void calcMean() {
		float sum = 0;

		for(float i: numArray) {
			sum += i;
		}

		mean = sum / (float)numArray.length;

		System.out.println("Mean: " + mean);
	}

	private static void calcMedian() {
		int midVar = numArray.length / 2;

		median = numArray[midVar];

		System.out.println("Median: " + median);
	}

	private static void calcMode() {
		HashMap<Float, Float> fx = new HashMap<Float, Float>();

		for(float x: numArray) {
			Float f = fx.get(x);
			if(f == null) {
				fx.put(x, (float)1);
			} else {
				fx.put(x, f + 1);
			}
		}

		float modeFreq = 0;

		for(Map.Entry<Float, Float> entry: fx.entrySet()) {
			float freq = entry.getValue();
			if(freq > modeFreq) {
				modeFreq = freq;
				mode = entry.getKey();
			}
		}

		System.out.println("Mode: " + mode + " (frequency: " + modeFreq + ")");
	}

	private static void calcRange() {
		int l = numArray.length -1;
		range = numArray[l] - numArray[0];

		System.out.println("Range: " + range);

		if(verbose) {
			System.out.println(numArray[l] + " - " + numArray[0] + " = " + range + "\n");
		}
	}

	private static void calcStdDev() {
		float difSum = 0;
		for(int i = 0; i < numArray.length; i++) {
			difSum += numArray[i] - mean;
		}

		stdDev = difSum / (float)Math.sqrt((double)numArray.length);

		System.out.println("Standard Deviation: " + stdDev);
	}

	private static void calcVarience() {
		// NOTE: I'm doing it this way so I don't have to convert the variables to doubles and lose precision.
		varience = stdDev * stdDev;

		System.out.println("Varience: " + varience);
	}
}