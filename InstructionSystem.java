package instruction;

import java.io.*;
import java.util.regex.Pattern;

public class InstructionSystem {
	int[] registers = new int[33];
	int[] memory = new int[16];
	int pc =0;
	
	String[] readFile(String filename) {
		File file = new File(filename);
		String[] lines = new String[25];
		FileReader fr = null;
		BufferedReader br= null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String lineTXT = null;
			int i = 0;
			while((lineTXT = br.readLine())!=null) {
				lines[i] = lineTXT;
				i++;
			}
			fr.close();
			br.close();
		} 
		catch (IOException e2) {
			e2.printStackTrace();
		} 
		return lines;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InstructionSystem is = new InstructionSystem();
		String[] list = null;
		list = is.readFile("C:\\javacode\\instruction\\src\\instruction\\string.txt");
		
		String key ="#[code]";
		int dataStart = 1;
		int dataOver = 0;
		for(int i = 0;i<list.length;i++) {
			if(list[i]!=null&&list[i].equals(key)){
				dataOver = i-1;
			}
		}
		for(int i = dataStart;i<=dataOver;i++) {
			String[] sDate = list[i].split("=");
			
			int a = (i-1)*4;
			is.memory[a] = Integer.parseInt(sDate[1]);
		}
		
		String instruction1 = "li";
		String instruction2 = "lw";
		String instruction3 = "LOOP:";
		String instruction4 = "lt";
		String instruction5 = "bne";
		String instruction6 = "add";
		String instruction7 = "sw";
		String instruction8 = "jmp";
		int LOOp=0;
		
		for(int i = dataOver+2;i<list.length;i++) {
			
			System.out.println(i+" "+list[i]);
			
			String[]  sInstruction = list[i].split("\\s+");
			if(sInstruction[0].equals(instruction1)){
				int arg2 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[2]).replaceAll("").trim());
				int arg3 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[3]).replaceAll("").trim());
				is.registers[arg2] = arg3;
				//li	001000	rs	rt	imm	rt=imm
			}else if(sInstruction[0].equals(instruction2)){
				int arg1 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[1]).replaceAll("").trim());
				int arg2 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[2]).replaceAll("").trim());
				int arg3 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[3]).replaceAll("").trim());
				is.registers[arg2] = is.memory[is.registers[arg1] +arg3];
				//lw	000101	rs	rt	imm	rt=M(rs+imm)
			}else if(sInstruction[0].equals(instruction3)){
				LOOp = i;
				//LOOP
			}else if(sInstruction[0].equals(instruction4)){
				int arg1 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[1]).replaceAll("").trim());
				int arg2 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[2]).replaceAll("").trim());
				int arg3 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[3]).replaceAll("").trim());
				if(is.registers[arg1]<=is.registers[arg2]) {
					is.registers[arg3] = 1;
				}else {
					is.registers[arg3] = 0;
				}
				//lt	000001	rs	rt	rd	00000	100100	if(rs<=rt)rd=1 else rd=0
			}else if(sInstruction[0].equals(instruction5)){
				int arg1 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[1]).replaceAll("").trim());
				int arg2 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[2]).replaceAll("").trim());
				int arg3 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[3]).replaceAll("").trim());
				if(is.registers[arg1]!=is.registers[arg2]) {
					is.pc = arg3;
				}
				//bne	001010	rs	rt	imm	If(rs!=rt)  PC=imm
			}else if(sInstruction[0].equals(instruction6)){
				int arg1 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[1]).replaceAll("").trim());
				int arg2 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[2]).replaceAll("").trim());
				int arg3 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[3]).replaceAll("").trim());
				is.registers[arg3] = is.registers[arg1] + is.registers[arg2];
				//add	000001	rs	rt	rd	00000	100000	rd=rs+rt
			}else if(sInstruction[0].equals(instruction7)){
				int arg1 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[1]).replaceAll("").trim());
				int arg2 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[2]).replaceAll("").trim());
				int arg3 = Integer.parseInt(Pattern.compile("[^0-9]").matcher(sInstruction[3]).replaceAll("").trim());
				is.memory[is.registers[arg1]+arg3] = is.registers[arg2];
				//sw	000110	rs	rt	imm	M(rs+imm)=rt
			}else if(sInstruction[0].equals(instruction8)){
				i = is.pc + LOOp;
				//jmp	010001	address	PC+=address
				//JMP指令中规定了地址是25位，不能实现32位地址的任意跳转。

				//打印斐波那契数列
				//if(is.memory[10]!=0) {System.out.print(is.memory[10]+" ");}
				//1 1 2 3 5 8 13 21 
			}
		}
	}
}
