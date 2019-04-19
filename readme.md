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

- 20190418

    - 导入pdi-plugins-repositories时出现问题无法引入版本pentaho-platform-extensions的8.3.0.0-196或84和pentaho-i18n-bundle:8.3.0.0-208，虽然这两项在仓库中可以找到。
    - 考虑降级到8.1.0.6版本根据已经发布的webSpoon也是在8.1上发布的。



- 20190416

    - 通过启动时调用仓库设定对话框，为Spoon对象设置Repository，使得浏览仓库可用。
    - TODO: 需要找到工具栏上的Connect按钮，来连接入Repository。

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

