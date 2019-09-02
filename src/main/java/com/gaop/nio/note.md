## 第 三十三 节 nio 讲解与体系分析

关于 Java.io 与 Java.nio 的区别：

**Java.io 中最为核心的一个概念是流(Stream)，面向流的编程。** 流是职责单一的，一个流，不可能
既是输入流，又是输出流。

Java.nio 中有三个核心概念：Selector, Channel 与 Buffer。在 Java.nio 中，我们是面向块(Block)
或是缓冲区(Buffer)编程的。Buffer 本身就是一块内存，底层实现上，实际是一个数组。数据的读、写都是
通过 Buffer 来实现的。


![image](https://www.processon.com/view/link/5d6c646de4b0f42553473b00)
![image](D:/youdaoPic/selector_channel_buffer.png)