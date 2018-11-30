import java.io.IOException;
import java.util.Arrays;


public class Main extends BinaryFileToHex{

	public static void main(String[] args) throws IOException {

		// Testing 
//		BinaryFileToHex binaryFile = new  BinaryFileToHex();
//		
//		System.out.println("Original Array : "+Arrays.toString(BinaryFileToHex.HexArray()));
//		System.out.println("Flipped bytes  : "+BinaryFileToHex.ConvertedByteToHex());
//		int[] test = binaryFile.tester();
//		System.out.println("Hex as index   : "+test);
//		
//		if (0xF9C38E13 > Math.pow(2, 32) * 0.5 - 1){
//			System.out.println("True");
//		} 
//		else {
//			System.out.println("False");
//		}
		String result = Long.toBinaryString( Integer.toUnsignedLong(30) | 0x100000000L ).substring(1);
		System.out.println(result);
		
	}
}