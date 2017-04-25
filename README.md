# Advanced_Algorithms

Problem Statement Assignment 1 - 
1. Generate two NxN matrices M1 and M2, with float data type, using built in random number  functions, of your dev environment. (Time taken = T1 and T2 respectively for M1 and M2)
2. Generate the product M1xM2 using 
      a. Standard inner product based row_col multiplication algorithm. (result = M3, Time  taken = T3) 
      b. O(n^3) Divide_n_Conquer strategy_based multiplication algorithm. (result = M4,  Time taken = T4) 
      c. Strassen's recursive divide_n_conquer strategy_based algorithm. (result = M5, Time  taken = T5)
3. Verify equality of M3, M4 and M5. (Result = TRUE/FALSE, Time Taken = T6)
4. Vary the input size to cover the following input sizes N = 16, 64, 256, 512, 1024, 2048,  4096 and log the times T3, T4, T5 and T6 and check against theoretical complexity growth  expected.
5. Enable/disable processor cores on your laptop to check and log the performance.
6. Change the loop access pattern in the algorithms (to be controlled by a exec switch option  -Row or -Col as in a Linux shell command) and log timing.
7. Finally let us know your observations, approximate number of hours spent on the  assignment and learning outcomes about the assignment


Problem Statement Assignment 2 -
 In the Text file ( AESOP TALES.txt ) provided as data
 
 1. Develop implementations  for the interface spec below: 
    a.  Find_Length_of _Text(txtfile) - normalize multiple blank chars to single blank char and remove(store) website URLS that have infected textfile using FSA based RegEx matcher.
      
    b.  Find_Pattern (pattern , InTextRange,  algo) - find the number of occurences of PATTERN using any one of the following algorithms ( 2nd parameter ) - Rabin-Karp,  Knuth_Morris_Pratt, Suffix Tree (with Suffix arrays & LCP).
 InTextRange - can be indices or two patterns ( eg. two story titles )
 
    c.   Build_Cross_Index( txtfile, algo ) - Build an Index ( Lex sorted ) ( WORD, Number of occurences, List of story titles & number of occurences of WORD)
      
    d.   Find_Maximal,Palindromes( PalindromeSize,  InTextRange ) - list maximal palindromes of size greater than or equal to PalindromeSize, with occurences ( Story titles and indices )
      
    e.   Print_Stats()  - Text Size used, URL infection list found, Algo used, Preprocessing time, Search time ( Vary  the parameters pattern,  InTextRange ) for timing plot and self test and verification outcome )
      
      
