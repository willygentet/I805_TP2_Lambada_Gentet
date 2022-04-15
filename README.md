# TP Compilation : Génération d'arbres abstraits

## Groupe : _Willy Gentet_

Seuls les mots clés utilisés dans l'exemple fonctionnent.

Lors que je run avec l'exemple :
```
let a = input;
let b = input;
while (0 < b)
do (let aux=(a mod b); let a=b; let b=aux );
output a.
```
J'obtiens bien le résultat attendu.
```
> Task :Main.main()
DATA SEGMENT
a DD
b DD
aux DD
DATA ENDS
CODE SEGMENT
in eax
mov a, eax
in eax
mov b, eax
debut_while_1:
mov eax, 0
push eax
mov eax, b
pop ebx
sub eax, ebx
jle faux_gt_1
mov eax, 1
jmp sortie_gt_1
faux_gt_1:
mov eax, 0
sortie_gt_1:
jz sortie_while_1
mov eax, b
push eax
mov eax, a
pop ebx
mov ecx, eax
div ecx, ebx
mul ecx, ebx
sub eax, ecx
mov aux, eax
mov eax, b
mov a, eax
mov eax, aux
mov b, eax
jmp debut_while_1
sortie_while_1:
mov eax, a
out eax
CODE ENDS
```