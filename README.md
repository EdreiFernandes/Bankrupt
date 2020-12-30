# Bankrupt

Desafio proposto pela Tapps Games

## Creating .jar

### manifest

- Manifest-Version: 1.0
- Class-Path: .
- Main-Class: bankrupt.Game

### Compiling

- javac -d ./bin src/config/\*.java
- javac -cp ./bin src/bankrupt/\*.java
- move .class from 'src/bankrupt' to 'bin/bankrupt'
- create manifest.txt in bin folder
- jar -cvmf bin/manifest.txt bin/Bankrupt.jar bin/bankrupt/\*.class

### Running

- java -jar bin/Bankrupt.jar
