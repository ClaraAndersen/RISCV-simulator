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
					switch(funct7) {
						case 0x0: //SRLI -Shift right logical, immediate 
							reg[rd]= reg[res1] >> res2;
							break;
						case 0x20: //SRAI -Shift right arithmetic immediate
							//We save the sign bit res2 corresponds to shamt
							if((res2 >> 4) ==1) { //we have a negative number
								if(((reg[res1] >> res2) >> 31)==0) {
									reg[rd]= (reg[res1] >> res2)+(80000000); //should set the sign bit to 1 
								}
								else {
									reg[rd]= (reg[res1] >> res2); 
								}
							}
							else {//we have a positve number
								if(((reg[res1] >> res2) >> 31)==1) {
									reg[rd]= (reg[res1] >> res2)+(80000000); //should set the sign bit to 0 (with overflow)
								}
								else {
									reg[rd]= (reg[res1] >> res2);
								}
							}
							
							break;
					}
				break;
				}
				
			case 0x33:
				switch(funct3) {
				case 0x0:
					switch(funct7) {
					case 0x0: //add
						reg[rd]=reg[res1]+reg[res2];
						break;
					case 0x20: //sub
						reg[rd]=reg[res1]-reg[2];
						break;
					}
				case 0x1: //SLL- Shift left logical
					reg[rd]=reg[res1]<<(reg[res2] & 0x1F); //because it is only by the amount in the lower 5 bit
					break;
				case 0x2: //SLT -Set less than
					if(reg[res1]<reg[res2]) {
						reg[rd]=1;
					}
					else {
						reg[rd]=0;
					}
					break;
				//case 0x3: //SLTU -Set less than unsigned ?????
				case 0x4: //XOR -exclusive or
					reg[rd]=reg[res1]^reg[res2];
					break;
				case 0x5:
					switch(funct7) {
					case 0x0://SRL -Shift right logical
					reg[rd]=reg[res1]>>(reg[res1]& 0x1F);
					break;
					case 0x20://SRA -Shift right arithmetic
						if((reg[res1] & 80000000)==1) { //negative number
							if((reg[res1]>>(reg[res1]& 0x1F)>>31)==0) {
								reg[rd]= (reg[res1] >> res2)+(80000000);//set bit sign to 1
							}
							else {
								reg[rd]= (reg[res1] >> res2);
							}
						}
						else {//positive
							if((reg[res1]>>(reg[res1]& 0x1F))==1) {//negative
								reg[rd]= (reg[res1] >> res2)+(80000000);//set bit sign to 0
							}
							else {
								reg[rd]= (reg[res1] >> res2);
							}	
						}
						}
				case 0x6: //OR
					reg[rd]=reg[res1]|reg[res2];
					break;
				case 0x7://AND
					reg[rd]=reg[res1]&reg[res2];
					}
					break;
			case 0x73://ECALL
				
				
				
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
