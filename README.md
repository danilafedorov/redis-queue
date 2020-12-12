##### Intro:

This is small example with Redis RSet (distributed set). 

- PutThread's put random data in queue
- GetThread's get data as collection

###### The result:
-  RSet doesn't use equals and hashcode
-  RSet very fast
-  sequential locks not good:) 

