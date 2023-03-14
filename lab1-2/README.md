# Lab 1-2

## 手动下载并配置Java

### 软件包下载

* Java现在由Oracle维护，每年都会维护并更新版本，但配环境时推荐**Java 8**，新版本并不一定能向下兼容
* 下载时，同一版本下，也有JDK、JRE等多种类型软件包可以选择，简单来说，JDK下可用的工具最全面
* 软件包可以在[Oracle官网](https://www.oracle.com/java/technologies/downloads/archive/)下载，选择适合设备的版本

### 环境配置

上一节中下载的Java的软件包，如果是可执行文件（如win设备下载的.exe后缀的安装向导），一般在安装之后会自动配置好系统的环境变量等，也就是让我们可以直接使用`java`、`javac`、`jar`等命令，开启一个终端，使用诸如`java --version`的查看版本命令即可验证

如果是单纯压缩的软件包，如Linux设备下载的.tar.gz包，就需要手动配置环境变量，

### Linux：（以下用[JDK_ARCHIVE_PATH]指代解压后软件包的绝对路径）
1. 使用文本编辑器修改当前使用的shell的资源文件，一般为home目录下rc结尾的隐藏文件，例如使用**bash**时，修改`~/.bashrc`
2. 在资源文件的末尾用`export`命令添加环境变量，保存

    ```shell
     export JAVA_HOME=[JDK_ARCHIVE_PATH]
     export JRE_HOME=${JAVA_HOME}/jre
     export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
     export PATH=${JAVA_HOME}/bin:$PATH
    ```

3. 使用`source ~/.bashrc`命令让shell重新加载资源文件，或者重启shell
4. 如果想要切换不同的java版本，也可以修改对应的资源文件

### Windows: 
系统变量 `JAVA_HOME` 的作用：作为变量方便引用，方便进行`jdk`版本替换。

系统变量 `PATH` 的作用：`Windows`操作系统是根据环境变量 `PATH` 来查找命令。

系统变量 `CLASSPATH` 的作用：指定类搜索路径，要使用已经编写好的类，`JVM`就是通过 `CLASSPATH` 来寻找使用已经编写好的`Java`类。（jdk1.5版本以上不需要设置）

#### <font color=red>设置永久环境变量</font>
1. 于Oracle官网下载[JDK](	
https://download.oracle.com/java/19/latest/jdk-19_windows-x64_bin.zip)，解压到任何路径下
2. 在windows 设置中搜索环境变量
3. 在下方系统变量中，点击新建，输入变量名`JAVA_HOME`，变量值为步骤1解压路径的目录（如xxx/jdk-19.0.2)
4. 在下方系统变量中，选择Path变量，点击编辑，新增两个变量 `%JAVA_HOME%\bin` 与 `%JAVA_HOME%\jre\bin`。
5. 在下方系统变量中，点击新建，输入变量名`CLASSPATH`，变量值为 `.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;`
6. 快捷键 `window+R`，输入`cmd`打开命令提示符，输入`java`检测是否正确配置好系统环境。

#### 设置临时环境变量
1. 使用set命令可设置临时环境变量path，所有在命令行下对环境变量的修改只对当前窗口有效，不是永久性的修改。
2. 查看某个环境变量：输入 `set 变量名`，例如 `set path`
3. 修改环境变量：输入`set 变量名=变量内容`，例如 `set path=D:\test`
4. 追加环境变量内容：`set 变量名=%变量名%;变量内容`，例如 ` set path=%path%;d:\file`
5. 设置临时环境的变量名与变量值与设置永久变量相同，此处作为知识拓展使用。

## 包管理系统安装Java

* 如果使用**Ubuntu**等Linux发行版，一般拥有**apt**等包管理系统，使用特定的命令搜索java软件包并安装，环境就会自动配置完成

  例如，在Ubuntu系统上安装Java 8的JDK软件包的命令为：`openjdk-8-jdk`，安装后可以在`/usr/lib/jvm/java-8-openjdk-amd64`目录下找到

* 使用包管理系统安装的好处之一在于，可以很方便的管理不同的java版本，例如使用apt进行包管理时，可以用如下命令切换版本

  ```shell
  # 展示所有可用的Java版本
  update-java-alternatives --list
  
  # 选择默认的Java
  sudo  update-alternatives --config java
  
  # 选择默认的javac
  sudo  update-alternatives --config javac
  ```

## 类与包与导入-初步

### 类

* Java程序的基本组成单位是**类(class)**，多数代码都要写在类里面，使用`class`关键字开头
* Java程序的源代码文件扩展名是`.java`，文件名一般为源代码中类的名称，如`helloworld.java`文件中定义`helloworld`类
* 一般一个文件中定义一个类
* 类的全名=包名.类名

### 包

写在类定义的文件的开头，使用`package`关键字

* 用于隔离类名，避免命名冲突

* 包名可以写的很长，中间的英文句号`.`和包所在的**目录结构对应**

  例如，类`hello`所在的包为`package what.day.is.it.today;`，它所在目录可能是`~/what/day/is/it/today/`，后续在`~`目录下执行命令即可定位文件1

* 加了包名之后要和目录结构统一，比如在`/com`目录下用`shell`，就不能正常解析主类`com.Main`

### 导入

不同的文件之间使用`import`关键字来导入整个包或者包里的某个类，类似C语言中的#include头文件



## 编译与运行

### 编译

使用`javac`命令来编译一个`.java`文件，输出的`.class`文件可以被Java虚拟机（JVM）解释执行，从而构建跨平台的程序

常用的选项为`-d`，可以设置输出目录，并在输出时保留`.java`原有的目录结构

### <font color=red>运行</font>

使用`java`命令来运行一个`.class`文件，更确切的说是该文件中的类

Java程序在JVM上执行，将有`main`方法的类作为执行的入口，该方法类似C语言的main函数

当使用`java`命令执行一个Java程序时，给出的是入口类的类名，而且**必须是类的全名**

例如：要使用命令行运行一个 `HelloWorld.java` 文件，首先使用 `javac HelloWorld.java` 将其编译为 `HelloWorld.class` 文件，之后使用 `java HelloWorld` 调用虚拟机执行程序。

## 依赖与打包

### <font color=red>依赖</font>

当使用`javac`或`java`命令时，通过`-cp`选项指定类路径(**classpath**)，用于搜索导入的类，当有多个依赖时可以使用`;`(win)或`:`(类unix)来分隔

`javac`需要找到被编译的`.java`文件中被导入的包，`java`需要找到入口类所属的`.class`文件
当出现需要`import`的自定义类时，需要使用`-cp path` 编译需要依赖的类并指明依赖的类所在的位置。例如：Main.java文件中import了当前目录com文件下的`Hello.java`中的类，因此在编译`Main.java`文件时需要使用 `-cp` 指令显式编译`Main.java`文件。（命令为 `javac Main.java -cp xxx`，其中`xxx`为当前所在目录的绝对路径）

### 打包

如果一个Java程序所需要的类和依赖过多时，可以将整个程序打包成为`.jar`格式

* 此时，可以使用`-cp xxx.jar`来很方便的指定类路径
* 使用`jar`命令就可以执行打包的各类操作


## <font color=red>作业上传</font>

本次作业需要提供的实验步骤操作截图，包括：
* 检验Java环境变量配置是否正确，将在CMD中正确运行java命令的页面截图
* 使用命令行运行提供的Java文件（包括运行提供的 `HelloWorld.java` 文件以及带有依赖关系的 `Main.java` 文件)，将运行命令与输出的页面截图

将上述页面的截图打包到word文档中，在elearning作业提交界面上传
