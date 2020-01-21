package edu.c3341;

import static edu.c3341.TokenKind.ERROR;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Project 3 for CSE 3341. Test an Interpreter of Core.
 *
 * @author Evan Wehmeyer
 *
 */
public final class Interpreter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Interpreter() {
    
    }
    static int tabCount = 0;
    
    private static void parseProg(ParseTree p, Tokenizer t) { //DONE
		skipSafe(t, TokenKind.PROGRAM_WORD);
		p.setNTNo(1);
		p.setAlt(1);
		
		p.createLB();
		p.goDownLB();
		parseDeclSeq(p, t);
		p.goUp();
		
		skipSafe(t, TokenKind.BEGIN_WORD);
				
		p.createMB();
		p.goDownMB();
		parseStmtSeq(p, t);
		p.goUp();
				
		skipSafe(t, TokenKind.END_WORD);
	}
	
	private static void skipSafe(Tokenizer t, TokenKind x) {
		if (t.getToken() == x) {
			t.skipToken();
		} else {
			System.err.println("ERROR: ENCOUNTERED INVALID TOKEN");
			System.exit(0);
		}
	}

	private static void parseDeclSeq(ParseTree p, Tokenizer t) { //DONE
		if(t.getToken() == TokenKind.INT_WORD) {
			p.setNTNo(2);
			
			p.createLB();
			p.goDownLB();
			parseDecl(p, t);
			p.goUp();
			
			if(t.getToken() == TokenKind.INT_WORD) {
				p.setAlt(2);
				
				p.createMB();
				p.goDownMB();
				parseDeclSeq(p, t);
				p.goUp();
			} else {
				p.setAlt(1);
			}
		} else {
			System.err.println("ERROR: EXPECTED KEYWORD int");
			System.exit(0);
		}
	}
	
	private static void parseStmtSeq(ParseTree p, Tokenizer t) { //DONE
		TokenKind r = t.getToken();
		if(r == TokenKind.IF_WORD || r == TokenKind.IDENTIFIER || r == TokenKind.WHILE_WORD || r == TokenKind.READ_WORD || r == TokenKind.WRITE_WORD) {
			p.setNTNo(3);
			
			p.createLB();
			p.goDownLB();
			parseStmt(p, t);
			p.goUp();
			
			r = t.getToken();
			if (r == TokenKind.IF_WORD || r == TokenKind.IDENTIFIER || r == TokenKind.WHILE_WORD || r == TokenKind.READ_WORD || r == TokenKind.WRITE_WORD) {
				p.setAlt(2);
				
				p.createMB();
				p.goDownMB();
				parseStmtSeq(p, t);
				p.goUp();
			} else {
				p.setAlt(1);
			}
		} else {
			System.err.println("ERROR: EXPECTED KEYWORD FOR STATEMENT");
			System.exit(0);
		}
	}
	
	private static void parseDecl(ParseTree p, Tokenizer t) { //DONE
			t.skipToken(); //INT
			p.setNTNo(4);
			
			p.createLB();
			p.goDownLB();
			parseIdListDecl(p, t);
			p.goUp();
			
			skipSafe(t, TokenKind.SEMICOLON);
	}
	
	private static void parseIdListDecl(ParseTree p, Tokenizer t) { //DONE
		if(t.getToken() == TokenKind.IDENTIFIER) {
			p.setNTNo(5);
			
			p.createLB();
			p.goDownLB();
			parseIdDecl(p, t);
			p.goUp();
			
			if(t.getToken() == TokenKind.COMMA) {
				t.skipToken(); //,
				p.setAlt(2);
				
				p.createMB();
				p.goDownMB();
				parseIdListDecl(p, t);
				p.goUp();
			} else {
				p.setAlt(1);
			}
		} else {
			System.err.println("ERROR: EXPECTED IDENTIFIER");
			System.exit(0);
		}
	}
	
	private static void parseIdListStmt(ParseTree p, Tokenizer t) { //DONE
		if(t.getToken() == TokenKind.IDENTIFIER) {
			p.setNTNo(5);
			
			p.createLB();
			p.goDownLB();
			parseIdStmt(p, t);
			p.goUp();
			
			if(t.getToken() == TokenKind.COMMA) {
				t.skipToken(); //,
				p.setAlt(2);
				
				p.createMB();
				p.goDownMB();
				parseIdListStmt(p, t);
				p.goUp();
			} else {
				p.setAlt(1);
			}
		} else {
			System.err.println("ERROR: EXPECTED IDENTIFIER");
			System.exit(0);
		}
	}
	
	private static void parseStmt(ParseTree p, Tokenizer t) { //DONE
		TokenKind r = t.getToken();
		
		p.setNTNo(6);
			
		if(r == TokenKind.IDENTIFIER) {
			p.setAlt(1);
			p.createLB();
			p.goDownLB();
			parseAssign(p, t);
		} 
		else if(r == TokenKind.IF_WORD) {
			p.setAlt(2);
			p.createLB();
			p.goDownLB();
			parseIf(p, t);
		} 
		else if(r == TokenKind.WHILE_WORD) {
			p.setAlt(3);
			p.createLB();
			p.goDownLB();
			parseLoop(p, t);
		} 
		else if(r == TokenKind.READ_WORD) {
			p.setAlt(4);
			p.createLB();
			p.goDownLB();
			parseIn(p, t);
		} 
		else if(r == TokenKind.WRITE_WORD){
			p.setAlt(5);
			p.createLB();
			p.goDownLB();
			parseOut(p, t);
		}
		p.goUp();
	}
	
	private static void parseAssign(ParseTree p, Tokenizer t) { //DONE - MAYBE? CHECK
		p.setNTNo(7);
		p.setAlt(1);
		
		p.createLB();
		p.goDownLB();
		parseIdStmt(p, t);
		p.goUp();
		
		skipSafe(t, TokenKind.ASSIGNMENT_OPERATOR);
		
		p.createMB();
		p.goDownMB();
		parseExp(p, t);
		p.goUp();
		
		skipSafe(t, TokenKind.SEMICOLON);
	}
	
	private static void parseIf(ParseTree p, Tokenizer t) { //DONE
		t.skipToken();
		p.setNTNo(8);
		
		p.createLB();
		p.goDownLB();
		parseCond(p, t);
		p.goUp();
		
		skipSafe(t, TokenKind.THEN_WORD);
			
		p.createMB();
		p.goDownMB();
		parseStmtSeq(p, t);
		p.goUp();
		
		if(t.getToken() == TokenKind.ELSE_WORD) {
			t.skipToken(); //else
			p.setAlt(2);
			
			p.createRB();
			p.goDownRB();
			parseStmtSeq(p, t);
			p.goUp();
		} else {
			p.setAlt(1);
		}
		skipSafe(t, TokenKind.END_WORD);
		skipSafe(t, TokenKind.SEMICOLON);
	}
	
	private static void parseLoop(ParseTree p, Tokenizer t) { //DONE - MAYBE? CHECK
		t.skipToken(); //while
		p.setNTNo(9);
		
		p.createLB();
		p.goDownLB();
		parseCond(p, t);
		p.goUp();
		
		skipSafe(t, TokenKind.LOOP_WORD);
		
		p.createMB();
		p.goDownMB();
		parseStmtSeq(p, t);
		p.goUp();
		
		skipSafe(t, TokenKind.END_WORD);
		skipSafe(t, TokenKind.SEMICOLON);
	}
	
	private static void parseIn(ParseTree p, Tokenizer t) { //DONE - MAYBE? CHECK
		t.skipToken(); //read
		p.setNTNo(10);
		
		p.createLB();
		p.goDownLB();
		parseIdListStmt(p, t);
		p.goUp();
		
		skipSafe(t, TokenKind.SEMICOLON);
		p.setAlt(1);
	}
	
	private static void parseOut(ParseTree p, Tokenizer t) { //DONE - MAYBE? CHECK
		t.skipToken(); //WRITE
		p.setNTNo(11);
		
		p.createLB();
		p.goDownLB();
		parseIdListStmt(p, t);
		p.goUp();
		
		skipSafe(t, TokenKind.SEMICOLON);
		p.setAlt(1);
	}
	
	private static void parseCond(ParseTree p, Tokenizer t) { //DONE - MAYBE? CHECK
		p.setNTNo(12);
		
		p.createLB();
		p.goDownLB();
		TokenKind r = t.getToken();
		if (r == TokenKind.LEFT_PARENTHESIS) {
			parseComp(p, t);
			p.goUp();
			p.setAlt(1);
		} else if (r == TokenKind.NOT) {
			t.skipToken(); //!
			parseCond(p, t);
			p.goUp();
			p.setAlt(2);
		} else if (r == TokenKind.LEFT_BRACKET) {
			t.skipToken(); //[
			parseCond(p, t);
			p.goUp();
			r = t.getToken();
			if (r == TokenKind.AND) {
				t.skipToken(); //&&
				p.createMB();
				p.goDownMB();
				parseCond(p, t);
				p.goUp();
				p.setAlt(3);
			} else if (r == TokenKind.OR_OPERATOR) {
				t.skipToken(); //or
				p.createMB();
				p.goDownMB();
				parseCond(p, t);
				p.goUp();
				p.setAlt(4);
			}
			skipSafe(t, TokenKind.RIGHT_BRACKET);
		}		
	}
	
	private static void parseComp(ParseTree p, Tokenizer t) { //DONE - MAYBE? CHECK
		t.skipToken(); //(
		p.setNTNo(13);
		
		p.createLB();
		p.goDownLB();
		parseOp(p, t);
		p.goUp();
		
		p.createMB();
		p.goDownMB();
		parseCompOp(p, t);
		p.goUp();
		
		p.createRB();
		p.goDownRB();
		parseOp(p, t);
		p.goUp();
		
		p.setAlt(1);
		skipSafe(t, TokenKind.RIGHT_PARENTHESIS);
	}
	
	private static void parseExp(ParseTree p, Tokenizer t) { //DONE - MAYBE? CHECK
		p.setNTNo(14);
		
		p.createLB();
		p.goDownLB();
		parseTrm(p, t);
		p.goUp();
		
		TokenKind r = t.getToken();
		if(r == TokenKind.ADD_OPERATOR) {
			t.skipToken(); //+
			p.createMB();
			p.goDownMB();
			parseExp(p, t);
			p.goUp();
			p.setAlt(2);
		} else if(r == TokenKind.SUBTRACT_OPERATOR) {
			t.skipToken(); //-
			p.createMB();
			p.goDownMB();
			parseExp(p, t);
			p.goUp();
			p.setAlt(3);
		} else {
			p.setAlt(1);
		}
	}
	
	private static void parseTrm(ParseTree p, Tokenizer t) { //DONE - MAYBE? CHECK
		p.setNTNo(15);
		
		p.createLB();
		p.goDownLB();
		parseOp(p, t);
		p.goUp();
		
		TokenKind r = t.getToken();
		if(r == TokenKind.MULTIPLY_OPERATOR) {
			t.skipToken(); //*
			p.createMB();
			p.goDownMB();
			parseTrm(p, t);
			p.goUp();
			p.setAlt(2);
		} else {
			p.setAlt(1);
		}
	}
	
	private static void parseOp(ParseTree p, Tokenizer t) { //DONE - MAYBE? CHECK
		p.setNTNo(16);
		TokenKind r = t.getToken();
		p.createLB();
		p.goDownLB();
		
		
		if(r == TokenKind.INTEGER_CONSTANT) {
			parseNo(p, t);
			p.goUp();
			p.setAlt(1);
		} else if(r == TokenKind.IDENTIFIER) {
			parseIdStmt(p, t);
			p.goUp();
			p.setAlt(2);
		} else if(r == TokenKind.LEFT_PARENTHESIS){
			t.skipToken(); //(
			parseExp(p, t);
			skipSafe(t, TokenKind.RIGHT_PARENTHESIS);
			p.setAlt(3);
		}
	}
	
	private static void parseCompOp(ParseTree p, Tokenizer t) {
		TokenKind r = t.getToken();
		p.setNTNo(17);
		if (r == TokenKind.NON_EQUALITY_TEST) {
			t.skipToken();
			p.setAlt(1);
		} else if (r == TokenKind.EQUALITY_TEST) {
			t.skipToken();
			p.setAlt(2);
		} else if (r == TokenKind.LESS_THAN_TEST) {
			t.skipToken();
			p.setAlt(3);
		} else if (r == TokenKind.GREATER_THAN_TEST) {
			t.skipToken();
			p.setAlt(4);
		} else if (r == TokenKind.EQUAL_OR_LESS_TEST) {
			t.skipToken();
			p.setAlt(5);
		} else if (r == TokenKind.EQUAL_OR_GREATER_TEST) {
			t.skipToken();
			p.setAlt(6);
		}
	}
	
	private static void parseIdDecl(ParseTree p, Tokenizer t) { //add ID to an array of identifiers??
		p.setAlt(1);
		p.setNTNo(18);
		p.addId(t.idName());
		
		t.skipToken(); //IDENTIFIER
	}
	
	private static void parseIdStmt(ParseTree p, Tokenizer t) {
		p.setNTNo(18);
		p.setAlt(1);
		boolean b = p.findId(t.idName());
		if (!b) {
			System.err.println("ERROR: IDENTIFIER "+ t.idName() +" NOT FOUND IN DECLARATION!");
			System.exit(0);
		}
		t.skipToken(); //IDENTIFIER
	}
	
	private static void parseNo(ParseTree p, Tokenizer t) {
		if(t.getToken() == TokenKind.INTEGER_CONSTANT) {
			p.setNTNo(20);
			p.setNoVal(t.intVal());
			p.setAlt(1);
			
			t.skipToken(); //INTEGER
		} else {
			System.err.println("ERROR: EXPECTED AN INTEGER");
			System.exit(0);
		}
	}
	
	
	static void printProg(ParseTree p) {
		System.out.println("program");
		
		p.goDownLB();
		printDeclSeq(p);
		p.goUp();
		
		System.out.println("begin");
		
		p.goDownMB();
		tabCount++;
		printStmtSeq(p);
		p.goUp();
		
		System.out.println("end");
	}
	
	static void printDeclSeq(ParseTree p) {
		p.goDownLB();
		printDecl(p);
		p.goUp();
		
		if(p.currentAlt() == 2) {
			p.goDownMB();
			printDeclSeq(p);
			p.goUp();
		}
	}
	
	static void printStmtSeq(ParseTree p) {
		p.goDownLB();
		printStmt(p);
		p.goUp();
		
		if(p.currentAlt() == 2) {
			p.goDownMB();
			printStmtSeq(p);
			p.goUp();
		}
	}
	
	static void printDecl(ParseTree p) {
		System.out.print("	int ");
		
		p.goDownLB();
		printIdList(p);
		p.goUp();
		
		System.out.println(";");
	}
	
	static void printIdList(ParseTree p) {
		p.goDownLB();
		printId(p);
		p.goUp();
		
		if(p.currentAlt() == 2) {
			System.out.print(", ");
			p.goDownMB();
			printIdList(p);
			p.goUp();
		}
	}
	
	static void printStmt(ParseTree p) {
		printTabs();
		if(p.currentAlt() == 1) {
			p.goDownLB();
			printAssign(p);
			p.goUp();
		} else if(p.currentAlt() == 2) {
			p.goDownLB();
			printIf(p);
			p.goUp();
		} else if(p.currentAlt() == 3) {
			p.goDownLB();
			printLoop(p);
			p.goUp();
		} else if(p.currentAlt() == 4) {
			p.goDownLB();
			printIn(p);
			p.goUp();
		} else if(p.currentAlt() == 5) {
			p.goDownLB();
			printOut(p);
			p.goUp();
		}
	}
	
	static void printTabs() {
		for(int i = 0; i < tabCount; i++) { System.out.print("\t");}
	}

	static void printAssign(ParseTree p) {
		p.goDownLB();
		printId(p);
		p.goUp();
	
		System.out.print(" = ");
		
		p.goDownMB();
		printExp(p);
		p.goUp();
		
		System.out.println(";");
	}
	
	static void printIf(ParseTree p) {
		System.out.print("if ");
		
		p.goDownLB();
		printCond(p);
		p.goUp();
	
		System.out.println(" then");
		
		p.goDownMB();
		tabCount++;
		printStmtSeq(p);
		tabCount--;
		p.goUp();
		
		if(p.currentAlt() == 2) {
			printTabs();
			System.out.println("else");
			
			p.goDownRB();
			tabCount++;
			printStmtSeq(p);
			tabCount--;
			p.goUp();
		}
		
		printTabs();
		System.out.println("end;");
	}
	
	static void printLoop(ParseTree p) {
		System.out.print("while ");
		
		p.goDownLB();
		printCond(p);
		p.goUp();
	
		System.out.println(" loop");
		
		p.goDownMB();
		tabCount++;
		printStmtSeq(p);
		tabCount--;
		p.goUp();
		
		printTabs();
		System.out.println("end;");
	}
	
	static void printIn(ParseTree p) {
		System.out.print("read ");
		
		p.goDownLB();
		printIdList(p);
		p.goUp();
	
		System.out.println(";");
	}
	
	static void printOut(ParseTree p) {
		System.out.print("write ");
		
		p.goDownLB();
		printIdList(p);
		p.goUp();
	
		System.out.println(";");
	}
	
	static void printCond(ParseTree p) {
		if(p.currentAlt() == 1) {
			p.goDownLB();
			printComp(p);
			p.goUp();
		} else if (p.currentAlt() == 2) {
			System.out.print("!");
		
			p.goDownLB();
			printCond(p);
			p.goUp();
		} else if (p.currentAlt() == 3) {
			System.out.print("[");
		
			p.goDownLB();
			printCond(p);
			p.goUp();
			
			System.out.print("] && [");
			
			p.goDownMB();
			printCond(p);
			p.goUp();
			
			System.out.print("]");
		} else if (p.currentAlt() == 4) {
			System.out.print("[");
			
			p.goDownLB();
			printCond(p);
			p.goUp();
			
			
			System.out.print("] || [");
			
			p.goDownMB();
			printCond(p);
			p.goUp();
			
			System.out.print("]");
		}
	}
	
	static void printComp(ParseTree p) {
		System.out.print("(");
		
		p.goDownLB();
		printOp(p);
		p.goUp();
		
		System.out.print(" ");
		
		p.goDownMB();
		printCompOp(p);
		p.goUp();
		
		System.out.print(" ");
		
		p.goDownRB();
		printOp(p);
		p.goUp();
	
		System.out.print(")");
	}
	
	static void printExp(ParseTree p) {
		p.goDownLB();
		printTrm(p);
		p.goUp();
		
		if (p.currentAlt() == 2) {
			System.out.print(" + ");
		
			p.goDownMB();
			printExp(p);
			p.goUp();
		} else if (p.currentAlt() == 3) {
			System.out.print(" - ");
		
			p.goDownMB();
			printExp(p);
			p.goUp();
		}
	}
	
	static void printTrm(ParseTree p) {
		p.goDownLB();
		printOp(p);
		p.goUp();
		
		if (p.currentAlt() == 2) {
			System.out.print(" * ");
		
			p.goDownMB();
			printTrm(p);
			p.goUp();
		}
	}
	
	static void printOp(ParseTree p) {
		if (p.currentAlt() == 1) {
			p.goDownLB();
			printNo(p);
			p.goUp();
		} else if (p.currentAlt() == 2) {
			p.goDownLB();
			printId(p);
			p.goUp();
		} else if (p.currentAlt() == 3) {
			System.out.print("(");
		
			p.goDownLB();
			printExp(p);
			p.goUp();
			
			System.out.print(")");
		}
	}
	
	static void printCompOp(ParseTree p) {
		if (p.currentAlt() == 1) {
			System.out.print("!=");
		} else if (p.currentAlt() == 2) {
			System.out.print("==");
		} else if (p.currentAlt() == 3) {
			System.out.print("<");
		} else if (p.currentAlt() == 4) {
			System.out.print(">");
		} else if (p.currentAlt() == 5) {
			System.out.print("<=");
		}  else if (p.currentAlt() == 6) {
			System.out.print(">=");
		}
	}
	
	static void printId(ParseTree p) {
		System.out.print(p.idName());
	}
	
	static void printNo(ParseTree p) {
		System.out.print(Integer.toString(p.currNoVal()));
	}
	
	static void execProg(ParseTree p, Scanner x) {
		p.goDownMB();
		execStmtSeq(p, x);
		p.goUp();
	}

	private static void execStmtSeq(ParseTree p, Scanner x) {
		p.goDownLB();
		execStmt(p, x);
		p.goUp();
		
		if(p.currentAlt() == 2) {
			p.goDownMB();
			execStmtSeq(p, x);
			p.goUp();
		}
	}

	private static void execStmt(ParseTree p, Scanner x) {
		if(p.currentAlt() == 1) {
			p.goDownLB();
			execAssign(p);
			p.goUp();
		} else if(p.currentAlt() == 2) {
			p.goDownLB();
			execIf(p, x);
			p.goUp();
		} else if(p.currentAlt() == 3) {
			p.goDownLB();
			execLoop(p, x);
			p.goUp();
		} else if(p.currentAlt() == 4) {
			p.goDownLB();
			execIn(p, x);
			p.goUp();
		} else if(p.currentAlt() == 5) {
			p.goDownLB();
			execOut(p);
			p.goUp();
		}
	}

	private static void execAssign(ParseTree p) {
		p.goDownMB();
		int y = evalExp(p);
		p.goUp();
		
		p.goDownLB();
		p.setIdVal(y);
		p.goUp();
	}

	private static void execIf(ParseTree p, Scanner x) {
		p.goDownLB();
		boolean b = evalCond(p);
		p.goUp();
		
		if(b) {
			p.goDownMB();
			execStmtSeq(p, x);
			p.goUp();
		} else {
			if (p.currentAlt() == 2) {
				p.goDownRB();
				execStmtSeq(p, x);
				p.goUp();
			}
		}
		
	}

	

	private static void execLoop(ParseTree p, Scanner x) {
		p.goDownLB();
		boolean b = evalCond(p);
		p.goUp();
		
		while(b) {
			p.goDownMB();
			execStmtSeq(p, x);
			p.goUp();
			
			p.goDownLB();
			b = evalCond(p);
			p.goUp();
		}
		
	}

	private static void execIn(ParseTree p, Scanner x) {
		p.goDownLB();
		readIds(p, x);
		p.goUp();
	}

	private static void readIds(ParseTree p, Scanner x) {
		p.goDownLB();
		p.setIdVal(x.nextInt());
		p.goUp();
		
		if(p.currentAlt() == 2) {
			p.goDownMB();
			readIds(p, x);
			p.goUp();
		}
	}

	private static void execOut(ParseTree p) {
		p.goDownLB();
		writeIds(p);
		p.goUp();
		
	}
	
	private static void writeIds(ParseTree p) {
		p.goDownLB();
		System.out.println(p.idName() + " = " + p.currIdVal());
		p.goUp();
		
		if(p.currentAlt() == 2) {
			p.goDownMB();
			writeIds(p);
			p.goUp();
		}
		
	}
	
	private static boolean evalCond(ParseTree p) {
		boolean b = false;
		if(p.currentAlt() == 1) {
			p.goDownLB();
			b = evalComp(p);
			p.goUp();
		} else if(p.currentAlt() == 2) {
			p.goDownLB();
			b = !(evalCond(p));
			p.goUp();
		} else if(p.currentAlt() == 3) {
			p.goDownLB();
			boolean a = evalCond(p);
			p.goUp();
			
			p.goDownMB();
			boolean c = evalCond(p);
			p.goUp();
			
			b = a && c;
		} else if(p.currentAlt() == 4) {
			p.goDownLB();
			boolean a = evalCond(p);
			p.goUp();
			
			p.goDownMB();
			boolean c = evalCond(p);
			p.goUp();
			
			b = a || c;
		}
		return b;
	}
	
	private static boolean evalComp(ParseTree p) {
		p.goDownLB();
		int a = evalOp(p);
		p.goUp();
		
		p.goDownRB();
		int c = evalOp(p);
		p.goUp();
		
		boolean b = false;
		
		p.goDownMB();
		if (p.currentAlt() == 1) {
			b = a != c;
		} else if (p.currentAlt() == 2) {
			b = a == c;
		} else if (p.currentAlt() == 3) {
			b = a < c;
		} else if (p.currentAlt() == 4) {
			b = a > c;
		} else if (p.currentAlt() == 5) {
			b = a <= c;
		} else if (p.currentAlt() == 6) {
			b = a >= c;
		}
		
		p.goUp();
		
		return b;
	}

	private static int evalExp(ParseTree p) {
		p.goDownLB();
		int a = evalTrm(p);
		p.goUp();
		
		if (p.currentAlt() == 2) {
			p.goDownMB();
			a += evalExp(p);
			p.goUp();
		} else if (p.currentAlt() == 3) {
			p.goDownMB();
			a -= evalExp(p);
			p.goUp();
		}
		return a;
	}
	
	private static int evalTrm(ParseTree p) {
		p.goDownLB();
		int a = evalOp(p);
		p.goUp();
		
		if (p.currentAlt() == 2) {
			p.goDownMB();
			a *= evalTrm(p);
			p.goUp();
		}
		return a;
	}

	private static int evalOp(ParseTree p) {
		int a = 0;
		if (p.currentAlt() == 1) {
			p.goDownLB();
			a = p.currNoVal();
			p.goUp();
		} else if (p.currentAlt() == 2) {
			p.goDownLB();
			a = p.currIdVal();
			p.goUp();
		} else if (p.currentAlt() == 3) {
			p.goDownLB();
			a = evalExp(p);
			p.goUp();
		}
		return a;
	}

	/**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(Paths.get(args[0]));
        } catch (IOException e) {
            System.err.println("Error opening file: " + args[0]);
            return;
        }
        
        Scanner var;
        try {
            var = new Scanner(Paths.get(args[1]));
        } catch (IOException e) {
            System.err.println("Error opening file: " + args[1]);
            in.close();
            return;
        }
        
        Tokenizer t = Tokenizer1.create(in);
        ParseTree p = ParseTree1.create();
        
        parseProg(p, t);
        if (args[2].compareTo("print") == 0) {
        	printProg(p);
        }
        execProg(p, var);
        /*
         * Close input stream
         */
        in.close();
        var.close();
    }

}