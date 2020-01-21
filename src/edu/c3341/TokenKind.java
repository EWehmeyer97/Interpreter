package edu.c3341;

/**
 * Token kinds needed for Part 1 of the Core Interpreter project.
 *
 * @author Wayne D, Heym and Evan Wehmeyer
 *
 */
enum TokenKind {

    /**
     * Test driver's token number = 1; token is program.
     */
    PROGRAM_WORD(1),
    
    /**
     * Test driver's token number = 2; token is begin.
     */
    BEGIN_WORD(2),
    
    /**
     * Test driver's token number = 3; token is end.
     */
    END_WORD(3),
    
    /**
     * Test driver's token number = 4; token is int.
     */
    INT_WORD(4),
    
    /**
     * Test driver's token number = 5; token is if.
     */
    IF_WORD(5),
    
    /**
     * Test driver's token number = 6; token is then.
     */
    THEN_WORD(6),
    
    /**
     * Test driver's token number = 7; token is else.
     */
    ELSE_WORD(7),
    
    /**
     * Test driver's token number = 8; token is while.
     */
    WHILE_WORD(8),
    
    /**
     * Test driver's token number = 9; token is loop.
     */
    LOOP_WORD(9),
    
    /**
     * Test driver's token number = 10; token is read.
     */
    READ_WORD(10),
    
    /**
     * Test driver's token number = 11; token is write.
     */
    WRITE_WORD(11),

    /**
     * Test driver's token number = 12; token is ;.
     */
    SEMICOLON(12),
    
    /**
     * Test driver's token number = 13; token is ,.
     */
    COMMA(13),

    /**
     * Test driver's token number = 14; token is =.
     */
    ASSIGNMENT_OPERATOR(14),
    
    /**
     * Test driver's token number = 15; token is !.
     */
    NOT(15),
    
    /**
     * Test driver's token number = 16; token is [.
     */
    LEFT_BRACKET(16),

    /**
     * Test driver's token number = 17; token is ].
     */
    RIGHT_BRACKET(17),
    
    /**
     * Test driver's token number = 18; token is &&.
     */
    AND(18),
    
    /**
     * Test driver's token number = 19; token is ||.
     */
    OR_OPERATOR(19),
    
    /**
     * Test driver's token number = 20; token is (.
     */
    LEFT_PARENTHESIS(20),
    
    /**
     * Test driver's token number = 21; token is ).
     */
    RIGHT_PARENTHESIS(21),
    
    /**
     * Test driver's token number = 22; token is +.
     */
    ADD_OPERATOR(22),
    
    /**
     * Test driver's token number = 23; token is -.
     */
    SUBTRACT_OPERATOR(23),
    
    /**
     * Test driver's token number = 24; token is *.
     */
    MULTIPLY_OPERATOR(24),
    
    /**
     * Test driver's token number = 25; token is !=.
     */
    NON_EQUALITY_TEST(25),

    /**
     * Test driver's token number = 26; token is ==.
     */
    EQUALITY_TEST(26),
    
    /**
     * Test driver's token number = 27; token is <.
     */
    LESS_THAN_TEST(27),
    
    /**
     * Test driver's token number = 28; token is >.
     */
    GREATER_THAN_TEST(28),
    
    /**
     * Test driver's token number = 29; token is <=.
     */
    EQUAL_OR_LESS_TEST(29),
    
    /**
     * Test driver's token number = 30; token is >=.
     */
    EQUAL_OR_GREATER_TEST(30),

    /**
     * Test driver's token number = 31.
     */
    INTEGER_CONSTANT(31),

    /**
     * Test driver's token number = 32.
     */
    IDENTIFIER(32),

    /**
     * Test driver's token number = 33.
     */
    EOF(33),

    /**
     * Test driver's token number = 34.
     */
    ERROR(34);

    /**
     * Test driver's token number.
     */
    private int testDriverTokenNumber;

    /**
     * Constructor.
     *
     * @param number
     *            the test driver's token number
     */
    private TokenKind(int number) {
        this.testDriverTokenNumber = number;
    }

    /**
     * Return test driver's token number.
     *
     * @return test driver's token number
     */
    public int testDriverTokenNumber() {
        return this.testDriverTokenNumber;
    }
}