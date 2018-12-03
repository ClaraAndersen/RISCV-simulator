package RV32I;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BinaryDump {
	
	private String filename = "./src/BinaryDump.txt";

	public void saveDataInFile(String data) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
	    
	    writer.append(data);
	    writer.close();
	    	    
	}
	
	

}
