# chesschallenge

This branch holds modified version of the application which maintains index of pieces on board. This drastically increases performance for large boards (+500% for `-f20 -r20 -B2 -Q2 -K2 -N1`) as computational complexity no longer dependends on board size but on number of pieces placed on it. However, for small boards it performs worse.

build executable:
````
./gradlew shadowJar
````
to get help:
````
java -jar build/libs/chesschallenge-1.0-all.jar -h
````
usage:
````
java -jar build/libs/chesschallenge-1.0-all.jar -f3 -r3 -K2 -R1
0
K|_|K|
_|_|_|
_|R|_|

1
_|_|K|
R|_|_|
_|_|K|

2
_|R|_|
_|_|_|
K|_|K|

3
K|_|_|
_|_|R|
K|_|_|

Total permutations: 252
Independent:        4
Running time:       00:00:00.005
````

````
java -jar build/libs/chesschallenge-1.0-all.jar -f4 -r4 -N4 -R2
0
R|_|_|_|
_|N|_|N|
_|_|R|_|
_|N|_|N|

1
_|N|_|N|
R|_|_|_|
_|N|_|N|
_|_|R|_|

2
_|_|R|_|
_|N|_|N|
R|_|_|_|
_|N|_|N|

3
N|_|N|_|
_|R|_|_|
N|_|N|_|
_|_|_|R|

4
N|_|N|_|
_|_|_|R|
N|_|N|_|
_|R|_|_|

5
_|N|_|N|
_|_|R|_|
_|N|_|N|
R|_|_|_|

6
_|R|_|_|
N|_|N|_|
_|_|_|R|
N|_|N|_|

7
_|_|_|R|
N|_|N|_|
_|R|_|_|
N|_|N|_|

Total permutations: 120120
Independent:        8
Running time:       00:00:00.088
````
