# demo-java-spi
a simple demo for jdk spi(Service Prodiver Interface)
###前提概述
在面向对象的设计里，我们一般推荐模块之间基于接口编程，模块之间不对实现类进行硬编码。一旦代码里涉及具体的实现类，就违反了可插拔的原则。再者硬编码的不好这处还在于：当原来的模块实现发生改变之后，依赖这个模块的功能代码必须进行调整修改。
###SPI简介
* spi 全称为(**Service Provider Interface**),是JDK内置的一种服务提供机制。
* 这个是针对厂商或者插件的。
* 一般来说对于未知的实现或者对扩展开放的系统，通常会把一些东西抽象出来，抽象的各个模块往往有很多不同的实现方案,例如：日志模块、xml解析模块、jdbc模块等。
###SPI约定
* 当服务的提供者，提供了服务接口的一种实现之后，在jar包中`META-INF/services`目录里同时创建一个以服务接口命名的文件。
* 该文件里就是实现该服务接口的具体实现类（全称）。
* 而当外部程序装配这个模块的时候，就能通过该jar包`META-INF/services/`里的配置文件找到具体的实现类名，并装载实例化，完成模块的注入。

###应用场景
* 1. common-logging 是apache最早提供的日志门面接口（只有接口，没有实现）。发现日志提供商是通过扫描META-INF/services/org.apache.commons.loggings.logging.LogFactory配置文件，通过读取该文件的内容找到日志提供商实现类。
* 2. jdbc jdbc4基于spi的机制来发现驱动提供商，可以通过META-INF/services/java.sql.Driver文件里指定实现类的方式来暴露驱动提供者。

###案例说明
在我们日常开发系统的过程中，日志收集、打印是一个不可缺少的功能模块。这里我实现一个简单的日志模块。

##### 日志接口定义
```
package com.oneape.javaspi;
/**
 * described : 日志打印抽象类
 * Created by oneape on 2018-12-24 10:18.
 * Modify:
 */
public interface Log {
    void debug(String log);
    void info(String log);
}
```

##### 日志接口实现1（Log4j方式）
Log4j.java类实现
```
package com.oneape.javaspi.log4j;

import com.oneape.javaspi.Log;

/**
 * described :
 * Created by oneape on 2018-12-24 10:19.
 * Modify:
 */
public class Log4j implements Log {

    public void debug(String log) {
        System.out.println("Log4j[debug]: --->" + log);
    }

    public void info(String log) {
        System.out.println("Log4j[info]: --->" + log);
    }
}
```
META-INF/services/com.oneape.javaspi.Log文件内容
```
com.oneape.javaspi.log4j.Log4j
```

#####日志接口实现2（Logback方式）
Logback.java类实现
```
package com.oneape.javaspi.logback;

import com.oneape.javaspi.Log;

/**
 * described :
 * Created by oneape on 2018-12-24 10:23.
 * Modify:
 */
public class Logback implements Log {
    public void debug(String log) {
        System.out.println("Logback[debug]: --->" + log);
    }

    public void info(String log) {
        System.out.println("Logback[info]: --->" + log);
    }
}
```
META-INF/services/com.oneape.javaspi.Log文件内容
```
com.oneape.javaspi.logback.Logback
```

##### 外部程序调用方
调用方代码
```
package com.oneape.javaspi.use;

import com.oneape.javaspi.Log;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * described :
 * Created by oneape on 2018-12-24 10:30.
 * Modify:
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("---开始运行---");
        ServiceLoader<Log> serviceLoader = ServiceLoader.load(Log.class);
        Iterator<Log> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Log log = iterator.next();
            log.debug("开始使用日志");
            log.info("开始使用日志");
        }
        System.out.println("---运行结束---");
    }
}
```
pom.xml文件
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>spi</artifactId>
        <groupId>com.oneape</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>log-use</artifactId>

    <properties>
        <jdk.version>1.8</jdk.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.oneape</groupId>
            <artifactId>log</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.oneape</groupId>
            <artifactId>log4j</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
```
最终运行结果如下：
```
---开始运行---
Log4j[debug]: --->开始使用日志
Log4j[info]: --->开始使用日志
---运行结束---

Process finished with exit code 0
```

##### 小彩蛋
 如果想要整个demo的源代码可以到github上下载
[demo-java-spi](https://github.com/newenter/demo-java-spi)
