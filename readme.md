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
    
- 编译问题:
    - 下载依赖缓慢: 下载并编译源码可以先注释build.gradle中除了aliyun之外的源，先从阿里云的源下载，再解除注释从其余地方下载剩余。
    - 依赖项无法下载: Gradle不像maven总会存放repository,某些依赖项由于中央仓库的移动、间接依赖等原因无法下载或反复被下载。对于此类可以使用maven手动下载,下载后就会存在于maven本地仓库中被build.gradle根据localmaven()访问。
    - IDEA构建代理设置: 有些已经在build.gradle中定义的包IDEA报不存在。File-->Settings-->Build、Execution、Deploy-->Gradle-->DelegateSetting-->Build and Run --> use Gradle
    - test之间的依赖: kettle-engine项目的test依赖于kettle-core的test, 在gradle中已解决特标记.
        - 参考: https://stackoverflow.com/questions/5144325/gradle-test-dependency
        - 例如: project(path: ':kettle-core', configuration: 'tests'),
    - test无法通过编译: kettle-engine部分测试用例的null参数缺少类型,增加了对应的类型声明例如:将null改为(String[])null
    - 去掉了部分模块: kettle-engine中的Report和Formula有关的代码和测试，避免引入太多的内容。
    - cwm版本: 只有1.5.4而非8.1.0.6R
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

