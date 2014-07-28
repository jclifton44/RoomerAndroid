print 'generating values...'
print 'using function 15*e^((13.41/100)*x)'


def getStringRepresentation( value ):
	if value <= 0: 
		return "15m";
	if value >= 100:
		return "10Mm";
	if value > 0 and value < 100:
		exponet = (6.84122/100 * value) ** 1.35
		x = 15 * (2.71828 ** exponet )
		x = round(x, 0);
		if x < 100: 
			return str(x)+"m"
		if x < 1000:
			return str(round(x/1000, 2)) + "km"
		if x < 10000:
			return str(round(x/1000, 1)) + "km"
		if x < 100000:
			return str(round(x/1000, 1)) + "km"
		if x < 1000000:
			return str(round(x/1000, 0)) + "km"
		if x < 10000000:
			return str(round(x/100, 0)) + "km"



finalArray = "[\n";
for i in range(0, 100):
	finalArray =  finalArray + str(getStringRepresentation(i))  + ",\n"

finalArray += "]"


print 'Array Values...'
print finalArray


print getStringRepresentation (-1)
print getStringRepresentation (100)
print getStringRepresentation (87)
print getStringRepresentation (23)
print getStringRepresentation (50)

