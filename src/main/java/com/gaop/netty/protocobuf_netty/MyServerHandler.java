package com.gaop.netty.protocobuf_netty;

import com.gaop.netty.protocolBuf.AddressBookProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gaopeng@doctorwork.com
 * @description
 * @date 2019-08-14 7:51
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<AddressBookProtos.Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddressBookProtos.Person person) throws Exception {
        System.out.println(person.getId());
        System.out.println(person.getName());
        System.out.println(person.getEmail());
    }
}
