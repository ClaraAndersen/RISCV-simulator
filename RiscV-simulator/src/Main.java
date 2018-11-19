import java.io.IOException;
import java.util.Arrays;


public class Main extends BinaryFileToHex{

	public static void main(String[] args) throws IOException {

		// Testing 
		BinaryFileToHex binaryFile = new  BinaryFileToHex();
		System.out.println("Original Array : "+Arrays.toString(binaryFile.HexArray()));
		System.out.println("Flipped bytes  : "+binaryFile.ConvertedByteToHex());
		String test = binaryFile.HexArray()[4];
		System.out.println("Hex as index   : "+test);
		//		

	}
}