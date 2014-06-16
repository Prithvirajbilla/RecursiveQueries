python create.py < $1 > create.sql

javac -cp .:ojdbc6.jar src/testdata_gen/DataGen.java -d .
java -cp .:ojdbc6.jar DataGen
