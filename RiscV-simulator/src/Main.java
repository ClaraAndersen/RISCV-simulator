import java.io.IOException;
import java.util.Arrays;

public class Main extends BinaryFileToHex{

	public static void main(String[] args) throws IOException {


		BinaryFileToHex binaryFile = new  BinaryFileToHex();
		System.out.println(binaryFile.hexToArray().toString());

	}
}