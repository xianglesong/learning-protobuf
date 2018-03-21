package com.xianglesong.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import com.example.tutorial.AddressBookProtos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by rollin on 18/3/20.
 */
public class Test {

    public static void main(String[] args) {

        AddressBookProtos.Person.Builder personBuilder = AddressBookProtos.Person.newBuilder();
        personBuilder.setEmail("test@xianglesong.com");
        personBuilder.setId(1000);
        AddressBookProtos.Person.PhoneNumber.Builder phone = AddressBookProtos.Person.PhoneNumber.newBuilder();
        phone.setNumber("18600000000");

        personBuilder.setName("tester");
        personBuilder.addPhones(phone);

        AddressBookProtos.Person person = personBuilder.build();

        System.out.println(person);

        // 获取字节数组，适用于SOCKET或者保存在磁盘。
        byte[] data = person.toByteArray();
        // 反序列化
        AddressBookProtos.Person result = null;
        try {
            result = AddressBookProtos.Person.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        System.out.println(result);
        System.out.println(result.getEmail());

        System.out.println("1 ==================================");
        // 第二种序列化：粘包,将一个或者多个protobuf对象字节写入stream。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 生成一个由：[字节长度][字节数据]组成的package。特别适合RPC场景
        try {
            person.writeDelimitedTo(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 反序列化，从steam中读取一个或者多个protobuf字节对象
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        try {
            result = AddressBookProtos.Person.parseDelimitedFrom(byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        System.out.println(result.getEmail());

        System.out.println("2 ==================================");

        // 第三种序列化,写入文件或者Socket
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("test.dt"));
            person.writeTo(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("test.dt"));
            result = AddressBookProtos.Person.parseFrom(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);

        System.out.println("3 ==================================");
        AddressBookProtos.AddressBook.Builder addressBuilder = AddressBookProtos.AddressBook.newBuilder();
        addressBuilder.addPeople(person);

        AddressBookProtos.AddressBook addressBook = addressBuilder.build();

        fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("address.dt"));
            addressBook.writeTo(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
