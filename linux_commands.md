# systemd

Systemd管理的最小单元就是Unit，它是单个进程的描述，Unit之间相互调用和依赖组成了一个庞大的任务管理系统，这就是Systemd的基本思想。

Unit有很多种：

- **Service Unit** ：用于定义系统服务
- **Target Unit**：用于模拟实现“运行级别”
- **Socket Unit**：用于Systemd监听Socket,在有连接的时候启动

- **Device Unit**：用于定义内核识别的设备
- **Mount Unit**：用于定义文件系统的挂载
- **Snapshot Unit**：用于管理系统快照
- **Swap Unit**：用于表示swap设备
- **Automount Unit**：用于文件系统自动挂载

- **Path Unit**：用于定义文件系统中的文件或目录

所有的unit的描述文件都存放在`/usr/lib/systemd/system`和`/etc/systemd/system`中，systemd通过这些文件确定每一个unit

## Service Unit

通常有`[Unit]` `[Service]` `[Install]`组成

示例：`/usr/lib/systemd/system/xxxx.service`

```ini
[Unit]
Description=描述信息
Documentation=man:xxx(x)
Documentation=https://xxxx.xxx.xxx/xxx
#After定义unit的启动次序，表示当前unit应该晚于哪些
After=xxx.service
#依赖到的其他units；强依赖，被依赖的units无法激活时，当前unit即无法激活
Requies=xxx.service
#依赖到的其他units;弱依赖
Wants=xxx.service
#定义units之间的冲突
Conflicts=xxx.service

[Install]
#别名，可以使用systemctl command Alias.service；
Alias=
#被哪些units所依赖
RequiredBy=
#被哪些units所依赖，如果要开机自启动（systemctl enable）则需要配置此项目为multi-user.target，表示该进程可以在boot-up阶段启动
WantedBy=multi-user.target

[Service]
#用于定义影响ExecStart及相关参数的功能的unit进程启动类型，其类型有：
#	simple：默认值，执行ExecStart指定的命令启动主进程
#	forking：以fork的方式从父进程创建子进程，创建后父进程会立即退出
#	oneshot：一次性进程，Systemd会等待当前服务退出，再继续往下执行
#	dbus：当前服务通过D-Bus启动
#	notify：当前服务启动完毕，会通知systemd在继续往下执行
#	idle：如果有其他任务执行完毕，当前服务才会运行
Type=
#EnvironmentFile：环境配置文件
EnvironmentFile=
#指明启动unit要运行命令或者脚本
ExecStart=
#在ExecStart之前运行
ExecStartPre=
#在ExecStartPost之后运行
ExecStartPost=
#指明重新加载配置的命令或脚本
ExecReload=/bin/kill -HUP $MAINPID
#指明停止unit要运行的命令或者脚本
ExecStop=
#当设定Restart=1时，则当次daemon服务意外终止的时候会再次自动启动
Restart=
#指运行命令的用户
User=
```

## Timer Unit

定时器：可以定时调用其他Unit

Service Unit只是定义了如何执行任务，还必须通过Timer Unit定义执行时间

实例：

```ini
#定义Timer调用的my-timer.service
[Unit]
Description=MyTime
[Service]
ExecStart=/bin/echo 'hello my timer'

#定义调用Service的my-timer.timer
[Unit]
Description= Run mytimer every minutes
[Timer]
OnUnitActiveSec=1m
Unit=mytimer.service
[Install]
WantedBy=multi-user.target
```

timer字段：

## Systemd 操作命令

查看Unit状态

```shell
systemctl status UnitName
```

查看是否已经启用该单元

```shell
systemctl is-enabled UnitName
```

开机自动执行该单元

```shell
systemctl enable UnitName
```

关闭开机自动执行

```shell
systemctl disable UnitName
```

启动单元

```shell
systemctl start UnitName
```

重启单元

````shell
systemctl restart UnitName
````

重新加载服务配置而不中断服务

```shell
systemctl reload UnitName
```

杀死进程单元

```shell
systemctl kill UnitName
```

禁止服务，无法启动或者开机启动

```shell
systemctl mask UnitName
```

列出systemd现在在内存中的unit

```shell
systemctl list-units
```

列出所有的unit文件以及他们是否启用

```
systemctl list-unit-files
```

# date

按默认格式输出时间日期

```shell
$ date
Tue Sep 6 08:33:51 AM CST 2022
```

按指定模板输出时间日期

```
date +%Y-%m-%d_%H:%M:%S
```

# echo

```shell
echo content
```

