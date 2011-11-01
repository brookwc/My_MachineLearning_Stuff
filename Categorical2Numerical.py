# coding=utf-8

import sys


def main(args):
	sys.stderr.write("Usage: categorical2numerical feature\n")

	null = '__NULL__'

	targetFeature = args[0]

	header = sys.stdin.readline()
	features = header.strip().split('\t')
	pos = features.index(targetFeature)
	if pos < 0:
		sys.exit("feature does not exist in data file - aborting...\n")
	
	sys.stderr.write("pos = " + str(pos))
	
	sys.stdout.write(header);

	valueMap = {}
	cnt = 0
	for ln in sys.stdin:
		vals = ln.split('\t')
		
		val = vals[pos]
		
		if val == null or len(val) == 0: continue
		else:
			if (not valueMap.has_key(val)):
				valueMap[val] = str(len(valueMap) + 1)

			vals[pos] = valueMap[val]	

			sys.stdout.write('\t'.join(vals))

		cnt += 1
		if cnt % 1000 == 0:  
			sys.stderr.write(str(cnt) + ' lines processed\n')
	sys.stdin.close()
	sys.stdout.close()


if __name__ == "__main__":
        main(sys.argv[1:])
