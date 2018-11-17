import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BinaryFileToHex {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
//	public static void main(String[] args) throws IOException {
//		Path path = Paths.get("./src/addlarge.bin");
//		byte[] data = Files.readAllBytes(path);
//		System.out.println(bytesToHex(data));
	
	
	// Reads the binary file containing the instruction set into a byte array.
	public byte[] readByteArray() throws IOException{
		Path path = Paths.get("./src/addlarge.bin");
		byte[] data = Files.readAllBytes(path);
		return data;
	}
	
	// Converts the byte array into a Hex - string.
	public String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}	
	
	public String ConvertedByteToHex() throws IOException {
		return bytesToHex(readByteArray());	
	}
	
	public String[] hexToArray() throws IOException{

		BinaryFileToHex binaryFile = new  BinaryFileToHex();
		String s = binaryFile.ConvertedByteToHex(); 

		StringBuilder str = new StringBuilder(s);
		int idx = str.length() - 8;

		while (idx > 0){
			str.insert(idx, ",");
			idx = idx - 8;
		}
		String[] array = new String[]{str.toString()};
		return array;
	}
}




