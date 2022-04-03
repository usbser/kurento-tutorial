/*
 * Copyright 2018 Kurento (https://www.kurento.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kurento.tutorial.helloworld;

import org.kurento.client.KurentoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * Kurento Java Tutorial - Application entry point.
 */
//页面客户端通过WebSocket发送的信令消息会被应用程序服务端的Application类处理
// 同时，他还需要创建一个和Kurento Media Server进行WebSocket通信的客户端
//当页面客户端通过websocket发送过来信令后，会在HelloWorldHandler类
// 的 handlerTextMessage方法中进行分别处理:
@SpringBootApplication //告诉Spring Boot根据添加的jar依赖猜测你想如何配置Spring
@EnableWebSocket
public class Application implements WebSocketConfigurer
{
  //页面客户端通过WebSocket发送的信令消息会被应用程序服务端的Handler类处理
  @Bean//实现了TextWebSocketHandler来处理文本WebSocket的请求，它就是WebSocket的服务端
  public Handler handler()
  {
    return new Handler();
  }

  @Bean
  public KurentoClient kurentoClient()
  {
    return KurentoClient.create();
  }

  @Bean
  public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
    ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    container.setMaxTextMessageBufferSize(32768);
    return container;
  }

  @Override//注册函数 注册了一个 WebSocketHandler
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
  {
    registry.addHandler(handler(), "/helloworld");
  }

  public static void main(String[] args) throws Exception
  {
    SpringApplication.run(Application.class, args);
  }
}
