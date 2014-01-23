Simple TCP Servers generic and NIO

run server
 
NIO 

$ java -cp tcp.server-0.0.1.jar com.nio.tcp.server.NIOTCPServer port_number ...

Generic 

$ java -cp tcp.server-0.0.1.jar com.tcp.server.TCPServer 5555

*** TCP Server start @ port:5555 with backlog: 1000

run client 

$ java -cp tcp.server-0.0.1.jar com.tcp.client.TCPClient 


