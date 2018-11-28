package RV32I;

public class programCounter {
	int pc;
	
	programCounter(){
		pc=0;
	}
	
	 public void nextInstruction() {
		 pc+=4;
	 }
	 
	 public void jal(int i, int s) {//negative case where sign extension is need for offset
		 if (s==1) {
			 pc=(0xFFE00000 + i) + pc;
			 System.out.println("pc: " + (0xFFE00000 + i));
		 }
		 else { //positive case
			 pc=i+pc;
		 }

	 }

}
