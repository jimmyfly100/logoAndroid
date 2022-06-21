# logoAndroid
### 在安卓平台上实现仿照Logo语言的画图编程。
命令不区分大小写,以空格或换行控制语法，不支持逗号，分号等 

FD（前进）

BK（后退）

RT（右转）

LT（左转）

REPEAT（重复）

FUNC（自定义函数）

如：
REPEAT 4[FD 100 RT 90] 绘制一个边长100的正方形

### 支持定义变量(只支持double，即数字变量)
如：
A = 5 B = 90 REPEAT 100[FD A+5 RT 90 A=A+5] 绘制一个螺旋迷宫状图案 

### 支持自定义函数
如
FUNC Polygon length num [REPEAT num[FD length RT 360/num]] Polygon 100 6 定义一个多边形函数，调用绘制一个六边形

### 支持函数保存本地/从本地读取

todo: 
1.图形页面缩放
2.运算目前只支持从左算到右，不支持小括号以及乘除优先于加减这些
