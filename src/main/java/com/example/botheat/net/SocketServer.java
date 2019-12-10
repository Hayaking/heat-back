package com.example.botheat.net;


import com.example.botheat.entity.Customer;
import com.example.botheat.entity.HeatData;
import com.example.botheat.service.CustomerService;
import com.example.botheat.service.HeatDataService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author haya
 */
@Log4j2
public class SocketServer {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private HeatDataService heatDataService;
    private ExecutorService pool = Executors.newSingleThreadExecutor();

    private Map<String, Customer> customerMap;
    private Map<String, Socket> socketMap = new ConcurrentHashMap<>();
    private ServerSocket serverSocket;
    private boolean running = true;

    public SocketServer() {

    }

    public void start() {
        try {
            customerMap = customerService.findCustomerMap();
            pool.execute( new MainHandler() );
            pool.execute( new ClientHandler() );
        } catch (Exception e) {

        }

    }

    public void stop() {
        setRunning( false );
        try {
            for (Socket s : socketMap.values()) {
                s.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    public class MainHandler implements Runnable {
        public MainHandler() {
            try {
                serverSocket = new ServerSocket( 5005 );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            Socket client;
            while (isRunning()) {
                try {
                    // 1.接收客户端连接
                    client = serverSocket.accept();
                    client.setSoTimeout( 5000 );
                    String inStr = "";
                    String gprsId = "";
                    // 2.接收客户端发送的数据
                    byte[] inData = new byte[45];
                    DataInputStream input = new DataInputStream( client.getInputStream() );
                    input.read( inData );
                    inStr = HexUtil.bytesToString( inData );
                    if ("FF0D0901".equals( inStr.substring( 0, 8 ) )) {
                        // 3.将8到30位的内容转换为字符串 grpsId
                        gprsId = HexUtil.hexToASCII( inStr.substring( 8, 30 ) );
                        // 4.将gprsId与client映射
                        socketMap.put( gprsId, client );
                        log.warn( "haya:" + gprsId );
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public class ClientHandler implements Runnable {
        private Socket clientSocket;
        private DataInputStream input;
        private DataOutputStream output;
        private byte[] inData = new byte[45];
        private byte[] outData = null;

        public ClientHandler() {
        }

        @Override
        public void run() {
            outer:
            while (isRunning()) {
                // 遍历客户端
                for (String gprsId : socketMap.keySet()) {
                    try {
                        clientSocket = socketMap.get( gprsId );
                        input = new DataInputStream( clientSocket.getInputStream() );
                        output = new DataOutputStream( clientSocket.getOutputStream() );
                        // 初始化输出数据
                        initOutData( gprsId );
                        // 写出
                        output.write( outData, 0, outData.length );
                        output.flush();
                        try {
                            // 读
                            input.read( inData );
                            String inStr = HexUtil.bytesToString( inData );
                            if (inStr.length() == 90 && "0000".equals( HexUtil.crc16( inData ) )) {
                                // 保存数据
                                saveInData( inStr, gprsId );
                            }
                        } catch (SocketTimeoutException e) {
                            e.printStackTrace();
                        }
                        Thread.sleep( 2000 );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (!isRunning()) {
                        break outer;
                    }
                } // for
                try {
                    Thread.sleep( 30000 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } // while
        } // run

        private String getTheAddr(String gprsId) {
            log.info( gprsId );
            String addr = Integer.toHexString( customerMap.get( gprsId ).getAddr() );
            if (addr.length() == 1) {
                addr = "0" + addr;
            }
            return addr;
        }

        private String getTheCustName(String gprsId) {
            return customerMap.get( gprsId ).getCustName();
        }

        private synchronized void saveInData(String inStr, String gprsId) {
            HeatData heatData = new HeatData(){{
                setAddr( Integer.parseInt( getTheAddr( gprsId ), 16 ) );
                setCustName(getTheCustName( gprsId ) );
                setFlow( HexUtil.hexStrToFloat( inStr.substring( 6, 14 ) ) );
                setTemperature( HexUtil.hexStrToFloat( inStr.substring( 14, 22 ) ) );
                setPressure( HexUtil.hexStrToFloat( inStr.substring( 22, 30 ) ) );
                setTotalFlow( HexUtil.hexStrToFloat( inStr.substring( 62, 70 ) ) );
                setMonthFlow( HexUtil.hexStrToFloat( inStr.substring( 70, 78 ) ) );
                setDayFlow( HexUtil.hexStrToFloat( inStr.substring( 78, 86 ) ) );
                setAcquireTime( new Date().getTime() );
            }};
            heatDataService.add( heatData );
//            heatData.insert();
            log.info( "----------------------" );
            log.info( "手机号=" + gprsId );
            log.info( "瞬时流量="
                    + HexUtil.hexStrToFloat( inStr.substring( 6, 14 ) ) );
            log.info( "温度="
                    + HexUtil.hexStrToFloat( inStr.substring( 14, 22 ) ) );
            log.info( "压力="
                    + HexUtil.hexStrToFloat( inStr.substring( 22, 30 ) ) );
            log.info( "总累计流量="
                    + HexUtil.hexStrToFloat( inStr.substring( 62, 70 ) ) );
            log.info( "月累计流量="
                    + HexUtil.hexStrToFloat( inStr.substring( 70, 78 ) ) );
            log.info( "日累计流量="
                    + HexUtil.hexStrToFloat( inStr.substring( 78, 86 ) ) );
        }

        private void initOutData(String gprsId) {
            outData = HexUtil.stringToBytes( getTheAddr( gprsId ) + "03000000140000" );
            String crc = HexUtil.crc16( Arrays.copyOf( outData, 6 ) );
            // CRC-L
            outData[6] = (byte) Integer.parseInt( crc.substring( 2 ), 16 );
            // CRC-H
            outData[7] = (byte) Integer.parseInt( crc.substring( 0, 2 ), 16 );
        }
    }
}
