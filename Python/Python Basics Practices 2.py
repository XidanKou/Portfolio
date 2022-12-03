# Question 1
m = [[0, 0, 1, 0, 1, 1], 
     [0, 0, 0, 0, 1, 1], 
     [0, 0, 0, 1, 1, 0], 
     [0, 0, 0, 0, 0, 0], 
     [0, 0, 0, 0, 0, 1]]
def matrix_to_list(m):
  li = []
  for row in range(len(m)):
    for column in range(len(m[row])):
      if(m[row][column] == 1):
        li.append((row,column))
  li = sorted(li , key=lambda k: [k[0], k[1]])
  return li
print(matrix_to_list(m))
# Question 2
li = [(0, 2), (0, 4), (0, 5), (1, 4), (1, 5), (2, 3), (2, 4), (4, 5)]
def list_to_dict(li):
  dict = {}
  key = 0
  i = 0
  while i < len(li):
    value = []
    temp = 0
    for j in range(i,len(li)):
      if li[j][0] == key:
        value.append(li[j][1])
        temp = temp + 1
    dict[key] = value
    key = key + 1
    i = i + temp
  return dict
print(list_to_dict(li))
# Question 3
li = [10,3,6,2,7,5,8,11,1]
def lo_high(li):
  lo = li[0]
  hi = li[0]
  for i in range(1,len(li)):
    if(li[i]) < lo:
      lo = li[i]
      lo_index = i
    elif li[i] > hi:
      hi = li[i]
      hi_index = i
  temp = li[0]
  li[0] = lo
  li[lo_index] = temp
  temp = li[len(li)-1]
  li[len(li)-1] = hi
  li[hi_index] = temp
  print(li)
lo_high(li)

m = [[0, 1, 0, 1, 1],
    [1, 0, 1, 1, 0],
    [0, 1, 0, 1, 0],
    [1, 1, 1, 0, 1],
    [1, 0, 0, 1, 0]]
print("Complement Matrix:")
def complement(m):
  mComplement = []
  one = 1
  for i in range(len(m)):
      mComplement.append([0]*len(m[i]))
  for i in range(len(m)):
    for j in range(len(m[i])):
      if(i != j):
        if(m[i][j]==0):
          mComplement[i][j] = one
        else:
          mComplement[i][j] = 0
  return mComplement
result = (complement(m))
for row in result:
  print(row)
print("columns:")
def columns(rows, columns):
  result = []
  for i in range(rows):
    result.append([0] * columns)
  
  for x in range(rows):
    for y in range(columns):
      result[x][y] = (x+1) + (y*rows)
  return result
result = columns(5, 4)
for row in result:
  print(row)
print("upper right triangle:")
def upper_right(rows, columns):
  result = []
  for i in range(rows):
    result.append([1] * columns)
  for x in range(rows):
    for y in range(columns):
      if x > y:
        result[x][y] = 0
  return result
result = upper_right(4, 4)
for row in result:
  print(row)
print("upper left triangle:")
def upper_left(rows, columns):
  result = []
  for i in range(rows):
    result.append([0] * columns)
  for x in range(rows):
    for y in range(columns-x):
      
        result[x][y] = 1
  return result
result = upper_left(4, 4)
for row in result:
  print(row)
print("lower left triangle")
def lower_left(rows, columns):
  result = []
  for i in range(rows):
    result.append([1] * columns)
  for x in range(rows):
    for y in range(columns):
      if x < y:
        result[x][y] = 0
  return result
result = lower_left(4, 4)
for row in result:
  print(row)
print("lower right triangle")
def lower_right(rows, columns):
  result = []
  for i in range(rows):
    result.append([0] * columns)
  for x in range(rows):
    for y in range(columns-x-1,columns):
        result[x][y] = 1
  return result
result = lower_right(4, 4)
for row in result:
  print(row)
print("square")
def square(rows, columns):
  result = []
  li = [1]*columns
  result.append(li)
  for i in range(1,rows-1):
    result.append([0] * columns)
  result.append(li)
  for i in range(1,rows-1):
    result[i][0] = 1
    result[i][columns-1] = 1
  return result
result = square(5, 4)
for row in result:
    print(row)

#find the name of the student with the highest GPA. The method should return 
"Smith"
import csv
print("1. highest_gpa: ")
def highest_gpa():
  with open('students.csv', mode='r') as csv_file:
    csv_reader = csv.DictReader(csv_file)
    gpa_highest = 0
    name = ""
    for i in csv_reader:
      if i['gpa'] > str(gpa_highest):
        gpa_highest = i['gpa']
        name = i['name']
  return name
print(highest_gpa())
print()
print("2. at_least_60_credits:")
#returns a list of the names of the student who have earned at least 60 credits. 
should return['Smith', 'Jones', 'Clinton', 'Carlson']
def at_least_60_credits():
  with open('students.csv', mode='r') as csv_file:
    csv_reader = csv.DictReader(csv_file)
    result = []
    for i in csv_reader:
      if int(i['credits_earned']) >= 60:
        result.append(i['name'])
  return result
print(at_least_60_credits())
print()
print("3. high_gpa_women:")
#returns a list of women who have at least a 3.0 gpa. Should return ['Jones', 
'Fletcher', 'Carlson']
def high_gpa_women():
  with open('students.csv', mode='r') as csv_file:
    csv_reader = csv.DictReader(csv_file)
    result =[]
    for i in csv_reader:
      if i['sex'] == "F" :
        if float(i['gpa']) >= 3.0:
          result.append(i['name'])
  return result
    
print(high_gpa_women())
#determines the rank of students after the current semester is done. If a student 
#has less that 30 credits, they are a freshman, less than 60, a sophomore, less than
#90, a junior, and 90 or greater, a senior. The method should return a dictionary 
#where the keys are students names and the values are their ranks.
#for example:
#{'Adams': 'Sophomore', 'Smith': 'Junior', 'Jones': 'Junior', 'Fletcher': 'Junior',
'Biden': 'Sophomore', 'Clinton': 'Junior', 'Waters': 'Sophomore', 'Hamilton': 
'Sophomore', 'Carlson': 'Junior'}

print()
print("4. rank_after_semester_done:")
def rank_after_semester_done():
  with open('students.csv', mode='r') as csv_file:
    result = {}
    csv_reader = csv.DictReader(csv_file)
    for student in csv_reader:
      if int(student['credits_earned']) < 30: 
        result[student['name']] = "freshman"
      elif int(student['credits_earned']) < 60: 
        result[student['name']] = "sophomore"
      elif int(student['credits_earned']) < 90: 
        result[student['name']] = "junior"
      else:
        result[student['name']] = "senior"
  return result
    
print(rank_after_semester_done())
#returns a dictionary where the keys represents sexes and the values are the number
# of each sex 
#for the given csv file, the method should return {'M': 4, 'F'; 3} 
print()
print("5. rank_after_semester_done:")
def count_high_gpa():
  result = {}
  with open('students.csv', mode='r') as csv_file:
    csv_reader = csv.DictReader(csv_file)
  num_female = 0
  num_male = 0
  for i in csv_reader:
    if str(i['sex']) == "F":
      num_female = num_female + 1 
    else:
      num_male = num_male + 1
  result['M'] = num_male
  result['F'] = num_female
  return result
   
print(rank_after_semester_done())

#The class below represent complex numbers; that is number of the form a + bi 
import math
class Complex:
  def __init__(self, a, b):
    self.a = a
    self.b = b
    
  def __str__(self):
    return str(self.a) + " + " + str(self.b) + "i"
  def __repr__(self):
    return str(self.a) + " +  " + str(self.b) + "i"
 #Below, a' and b' indicate the values of a different complex number. They are NOT 
derivatives.
 #By FOILing, (a + bi)(a' + b'i) = (aa' - bb') +   (ab' + ba')i. Note that i^2 = -1
  def __mul__(self, other):
    return Complex((self.a * other.a - self.b * other.b), (self.a * other.b + 
self.b * other.a))
# (a +bi) + (a' + b'i) = (a + a') + (b + b')i
  def __add__(self, other):
    return Complex((self.a + other.a ), (self.b +  other.b))
# (a +bi) - (a' + b'i) = (a - a') + (b - b')i
  def __sub__(self, other):
    return Complex((self.a - other.a ), (self.b -  other.b))
#The conjugate of a + bi is a -bi
  def conjugate(self):
    return Complex(self.a,-self.b )
# Google modulus if you've forgotten what it is.
  def modulus(self):
       return math.sqrt(self.a * self.a + self.b * self.b)
Comp1 = Complex(2,3)
Comp2 = Complex(3,4)
Comp3 = Comp1.modulus()
print(Comp3)
