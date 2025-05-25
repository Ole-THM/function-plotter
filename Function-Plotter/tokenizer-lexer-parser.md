## 1. **Tokenizer**

A lexer breaks a stream of text into tokens, usually by looking for whitespace (tabs, spaces, new lines).

## 2. **Lexer**

A lexer is basically a lexer, but it usually attaches extra context to the tokens -- this token is a number, that token is a string literal, this other token is an equality operator.

## 3. **Parser**

A parser takes the stream of tokens from the lexer and turns it into an abstract syntax tree representing the (usually) program represented by the original text