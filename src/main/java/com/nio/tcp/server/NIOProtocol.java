package com.nio.tcp.server;
import java.nio.channels.SelectionKey;  
import java.io.IOException;  
  
public interface NIOProtocol {  
    void handleAccept(SelectionKey key) throws IOException;  
  
    void handleRead(SelectionKey key) throws IOException;  
  
    void handleWrite(SelectionKey key) throws IOException;  
} 