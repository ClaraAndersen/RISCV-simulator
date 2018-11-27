package RV32I;

import java.io.IOException;

public class rv32I2 {
	//static int pc;
	static byte [] memory = new byte [4000000];
	static Boolean jumpe;
	static int reg[] = new int[32]; //Initializing register
	static BinaryFileToHex binaryFile = new  BinaryFileToHex();
	public static int compareUnsigned(long x, long y) {
		   return Long.compare(x + Long.MIN_VALUE, y + Long.MIN_VALUE);
		}
	static programCounter pc = new programCounter();
	//static int memory[]= new int[(int) Math.pow(1000,2)];

	//The simulation
	public static void main(String[] args) throws IOException {
//		int progr[] = binaryFile.tester();
		int progr[] = {
				0x00100137,  
				0x00010113,
				0x07c000ef,
				0x00050593,
				0x00a00513,
				0x00000073,
				0xfd010113,
				0x02812623,
				0x03010413,
				0xfca42e23,
				0xfcb42c23,
				0xfe042623,
				0xfe042423,
				0x0300006f,
				0xfe842783,
				0x00279793,
				0xfdc42703,
				0x00f707b3,
				0x0007a783,
				0xfec42703,
				0x00f707b3,
				0xfef42623,
				0xfe842783,
				0x00178793,
				0xfef42423,
				0xfe842703,
				0xfd842783,
				0xfcf746e3,
				0xfec42783,
				0x00078513,
				0x02c12403,
				0x03010113,
				0x00008067,
				0xfd010113,
				0x02112623,
				0x02812423,
				0x02912223,
				0x03010413,
				0x00010513,
				0x00050493,
				0x06400513,
				0xfea42423,
				0xfe842503,
				0xfff50893,
				0xff142223,
				0x00050893,
				0x00088313,
				0x00000393,
				0x01b35893,
				0x00539713,
				0x00e8e733,
				0x00531693,
				0x00050713,
				0x00070593,
				0x00000613,
				0x01b5d713,
				0x00561813,
				0x01076833,
				0x00559793,
				0x00050793,
				0x00279793,
				0x00378793,
				0x00f78793,
				0x0047d793,
				0x00479793,
				0x40f10133,
				0x00010793,
				0x00378793,
				0x0027d793,
				0x00279793,
				0xfef42023,
				0xfe042623,
				0x0280006f,
				0xfe042703,
				0xfec42783,
				0x00279793,
				0x00f707b3,
				0xfec42703,
				0x00e7a023,
				0xfec42783,
				0x00178793,
				0xfef42623,
				0xfec42703,
				0xfe842783,
				0xfcf74ae3,
				0xfe042783,
				0xfe842583,
				0x00078513,
				0x00000317,
				0xeb8300e7,
				0xfca42e23,
				0x00048113,
				0x00000013,
				0xfd040113,
				0x02c12083,
				0x02812403,
				0x02412483,
				0x03010113,
				0x00008067,
		};
		programCounter PC=pc;
		
		for (;;) {
			jumpe=false;
			
			int instr = progr[PC.pc/4]; //reading the instruction from the instruction memory
			//System.out.println(instr);
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
			
			System.out.println(opcode);
			
			switch (opcode) {
			case 0x37: //LUI -Load upper immediate
				reg[rd] = imm20 << 12;
				break;

			case 0x17: //AUIPC -Add upper immediate to PC
				reg[rd] = (imm20 << 12) + PC.pc;
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
						if(compareUnsigned(reg[res1],(0xFFFFF000 +imm12))<0) {
							reg[rd]=1;
						}
						else {
							reg[rd]=0;
						}
					}
					else { //positive case
						if(compareUnsigned(reg[res1],imm12)<0) {
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
						reg[rd]= reg[res1] >>> (res2 & 0x1F); //shift, and put zero into the bits in front
					break;

				case 0x20: //SRAI -Shift right arithmetic immediate
					reg[rd] = reg[res1] >> (imm12 & 0x1F);
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
						reg[rd]=reg[res1]-reg[res2];
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
					case 0x3: //SLTU -Set less than unsigned
							if(compareUnsigned(reg[res1],reg[res2])<0) {
								reg[rd]=1;
							}
							else {
								reg[rd]=0;
							}
							break;
					
				case 0x4: //XOR -exclusive or
					reg[rd]=reg[res1]^reg[res2];
					break;
				case 0x5:
					switch(funct7) {
					case 0x0://SRL -Shift right logical
						reg[rd]=reg[res1]>>>(reg[res2]& 0x1F);
					break;
					case 0x20://SRA -Shift right arithmetic
						reg[rd]=reg[res1]>>(reg[res2]& 0x1F);
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
				
			case 0x6F: //JAL- Jump and link
				if (rd!=0) { // Test if we should save link
				reg[rd]=PC.pc+4; //we save the address for the next instruction to performe
				}
				PC.jal(imm12);
				jumpe=true;
				break;
				
			case 0x67: //JALR- jump and link register
				if (rd!=0) { // Test if we should save link
				reg[rd]=PC.pc+4;
				}
				if (imm12>>11==1) { //negative case
					 PC.pc=(reg[res1]+(0xFFFFF000 + imm12))& 0x7FFFFFFE;
				 }
				 else { //positive case
					 PC.pc=(reg[res1]+imm12)& 0x7FFFFFFE;
				 }
				jumpe=true;
				break;
				
			case 0x63:
				switch(funct3){
				case 0x0: //BEQ -branch if equal
					if(reg[res1]==reg[res2]) {
//						imm12=(imm7<<5)+imm5;
						int im11 = (instr>>7) & 0x1;
						int im4 = (instr>>8) & 0xF;
						int im6 = (instr>>25) & 0x1B207;
						int im1 = (instr<<31) & 0x1;
 						//int imm13=((imm7<<5)+imm5);
						int imm13= (((im1<<11)+(im11<<10))+(im6<<4)+im4)<<1;
//						if (imm12>>11==1) { //negative case
//							 PC.pc=PC.pc+(0xFFFFF000 + imm12);
//						 }
						if (imm13<0) { //negative case
//							 PC.pc=PC.pc+(0xFFFFE000 + imm13);
							PC.pc=PC.pc+(0xFFFFE000 + imm13);
						 }
						 else { //positive case
							 PC.pc=PC.pc+imm13;
						 }
						jumpe=true;
					}
				break;
				case 0x1: //BNE -branch if not equal
					if(reg[res1]!=reg[res2]) {
						//imm12=(imm7<<5)+imm5;
						int im11 = (instr>>7) & 0x1;
						int im4 = (instr>>8) & 0xF;
						int im6 = (instr>>25) & 0x1B207;
						int im1 = (instr<<31) & 0x1;
 						//int imm13=((imm7<<5)+imm5);
						int imm13= (((im1<<11)+(im11<<10))+(im6<<4)+im4)<<1;
//						if (imm12>>11==1) { //negative case
//						 PC.pc=PC.pc+(0xFFFFF000 + imm12);
//					 }
						if (imm13<0) { //negative case
//							 PC.pc=PC.pc+(0xFFFFE000 + imm13);
							PC.pc=PC.pc+(0xFFFFE000 + imm13);
						 }
						 else { //positive case
							 PC.pc=PC.pc+imm13;
						 }
						jumpe=true;
					}
				break;
				case 0x4: //BLT -branch if less than
					if(reg[res1]<reg[res2]) {
						int im11 = (instr>>7) & 0x1;
						int im4 = (instr>>8) & 0xF;
						int im6 = (instr>>25) & 0x3F;
						int im1 = (instr>>31) & 0x1;
 						//int imm13=((imm7<<5)+imm5);
						int imm13= ((((im1<<11) & 0x800)+((im11<<10) & 0x400))+((im6<<4) & 0x3F0) + (im4 & 0xF))<<1;
						//System.out.println("test im1" )
						if (im1==1) { //negative case
//							 PC.pc=PC.pc+(0xFFFFE000 + imm13);
							PC.pc=PC.pc+(0xFFFFE000 + imm13);
						 }
						 else { //positive case
							 PC.pc=(PC.pc+imm13);
						 }
						jumpe=true;
						
					}
					
				break;
				case 0x6: //BLTU -branch if less than, unsigned
					if(compareUnsigned(reg[res1],reg[res2])<0) {
//						imm12=(imm7<<5)+imm5;
						int im11 = (instr>>7) & 0x1;
						int im4 = (instr>>8) & 0xF;
						int im6 = (instr>>25) & 0x1B207;
						int im1 = (instr<<31) & 0x1;
 						//int imm13=((imm7<<5)+imm5);
						int imm13= (((im1<<11)+(im11<<10))+(im6<<4)+im4)<<1;
						if (imm13<0) { //negative case
//							 PC.pc=PC.pc+(0xFFFFE000 + imm13);
							PC.pc=PC.pc+(0xFFFFE000 + imm13);
						 }
//						if (imm12>>11==1) { //negative case
//							 PC.pc=PC.pc+(0xFFFFF000 + imm12);
//						 }
						 else { //positive case
							 PC.pc=PC.pc+imm13;
						 }
						jumpe=true;
					}
				break;
				
				case 0x7: //BGEU -branch if greater than or equal, unsigned
					if(compareUnsigned(reg[res1],reg[res2])>=0) {
//						imm12=(imm7<<5)+imm5;
						int im11 = (instr>>7) & 0x1;
						int im4 = (instr>>8) & 0xF;
						int im6 = (instr>>25) & 0x1B207;
						int im1 = (instr<<31) & 0x1;
 						//int imm13=((imm7<<5)+imm5);
						int imm13= (((im1<<11)+(im11<<10))+(im6<<4)+im4)<<1;
						if (imm13<0) { //negative case
//							 PC.pc=PC.pc+(0xFFFFE000 + imm13);
							PC.pc=PC.pc+(0xFFFFE000 + imm13);
						 }
//						if (imm12>>11==1) { //negative case
//							 PC.pc=PC.pc+(0xFFFFF000 + imm12);
//						 }
						 else { //positive case
							 PC.pc=PC.pc+imm13;
						 }
						jumpe=true;
					}
				break;
					
				case 0x5: //BGE -branch if greater than or equal 
					if(reg[res1]>=reg[res2]) {
						int im11 = (instr>>7) & 0x1;
						int im4 = (instr>>8) & 0xF;
						int im6 = (instr>>25) & 0x1B207;
						int im1 = (instr<<31) & 0x1;
 						//int imm13=((imm7<<5)+imm5);
						int imm13= (((im1<<11)+(im11<<10))+(im6<<4)+im4)<<1;
//						if (imm13<0) { //negative case
//						imm12=(imm7<<5)+imm5;
//						if (imm12>>11==1) { //negative case
//							 PC.pc=PC.pc+(0xFFFFF000 + imm12);
//						 }
						if (imm13<0) { //negative case
//							 PC.pc=PC.pc+(0xFFFFE000 + imm13);
							PC.pc=PC.pc+(0xFFFFE000 + imm13);
						 }
						 else { //positive case
							 PC.pc=PC.pc+imm13;
						 }
						jumpe=true;
					}
					break;
				}
				break;
				
			case 0x3:
				switch(funct3) {
				case 0x0: //LB -load byte
					if(imm12<0) { //negative offset
						imm12=(imm12 + 0xFFFFF000);
						if(memory[reg[res1]+imm12]<0) { //negative value in memory
							reg[rd]=((memory[reg[res1]+imm12])& 0xFF) + 0xFFFFFF00;
						}
						else {//positive value in memory
							reg[rd]=((memory[reg[res1]+imm12])& 0xFF);
						}
						
					}
					else { //positive offset
						if(memory[reg[res1]+imm12]<0) { //negative value in memory
							reg[rd]=((memory[reg[res1]+imm12])& 0xFF) + 0xFFFFFF00;
						}
						else {//positive value in memory
							reg[rd]=((memory[reg[res1]+imm12])& 0xFF);
						}
					}
					break;
					
					
				case 0x1: //LH -load hexa
					if(imm12<0) { //negative offset
						imm12=(imm12 + 0xFFFFF000);
					}
					reg[rd]=((int)memory[reg[res1]+imm12+1]<<8) + (((int)memory[reg[res1]+imm12])& 0xFF);
					
				break;
				
				case 0x2: //LW -load word
					if(imm12<0) { //negative offset
						imm12=(imm12 + 0xFFFFF000);
					}
					reg[rd]= (((int)memory[reg[res1]+imm12+3]<<24))+
							(((int)memory[reg[res1]+imm12+2]<<16)&0xFFFFFF)+
							(((int)memory[reg[res1]+imm12+1]<<8)&0xFFFF) + 
							(((int)memory[reg[res1]+imm12])& 0xFF);
					
				break;
				
				case 0x4: //LBU -load byte unsigned
					if(imm12<0) { //negative offset
						imm12=(imm12 + 0xFFFFF000);
					}
					reg[rd]= (((int)memory[reg[res1]+imm12])& 0xFF);
					
				break;
				
				case 0x5: //LHU -load hexa unsigned 
					if(imm12<0) { //negative offset
						imm12=(imm12 + 0xFFFFF000);
					}
					reg[rd]= (((int)memory[reg[res1]+imm12+1]<<8)&0xFFFF) + 
							(((int)memory[reg[res1]+imm12])& 0xFF);
					
				break;
					
				}
				
			break;	
				
			case 0x23:
				switch(funct3) {
				case 0x0: //SB -save byte
					imm12=(imm7<<5)+imm5;
					if(imm12<0) { //negative offset
						imm12 = (imm12 + 0xFFFFF000);	
					}
					memory[(reg[res1]+imm12)]=(byte) (reg[res2] & 0xFF);
					System.out.println(memory[reg[res1]+imm12]);
				break;
				
				case 0x1: //SH -save hexa
					imm12=(imm7<<5)+imm5;
					if(imm12<0) { //negative offset
						imm12=(imm12 + 0xFFFFF000);	
					}
					memory[reg[res1]+imm12]=(byte)(reg[res2] & 0xFF);
					memory[reg[res1]+imm12+1]=(byte)((reg[res2] >> 8) & 0xFF);	
//					System.out.println("+0: "+ memory[reg[res1]+imm12]);
//					System.out.println("+1: "+ memory[reg[res1]+imm12+1]);
				break;
				
				case 0x2: //SW -save word
					imm12=(imm7<<5)+imm5;
					if(imm12<0) { //negative offset
						imm12=(imm12 + 0xFFFFF000);
					}
					memory[reg[res1]+imm12]=(byte)(reg[res2] & 0xFF);
					memory[reg[res1]+imm12+1]=(byte)((reg[res2] >> 8) & 0xFF);	
					memory[reg[res1]+imm12+2]=(byte)((reg[res2] >> 16) & 0xFF);	
					memory[reg[res1]+imm12+3]=(byte)((reg[res2] >> 24) & 0xFF);	
					
//					System.out.println("+0: "+ memory[reg[res1]+imm12]);
//					System.out.println("+1: "+ memory[reg[res1]+imm12+1]);
//					System.out.println("+2: "+ memory[reg[res1]+imm12+2]);
//					System.out.println("+3: "+ memory[reg[res1]+imm12+3]);
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
				else if(reg[10]==10) {//exit program
					PC.pc= progr.length*5;
				}
				else if(reg[10]==11) { //prints ASCII character in a1
					System.out.println(Character.toString ((char) reg[11]));
				}
				else if(reg[10]==17) { //ends the program with return code in a1
					System.out.print(reg[11]);
					PC.pc= progr.length*5;
				}
				else {//not recognized ID in a0
					System.out.print("ecall " +reg[10] + " is not valid ");
				}
				break;


			default:
				System.out.println("Opcode " + opcode + " not yet implemented");
				break;
			}
			if(!jumpe) {
			PC.nextInstruction();
			}
			
			if (PC.pc/4 >= progr.length) {
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
