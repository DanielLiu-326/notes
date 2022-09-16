# Android 项目目录结构

- build：放置编译后的文件
  - outputs /apk/debug/apks.apk放置构件好的apk文件
- libs：jar包放置地点

- src：代码源文件
  - androidTest:测试类
  - main代码
    - java：源代码
  - res：资源文件
    - drawable ：图片
    - layout：布局
    - mipmap：应用图标不同分辨率
    - values：常量值
  - AndroidManifest.xml：清单文件，四大组件注册
  - test：单元测试文件

# Activity

安卓中描述大小的单位有：

- px：pixels像素，实际大小随像素密度改变而改变，但占用的物理像素数固定
- pt：point，长度单位，1pt = 1/72英寸，用于印刷行业，实际大小固定，像素数随像素密度改变
- dp：dip,device independent pixels设备独立像素，dp = px*(dpi/160)，px = dp/(dpi/160)
- sp：scale pixels，它是安卓的字体单位，安卓字体有小，正常，大超大等，当字体为正常时，1sp = 1dp，当字体为大或超大时，1sp>1dp

# 安卓控件

layout

```xml
<LinearLayout android:layout_width>
</LinearLayout>
```



## TextView

```xml
<TextView
          android:text="hello world"
/>
```



### 基础属性

- layout_width:组件宽度
  - match_parent：宽度和父容器大小相同
  - wrap_content：根据内容自动分配内容，但小于父容器
  - 123dp：大小（设备独立像素，不依赖实际像素）
- layout_hight:组件高度（同width）
- id:为控件设置一个id
  - 值:@+id/foo，方便Java代码通过findViewById获取到此控件
- text：设置显示的文本内容
- textColor：文本颜色
  - #00 00 00 00每两位代表一个值，不透明度+RGB
- textStyle：文本风格，normal（无），bold（加粗），italic（斜体）
- textSize：字体大小
  - 20sp 像素无关字体大小

- background：控件的背景颜色（可以是图片）
  - #ff ff 00 00：（红色ORGB）

- gravity：控件内容对齐方向，TextView中是文字imageView是图片
  - center，bottom，left，right，center_vertical（垂直居中），center_horizontal（水平居中）

常量值可以定义在values文件夹下的colors.xml , strings.xml , themes.xml下

定义方式：

- strings.xml

  ```xml
  <resouces>
      <string name="app_name">MyApp</string>
      <string name = "tv_one">test</string>
  </resouces>
  ```


- colors.xml

  ```xml
  <resource>
      <color name="black">#FFFFFFFF</color>
  </resource>
  ```
  

引用方式：

@string/tv_one

@color/black

### 阴影

layout.xml配置标签

- android:shadowColor:设置阴影颜色
- android:shadowRadius设置阴影大小,默认= 0
- android:shadowDx：设置阴影在水平方向偏移，即：水平方向阴影开始的位置
- android：shadowDy：设置阴影在竖直方向偏移，即：竖直方向阴影开始的位置

例如：

color 红色，radius为3.0的效果：

![shadow](/home/danny/Documents/notes/android_dev/textview_shadow.png)

### 跑马灯

- android:singleLine内容单行显示

- android:focusable:是否可以获取焦点

- android:focusableInTouchMode:用于控制视图在触摸模式下是否可以聚焦

- android:ellipsize：由于文本框大小不够容纳文本而省略文本的方式
  - marquee：跑马灯start：开市处有省略
  - end：结束处有省略
  - start：开始处有省略
  - middle：中间有省略

- android:marqueeRepeatLimit：字幕动画重复次数

注意：

需要设置focusable为true，focusableInTouchMode为true，且textView焦点为true

如何获取焦点：

- 1.当textview的android:clickable设置为true时，点击textview就会让textview获取到焦点

- 2.继承Textview，重写其isFocused，返回true

- 3.在TextView开始结束标签之间添加\<requestFocus/\>标签

## Button

Button继承TextView，textview的所有属性Button都有

```xml
<Button
        android:layout_width=""
        android:layout_hight=""
/>
```

### StateListDrawable

是drawable资源的一种，可以根据控件的不同状态，设置不同图片或颜色，根节点\<selector\>，只需将Button

drawable/btn_selector.xml

```xml
<selector>
          <!--设置当按钮被按下时的item-->
    <item android:drawable = "@drawable/ic_launcher_background" android:state_pressed="true"/>
        <!--设置按钮的默认状态-->
    <item android:drawable="...">
</selector>
```

引用该Drawable资源

```xml
android:background="@drawable/...."
```

### 事件

#### 点击

```java
Button btn = findVIewById(R.id.btn);
btn.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v){
        Log.e(TAG,"onClick");
    }
});
```

#### 长按，触摸

同点击，但Listener继承的是View.OnLongClickListener或View.OnTouchListener

对于触摸，onTouch成员函数的event参数有几种Action类型，可以通过getAction函数获取，如

- ACTION_DOWN
- ACTION_UP
- ACTION_MOVE

onTouch和onLongClick事件处理函数如果返回true标明当前事件被该函数消费，不会再触发其他事件

### 通过xml指定事件处理函数

```xml
<!--指定activity的类成员函数作为事件处理函数-->
android:onClick="函数名"
```

## EditText

EditText继承于TextView，所以TextView有的EditText也有

### 属性

- android:hint：输入提示
- android:textColorHint：输入提示的颜色
- android:InputType：输入类型如设置phone后只能输入数字等
- android:drawableXxx：Xxx为Left，Right，指定输入框左边或右边的图标
- android:drawablePadding：输入文字和图标之间的间距
- android:paddingXxx：内容与边框的间距(paddingLeft,paddingRight.....)
- android:background：背景色



## ImageView

### 属性

- android:src：设置图片资源
- android:scaleType：设置图片缩放类型
- android:maxHeight：最大高度
- android:maxwidth：最大宽度
- android:adjustViewBounds：调整view的边界



### 图片拉伸类型（ScaleType）

- fitStart：保持宽高比缩放图片，直到较长的边与Image边长相等
- fitCenter：默认值，同上缩放后放置于中间
- fitEnd：同上，缩放后放在右下角
- fitXY：对图像的横纵方向独立缩放，使图片完全充满ImageView，会更改图片的宽高比
- center：保持原图片的大小，显示在ImageView中间，当原图size大于ImageView的Size，超出部分剪裁处理
- centerCrop：保持宽高比缩放图片，直到完全覆盖ImageView，较长的一边被裁剪
- centerInside：保持宽高比缩放图片，直到ImageView能够完全显示图片
- matrix：不改变原图的大小，从ImageView的左上角开始绘制图片，原图超过ImageView的部分被裁剪。

## ProgressBar

### 属性

- android:max ：进度条的最大值
- android:progress：进度条已完成的进度之
- android:indeterminate：如果设置成true，则进度条不精确显示进度
- style="?android:attr/progressBarStyleHorizontal"水平进度条



## Notification



### 创建NotificationManager

NotificationManager是一个通知管理器，是系统维护的服务，以单例模式获得，所以一般不实例化该对象，在activity中，可以使用Activity.getSystemService(String)获取NotificationManager对象，Activity.getSystemService(String)fa方法可以通过android系统级服务的句柄，返回对应的对象，在这里需要返回NotificationManager，所以直接传递Context.NOTIFICATION_SREVICE即可

```java
NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
```

### 使用Builder创建Notification对象

使用NotificationCompat类的Builder构造器来创建Notification对象，可以保证程序在所有的版本上都能正常工作，android8.0新增了通知渠道概念，如果没有设置，则通知无法在android8.0的机器上显示。

Builder选项，带*为必须的

- setContentTitle(String)：标题* 
- setContentText(String)：文本内容*
- setSmallIcon(String)：小图标，小图标必须只使用Alpha图层*
- setLargeIcon(Bitmap)：通知大图标
- setColor(int)：设置小图标的颜色
- setContentIntent(PendingIntent)：设置通知后的跳转意图
- setAutoCancel(Boolean)：设置点击通知后自动清除通知
- setWhen(long)：设置通知被创建的时间

```java
if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.0){
    //版本超过android8.0需要创建channel
    NotificationChannel channel = 
        new NotificationChannel(id:"foo",name:"bar",NotificationManager.IMPORTANCE_HIGH);
	manager.createNotificationChannel(channel);
}
Notification notification = new NotificationCompact.Builder(context:this,channelid:"foo")
    .setContentTitle("通知标题")
    .setContentText("通知内容")
    .setSmallIcon(图标资源)//但图片不能带颜色
    .build();
```

#### 通知重要程度

| 常量               | 是否开启 | 状态栏 | 提示音 | 弹出 |
| ------------------ | -------- | ------ | ------ | ---- |
| IMPORTANCE_NONE    |          |        |        |      |
| IMPORTANCE_MIN     | √        |        |        |      |
| IMPORTANCE_LOW     | √        | √      |        |      |
| IMPORTANCE_DEFAULT | √        | √      | √      |      |
| IMPORTANCE_HIGH    | √        | √      | √      | √    |

### 触发通知

```java
manager.notify(whatever int,notification);
```

### 触发意图

```java
Intent intent = new Intent(this,Activity.class);
PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent);
notification.setContentIntent(pendingIntent);
```

## ToolBar

### 属性

- android:layout_width="match_parent"：宽度
- android:layout_height="?attr/actionBarSize"：高度
- android:background="#ffff00"：背景颜色
- app:navigationIcon="@drawable/ic_baseline_arrow_back_24"：最左边的图标
- app:title="主标题"：标题
- app:titleTextColor="#ff0000"：标题颜色
- app:titleMarginStart="90dp"：标题大小
- app:subtitile="子标题"：子标题大小
- app:subtitleTextColor="#00ffff"：子标题颜色
- app:logo="@mipmap/ic_launcher"：logo

如果顶部要使用toolbar，需要该themes.xml修改

```xml
<style name = "..." parent="NoActionBar">
	<~
</style>
```

然后在manifest文件中，将对应的activity的主题设置成该主题

### 事件

```java
ToolBar toolbar = findViewById(R.id.tb);
toolbar.setNavigationOnClickListener(new View.OnClickListener(){
	@Override
	public void onClick(View v){
	
	}
});
```

对最左边的图标设置事件

标题居中：

在Toolbar体中加一个TextView用于显示标题，然后将其设置为`android:layout_gravity="center"`



## AlertDialog

### 创建

```java
AlertDialog.Builder builder = new AlertDialog.Builder(context);
builder.setIcon(int iconId);//设置icon
builder.setTitle(CharSequence title);//设置标题
builder.setMessage(CharSequence message);//设置消息
builder.setView(View view);//设置自定义布局
builder.create();//创建dialog
builder.show();//显示对话
setPositiveButtion();//确定按钮
setNegativeButton();//取消按钮
setNeutralButton；//中立按钮
```

### 事件

```java
setPositiveButton("确定",new DialogInterface.OnClickListener(){
	@Override
    public void OnClick(DialogInterface dialog,int which){
        Log.e(TAG,"....");
    }
});
```

### 自定义布局

创建布局文件layout/dialog_view.xml

```java
View dialogView = getLayoutInflater.inflate(R.layout.dialog_view,null);
builder.setView(dialogView);
```

设置的布局将会出现在消息下面，按钮上方

```
Android LayoutInflater详解
在实际开发中LayoutInflater这个类还是非常有用的，它的作用类似于findViewById()。不同点是LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化；而findViewById()是找xml布局文件下的具体widget控件(如Button、TextView等)。

具体作用：
1、对于一个没有被载入或者想要动态载入的界面，都需要使用LayoutInflater.inflate()来载入；
2、对于一个已经载入的界面，就可以使用Activiyt.findViewById()方法来获得其中的界面元素。
```

## PopupWindow

### 属性

- setContentView(View contentView)；设置PopupWindow显示的View
- showAsDropDown(View anchor)；相对某个控件的位置的下方
- showAsDropDown(view anchor,int xoff,int yoff)；设置相对于某个控件的位置，有偏移
- setFocusable(boolean focusable)；设置是否获取焦点，如果为true，则点击空白处将关闭window
- setBackgroundDrawable(Drawable background)；设置背景
- dismiss()；关闭弹窗，可以在popwindow内的控件的事件内调用
- setAnimationStyle(int animationStyle)设置加载动画
- setTouchable(boolean touchable)设置触摸
- setOutsideTouchable(boolean touchable)设置PopupWindow外面的触摸

```java
View popupView = getLayoutInflater().inflate(R.layout.popup_view,null);
PopupWindow popupWindow = new PopupWindow(popupView，ViewGroup.LayoutParams.WRAP_CONTENT,300);
```

## ListView



# 布局

每一个布局都是一个容器，所以每个layout都可以包含子ayout，且最外层layout应当设置layout_width,layout_height为match_parent

### LinearLayout

布局将按水平或者垂直依次排列的方式进行

#### 属性

- orientation：布局中组件的排列方式，有水平和垂直
- gravity：控制组件所包含的子元素的对齐方式，可以用“|”组合
- layout_gravity：控制该组件在父容器里的对齐方式
- background：为该组件设置一个背景图片，或者纯色
- divider：分割线，设置为某个图片
- showDividers：设置分割线所在的位置，none（无），begining，end，middle（每两个组件之间）
- dividePadding设置分割线的padding，（左右两端）
- layout_weight（权重）：按比例划分区域，当当前屏幕有剩余空间(父容器大小-子控件大小之和，包括负数)时将按照权重比例将这些剩余空间分配给控件区域

### RelativeLayout

相对布局

#### 属性

##### 根据父容器定位：

- layout_alignParentLeft=true|false，左对齐

- layout_alignParentRight=true|false，右对齐

- layout_alignParentTop=true|false，顶部对齐
- layout_alignParentBottom=true|false，底部对齐
- layout_centerHorizontal=true|false，水平居中

- layout_centerVertical=true|false，垂直居中
- layout_centerInParent=true|false，中间位置

##### 根据兄弟组件定位：

- layout_toLeftOf="@id/..."，放置于参考组件的左边(组件的右边界和参考组件的左边界对齐)
- layout_toRightOf，放置于参考组件的右边
- layout_above，放置于参考组件的上方
- layout_below,放置在参考组件的下方
- layout_alignTop，对齐参考组件的上边界
- layout_alignBottom，对齐参考组件的下边界
- layout_alignLeft，对齐参考组件的左边界
- layout_alignRight，对齐参考组件的右边界

##### margin

- layout_margin 上下左右外边距
- layout_marginLeft：左外边距

- layout_marginRight:
- layout_marginTop
- layout_marginBottom

##### padding

- layout_padding上下左右内边距
- 。。。同margin

### FrameLayout

帧布局

对每个子控件从左上角开始绘制，每绘制完一个控件，回到左上角开始对下个子控件绘制，即重叠对齐每个子控件的左上角

#### 属性

- foreground：设置前景色，或图片

- foregroundGravity：设置前景位置right ,bottom ，top,left用或连接，

其他属性同前几个layout

### TableLayout

表格布局

\<TableRow\>用于将多个子控件放在同一行，并按水平线性方式排列，行高正好容纳最大的一个控件

当宽度超出屏幕宽度时，直接内容显示不全

#### 属性

- collapseColumns，设置需要被隐藏的列序号，从0开始
- stretchColumns设置允许被拉伸的列的列序号，从0开始
- shrinkColumns设置允许被收缩的列的列序号，从0开始

子控件的属性

- layout_column 显示在某一列,如果后面还有一个控件，则剩下的控件依次排列在当前列后方
- layout_span横跨几列

### GridLayout

#### 属性

- orientation:设置水平还是垂直显示
- columnCount:设置行的显示个数
- rowCount:设置列的显示个数

#### 子控件的属性

- layout_column:显示在第几列

- layout_columnSpan：横跨几列

- layout_columnWeight：横向剩余空间分配比重

- layout_gravity：在网格的显示位置

- layout_row：显示在第几行

- layout_rowSpan：横跨几行

- layout_rowWeight：纵向剩余空间分配方式

### ConstraintLayout

拖动显示



## ListView

ListView是列表，其中每个条目叫做item

下面是容纳listView相关的布局代码

```xml
<ListView
          android:layout_width="match_parent"
          android:layout_height="match_parent">
</ListView>
```

listview还要创建item的View，所以还要为item设置布局xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    ....
</LinearLayout>
```

下面要创建item的View并放置进ListView

1.找到ListView对象

```java
ListView listView = findViewById(R.id.lv);
```

2.创建adapterandroid类用于管理item的view

```java
package com.danny.androidex2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter{

    public List<String> data;

    private Context context;

    public MyAdapter(List<String> data,Context context){
        super();
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if(view==null){
            view =  LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            TextView textView = view.findViewById(R.id.tv);
            textView.setText(this.data.get(i));
        }
        return view;
    }
}
```

3.为ListView指定Item的Adapter

```java
listview.setAdapter(new MyAdapter);
```

### 优化

getview会在上下滑动的时候被调用。

每次调用getView的时候都会findViewById，性能差。

```java
public class ViewHolder{
	TextView textView;
}
```

将每个item的所有控件都放入ViewHolder中，然后将ViewHolder存放在View中：

```java
public View getView(int position,View convertView,ViewGroup parent){
    ViewHolder viewHolder;
    if (convertView = null){
        viewHolder = new ViewHolder();
        convertView= LayoutInflater.from(context).inflate(R.layout.list_item,parent,null);
        viewHolder.textView = convertView.findViewById(R.id.tv);
        convertView.setTag(viewHolder);//将ViewHolder保存到convertView，下次调用时，可以通过convertView获取到ViewHolder
    }else{
        viewHolder = (ViewHolder) convertView.getTag();        
    }
    viewHolder.setText(...);
}
```

# Activity组件


## 生命周期

- onCreate，Activity启动后调用的第一个函数
- onStart，当Activity显示在屏幕上时
- onRestart，当AconStart，当Activity显示在屏幕上时tivity从停止到进入活动状态前。
- onResume，当Activity可接收用户输入时调用，位于Activity栈顶
- onPause，当activity进入暂停，函数被调用。
- onStop，当activity对用户不可见后，进入停止状态。
- onDestroy，当activity被终止前进入非活动状态调用。

![](/home/danny/Documents/notes/android_dev/activity_life_circle.png)

# Service组件

后台服务是一种组件，同Activity一样，但没有界面，后台服务的回调函数也在主线程。但后台服务可以开启线程，值得注意的是，当service被关闭后，其创建的线程并不会随service本身额关闭自动关闭，需要手动指定标志位关闭。service与子线程通信同Activity





# 组件间通信

## 组件注册

AndroidManifest.xml

````xml
<application ......>
    <!--默认的activity-->
    <activity android:name=".MainActivity">
        <!--激活默认的activity的意图-->
        <intent-filter>
        	<action android:name="android.intent.action.MAIN"/>
            <category android:name="android.intent.category.LAUNCHER"/>
        </intent-filter>
    </activity>
    <activity android:name=".MainActivity2"></activity>
</application>
````

## 组件Intent过滤器

组件之间通过Intent传递消息，有两种I指定目标组件的方法，一种是显示指定目标类，另一种是指定一个动作，让安卓系统自己决定传递给哪个程序的哪个组件

Intent过滤器就是让组件能够接收某个类型的Intent，过滤的内容是动作（action），类别（category），数据（data），过滤器\<intent-filter>定义在清单文件的组件下

//todo

## Activity 跳转

### 1.显式启动

创建Intent时指定activity的class类型。

```java
startActivity(new Intent(packageContext:this,MainActivity2.class));
```

### 2.隐式启动

```java
Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.baidu.com"));
startActivity(intent);
```


| 动作              | 说明                                                         |
| ----------------- | ------------------------------------------------------------ |
| ACTION_ANSWER     | 打开接听电话的Activity，默认为Android的拨号界面              |
| ACTION_CALL       | 打开拨号盘界面并拨打电话，使用Uri的数字作为电话号码          |
| ACTION_DELETE     | 打开Activity，对所提供的数据进行删除操作                     |
| ACTION_DIAL       | 打开内置拨号界面，显式URI中提供的号码                        |
| ACTION_EDIT       | 打开Activity，对所提供的数据进行编辑操作                     |
| ACTION_INSERT     | 打开Activity，在提供数据的当前位置插入新项                   |
| ACTION_PICK       | 启动一个子Activity，从提供的数据列表中选择一项               |
| ACTION_SEARCH     | 启动一个Activity，执行搜索动作                               |
| ACTION_SENDTO     | 启动一个Activity，向数据提供的联系人发送信息                 |
| ACTION_SEND       | 启动一个可以发送数据的Activity                               |
| ACTION_VIEW       | 最常用，以uri的方式发送数据，根据URI中的协议类型，以最佳方式启动相应的Activity处理 |
| ACTION_WEB_SEARCH | 打开一个Activity，对提供的数据进行web搜                      |

### 获取Activity返回值

**父Activity中启动子Activity**

```java
//显式启动子Activity
int SUBACTIVITY1=1;
Intent intent = new Intent(this,SubActivity1.class);
startActivityForResult(intent:intent,requestCode:SUBACTIVITY1);
//隐式启动子Activity
int SUBACTIVITY2=2;
Uri uri = Uri.parse("content://contacts/people");
Intent intent = new Intent(Intent.ACTION_PICK,uri);
startActivityForResult(intent,SUBACTIVITY2);
```

**子activity设置返回值**

```java
Uri data = Uri.parse("tel:"+tel_number);
Intent result = new Intent(null,data);
result.putExtra("address","JD Streat");
setResult(RESULT_OK,result);
finish();//关闭activity
```

**在父Activity中获取返回值**

当子Activity关闭后，父Activity会收到子Activity关闭的事件，调用onActivityResult()

```java
public void onActivityResult(int requestCode,int resultCode,Intent result);
```

例：

````java
public void onActivityResult(int request,int resultCode,Intent data){
	Super.onActivityResult(requestCode,resultCode,data);
    switch(requestCode){
        case SUBACTIVITY1:
            if (resultCode==Activity.RESULT_OK){
                Uri uriData = data.getData();
            }else if(resultCode==Activity.Result_CANCEL){}
            break;
        case SUBACTIVITY2:
            break;
    }
}}
````



## service启动

通过startService



