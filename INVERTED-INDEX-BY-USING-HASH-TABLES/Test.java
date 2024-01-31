
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	public static void searchWords(HashedDictionary hashedDictionary) {

		double[] allTimes = new double[1000];

		int count = 0;
		try {// file reading operations
			File myObj2 = new File("search.txt");// file path
			Scanner myReader2 = new Scanner(myObj2, "UTF-8");// first reader for add stack
			while (myReader2.hasNextLine()) {
				String nextLine = myReader2.nextLine();
				if (nextLine != "") {
					long startTime = System.nanoTime();
					hashedDictionary.search(nextLine);
					long endTime = System.nanoTime();
					long estimatedTime = endTime - startTime; // Geçen süreyi milisaniye cinsinden elde ediyoruz
					double seconds = (double) estimatedTime / 1000000000; // saniyeye çevirmek için 1000'e bölüyoruz.
					allTimes[count] = seconds;
					count++;

				}

			}
			myReader2.close();

		} catch (FileNotFoundException e) {// error catch
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		double sum = 0;
		double min = allTimes[1];
		double max = allTimes[1];

		for (int i = 0; i < allTimes.length; i++) {
			if (allTimes[i] >= max) {
				max = allTimes[i];
			}
			if (allTimes[i] <= max) {
				min = allTimes[i];
			}
			sum += allTimes[i];
		}

		double average = sum / 1000;

		System.out.println("min " + min + " max " + max + " average" + average);

	}

	public static void main(String args[]) throws IOException {

		boolean isLoadFactor50 = false;
		boolean isHashFunctionSSF = false;
		boolean isCollisionHandlingLP = false;
		Scanner input2 = new Scanner(System.in);
		int output;
		System.out.print("select max load factor press 1 for 0.5 press 2  for 0.8 :");
		output = input2.nextInt();
		System.out.println();
		if (output == 1) {
			isLoadFactor50 = true;
		}
		System.out.print("select hash function press 1 for SSF press 2  for PAF :");
		output = input2.nextInt();
		System.out.println();
		if (output == 1) {
			isHashFunctionSSF = true;
		}
		System.out.print("select Collision Handling press 1 for linear probing press 2 for double hashing :");
		output = input2.nextInt();
		System.out.println();
		if (output == 1) {
			isCollisionHandlingLP = true;
		}

		HashedDictionary hashedDictionary = new HashedDictionary<>(isLoadFactor50, isHashFunctionSSF,
				isCollisionHandlingLP);

		ArrayList<String> stopWords = new ArrayList<String>();
		stopWords.add("");
		try {// file reading operations
			File myObj2 = new File("stop_words_en.txt");// file path
			Scanner myReader2 = new Scanner(myObj2, "UTF-8");// first reader for add stack
			while (myReader2.hasNextLine()) {
				String nextLine = myReader2.nextLine();
				if (nextLine != "") {
					stopWords.add(nextLine);
				}

			}
			myReader2.close();

		} catch (FileNotFoundException e) {// error catch
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		String DELIMITERS = "[-+=" +

				" " + // space

				"\r\n " + // carriage return line fit

				"1234567890" + // numbers

				"’'\"" + // apostrophe

				"(){}<>\\[\\]" + // brackets

				":" + // colon

				"," + // comma

				"‒–—―" + // dashes

				"…" + // ellipsis

				"!" + // exclamation mark

				"." + // full stop/period

				"«»" + // guillemets

				"-‐" + // hyphen

				"?" + // question mark

				"‘’“”" + // quotation marks

				";" + // semicolon

				"/" + // slash/stroke

				"⁄" + // solidus

				"␠" + // space?

				"·" + // interpunct

				"&" + // ampersand

				"@" + // at sign

				"*" + // asterisk

				"\\" + // backslash

				"•" + // bullet

				"^" + // caret

				"¤¢$€£¥₩₪" + // currency

				"†‡" + // dagger

				"°" + // degree

				"¡" + // inverted exclamation point

				"¿" + // inverted question mark

				"¬" + // negation

				"#" + // number sign (hashtag)

				"№" + // numero sign ()

				"%‰‱" + // percent and related signs

				"¶" + // pilcrow

				"′" + // prime

				"§" + // section sign

				"~" + // tilde/swung dash

				"¨" + // umlaut/diaeresis

				"_" + // underscore/understrike

				"|¦" + // vertical/pipe/broken bar

				"⁂" + // asterism

				"☞" + // index/fist

				"∴" + // therefore sign

				"‽" + // interrobang

				"※" + // reference mark

				"]";

		long startTime = System.nanoTime();

		// Creating a File object for directory
		File directoryPath = new File("bbc");
		// List of all files and directories
		File filesList[] = directoryPath.listFiles();

		Scanner sc = null;

		for (int i = 0; i < filesList.length; i++) {
			File directoryPath2 = new File(filesList[i].toString());
			File filesList2[] = directoryPath2.listFiles();
			for (int j = 0; j < filesList2.length; j++) {

				// Instantiating the Scanner class

				sc = new Scanner(filesList2[j]);
				String input;
				StringBuffer sb = new StringBuffer();
				while (sc.hasNextLine()) {
					input = sc.nextLine();
					sb.append(input + " ");
				}
				if (filesList2[j].getName().toString().equals("003.txt")) {
					int a = 5;
				}
				String[] splitted = sb.toString().split(DELIMITERS);
				for (int k = 0; k < splitted.length; k++) {
					boolean isStopWords = false;
					for (int m = 0; m < stopWords.size(); m++) {
						if (splitted[k].equals(stopWords.get(m))) {
							isStopWords = true;
						}
					}
					if (!isStopWords) {
						hashedDictionary.put(splitted[k], filesList2[j].getName());
					}
				}
//				System.out.println("Contents of the file: " + sb.toString());
//				System.out.println(" ");
			}
		}
		long endTime = System.nanoTime();
		long estimatedTime = endTime - startTime; //
		double seconds = (double) estimatedTime / 1000000000; //

//		hashedDictionary.getValue("sales");
//		hashedDictionary.remove("sales");
//		
//		hashedDictionary.getValue("sales");
		System.out.println("Collision Count " + hashedDictionary.getCollisionCount());

		searchWords(hashedDictionary);

		System.out.println("index time" + seconds);

	}
}
