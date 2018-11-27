package RV32I;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;



public class BinaryFileToHex {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public BinaryFileToHex() {

	}

	// Method that reads the binary file containing the instruction 
	// set into a byte array.
	public static byte[] readByteArray() throws IOException{
		Path path = Paths.get("./src/test3/loop.bin");
		byte[] data = Files.readAllBytes(path);
		return data;
	}
	// Method that reverses the order of the byte array.
	// Because we want to prepare for converting from little edian to big edian 
	public static byte[] reverse(byte[] array) {
		int i = 0;
		int j = array.length - 1;
		byte tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
		return array;
	}

	// Method that converts the byte array into a Hex - string.
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}	

	// Method that returns the Hex with 0x in the start.
	public static String ConvertedByteToHex() throws IOException {
		String myString = bytesToHex(reverse(readByteArray()));
		//		String newString = "0x" + myString.replaceAll("(.{8})(?!$)", "$1 0x");
		String newString = myString.replaceAll("(.{8})(?!$)", "$1 ");
		return newString;
	}

	// Return hex in a String array where we can access the indices with " HexArray()[1]"
	// and reverse the order of the elements in the string array.
	public static String[] HexArray() throws IOException {
		String line = ConvertedByteToHex();
		Pattern pattern = Pattern.compile(" ");
		String [] words = pattern.split(line);
		Arrays.toString(words);
		for(int i = 0; i < words.length/2; i++) {
			String temp = words[i];
			words[i] = words[words.length - i - 1];
			words[words.length - i - 1] = temp;
		}
		return words;
	}

	public static int[] StringArrToIntArr(String[] s) {
		int[] result = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			result[i] = (int)Long.parseLong(s[i],16);
		}
		return result;
	}


	public static int[] tester() throws IOException {
//		int[] result= {0x07D00313, 0x02330393, 0xF9C38E13, 0x00A00513, 0x00000073};
		String[] test = HexArray();
		int[] result = StringArrToIntArr(test);
		System.out.println(result[1]);
		System.out.println(result.length);
		return result;
	}




}







