package RV32I;

public class programCounter {
	int pc;
	
	programCounter(){
		pc=0;
	}
	
	 public void nextInstruction() {
		 pc+=4;
	 }
	 
	 public void jal(int i) {//negative case where sign extension is need for offset
		 if (i>>11==1) {
			 pc=(0xFFFFF000 + i) + pc;
		 }
		 else { //positive case
			 pc=i+pc;
		 }

	 }

}
