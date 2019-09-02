package com.gaop.netty.protocobuf_netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @author gaopeng@doctorwork.com
 * @description
 * @date 2019-08-14 8:06
 **/
public class  MyClientHandler extends SimpleChannelInboundHandler<AddressBookProtos.Person>{

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddressBookProtos.Person person) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder().
                setName("高鹏").
                setId(1).
                setEmail("gp626676634@sina.com").
                build();

        int rst = new Random().nextInt(3);

        if (0 == rst) {
//            AddressBookProtos.MyMessage myMessage = AddressBookProtos.MyMessage.newBuilder().
//                    setDataType(AddressBookProtos.MyMessage.DataType.PersonType).setPerson()
        } else if (1 == rst) {

        } else {

        }



        ctx.channel().writeAndFlush(person);
    }
}
