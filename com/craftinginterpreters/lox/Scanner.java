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

    tokens.add(new Token(TokenType.EOF, "", null, line));

    return tokens;
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
      case '!':
        addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
        break;
      case '=':
        addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
        break;
      case '<':
        addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
        break;
      case '>':
        addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
        break;
      case '/':
        // if the next character is a slash, then ignore everything until you hit a \n
        if (match('/')) {
          while (peek() != '\n' && !isAtEnd()) {
            advance();
          }
        } else {
          addToken(TokenType.SLASH);
        }
        break;
      default:
        Lox.error(line, "Encountered unexpected character: " + c);
    }
  }

/**
 * Since `current` is just an index
 * we can determine whether the current index
 * has reached the number of characters
 * in the source file.
 * 
 * @return a boolean representing
 * whether we've reached the end of the source file
 */
  public Boolean isAtEnd() {
    return current >= source.length();
  }

/**
 * Get char at current position and return it
 * while advancing the scanner
 * @return char at current position
 */
  public char advance() {
    char currentChar = source.charAt(current);

    current++;

    return currentChar;
  }

/**
 * Add tokens that don't have a literal value.
 * 
 * @param type The type of the token being added
 */
  private void addToken(TokenType type) {
    addToken(type, null);
  }

/**
 * Add tokens that have a literal value.
 * 
 * @param type The type of the token being added
 * @param literal The literal value of the token
 */
  private void addToken(TokenType type, Object literal) {
    String text = source.substring(start, current);
    tokens.add(new Token(type, text, literal, line));
  }

/**
 * Match returns true if the current character
 * matches the query and false otherwise.
 * 
 * `current` is incremented only if the
 * current character is a match.
 * @param expected
 * @return
 */
  private Boolean match(char expected) {
    if (isAtEnd()) {
      return false;
    }

    if (source.charAt(current) != expected) {
      return false;
    }

    current++;

    return true;
  }

/**
 * Peek returns the current character
 * but doesn't increment `current`
 * @return character at current position
 */
  private char peek() {
    if (isAtEnd()) {
      return '\0';
    }
    return source.charAt(current);
  }
}
