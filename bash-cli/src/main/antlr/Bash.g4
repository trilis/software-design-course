grammar Bash;

@parser::members {
    @Override
    public void notifyErrorListeners(Token offendingToken, String msg, RecognitionException ex) {
        throw new IllegalStateException(msg);
    }
}
@lexer::members {
    @Override
    public void recover(RecognitionException ex) {
        throw new IllegalStateException(ex.getMessage());
    }
}

instruction:
    pipeline
    | assignment
;

pipeline:
    commands += command ('|' commands += command)* EOF
;

command:
    WHITESPACES? name=token (WHITESPACES? arguments += token)* WHITESPACES?
;

assignment:
    WHITESPACES? variable=WORD WHITESPACES? '=' WHITESPACES? value=token WHITESPACES?
;

token:
    '\'' (singleQuoted += (WORD | WHITESPACES | '"' | '=' | '|'))* '\'' #single
    | '"' (doubleQuoted += (WORD | WHITESPACES | '\'' | '=' | '|'))* '"' #double
    | simpleToken=WORD #simple
;

WORD:
    ~[ \t\r\n"'=|]+
;

WHITESPACES:
    [ \t\r\n]+
;
