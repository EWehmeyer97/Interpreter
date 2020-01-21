package edu.c3341;

import java.util.Iterator;

/**
 * Part 1 of Project 1 for CSE 3341. Provides an implementation for methods needed to create a Tokenizer for the Core language.
 *
 * @author Evan Wehmeyer
 *
 */

public class Tokenizer1 implements Tokenizer {

	private String head = ""; //Holds front part of Program
	private int pos = 0; //Looks at further positions in Tokenizer?
	private Iterator<String> tail; //Holds rest of Program
	private StringBuilder token = new StringBuilder();; //Holds one token
	private TokenKind kind; //holds kind of token
	private Tokenizer1() {};
	private static Tokenizer1 instance;
	
	
	public static Tokenizer create(Iterator<String> in) {
		instance = new Tokenizer1() {};
		instance.tail = in;
		instance.findToken();
		return instance;
	}
	public static Tokenizer instance() {
		return instance;
	}

	private void findToken () { //this method finds out what token is here!
		token.setLength(0); //resets Token
		if (this.head.length() <= this.pos) {
			if(!tail.hasNext()) {
				this.kind = TokenKind.EOF;
			} else {
				head = tail.next();
				this.pos = 0;
			}
		}
		if (!(this.kind == TokenKind.EOF)) { //Rest of Method is nested in this IF Statement. (It ensures EOF is recognized.)
			//code is not indented for better readability of following code.
		this.token.append(this.head.charAt(this.pos)); //adds first char to Token
		
		//finds = or ==
		if (this.head.charAt(this.pos) == '=') {
			this.pos++;
			if (this.pos < this.head.length() && this.head.charAt(pos) == '=') { //==
				this.token.append(this.head.charAt(this.pos));
				this.kind = TokenKind.EQUALITY_TEST;
				this.pos++;
			} else { //=
				this.kind = TokenKind.ASSIGNMENT_OPERATOR;
			}
		} 
		//finds || or ERROR
		else if (this.head.charAt(this.pos) == '|') { 
			this.pos++;
			if (this.pos < this.head.length() && this.head.charAt(this.pos) == '|') { //||
				this.token.append(this.head.charAt(this.pos));
				this.kind = TokenKind.OR_OPERATOR;
				this.pos++;
			} else { //ERROR
				this.kind = TokenKind.ERROR;
			}
		} 
		//finds ;
		else if (this.head.charAt(this.pos) == ';') { 
			this.kind = TokenKind.SEMICOLON;
			this.pos++;
		} 
		//finds ,
		else if (this.head.charAt(this.pos) == ',') { 
			this.kind = TokenKind.COMMA;
			this.pos++;
		}
		//finds ! or !=
		else if (this.head.charAt(this.pos) == '!') { 
			this.pos++;
			if (this.pos < this.head.length() && this.head.charAt(pos) == '=') { //!=
				this.token.append(this.head.charAt(this.pos));
				this.kind = TokenKind.NON_EQUALITY_TEST;
				this.pos++;
			} else { //!
				this.kind = TokenKind.NOT;
			}
		}
		//finds [
		else if (this.head.charAt(this.pos) == '[') { 
			this.kind = TokenKind.LEFT_BRACKET;
			this.pos++;
		}		
		//finds ]
		else if (this.head.charAt(this.pos) == ']') { 
			this.kind = TokenKind.RIGHT_BRACKET;
			this.pos++;
		}
		//finds && or ERROR
		else if (this.head.charAt(this.pos) == '&') { 
			this.pos++;
			if(this.pos < this.head.length() && this.head.charAt(this.pos) == '&') {
				this.token.append(this.head.charAt(this.pos));
				this.kind = TokenKind.AND;
				this.pos++;
			} else {
				this.kind = TokenKind.ERROR;
			}
		}
		//finds (
		else if (this.head.charAt(this.pos) == '(') { 
			this.kind = TokenKind.LEFT_PARENTHESIS;
			this.pos++;
		}
		//finds )
		else if (this.head.charAt(this.pos) == ')') { 
			this.kind = TokenKind.RIGHT_PARENTHESIS;
			this.pos++;
		}
		//finds +
		else if (this.head.charAt(this.pos) == '+') { 
			this.kind = TokenKind.ADD_OPERATOR;
			this.pos++;
		}
		//finds -
		else if (this.head.charAt(this.pos) == '-') { 
			this.kind = TokenKind.SUBTRACT_OPERATOR;
			this.pos++;
		}
		//finds *
		else if (this.head.charAt(this.pos) == '*') { 
			this.kind = TokenKind.MULTIPLY_OPERATOR;
			this.pos++;
		}
		//finds < or <=
		else if (this.head.charAt(this.pos) == '<') { 
			this.pos++;
			if (this.pos < this.head.length() && this.head.charAt(pos) == '=') { //<=
				this.token.append(this.head.charAt(this.pos));
				this.kind = TokenKind.EQUAL_OR_LESS_TEST;
				this.pos++;
			} else { //<
				this.kind = TokenKind.LESS_THAN_TEST;
			}
		}
		//finds > or >=
		else if (this.head.charAt(this.pos) == '>') { 
			this.pos++;
			if (this.pos < this.head.length() && this.head.charAt(pos) == '=') { //>=
				this.token.append(this.head.charAt(this.pos));
				this.kind = TokenKind.EQUAL_OR_GREATER_TEST;
				this.pos++;
			} else { //>
				this.kind = TokenKind.GREATER_THAN_TEST;
			}
		}
		//finds RESERVED WORD (1-11) or ERROR
		else if (Character.isLowerCase(this.head.charAt(this.pos))) {
			this.pos++;
			while (this.pos < this.head.length() && Character.isLowerCase(this.head.charAt(this.pos))) { //Reserved word or ERROR
				this.token.append(this.head.charAt(this.pos));
				this.pos++;
			}
			if (this.pos < this.head.length() && (Character.isUpperCase(this.head.charAt(this.pos)) || Character.isDigit(this.head.charAt(this.pos)))) { //ERROR
				while(this.pos < this.head.length() && !(this.head.charAt(this.pos) == ';' || this.head.charAt(this.pos) == '|' || this.head.charAt(this.pos) == '=')) {
					this.token.append(this.head.charAt(this.pos));
					this.pos++;
				}
				this.kind = TokenKind.ERROR;
			} else {
				String temp = this.token.toString();
				if (temp.compareTo("program") == 0) { //program
					this.kind = TokenKind.PROGRAM_WORD;
				} else if (temp.compareTo("begin") == 0) { //being
					this.kind = TokenKind.BEGIN_WORD;
				} else if (temp.compareTo("end") == 0) { //end
					this.kind = TokenKind.END_WORD;
				}  else if (temp.compareTo("int") == 0) { //int
					this.kind = TokenKind.INT_WORD;
				}  else if (temp.compareTo("if") == 0) { //if
					this.kind = TokenKind.IF_WORD;
				}  else if (temp.compareTo("then") == 0) { //then
					this.kind = TokenKind.THEN_WORD;
				}  else if (temp.compareTo("else") == 0) { //else
					this.kind = TokenKind.ELSE_WORD;
				} else if (temp.compareTo("while") == 0) { //while
					this.kind = TokenKind.WHILE_WORD;
				} else if (temp.compareTo("loop") == 0) { //loop
					this.kind = TokenKind.LOOP_WORD;
				} else if (temp.compareTo("read") == 0) { //read
					this.kind = TokenKind.READ_WORD;
				} else if (temp.compareTo("write") == 0) { //write
					this.kind = TokenKind.WRITE_WORD;
				} else { 								  //ERROR
					this.kind = TokenKind.ERROR;
				}
			}
		} 
		//finds Integer or ERROR
		else if (Character.isDigit(this.head.charAt(this.pos))) { 
			this.pos++;
			while (this.pos < this.head.length() && Character.isDigit(this.head.charAt(this.pos))) { //Integer
				this.token.append(this.head.charAt(this.pos));
				this.pos++;
			}
			if (this.pos < this.head.length() && (Character.isUpperCase(this.head.charAt(this.pos)) || Character.isLowerCase(this.head.charAt(this.pos)))) { //ERROR
				while(this.pos < this.head.length() && !(this.head.charAt(this.pos) == ';' || this.head.charAt(this.pos) == '|' || this.head.charAt(this.pos) == '=')) {
					this.token.append(this.head.charAt(this.pos));
					this.pos++;
				}
				this.kind = TokenKind.ERROR;
			} else {
				this.kind = TokenKind.INTEGER_CONSTANT;
			}
		} 
		//finds Identifier (may end w/ numbers) or ERROR 
		else if (Character.isUpperCase(this.head.charAt(this.pos))) { 
			this.pos++;
			while (this.pos < this.head.length() && Character.isUpperCase(this.head.charAt(this.pos))) { //Identifier w/ Uppercase letters
				this.token.append(this.head.charAt(this.pos));
				this.pos++;
			}
			while (this.pos < this.head.length() && Character.isDigit(this.head.charAt(this.pos))) { //Identifier w/ Number ending
				this.token.append(this.head.charAt(this.pos));
				this.pos++;
			}
			if (this.pos < this.head.length() && (Character.isUpperCase(this.head.charAt(this.pos)) || Character.isLowerCase(this.head.charAt(this.pos)))) { //ERROR
				while(this.pos < this.head.length() && !(this.head.charAt(this.pos) == ';' || this.head.charAt(this.pos) == '|' || this.head.charAt(this.pos) == '=')) {
					this.token.append(this.head.charAt(this.pos));
					this.pos++;
				}
				this.kind = TokenKind.ERROR;
			} else {
				this.kind = TokenKind.IDENTIFIER;
			}
		} else { //any other character - ERROR
			this.pos++;
			this.kind = TokenKind.ERROR;
		}
		} //End of if (!(this.kind == TokenKind.EOF))
	}
	
	public TokenKind getToken() {
		return kind;
	}
	@Override
	public void skipToken() {
		this.findToken();
	}
	@Override
	public int intVal() {
		return Integer.parseInt(this.token.toString());
	}
	@Override
	public String idName() {
		return this.token.toString();
	}
}
