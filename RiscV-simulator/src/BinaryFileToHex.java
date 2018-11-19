import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;


public class BinaryFileToHex {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	// Method that reads the binary file containing the instruction 
	// set into a byte array.
	public byte[] readByteArray() throws IOException{
		Path path = Paths.get("./src/test_addi.bin");
		byte[] data = Files.readAllBytes(path);
		return data;
	}
	// Method that reverses the order of the byte array.
	// Because we want to prepare for converting from little edian to big edian 
	public byte[] reverse(byte[] array) {
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
	public String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}	

	// Method that returns the Hex with 0x in the start.
	public String ConvertedByteToHex() throws IOException {
		String myString = bytesToHex(reverse(readByteArray()));
//		String newString = "0x" + myString.replaceAll("(.{8})(?!$)", "$1 0x");
		String newString = myString.replaceAll("(.{8})(?!$)", "$1 ");
		return newString;
	}

	// Return hex in a String array where we can access the indices with " HexArray()[1]"
	// and reverse the order of the elements in the string array.
	public String[] HexArray() throws IOException {
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
	
	
	

}




