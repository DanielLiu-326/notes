# 语法

## 选择器

### 元素选择器

```js
$("p")//选择所有的段落元素
$(document).ready(function(){
  $("button").click(function(){
    $("p").hide();
  });
});
```

### id 选择器

```js
$("#test")
$(document).ready(function(){
  $("button").click(function(){
    $("#test").hide();
  });
});
```

### class 选择器

```js
$(".test")
$(document).ready(function(){
  $("button").click(function(){
    $(".test").hide();
  });
});
```

### 其他选择器

| 语法                     | 描述                                                      |
| :----------------------- | :-------------------------------------------------------- |
| $("*")                   | 选取所有元素                                              |
| $(this)                  | 选取当前 HTML 元素                                        |
| $("p.intro")             | 选取 class 为 intro 的 \<p\> 元素                         |
| $("p:first")             | 选取第一个 \<p> 元素                                      |
| $("ul li:first")         | 选取第一个 \<ul> 元素的第一个 \<li> 元素                  |
| $("ul li:first-child")   | 选取每个 \<ul> 元素的第一个 \<li> 元素                    |
| $("[href]")              | 选取带有 href 属性的元素                                  |
| $("a[target='_blank']")  | 选取所有 target 属性值等于 "_blank" 的 \<a> 元素          |
| $("a[target!='_blank']") | 选取所有 target 属性值不等于 "_blank" 的 \<a> 元素        |
| $(":button")             | 选取所有 type="button" 的 \<input> 元素 和 \<button> 元素 |
| $("tr:even")             | 选取偶数位置的 \<tr> 元素                                 |
| $("tr:odd")              | 选取奇数位置的 \<tr> 元素                                 |

## 事件

### 常见事件

| 鼠标事件                                                     | 键盘事件                                                     | 表单事件                                                  | 文档/窗口事件                                             |
| :----------------------------------------------------------- | :----------------------------------------------------------- | :-------------------------------------------------------- | :-------------------------------------------------------- |
| [click](https://www.runoob.com/jquery/event-click.html)      | [keypress](https://www.runoob.com/jquery/event-keypress.html) | [submit](https://www.runoob.com/jquery/event-submit.html) | [load](https://www.runoob.com/jquery/event-load.html)     |
| [dblclick](https://www.runoob.com/jquery/event-dblclick.html) | [keydown](https://www.runoob.com/jquery/event-keydown.html)  | [change](https://www.runoob.com/jquery/event-change.html) | [resize](https://www.runoob.com/jquery/event-resize.html) |
| [mouseenter](https://www.runoob.com/jquery/event-mouseenter.html) | [keyup](https://www.runoob.com/jquery/event-keyup.html)      | [focus](https://www.runoob.com/jquery/event-focus.html)   | [scroll](https://www.runoob.com/jquery/event-scroll.html) |
| [mouseleave](https://www.runoob.com/jquery/event-mouseleave.html) |                                                              | [blur](https://www.runoob.com/jquery/event-blur.html)     | [unload](https://www.runoob.com/jquery/event-unload.html) |
| [hover](https://www.runoob.com/jquery/event-hover.html)      |                                                              |                                                           |                                                           |

### 事件注册

语法：

```js
$("p").click(function(){
    // 动作触发后执行的代码!!
});
```

### 常用事件

#### click()

单击

```js
$("p").click(function(){
  $(this).hide();
});
```

#### dblclick()

双击

```
$("p").dblclick(function(){
  $(this).hide();
});
```

#### mouseenter()

鼠标进入

```js
$("#p1").mouseenter(function(){
    alert('您的鼠标移到了 id="p1" 的元素上!');
});
```

#### mouseleave()

鼠标移出

```js
$("#p1").mouseleave(function(){
    alert("再见，您的鼠标离开了该段落。");
});
```

#### mousedown()

鼠标按下

```js
$("#p1").mousedown(function(){
    alert("鼠标在该段落上按下！");
});
```

#### mouseup()

鼠标弹起

```js
$("#p1").mouseup(function(){
    alert("鼠标在段落上松开。");
});
```

#### hover()

相当于鼠标进入和鼠标移出两个事件，所以传入两个函数

```js
$("#p1").hover(
    function(){
        alert("你进入了 p1!");
    },
    function(){
        alert("拜拜! 现在你离开了 p1!");
    }
);
```

#### focus()

获得焦点

```js
$("input").focus(function(){
  $(this).css("background-color","#cccccc");
});
```

#### blur()

失去焦点

```js
$("input").blur(function(){
  $(this).css("background-color","#ffffff");
});
```

### jQuery 链式调用

```js
$("#p1").css("color","red").slideUp(2000).slideDown(2000);
```

```js
$("#p1").css("color","red")
  .slideUp(2000)
  .slideDown(2000);
```



## jQuery 效果

### hide()/show()/toggle()

#### 语法

$(*selector*).hide(*speed,animate,callback*);

 $(*selector*).show(*speed,*,[*animate*],*callback*);

$(*selector*).toggle(*speed,*,[*animate*],*callback*);

speed：fast|slow|毫秒

callback : callback

animate: 

#### 实例

```js
$("#hide").click(function(){
  $("p").hide();
});
$("#show").click(function(){
  $("p").show();
});
```

切换hide()或show()

```js
$("button").click(function(){
  $("p").toggle();
});
```

### 淡入淡出

#### fadeIn()

语法：$(*selector*).fadeIn(*speed,callback*);

```js
$("button").click(function(){
  $("#div1").fadeIn();
  $("#div2").fadeIn("slow");
  $("#div3").fadeIn(3000);
});
```

#### fadeOut()

语法：$(*selector*).fadeOut(*speed,callback*);

```js
$("button").click(function(){
  $("#div1").fadeOut();
  $("#div2").fadeOut("slow");
  $("#div3").fadeOut(3000);
});
```

#### fadeToggle()

语法：$(*selector*).fadeToggle(*speed,callback*);

```js
$("button").click(function(){
  $("#div1").fadeToggle();
  $("#div2").fadeToggle("slow");
  $("#div3").fadeToggle(3000);
});
```

#### fadeTo()

语法：$(*selector*).fadeTo(*speed,opacity,callback*);

```js
$("button").click(function(){
  $("#div1").fadeTo("slow",0.15);
  $("#div2").fadeTo("slow",0.4);
  $("#div3").fadeTo("slow",0.7);
});
```

### 滑入滑出

#### slideDown()

语法：$(*selector*).slideDown(*speed,callback*);

```js
$("#flip").click(function(){
  $("#panel").slideDown();
});
```

#### slideUp()

语法:$(*selector*).slideUp(*speed,callback*);

```js
$("#flip").click(function(){
  $("#panel").slideUp();
});
```

#### slideToggle()

语法：$(*selector*).slideToggle(*speed,callback*);

```js
$("#flip").click(function(){
  $("#panel").slideToggle();
});
```

### 自定义动画

#### 绝对值

语法：$(*selector*).animate({*params*}*,speed,callback*);

```js
$("button").click(function(){
  $("div").animate({left:'250px'});//让left缓缓变成250像素
});
```

```js
$("button").click(function(){
  $("div").animate({
    left:'250px',//让left缓慢变成250px
    opacity:'0.5',//缓慢变成0.5
    height:'150px',//缓慢变成150px
    width:'150px'//缓慢变成150px
  });
});
以上四个动画是叠加起来作用于div的
```

#### 相对值

```js
$("button").click(function(){
  $("div").animate({
    left:'250px',
    height:'+=150px',
    width:'+=150px'
  });
});
```

### 停止动画

语法：$(selector).stop([stopAll],[goToEnd]);

注意: 由于动画是一个过程而不是一瞬间完成的，浏览器内部维护一个动画队列，停止动画可以停止当前的 紧接着执行下一个动画，也可以停止所有的

stopAll :结束当前动画并清空动画队列 取值：true|false  默认：false

goToEnd:是否立即完成当前动画 取值: true|false 默认false。

```js
$("#stop").click(function(){
  $("#panel").stop();
});
```

### 动画的回调函数

许多 jQuery 函数涉及动画。这些函数也许会将 *speed* 或 *duration* 作为可选参数。

*speed* 或 *duration* 参数可以设置许多不同的值: "slow", "fast", "normal" 或毫秒。

```js
$("button").click(function(){
  $("p").hide(1000);
  alert("段落现在被隐藏了");
});
```

## HTML操作

### 获取

#### text()

设置或返回所选元素的文本内容

```js
$("#btn1").click(function(){
  alert("Text: " + $("#test").text());
});

```

#### html()

设置或返回所选元素的内容（包括 HTML 标记）

```js
$("#btn2").click(function(){
  alert("HTML: " + $("#test").html());
});
```

#### val()

设置或返回表单字段的值

```js
$("#btn1").click(function(){
  alert("值为: " + $("#test").val());
});
```

#### attr()

获取属性值。

```js
$("button").click(function(){
  alert($("#runoob").attr("href"));
});
```

### 设置

#### text(set)/html(set)/val(set)

同上

```

$("#btn1").click(function(){
    $("#test1").text("Hello world!");
});
$("#btn2").click(function(){
    $("#test2").html("<b>Hello world!</b>");
});
$("#btn3").click(function(){
    $("#test3").val("RUNOOB");
});

```

#### attr({key1:value1,key2:value2....})

语法：$(*selector*).attr({*params*}*,callback*);

params:{key1:value1,key2:value2....}

callback:function(i,origValue){...}其中i表示被选元素列表中当前元素的下标,origValue表示旧值

```js
$("button").click(function(){
  $("#runoob").attr("href", function(i,origValue){
    return origValue + "/jquery"; 
  });
});
```

### 添加

#### append()/prepend()

语法：$(选择器).append(文本1,文本2.......);

prepend() 方法在被选元素的结尾/开头插入内容。

```js
$("p").append("追加文本");
```

```js
$("p").prepend("在开头追加文本");
```

#### after()/before()

语法：$(选择器).after(元素1,文本2,......);

```js

function afterText()
{
    var txt1="<b>I </b>";                    // 使用 HTML 创建元素
    var txt2=$("<i></i>").text("love ");     // 使用 jQuery 创建元素
    var txt3=document.createElement("big");  // 使用 DOM 创建元素
    txt3.innerHTML="jQuery!";
    $("img").after(txt1,txt2,txt3);          // 在图片后添加文本
}
```

### 删除

#### remove()

删除被选元素及其子元素。

```js
$("#div1").remove();
```

##### 选择性删除

```js
$("p").remove(".italic");
```

注意：

**$(selector)** 语法的返回结果是一个元素的列表，即：将 **$("#div1")** 看作一个列表，**remove()** 中的筛选条件实际上是对这个列表中的元素进行筛选删除，而不会去删除这个列表中不存在的元素（子元素不在这个列表中）。

#### empty()

删除被选元素的所有子元素

```js
$("#div1").empty();
```

### CSS操作

#### css类操作

##### addClass()

向被选元素添加一个或多个类(相当于class="类名1 类名2")

```js
$("button").click(function(){
  $("body div:first").addClass("important blue");
});
```

##### removeClass()

删除指定的 class 属性

```js
$("button").click(function(){
  $("h1,h2,p").removeClass("blue");
});
```

##### toggleClass

进行添加/删除类的切换操作

```js
$("button").click(function(){
  $("h1,h2,p").toggleClass("blue");
});
```

#### css属性操作

##### css("*propertyname*");

返回属性

```js
alert($("p").css("background-color"));
```

##### css("*propertyname*","*value*");

设置propertyname值为value

```js
$("p").css("background-color","yellow");
```

##### css({"*propertyname*":"*value*","*propertyname*":"*value*",...});

设置多个 CSS 属性

```js
$("p").css({"background-color":"yellow","font-size":"200%"});
```

#### 设置尺寸

- width()

- height()

- innerWidth()

- innerHeight()

- outerWidth()

- outerHeight()

  <img src="https://www.runoob.com/images/img_jquerydim.gif" alt="https://www.runoob.com/images/img_jquerydim.gif">

## 遍历dom树

#### 祖先

- parent() // 直接父元素
- parents([过滤])// 所有[*符合过滤的*]祖先元素
- parentsUntil()//介于两个给定元素之间的所有祖先元素

#### 后代

- children()//所有子元素
- find([过滤])//返回被选元素后代元素中符合条件的，一路向下直到最后一个后代。传入"*"不进行过滤

```js
$(document).ready(function(){
  $("div").children();
});
```

下面的例子返回类名为 "1" 的所有 \<p> 元素，并且它们是 \<div> 的直接子元素：

```js
$(document).ready(function(){
  $("div").children("p.1");
});
```

#### 同胞

- siblings()//所有同胞元素。
- next()//被选元素的下一个同胞元素
- nextAll()//当前元素下一个元素  ，下下个元素，下下下个元素........
- nextUntil()//返回给定两个元素之间的所有元素
- prev()//返回当前的前一个元素
- prevAll()//当前的前一个  ，前前一个，前前前个元素......
- prevUntil()//同nextUnil();但方向相

```js
$(document).ready(function()
  $("div p").first();
});
```

#### 过滤

##### first()

被选元素的首个元素。

```js
$(document).ready(function(){
  $("div p").first();//第一个 <div> 元素内部的第一个 <p> 元素：
});
```

##### last()

返回被选元素的最后一个元素。

```
$(document).ready(function(){
  $("div p").last();
});
```

##### eq()

被选元素中带有指定索引号的元素

注意：索引号从 0 开始，因此首个元素的索引号是 0 而不是 1。

```js
$(document).ready(function(){
  $("p").eq(1);
});
```

##### filter()

规定一个标准。不匹配这个标准的元素会被从集合中删除，匹配的元素会被返回。

```js
$(document).ready(function(){
  $("p").filter(".url");//返回带有类名 "url" 的所有 <p> 元素：
});
```

##### not()

同filter 但是是返回不匹配标准的所有元素。

```js
$(document).ready(function(){
  $("p").not(".url");
});
```

## Ajax

是一种向后端发送请求并接收结果的过程

### load 方法

使用HTTP get方法从服务器加载数据，并把返回的数据放入被选元素中。

语法：$(selector).load(URL,data,callback);

其中：

URL:请求的路径(不包括查询串)

data:查询串键值对

callback:load方法完成后回调其定义是function(responseTxt,statusTxt,xhr){)

```
	responseTxt - 包含调用成功时的结果内容
    statusTXT - 包含调用的状态
    xhr - 包含 XMLHttpRequest 对象
```

```js
$("#div1").load("/try/ajax/demo_test.txt",function(responseTxt,statusTxt,xhr){
      if(statusTxt=="success")
        alert("外部内容加载成功!");
      if(statusTxt=="error")
        alert("Error: "+xhr.status+": "+xhr.statusText);
});
```

### GET/POST方法

一种默认不会对dom树修改的发出请求的方法

#### GET

$.get(*URL*,*callback*);

URL：包含查询串

callback: function(data,status){...}

```js
$("button").click(function(){
  $.get("demo_test.php",function(data,status){
    alert("数据: " + data + "\n状态: " + status);
  });
});
```

#### POST

$.post(*URL,data,callback*);

URL:地址

data:post的数据

callback:function(data,status){...}

```js
$("button").click(function(){
    $.post("register",
    {
        name:"danny",
        email:"test@example.com"
    },
    function(data,status){
        alert("数据: \n" + data + "\n状态: " + status);
    });
});
```

# 附录

.....TODO:官方参考手册

