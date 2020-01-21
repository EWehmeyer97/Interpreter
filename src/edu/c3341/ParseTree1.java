package edu.c3341;

/**
 * Project 3 for CSE 3341. Provides an implementation for methods needed to create a ParseTree for the Core language.
 *
 * @author Evan Wehmeyer
 *
 */

public class ParseTree1 implements ParseTree {

	private int[][] prog = new int[1000][6]; //holds each statement 
	//0 :: Non-Terminal, 1 :: Parent Position, 2 :: Alt No., 3 :: Left Child (or idLocation, or NoVal), 4 :: Middle Child, 5 :: Right Child
	private int pos = 0; //Looks at current position within ParseTree
	private int unFill = 1; //Looks at current unfilled place in array
	private String[] idNames = new String[20];
	private int[] idVals = new int[20];
	private boolean[] idSet = new boolean[20];
	private int idPos = 0;
	private static ParseTree1 instance;
	
	
	public static ParseTree create() {
		instance = new ParseTree1() {};
		return instance;
	}
	
	public static ParseTree instance() {
		return instance;
	}
	
	@Override
	public void goDownLB() {
		if (this.prog[this.pos][0] != 18 && this.prog[this.pos][0] != 20) { //add an error message????
			this.pos = this.prog[this.pos][3];
		}
	}
	@Override
	public void goDownMB() {
		this.pos = this.prog[this.pos][4];
		
	}
	@Override
	public void goDownRB() {
		this.pos = this.prog[this.pos][5];
		
	}
	@Override
	public void goUp() {
		this.pos = this.prog[this.pos][1];
		
	}
	@Override
	public int currentNTNo() {
		return this.prog[this.pos][0];
	}
	@Override
	public int currentAlt() {
		return this.prog[this.pos][2];
	}
	@Override
	public String idName() {
		return this.idNames[this.prog[this.pos][3]];
	}
	@Override
	public void addId(String x) { //Does it need to check against existing names???
		this.idNames[this.idPos] = x;
		this.prog[this.pos][3] = this.idPos;
		this.idPos++;
	}
	@Override
	public boolean findId(String x) {
		boolean b = false;
		for(int i = 0; i < this.idPos; i++) {
			if(x.compareTo(this.idNames[i]) == 0) {
				this.prog[this.pos][3] = i;
				b = true;
			}
		}
		return b;
	}
	@Override
	public int currIdVal() { //Should I make a boolean[] to ensure that all values have been set before??? + Undeclared Variables???
		if (!this.idSet[this.prog[this.pos][3]]) {
			System.err.println("ERROR: VARIABLE NOT YET INITIALIZED!");
			System.exit(0);
		}
			return this.idVals[this.prog[this.pos][3]];
	}
	@Override
	public void setIdVal(int x) {
		if(this.prog[this.pos][0] == 18) {
			this.idVals[this.prog[this.pos][3]] = x;
			this.idSet[this.prog[this.pos][3]] = true;
		}
	}
	@Override
	public void setNoVal(int x) {
		if(this.prog[this.pos][0] == 20) {
			this.prog[this.pos][3] = x;
		}
	}
	@Override
	public int currNoVal() {
		if(this.prog[this.pos][0] == 20) {
			return this.prog[this.pos][3];
		}
		return -1; //needs an error!!
	}
	@Override
	public void createLB() {
		this.prog[this.pos][3] = this.unFill;
		this.prog[this.unFill][1] = this.pos;
		this.unFill++;
	}

	@Override
	public void createMB() {
		this.prog[this.pos][4] = this.unFill;
		this.prog[this.unFill][1] = this.pos;
		this.unFill++;
	}

	@Override
	public void createRB() {
		this.prog[this.pos][5] = this.unFill;
		this.prog[this.unFill][1] = this.pos;
		this.unFill++;
	}

	@Override
	public void setNTNo(int x) {
		this.prog[this.pos][0] = x;
	}

	@Override
	public void setAlt(int x) {
		this.prog[this.pos][2] = x;
	}
}
