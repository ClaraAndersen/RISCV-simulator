package RV32I;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class BinaryDump {

	BinaryDump(){

	}

	public static void CreatingTempFile(String s) throws IOException {
		FileOutputStream outputStream = new FileOutputStream("./src/RV32I/temp.txt");
		
		String[] fileContent = s.split(" ");
		
		int[] integers = new int[fileContent.length];
		for (int i = 0; i < integers.length; i++){
		    integers[i] = Integer.parseInt(fileContent[i]); 
		}
		String strOfInts = Arrays.toString(integers).replaceAll("\\[|\\]|,|\\s", "");
		
		byte[] strToBytes = strOfInts.getBytes();
	    outputStream.write(strToBytes);
		outputStream.close();
	}
	
	

	
}

