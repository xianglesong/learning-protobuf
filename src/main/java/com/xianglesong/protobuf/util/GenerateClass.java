package com.xianglesong.protobuf.util;

import java.io.IOException;

/**
 * Created by rollin on 18/3/20.
 */
public class GenerateClass {

    public static void main(String[] args) {
        String protoFile = "msg.proto";
        String strCmd = "/usr/local/bin/protoc -I=./proto --java_out=./src/main/java ./proto/" + protoFile;

        // 通过执行cmd命令调用protoc.exe程序
        try {
            Runtime.getRuntime().exec(strCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
