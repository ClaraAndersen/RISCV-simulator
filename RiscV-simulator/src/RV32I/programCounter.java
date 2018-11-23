package RV32I;

public class programCounter {
	static int pc;
	
	programCounter(){
		pc=0;
	}
	
	 public static void nextInstruction() {
		 pc+=1;
	 }
	 public static void jump(int i) {//negative case where sign extension is need for offset
		 if (i>>11==1) {
			 pc=(0xFFFFF000 + i) + pc;
		 }
		 else { //positive case
			 pc=i+pc;
		 }
	 }

}
