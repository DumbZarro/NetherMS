# 开发规范

本系统的开发手册XD。

## 命名规则
#### 标识符

对于标识符，使用驼峰命名法。

具体地，对于函数命名，尽量使用**动词+名词**的形式，且**首字母小写**，例如`getUsername`。这里的话`Username`不写作`UserName`是因为`Username`已经约定俗成了；

1. userInfo
2. getUserInfo()
3. myHeadLine

在定义类内成员以及形参时，命名应该尽量详细，例如username`和`getUser(String UserID)`；而对于函数内局部变量，可以适当使用缩写，但能一眼看出该变量能拿来干啥，例如`usrMsg`。

## 目录结构

https://www.jianshu.com/p/b5ebe9fc6c3d

+ common
  + 基础工具包和常量package
+ controller
  + 控制逻辑的接口
+ dao
  + 数据库以及模型
+ resource
  + 静态文件，主要是图形界面