import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BinaryToHex {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		File f = new File("addlarge.bin");
		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(f))) {
			for (int b; (b = is.read()) != -1;) {
				/** 
				 * // This part will convert to binary. 
				 * String s = "0000000" + Integer.toBinaryString(b);
				 * s = s.substring(s.length() - 8); 
				 * sb.append(s).append(' ');
				 */
				String s = Integer.toHexString(b).toUpperCase();
				if (s.length() == 1) {
					sb.append('0');
				}
				sb.append(s).append(' ');
			}
			System.out.println(sb);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
