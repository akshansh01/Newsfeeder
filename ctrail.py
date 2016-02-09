x = 5
y = 10

while x > 0:
	print "while loop",x
	x=x-1

z = [1,2,3,4,5,6,7,8]

for i in z:
	print "for loop",i

for v in range(5):
	print v

#x= input("please enter a number: ")
#print "The entered number is ",x

print z[3:8]

def square(x):
	return x*x


print square(4)

def change(list_var):
	list_var[1]=99

change(z)
print z



