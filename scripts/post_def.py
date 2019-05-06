import csv
import subprocess, shlex
import numpy as np
import pandas
import itertools
from collections import Counter

dates = []
 
with open('post_rel_jira.csv') as csvDataFile:
    csvReader = csv.reader(csvDataFile)
    for row in csvReader:
        dates.append(row[0])
kwargs={}
kwargs['stdout']= subprocess.PIPE
kwargs['stderr']= subprocess.PIPE


#dates= [ "HADOOP-15947", "HADOOP-15781" ]
outp=[]
test =[]
X= len(dates)
i=0
while i < X:
	git_cmd = "git log --name-only --pretty=format: --grep="+ dates[i]
	proc= subprocess.Popen(shlex.split(git_cmd), **kwargs)
	(stdout_str, stderr_str)= proc.communicate()
	return_code= proc.wait()

	outp.append( (dates[i]+ "\n"+stdout_str.decode('ascii')).splitlines())
	test.append(stdout_str.decode('ascii').splitlines())
	i+=1
	

flattened_list  = list(itertools.chain(*test))

count=dict(Counter(flattened_list))
print(count)

df = pandas.DataFrame.from_dict(count, orient="index")
df.to_csv("post_rel_defects.csv")


