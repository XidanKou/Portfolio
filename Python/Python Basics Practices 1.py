# Question 1
# a)
l1a = [4,3,2]
l2a = [5,42]
l1a.extend(l2a)
print(l1a)
# b)
l1b = [1, 7, 42, 55, 6, 10]
print(l1b.index(42))
# c)
l1c = [4, 5, 1, 6, 7]
l1c.insert(2,42)
print(l1c)
# d)
l1d = [4, 42, 3, 42, 5, 6, 42, 7]
print(l1d.count(42))
# Question 2
def multiple(num):
  print(num % 3 )
# Question 3 
def sum(li1, li2):
  matrix = []
  for i in range (len(li1)):
    matrix.append([0] * len(li1[0]))
    for j in range (len(li1[i])):
      matrix[i][j] = li1[i][j] + li2[i][j]
  return matrix
li1 = [[1,4,3,5],[6,5,1,0],[2,1,3,8]]
li2 = [[0,3,2,1],[3,6,1,1],[0,1,5,2]]
li3 = [[2,3,4],[4,8,1]]
li4 = [[10,4,7],[1,4,0]]
print(sum(li1, li2))
print(sum(li3, li4))

# 1)
def method_1(n):
  sum = 0
  for i in range(n):
    sum += (i+1)*(i+1)
  print(sum)
method_1(4)
# 2)
def method_2(a, b):
  sum = 0
  for i in range(a,b+1):
    if i % 2 == 0 :
      sum += i
  print(sum)
method_2(4,9)
# 3) 
def method_3(n):
  sum = 0
  for i in range(n+1):
    sum += pow(2,i)
  print(sum)
method_3(4)
# 4) 
def prime(n):
  x = True
  for i in range(2,n):
    if n % i == 0:
      x = False
  return x
print(prime(10))
# 5) 
def split(li):
  sum_first = 0
  x = False
  for i in range(len(li)):
    sum_first += li[i]
    sum_second = 0
    for j in range(i+1,len(li)):
      sum_second += li[j]
    if sum_first == sum_second:
      x = True
  return x
li = [3,2,1,1,3]
print(split(li))

m = [[1, 2, 3],
     [4, 5, 6],
     [7, 8, 9]]
print("1a) starts here:")
def matrix_a(m):
      for i in range(len(m)):
        for j in range(len(m)-i):
          print(m[i][j], end = "")
        print()
matrix_a(m)
print("1b) starts here:")
def matrix_b(m):
      for i in range(len(m)):
        for j in range(i+1):
          print(m[i][j], end = "")
        print()
matrix_b(m)
print("1c) starts here:")
def matrix_c(m):
      for i in range(len(m)):
        for j in range(i,len(m)):
          print(m[i][j], end = "")
        print()
        
matrix_c(m)
print("1d) starts here:")
def matrix_d(m):
      for i in range(len(m)):
        for j in range(len(m)-i-1,len(m)):
          print(m[i][j], end = "")
        print()
matrix_d(m)
print("Question 2 starts here:")
def reverse(li):
  for i in range(int(len(li)/2)):
    li[i], li[len(li)-i-1] = li[len(li)-i-1],li[i]
  print(li)
li_odd = [7,8,10,5,0]
reverse(li_odd)
li_even = [1,2,3,4]
reverse(li_even)
print("Question 3 starts here:")
def second_smallest(li):
  smallest = li[0]
  second_small = li[1]
  if second_small < smallest:
    second_small, smallest = smallest,second_small
  if len(li) > 2:
    for i in range(2,len(li)):
      if(li[i] < smallest):
        second_small = smallest
        li[i], smallest = smallest, li[i]
      elif(li[i] < second_small):
        second_small = li[i]
  return second_small
li_odd = [7,8,10,5,0]
li_even = [1,2,3,4]
li_1 = [2,6,7,8,9,10]
li_2 = [3,6,5,4,5]
li_3 = [7,8,10,4,7,9]
print(second_smallest(li_odd))
print(second_smallest(li_even))
print(second_smallest(li_1))
print(second_smallest(li_2))


print("Question 1")
def transpose(d):
 dic = {}
 for i in range(len(d)):
   value = []
   for k,v in d.items():
     for j in range(len(v)):
       if i == v[j]:
         value.append(k)
   dic[i] = value
 return dic
d = {0: [1, 4],
 1: [2, 4],
 2: [0],
 3: [2],
 4: [0, 2]}
print(transpose(d))
print("Question 2")
def largest_value(d):
  dic = {}
  for k,v in d.items():
      largest = v[0]
      for i in range(1,len(v)):
        if v[i] > largest:
          largest = v[i]
      dic[k] = largest
  return dic
d = {0: [1, 4, 3, 2],
 1: [2, 4, 8, 1],
 2: [0],
 3: [2, 1],
4: [1, 2]}
print(largest_value(d))
print("Question 3")
def lists_to_dict(l1, l2):
  dic = {}
  for i in range(len(l2)):
    dic.update({l1[i]:l2[i]})
  return dic
l1 = [2, 3, 1, 5]
l2 = [4, 3, 1, 2]
print(lists_to_dict(l1, l2))
print("Question 3")
def raise_sal(d):
  for k,v in d.items():
    for subk,subv in v.items():
      sal = v["salary"]* 1.05
      v.update({"salary": int(sal)})
  print(d)
d = {103: {"name": "John", "salary" : 60000},
 101: {"name": "Sally", "salary" : 72000},
 102: {"name": "Marco", "salary" : 100000},
}
raise_sal(d)