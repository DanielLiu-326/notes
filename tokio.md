#  tokio::fs

## 结构体

### DirBuilder

用于创建文件夹

#### 成员函数

##### new 

```rust
pub fn new() -> Self//构造函数
```

创建DirBuilder实例

##### recursive

```rust
pub fn recursive(&mut self, recursive: bool) -> &mut Self
```

如果参数recursive是true则调用create()函数创建文件夹时会将路径中不存在的父文件夹全部创建

##### create

```rust
pub async fn create(&self, path: impl AsRef<Path>) -> Result<()>
```

执行创建文件夹操作。

##### mode

```rust
impl DirBuilder
	pub fn mode(&mut self, mode: u32) -> &mut Self//(Unix Only)
```

只在unix环境下存在，指定创建文件夹的权限

```rust
#[tokio::main]
async fn main()->std::io::Result<()>{
    let mut dirbdr = DirBuilder::new();
    dirbdr.recursive(true)
        .mode(0o777)
        .create("./test/test/")
        .await
        .expect("cant create folder");
    return Ok(());
}
```

### File

#### 成员函数

##### open

```rust
pub async fn open(path: impl AsRef<Path>) -> Result<File>
```

以只读的方式打开

##### create

```rust
pub async fn create(path: impl AsRef<Path>) -> Result<File>
```

创建一个文件并以只写的方式打开,如果文件存在将截断它

##### from_std

```rust
pub fn from_std(std: File) -> File
```

从标准库File结构体创建

##### sync_all

```rust
pub async fn sync_all(&self) -> Result<()>
```

将操作系统的缓存写入到硬盘中

##### sync_data

```rust
pub async fn sync_data(&self) -> Result<()>
```

与sync_all函数类似，但可以减少磁盘操作，性能更好。

##### set_len

```rust
pub async fn set_len(&self, size: u64) -> Result<()>
```

修改文件长度，如果设置的文件长度小于当前文件长度，将文件截短，如果设置的文件长度大于当前的文件长度，则将文件后面用0填充

当文件打开时未传入写flag ，则返回错误

##### metadata

```rust
pub async fn metadata(&self) -> Result<Metadata>
```

返回底层的metadata

##### try_clone

```rust
pub async fn try_clone(&self) -> Result<File>
```

尝试创建一个新的File对象，但是同时引用相同的底层文件句柄

##### into_std

```rust
pub async fn into_std(self) -> File
```

转变为标准库文件对象

##### set_permissions

```rust
pub async fn set_permissions(&self, perm: Permissions) -> Result<()>
```

设置文件权限

###  OptionOptions

#### 成员函数

##### new

```rust
pub fn new() -> OpenOptions
```

构造函数

##### 选项

- read 设置文件打开时可读
- write 设置文件打开时可写
- append 设置文件打开方法是附加
- truncate 设置文件打开时被清空
- create 设置文件如果不存在则创建
- create_new 创建文件，当文件已存在时返回错误（原子操作）

##### open

```rust
pub async fn open(&self, path: impl AsRef<Path>) -> Result<File>
```

打开文件。

错误：

- NotFound：未找到文件或路径不存在
- PermissionDenied: 没有权限
- AlreadyExists: `create_new`为true且文件已存在
- InvalidInput: 无效的打开选项组合（例如truncate为true 但write为false）
- Other ：其他错误

##### mode

```rust
pub fn mode(&mut self, mode: u32) -> &mut OpenOptions
```

(Unix Only)

设置创建文件的权限

##### custom_flags

```rust
pub fn custom_flags(&mut self, flags: i32) -> &mut OpenOptions
```

(Unix Only)

 ### ReadDir

创建方式：

```rust
let mut dirReader = tokio::fs::read_dir("./test");
```

#### 成员函数

##### next_entry

```rust
pub async fn next_entry(&mut self) -> Result<Option<DirEntry>>
```

返回下一个（第一个）DirEntry实例

##### poll_next_entry

```rust
pub fn poll_next_entry(
    &mut self,
    cx: &mut Context<'_>
) -> Poll<Result<Option<DirEntry>>>
```

函数提供类似future的poll方法

返回值：

- `Poll::Pending` 未完成读取
- `Poll::Ready(Ok(Some(entry)))`已完成，并且下一个文件/文件夹为变量entry
- `Poll::Ready(Ok(None))` 已完成，没有下一个文件/文件夹
- `Poll::Ready(Err(err))` 已完成，但出错

注意，poll_next_entry 只会调用最后一次传入的waker

### DirEntry

创建方式

```rust
let mut dir_entry = dir_reader.next_entry().await.unwrap;
```

#### 成员函数

##### ino

```rust
pub fn ino(&self) -> u64
```

(Unix Only)

函数返回一个底层d_ino值

##### path

```rust
pub fn path(&self) -> PathBuf
```

返回文件/文件夹的路径，可能是相对路径也可能是绝对路径取决于：read_dir(path) 参数中的path。

##### file_name

```rust
pub fn file_name(&self) -> OsString
```

文件名。OsString

##### metadata

```rust
pub async fn metadata(&self) -> Result<Metadata>
```

返回文件的metadata,

如果此文件是符号链接，则不会遍历符号链接

##### file_type

```rust
pub async fn file_type(&self) -> Result<FileType>
```

返回文件的类型(不会递归遍历符号链接)

## 函数

### canonicalize

```rust
pub async fn canonicalize(path: impl AsRef<Path>) -> Result<PathBuf>
```

函数传入一个路径，将该路径简化为最简绝对路径，

同步版本std::fs::canonicalize

### copy

```rust
pub async fn copy(
    from: impl AsRef<Path>, 
    to: impl AsRef<Path>
) -> Result<u64, Error>
```

函数复制一个文件到另一个文件，该操作将会复制该文件的权限掩码，将会覆盖to指定的文件内容，(同[`std::fs::copy`)

### create_dir

```rust
pub async fn create_dir(path: impl AsRef<Path>) -> Result<()>
```

通过路径创建一个目录(同步版本 `std::fs::create_dir`)

如果要创建的目录的父目录不存在则返回错误，如果要将父目录也创建，则使用create_dir_all代替

**当文件夹已存在则报错**

### create_dir_all

```rust
pub async fn create_dir_all(path: impl AsRef<Path>) -> Result<()>
```

递归创建一个目录和父目录

同步版本:`std::fs::create_dir_all`

**文件夹已存在不报错**

### hard_link

```rust
pub async fn hard_link(
    src: impl AsRef<Path>, 
    dst: impl AsRef<Path>
) -> Result<()>
```

创建一个硬链接

同步版本`std::fs::hard_link`

### metadata

```rust
pub async fn metadata(path: impl AsRef<Path>) -> Result<Metadata>
```

对应unix中的stat

获取文件或文件夹的metadata，路径不存在和无权限将返回错误

如果路径目标是一个软连接则返回软连接的目标文件的metadata，如果路径目标是文件，则返回它的metadata

同步版本`std::fs::metadata`

### read

```rust
pub async fn read(path: impl AsRef<Path>) -> Result<Vec<u8>>
```

同步版本`std::fs::read`

打开并读取一个文件，将创建一个基于文件大小的buf，相较于read_to_end()直接传入一个vec::new()更快。

当文件不存在返回错误

### read_dir

```rust
pub async fn read_dir(path: impl AsRef<Path>) -> Result<ReadDir>
```

同步版本`std::fs::read_dir`

返回一个ReadDir 实例

### read_link 

```rust
pub async fn read_link(path: impl AsRef<Path>) -> Result<PathBuf>
```

同步版本`std::fs::read_link`

读取一个软连接，返回它指向的位置,注意指向的位置可能是绝对路径也可能是相对路径，取决于创建链接时给出的路径

### read_to_string

```rust
pub async fn read_to_string(path: impl AsRef<Path>) -> Result<String>
```

同步版本 `std::fs::read_to_string`

打开文件并读取全部内容到string内

### remove_dir

```rust
pub async fn remove_dir(path: impl AsRef<Path>) -> Result<()>
```

同步版本 `std::fs::remove_dir`

删除一个空的目录，如果目录不是空返回错误

### remove_dir_all

```rust
pub async fn remove_dir_all(path: impl AsRef<Path>) -> Result<()>
```

同步版本 `std::fs::remove_dir_all`

删除一个目录和目录的内容

### remove_file

```rust
pub async fn remove_file(path: impl AsRef<Path>) -> Result<()>
```

同步版本:`std::fs::remove_file`

删除文件.

### rename

```rust
pub async fn rename(from: impl AsRef<Path>, to: impl AsRef<Path>) -> Result<()>
```

同步版本:`std::fs::rename`

重命名文件或目录，当目标位置已存在时替换原先的文件，如果新的位置在不同的挂载点，操作将不会执行。

### set_permissions

```rust
pub async fn set_permissions(
    path: impl AsRef<Path>, 
    perm: Permissions
) -> Result<()>
```

同步版本 `std::fs::set_permissions`

修改文件或者目录的权限。

### symlink

```rust
pub async fn symlink(src: impl AsRef<Path>, dst: impl AsRef<Path>) -> Result<()>
```

同步版本`std::os::unix::fs::symlink`

在文件系统上创建一个软连接

### symlink_metadata

```rust
pub async fn symlink_metadata(path: impl AsRef<Path>) -> Result<Metadata>
```

对应unix中的lstat

同步版本： `std::fs::symlink_metadata`

如果路径目标是一个软连接，则获取该软连接的的metadata，如果路径目标是一个文件本身，则获取该文件本身的metadata。

对比metadata

### write

```rust
pub async fn write(
    path: impl AsRef<Path>, 
    contents: impl AsRef<[u8]>
) -> Result<()>
```

打开一个文件并清空它然后写入内容

同步版本：`std::fs::write`

# tokio::io

## 结构体

### BufReader

#### 创建方式

```rust
let reader = BufReader::new(tokio::io::stdin());
```

#### 成员函数

##### new

```rust
pub fn new(inner: R) -> Self
```

创建一个BufReader实例，带有默认的buffer容量，默认值目前为8KB

##### with_capacity

```rust
pub fn with_capacity(capacity: usize, inner: R) -> Self
```

创建一个BufReader实例，并将其buffer容量指定为capacity参数

##### get_ref

```
pub fn get_ref(&self) -> &R
```

获取底层的reader类型的不可变引用

##### get_mut

```rust
pub fn get_mut(&mut self) -> &mut R
```

获取底层reader类型的可变引用

##### get_pin_mut

```rust
pub fn get_pin_mut(self: Pin<&mut Self>) -> Pin<&mut R>
```

获取底层reader的固定了的可变Pin引用

##### into_inner

```rust
pub fn into_inner(self) -> R
```

将自己变成底层reader

##### buffer

```rust
pub fn buffer(&self) -> &[u8]
```

获取buffer

#### 实现接口

```rust
impl<R: AsyncRead> AsyncBufRead for BufReader<R>
impl<R: AsyncRead> AsyncRead for BufReader<R>
impl<R: AsyncRead + AsyncSeek> AsyncSeek for BufReader<R>
impl<R: AsyncRead + AsyncWrite> AsyncWrite for BufReader<R>
impl<R: Debug> Debug for BufReader<R>
impl<RW> From<BufReader<BufWriter<RW>>> for BufStream<RW>
impl<'__pin, R> Unpin for BufReader<R> 
```

### BufStream

```rust
pub struct BufStream<RW> { /* fields omitted */ }
```

#### 创建方式

```rust
let stream = BufStream::new(someStream);
```



#### 成员函数

##### new

```rust
pub fn new(stream: RW) -> BufStream<RW>
```

构造函数

##### with_capacity

```rust
pub fn with_capacity(
    reader_capacity: usize,
    writer_capacity: usize,
    stream: RW
) -> BufStream<RW>
```

通过容量构造实例

##### get_ref

```rust
pub fn get_ref(&self) -> &RW
```

获取底层的io对象的引用

##### get_mut

```rust
pub fn get_mut(&mut self) -> &mut RW
```

获取底层io对象的可变引用

##### get_pin_mut

```rust
pub fn get_pin_mut(self: Pin<&mut Self>) -> Pin<&mut RW>
```

获取可变pin对象指向底层io实例

##### into_inner

```rust
pub fn into_inner(self) -> RW
```

将自己转换为底层io对象

#### 实现接口

```rust
impl<RW: AsyncRead + AsyncWrite> AsyncBufRead for BufStream<RW>
impl<RW: AsyncRead + AsyncWrite> AsyncRead for BufStream<RW>
impl<RW: AsyncRead + AsyncWrite> AsyncWrite for BufStream<RW>
impl<RW: Debug> Debug for BufStream<RW>
impl<RW> From<BufReader<BufWriter<RW>>> for BufStream<RW>
impl<RW> From<BufWriter<BufReader<RW>>> for BufStream<RW>
```



### BufWriter

```rust
pub struct BufWriter<W> { /* fields omitted */ }
```

#### 创建方式

```rust
let writer = BufWriter::new(tokio::io::stdout());
```

#### 成员函数

##### new

```rust
pub fn new(inner: W) -> Self
```

创建BufWriter实例

##### with_capacity

```rust
pub fn with_capacity(cap: usize, inner: W) -> Self
```

通过容量创建一个BufWriter实例

##### get_ref

```rust
pub fn get_ref(&self) -> &W
```

获取底层的writer 实例

##### get_mut 

```rust
pub fn get_mut(&mut self) -> &mut W
```

获取底层writer的可变引用

##### get_pin_mut

```rust
pub fn get_pin_mut(self: Pin<&mut Self>) -> Pin<&mut W>
```

获取被pin包裹的底层对象的可变引用

##### into_inner

```rust
pub fn into_inner(self) -> W
```

将自己转换为底层writer对象

##### buffer

```rust
pub fn buffer(&self) -> &[u8]
```

返回一个底层buffer引用

#### 实现接口

```rust
impl<W: AsyncWrite + AsyncBufRead> AsyncBufRead for BufWriter<W>
impl<W: AsyncWrite + AsyncRead> AsyncRead for BufWriter<W>
impl<W: AsyncWrite + AsyncSeek> AsyncSeek for BufWriter<W>
impl<W: AsyncWrite> AsyncWrite for BufWriter<W>
impl<W: Debug> Debug for BufWriter<W>
impl<RW> From<BufWriter<BufReader<RW>>> for BufStream<RW>
impl<'__pin, W> Unpin for BufWriter<W> 
```

### DuplexStream

```rust
pub struct DuplexStream { /* fields omitted */ }
```

#### 创建方式

```rust
let (mut client, mut server) = tokio::io::duplex(64);
```

#### 实现接口

```rust
impl AsyncRead for DuplexStream
impl AsyncWrite for DuplexStream
impl Debug for DuplexStream
impl Drop for DuplexStream
```

### Empty

该类的实例不论什么时候读都会返回EOF

```rust
pub struct Empty { /* fields omitted */ }
```

#### 实现接口

```rust
impl AsyncBufRead for Empty
impl AsyncRead for Empty
impl Debug for Empty
```

### Interest

可读可写标记

```rust
pub struct Interest(_);
```

#### 创建方式

```rust
let both = Interest::READABLE | Interest::WRITABLE;
```

#### 实现

##### const READABLE

```rust
pub const READABLE: Interest
```

可读选项

##### const WRITABLE

```rust
pub const WRITABLE: Interest
```

可写选项

##### is_readable

```rust
pub const fn is_readable(self) -> bool
```

获取interest是否有可读标记，（不会捕获self）

##### is_writeable

```rust
pub const fn is_writable(self) -> bool
```

获取interest 实例是否有可写标记，（不会捕获self）

##### add

```rust
pub const fn add(self, other: Interest) -> Interest
```

将两个interest的标记加起来，不捕获变量

#### 实现接口

```rust
impl BitOr<Interest> for Interest
impl BitOrAssign<Interest> for Interest
impl Clone for Interest
impl Debug for Interest
impl PartialEq<Interest> for Interest
impl Copy for Interest
impl Eq for Interest
impl StructuralEq for Interest
impl StructuralPartialEq for Interest
```

### Lines

```rust
pub struct Lines<R> { /* fields omitted */ }
```

#### 创建方式

```rust
let mut reader = BufReader::new(tokio::io::stdin());
let lines = reader.lines();
while let Some(line) = lines.next_line().await?{
    println!("length:{}",line.len());
}
```

#### 成员函数

##### next_line

```rust
pub async fn next_line(&mut self) -> Result<Option<String>>
```

返回下一行，如果有下一行则返回Some(line) 如果没有，则返回None;

##### get_mut

```rust
pub fn get_mut(&mut self) -> &mut R
```

返回底层的reader的可变引用

##### get_ref

```rust
pub fn get_ref(&mut self) -> &R
```

返回底层reader的不可变引用

##### into_inner

```rust
pub fn into_inner(self) -> R
```

将自己转换为底层类型

### ReadBuf

一个字节缓冲区的包装，渐进式的填充和初始化。

```rust
pub struct ReadBuf<'a> { /* fields omitted */ }
```

#### 创建方式

```rust
let buf = vec![1,2,3,4,5,6,7,8,9,10,11];
let readBuf = ReadBuf::new(&mut buf);
```

#### 成员函数

##### new

```rust
pub fn new(buf: &'a mut [u8]) -> ReadBuf<'a>
```

构造函数

##### uninit

```rust
pub fn uninit(buf: &'a mut [MaybeUninit<u8>]) -> ReadBuf<'a>
```

创建一个未初始化的buffer

##### capacity

```rust
pub fn capacity(&self) -> usize
```

返回容量

##### filled

```rust
pub fn filled(&self) -> &[u8]
```

返回底层的已填充的buffer的不可变切片

##### filled_mut 

```rust
pub fn filled_mut(&mut self) -> &mut [u8]
```

返回底层已填充部分的buffer的可变引用

##### take

```rust
pub fn take(&mut self, n: usize) -> ReadBuf<'_>
```

返回一个新的ReadBuf实例，该实例返回值的未填充部分不大于n

##### initialized

```rust
pub fn initialized(&self) -> &[u8]
```

返回已经初始化的部分

##### initialized_mut

```rust
pub fn initialized_mut(&mut self) -> &mut [u8]
```

返回已经初始化的部分

##### inner_mut

```rust
pub unsafe fn inner_mut(&mut self) -> &mut [MaybeUninit<u8>]
```

返回底层buffer的可变引用

##### unfilled_mut

```rust
pub unsafe fn unfilled_mut(&mut self) -> &mut [MaybeUninit<u8>]
```

返回一个未填充部分的可变切片

##### initialized_unfilled

```rust
pub fn initialize_unfilled(&mut self) -> &mut [u8]
```

返回未填充部分且已经初始化部分的buffer的可变引用

##### initialized_unfilled_to

```rust
pub fn initialize_unfilled_to(&mut self, n: usize) -> &mut [u8]
```

返回n个byte的未填充部分且已经初始化部分的可变引用



##### remaining

```rust
pub fn remaining(&self) -> usize
```

返回未填充部分的长度

##### clear

```rust
pub fn clear(&mut self)
```

清空buffer，将填充区域的大小设置为0，底层buffer的内容不改变，已初始化长度不改变

##### advance

```rust
pub fn advance(&mut self, n: usize)
```

将已填充部分的大小扩大，且不改变已初始化部分的大小，当未填充部分的大小小于n时panic

##### set_filled

```rust
pub fn set_filled(&mut self, n: usize)
```

设定已填充部分的长度,已初始化区域的大小不改变，当n大于已初始化区域，则会出现恐慌

##### assume_init

```rust
pub unsafe fn assume_init(&mut self, n: usize)
```

假定，缓冲区的前n个字节已初始化，当n大于已初始化区域的长度时，不panic，但这通常不是想要的结果

##### put_slice

```rust
pub fn put_slice(&mut self, buf: &[u8])
```

附加数据到buffer，前移写入位置，如果有必要则前移已初始化的位置

当未填充的空间小于buf.len()时panic

### ReadHalf

```rust
pub struct ReadHalf<T> { /* fields omitted */ }
```

#### 创建方式

```rust
let (mut stream1,mut stream2) = tokio::io::duplex(100);
let (readhalf,writehalf) = tokio::io::split(stream1);
```

#### 成员函数

##### is_pair_of

```rust
pub fn is_pair_of(&self, other: &WriteHalf<T>) -> bool
```

检查readhalf和writehalf是否是从同一个stream中分割出来的

##### unsplite

```rust
pub fn unsplit(self, wr: WriteHalf<T>) -> T
```

重新将其与之前一同分割出来的writehalf

### Ready

描述io资源的完成状态

```rust
pub struct Ready(_);
```

#### 实现

##### const EMPTY:Ready

##### const READABLE:Ready

##### const WRITEABLE:Ready

##### const WRITE_CLOSED:Ready

##### const ALL:Ready

##### is_empty

```rust
pub fn is_empty(self) -> bool
```

如果Ready是空则返回true

##### is_readable

```rust
pub fn is_readable(self) -> bool
```

当值包括readable时返回true

##### is_writable

```rust
pub fn is_writable(self) -> bool
```

当值包含writable返回true

##### is_read_closed

```rust
pub fn is_read_closed(self) -> bool
```

如果值包含read-closed则返回true

##### is_write_closed

```rust
pub fn is_write_closed(self) -> bool
```

如果值包含write-closed则返回true

#### 实现接口

```rust
impl BitAnd<Ready> for Ready
impl BitOr<Ready> for Ready
impl BitOrAssign<Ready> for Ready
impl Clone for Ready
impl Debug for Ready
impl PartialEq<Ready> for Ready
impl PartialOrd<Ready> for Ready
impl Sub<Ready> for Ready
impl Copy for Ready
impl StructuralPartialEq for Ready
```

### Repeat

一个reader，每次读取都会返回一个先前给定的字符，可以读无限次

```rust
pub struct Repeat { /* fields omitted */ }
```

#### 创建方式

```rust
let mut repeat = tokio::io::repeat(10);
```

#### 实现接口

```rust
impl AsyncRead for Repeat
impl Debug for Repeat
```

### Sink

```rust
pub struct Sink { /* fields omitted */ }
```

一个Writer,将数据写到虚空。

#### 创建方式

```rust
let sink = tokio::io::sink();
```

#### 实现

```rust
impl AsyncWrite for Sink
impl Debug for Sink
```

### Split

split函数的返回值

```rust
pub struct Split<R> { /* fields omitted */ }
```

#### 创建方式

从实现AsyncBufReadExt trait的类的split函数返回

```rust
let file = OpenOption::new().read(true).open("test.txt").await.unwrap();
let split = tokio::io::BufReader::new(file).split(10);
```

#### 成员函数

##### next_segment

```rust
pub async fn next_segment(&mut self) -> Result<Option<Vec<u8>>>
```

返回流中的分割好的下一个片段

##### poll_next_segment

```rust
pub fn poll_next_segment(
    self: Pin<&mut Self>,
    cx: &mut Context<'_>
) -> Poll<Result<Option<Vec<u8>>>>
```

#### 实现接口

```rust
impl<R: Debug> Debug for Split<R>
impl<'__pin, R> Unpin for Split<R> where
    __Origin<'__pin, R>: Unpin, 
```

### Stderr

标准错误流

```rust
pub struct Stderr { /* fields omitted */ }
```

#### 创建方式

```rust
let mut stderr = tokio::io::stderr();
```

#### 实现接口

```rust
impl AsRawFd for Stderr
impl AsyncWrite for Stderr
impl Debug for Stderr
```

### Stdin

标准输入流

```rust
pub struct Stdin { /* fields omitted */ }
```

#### 创建方式

```rust
let mut stdin = tokio::io::stdin();
```

#### 实现接口

```rust
impl AsRawFd for Stdin
impl AsyncRead for Stdin
impl Debug for Stdin
```

### Stdout

标准输出流

```rust
pub struct Stdout { /* fields omitted */ }
```

#### 创建方式

```rust
let mut stdout = tokio::io::stdout();
```

#### 实现接口

```rust
impl AsRawFd for Stdout
impl AsyncWrite for Stdout
impl Debug for Stdout
```

### Take

从Stream对象的take方法返回的对象，该对象为一个reader，且只能从其中读出limit字节的数据，limit由创建时给出，当超过limit时会返回EOF

```rust
pub struct Take<R> { /* fields omitted */ }
```

#### 创建方式

```rust
let f = File::open("foo.txt").await.unwrap();
let mut take = f.take(5);
```

#### 成员函数

##### limit

```rust
pub fn limit(&self) -> u64
```

返回从Take对象中还能读取的byte数,

注意：当底层的reader读出EOF时，即便读取的总数未超出limit，也会返回EOF

##### set_limit

```rust
pub fn set_limit(&mut self, limit: u64)
```

重新设置一个limit，并且之前已经读出的byte置零，相当于重新创建了一个Take对象

##### get_ref

```rust
pub fn get_ref(&self) -> &R
```

返回底层Reader的不可变引用

##### get_mut

```rust
pub fn get_mut(&mut self) -> &mut R
```

返回底层Reader的不可变引用

##### get_pin_mut

```rust
pub fn get_pin_mut(self: Pin<&mut Self>) -> Pin<&mut R>
```

返回包装了底层Reader对象的Pin对象的可变引用

##### into_inner

```rust
pub fn into_inner(self) -> R
```

将Take对象转变为底层Reader对象

#### 实现接口

```rust
impl<R: AsyncBufRead> AsyncBufRead for Take<R>
impl<R: AsyncRead> AsyncRead for Take<R>
impl<R: Debug> Debug for Take<R>
impl<'__pin, R> Unpin for Take<R> where
    __Origin<'__pin, R>: Unpin, 
```



### WriteHalf

同ReadHalf

## Traits

### AsyncBufRead

```rust
pub trait AsyncBufRead: AsyncRead {/*....*/}
```

同步版本：std::io::BufRead

#### 接口函数

##### poll_fill_buf

```rust
fn poll_fill_buf(
	self: Pin<&mut Self>, 
	cx: &mut Context<'_>
) -> Poll<Result<&[u8]>>;
```

同Future::polling

这个函数是底层调用需要配合consume函数使用，

##### consume

```rust
fn consume(self: Pin<&mut Self>, amt: usize)
```

设定buffer中的amt个byte已经被读出，致使poll_read函数不会再次读出这amt字节

注意:这个函数是底层调用，并不产生额任何的IO操作，

#### 外部实现

```rust
impl<T: ?Sized + AsyncBufRead + Unpin> AsyncBufRead for Box<T>
impl<T: ?Sized + AsyncBufRead + Unpin> AsyncBufRead for &mut T
impl<P> AsyncBufRead for Pin<P> 
where
    P: DerefMut + Unpin,
    P::Target: AsyncBufRead, 
impl AsyncBufRead for &[u8]
impl<T: AsRef<[u8]> + Unpin> AsyncBufRead for Cursor<T>
```

### AsyncBufReadExt

```rust
pub trait AsyncBufReadExt: AsyncBufRead {/*...*/}
```

一个扩展特性，可以给AsyncBufRead类型添加工具方法

#### 接口函数

##### read_until

```rust
async fn read_until(&mut self, byte: u8, buf: &mut Vec<u8>) -> io::Result<usize>;
```

将所有的内容读入到buf中，直到分隔符或者EOF被读入，该函数会扩大buf的大小，如果buf不是空的，将在buf后面附加数据，包括分隔符(如果有)。如果成功读入返回Ok(n)，如果n=0则说明读到EOF

###### 错误

函数将忽略所有的ErrorKind::interrupted并且返回fill_buf返回的错误。

##### read_line

```rust
async fn read_until(&mut self, byte: u8, buf: &mut Vec<u8>) -> io::Result<usize>;
```

将从底层stream读取字节，直到分隔符或者EOF被读入，一旦读入，所有的内容都会附加到buf中，包含分隔符(如果读入);

###### 错误

错误类似于read_until函数，但是会检查读到的字节是不是有效的UTF-8.

##### split

```rust
fn split(self, byte: u8) -> Split<Self> where
    Self: Sized + Unpin, 
```

返回一个Reader，该对象以byte参数为分隔符每次只读取一个片段（不包括分隔符）。

##### lines

```rust
fn lines(self) -> Lines<Self> where
    Self: Sized, 
```

返回Lines对象，每次只读一行。

### AsyncRead

```rust
pub trait AsyncRead {/*...*/}
```

#### 接口函数

##### poll_read

```rust
fn poll_read(
    self: Pin<&mut Self>,
    cx: &mut Context<'_>,
    buf: &mut ReadBuf<'_>
) -> Poll<Result<()>>
```

同步版本：std::io::Read

尝试读取数据到buf中。

### AsyncReadExt

#### 接口函数

```rust
pub trait AsyncReadExt: AsyncRead {
    fn chain<R>(self, next: R) -> Chain<Self, R>
    where
        Self: Sized,
        R: AsyncRead,
    { ... }
    //创建一个实现了AsyncRead的对象当第一个对象被读取完毕时会接着读取第二个对象的内容。
    fn read<'a>(&'a mut self, buf: &'a mut [u8]) -> Read<'a, Self>
    where
        Self: Unpin,
    { ... }
    //读取为字符u8数组
    fn read_buf<'a, B>(&'a mut self, buf: &'a mut B) -> ReadBuf<'a, Self, B>
    where
        Self: Sized + Unpin,
        B: BufMut,
    { ... }
    //获取将数据读入到ReadBuf并且移动ReadBuf中的指针。
    fn read_exact<'a>(&'a mut self, buf: &'a mut [u8]) -> ReadExact<'a, Self>
    where
        Self: Unpin,
    { ... }
    //读取确定大小的数据，填充到buf中
    fn read_u8<'a>(&'a mut self) -> ReadU8<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为u8
    fn read_i8<'a>(&'a mut self) -> ReadI8<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为i8
    fn read_u16<'a>(&'a mut self) -> ReadU16<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为u16
    fn read_i16<'a>(&'a mut self) -> ReadI16<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为i16
    fn read_u32<'a>(&'a mut self) -> ReadU32<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为u32
    fn read_i32<'a>(&'a mut self) -> ReadI32<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为i32
    fn read_u64<'a>(&'a mut self) -> ReadU64<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为u64
    fn read_i64<'a>(&'a mut self) -> ReadI64<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为i64
    fn read_u128<'a>(&'a mut self) -> ReadU128<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为u128
    fn read_i128<'a>(&'a mut self) -> ReadI128<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为i128
    fn read_u16_le<'a>(&'a mut self) -> ReadU16Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为i16
    fn read_i16_le<'a>(&'a mut self) -> ReadI16Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为小端储存的i16类型
    fn read_u32_le<'a>(&'a mut self) -> ReadU32Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为小端储存的u32类型
    fn read_i32_le<'a>(&'a mut self) -> ReadI32Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为小端储存的i32类型
    fn read_u64_le<'a>(&'a mut self) -> ReadU64Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为小端储存的u64类型
    fn read_i64_le<'a>(&'a mut self) -> ReadI64Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为小端储存的i64类型
    fn read_u128_le<'a>(&'a mut self) -> ReadU128Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为小端储存的u128类型
    fn read_i128_le<'a>(&'a mut self) -> ReadI128Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    //读取为小端储存的i128类型
    fn read_to_end<'a>(
        &'a mut self, 
        buf: &'a mut Vec<u8>
    ) -> ReadToEnd<'a, Self>
    where
        Self: Unpin,
    { ... }
    //将全部的数据全部读入，将其附加到buf中，如果正确读取，返回读到的长度，如果在读取中出错，将把所有正确读入的数据放到buf中，
    fn read_to_string<'a>(
        &'a mut self, 
        dst: &'a mut String
    ) -> ReadToString<'a, Self>
    where
        Self: Unpin,
    { ... }
    //与read_to_end相似，但会检查数据是否是正确的utf-8字符
    fn take(self, limit: u64) -> Take<Self>
    where
        Self: Sized,
    { ... }
    //创建一个reader，只能从当前对象中读取小于limit个byte
}
```

### AsyncSeek

```rust
pub trait AsyncSeek {
    fn start_seek(self: Pin<&mut Self>, position: SeekFrom) -> Result<()>;
    fn poll_complete(
        self: Pin<&mut Self>, 
        cx: &mut Context<'_>
    ) -> Poll<Result<u64>>;
}
```

### AsyncSeekExt

```rust
pub trait AsyncSeekExt: AsyncSeek {
    fn seek(&mut self, pos: SeekFrom) -> Seek<'_, Self>
    where
        Self: Unpin,
    { ... }
    fn stream_position(&mut self) -> Seek<'_, Self>
    where
        Self: Unpin,
    { ... }
}
```

### AsyncWrite

```rust
pub trait AsyncWrite {
    fn poll_write(
        self: Pin<&mut Self>, 
        cx: &mut Context<'_>, 
        buf: &[u8]
    ) -> Poll<Result<usize, Error>>;
    fn poll_flush(
        self: Pin<&mut Self>, 
        cx: &mut Context<'_>
    ) -> Poll<Result<(), Error>>;
    fn poll_shutdown(
        self: Pin<&mut Self>, 
        cx: &mut Context<'_>
    ) -> Poll<Result<(), Error>>;

    fn poll_write_vectored(
        self: Pin<&mut Self>, 
        cx: &mut Context<'_>, 
        bufs: &[IoSlice<'_>]
    ) -> Poll<Result<usize, Error>> { ... }
    fn is_write_vectored(&self) -> bool { ... }
}
```

### AsyncWriteExt

```rust
pub trait AsyncWriteExt: AsyncWrite {
    fn write<'a>(&'a mut self, src: &'a [u8]) -> Write<'a, Self>
    where
        Self: Unpin,
    { ... }
    fn write_vectored<'a, 'b>(
        &'a mut self, 
        bufs: &'a [IoSlice<'b>]
    ) -> WriteVectored<'a, 'b, Self>
    where
        Self: Unpin,
    { ... }
    fn write_buf<'a, B>(&'a mut self, src: &'a mut B) -> WriteBuf<'a, Self, B>
    where
        Self: Sized + Unpin,
        B: Buf,
    { ... }
    fn write_all_buf<'a, B>(
        &'a mut self, 
        src: &'a mut B
    ) -> WriteAllBuf<'a, Self, B>
    where
        Self: Sized + Unpin,
        B: Buf,
    { ... }
    fn write_all<'a>(&'a mut self, src: &'a [u8]) -> WriteAll<'a, Self>
    where
        Self: Unpin,
    { ... }
    fn write_u8<'a>(&'a mut self, n: u8) -> WriteU8<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_i8<'a>(&'a mut self, n: i8) -> WriteI8<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_u16<'a>(&'a mut self, n: u16) -> WriteU16<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_i16<'a>(&'a mut self, n: i16) -> WriteI16<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_u32<'a>(&'a mut self, n: u32) -> WriteU32<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_i32<'a>(&'a mut self, n: i32) -> WriteI32<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_u64<'a>(&'a mut self, n: u64) -> WriteU64<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_i64<'a>(&'a mut self, n: i64) -> WriteI64<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_u128<'a>(&'a mut self, n: u128) -> WriteU128<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_i128<'a>(&'a mut self, n: i128) -> WriteI128<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_u16_le<'a>(&'a mut self, n: u16) -> WriteU16Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_i16_le<'a>(&'a mut self, n: i16) -> WriteI16Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_u32_le<'a>(&'a mut self, n: u32) -> WriteU32Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_i32_le<'a>(&'a mut self, n: i32) -> WriteI32Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_u64_le<'a>(&'a mut self, n: u64) -> WriteU64Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_i64_le<'a>(&'a mut self, n: i64) -> WriteI64Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_u128_le<'a>(&'a mut self, n: u128) -> WriteU128Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn write_i128_le<'a>(&'a mut self, n: i128) -> WriteI128Le<&'a mut Self>
    where
        Self: Unpin,
    { ... }
    fn flush(&mut self) -> Flush<'_, Self>
    where
        Self: Unpin,
    { ... }
    fn shutdown(&mut self) -> Shutdown<'_, Self>
    where
        Self: Unpin,
    { ... }
}
```

## 函数

### copy

```rust
pub async fn copy<'a, R: ?Sized, W: ?Sized>(
    reader: &'a mut R, 
    writer: &'a mut W
) -> Result<u64> where
    R: AsyncRead + Unpin,
    W: AsyncWrite + Unpin, 
```

异步地将reader的全部内容复制到writer，该函数返回一个future,持续地从reader中读取数据，然后将数据写入writer,直到reader返回EOF

同步版本：std::io::copy

#### 错误

当poll_read或者poll_write返回错误时future会立即返回一个错误

### copy_bidirectional

```rust
pub async fn copy_bidirectional<A: ?Sized, B: ?Sized>(
    a: &mut A, 
    b: &mut B
) -> Result<(u64, u64), Error> where
    A: AsyncRead + AsyncWrite + Unpin,
    B: AsyncRead + AsyncWrite + Unpin, 
```

将两个可读可写的对象数据互相读取写入,两个过程并行运行。

当streamA读取达到EOF时，streamB的shutdown函数将会被调用，streamA的读取将会结束，但是另外一个方向的读取将继续进行。

当两端的交流都被停止时，函数返回的future结束

#### 错误

当a或b返回错误时，这个future将立即返回错误

### copy_buf

```rust
pub async fn copy_buf<'a, R: ?Sized, W: ?Sized>(
    reader: &'a mut R, 
    writer: &'a mut W
) -> Result<u64> where
    R: AsyncBufRead + Unpin,
    W: AsyncWrite + Unpin, 
```

异步地将一个reader的所有内容复制进writer

#### 错误

如果调用poll_fill_buf或poll_write返回错误时，该future会立即返回一个错误

### duplex

```rust
pub fn duplex(max_buf_size: usize) -> (DuplexStream, DuplexStream)
```

创建一对的DuplexStream流，类似于一对连接后的socket

### empty

```rust
pub fn empty() -> Empty
```

创建一个异步Empty实例

### repeat

```rust
pub fn repeat(byte: u8) -> Repeat
```

创建一个异步reader,无限的可以重复读取出同一个byte

同步版本: std::io::repeat

### sink

```rust
pub fn sink() -> Sink
```

创建一个只写对象，任何poll_write都会返回Poll::Ready(Ok(buf.len()));

同步版本:std::io::sink

### split

```rust
pub fn split<T>(stream: T) -> (ReadHalf<T>, WriteHalf<T>) where
    T: AsyncRead + AsyncWrite, 
```

将一个AsyncRead+AsyncWrite分割为分开的AsyncRead和AsyncWrite对象

如果要将两个对象重新合成为一个，需要调用unsplit函数

### stderr/stdin/stdout

```rust
pub fn stdXXX() -> StdXXX
```

返回一个标准流对象。

# tokio::net

TCP/UDP/Unix bindings for `tokio`.

## 结构体

### TcpListener

```rust
pub struct TcpListener { /* fields omitted */ }
```

一个Tcp套接字服务器,监听连接,通过accept方法来创建一个新的连接。

它可以通过TcpListenerStream来将其装换为一个流对象。

#### 成员函数

##### bind

```rust
pub async fn bind<A: ToSocketAddrs>(addr: A) -> Result<TcpListener>
```

构造函数，并将其绑定到指定的地址，如果给定端口为0，将由操作系统指定一个端口，端口号可通过local_addr函数获得,addr 可以为任意实现了ToSocketAddrs的类。

##### accept

```rust
pub async fn accept(&self) -> Result<(TcpStream, SocketAddr)>
```

接受一个连接，一旦连接创建了，函数将立即让出执行权，当创建成功后，TcpStream对端的地址将会被返回。

##### poll_accept

```rust
pub fn poll_accept(
    &self,
    cx: &mut Context<'_>
) -> Poll<Result<(TcpStream, SocketAddr)>>
```

轮寻接收连接，如果没有连接，则返回Pending

##### from_std

```rust
pub fn from_std(listener: TcpListener) -> Result<TcpListener>
```

通过标准TcpListener(std::net::TcpListener)创建一个新的TcpListener，并且会消耗底层的TcpListener

该函数将标准库的TcpListener包装为tokio版本的TcpListener该函数，不转换底层的listener

###### panic

如果未指定thread-local runtime，函数将恐慌

##### into_std

```rust
pub fn into_std(self) -> Result<TcpListener>
```

from_std的逆过程

##### local_addr

```rust
pub fn local_addr(&self) -> Result<SocketAddr>
```

返回绑定的本地地址。

##### ttl

```rust
pub fn ttl(&self) -> Result<u32>
```

获取IP_TTL选项的值，参见set_ttl。

##### set_ttl

```rust
pub fn set_ttl(&self, ttl: u32) -> Result<()>
```

设置IP_TTL的值，这个值设定了从该套接字发送的每个数据包中的生存时间字段.(属于IP协议中的字段)

#### 实现函数

```rust
impl AsRawFd for TcpListener
impl Debug for TcpListener
impl TryFrom<TcpListener> for TcpListener
```

### TcpSocket

没被转化为TcpStream或TcpListener的套接字。

实例包装了一个系统套接字，并且允许调用者在创建Tcp连接之前配置socket选项

```rust
pub struct TcpSocket { /* fields omitted */ }
```

调用`TcpStream::connect("127.0.0.1:8080")`相当于：

```rust
let addr = "127.0.0.1:8080".parse().unwrap();
let socket = TcpSocket::new_v4()?;
let stream = socket.connect(addr).await?;
```

调用`TcpListener::bind("127.0.0.1:8080")` 相当于:

```rust
let addr = "127.0.0.1:8080".parse().unwrap();
let socket = TcpSocket::new_v4()?;
socket.set_reuseaddr(true)?;
socket.bind(addr)?;
let listener = socket.listen(1024)?;
```

#### 成员函数

##### new_v4

```rust
pub fn new_v4() -> Result<TcpSocket>
```

创建一个AF_INET,SOCK_STREAM类型的socket，如果成功，新创建的TcpSocket将会被返回，出错则返回错误。

##### new_v6

```rust
pub fn new_v6() -> Result<TcpSocket>
```

创建AF_INET6和SOCK_STREAM类型的socket，如果成功，返回TcpSocket，如果失败返回错误。

##### set_reuseaddr

```rust
pub fn set_reuseaddr(&self, reuseaddr: bool) -> Result<()>
```

设置是否处于TIME_WAIT状态下的socket可被绑定(因操作系统而异)

##### reuseaddr

```rust
pub fn reuseaddr(&self) -> Result<bool>
```

获取是否处于TIME_WAIT状态下的socket可被绑定(因操作系统而异)

##### set_reuseport

```rust
pub fn reuseaddr(&self) -> Result<bool>
```

(Unix Only)

端口可复用（因操作系统而异）

##### reuseport

```rust
pub fn reuseport(&self) -> Result<bool>
```

(Unix Only)

获取端口是否可复用

##### set_send_buffer_size

```rust
pub fn set_send_buffer_size(&self, size: u32) -> Result<()>
```

设置Tcp发送缓冲区的大小，在绝大多数操作系统中，相当于设置SO_SNDBUF选项。

##### send_buffer_size

```rust
pub fn send_buffer_size(&self) -> Result<u32>
```

返回Tcp发送缓冲区的大小，在绝大多数操作系统中相当于获取SO_SNDBUF选项的值

##### set_recv_buffer_size

```rust
pub fn set_recv_buffer_size(&self, size: u32) -> Result<()>
```

设置Tcp接收缓冲区的大小，绝大多数操作系统中，相当于设置SO_RCVBUF选项。

##### recv_buffer_size

```rust
pub fn recv_buffer_size(&self) -> Result<u32>
```

返回Tcp缓冲区大小.

##### local_addr

```rust
pub fn local_addr(&self) -> Result<SocketAddr>
```

获取socket的本地地址。

##### bind

```rust
pub fn bind(&self, addr: SocketAddr) -> Result<()>
```

将socket绑定到给定的地址中。

##### connect

```rust
pub async fn connect(self, addr: SocketAddr) -> Result<TcpStream>
```

创建一个Tcp连接，对端地址由addr指出。

##### listen

```rust
pub fn listen(self, backlog: u32) -> Result<TcpListener>
```

将socket转化为一个TcpListener

#### 实现接口

```rust
impl AsRawFd for TcpSocket
impl Debug for TcpSocket
impl FromRawFd for TcpSocket
impl IntoRawFd for TcpSocket
```

### TcpStream

```rust
pub struct TcpStream { /* fields omitted */ }
```

#### 成员函数

##### connect

```rust
pub async fn connect<A: ToSocketAddrs>(addr: A) -> Result<TcpStream>
```

创建一个TCP连接。

##### from_std

```rust
pub fn from_std(stream: TcpStream) -> Result<TcpStream>
```

将标准tcp流转化为tokio tcp流

##### into_std

```rust
pub fn into_std(self) -> Result<TcpStream>
```

from_std的逆操作

##### local_addr

```rust
pub fn local_addr(&self) -> Result<SocketAddr>
```

返回本地端口

##### peer_addr

```rust
pub fn peer_addr(&self) -> Result<SocketAddr>
```

返回对端的地址

##### poll_peek

```rust
pub fn poll_peek(
    &self,
    cx: &mut Context<'_>,
    buf: &mut ReadBuf<'_>
) -> Poll<Result<usize>>
```

##### ready

```rust
pub async fn ready(&self, interest: Interest) -> Result<Ready>
```

返回当前状态。有数据可读，有空间可写。。

##### readable/writeable

```rust
pub async fn readable(&self) -> Result<()>
```

等待当前tcp流可读/写。

##### poll_read_ready/poll_write_ready

```rust
pub fn poll_read_ready(&self, cx: &mut Context<'_>) -> Poll<Result<()>>
```

返回可读/写性

##### try_read/try_write

```rust
pub fn try_read(&self, buf: &mut [u8]) -> Result<usize>
```

尝试非阻塞地读/写

##### try_read_vectored/try_write_vectored

```rust
pub fn try_read_vectored(&self, bufs: &mut [IoSliceMut<'_>]) -> Result<usize>
```

尝试读取到vector中

##### peek

```rust
pub async fn peek(&self, buf: &mut [u8]) -> Result<usize>
```

拷贝接收到的数据到buf中，但是并不将数据从队里中移除

##### nodelay

```rust
pub fn nodelay(&self) -> Result<bool>
```

获取Socket选项中的TCP_NODELAY选项的值

##### set_nodelay

```rust
pub fn set_nodelay(&self, nodelay: bool) -> Result<()>
```

设置TCP_NODELAY选项，如果为true则关闭Nagle算法，这意味着发送的数据段将尽可能早地被送出

##### linger

```rust
pub fn linger(&self) -> Result<Option<Duration>>
```

获取SO_LINGER选项的值

##### set_linger

```rust
pub fn set_linger(&self, dur: Option<Duration>) -> Result<()>
```

设置SO_LINGER选项的值

##### ttl

```rust
pub fn ttl(&self) -> Result<u32>
```

返回IP_TTL选项的值。



##### set_ttl

```rust
pub fn set_ttl(&self, ttl: u32) -> Result<()>
```

设置IP_TTL选项的值

##### split

```rust
pub fn split<'a>(&'a mut self) -> (ReadHalf<'a>, WriteHalf<'a>)
```

将stream分割。同std::io中的split

##### into_split

```rust
pub fn into_split(self) -> (OwnedReadHalf, OwnedWriteHalf)
```

将自己转变为两个只读和只写的部分，与split函数类似，但是会捕获self

#### 实现接口

```rust
impl AsRawFd for TcpStream
impl AsRef<TcpStream> for ReadHalf<'_>
impl AsRef<TcpStream> for WriteHalf<'_>
impl AsRef<TcpStream> for OwnedReadHalf
impl AsRef<TcpStream> for OwnedWriteHalf
impl AsyncRead for TcpStream
impl AsyncWrite for TcpStream
impl Debug for TcpStream
impl TryFrom<TcpStream> for TcpStream
```



### UdpSocket

```rust
pub struct UdpSocket { /* fields omitted */ }
```

Udp套接字

#### 成员函数

##### bind

```rust
pub async fn bind<A: ToSocketAddrs>(addr: A) -> Result<UdpSocket>
```

绑定本地地址。

##### from_std

```rust
pub fn from_std(socket: UdpSocket) -> Result<UdpSocket>
```

将标准库的UdpSocket构造为tokio库的UdpSocket，如果thread-local未指定则panic

##### into_std

```rust
pub fn into_std(self) -> Result<UdpSocket>
```

将tokio的UdpSocket转变为标准库的UdpSocket

##### local_addr

```rust
pub fn local_addr(&self) -> Result<SocketAddr>
```

返回本地地址

##### connect

```rust
pub async fn connect<A: ToSocketAddrs>(&self, addr: A) -> Result<()>
```

为发送指定默认目标ip地址，指定后用send ，recv或read，write发送

##### ready

```rust
pub async fn ready(&self, interest: Interest) -> Result<Ready>
```

返回可读/写状态的集合

##### writeable / readable

```rust
pub async fn writable(&self) -> Result<()>
```

等待socket可读/写

.

.

.

参见docs.rs

### UnixDatagram

TODO

### UnixListener

TODO

### UnixStream

TODO

## Traits

### ToSocketAddrs

TODO

## 函数                                             

### lookup_host

this.na



# tokio::process



## Command

```
```



同步版本：std::process::Command









