import java.io.IOException;


public class Main extends BinaryFileToHex{

	public static void main(String[] args) throws IOException {

		BinaryFileToHex binaryFile = new  BinaryFileToHex();
//		System.out.println(Arrays.toString(binaryFile.HexArray()));
		
//		System.out.println(binaryFile.ConvertedByteToHex());
//		
		String test = binaryFile.HexArray()[1];
		System.out.println("Hex: "+test);
//		

//		int hello = Integer.parseInt(test,16);
//		System.out.println("Decimal of hex: "+hello);
		
//		short s = (short) Integer.parseInt(test,16);
//		System.out.println(s);
//		System.out.println(unsignedByteToInt(hello));
		
//		String hex = Integer.toHexString(s);
//		System.out.println(hex);
	
//		int something = Integer.parseUnsignedInt(test);
//		System.out.println(something);
		
//		int intVal = Integer.parseInt(test, 16);
//		String twosComplement = Integer.toHexString((-1 * intVal));
//		System.out.println(twosComplement);
		
//		String hex = test;
//
//		// First convert the Hex-number into a binary number:
//		String bin = Long.toString(Integer.parseInt(hex, 16), 2);
		

		// Now create the complement (make 1's to 0's and vice versa)
//		String binCompl = bin.replace('0', 'X').replace('1', '0').replace('X', '1');
//
//		// Now parse it back to an integer, add 1 and make it negative:
//		int result = (Integer.parseInt(binCompl, 2) + 1) * -1;
//		
//		System.out.println("Convert    : "+result);
//		System.out.println("Binary     : "+bin);
//		System.out.println("BinaryCompl: "+binCompl);
		
//		
//		System.out.println(Arrays.toString(binaryFile.readByteArray()));
//		Arrays.copyOfRange(binaryFile.readByteArray(),  6,  binaryFile.readByteArray().length);
		
	}
	
//	  public static int unsignedByteToInt(int s) {
//		    return (int) s & 0xFF;
//		  }
}