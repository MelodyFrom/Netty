package com.gaop.netty.protocolBuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author gaopeng@doctorwork.com
 * @description
 * @date 2019-08-06 8:02
 **/
public class ProtobufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder().
                setName("高鹏").
                setId(1).
                setEmail("gp626676634@sina.com").
                build();

        byte[] person2byteArgs = person.toByteArray();
        for (byte b : person2byteArgs) {
            System.out.print(b);
        }
        System.out.println("原结构：");
        AddressBookProtos.Person person2 = AddressBookProtos.Person.parseFrom(person2byteArgs);

        System.out.println(person2);
    }
}
