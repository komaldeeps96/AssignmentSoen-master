import csv
import subprocess, shlex
import numpy as np
import pandas


filepath = []
 
with open('file_paths.csv') as csvDataFile:
    csvReader = csv.reader(csvDataFile)
    for row in csvReader:
        filepath.append(row[0])
kwargs={}
kwargs['stdout']= subprocess.PIPE
kwargs['stderr']= subprocess.PIPE

counter=0
#filepath= [ "hadoop-common-project/hadoop-annotations/src/main/java/org/apache/hadoop/classification/InterfaceAudience.java" ]
authors=[]


finaldict= {}
X= len(filepath)
i=0
while i < X:
	git_cmd = "git shortlog -s -e --before=\"05 Apr 2018\" "+ filepath[i]
	proc= subprocess.Popen(shlex.split(git_cmd), **kwargs)
	(stdout_str, stderr_str)= proc.communicate()
	return_code= proc.wait()


	test1= stdout_str.decode('ascii').splitlines()
	authorset= set()
	for temp in test1:
		start1= temp.find("<")
		temp=temp[start1: len(temp)]
		authorset.add(temp)

	print(authorset)
	print("\n")
	counter=counter+1
	print(counter)
	finaldict[filepath[i]]= len(authorset)
	i+=1



df = pandas.DataFrame.from_dict(finaldict, orient="index")
df.to_csv("code_ownership.csv")


