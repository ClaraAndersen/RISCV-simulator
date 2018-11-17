package RV32I;

public class rv32I2 {
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
			int rd = (instr >> 7) & 0x01f;
			int funct3 = (instr >> 12) & 7;
			int res1 = (instr >> 14) & 0x01f;
			int res2 = (instr >> 20) & 0x01f;
			int funct7 = (instr >> 25) & 0x7f;
			int imm12 =instr>>20;
			int imm7 =instr>>25;
			int imm5 =(instr >> 7) & 0x1f;
			int imm20 =instr>>12;
			
			switch (opcode) {
			case 0x37: //LUI -Load upper immediate
				reg[rd] = imm20 << 12;
				break;
				
			case 0x17: //AUIPC -Add upper immediate to PC
				reg[rd] = (imm20 << 12) + pc;
				break;

			case 0x13: //format type I
				
				switch (funct3) {
				
				case 0x0: //ADDI- Add immediate
					reg[rd]=reg[res1] + imm12;
				case 0x2: // SLTI - Set less than immediate
					if(reg[res1]< imm12) {
						reg[rd]=1;
					}
					else {
						reg[rd]=0;
					};
					break;
					
				case 0x3: //SLTIU -set less than immediate unsigned 
					if((imm12 >> 11) ==1) {
						if(reg[res1]< 0xFFFFF000 + imm12){ 
							//what does it mean to do an unsigned comparison.
							reg[rd]=1;}
						else {
							reg[rd]=0;
						}
					}
					else {
						if(reg[res1]< imm12) {
							reg[rd]=1;}
						else {
							reg[rd]=0;
						}
					};
					break;
					
				case 0x4: //XORI - Exclusive or immediate
					if((imm12 >> 11) ==1) {
						if((reg[res1] >> 4) ==1) {
							reg[rd]=(0xFFFFF000 + imm12)^(0xFFFFFFE0 + reg[res1]);
						}
						else {
							reg[rd]=(0xFFFFF000 + imm12)^(reg[res1]);
						}
					}
					else {
						if((reg[res1] >> 4) ==1) {
							reg[rd]=(imm12)^(0xFFFFFFE0 + reg[res1]);
						}
						else {
							reg[rd]=(imm12)^(reg[res1]);
						}
					};
					break;
					
				case 0x6: //ORI -or, immediate
					if((imm12 >> 11) ==1) {
						if((reg[res1] >> 4) ==1) {
							reg[rd]=(0xFFFFF000 + imm12)|(0xFFFFFFE0 + reg[res1]);
						}
						else {
							reg[rd]=(0xFFFFF000 + imm12)|(reg[res1]);
						}
					}
					else {
						if((res1 >> 4) ==1) {
							reg[rd]=(imm12)|(0xFFFFFFE0 + reg[res1]);
						}
						else {
							reg[rd]=(imm12)|(reg[res1]);
						}
					};
					break;
					
				case 7: //ANDI- bitwise and, immediate
					if((imm12 >> 11) ==1) {
						if((reg[res1] >> 4) ==1) {
							reg[rd]=(0xFFFFF000 + imm12)&(0xFFFFFFE0 + reg[res1]);
						}
						else {
							reg[rd]=(0xFFFFF000 + imm12)&(reg[res1]);
						}
					}
					else {
						if((reg[res1] >> 4) ==1) {
							reg[rd]=(imm12)&(0xFFFFFFE0 + reg[res1]);
						}
						else {
							reg[rd]=(imm12)&(reg[res1]);
						}
					};
					break;
				case 0x1: //SLLI -Shift left logical, immediate 
					reg[rd]= reg[res1] << res2;
					break;
					
				case 0x5:
					switch(imm7) {
						case 0x0: //SRLI -Shift right logical, immediate 
							reg[rd]= reg[res1] >> res2;
							break;
						case 0x20: //SRAI -Shift right arithmetic immediate
							//We save the sign bit
							reg[rd]= (reg[res1] >> res2)+();
							break;
					}
				break;
					
					
					
				}
				
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
