# CMake教程

## project关键字

指定工程名字和支持的语言，默认支持所有语言

- project(Hello),指定工程名字为Hello
- project(Hello C CXX)指定工程名字，并姑且支持的语言是c++和c

本命令会创建两个变量：

- `<PROJECT_NAME>_BINARY_DIR`即：`Hello_BINARY_DIR`
- `<PROJECT_NAME>_SOURCE_DIR`即：`Hello_SOURCE_DIR`

使用上述两个变量会导致问题，当project名称发生变化时，这两个变量名就不存在了，所以定义一个不变的名字永远指这两个值，分别是`PROJECT_BINARY_DIR`和`PROJECT_SOURCE_DIR`



## set关键字

作用：定义变量

set(SRC_LIST main.cpp t1.cpp t2.cpp)

使用时${SRC_LIST}



## message 关键字

构建时输出自定义消息

包括三种信息：

- SEND_ERROR

  产生错误，生成过程被跳过

- STATUS

  输出前缀为`--`的消息

- FATAL_ERROR

  立即种植所有CMAKE过程



## add_executable 关键字

生成可执行文件，

```cmake
add_executable(hello ${SRC_LIST})
add_executable(hello main.cpp)
```



## 语法基本规则

取变量值${}，但是if语句中直接使用变量名

指令语法：<指令名>(<参数1> <参数2> <参数3>)

指令本身大小写不敏感，但参数大小写敏感

## 语法注意事项

- ```add_executable(hello main.cpp)```

  也可以写作:

  `add_executable(hello "main.cpp")`

- `add_executable(hello main.cpp)`也可以写作add_executable(hello main)省略.c或者.cpp，但不推荐这样写





## 内部构建和外部构建

直接运行cmake会在当前目录下创建许多中间文件，所以推荐先cd到构建目录然后

`cmake ../`

此处PROJECET_BINARY_DIR就是当前目录



