
FastDFS java client SDK

使用：
1.spring bean 

<bean id="fastdfs" class="com.ttfc.fastdfs.client.FastDFS">
		<property name="fastClientConf" value="classpath:client.conf" />
</bean>

2. 调用
 FastDFS fastdfs = (FastDFS) context.getBean("fastdfs");
 String fileId = fastdfs.upload("/work/project/spring/jar/springjarmvn/src/main/resources/fastdfs/test.jpg");
 
 返回以/group1/****开始的地址，可以直接作为Url地址使用