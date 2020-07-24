/*
 * Copyright 2019-2020 Shawn Peng
 * Email: shawnpeng@yeah.net
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quantworld.app.data;

import com.quantworld.app.data.constants.DirectionEnum;
import com.quantworld.app.data.constants.ExchangeEnum;
import com.quantworld.app.data.constants.OffsetEnum;
import com.quantworld.app.data.constants.OrderTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Shawn
 * @Date: 10/17/2019
 * @Description:
 */
public class OrderRequest extends BaseData {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private String symbol = StringUtils.EMPTY;
  private ExchangeEnum exchange = ExchangeEnum.NONE;
  private DirectionEnum direction = DirectionEnum.NONE;
  private OrderTypeEnum type = OrderTypeEnum.NONE;
  private float volume;
  private float price;
  private OffsetEnum offset = OffsetEnum.NONE;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public ExchangeEnum getExchange() {
    return exchange;
  }

  public void setExchange(ExchangeEnum exchange) {
    this.exchange = exchange;
  }

  public DirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(DirectionEnum direction) {
    this.direction = direction;
  }

  public OrderTypeEnum getType() {
    return type;
  }

  public void setType(OrderTypeEnum type) {
    this.type = type;
  }

  public float getVolume() {
    return volume;
  }

  public void setVolume(float volume) {
    this.volume = volume;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public OffsetEnum getOffset() {
    return offset;
  }

  public void setOffset(OffsetEnum offset) {
    this.offset = offset;
  }

  public String getQwSymbol() {
    return (symbol + "." + exchange.name()).toLowerCase();
  }

  public OrderData createOrderData(String orderId, String gatewayName) {
    OrderData order = new OrderData();
    order.setSymbol(symbol);
    order.setExchange(exchange);
    order.setOrderId(orderId);
    order.setType(type);
    order.setDirection(direction);
    order.setOffset(offset);
    order.setPrice(price);
    order.setVolume(volume);
    order.setGatewayName(gatewayName);

    return order;
  }

  public OrderRequest deepClone() {
    return (OrderRequest) super.deepClone();
  }

  @Override
  public String toString() {
    return "OrderRequest{" +
        "symbol='" + symbol + '\'' +
        ", exchange=" + exchange +
        ", direction=" + direction +
        ", type=" + type +
        ", volume=" + volume +
        ", price=" + price +
        ", offset=" + offset +
        ", gatewayName='" + gatewayName + '\'' +
        '}';
  }
}
