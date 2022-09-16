### Tuple

##### usage

```rust
let tup:(i32,f64,u8)=(500,64.0,1)
let (x,y,z) = tup;
println!("{},{},{}",x,y,z);
println!("{},{},{}",tup.0,tup.1,tup.2);
```

### Array

##### 使用方法

```rust
//普通声明方法
let a = [1,2,3];
let a:[i32;5] = [1,2,3,4,5]
//另一种声明方法
let a =[3;5];// is same with let a =[3,3,3,3,3];
```

##### 访问方法

let first = months[0];

let second = months[1];

##### 异常情况

数组下标越界

编译：弱检查（error）

运行：报错（panic）



### 语句和表达式

##### 使用方法

```
let y = {
	let x = 1;
	x+3//
};
```



### 函数

##### 使用方法

```
fn func()
{
	println!("calling func");
	func2();
}
fn func2(x:i32)
{
	println!("{}",x);
}
```

##### 函数返回值

```
fn five(x:i32) -> i32{
	5+ x//相当于 return 5;
}
```

函数不指定返回值的话返回值默认为空元组

### 控制流关键字

##### if表达式

```
if 表达式 {

}else{

}
```

if或者else关键字的{}不能省略除非else后面紧跟一个if

if可以视作一个表达式

```
let number = if condition{5} else {6};
```

##### 循环

loop 循环

```rust
loop{
`	if ... {
    	break;
    }
}
```

loop和while(true)是一样的

while循环

```rust
let mut number = 3;
while number != 0
{
	println!("{},",number);
	number=number -1;
}
```

for循环

```rust
let a = [1,2,3,4];
for element in a.iter()
{
	println!("{},",element);
}
```

Range

```rust
for a in (1..4){
	println!("{},",a)//1,2,3,
}
for a in (1..4).rev(){
	println!("{}",a)//3,2,1,
}
```

### 所有权

##### 规则

每个值都有一个变量，这个变量是这个值的所有者

每个值同时只能有一个变量

当所有者超出作用域的时候，该值被删除

 ##### rust内存分配

当变量超出作用域的时候会自动删除该变量并调用函数drop();

##### 变量和数据的交互

###### move语义（c++移动语义 ）
rust中的类型，如果没有实现Copy trait，那么在此类型的变量赋值、函数入参、函数返回值都是move语义。这是与c++的最大区别，从c++11开始，右值引用的出现，才有了move语义。但rust天生就是move语义。

如下的代码中，变量a绑定的String实例，被move给了b变量，此后a变量就是不可访问了（编译器会帮助我们检查）。然后b变量绑定的String实例又被move到了f1函数中，，b变量就不可访问了。f1函数对传入的参数做了一定的运算后，再将运算结果返回，这是函数f1的返回值被move到了c变量。在代码结尾时，只有c变量是有效的。

fn f1(s: String) -> String {    s + " world!"}
let a = String::from("Hello");let b = a;let c = f1(b);
注意，如上的代码中，String类型没有实现Copy trait，所以在变量传递的过程中，都是move语义。

###### copy语义（c++普通赋值）
rust中的类型，如果实现了Copy trait，那么在此类型的变量赋值、函数入参、函数返回值都是copy语义。这也是c++中默认的变量传递或赋值的语义。

看看类似的代码，变量a绑定的i32实例，被copy给了b变量，此后a、b变量同时有效，并且是两个不同的实例。然后a变量绑定的i32实例又被copy到了f1函数中，a变量仍然有效。传入f1函数的参数i是一个新的实例，做了一定的运算后，再将运算结果返回。这时函数f1的返回值被copy到了c变量，同时f1函数中的运算结果作为临时变量也被销毁（不会调用drop，如果类型实现了Copy trait，就不能有Drop trait）。传入b变量调用f1的过程是相同的，只是返回值被copy给了d变量。在代码结尾时，a、b、c、d变量都是有效的。

fn f2(i: i32) -> i32 {    i + 10}
let a = 1_i32;let b = a;let c = f1(a);let d = f1(b);
这里再强调下，i32类型实现了Copy trait，所以整个变量传递过程，都是copy语义。

###### clone语义（c++相当于重载后的深拷贝）
move和copy语义都是隐式的，clone需要显式的调用。

参考类似的代码，变量a绑定的String实例，在赋值前先clone了一个新的实例，然后将新实例move给了b变量，此后a、b变量同时有效。然后b变量在传入f1函数前，又clone一个新实例，再将这个新实例move到f1函数中。f1函数对传入的参数做了一定的运算后，再将运算结果返回，这里函数f1的返回值被move到了c变量。在代码结尾时，a、b、c变量都是有效的。

fn f1(s: String) -> String {    s + " world!"}
let a = String::from("Hello");let b = a.clone();let c = f1(b.clone());
在这个过程中，在隐式move前，变量clone出新实例并将新实例move出去，变量本身保持不变。

###### drop语义（相当于c++析构函数）
rust的类型可以实现Drop trait，也可以不实现Drop trait。但是对于实现了Copy trait的类型，不能实现Drop trait。也就是说Copy和Drop两个trait对同一个类型只能有一个。

变量在离开作用范围时，编译器会自动销毁变量，如果变量类型有Drop trait，就先调用Drop::drop方法，做资源清理，一般会回收heap内存等资源，然后再收回变量所占用的stack内存。如果变量没有Drop trait，那就只收回stack内存。

正是由于在Drop::drop方法会做资源清理，所以Copy和Drop trait只能二选一。如果类型实现了Copy trait，在copy语义中并不会调用Clone::clone方法，不会做deep copy，那就会出现两个变量同时拥有一个资源（比如说是heap内存等），在这两个变量离开作用范围时，会分别调用`Drop::drop`方法释放资源，这就会出现double free错误。
————————————————
版权声明：本文为CSDN博主「Rust语言中文社区」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u012067469/article/details/106045528/

### 引用和借用

##### 语法

```rust
fn calculate_length( s:  &String) -> usize
{
	s.len()
}
```

把引用作为函数参数这个行为叫做借用

借用默认是不可变的（不能修改借用的变量）

##### 引用使用条件

对于可变引用&mut Type ， 不能有多个可变引用指向同一块数据

不可以同时拥有不可变引用和可变引用（为了防止数据竞争）

数据竞争的条件：

1.两个或多个指针同时访问同一个数据

2.至少有一个指针用于写入数据

3.没有使用任何机制来同步对数据访问

### 字符串切片

##### 语法

```rust
let s = String::from("Hello world");
let hello = &s[0..5];
let world = &s[6..11];
let all = &s[..];
```

切片作为函数返回值时应当使用&str当做类型

当一个字符串创建了切片后就相当于创建了一个引用一样，不能再有可变引用指向这个字符串 ，也不能修改字符串

### Struct

##### 定义结构体

```rust
struct User{
	username:String,
	email:String,
	sign_in_count:u64,
	active:bool,
}
```

##### 实例化

```rust
let user1 = User{
	email:String::from("someone@example.com"),
	username:String::from("someusername123"),
	active:true,
	sign_in_count:1,
};
```

初始化的时候必须对所有成员进行初始化 否则会编译出错

##### 注意

struct结构体不能单独定义某一个成员变量的可变性吗，但可以在初始化struct的时候定义整个struct实例的可变性

初始化struct可以简写，当局部变量和struct内成员名一样时省略冒号以及后面的东西

##### struct更新语法

```rust
let user2 = User{
	email:String::from("test@example.com"),
	username: String::from("danny"),
	..user1//其他字段和user1相同
}
```

##### Struct 打印

```rust
#[derive(Debug)]//表示为struct实现Debug特性，
struct Rectangle
{
	width:i32,
	height:i32,
}
fn main()
{
	let rect = Rectangle{
		width:30,
		height:50,
	};
	println!("{:?}",rect);
	println!("{:#?}",rect);
}
//输出:
/*
Rectangle { width: 30, height: 50 }
Rectangle {
    width: 30,
    height: 50,
}
*/
```



### Tuple Struct

##### 定义

```
struct Color(i32,i32,i32);
fn main()
{
	let color:Color = Color(1,2,3);
	let (r,g,b) = color;
}
```

### Struct方法

##### 方法定义

```rust
struct Rectangle
{
	width:u32,
	length:u32,
}
imp Rectangle{
	fn area(&self)->u32{
		self.width*self.length;
	}
}
```

##### 方法调用

```rust
let rect = Rectangle{
	width:30,
	length:50,
};
println!("{}",rect.area());
```

Rust  调用成员会自动解引用

### Struct关联函数

关联函数就是不包含&self的函数

关联函数类似于静态函数，一般用于构造器

### Enum枚举

```
enum IpAddrKind{
	v4,
	v6,
}
let var = IpAddrKind::v4;
```

##### 附加数据

```rust
enum IpAddr{
	v4(String),
	v6(String),
}
也可以
enum IpAddr{
	v4(u8,u8,u8,u8),
	v6(String),
}
或者：
enum Message{
	Quit,
	Move{x:i32,y:i32},
	Write(String),
	ChangeColor(i32,i32,i32),
}
```

##### 定义成员函数

```rust
impl Message{
	fn call(&self)
	{
		
	}
}
```

### Option 枚举

Rust里面没有NULL值

但是rust定义了类似于null的概念的枚举 Option<T>

它是标准库的预导入模块

```
enum Option<T>{
	Some(T),
	None(),
}
```

##### 使用方法

```
let x :i8 = 5;
let y:Option<i8>= Some(5);
let sum = x+y; //error
//should be:

```

### Match 表达式

```
match Enum{
	Enum::Value=>value,
}
```

例如：

```
let x:Option<i32> = Option::Some(1);
let z = match x =>{
	Option::Some(n)=> n,
	Option::None=>0,
}
```

match必须穷举所有可能值

但可以用“_“通配符来匹配上面没有列举的情况

### If let表达式

if let 相当于简化的Match 它只处理其中一种情况

```
    let y:Option<i32> = Option::Some(100);
    if let Some(n)=y{
        println!("{}",n);
    }
```

### Package Crate Module

Package: Cargo 的特性构建测试共享Crate

Crate：模块树，它可以产生library或可执行文件

Module: use 让你控制代码的组织，作用域，私有路径

Path(路径): 为struct function module等项目命名的方式

##### Crate

分两种

binary

library

Crate root是源代码文件，rust编译的七点，组成Crate 的根module

##### Package

一个package只能包含一个Cargo.toml，他描述了如何构建包下的Crates

只能包含0或1个library crate

可以包含任意数量的binary crate

至少包含一个Crate

Cargo惯例：

src/main.rs是 binary 的Crate root

Crate的名和package名相同

src/lib.rs是library的Crate root

放在src/bin下的每个文件都是binary Crate

##### module

在一个Crate 内将代码进行分组

增加可读性便于复用

控制项目私有性 public private

module 创建：

```
mod front_of_house{
	mod hosting{
		fn add_to_waitlist(){
		
		}
		fn seat_at_table()
		{
		
		}
	}
}
```

rs文件形成一个名为Crate的模块位于模块树的根部，所有的模块都在Crate模块下

默认情况下所有rust条目都是私有的

父模块无法访问子模块中的私有条目

子模块可以使用所有祖先模块的条目

使用pub关键字来公开一个模块

公开的enum类型其所有值都是公开的

公开的struct成员默认都是私有的

### 路径

##### 绝对路径:

从Crate root 开始，使用Crate 名或者字面值Crate

##### 相对路径：

从当前模块开始，使用self，super或当前模块的表示符开始

路径都至少由一个标识符::组成



##### pub enum 中：

enum本身公共

enum成员公共

##### pub struct 中:

struct本身公共

struct成员私有

### use 关键字

相当于import

##### use惯用情况

对于函数:引入其父级模块

对于enum和struct:直接引入其本身

as制定本地别名

pub use可以指定公开某个子模块

可以使用嵌套路径进行简写:

```
use 相同部分::{差异部分}
```

### 使用外部包

#### cargo 换源

文件:   ~/.cargo/config

```
[source.crates-io] 
registry = "https:/github.com/rust-lang/crates.io-index" 
replace-with = 'tuna' 
[source.tuna] 
registry = "https://mirrors.tuna.tsinghua.edu.cn/git/crates.io-index.git" 
[net] 
git-getch-with-cli = true
```

1.Cargo.toml添加包(package)

```
[dependencies]
rand="0.5.5"
```

2.use 该包

Cargo.toml

```
[package]
...
[dependencies]
rand="0.5.5"
```

### 将模块分为不同文件

pub mod network; 表示声明一个模块，编译器会在源文件中找到名为network.rs的文件并将其中的内容作为network模块的内容



### Vector

类型Vec<T>

由标准库创建

可以储存多个值

只能储存类型相同的数据

内存连续

#### 对象创建

```rust
let vec1:Vec<i32> = Vec::new();
let vec2 = vec![1,2,3];
```

#### 添加成员

```rust
vec1.push(1)
```

#### 成员访问

```rust
vec2[1];//使用索引方式
match vec2.get(1){
    Some(n)=>{
        
    }
    None=>{
        
    }
}
```

访问出错:下标出错（只对于下标访问方式）panic

### 字符串

#### 创建

```rust
let mut s = String::new();
let data = "";
let s1 = data.to_string();
let s2 = String::from("str");

```

#### 修改

```rust
let mut s =  String::from("foo");
let s1 = String::from("bar")
s.push_str(&s1)
//+连接符
let s3 = s+&s1;
```

其中+连接符相当于调用了一个如下的函数

```rust
fn add(self , s:&str)->String
```

由于self传参时会将所有权转移到函数内，所以s在调用+后会失效

#### 格式化创建

```rust
format!("{}-{}-{}",s1,s2,s3)
```

#### 迭代访问

无法使用[]对字符串访问

```rust
let s= String::from("你好，世界");
for x in s.chars()
{
	println!("{}",x);
}
for x in s.bytes(){
	println!("{}",x)
}
```

#### 切片

可以使用[]和一个范围来创建字符串切片

如let s1=  s[1..4];

##### 错误

切片不在char的边界时会panic

### HashMap

没有内置宏通过值来创建HashMap

数据储存在堆内存上

一个HashMap中K和V必须分别是相同的类型

#### 包

std::collections::hash:

#### 创建

```rust
let mut scores:HashMap<i32,i32> = HashMap::new();
//Collect 创建方法
let teams = vec![String::from("Blue"),String::from("Yellow")]
let intial_scores = vec![10,50];
let scores:HashMap<_,_> = teams.iter().zip(intial_scores.iter()).collect();
```

#### 更新

两次向同一个key插入不同的value 会覆盖之前的value

entry方法：检查一个key是否存在 如果存在，返回OccupiedEntry ，如果不存在返回VacantEntry

entry实例的or_insert函数：当entry为VacantEntry时插入

#### 切换哈希函数

TODO：切换哈希函数

### 错误处理

#### 不可恢复错误

panic!宏默认执行顺序：

1.打印错误信息

2.展开并清理调用栈

3.退出程序

##### 修改panic宏的行为

在Cargo.toml中的release.profile，debug.profile中设置 panic='abort'

在 cargo run执行之前设置环境变量 set RUST_BACKTRACE = 1 后执行会输出展开调用栈

```shell
set  RUST_BACKTRACE = 1 && cargo run
```

#### 可恢复错误

```rust
enum Result<T,E>{
	Ok(T),
	Err(e),
}//prelude
```

简单的例子:

```rust
fn main(){
    let f = File::::open("hello.txt");
    let f = match f{
        Ok(file)=>file,
        Err(error)=>match error.kind(){
            ErrorKind::NotFound => match File::create("hello.txt"){
                Ok(fc)=>fc,
                Err(e)=>panic!("Error creating file:{:?}",e),
            },
            other_error=>panic!("Error opening the file:{:?}",other_error),
        },
    };
}
```

##### unwrap（）

如果Result结果是Ok返回Ok里面的值

如果Result结果是Err，调用panic!宏

##### expect(what:&str)

用法同unwrap Err时调用panic传入参数

#### 上抛错误

?运算符：当语句值为Err的时候直接返回错误

#### 错误转换

Trait std::convert::from 当A类型的能转换为B类型时  A必须实现from函数 其返回值为B类型

### 泛型

例子

```rust
fn largest<T>(list:&[T])->T{
	....
}

struct Point<T>{
    x:T,
    y:T,
}

enum Option<T>{
  	Some(T),
    None,
}

enum Result<T,E>{
    Ok(T),
    Err(E),
}

impl<T> Point<T>{
   	fn x(&self)->&T{
        &self.x
    }
}
//特化
impl<i32> Point<i32>{
    
}
//把T放在impl关键字后表示在类型T身上实现方法
```

### Trait

#### 定义

```rust
pub trait Summary{
    fn summarize(&self)->String;
}
```

####  使用

```rust
impl Summary for 。。。{
	...
}
```

类型或者trait必须是在本地定义的

无法为外部类型实现外部trait

#### 默认实现

可以在trait里面直接实现函数体

默认实现可以调用trait中的其他函数，即使它没有默认实现

#### Trait约束

某一个函数可以将实现了一个Trait的类当做参数

```rust
//impl语法
pub fn notify(item:impl Summary)->impl Display{
	...
}
//trait bound语法
pub fn notify<T:Summary>(item:T,item2:T)->String{
    
}
pub fn notify<T:Summary+Display>(item:T)->String{
    
}
//带有where的traitbound 方法
pub fn notify<T>(item:T)->String
	where T:Summary+Display
{
    ...
}

//

```

### 生命周期

生命周期是引用的作用域

函数参数的生命周期是 **输入生命周期**（*input lifetimes*）

返回值的生命周期是**输出生命周期**（*output lifetimes*）。

```rust
&i32//这是一个引用
&'a i32 //这是一个带哟生命周期的引用
&'a mut i32 //这是一个带有生命周期的可变引用
fn longest<'a>(x:&'a,y:'a str)->&'a str{
    
}
```

#### struct的生命周期标注

```rust
struct SomeStruct<'a>{
    x:&'a str,
}
//其中‘a表示x和SomeStruct生命周期相同

```

#### 生命周期的省略

##### 规则

1 每个引用类型的参数都有自己的生命周期

2 只有一个输入生命周期参数，那么该生命周期被赋给所有输出生命周期

3 多个生命周期参数其中一个是&self或者&mut self 那么self的生命周期会被付给所有输出生命周期参数

#### 静态生命周期

'static 是一个特殊的生命周期：整个程序的持续时间

### 闭包

 #### 定义

```rust
let expensive_closure = |num| {
        println!("calculating slowly...");
        thread::sleep(Duration::from_secs(2));
        num
    };
```

#### 类型推断

若有如下lambda表达式

```rust
fn main()
{
	let lambda1 = |number|{
    println!("{}",number);
  	}
  	lambda1(123);
}
```

或者

```rust
fn main()
{
	let lambda1 = |number|{
    println!("{}",number);
  	}
  	lambda1("123");
}
```

两个代码都可以正常执行 但是如果这样写:

```rust
fn main()
{
	let lambda1 = |number|{
    println!("{}",number);
  	}
  	lambda1("123");
    lambda1(123);//编译出错
}
```

因为lambda表达式会在第一次被调用的时候就会推断出参数和返回值类型，如果再次调用会出现类型不匹配错误,也可以在定义的时候直接指明参数和返回值类型：

```rust
let add_one_v2 = |x: u32| -> u32 { x + 1 };
let add_one_v3 = |x|             { x + 1 };
let add_one_v4 = |x|               x + 1  ;
```

#### 闭包作为结构体成员

所有的闭包或者函数都实现了 `Fn` `FnMut` 或 `FnOnc`三个trait之中的一个所以可以用他们约束泛型参数

```rust
struct Cacher<T>
    where T: Fn(u32) -> u32
{
    calculation: T,
    value: Option<u32>,
}
//赋值
    let x = Cacher{
        :|number|{
            number
        },
        value:None,
    };
```

#### 闭包对环境的捕获

```rust
fn main() {
    let x = 4;
    let equal_to_x = |z| z == x;//闭包中使用了环境中的x变量
    let y = 4;
    assert!(equal_to_x(y));
}
```

当闭包从环境中捕获一个值，闭包会在闭包体中储存这个值以供使用。这会使用内存并产生额外的开销，在更一般的场景中，当我们不需要闭包来捕获环境时，我们不希望产生这些开销。因为函数从未允许捕获环境，定义和使用函数也就从不会有这些额外开销。

闭包可以通过三种方式捕获其环境：获取所有权，可变借用和不可变借用。对应三个 `Fn` trait：

- `FnOnce` 消费从周围作用域捕获的变量，闭包周围的作用域被称为其 **环境**，*environment*。为了消费捕获到的变量，闭包必须获取其所有权并在定义闭包时将其移动进闭包。其名称的 `Once` 部分代表了闭包不能多次获取相同变量的所有权的事实，所以它只能被调用一次。
- `FnMut` 获取可变的借用值所以可以改变其环境
- `Fn` 从其环境获取不可变的借用值

值得注意的是 闭包对环境的捕获发生在定义阶段 而不是调用阶段

```rust
fn main() {
    let x = vec![1, 2, 3];
    let equal_to_x = move |z| z == x;
    println!("can't use x here: {:?}", x);//报错
}
```

### 迭代器

```rust
let v1 = vec![1, 2, 3];
let v1_iter = v1.iter();//创建一个v1的迭代器
let v1_mutIter = v1.iter_mut();//创建一个v1的可变迭代器
```

#### 迭代器接口

```rust
pub trait Iterator {
    type Item;
    fn next(&mut self) -> Option<Self::Item>;
    // 此处省略了方法的默认实现
}
```

**注意:next要求一个&mut self 也就是说调用时必须传入可变引用**

```rust
let v1 = vec![1, 2, 3];
let mut v1_iter = v1.iter();
v1_iter.next()
```
其中 v1_iter必须被声明为可变的，next要求一个可变引用
```rust
let v1 = vec![1, 2, 3];
let v1_iter = v1.iter();
for val in v1_iter {
    println!("Got: {}", val);
}
```
其中v1_iter不需要是可变的 ，因为for语句会自动获取v1_iter的所有权并将其在内部转换为可变的
#### 消费迭代器的方法
rust库有一些自带的函数，其内部会调用迭代器的next方法这些方法被称作消费适配器
例子：

```rust
#[test]
fn iterator_sum() {
    let v1 = vec![1, 2, 3];
    let v1_iter = v1.iter();
    let total: i32 = v1_iter.sum();
    assert_eq!(total, 6);
}
```
注意：sum函数会获取迭代器的所有权所以不能再使用迭代器

#### 迭代器适配器

```rust
let v1: Vec<i32> = vec![1, 2, 3];

v1.iter().map(|x| x + 1);
```


### 智能指针

#### Box\<T\>

用法

```rust
fn main()
{
	let b = Box::new(5); // B 是
    println!("b is {}",b);//自动解引用
}
```

#### Rc\<T\>基于引用计数的智能指针

crate导入:

```rust
use std::rc::Rc;
```

用法:

```rust
let x = Rc::new(5);
```

自动增加引用计数

```rust
let y = Rc::clone(&a);
```

获取引用计数Rc::strong_count(&x);

#### 智能指针的实现

```rust
struct MyBox<T>(T);

impl<T> MyBox<T> {
    fn new(x: T) -> MyBox<T> {
        MyBox(x)
    }
}
use std::ops::Deref;
impl<T> Deref for MyBox<T> {
    type Target = T;
    fn deref(&self) -> &T {
        &self.0
    }
}
```

隐式解引用强制多态：

Rust 在发现类型和 trait 实现满足三种情况时会进行解引用强制多态：

- 当 `T: Deref<Target=U>` 时从 `&T` 到 `&U`。
- 当 `T: DerefMut<Target=U>` 时从 `&mut T` 到 `&mut U`。
- 当 `T: Deref<Target=U>` 时从 `&mut T` 到 `&U`。

### 并发编程

#### thread::spawn:

导入 ： use std::thread;

```rust
fn main(){
	thread::spawn(||{
        for i in 1..10{
            println!("hi number{} from the spawned thread!",i);
            thread::sleep(Duration::from_millis(1));
        }
    })
}
```

thread::spawn 会返回一个 handle 对象，可以调用其join函数来阻塞当前线程等待另一个线程的结束

#### 进程间通信

##### channel

```rust
use std::sync::mpsc
fn main() {
    let (sender, recvr) = mpsc::channel();
}
```

std::sync::mpsc
pub fn channel<T>() -> (Sender<T>, Receiver<T>)

```rust
use std::thread;
use std::sync::mpsc;

fn main() {
    let (tx, rx) = mpsc::channel();

    thread::spawn(move || {
        let val = String::from("hi");
        tx.send(val).unwrap();
    });

    let received = rx.recv().unwrap();
    println!("Got: {}", received);
}
```

通道的接收端有两个有用的方法：`recv` 和 `try_recv`。这里，我们使用了 `recv`，它是 *receive* 的缩写。这个方法会阻塞主线程执行直到从通道中接收一个值。一旦发送了一个值，`recv` 会在一个 `Result<T, E>` 中返回它。当通道发送端关闭，`recv` 会返回一个错误表明不会再有新的值到来了。

`try_recv` 不会阻塞，相反它立刻返回一个 `Result<T, E>`：`Ok` 值包含可用的信息，而 `Err` 值代表此时没有任何消息。如果线程在等待消息过程中还有其他工作时使用 `try_recv` 很有用：可以编写一个循环来频繁调用 `try_recv`，在有可用消息时进行处理，其余时候则处理一会其他工作直到再次检查。

###### 所有权

```rust
    thread::spawn(move || {
        let val = String::from("hi");
        tx.send(val).unwrap();
        println!("val is {}", val);//报错，val已被移动
    })
```

##### 共享状态

“不要通过共享内存来通讯”（“do not communicate by sharing memory.”）

###### 互斥器（Mutex\<T\>）

mutex其本身可以当做一个智能指针

```rust
use std::sync::Mutex;
fn main() {
    let m = Mutex::new(5)
    {
        let mut num = m.lock().unwrap();
        *num = 6;
    }
    println!("m = {:?}", m);
}
```

lock的异常情况：另一个线程执行lock后出现了panic，则当前线程的lock函数会出错

###### 原子引用计数 `Arc`

`Arc<T>` 和 `Rc<T>` 有着相同的 API

