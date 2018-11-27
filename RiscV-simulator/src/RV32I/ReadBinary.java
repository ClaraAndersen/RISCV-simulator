package RV32I;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadBinary {

	static ArrayList<Integer> progr=new ArrayList<>();
	
	static String destination = "./src/test3/loop.bin";
	
	public static ArrayList<Integer> readBinFile(String destination) throws IOException{
		DataInputStream binfile = new DataInputStream(new FileInputStream(destination));
		while(binfile.available() > 0) {
			try
			{
				int instruction = binfile.readInt();
				instruction = Integer.reverseBytes(instruction);
				progr.add(instruction);
			}
			catch(EOFException e) {
			}
		}
		binfile.close();
		return progr;
	}
		
	public static int[] convertIntegers(ArrayList<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
	
	public static int[] converted() throws IOException {
		return convertIntegers(readBinFile(destination));
	}
	
	
	public static int[] intArray() throws IOException {
		return converted();
	}
}