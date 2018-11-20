import java.io.IOException;

/**
 * RISC-V Instruction Set Simulator
 * 
 * A tiny first step to get the simulator started. Can execute just a single
 * RISC-V instruction.
 * 
 * @author Martin Schoeberl (martin@jopdesign.com)
 *
 */
public class IsaSim {

	static int pc;
	static int reg[] = new int[32];

	 // Here the first program hard coded as an array
//	static int progr[] = {
//			// As minimal RISC-V assembler example
//			0x07D00313,
//			0x02330393,
//			0xF9C38E13,
//			0x00A00513,
//			0x00000073    
//	};
	static BinaryFileToHex binaryFile = new  BinaryFileToHex();

	public static void main(String[] args) throws IOException {
		
		int progr[] = binaryFile.tester();
		

		System.out.println("Hello RISC-V World!");

		pc = 0;

		for (;;) {

			int instr = progr[pc];
			int opcode = instr & 0x7f;
			int rd = (instr >> 7) & 0x01f;
			int rs1 = (instr >> 15) & 0x01f;
			int imm = (instr >> 20);

			switch (opcode) {

			case 0x13:
				swithc(rd){case 7:}
				
				reg[rd] = reg[rs1] + imm;
				break;
			default:
				System.out.println("Opcode " + opcode + " not yet implemented");
				break;
			}

			++pc; // We count in 4 byte words
			if (pc >= progr.length) {
				break;
			}
			for (int i = 0; i < reg.length; ++i) {
				System.out.print(reg[i] + " ");
			}
			System.out.println();
		}

		System.out.println("Program exit");

	}

}