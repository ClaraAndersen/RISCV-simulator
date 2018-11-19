import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


public class BinaryFileToHex {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private String[] words;
	private int i;
	//	public static void main(String[] args) throws IOException {
	//		Path path = Paths.get("./src/addlarge.bin");
	//		byte[] data = Files.readAllBytes(path);
	//		System.out.println(bytesToHex(data));


	// Reads the binary file containing the instruction set into a byte array.
	// Maybe not fully useful.
	public byte[] readByteArray() throws IOException{
		Path path = Paths.get("./src/test_addi.bin");
		byte[] data = Files.readAllBytes(path);
		return data;
//		List<byte[]> listOfByte = Arrays.asList(data);	
//		Collections.reverse(listOfByte);
//		
//		byte[] result = listOfByte.stream().collect(
//				() -> new ByteArrayOutputStream(),
//				(b, e) -> {
//					try {
//						b.write(e);
//					} catch (IOException e1) {
//						throw new RuntimeException(e1);
//					}
//				},
//				(a, b) -> {}).toByteArray();
//		return result;
	}
	// Reverse
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

	// Converts the byte array into a Hex - string.
	public String bytesToHex(byte[] bytes) {
		
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
//		String newString = new StringBuilder(new String(hexChars)).reverse().toString();
		return new String(hexChars);
	}	

	// Returns the Hex with 0x in the start.
	public String ConvertedByteToHex() throws IOException {
		String myString = bytesToHex(reverse(readByteArray()));
//		String newString = "0x" + myString.replaceAll("(.{8})(?!$)", "$1 0x");
		String newString = myString.replaceAll("(.{8})(?!$)", "$1 ");
		return newString;
	}

	// Return hex in a String array where we can access the indices with " HexArray()[1] "
	public String[] HexArray() throws IOException {
		String line = ConvertedByteToHex();
		Pattern pattern = Pattern.compile(" ");
		words = pattern.split(line);
		return words;
	
	}
	
	public String[] reverseString(String[] words) {
		String[] t = new String[words.length];
		for (String wordTemp : words) {
			StringBuilder sb = new StringBuilder(wordTemp);
			t[i] = sb.reverse().toString();   
		}
		return t;
	}
}




