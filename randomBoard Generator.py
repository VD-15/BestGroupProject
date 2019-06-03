import random

charSet = ['1','2','3','4','-','+','x','.','^','v','<','>','W','E','N','S','w','e','n','s']

boardFile = "RandomBoard.brd"
width = 40
height = 40
random.seed = 2


def randomBoard():
    f = open(boardFile,"w+")
    f.write("format 1\n")
    for i in range(height):
        line = ""
        for i in range(width):
            line+= charSet[random.randint(0, len(charSet) - 1)]
        line+='\n'
        f.write(line)
    f.close()

randomBoard()

print("board file generated")
