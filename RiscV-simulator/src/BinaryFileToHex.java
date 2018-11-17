import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BinaryFileToHex
{
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static void main(String[] args) throws IOException
	{
		Path path = Paths.get("./src/addlarge.bin");
		byte[] data = Files.readAllBytes(path);
		System.out.println(bytesToHex(data));
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}	
}


