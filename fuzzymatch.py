import sys
from fuzzywuzzy import fuzz
from fuzzywuzzy import process


db = open('database.txt','r')
fin = db.readlines()
dic = {}


#for i in range(len(fin)):
#	dic[i]=fin[i]

	
#for keys,values in dic.items():
#	print(keys)
#	print(values)


#for i in range(len(fin))-1:
#	print fin[i]
#	dic[fin[i]]=fin[i+1]
#	i = i+1

def get_pair(line):
	key, sep, value = line.strip().partition("$")
	return key,value


def get_pair_2(line):
	key, sep, value = line.strip().partition(",")
	return key,value
	

d = {}
with open('output.txt') as f:
	d = dict(get_pair(line) for line in f)

#for keys,values in d.items():
#    print(keys)
#    print(values)

#now fuzzy matching of strings...

dic2 = {}
wghtmat = []
for keys,values in d.items():
	print(process.extract(keys,fin))
	dic2 =process.extract(keys,fin)	
	wghtmat.append(dic2)
	print "\n"


dic3 = {}
i = 0
for x in wghtmat:
	print(x)
	for y in x:
#		print y
#		print ("\n")
		i=0
		for value in y:d
#			print value			
#			print ("\n")
			if i == 0:
				temp1=value
			else:
				temp2=value
			i=i+1

		dic3[temp1]=temp2	
	print("\n")



for keys,values in dic3.items():
    print(keys)
    print(values)


print ("Printing revelent keywords\n")

cnt = 0
per = 0

for keys,values in dic3.items():
	cnt = cnt+1
	if values > 80:
		print keys
		per = per+ values
		print ("\n")

print ("The relevent final % : ")
print ((per/cnt)*10)
