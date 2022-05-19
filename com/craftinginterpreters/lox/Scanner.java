package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
  private final String source;

  private final List<Token> tokens = new ArrayList<>();
  
  private int start = 0;
  private int current = 0;
  private int line = 1;

  Scanner(String source) {
    this.source = source;
  }

/**
 * 1. Iterate over every lexeme
 * 2. Parse the lexeme
 * 3. Add token to the `tokens` instance variable
 * @return Returns a reference to the instance variable
 */
  public List<Token> scanTokens() {
    while (!isAtEnd()) {
      start = current;
      scanToken();
    }

    this.tokens.add(new Token(TokenType.EOF, "", null, line));

    return this.tokens;
  }

  public void scanToken() {
    char c = advance();

    switch(c) {
      case '(': 
        addToken(TokenType.LEFT_PAREN);
        break;
      case ')': 
        addToken(TokenType.RIGHT_PAREN);
        break;
      case '{': 
        addToken(TokenType.LEFT_BRACE);
        break;
      case '}': 
        addToken(TokenType.RIGHT_BRACE);
        break;
      case '*': 
        addToken(TokenType.ASTERISK);
        break;
      case '-': 
        addToken(TokenType.MINUS);
        break;
      case '+': 
        addToken(TokenType.PLUS);
        break;
      case ',': 
        addToken(TokenType.COMMA);
        break;
      case '.': 
        addToken(TokenType.DOT);
        break;
      case ';': 
        addToken(TokenType.SEMICOLON);
        break;
      default:
        Lox.error(this.line, "Encountered unexpected character: " + c);
    }
  }

/**
 * Since `this.current` is just an index
 * we can determine whether the current index
 * has reached the number of characters
 * in the source file.
 * 
 * @return a boolean representing
 * whether we've reached the end of the source file
 */
  public Boolean isAtEnd() {
    return this.current >= source.length();
  }

/**
 * Get char at current position and return it
 * while advancing the scanner
 * @return char at current position
 */
  public char advance() {
    char currentChar = this.source.charAt(this.current);

    this.current++;

    return currentChar;
  }

/**
 * Add tokens that don't have a literal value.
 * 
 * @param type The type of the token being added
 */
  private void addToken(TokenType type) {
    this.addToken(type, null);
  }

/**
 * Add tokens that have a literal value.
 * 
 * @param type The type of the token being added
 * @param literal The literal value of the token
 */
  private void addToken(TokenType type, Object literal) {
    String text = this.source.substring(start, current);
    this.tokens.add(new Token(type, text, literal, line));
  }

}
