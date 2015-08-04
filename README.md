# chesschallenge

### 7x7 board, 2 Kings, 2 Queens, 2 Bishops, 1 Knight
````
time java -jar chesschallenge-1.0-all.jar -f7 -r7 -B2 -Q2 -K2 -N1 --print-limit1=0
500000

_|_|_|_|_|_|Q|
_|_|_|_|B|_|_|
_|_|B|_|_|_|_|
Q|_|_|_|_|_|_|
_|_|_|_|_|K|_|
_|_|_|_|_|_|_|
_|_|K|_|_|N|_|

1000000

_|_|Q|_|_|_|_|
_|_|_|_|B|_|_|
_|_|_|_|_|_|K|
K|_|_|_|B|_|_|
_|_|_|_|_|_|_|
_|Q|_|_|_|_|_|
_|_|_|_|_|N|_|

1500000

_|_|_|K|_|_|_|
_|Q|_|_|_|_|_|
_|_|_|_|B|_|_|
_|_|_|_|_|_|_|
_|_|_|K|_|_|_|
_|_|_|_|_|_|Q|
_|_|_|N|B|_|_|

2000000

_|_|_|_|_|B|B|
_|_|Q|_|_|_|_|
_|_|_|_|_|_|_|
_|_|_|_|_|_|K|
N|_|_|K|_|_|_|
_|_|_|_|_|_|_|
_|_|_|_|Q|_|_|

2500000

B|_|_|_|_|K|_|
_|_|_|Q|_|_|_|
N|B|_|_|_|_|_|
_|_|_|_|_|_|Q|
_|_|_|_|_|_|_|
_|K|_|_|_|_|_|
_|_|_|_|_|_|_|

3000000

_|_|_|_|_|_|_|
K|_|_|_|_|_|_|
_|_|_|_|_|Q|_|
_|K|_|_|_|_|_|
_|_|_|_|_|_|Q|
_|_|_|_|_|_|_|
B|_|B|N|_|_|_|

Total solutions found: 3063828
java -jar chesschallenge-1.0-all.jar -f7 -r7 -B2 -Q2 -K2 -N1   33,84s user 1,15s system 167% cpu 20,933 total
````

### Build executable
````
./gradlew shadowJar
````
### Usage
Get help:
````
java -jar build/libs/chesschallenge-1.0-all.jar -h
````
Solve:
````
java -jar build/libs/chesschallenge-1.0-all.jar -f3 -r3 -K2 -R1
1

_|_|K|
R|_|_|
_|_|K|

2

K|_|K|
_|_|_|
_|R|_|

3

_|R|_|
_|_|_|
K|_|K|

4

K|_|_|
_|_|R|
K|_|_|

Total solutions found: 4
````

````
java -jar build/libs/chesschallenge-1.0-all.jar -f4 -r4 -N4 -R2
1

_|N|_|N|
_|_|R|_|
_|N|_|N|
R|_|_|_|

2

_|_|R|_|
_|N|_|N|
R|_|_|_|
_|N|_|N|

3

_|N|_|N|
R|_|_|_|
_|N|_|N|
_|_|R|_|

4

R|_|_|_|
_|N|_|N|
_|_|R|_|
_|N|_|N|

5

N|_|N|_|
_|_|_|R|
N|_|N|_|
_|R|_|_|

6

_|_|_|R|
N|_|N|_|
_|R|_|_|
N|_|N|_|

7

N|_|N|_|
_|R|_|_|
N|_|N|_|
_|_|_|R|

8

_|R|_|_|
N|_|N|_|
_|_|_|R|
N|_|N|_|

Total solutions found: 8
````
