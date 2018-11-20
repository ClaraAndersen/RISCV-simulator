package RV32I;

public class rV32I {
	static int pc;
	static int reg[] = new int[32]; //Initializing register
	
	//We should here read program into instruction memory
	static int progr[] = {
			// As minimal RISC-V assembler example
			0x00200093, // addi x1 x0 2
			0x00300113, // addi x2 x0 3
			0x002081b3, // add x3 x1 x2
	};
	
	//The simulation
	public static void main(String[] args) {

		pc = 0;

		for (;;) {

			int instr = progr[pc]; //reading the instruction from the instruction memory
			
			//Decoding the instruction
			int opcode = instr & 0x7f; //7 first bits.
			
			//For U format
			int rd = (instr >> 7) & 0x01f;
			int imm = (instr >> 12);
			
			//For I format
			

			switch (opcode) {
			case 0x37: //LUI -Load upper immediate
				reg[rd] = imm << 12;
				break;
			case 0x17: //AUIPC -Add upper immediate to PC
				reg[rd] = (imm << 12) + pc;
				break;
//		case 0x6F: //JAL -Jump and link
//				reg[rd] = (imm << 12) + pc;
//				break;
			case 0x13: // ADDI
			//	reg[rd] = reg[rs1] + imm;
			//	break;
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


////*if((res2 >> 4) ==1) { //we have a negative number
//	if(((reg[res1] >> res2) >> 31)==0) {
//		reg[rd]= (reg[res1] >> res2)+(80000000); //should set the sign bit to 1 
//	}
//	else {
//		reg[rd]= (reg[res1] >> res2); 
//	}
//}
//else {//we have a positve number
//	if(((reg[res1] >> res2) >> 31)==1) {
//		reg[rd]= (reg[res1] >> res2)+(80000000); //should set the sign bit to 0 (with overflow)
//	}
//	else {
//		reg[rd]= (reg[res1] >> res2);
//	}
//}