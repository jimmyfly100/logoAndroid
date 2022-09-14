# logoAndroid
### An Android implementation of Logo-language
Commands are not case-sensitive, control syntax with spaces or line breaks, and do not support commas, semicolons, etc.

FD（go forward）

BK（go backward）

RT（turn right）

LT（turn left）

REPEAT（repeat the command）

PU (take up the pen)
PD (put down the pen)
SETXY （set XY axis）
SETH  （set angle）

For example：
REPEAT 4[FD 100 RT 90] //Draw a square with a side length of 100

### Four arithmetic operations are supported
Like：
(3+5)*8

### Support defining variables (only numeric variables)
For example：
A = 5 B = 90 REPEAT 100[FD A+5 RT 90 A=A+5]  //Draw a spiral maze-like pattern 

### Support defining functions
TO FuncName :param1 :param2

FD param1 RT param2

END

For example:
TO STAR :length REPEAT 5[FD length RT 144] END STAR 400 //Define a pentagram function, call and draw a pentagram with a customizable side length

### Support saving funtions to disk/loading funcions from disk

### Download and install logoAndroid_0.4.apk on your Android phone to use. 
### 在安卓平台上实现仿照Logo语言的画图编程。
命令不区分大小写,以空格或换行控制语法，不支持逗号，分号等 

FD（前进）

BK（后退）

RT（右转）

LT（左转）

REPEAT（重复）

PU (抬笔)
PD (落笔)
SETXY （设置xy坐标）
SETH  （设置角度朝向）
PX （异或模式）
PPT （画图模式（默认））

上述命令不区分大小写

如：
REPEAT 4[FD 100 RT 90] 绘制一个边长100的正方形

### 支持四则运算
如：
(3+5)*8

### 支持定义变量(只支持double，即数字变量)
如：
A = 5 B = 90 REPEAT 100[FD A+5 RT 90 A=A+5] 绘制一个螺旋迷宫状图案 

### 支持自定义函数
TO FuncName :param1 :param2

FD param1 RT param2

END

如
TO STAR :length REPEAT 5[FD length RT 144] END STAR 400 定义一个五角星函数，调用绘制一个边长可自定义的五角星

### 支持函数保存本地/从本地读取

