# java-study

    todo: 
    1. 添加注解功能
    2. servlet 支持 doGet doPost .....
   
5/6
    
    1. 修复 request 请求 path 无参数下的问题
    2. String.split("\\.") java 中 String.split 分割 .:*| 等字符需要加\\转义
    3. error: Request read Socket中的数据时 数组越界

5/1
    优化代码，添加多线程
    
    问题： Dispatcher 中的 run 方法为什么不释放资源的话，新的请求无法
    建立链接，不是已经用了多线程了嘛？？？

 4/29
    添加 Request 类， 用于请求的解析