# 基础语法

## 变量

变量不需要声明。

变量在内存中创建，都包括变量的标识，名称和数据信息。

变量使用前必须赋值，变量赋值后才会被创建

### 基本类型创建

#### 整数类型

```python
a = 100
```

#### 浮点类型

```python
b = 100.0
```

#### 字符串类型

```python
name = 'john'
```



## 数据结构

### 列表

列表可以储存多种不同类型的元素

#### 创建

```python
list1 = ['张三'，'李四','王五',['身高','体重']];
```

#### 访问

##### 正向索引：

```python
print(list1[0]);//获取第一个元素
```

##### 反向索引：

```python
print(list1[-1]);//获取最后一个元素
```

##### 切片：

语法[start​ : end : step],step默认为1

```Python
list2 = ['江苏','安徽','浙江','上海','山东','山西','湖北','湖南'];
print(list2[2,6]);#第三个元素到第6个元素 共四个
print(list2[1:6:2]);#安徽，上海，山西
print(list2[-3:-1]);#山西,湖南
```

##### 无限索引：

前n个元素[:n]

后n个元素[-n:]

所有元素[::]

奇数位置[::2]

#### 改变列表

##### 增加

append(x):在尾部增加一个元素x,list特有

extend(list):在尾部增加另外一个列表的元素

insert(n,list):在第n个元素后面插入list

```python
list3 = [1,10,100,1000,10000];
list3.append(100000);
print(list3);
list3.extend([1]);
print(list3);
list3.insert(2,999);#在10后添加999这个元素
print(list3);
```

##### 删除

pop([index]):

```python
list3.pop();#从后面删除一个元素
list3.pop(n);#删除索引为n的元素
```

remove(x):查找并删除

```Python
list3.remove(1000);#从list3中删除值为1000的元素
```

clear():清空元素

```python
list3.clear();
```

##### 修改

```python
list4 = ["山东","菏泽","曹县","牛逼","666","我的","包被二"];
list4[-1] = "宝贝儿";
```

#### 内置方法

有如下列表分别用不同方法处理

```
list5 = [7,3,9,11,4,6,10,3,7,4,4,3,6,3];
```

##### .count

计算某个元素的个数

```
list5.count(3)#查询3的个数
```

##### .index

查找某个元素从前到后第一次出现的索引

```
list5.index(3)
```

##### .reverse

原地颠倒列表

```
list5.reverse();
```

##### .sort

原地从小到大排序，参数reverse默认为False，如果为True从大到小

```
list5.sort(reverse = True);
```

### 元组

元祖大致上与list类似但是是不可变类型，速度较list更快

#### 创建

```python
t = (1,2,3,'a');
```

#### 访问

同list

```python
print(t[0])#第一个元素
print(t[-1])#最后一个元素
print(t[-3:])#最后三个元素
print(t[:3])#前三个元素
```

#### 改变元组

无法对一个元组进行修改。

#### 内置方法

.index .count 同list

### 字典

字典以key-val的形式储存数据

#### 创建

```python
dict1 = {'姓名':"张三",'年龄':19,'兴趣':['足球','游泳'],'子女':{'儿子':"张四"}};
```

#### 访问

```Python
print(dict1['姓名']);
```

#### 改变字典

##### 增加

setdefault(key,value):添加一个元素

update(dict):将另一个字典的元素添加到当前字典

[key]=value：插入key 和 value

```
dict1.setdefault('户籍','合肥')
dict1.update({'学历':'硕士'})
dict1['身高'] = 178
```

##### 删除

pop(key):删除key及其对应的val

popitem():删除任意一个key-val

clear():删除所有元素

```
dict1.pop('户籍')
dict1.popitem()
dict1.clear()
```

##### 修改

update(dict2)#对于dict2中的元素,self中有的key则改为dict2中的值，没有的则插入

[key] = val#修改或添加key-val

#### 内置方法

##### .get

取出键对应的值

```python
dict1.get('年龄')
```

##### .keys

取出所有的键

```python
dict1.keys();
#dict_keys(['姓名', '年龄', '兴趣', '子女'])
```

##### .values

取出所有的值

```python
dict1.values()
#dict_values(['张三', 19, ['足球', '游泳'], {'儿子': '张四'}])
```

##### .items()

取出所有的键值对,可用于迭代

```python
dict1.items()
#dict_items([('姓名', '张三'), ('年龄', 19), ('兴趣', ['足球', '游泳']), ('子女', {'儿子': '张四'})])
```

### 字符串

#### 创建

```python
str1 = 'abc"sdaf",'
str2 = "safd'asdf'"#双引号可以包含单引号
str2 = '''asdf'', "sadf"
asdf
asdf''' #三引号中可以包含换行，可以包含双引号和单引号
```

#### 内置方法

[start: end : split​] 切片同list

.split(x) :以x为分隔符分割字符串

```python
print("123@example.com".split('@')) 
#['12345','example.com']
```

.replace(from,to):将字符串中所有from替换为to

```python
tel = "13612348697";
print(tel.replace(tel[3:7],"****"))
#136****8697
```

.join(x):将字符串中所有字符间用x分割

```python
print("python".join('-'))
#p-y-t-h-o-n
```

.strip .lstrip .rstrip：去除  所有/左边/右边  空白

```python
print("   sadf   ".strip())
#sadf
```

.count(x)查找字符串中出现x的次数

```python
print("can you can a can as a canner can can a can".count("can"))
#7
```

.find(x) .index(x):从字符串找出第一个x的下标

```python
print("can you can a can as a canner can can a can".find("can"))
print("can you can a can as a canner can can a can".index("can"))
```

.startswith(x) .endswith(x):字符串是否以x结尾/开头

```python
print("asdf fdsa".startswith("asdf"));
#True
```

#### 正则表达式

包：import re

| 字符         | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| \            | 将下一个字符标记为一个特殊字符、或一个原义字符、或一个 向后引用、或一个八进制转义符。例如，'n' 匹配字符 "n"。'\n' 匹配一个换行符。序列 '\\' 匹配 "\" 而 "\(" 则匹配 "("。 |
| ^            | 匹配输入字符串的开始位置。如果设置了 RegExp 对象的 Multiline 属性，^ 也匹配 '\n' 或 '\r' 之后的位置。 |
| $            | 匹配输入字符串的结束位置。如果设置了RegExp 对象的 Multiline 属性，$ 也匹配 '\n' 或 '\r' 之前的位置。 |
| *            | 匹配前面的子表达式零次或多次。例如，zo* 能匹配 "z" 以及 "zoo"。* 等价于{0,}。 |
| +            | 匹配前面的子表达式一次或多次。例如，'zo+' 能匹配 "zo" 以及 "zoo"，但不能匹配 "z"。+ 等价于 {1,}。 |
| ?            | 匹配前面的子表达式零次或一次。例如，"do(es)?" 可以匹配 "do" 或 "does" 。? 等价于 {0,1}。 |
| {n}          | n 是一个非负整数。匹配确定的 n 次。例如，'o{2}' 不能匹配 "Bob" 中的 'o'，但是能匹配 "food" 中的两个 o。 |
| {n,}         | n 是一个非负整数。至少匹配n 次。例如，'o{2,}' 不能匹配 "Bob" 中的 'o'，但能匹配 "foooood" 中的所有 o。'o{1,}' 等价于 'o+'。'o{0,}' 则等价于 'o*'。 |
| {n,m}        | m 和 n 均为非负整数，其中n <= m。最少匹配 n 次且最多匹配 m 次。例如，"o{1,3}" 将匹配 "fooooood" 中的前三个 o。'o{0,1}' 等价于 'o?'。请注意在逗号和两个数之间不能有空格。 |
| ?            | 当该字符紧跟在任何一个其他限制符 (*, +, ?, {n}, {n,}, {n,m})  后面时，匹配模式是非贪婪的。非贪婪模式尽可能少的匹配所搜索的字符串，而默认的贪婪模式则尽可能多的匹配所搜索的字符串。例如，对于字符串  "oooo"，'o+?' 将匹配单个 "o"，而 'o+' 将匹配所有 'o'。 |
| .            | 匹配除换行符（\n、\r）之外的任何单个字符。要匹配包括 '\n' 在内的任何字符，请使用像"**(.\|\n)**"的模式。 |
| (pattern)    | 匹配 pattern 并获取这一匹配。所获取的匹配可以从产生的 Matches 集合得到，在VBScript 中使用 SubMatches 集合，在JScript 中则使用 $0…$9 属性。要匹配圆括号字符，请使用 '\(' 或 '\)'。 |
| (?:pattern)  | 匹配 pattern 但不获取匹配结果，也就是说这是一个非获取匹配，不进行存储供以后使用。这在使用 "或" 字符 (\|)  来组合一个模式的各个部分是很有用。例如， 'industr(?:y\|ies) 就是一个比 'industry\|industries'  更简略的表达式。 |
| (?=pattern)  | 正向肯定预查（look ahead positive  assert），在任何匹配pattern的字符串开始处匹配查找字符串。这是一个非获取匹配，也就是说，该匹配不需要获取供以后使用。例如，"Windows(?=95\|98\|NT\|2000)"能匹配"Windows2000"中的"Windows"，但不能匹配"Windows3.1"中的"Windows"。预查不消耗字符，也就是说，在一个匹配发生后，在最后一次匹配之后立即开始下一次匹配的搜索，而不是从包含预查的字符之后开始。 |
| (?!pattern)  | 正向否定预查(negative  assert)，在任何不匹配pattern的字符串开始处匹配查找字符串。这是一个非获取匹配，也就是说，该匹配不需要获取供以后使用。例如"Windows(?!95\|98\|NT\|2000)"能匹配"Windows3.1"中的"Windows"，但不能匹配"Windows2000"中的"Windows"。预查不消耗字符，也就是说，在一个匹配发生后，在最后一次匹配之后立即开始下一次匹配的搜索，而不是从包含预查的字符之后开始。 |
| (?<=pattern) | 反向(look behind)肯定预查，与正向肯定预查类似，只是方向相反。例如，"`(?<=95|98|NT|2000)Windows`"能匹配"`2000Windows`"中的"`Windows`"，但不能匹配"`3.1Windows`"中的"`Windows`"。 |
| (?<!pattern) | 反向否定预查，与正向否定预查类似，只是方向相反。例如"`(?<!95|98|NT|2000)Windows`"能匹配"`3.1Windows`"中的"`Windows`"，但不能匹配"`2000Windows`"中的"`Windows`"。 |
| x\|y         | 匹配 x 或 y。例如，'z\|food' 能匹配 "z" 或 "food"。'(z\|f)ood' 则匹配 "zood" 或 "food"。 |
| [xyz]        | 字符集合。匹配所包含的任意一个字符。例如， '[abc]' 可以匹配 "plain" 中的 'a'。 |
| [^xyz]       | 负值字符集合。匹配未包含的任意字符。例如， '[^abc]' 可以匹配 "plain" 中的'p'、'l'、'i'、'n'。 |
| [a-z]        | 字符范围。匹配指定范围内的任意字符。例如，'[a-z]' 可以匹配 'a' 到 'z' 范围内的任意小写字母字符。 |
| [^a-z]       | 负值字符范围。匹配任何不在指定范围内的任意字符。例如，'[^a-z]' 可以匹配任何不在 'a' 到 'z' 范围内的任意字符。 |
| \b           | 匹配一个单词边界，也就是指单词和空格间的位置。例如， 'er\b' 可以匹配"never" 中的 'er'，但不能匹配 "verb" 中的 'er'。 |
| \B           | 匹配非单词边界。'er\B' 能匹配 "verb" 中的 'er'，但不能匹配 "never" 中的 'er'。 |
| \cx          | 匹配由 x 指明的控制字符。例如， \cM 匹配一个 Control-M 或回车符。x 的值必须为 A-Z 或 a-z 之一。否则，将 c 视为一个原义的 'c' 字符。 |
| \d           | 匹配一个数字字符。等价于 [0-9]。                             |
| \D           | 匹配一个非数字字符。等价于 [^0-9]。                          |
| \f           | 匹配一个换页符。等价于 \x0c 和 \cL。                         |
| \n           | 匹配一个换行符。等价于 \x0a 和 \cJ。                         |
| \r           | 匹配一个回车符。等价于 \x0d 和 \cM。                         |
| \s           | 匹配任何空白字符，包括空格、制表符、换页符等等。等价于 [ \f\n\r\t\v]。 |
| \S           | 匹配任何非空白字符。等价于 [^ \f\n\r\t\v]。                  |
| \t           | 匹配一个制表符。等价于 \x09 和 \cI。                         |
| \v           | 匹配一个垂直制表符。等价于 \x0b 和 \cK。                     |
| \w           | 匹配字母、数字、下划线。等价于'[A-Za-z0-9_]'。               |
| \W           | 匹配非字母、数字、下划线。等价于 '[^A-Za-z0-9_]'。           |
| \xn          | 匹配 n，其中 n 为十六进制转义值。十六进制转义值必须为确定的两个数字长。例如，'\x41' 匹配 "A"。'\x041' 则等价于 '\x04' & "1"。正则表达式中可以使用 ASCII 编码。 |
| \num         | 匹配 num，其中 num 是一个正整数。对所获取的匹配的引用。例如，'(.)\1' 匹配两个连续的相同字符。 |
| \n           | 标识一个八进制转义值或一个向后引用。如果 \n 之前至少 n 个获取的子表达式，则 n 为向后引用。否则，如果 n 为八进制数字 (0-7)，则 n 为一个八进制转义值。 |
| \nm          | 标识一个八进制转义值或一个向后引用。如果 \nm 之前至少有 nm 个获得子表达式，则 nm 为向后引用。如果 \nm  之前至少有 n 个获取，则 n 为一个后跟文字 m 的向后引用。如果前面的条件都不满足，若 n 和 m 均为八进制数字 (0-7)，则 \nm  将匹配八进制转义值 nm。 |
| \nml         | 如果 n 为八进制数字 (0-3)，且 m 和 l 均为八进制数字 (0-7)，则匹配八进制转义值 nml。 |
| \un          | 匹配 n，其中 n 是一个用四个十六进制数字表示的 Unicode 字符。例如， \u00A9 匹配版权符号 (?)。 |

##### findall

findall(pattern,string,flags = 0)

函数对指定字符串进行遍历匹配获取字符串中所欧匹配的子串，并返回一个列表包含所有符合子串的结果

- pattern :正则表达式
- string：待匹配字符串
- flags:可以为re.I,re.M,re.S,re.X    ，re.I大小写不敏感，re.M多行匹配，re.S表明.可以匹配任意符号包括\n，re.X允许正则表达式写的更加详细

```python
str1 = "Together, we discovered that a free market only thrives when there are rules to ensure competition and fair play."
print(re.findall("\w*o\w*",str1,flags = re.I))
#['Together', 'discovered', 'only', 'to', 'competition']
```

**当正则表达式中有括号子表达式时  结果将只包含子表达式的内容，如果有多个子表达式则以元祖方式排列 ，子表达式嵌套时结果元组不嵌套，按照深度优先排列嵌套表达式匹配结果，如下：**

```python
import re;
str1 = "Together, we discovered that a free market only thrives when there are rules to ensure competition and fair play."
print(re.findall("(((\w*))(o)(\w*))",str1,flags = re.I))
#[('Together', 'T', 'T', 'o', 'gether'), ('discovered', 'disc', 'disc', 'o', 'vered'), ('only', '', '', 'o', 'nly'), ('to', 't', 't', 'o', ''), ('competition', 'competiti', 'competiti', 'o', 'n')]
```

##### sub

sub(pattern,repl ,string,count = 0,flags = 0)

替换，将满足pattern的部分替换为string

- pattern：正则表达式
- repl：替换目标值
- string:待匹配字符串
- count :替换次数
- flags:同findall

```python
str2 = "在1987年11月，世界卫生组织（WHO）在日本东京举行的第6届吸烟与健康国际会议上建议把每年的4月7日定为世界无烟日（World No Tobacco Day）"
print(re.sub("[，。、a-zA-Z0-9（）]","",str2))
#在年月世界卫生组织在日本东京举行的第届吸烟与健康国际会议上建议把每年的月日定为世界无烟日 
```

##### split

split(pattern,string,maxsplit=0,flags = 0)

将字符串按照正则表达式分隔开，类似于字符串的split

- pattern:正则表达式
- maxsplit：最大分割次数，默认为全部分割
- string：被分割字符串
- flags：同findall

TODO:code

## 控制流

控制流关键字管理不同代码块是否被执行和执行顺序，Python代码块靠缩进区分

#### if分支

双分支：

```python
if condition1:
	expression1
else condtion2:
	expression2
```

多分支：

```Python
if condition1:
	expression1
elif condtion2:
	expression2
else condtion3:
	expression3
```

#### for 循环

##### 迭代数字

```python
for i in range(1,101)
	print(i)#i 将从1到100迭代
```

##### 迭代容器

```python
list6 = [1,5,2,8,10];
for i in list6:
    print(i)# 迭代输出list6中的元素
```

#### while 循环

```python
while condition:
	expression
```

## 函数

#### 定义

```python
def func_name(param1,param2):
	expression
	return result
```

#### 参数类型

##### 必选参数

定义中的param1，param2都是必选参数

##### 默认参数

```python
def func(param1,param2 = 10):#其中param2 是可选参数，默认为10
	expression
	return result
```

##### 可变参数

```python
def func(*args):
	print(args);
func(1,2,3,5,4,7,8);
#(1, 2, 3, 5, 4, 7, 8)
```

##### 关键字参数

关键字参数将用户随意指定的参数转换为字典，如下：

```
def func(**arg):
	print(arg)
func(name = "danny",age = "19");
#{'name': 'danny', 'age': '19'}
```

# 其他库

## Numpy库

import numpy as np

### 数组

#### 创建

```python
arr1 = np.array([1,2,3,4,5,6]);
arr2 = np.array((1,2,3,4,5));
arr3 = np.array([[1,2,3],[4,5,6],[7,8,9]])#嵌套列表创建多维数组
arr4 = np.array(((1,2,3),(4,5,6),(7,8,9))) #嵌套元组创建多维数组
```

#### 访问

[]运算符中可以接收一个列表作为参数，获取以该列表中的元素为下标位置的元素

```python
print(arr1[0])
print(arr1[[1,2,3]])
```

对于多维数组arr5[a,b,c,.....]代表a行b列c层......

完整语法为：arr[a:b,c:d,......]a行-b行的c列-d列

np.ix_() 函数接受一个可变参数列表，可以传入多个list 第一个是行第二个是列第三个是....

#### 数组属性

```python
arr = np.array(((1,2,3),(4,5,6),(7,8,9))
```

##### 数组类型

type(arr)

##### 数组维度

```python
print(arr.ndim)
#2
```

##### 数组形状

```python
print(arr.shape)
# (3,3)
```

##### 元素类型

```python
print(arr.dtype)
#int64
```

##### 元素总数

```python
print(arr.size)
```

#### 改变形状

##### reshape

非原地操作,返回视图

reshape 接受一个可变参数列表，可以传入任意多个整数，

传入一个n表示将数组变为一个长度为n的一维数组，传入m,n两个整数代表将数组变为一个m行n列的数组

```python
arr = np.array(((1,2,3),(4,5,6),(7,8,9)))
print(arr.reshape(9));
#[1 2 3 4 5 6 7 8 9]
```

##### resize

原地操作

resize参数同reshape，但为原地操作

```python
arr.resize(9)
print(arr)
#[1 2 3 4 5 6 7 8 9]
```

##### ravel

原地操作，返回视图（引用）

ravel 按行顺序将数组降维，

- order =  'C' | 'F' | 'A' |'K'  ， 'C'按行顺序，'F'按列顺序

```python
arr.ravel(9)
print(arr)
#[1 2 3 4 5 6 7 8 9]
```

##### flatten

非原地操作，返回复制后的数组

参数功能同ravel

```python
print(arr.flatten(9))
#[1 2 3 4 5 6 7 8 9]
```

#### 堆叠

vstack()/hstack():水平堆叠 /垂直堆叠

```python
arr1 = np.array((10,100,1000))
arr2 = np.array(((1,2,3),(4,5,6),(7,8,9)))
arr3 = np.array([[10],[100],[1000]])
print(np.vstack([arr2,arr1]))
'''
输出：
[[   1    2    3]
[   4    5    6]
[   7    8    9]
[  10  100 1000]]
'''
print(np.hstack([arr2,arr3]))
'''
输出：
[[   1    2    3   10]
 [   4    5    6  100]
 [   7    8    9 1000]]
'''
```

#### 运算

##### 数与数组运算：

//整除 ，% 求模 ，**乘方，+加，-减，\*乘，/除以 ，进行运算时会对数组内每一个元素进行运算

##### 数组间运算

不同数组可以运算的条件:

- 各输入数组维度可以不相同，但必须确保从右到左的维度值相同
- 如果对应的维度值不相等，必须保证其中一个是1

```python
print(arr4==arr4)
'''
[[ True  True  True]
 [ True  True  True]
 [ True  True  True]]
'''
```

