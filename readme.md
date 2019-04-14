# Flat-Kettle项目

## 概述
为便于研究Kettle代码和更好的使用该开源项目，采用Gradle编译源码，扁平式源码组织。

- 解决: 
    - 仓库不可用: ExplorerRepository功能时常是Disable的状态。
    - Web化: 有需求在Web页面中嵌入Kettle建立ETL过程。

- 方案: 
    - 依赖现有: 先依赖nexus.pentaho.org的8.3.0.0-195版本编译。
    - 逐步导入: 逐步将源码完全在这个项目中做到能够编译。
    - 解决问题: 解决Repository, 页面化等问题。
    - 简单发布: 能够简单快捷的编译和发布。
    
## 开发环境

- JDK1.8
- Gradle

## 日志

- 20190414
    - 导入kettle-core
    - 导入kettle-engine
    - 导入kettle-ui-swt
    - 导入pdi-osgi-bridge-activator
    - 导入pdi-osgi-bridge-core

- 20190413
    - 导入kettle-dbdialog
    - 解决swt依赖的问题通过使用订制的maven仓库: https://github.com/tlw-ray/swt-maven-repository

- 20190412
    - 导入pentaho-vfs-vrowser的源码
    - 导入commons-xul-swt的源码

- 20190411
    - 导入pentaho-metastore的源码。

- 20190410
    - 导入pentaho-application-launcher的源码。

- 20190409
    - 创建该项目，由于ExplorerRepository功能无法使用，期望将KettleWeb单页化嵌入到其他系统中等原因

