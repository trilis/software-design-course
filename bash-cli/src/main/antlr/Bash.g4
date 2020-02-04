grammar Bash;

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
