import string
import re
import math 
from functools import cmp_to_key
import time
"""
startTime = 0.0
endTime = 0.0
preTimeKMP = 0.0
preTimeSASM = 0.0
preTimeRabin = 0.0
searchTimeKMP = 0.0
searchTimeSASM = 0.0
searchTimeRabin = 0.0
"""
def create_state_table(charset = 256):
	table = []
	for i in range(11):
		if i == 9:
			table.append(list([9]*charset))
		else:
			table.append(list([0]*charset))
	table[0][ord('h')] = 1
	table[0][ord('f')] = 6
	table[1][ord('t')] = 2
	table[2][ord('t')] = 3
	table[6][ord('t')] = 7
	table[3][ord('p')] = 4
	table[4][ord(':')] = 9
	table[4][ord('s')] = 5
	table[5][ord(':')] = 9
	table[7][ord('p')] = 8
	table[8][ord(':')] = 9
	table[9][ord(' ')] = 10
	table[9][ord('\n')] = 10
	table[9][ord('\0')] = 10	
	return table

def match_url(lst, states):
	lst1 = []
	url = []
	length_of_text = 0
	for text in lst:
		current_state = 0
		for i in text:
			length_of_text += len(text)
			current_state = states[current_state][ord(i)]
		if current_state == 9 or current_state ==10:
			url.append(text)
		else:
			lst1.append(''.join(c for c in text if c not in string.punctuation))#text = ''.join(c for c in text if c not in string.punctuation)		
	return lst1, url, length_of_text

def normalize(read, write):
	url_list = []
	length = 0
	states = create_state_table()
	with open(read, 'r') as readfile:
		with open(write, 'w') as writefile:
			for line in readfile:
				l = line.strip().split()
				#print(l)
				l, u, lgt = match_url(l, states)
				url_list.extend(u)
				length += lgt 
				writefile.writelines("%s " %i for i in l)
				writefile.write("\n")
	return url_list, length

def Find_Length_Of_Text(txtfile):
	url_list, length = normalize(txtfile, 'normalized.txt')
	return url_list, length

def Find_Pattern(pattern, inTextRange, algo):
	indices = TextRange(inTextRange)
	readfile = open('txtfile.txt').read()
	text = readfile[indices[0]:indices[1]]
	text = re.sub('\n', ' ', text)
	count = len(algo(pattern, text))
	return count

def RABIN_KARP(pattern, text):
	return rabin_karp(text, pattern, 10007)

def KMP(pattern, text):
	return kmp_matcher(text, pattern)

def rabin_karp(text,pattern,q):
    n = len(text)
    m = len(pattern)
    d = 256
    p = 0
    t = 0
    h = pow(d,m-1)%q
    l = []
    global startTime, endTime, preTime, searchTime
    startTime = round(time.time()*1000000)
    for i in range(0,m):
        p = (d*p + ord(pattern[i]))%q
        t = (d*t + ord(text[i]))%q
    endTime = round(time.time()*1000000)
    preTime = endTime - startTime
    startTime = round(time.time()*1000000)
    for s in range(0,n-m+1):
        if(p==t):
            for i in range(0,n-m+1):
                j=0
                while(j<m and pattern[j]==text[i+j]):
                    j=j+1
                if(j==m):
                    l.append(i)
        if(s<n-m):
            t = (d*(t-ord(text[s])*h)+ord(text[s+m]))%q
            if(t<0):
                t=t+q
    endTime = round(time.time()*1000000)
    searchTime = endTime - startTime
    return list(set(l))

def kmp_matcher(t, p):
	n = len(t)
	m = len(p)
	global startTime, endTime, preTime, searchTime
	startTime = round(time.time()*1000000)
	pi = compute_prefix_function(p)
	endTime = round(time.time()*1000000)
	preTime = endTime - startTime
	q = 0
	flag = False
	startTime = round(time.time()*1000000)
	indices = []
	for i in range(n):
		while(q>0 and p[q] != t[i]):
			q = pi[q-1]
		if p[q]==t[i]:
			q = q+1
		if q == m:
			indices.append(i-m+1)
			flag = True
			q = pi[q-1]
	if flag == False:
		pass
	endTime = round(time.time()*1000000)
	searchTime = endTime - startTime
	return indices

def compute_prefix_function(p):
	m = len(p)
	pi = [0]*(m)
	k = 0
	for q in range(1, m):
		while(k > 0 and p[k] != p[q]):
			k = pi[k-1]
		if p[k] == p[q]:
			k = k +1
		pi[q]=k
	return pi

def genPow2(n):
	s = 1
	while s < n:
		yield s
		s *= 2
def cmpare(a, b):
	res = 0
	if a[0] == b[0]:
		if a[1] < b[1]:
			res = -1
		elif a[1] > b[1]:
			res = 1
		
	elif a[0] < b[0]:
		res = -1
	else:
		res = 1
		
	return res	

def strcmp(t1, t2):
	if t1 == t2:
		res = 0
	elif t1 < t2:
		res = -1
	else: res = 1

	return res


def SUFFIX(pattern, text, sa = None):
	return SASM(pattern, text, sa)
def createSuffixArray(text, n):
	p = []
	for i in range(n):
		p.append(ord(text[i])-ord('a'))
	for step in genPow2(n):
		l = []
		for i in range(n):
			l.append((p[i], p[i + step] if i + step < n else -1, i))
		l.sort(key = cmp_to_key(cmpare))
		
		for i in range(n):
			p[l[i][2]] = p[l[i-1][2]] if i > 0 and l[i][0] == l[i-1][0] and l[i][1] == l[i-1][1] else i
	
	return list(map(lambda x: x[2], l))

#find lcp
def LCP(text,sa, i, n):
	i1 = sa[i-1]; i2 = sa[i]
	length = 0
	while i1 < n and i2 < n and text[i1] == text[i2] :
		length += 1
		i1 += 1
		i2 += 1
	
	return length
	
def SASM(pattern, text = None, sa = None):   # (suffix array string matching)
	n = len(text)
	m = len(pattern)
	isFound = False
	global startTime, endTime, preTime, searchTime
	startTime = round(time.time()*1000000)
	if not sa:
		sa = createSuffixArray(text, n)
	endTime = round(time.time()*1000000)
	preTime = endTime - startTime
	ls = []
	l = 0
	r = n -1
	startTime = round(time.time()*1000000)
	while l <= r and not isFound:
		mid = l + (r - l)//2
		res = strcmp(pattern, text[sa[mid]:sa[mid]+m])
		if res == 0:
			isFound = True

		elif res < 0:
			r = mid - 1
		else:
			l = mid + 1

	if isFound:
		lbound = mid
		ubound = mid+1
		while lbound > 0 and LCP(text,sa, lbound, n) >= m :
			lbound -= 1
		while ubound < n and LCP(text,sa, ubound, n) >= m :
			ubound += 1
		ubound -= 1
	
		ls = list(map(lambda x: sa[x], range(lbound, ubound+1)))
	endTime = round(time.time()*1000000)
	searchTime = endTime - startTime

	return ls,sa		

def TextRange(lst):
	arg1, arg2 = lst[0], lst[1]
	text = open("normalized.txt").read()
	if int != type(arg1):
		i1 = KMP(arg1,text)
		i2 = KMP(arg2, text)
		if len(i1) == 0 or len(i2) == 0: 
			arg1 = -1
			arg2 = -1
		else:
			arg1 = i1[0] + len(arg1)
			arg2 = i2[0]	
			
	if arg1 > arg2:
		arg1, arg2 = arg2, arg1	
	return (arg1, arg2)

def build_cross_index(txtfile, algo):
	readfile = open(txtfile).read()
	readfile = re.sub(' +\n', '\n', readfile)
	readfile = re.sub('\n\n\n+', '\n\n\n', readfile)
	stories = readfile.split("\n\n\n")	
	titles = []
	body = []
	stories = stories[1:]
	for i in stories:
		portions = i.split('\n\n')
		if len(portions)==1:
			body[-1]+=re.sub('\n', ' ',portions[0])
		elif len(portions)==2:
			titles.append(re.sub('\n', ' ',portions[0]))
			body.append(re.sub('\n', ' ',portions[1]))
		elif len(portions)==3:
			titles.append(re.sub('\n', ' ',portions[0]))
			body.append(re.sub('\n', ' ', portions[1]))
			body[-1]+=" "+re.sub('\n', ' ',portions[2])
		elif len(portions)==4:
			titles.append(re.sub('\n', ' ',portions[0]))
			body.append(re.sub('\n', ' ',portions[1]))
			titles.append(re.sub('\n', ' ',portions[2]))
			body.append(re.sub('\n', ' ',portions[3]))
	
	words = {}
	story_split = {}
	for i in range(len(titles)):
		sa = None
		line = body[i].split()
		temp = {}
		for j in line:
			if j not in temp:
				if algo==SUFFIX:
					(num_occurences,sa) = algo(j, body[i], sa)
				else:
					num_occurences = algo(j, body[i])
				num_occurences = len(num_occurences)
				temp[j] = num_occurences
				if j in words:
					words[j] += num_occurences
				else:
					words[j] = num_occurences
		story_split[i] = temp

	for i in sorted(words.keys()):
		print(i + " -> " + str(words[i]))
		print("----------------")
		for j in range(len(titles)):
			if i in story_split[j]:
				print(str(titles[j]) + " -> " + str(story_split[j][i]))
		print("++++++++++++++++++++++++++++++++++++++++++++++++++++++")
			
def palindromes(t, size, titles, body):

	results = []
	tot = {}
	for i in range(len(titles)):
		temp = body[i].split(" ")
		for k in range(len(temp)):
			for j in range(len(temp[k])):
				for q in range(0, j):
					part = temp[k][q:j+1]

					if part == part[::-1] and len(part) >= size:
						results.append(part)
		lst = {}
		for k in range(len(results)):
			lst[results[k]] = t.index(results[k])
		if len(lst) != 0:
			tot[titles[i]] = lst
	return tot

def palindrome_text(txtfile):
	readfile = open(txtfile).read()
	readfile = re.sub(' +\n', '\n', readfile)
	readfile = re.sub('\n\n\n+', '\n\n\n', readfile)
	stories = readfile.split("\n\n\n")	
	titles = []
	body = []
	stories = stories[1:]
	for i in stories:
		portions = i.split('\n\n')
		if len(portions)==1:
			body[-1]+=re.sub('\n', ' ',portions[0])
		elif len(portions)==2:
			titles.append(re.sub('\n', ' ',portions[0]))
			body.append(re.sub('\n', ' ',portions[1]))
		elif len(portions)==3:
			titles.append(re.sub('\n', ' ',portions[0]))
			body.append(re.sub('\n', ' ', portions[1]))
			body[-1]+=" "+re.sub('\n', ' ',portions[2])
		elif len(portions)==4:
			titles.append(re.sub('\n', ' ',portions[0]))
			body.append(re.sub('\n', ' ',portions[1]))
			titles.append(re.sub('\n', ' ',portions[2]))
			body.append(re.sub('\n', ' ',portions[3]))
	return (titles, body)

def maximal_palindrome(size):
	titles, body = palindrome_text('normalized.txt')
	text = open('normalized.txt').read()
	text = re.sub('\n', ' ', text)
	text = re.sub(' * ', ' ', text)
	com = palindromes(text, size, titles, body)
	if len(com)!=0:
		print(com)
	else:
		print("No palindromes found")

if __name__ == '__main__':
	time.time()
	global preTime, searchTime
	choice = None
	txtfile = 'txtfile.txt'
	while(True):
		print("Enter options")
		print("1. Find Length of Text")
		print("2. Find Pattern")
		print("3. Build Cross Index")
		print("4. Find Maximal Palindrome")
		print("5. Exit")
		choice = int(input())
		if choice == 1:
			txtfile = input("Enter file name: ")
			url, length = Find_Length_Of_Text(txtfile)
			print("Number of urls: ")
			print(len(url))
			print("The url list: ")
			for elem in url:
				print (elem)
			print("Length of text: ")
			print(length)
		elif choice == 2:
			pattern = input("Pattern to search: ")
			start_ind = input("Text range beginning: ")
			end_ind = input("Text range ending: ")
			if ord(start_ind[0]) < 65:
				start_ind, end_ind = int(start_ind), int(end_ind)
			textrange = [start_ind, end_ind]
			algo = input("Enter algo: KMP/RABIN_KARP/SUFFIX: ")
			if algo == 'KMP':
				algo = KMP
			elif algo == 'RABIN_KARP':
				algo = RABIN_KARP
			else:
				algo = SUFFIX
			print("Number of occurences of "+pattern+" in given text range is: ", end="")
			print(Find_Pattern(pattern, textrange, algo))
			print("Preprocessing Time: ")
			print(preTime)
			print("Search Time: ")
			print(searchTime)
		elif choice == 3:
			algo = input("Enter algo: KMP/RABIN_KARP/SUFFIX: ")
			if algo == 'KMP':
				algo = KMP
			elif algo == 'RABIN_KARP':
				algo = RABIN_KARP
			else:
				algo = SUFFIX
			build_cross_index('normalized.txt', algo)
		elif choice == 4:
			size = int(input("Enter maximal palindrome size: "))
			maximal_palindrome(size)
		elif choice == 5:
			break
