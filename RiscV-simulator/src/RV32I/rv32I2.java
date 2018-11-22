package RV32I;

import java.io.IOException;
import java.lang.Math;

public class rv32I2 {
	static int pc;
	static int reg[] = new int[32]; //Initializing register
	static BinaryFileToHex binaryFile = new  BinaryFileToHex();


	//The simulation
	public static void main(String[] args) throws IOException {
		int progr[] = binaryFile.tester();

		pc = 0;

		for (;;) {

			int instr = progr[pc/4]; //reading the instruction from the instruction memory

			//Decoding the instruction
			int opcode = instr & 0x7f; //7 first bits.
			int rd = (instr >> 7) & 0x01f;
			int funct3 = (instr >> 12) & 7;
			int res1 = (instr >> 15) & 0x01f;
			int res2 = (instr >> 20) & 0x01f;
			int funct7 = (instr >> 25) & 0x7f;
			int imm12 =(instr>>20) & 0xFFF;
			int imm7 =(instr>>25) & 0x7F;
			int imm5 =(instr >> 7) & 0x1f;
			int imm20 =(instr>>12) & 0xFFFFF;

			switch (opcode) {
			case 0x37: //LUI -Load upper immediate
				reg[rd] = imm20 << 12;
				break;

			case 0x17: //AUIPC -Add upper immediate to PC
				reg[rd] = (imm20 << 12) + pc;
				break;

			case 0x13:
				switch (funct3) {

				case 0x0: //ADDI- Add immediate
					if((imm12 >> 11) ==1) { //negative immediate 
						reg[rd]=(0xFFFFF000 + imm12)+(reg[res1]); //sign extension
					}
					else { //positive immediate
						reg[rd]=(imm12)+(reg[res1]); //nothing is done to sign extend
					}
					break;
				case 0x2: // SLTI - Set less than immediate
					if((imm12 >> 11) ==1) { //negative immediate 
						if(reg[res1]< 0xFFFFF000 + imm12) {//comparison with sign extension
							reg[rd]=1;
						}
						else {
							reg[rd]=0;
						}
					}
					else { //positive immediate
						if(reg[res1]< imm12) {
							reg[rd]=1; 
						}
						else {
							reg[rd]=0;
						}
					}
					break;

				case 0x3: //SLTIU -set less than immediate unsigned 
					if((imm12 >> 11) ==1) { //negative case
						if(Math.abs(reg[res1])< Math.abs((0xFFFFF000 + imm12))){ //comparison with sign extension
							reg[rd]=1;
						}
						else {
							reg[rd]=0;
						}
					}
					else { //positive case
						if(Math.abs(reg[res1])< Math.abs(imm12)) {//nothing need to be done for sign extension, since sign bit is 0.
							reg[rd]=1; 
						}
						else {
							reg[rd]=0;
						}
					}
					break;

				case 0x4: //XORI - Exclusive or immediate
					if((imm12 >> 11) ==1) { //negative immediate 
						reg[rd]=(0xFFFFF000 + imm12)^(reg[res1]); //sign extension
					}
					else { //positive immediate
						reg[rd]=(imm12)^(reg[res1]); //nothing is done to sign extend
					}					

					break;

				case 0x6: //ORI -or, immediate
					if((imm12 >> 11) ==1) { //negative immediate
						reg[rd]=(0xFFFFF000 + imm12)|(reg[res1]); //sign extension
					}
					else {
						reg[rd]=(imm12)|(reg[res1]);
					}
					break;

				case 0x7: //ANDI- bitwise and, immediate
					if((imm12 >> 11) ==1) {//negative case
						reg[rd]=(0xFFFFF000 + imm12) & reg[res1]; //sign extended 
					}
					else {//positive case
						reg[rd]=(imm12)&(reg[res1]); 
					}
					break;

				case 0x1: //SLLI -Shift left logical, immediate 
					reg[rd]= reg[res1] << res2; //we refer to res2 here, as it is equivalent to the 5 lower bits in the immediate field in the I-format.
					break; 

				case 0x5:
					switch(funct7) {

					case 0x0: //SRLI -Shift right logical, immediate 
						reg[rd]= reg[res1] >> res2; //shift, and put zero into the bits in front
					break;

				case 0x20: //SRAI -Shift right arithmetic immediate
					reg[rd] = reg[res1] >>> res2;
					//>> put the sign bit into upper bits as the bits are being shifted
					break;
					}
					break;
				}
				break;
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
					break;
				case 0x1: //SLL- Shift left logical
					reg[rd]=reg[res1]<<(reg[res2] & 0x1F); //because it is only by the amount in the lower 5 bit
					break;
				case 0x2: //SLT -Set less than signed
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
						break;
					}break;
				case 0x6: //OR
					reg[rd]=reg[res1]|reg[res2];
					break;
				case 0x7://AND
					reg[rd]=reg[res1]&reg[res2];
					break;
				}
				break;

			case 0x73://ECALL
				if(reg[10]==1) {//print int in a0(x10)
					System.out.print(reg[11]);
				}
				else if(reg[10]==4){//print string in a0(x10)
					System.out.print(reg[11]);
				}
				else if(reg[10]==9) {//allocates a1 bytes on the heap, returns pointer to start in a0

				}
				else if(reg[10]==10) {//exit programm
					System.exit(1);
				}
				else if(reg[10]==11) { //prints ASCII character in a1
					System.out.print(reg[11]);
				}
				else if(reg[10]==17) { //ends the program with return code in a1
					System.out.print(reg[11]);
					System.exit(1);
				}
				else {//not recognized ID in a0
					System.out.print("ecall " +reg[10] + " is not valid ");
				}
				break;


			default:
				System.out.println("Opcode " + opcode + " not yet implemented");
				break;
			}

			pc=pc+4; // We count in 4 byte words
			if (pc/4 >= progr.length) {
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
