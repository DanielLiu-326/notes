# 0.面向对象设计原则

- 单一职责原则：一个对象应该只包含单一的职责，并且该职责被完整地封装到一个类中
- 开闭原则：软件实体应当对扩展开放对修改关闭
- 里氏代换原则：所有引用基类的地方必须能透明的使用其子类的对象
- 依赖倒转原则：高层模块不应当依赖低层模块，他们应当都依赖抽象，抽象不应该依赖于细节，细节应该依赖于抽象。
- 接口隔离原则：客户端不应该依赖那些它不需要的接口
- 合成复用原则：优先使用对象组合而不是继承来达到复用的目的。
- 迪米特法则：每一个软件单位对其他的单位都只有最少的知识，而且局限于那些于本单位密切相关的软件单位。

# 1.工厂模式

创建型模式

## 1.1 简单工厂模式

### 定义

Simple Factory Pattern

定义：定义一个工厂类，它可以根据参数的不同返回不同类型的实例，被创建的实例通常具有共同的父类。

### 角色

- Factory工厂角色

  负责创建产品

- Product抽象产品角色

  工厂类创建对象的父类

- ConcreteProduct具体产品角色

  工厂类创建的目标对象

### 实例代码

```c#
interface Chart{
    void display();
}
class HistogramChart:Chart{
    public HistogramChart(){}
    public void display(){
        Console.WriteLine("柱状图")
    }
    
}
class PieChart:Chart{
   	public PieChart(){}
    public void display(){
        Console.WriteLine("饼状图");
    }
}
class LineChart:Chart{
    public LineChart(){}
    public void display(){
        Console.WriteLine("折线图");
    }
}
class ChartFactory{
    public static Chart getChart(String type){
        Chart chart = null;
        switch(type){
            case "HistogramChart":{
                chart = new HistogramChart();
            }
            case "PieChart":{
                chart = new PieChart();
            }
            case "LineChart:"{
                chart = new LineChart();
            }
        }
        return chart;
    }
}
```

### 变体

- 反射

  ```C#
  public class ChartFactory {
   	public ChartFactory{
          
      }
      public static Chart getChart(string chartClass){
          //获取当前程序集
          Assembly ass = Assembly.GetCallingAssembly();
          //获取程序集中的类
          Type t = ass.GetType(chartClass);
          //创建类的实例对象
          Chart o = (Chart)Activator.CreateInstance(t);
          return o;
      }	
  }
  ```

- 简化

  ```C#
  abstract class Chart{
      public void display();
      public static Chart getChart(String chartType){
          ...
      }
  }
  class PieChart:Chart{
  	public PieChart(){}
      public void display(){
          Console.WriteLine("pie chart");
      }
  }
  ```
  
- 配置文件

  ```xml
  <?xml version="1.0" encoding="utf-8" ?>
  <configuration>
      <appSettings >
          <add key ="chartType" value ="PieChart"/>
      </appSettings>
  </configuration>
  ```

  ```C#
  static void main(string[] args){
      Chart chart;
      string chartStr = ConfigurationManager.AppSettings["chartType"];
      chart = ChartFactory.getChart(chartStr);
      chart.display();
  }
  ```

### 特点

优点

- 使用者可以省略创建对象的逻辑，创建使用分离
- 工厂可以自定义传入字符串与生成的产品类之间的对应关系，使用者只需传入字符串，可以减少记忆类名成本
- 可以引入配置文件在不更改代码情况下更改产品

缺点：

- 职责过重，一旦factory出现错误将影响整个产品的创建
- 增加类的个数（加入了工厂类），增加了系统复杂性
- 扩展困难，产品类多的时候工厂方法将难以维护
- 工厂类的方法采用静态方法，继承时不能被重写，实例对象访问的时候只能访问引用类型本身对应类型的方法

## 工厂方法模式

### 定义

Factory Method Pattern

定义一个用于创建对象的接口，但是让子类决定哪一个类实例化。 工厂方法模式让一个类的实例化延时到其子类。

### 角色

- Product抽象产品：它是定义产品的接口，是工厂方法模式所创建对象的超类。
- ConcreteProduct 具体产品：实现了抽象产品的接口，某种类型的具体产品由专门的具体工厂创建，具体产品与工厂类型一一对应
- Factory抽象工厂：所有工厂类型必须实现的接口
- ConcreteFactory具体工厂：抽象工厂类的子类，与ConcreteProduct一一对应

### 实例代码

```C#
// product interface
interface Logger{
    void WriteLog();
}
// concrete product class
class DatabaseLogger:Logger{
    public void WriteLog(){
        Console.WriteLine("database logger");
    }
}
class FileLogger:Logger{
    public void WriteLog(){
        Console.WriteLine("file logger");
    }
}
//Logger Factory interfa
interface LoggerFactory{
    Logger createLogger();
}
class DataBaseLoggerFacotry:LoggerFactory{
    public Logger createLogger(){
    	Logger logger = new DatabaseLogger();
    	return looger;
    }
}
class FileLoggerFactory:Logger{
    public Logger createLogger(){
        Logger logger = new FileLogger();
        return logger;
    }
}
```

### 变体

- 配置文件+反射

  appSettings配置文件：

  ```xml
  <?xml version="1.0" encoding="utf-8" ?>
  <configuration>
  	<appSettings>
      	<add key = "factory" value = "FactoryMethodSample.FileLoggerFactory" />
      </appSettings>
  </configuration>
  ```

  程序类的main函数

  ```C#
  static void Main(String[] args){
      LoggerFactory loggerFactory;
      Logger logger;
      string factoryString = ConfigurationManager.AppSettings["factoryString"];
      factory = (LoggerFactory)Assembly.Load("FactoryMethodSample").CreateInstance(factoryString);
      logger = factory.createLogger();
      logger.WriteLog();
  }
  ```

- 带有重载

  ```C#
  abstract class LoggerFactory{
      public void WriteLog(){
          Logger logger = this.CreateLogger();
          logger.WriteLog();
      }
      public abstract Logger CreateLogger()
  }
  ```

### 特点

优点：

- 工厂使用者无需关心创建细节无需知道具体产品的类名。
- 由于工厂类型与实际产品一一对应，它能够让使用者决定创建哪种产品，而不需要关心创建该种产品的细节。
- 加入新产品时无需修改抽象工厂类和抽象产品接口，无需修改客户端（如果使用了反射+配置文件），只需要添加一个具体工厂和具体产品即可

缺点：

- 需要编写新的具体产品类，而且需要提供其工厂类，类的个数随产品种类二倍增加，增加了系统复杂性。
- 增加理解难度。

使用环境：

- 客户端不知道它需要的对象的类，只需要知道对应的工厂即可。
- 抽象工厂类通过子类指定创建哪个对象，利用了多态性和里氏替换原则。

## 抽象工厂模式

### 定义

Abstract Factory Pattern

提供一个创建一系列**相关**或**相互依赖**对象的接口，无需指明其具体类。

### 角色

- AbstractFactory抽象工厂：声明了一组创建一系列相关产品的方法
- ConcreteFactory具体工厂：实现了在抽象工厂中声明的创建产品的方法，生成了一组具体产品，这些产品之间有可能相互关联或有关系
- AbstractProduct抽象产品：为每种产品声明接口，声明了具体产品想要被使用者知晓的所有方法。
- ConcreteProduct具体产品：定义了具体工厂生产的具体产品对象，实现了抽象产品接口中声明的业务方法。

### 实例代码

```c#
interface AbstractProductA{
    void productAFunction();
}
interface AbstractProductB{
    void productBFunction();
}
abstract class AbstractFactory{
    public abstract AbstractProductA createProductA();
    public abstract AbstractProductB createProductB();
}
class ConcreteFactory1:AbstractFactory{
    public override AbstractProductA createProductA(){
        return new ConcreteProductA1();
    }
    public override AbstractProductB createProductB(){
        return new ConcreteProductB1();
    }
}
class ConcreteFactory2:AbstractFactory{
    public override AbstractFactoryA createProductA(){
        return new ConcreteProductA2();
    }
    public override AbstractFactoryB createProductB(){
        return new ConcreteProductB2();
    }
}
```

### 变体

- 配置文件+反射

  配置文件+反射不是对工厂本身进行修改，而是动态使用工厂的方式。

  ```xml
  <?xml version="1.0" encoding="utf-8" ?>
  <configuration>
  	<appSettings>
      	<add key = "factory" value = "AbstractFactorySample.ConcreteFactory1" />
      </appSettings>
  </configuration>
  ```

  使用者（Client）

  ```C#
  static void Main(string[] args){
      AbstractFactory factory;
      AbstractProductA productA;
      AbstractProductB productB;
      string factoryType= COnfigurationManager.AppSettings["factory"];
      factory = (AbstractFactory)Assembly.Load("AbstractFactorySample").createInstance(factoryType);
      productA = factory.createProductA();
      productB = factory.createProductB();
  }
  ```

### 特点

优点：

- 隔离了具体类的生成，客户端无需插手创建过程，只需修改具体类的类型即可更改使用的产品族
- 能保证客户端只使用同种产品族内的产品。
- 增加新的产品族方便，无需修改客户端（如果是配置-反射式使用方式），无需修改已有工厂和产品。

缺点：

- 增加产品类型时不方便，需要对原有抽象层修改（增加```createProductC```）进而导致所有具体工厂都要修改。

使用场景：

- 系统不需要知道产品族实例如何组合，创建，表达。
- 系统有多余一个产品族，但使用时只需要使用同一个产品族内的产品。
- 属于同一个产品族内的产品需要一起使用
- 抽象类别结构稳定（抽象product不再会增加减少）

# 2.建造者模式

创建型模式

## 定义

Builder Pattern

将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。

## 角色

- Builder 抽象构建者：为创建产品的部件提供构建方法（BuildPartX） ，以及提供GetResult接口用于获取最终结果
- ConcreteBuilder具体构建者：实现了Builder接口，是一类产品实例的构建器，本身建造时应不接受参数
- Product产品：被构建的复杂对象（是一个具体类），包含多个组件
- Director指挥者：安排复杂对象的建造次序，客户端一般只需要与指挥着进行交互，在客户端确定具体建造者的类型（或通过反射-配置文件），并实例化具体建造者对象。然后传给Director，让director操作builder。

## 实例代码

产品类

```C#
class Product{
    private ComponentA partA;
    private ComponentB partB;
    private COmponentC partC;
    //.. setter or getter
}
```

抽象建造者

```C#
abstract class Builder{
    protected Product product = new Product();
    public abstract void BuildPartA();
    public abstract void BuildPartB();
    public abstract void BuildPartC();
    
    public Product getResult(){
        return product;
    }
}
```

具体构建者

```c#
class ConcreteBuilder1:Builder1{
    public override void BuildPartA(){
        product.PartA = ...;
    }
    public override void BuildPartB(){
        product.PartB = ...;
    }
    public override void BuildPartC(){
        product.PartC = ...;
    }
}
```

指挥者

```C#
class Director{
    private Builder builder;
    public Director(Builder builder){
        this.builder = builder;
    }
    public void SetBuilder(Builder builder){
        this.builder = builder;
    }
    public Product construct(){
        builder.BuildPartA();
        builder.BuildPartB();
        builder.BuildPartC();
        return Builder.GetResult;
    }
}
```

客户端

```C#
...
Builder builder = new ConcreteBuilder1();
Director director= new Director(builder);
Product product = director.construct();
...
```

## 变体

- 省略Director方法1

  改写Builder,合并Director与builder

  ```c#
  abstract class AbstractBuilder{
      protected static Product product = new Product();
      public abstract void buildPartA();
      public abstract void buildPartB();
      public static Product construct(AbstractBuilder builder){
          builder.buildPartA();
          builder.buildPartB();
          return builder.getResult();
      }
  }
  ```

  使用者代码：

  ```C#
  ...;
  AbstractBuilder ab;
  string builderType = ConfigurationManager.AppSettings["builder"];
  ab = (AbstractBuilder)Assembly.Load("BuilderExtend").createInstance(builderType);
  Product product;
  product = ab.construct(ab);
  ...;
  ```

- 省略Director方法2

  ```C#
  abstract class AbstractBuilder{
      protected Product product = new Product();
      public abstract void buildPartA();
      public abstract void buildPartB();
      public Product construct(){
          this.buildPartA();
          this.buildPartB();
          return this.product;
      }
  }
  ```

  客户端

  ```c#
  AbstractBuilder ab;
  string builderType = ConfigurationManager.AppSettings["builder"];
  ab = (AbstractBuilder)Assembly.Load("BuilderExtend").CreateInstance(builderType);
  Product product;
  product = ab.construct();
  ```

- 钩子方法

  改写builder,添加hook方法，用于builder被继承后重写以影响Director的调用过程。

  ```C#
  abstract class AbstractBuilder{
      protected Product product = new Product();
      public abstract void buildPartA();
     	public abstract void buildPartB();
      public virtual bool hookMethod(){
          return false;
      }
      public Product createProduct(){
          return this.product;
      }
  }
  ```

  具体builder重写hook方法

  ```c#
  class ConcreteBuilder{
     	public override void buildPartA(){
          this.product.partA = ...;
      }
      public override void buildPartB(){
          this.product.partB = ...;
      }
      public override bool hookMethod(){
          return true;
      }
  }
  ```

  Director检查hook方法返回值，并根据返回值调整build过程

  ```C#
  class Director{
      public Product constructor(AbstractBuilder builder){
          builder.buildPartA();
          if(builder.hookMethod()){
              builder.builderPartB();
          }else{
              //do something different with builder;
          }
          return builder.getResult();
      }
  }
  ```

## 特点

优点

- 客户端不需要知道产品的内部组成细节，将产品本身与产品的创建过程解偶，使得相同的创建代码可以创建不同的对象。
- 建造者之间独立，可以方便的替换和添加创建者
- 可以更加精细的控制创建过程，将创建过程分布到不同的方法内。

缺点：

- 建造者模式适合产品相似性高的应用场景，如果差异性大则不适合用建造者模式
- 如果产品内部变化复杂，可能需要定义许多具体建造者，增加了系统维护的成本。

使用场景：

- 对象内部有许多复杂的部件，包含很多成员变量。
- 对象内部的部件创建具有依赖性，需要注意创建顺序。
- 对象的创建过程独立于创建该对象的类。
- 隔离复杂对象的创建和适用，并使得相同的创建过程创建不同的产品。

# 3.原型模式

创建型模式

## 定义

Prototype Pattern

适用原型实例指定待创建的对象类型，并通过复制原型实例来创建新的对象

## 角色

- Prototype抽象原型类：它是声明克隆方法的接口，是所有具体原型类的公共类，可以是抽象类，接口，普通类
- ConcretePrototype具体原型类：实现原型类中声明的克隆方法，在克隆方法中返回自己的一个克隆对象。
- Client客户：适用原型类，通过克隆方法创建原型实例。

## 实例代码

- 1.经典实现

  ```C#
  abstract class Prototype{
      public abstract Prototype clone();
  }
  class ConcreteType:ProtoType{
      private string attr;
      public override Prototype clone(){
          ConcretePrototype prototype = new ConcretePrototype();
          prototype.attr = attr;
          return prototype;
      }
  }
  ```

  ```C#
  ...
  ConcretePrototype prototype = new ConcretePrototype;
  ConcretePrototype prototypeCopy = (ConcretePrototype)prototype.clone();
  ...
  ```

- c# 中的MemberwiseClone()与ICloneable

  MemberwiseClone是所有类都实现的一个接口,它将引用成员的引用全部拷贝，将值类型的成员全部复制。

  ```C#
  class ConcretePrototypeA{
      private Member member;
      public ConcretePrototypeA clone(){
          return (ConcretePrototypeA)this.MemberwiseClone();
      }
  }
  ```

## 变体

- 使用原型管理器（Prototype Manager）

  ```C#
  using System.Collections;
  class PrototypeManager{
      Hashtable ht = new Hashtable();
      public PrototypeManager(){
          ht.Add("A",new ConcretePrototypeA());
          ht.Add("B",new ConcretePrototypeB())
      }
      public void Add(string key,Prototype prototype){
          ht.add(key,prototype);
      }
      public Prototype Get(string key){
          Prototype clone = null;
          clone = ((Prototype)ht[key]).clone();
          return clone;
      }
  }
  ```

## 特点

优点

- 创建比较复杂时用prototype可以简化创建过程
- 扩展性好，客户端可以针对prototype接口编程
- 提供了简化创建结构，只需要一个接口和具体类型
- 可以使用深克隆方式保存对象状态，可以在做操作前克隆一份，这样就可以保存操作后的状态和操作前的状态。

缺点

- 需要为每个类配备一个克隆方法，且克隆方法在类内部，当对类进行改造时就要同时改造该方法，违背了开闭原则。
- 深克隆复杂，不易修改。

适用环境：

- 创建新对象成本较大
- 系统要保存对象的状态，状态变化很小
- 需要避免适用分层次的工厂类创建分层次的对象，并且类的实例对象只有一个或很少的组合状态。

# 4.单例模式

创建型模式

## 定义

Singleton Pattern

确保一个类只有一个实例，并提供一个全局访问点，来访问这个唯一实例。

## 角色

- singleton单例类：拥有一个私有构造函数，确保用户无法通过new关键字创建它。

## 实例代码

- 经典实现

  非线程安全，懒汉式

  ```c#
  class Singleton{
      private static Singleton instance = null;
      private Singleton(){
          //构造函数私有，确保不会被外部创建
      }
      public static Singleton GetInstance(){
          if(instance==null){
              instance = new Singleton();
              return instance;
          }
      }
  }
  ```

- 饿汉式

  线程安全，饿汉式

  ```c#
  class Singleton{
      private static Singleton instance = new Singleton();
      private Singleton(){}
      public static Singleton getInstance(){
          return instance;
      }
  }
  ```

- 懒汉式

  线程安全，懒汉式，双重加锁判断

  ```c#
  class Singleton{
      private static Singleton instance = null;
      private static readonly object locker = new Object();
      private Singleton(){}
      public static Singleton getInstance(){
          if(instance == null){
              lock(locker){
                  if(instance==null){
                      instance = new Singleton();
                  }
              }
          }
          return instance;
      }
  }
  ```

## 特点

优点

- 提供了对唯一实例的受控访问。
- 节约内存资源
- 可以以相似的方式实现“n例模式”（多例模式）

缺点

- 单例模式没有抽象层，所以扩展困难
- 单例类职责过重，一定程度上违背了单一职责原则
- 如果单例共享对象长时间不用可能会被垃圾回收器回收（？有疑问）

使用场景

- 系统只需要一个实例对象
- 客户端操作某一个资源只允许同时有一个。



# 5.适配器（包装器）模式

结构型模式

## 定义

Adapter Pattern(Wrapper Pattern)

将一个类的接口转换成客户希望的另一个接口，适配器模式让那些接口不兼容的类可以一起工作

## 角色

- Target目标抽象类：目标抽象类定义客户所需要的接口，可以是一个抽象类或接口，也可以是具体类。由于某些语言不支持多继承，所以只能是接口
- Adapter适配器类：可以调用另一个接口，作为一个转换器，对adaptee和target进行是配。
- Adaptee适配者类：被适配的角色，一般是一个具体类，包含了客户希望使用的业务方法。

## 实例代码

- 类适配器（继承方式）

  ```c#
  class Adapter：Adaptee,Target{
      public void request(){
          base.specificRequest();
      }
  }
  ```

- 对象适配器（组合方式），使用频率高

  ```c#
  class Adapter:Target{
      private Adaptee adaptee;
      public Adapter(Adaptee adaptee){
          this.adaptee = adaptee;
      }
      public void request(){
          adaptee.specificRequest();
      }
  }
  ```


## 变体

- 缺省适配器

  当不需要实现一个接口所提供的所有方法时，可先设计一个抽象类实现该接口，并为接口中的每个方法提供一个默认实现，那么该抽象类的子类可以有选择性的覆盖父类的某些方法来实现需求，它适用于不想使用一个接口中的所有方法的情况，又称为单接口适配器模式。

  ```c#
  abstract class AbstractAdapter:TargetInterface{
      public void method1(){
          //default implementation;
      }
      public void method2(){
          //default implementation;
      }
  }
  ```

- 双向适配器

  ```c#
  public class Adatper:Target,Adaptee{
      private Target target;
      private Adaptee adaptee;
      public Adapter(Target target){
          this.target = target;
      }
      public Adapter(Adaptee adaptee){
          this.adaptee = adaptee;
      }
      public void request(){
          targetRequest();
      }
      public void specificRequest(){
          target.request();
      }
  }
  ```

  

## 特点

优点：

- 将目标类和适配者类解耦，无需修改原有结构。
- 增加了类的透明性和复用性。
- 灵活性和扩展性好。
- 对象适配器可以适配适配者的子类（里氏替换原则）。
- 把多个不同的适配者适配到同一个目标。

缺点：

- 不支持多继承的语言一次只能适配一个适配者类，不能适配多个适配者。
- 适配者类不能为sealed（final）
- 目标抽象只能为接口不能为类。使用有局限性。

使用场景：

- 系统要用现有的类满足系统需要的接口
- 创建可重复使用的类，用于和一些彼此之间没有太大关联的类，包含一些可能在将来引进的类一起工作。

# 6.桥接模式

结构性模式

## 定义

Bridge Pattern

将抽象部分与它的实现部分解耦，使得两者能独立变化

## 角色

- Abstract抽象类：用于定义抽象类的接口，通常是抽象类而不是接口。
- RefinedAbstraction扩充抽象类：它扩充由Abstraction定义的接口，通常情况下不再是抽象类而是具体类，通过调用Implementor实现了在Abstraction中声明的抽象业务方法。
- Implementor实现类接口：Implementor只是基本的接口，它只实现最基本的功能，复杂逻辑由RefinedAbstraction实现。
- ConcreteImplementor具体实现类：它具体实现了Implementor的接口，在不同的ConcreteImplemtor中有不同的实现方式。

## 实例代码

```c#
interface Implementor{
    void operation0();
}
```

```C#
class ConcreteImplementor:Implementor{
    public void operation0(){
        //具体实现
    }
}
```

```C#
abstract class Abstraction{
    protected Implementor impl;
    public void setImpl(Implementor impl){
        this.impl = impl;
    }
    public abstract void operation1(){
        //call impl.operation0 in some way;
    }
}
```

```c#
class RefinedAbstraction:Abstraction{
    public override void operation(){
        impl.operationImpl();
    }
}
```

## 特点

优点：

- 分离接口的抽象部分和实现部分，桥接模式使用了对象间的关联关系解耦了实现与抽象间的固有绑定关系。
- 可以一定程度上解决多继承问题
- 提高了系统的可扩展性。

缺点：

- 增加系统的理解难度
- 需要正确辨识出系统中变化的两个维度，适用范围有局限性。

使用场景：

- 系统需要在抽象化和具体化之间增加更多的灵活性，避免在两个层次之间建立静态联系。
- 抽象部分和实现部分可以以继承的方式独立扩展而互不影响。
- 一个类存在两个独立变化的维度，而且这两个维度都需要独立的进行扩展。
- 对于不希望使用继承或者因为多层继承导致系统类个数急剧增加的系统。

# 7.组合模式

结构型模式

## 定义

Composite Pattern

组合多个对象形成树形结构，以表示具有部分-整体关系的层次结构，组合模式让客户端可以统一的对待单个对象和组合对象。

## 角色

- Component抽象构件：可以是接口或者抽象类，为叶子节点和容器构件对象声明了接口，可以对子构件进行增删改查。
- Leaf叶子构件：在组合模式中表示叶子节点对象，叶子节点没有子节点，它实现了在抽象构件中定义的行为。
- Composite容器构件：表示容器节点对象，容器节点包含子节点，其子节点可以是容器节点也可以是叶子节点

## 实例代码

```c#
abstract class Component{
    public abstract void Add(Component c);
    public abstract void Remove(Component c);
	public abstract Component GetChild(int i);
    public abstract void Operation();
}
```

```C#
class Leaf:Component{
    public override void Add(Component c){
        //异常处理或错误提示
    }
    public override void Remove(Component c){
        //异常处理或错误提示
    }
    public override Component GetChild(int i){
        //异常处理或错误提示
        return null;
    }
    public override void Operation(){
        //叶子构件具体业务方法的实现。
    }
}
```

```c#
class Composite:Component{
    private List<Component> list = new List<Component>();
    public override void Add(Component c){
        list.Add(c);
    }
    public override void Remove(Component c){
        list.Remove(c);
    }
    public override Component GetChild(int i){
        return (Component)list[i];
    }
    public override void Operation(){
        foreach(Object obj in list){
            ((Component)obj).Operation();
        }
    }
}
```

## 变体

- 透明组合模式

  即：叶子类也实现Component的所有方法，但运行时调用会出错。（上述代码就是）

- 安全组合模式

  叶子节点没有Component的所有方法没有Add,Remove,GetChild，所以编程时要区分对待。

## 特点

优点：

- 组合模式可以清楚的定义分层次的复杂对象，表示对象的全部或部分层次，让客户忽略了层次的差异，方便对整个层次结构进行控制。
- 客户端可以一致地使用一个组合结构或单个对象，不必关心处理的是单个对象还是整个组合结构，简化了客户端代码。
- 在组合模式中增加新的容器和叶子构件都很方便，无需对现有类库进行修改，遵循开闭原则。
- 通过叶子对象和容器对象的递归组合，可以形成复杂的树形结构。

缺点：

- 增加新构件时很难对容器中的构件类型进行限制。

使用场景

- 具有整体部分层次的结构，希望屏蔽整体与部分的差异。
- 需要树形组织的对象结构。
- 系统能够分离出叶子对象和容器对象。

# 8.装饰模式

结构型模式

## 定义

Decorator Pattern

动态地给一个对象增加一些额外的职责，就扩展功能而言，装饰模式提供了一种比使用子类更加灵活的替代方案。

## 角色

- Component抽象构件：具体的构件和抽象装饰类的共同父类，声明了在具体构件中实现的业务方法。
- ConcreteComponent具体构件：它是抽象构件类的子类，用于定义具体的构件对象，实现了在抽象构件中声明的方法。
- Decorator抽象装饰类：它也是抽象构件类的子类，用于给具体构件增加职责，但是具体职责通常在子类内部实现，维护一个指向抽象构件对象的引用，通过该引用可以调用装饰之前构件对象的方法，并通过其子类扩展该方法，来达到装饰的目的。
- ConcreteDecorator具体装饰类：它是抽象装饰器的子类，负责向构件中添加新的职责，每一个具体装饰类都定义了一些新行为，可以调用在抽象装饰器类中定义的方法，并可以增加新的方法以扩展对象的行为。

## 实例代码

```c#
abstract class Component{
    public abstract void operation();
}
```

```C#
class ConcreteComponent:Component{
    public override void operation(){
        //实现代码
    }
}
```

```c#
class Decorator:Component{
    private Component component;
    public Decorator(Component component){
        this.component = component;
    }
    public override void operation(){
        component.operation();//调用原业务方法。
    }
}
```

```c#
class ConcreteDecorator:Decorator{
    public ConcreteDecorator(Component component):base(component){
        
    }
    public override void Operation(){
        base.Operation();
        AddedBehavior();
    }
    public void AddedBehavior(){
        //added behavior
    }
}
```

## 变体

- 透明装饰模式

  客户端需要完全根据抽象编程，不能直接使用ConcreteDecorator的方法。

- 半透明装饰模式

  客户端可以使用具体类型。

## 特点

优点：

- 扩展对象比继承更加灵活，不会导致类的数量急剧增加
- 装饰模式可以通过一种动态的方式增加一个对象的功能，通过配置文件可以在运行时选择不同的具体装饰类，实现不同的行为。
- 装饰模式可以对一个对象进行多次装饰，通过使用不同的具体装饰类型以及这些装饰类的排列组合，可以创造出很多不同行为的组合。得到功能更加强大的对象。
- 在装饰模式中，具体构件类与具体装饰类可以独立变化，用户可以根据需要增加新的具体构件类和具体装饰类，无需修改原有代码，符合开闭原则。

缺点：

- 使用装饰模式会产生很多小对象，影响性能。
- 排错困难。

使用场景

- 在不影响其他对象的情况下，以动态透明的方式给单个对象添加职责。
- 不能采用继承对系统扩展或继承不利于系统维护时可以使用装饰模式。



# 9.外观模式

结构型模式

## 定义

Facade Pattern

为子系统中的一组接口提供一个统一的入口，外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。

## 角色

- Facade外观角色：在客户端可以调用它的方法，在外观角色中可以知道相关的子系统的功能和责任。
- SubSystem子系统角色：软件系统中有一个或者多个子系统角色，每一个子系统可以不是一个单独的类，而是一个类的集合，它实现子系统的功能，每一个子系统都可以被客户端直接调用，或者被 外观角色调用。

## 实例代码

```c#
class SubSystemA{
    public void MethodA(){
        
    }
}
class SubSystemB{
    public void MethodB(){
        
    }
}
class SubSystemC{
    public void MethodC(){
        
    }
}
public class Facade{
    private SubSystemA obj1 = new SubSystemA();
    private SubSystemB obj2 = new SubSystemB();
    private SubSystemC obj3 = new SubSystemC();
    public void Method(){
        obj1.MethodA();
        obj2.MethodB();
        obj3.MethodC();
    }
}
```

## 变体

- 抽象外观类

  ```c#
  public interface Facade{
      public abstract void Method();
  }
  ```

  ```c#
  public class ConcreteFacade{
      //....obj1 obj2 obj3
      public override void Method(){
   		//do something with obj1 obj2 obj3       
      }
  }
  ```

## 特点

优点

- 屏蔽了子系统组件，减少了客户端所需处理的对象数目，使得子系统使用起来更加容易。
- 实现了子系统与客户端之间的松耦合关系使得子系统的变化不会影响到调用它的客户端，只需要调整外观类即可。
- 一个子系统修改对其他子系统没有影响，而且内部变化不会影响到外观对象。

缺点

- 不能限制客户端直接接触子系统类。
- 设计不当时，子系统添加需要修改外观类的源代码。

使用场景

- 要为了访问一系列复杂的子系统提供一个简单入口时可以使用外观模式
- 客户程序与多个子系统之间存在很大的依赖性，引入外观类可以将子系统与客户端解耦，提高子系统的独立性和可移植性。
- 可以使用外观模式定义系统中的每一层入口，层与层之间不直接产生联系，而是通过外观类建立联系降低了耦合度。

# 10.享元模式

结构型模式

## 定义

Flyweight Pattern

运用共享技术有效的支持大量细粒度对象的复用。

## 角色

- Flyweight抽象享元类：是接口或抽象类，声明了具体享元类公共方法，这些方法可以向外界提供享元对象的内部数据（状态），
- ConcreteFlyweight具体享元类：实现了抽象享元类，其实例被称为享元对象，并内部提供了存储空间，通常可以结合单例模式来设计具体享元类，为每一个具体享元类提供唯一的享元对象。
- UnsharedConcreteFlyweight非共享具体享元类：并不是所有的抽象享元类的子类都需要被共享，用户可以将不能被共享的子类设计为非共享的具体享元类，当需要具体享元类对象时可以直接通过实例化创建
- FlyweightFactory享元工厂类：用于创建并管理享元对象，它针对抽象享元类编程，将各种类型的具体享元对象存储在享元池中（一般是一个map），

## 实例代码

```c#
abstract class Flyweight{
    public abstract void operation(string extrinsicState);
}
```

```c#
class ConcreteFlyweight:Flyweight{
    private string intrinsicState;
    
    public ConcreteFlyweight(string intrinsicState){
        this.intrinsicState = intrinsicState;
    }
    
    public override void operation(string extrinsicState){
        //业务方法。
    }
    
}
```

```c#
class UnsharedConcreteFlyweight:Flyweight{
    //实现业务方法
    public override void operation(string extrinsicState){
        //业务方法。
    }
}
```

```c#
class FlyweightFactory{
    private Hashtable flyweights = new Hashtable();
    public Flyweight getFlyweight(string key){
        //if the object exist in hashtable;
        if(flyweights.ContainsKey(key)){
            return (Flyweight)flyweights[key];
        }else{
            Flyweight fw = new ConcreteFlyweight("state");
            flyweights.Add(key,fw);
            return fw;
        }
    }
}
```

## 变体

- 无外部状态

  如上代码

- 有外部状态。

  外部状态需要手动保存，不在本设计模式范畴，Flyweight只保存不变的部分。

## 特点

优点

- 减少内存中的对象数量，使得相同或相似的对象在内存中只保存一份
- 外部状态相互独立，不会影响到内部状态，从而使得享元对象可以在不同环境中被共享

缺点

- 使得系统变得复杂，需要分离外部状态和内部状态
- 对象读取外部状态的过程会增加调用开销

使用场景

- 系统有大量相同的对象，造成内存浪费
- 对象带部分状态可以外部化
- 对象需要多次重复利用时，因为维护对象池需要耗费系统资源。

# 11.代理模式

结构型模式

## 定义

Proxy Pattern

给某一个对象提供一个代理或占位符，并由代理对象来控制对原对象的访问。

## 角色

- Subject抽象主题角色：声明了真实主题和代理主题的共同接口。
- Proxy代理主题角色：包含了对真实主题的引用，在实现Subject的方法时调用RealSubject内的方法
- RealSubject真实主题角色：定义了代理角色所代表的真实对象，实现了真实的业务操作。

## 实例代码

```c#
abstract class Subject{
    public abstract void request();
}
```

```c#
class RealSubject:Subject{
    public override void request(){
        //业务方法具体实现代码
    }
}
```

```c#
class Proxy:Subject{
    private RealSubject realSubject = new RealSubject();
    public void prerequset(){
        //some code
    }
    public override void request(){
        prerequest();
        realSubject.request();
        postrequest();
    }
    public void postrequest(){
        //some code
    }
}
```

## 变体

- 远程代理（Remote Proxy）：为一个位于不同地址空间的对象提供一个本地的代理对象，这个不同的地址空间可以在同一台主机中，也可以在另一台主机
- 虚拟代理（Virtual Proxy）：如果创建一个资源消耗大的对象，先创建一个消耗小的对象来表示，真实对象在需要时才被创建（懒加载）
- 保护代理（Protect Proxy）：控制对一个对象的访问，可以给不同的用户提供不同级别的使用权限
- 缓冲代理（Cache Proxy）：为某一个操作的结果提供临时存储空间，以便减少调用次数。
- 智能引用代理了（Smart Reference Proxy）：当一个对象被引用时，提供一些额外操作，例如将对象被调用次数记录下来

## 特点

优点

- 可以协调调用者与被调用者，在一定程度上降低了系统耦合度。
- 客户端可以根据抽象主题角色编程，增加和更换代理类无需修改代码，符合开闭原则，具有灵活性和可扩展性。

缺点

- 造成请求处理速度变慢
- 可能实现复杂

使用场景

- 访问远程主机可以用远程代理
- 需要用一个消耗资源少的对象来表示一个消耗资源较多的对象。
- 当需要为某一个被频繁访问的操作结果提供一个临时存储空间，来供多个客户端共享访问这些结果时可以使用缓冲代理。
- 需要控制对一个对象的访问，为不同用户提供不同级别的访问权限时可以使用保护代理
- 当需要为一个对象的访问，提供额外的操作。

# 12.职责链模式

行为型模式

## 定义

Chain of Responsibility Pattern

避免将一个请求的发送者与 接收者耦合在一起，让多个对象都有机会处理请求。将接收请求的对象连接成一条链，并且沿着这条链传递请求，直到有一个对象能处理它为止。

## 角色

- Handler 抽象处理者：定义了一个处理请求的接口，一般设计为抽象类。
- ConcreteHandler具体处理者：抽象处理者的子类，可以处理用户请求，在具体处理者类中实现了抽象处理者中定义的抽象请求处理方法，在处理请求之前需要判断是否有处理权限，如果可以就处理，否则将请求转发给后继者

## 实例代码

```c#
abstract class Handler{
    protected Handler successor;
    public void setSuccessor(Handler successor){
        this.successor = successor;
    }
    public abstract void HandleRequest(string request);
}
```

```c#
class ConcreteHandler:Handler{
    public override void HandleRequest(string request){
        if(满足条件){
            //处理请求
        }else{
            this.successor.HandleRequest(request);
        }
    }
}
```

```c#
Handler handler1,handler2,handler3;
handler1 = new ConcreteHandlerA();
handler2 = new ConcreteHandlerB();
handler3 = new ConcreteHandlerC();
handler1.setSuccessor(handler2);
handler2.setSuccessor(handler3);
handler1.HandleRequest("request");
```

##  变体

- 纯的责任链模式

  要求一个具体处理者对象只能在两个行为中选择一个，要么承担全部责任要么将责任推给下家，不允许出现偶一个具体处理者对象在承担了一部分或全部责任后又将责任向下传递的情况。

- 不纯的责任链模式

  允许某个请求被一个具体处理者部分处理后然后在向下传递

## 特点

优点

- 客户端无需知道是哪个对象处理请求，仅需要知道该请求会被处理即可。
- 请求处理对象仅需要维护一个指向其后继者的引用，不需要维持对所有对象的引用
- 在给对象分配职责时可以带来灵活性，运行时可以修改职责链
- 系统中增加新的具体请求处理者无需修改原有系统的代码，只需要在可以短重新建链即可，符合开闭原则。

缺点

- 不能保证请求一定会被处理
- 性能受到影响
- 链如果设计不当，可能导致循环调用。

使用场景

- 多个对象处理同一个请求，具体哪个对象处理需要到运行时确定，客户端只需要提交
- 在不明确制定接收者时向多个对象中的一个提交一个请求。
- 可动态指定一组对象处理请求，客户端可以动态的创建职责链处理请求，还可以改变链中的顺序。

# 13.命令模式

行为型模式

## 定义

Command Pattern

将一个请求封装为一个对象，从而让你可以用不同的请求对客户端进行参数化，对请求排队或者记录请求日志，以及支持撤销操作。

## 角色

- Command抽象命令类：一般是抽象类或者接口，声明了用于执行请求的Excute方法，通过这些方法可以调用请求接收者的相关操作

- ConcreteCommand具体命令类：抽象命令类的子类，实现了在抽象命令类中声明的方法。

- Invoker调用者：请求发送者，通过命令对象执行请求，一个调用者在设计时不需要指定接收者，只与抽象命令类之间存在关联关系
- Receiver接收者：执行请求相关的操作，具体实现对请求的业务处理。

## 实例代码

```c#
abstract class Command{
    public abstract void Execute();
}
```

````c#
class Invoker{
    private Command command;
    public Invoker(Command command){
        this.command = command;
    }
    public Command command{
        get{return command;}
        set{command = value;}
    }
    public void Call(){
        command.Execute();
    }
}
````

```c#
class ConcreteCommand:Command{
    private Receiver receiver;
    public override void Execute(){
        receiver.Action();
    }
}
```

```c#
class Receiver{
    public void Action(){
        //具体操作
    }
}
```

## 变体

- 命令队列

  ```c#
  class CommandQueue{
      private List<Command> command = new List<Command>();
      public void AddCommand(Command command){
          commands.add(command);
      }
      public void RemoveCommand(Command command){
          commands.remove(command);
      }
      public void Execute(){
          foreach(object cmd in commands){
              ((Command)command).Execute();
          }
      }
  }
  ```

  ```c#
  class Invoker{
      private CommandQueue queue;
      public Invoker(CommandQueue commandQueue){
          this.commandQueue = commandQueue;
      }
      public void SetCommandQueue(CommandQueue queue){
          this.commandQueue = commandQueue;
      }
      public void Call(){
          commandQueue.Execute();
      }
  }
  ```

- 记录日志

- 实现撤销操作

  ```c#
  abstract class AbstractCommand{
      public abstract int Execute(int value);
      public abstract int Undo();
  }
  ```

  ```c#
  class AddCommand:AbstractCommand{
  	private Adder add = new Adder();
      private int value;
      public override int Execute(int value){
          this.value = value;
          return adder.Add(value);
      }
      public override int Undo(){
          return add.Add(-value);
      }
  }
  ```

- 宏命令

  是一个具体命令类，他内部维护对其他命令的引用，当它的Execute方法被调用时会调用他内部其他命令的Execute方法

## 特点

优点

- 命令模式降低了系统的耦合度，由于请求者和接收者之间不存在直接引用，因此请求者与接收者之间完全解耦
- 通过使用命令模式可以很容易的添加新命令
- 使用命令模式可以比较容易的设计一个命令队列或宏命令
- 命令模式为请求的撤销和恢复提供一种设计和实现方案

缺点

- 导致系统产生过多的命令类

使用场景

- 系统需要请求调用者和请求接收者解耦
- 系统需要在不同的时间指定请求，将请求排队和执行请求。

# 14.解释器模式

行为型模式

## 定义

Interpreter Pattern

给定一个语言，定义他的文法的一种表示，并定义一个解释器，这个解释器使用该表示来解释语言中的句子

## 角色

- AbstractExpression抽象表达式：在抽象表达式中声明了抽象的解释操作，它是所有终结符表达式和非终结符表达式的公共父类
- TerminalExpression终结符表达式：抽象表达式的子类，实现了与文法中的终结符相关联的解释操作，在句子中的每一个终结符都是该类的实例
- NonterminalExpression非终结符表达式：抽象表达式的子类，实现了文法中非终结符的解释操作，非终结符表达式中可以包含终结符表达式也可以包含非终结符表达式，所以解释是递归执行的
- Context上下文环境：存储解释器之外的全局信息

## 实例代码

```c#
abstract class AbstractExpression{
    public abstract void Interpret(Context context);
}
```

```c#
class TerminalExpression:AbstractExpression{
    public override void Interpret(Context ctx){
        //解释操作
    }
}
```

```c#
class NonterminalExpression:AbstractExpression{
    private AbstractExpression left;//此处每一个句子都只有两个子节点，所以可以一个left一个right
    private AbstractEpxression right;
    public NonterminalExpression(AbstractExpression left,AbstractEpxression right){
        this.left = left;
        this.right = right;
    }
    public override void Interpret(Context context){
        
    }
}
```

```c#
class Context{
    private Hashtable ht = new Hashtable();
    public void Assign(string key , string value){
        ht.Add(key,value);
    }
    public string lookup(string key){
        return (string)ht[key];
    }
}
```

## 特点

优点

- 易于改变和扩展文法，通过继承等机制改变和扩展文法
- 每一条文法都可以表示为一个类，方便的实现一个简单的语言
- 实现文法较为容易
- 增加新的解释表达式较为方便，即新增加终结符和非终结符代表的类。

缺点

- 对于复杂文法难以维护，类的个数会急剧增加，导致系统难以维护和管理。

- 执行效率低，存在大量递归和循环调用。

使用场景

- 可以将一个需要解释执行的语言中的句子表示为一个抽象语法树，
- 一些重复出现的问题可以用一种简单的语言表达
- 文法较为简单
- 执行效率不是关键问题

# 15.迭代器模式

行为型模式

## 定义

Iterator Pattern

提供一种方法顺序访问一个聚合对象中的各个元素，且不用暴露该对象的内部表示

## 角色

- AbstractIterator抽象迭代器：定义了访问和便利元素的接口
- ConcreteIterator具体迭代器：实现了抽象迭代器接口，完成了对聚合对象的遍历
- Aggregate抽象聚合类：用于存储和管理元素对象，声明一个CreateIterator()方法用于创建一个迭代器对象，充当抽象迭代器工厂角色。
- ConcreteAggregate具体聚合类：是抽象聚合类的子类，实现了在抽象聚合类中声明的CreateIterator方法

## 实例代码

```c#
interface Iterator{
    void First();
    void Next();
    bool HasNext();
    object CurrentItem();
}
```

```c#
class ConcreteIterator:Iterator{
    private ConreteAggregate objects;
    private int cursor;
    public ConcreteIterator(ConcreteAggregate objects){
        this.objects = objects;
    }
    public void First(){
        //implement code;
    }
    public void Next(){
        
    }
    public bool HasNext(){
        
    }
    public object CurrentItem(){
        
    }
}
```

```c#
interface Aggregate{
	Iterator CreateIterator();
}
```

```c#
class ConcreteAggregate:Aggregate{
	public Iterator CreateIterator(){
        return new ConcreteIterator(this);
    }
}
```

## 特点

优点

- 支持不同方式遍历一个聚合对象，在同一个聚合对象上可以定义多种遍历方式

- 简化了聚合类，不需要让聚合类提供遍历逻辑
- 引入了抽象层，增加新的迭代器和聚合类方便，符合开闭原则。

缺点

- 将存储数据和遍历数据职责分离，在增加新的聚合类时需要对应增加迭代器类，使类个数增加
- 设计难度大，要充分考虑将来的扩展

使用场景

- 访问一个聚合对象的内容，无须暴露其内部细节
- 需要为一个聚合对象提供多种遍历方式
- 为遍历不同的聚合结构提供一个统一的接口，在该接口的实现类中为不同的聚合结构提供不同的遍历方式。

# 16.中介者模式

行为型模式

## 定义

Mediator Pattern

定义一个对象来封装一系列对象的交互，中介者模式使各个对象之间不需要显式的相互引用，从而使其耦合松散，而且让你可以独立的改变它们之间的交互。

## 角色

- Mediator抽象中介者：它定义了一个接口，该接口用于与各个同事对象之间进行通信
- ConcreteMediator具体中介者：它是中介者的子类，并声明了一些抽象方法供子类实现，维持了一个对抽象中介者类的引用。

- Colleague抽象同事类：定义了各个同事类的公有方法，声明了一些抽象方法用于子类实现，维护了一个对抽象中介者的引用
- ConcreteColleague具体同事类：抽象同事类的子类，每一个同事对象在需要和其他同事对象通信时

## 实例代码

```c#
abstract class Mediator{
    protected List<Colleague> colleague = new List<Colleague>();
    public void Register(Colleague colleague){
        colleague.Add(colleague);
    }
    public abstract void Operation();
}
```

```c#
class ConcreteMediator:Mediator{
    public override void Opeartion(){
        ((Colleague)(colleagues[0])).Method1();
    }
}
```

```c#
abstract class Colleague{
    protected Mediator mediator;
    public Colleague(Mediator mediator){
        this.mediator = mediator;
    }
    public abstract void Method1();
    public void Method2(){
        mediator.Operation();
    }
}
```

```c#
class ConcreteColleague:Colleague{
    public ConcreteColleague(Mediator mediator):base(mediator){
        
    }
    public override void Method1(){
        
    }
}
```

## 特点

等手段使受害人陷于恐惧并保持沉默优点

- 简化了对象之间的交互
- 将各个同事对象解耦
- 减少子类生成

缺点

- 中介者中包含大量的同事之间的交互细节，使得终结者复杂难以维护

使用场景

- 系统中对象之间存在复杂的引用关系，系统结构混乱难以理解
- 一个对象由于引用了其他很多对象并且直接和这些对象通信导致难以复用该对象
- 用户想通过一个中间类来封装多个类中的行为，又不想生成太多的子类。

# 17.备忘录模式

## 定义

Memento Pattern

在不破坏封装的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，这样就可以在以后将对象回复到原先保存的状态。

## 角色

- Originator原发器：是一个普通类，创建一个备忘录来存储当前内部状态，也可以使用备忘录来恢复内部状态
- Memento备忘录：用于存储原发器内部状态，根据原发器来决定保存哪些内部属性。
- CareTaker负责人：负责保存备忘录，但是不能对备忘录的内容进行操作。

## 实例代码

```c#
public class Originator{
    private string state;
    public Originator(string state){
        this.state = state;
    }
    internal Memento CreateMemento(){
        return new Memento(this);
    }
    internal void RestoreMemento(Memento m){
        state = m.GetState();
    }
    public void SetState(string state){
        this.state = state;
    }
    public string GetState(){
        return this.state;
    }
}
```

```c#
internal class Memento{
    private string state;
    internal Memento(Originator o){
        state = o.GetState();
    }
    internal void SetState(string state){
        this.state = state;
    }
    internal string GetState(){
        return this.state;
    }
}
```

```C#
public class Caretaker{
    private Memento memento;
    internal Memento GetMemento(){
        return memento;
    }
    internal void SaveMemento(Memento memento){
        this.memento = memento;
    }
}
```



# 18.观察者模式

行为型模式

## 定义

Observer Patterrn

定义对象之间的一种多对多依赖关系，使得每当一个对象状态发生改变时，其相关依赖对象都得到同志并被自动通知更新。

## 角色

- Subject 目标：被观察的对象

- ConcreteSubject具体目标：是目标类的子类，通常包含经常发生改变的数据，当状态发生改变时，向各个观察着发出通知。

- Observer观察者：将对观察目标的改变做出反应，一般定义为接口，该接口声明了更新数据的方法Update,因此又称为抽象观察者
- ConcreteObserver具体观察者：维护一个只想具体目标对象的引用，存储具体观察者的有关状态，这些状态需要和具体目标的状态保持一致。

## 实例代码

```c#
interface Observer{
    void Update();
}
```

```c#
class ConcreteObserver:Observer{
	public void update(){
        
    }
}
```

## 特点

优点

- 可以实现表示层和数据逻辑层分离，定义了稳定的消息更新传递机制，并抽象了更新接口，使得可以有各种各样不同的表示层充当具体观察着
- 在观察目标和观察着之间建立一个抽象的耦合，观察目标只需要维护一个抽象观察着的集合，无需了解其具体观察者。

- 观察着支持广播通信，观察目标会向所有已注册的观察者对象发送通知，简化一对多设计难度
- 观察着模式符合开闭原则。

缺点

- 如果一个观察着目标对象有很多直接和间接观察者，将所有的观察者都会通知到会花费很多时间。
- 如果在观察着和观察目标之间存在循环依赖，观察目标会触发他们进行循环调用，导致系统崩溃
- 观察者模式没有相应的机制，让观察者知道所观察的对象是怎么发生变化的。

使用场景

- 如果一个抽象模型有两方面，其中一方面依赖于另一方面，这两方面封装在独立的对象中使他们可以各自独立的改变和复用。
- 一个对象改变将导致其他一个或多个对象发生变化，具体不知道有多少个
- 需要在系统中创建一个触发链，A对象的行为将影响B对象，B对象的行为将影响C对象。



# 19.状态模式

行为型模式

## 定义

State Pattern

允许一个对象在其内部状态改变时改变它的行为，对象看起来似乎修改了它的类

## 角色

- Context上下文：拥有多种状态的对象，
- State抽象状态类：定义一个接口以封装与状态类的一个特定状态相关的行为。
- ConcreteState具体状态类：是抽象状态类的子类，每一个具体状态类实现一个与环境类的一个状态相关的行为，对应环境类的一个具体状态，不同的具体状态类其行为有所不同。

## 实例代码

```c#
abstract class State{
    public abstract void Handle();
}
```

```c#
class ConcreteState:State{
    public override void Handle(){
        //concrete code
    }
}
```

```c#
class Context{
    private State state;
    private int value;
    public void SetState(State state){
        this.state = state;
    }
    public void Request(){
        state.Handle();
    }
    public void ChangeState(){
        if(value == 0){
            this.SetState(new ConcreteStateA());
        }else{
            this.SetState(new ConcreteStateB());
        }
    }
    public void ChangeState(Context ctx){
        if(ctx.value == 1){
            ctx.SetState(new ConcreteStateB());
        }else if(ctx.value==2){
            ctx.SetState(new ConcreteStateC());
        }
    }
}
```

## 特点

优点 

- 封装了状态转换规则，在状态模式中可以将状态的转换代码封装在Context类中
- 状态模式将所有与某个状态有关的行为放到一个类中只需要注入一个不同的状态对象即可使环境对象拥有不同的行为
- 状态模式允许状态转换逻辑与状态对象合成一体，而不是提供一个巨大的条件语句块。
- 状态模式可以让多个环境对象共享一个状态对象，从而减少对象个数

缺点

- 增加系统中类和对象的个数，导致系统运行开销增大
- 结构和实现复杂
- 开闭原则支持差。

使用场景

- 对象行为依赖它的状态
- 代码中包含大量的与对象状态相关的条件语句。

# 20.策略模式

行为型模式

## 定义

Strategy Pattern

定义一系列算法，将每一个算法封装起来，并让他们可以相互替换，策略模式让算法可以独立于使用它的客户变化。

## 角色

- Context上下文：使用算法的角色，解决某个问题时可以采用多种策略
- Strategy抽象策略类：为所支持的算法声明了抽象方法，是所有策略类的父类。
- ConcreteStrategy具体策略类：实现了在抽象策略类中声明的算法。

## 实例代码

```c#
abstract class AbstractStrategy{
    public abstract void Algorithm();
}
```

```c#
class ConcreteStrategyA:AbstractStrategy{
    public override void Algorithm(){
        //算法A
    }
}
```

```C#
class Context{
    private AbstractStrategy strategy;
    public void SetStrategy(AbstractStrategy strategy){
        this.strategy = strategy;
    }
    public void Algorithm(){
        strategy.Algorithm();
    }
}
```

## 特点

优点

- 提供了对开闭原则的完美支持
- 提供了管理相关算法族的办法
- 提供了一种可替换继承关系的办法
- 可以避免多重条件选择语句
- 提供了一种算法的复用机制

缺点

- 客户端必须知道所有的策略类
- 策略模式将造成系统产生很多具体策略类
- 无法同时在客户端使用多个策略类

使用场景

- 一个系统需要动态地在几种算法中选择一种
- 一个对象有很多行为，如果使用恰当的模式，这些行为只能使用多重条件判断语句
- 不希望客户端知道复杂的与算法相关的数据结构。

# 21.模板方法模式

行为型模式

## 定义

Template Method Pattern

定义一个操作中算法的框架，而将一些步骤延时到子类中，模板方法模式使得子类不改变一个算法的结构即可重定义该算法的某些特定步骤

## 角色

- AbstractClass 抽象类：抽象类中定义了一系列基本操作，这些基本操作可以是具体的也可以是抽象的。每个基本操作对应算法的一个步骤，在其子类中可以重定义或实现这些步骤

- ConcreteClass具体子类：它是抽象类的子类，用于实现父类中声明的抽象基本操作，以完成子类特定算法的步骤，也可以覆盖在父类中已经实现的具体基本操作。

## 实例代码

实现模板方法模式时一般是开发抽象类的软件设计师和开发具体子类的软件设计师之间协作，一个设计师负责给出一个算法轮廓和框架，另一个设计师负责给出这个算法的各个逻辑步骤，实现这些具体逻辑步骤的方法即基本方法，而将这些基本方法汇总起来的方法称为模板方法。

（简单来讲就是一个对外暴露的方法内部会调用私有的几个基本方法来完成功能，不过期间会配合abstract方法和钩子函数）

# 22.访问者模式

