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
package com.quantworld.app.broker.gateway;

import com.alibaba.fastjson.JSONObject;
import com.quantworld.app.data.CancelRequest;
import com.quantworld.app.data.OrderData;
import com.quantworld.app.utils.QuantStringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.quantworld.app.constants.QuantConstants.QW;

/**
 * @author: Shawn
 * @Date: 3/1/2020
 * @Description:
 */
public class LocalOrderManager {

  private BaseGateway gateway;
  private Map<String, OrderData> orderDataMap = new ConcurrentHashMap<>();
  private Map<String, String> localSysOrderIdMap = new ConcurrentHashMap<>();
  private Map<String, String> sysLocalOrderIdMap = new ConcurrentHashMap<>();
  private Map<String, JSONObject> pushDataBuf = new ConcurrentHashMap<>();
  private Map<String, CancelRequest> cancelRequestBuf = new ConcurrentHashMap<>();

  public LocalOrderManager(BaseGateway gateway) {
    this.gateway = gateway;
  }

  /**
   * TODO: If the order Id is generated by UUID, the orderDataMap can be enlarged infinitely.
   * TODO: Hence, the id should be generated regularly.
   * Order Id: generated by UUID util
   * @return
   */
  public String getNewLocalOrderId() {
    return QW + QuantStringUtil.getUUID();
  }

  public String getLocalOrderId(String sysOrderId) {
    String localOrderId = sysLocalOrderIdMap.get(sysOrderId);
    if (StringUtils.isBlank(localOrderId)) {
      localOrderId = getNewLocalOrderId();
      updateOrderIdMap(localOrderId, sysOrderId);
    }
    return localOrderId;
  }

  public void updateOrderIdMap(String localOrderId, String sysOrderId) {
    sysLocalOrderIdMap.put(sysOrderId, localOrderId);
    localSysOrderIdMap.put(localOrderId, sysOrderId);

    checkCancelRequest(localOrderId);
    checkPushData(sysOrderId);
  }

  public void pushDataCallback(JSONObject data) {
  }

  public void checkCancelRequest(String localOrderId) {
    if (!cancelRequestBuf.keySet().contains(localOrderId)) {
      return;
    }
    CancelRequest request = cancelRequestBuf.get(localOrderId);
    gateway.cancelOrder(request);
  }

  public void checkPushData(String sysOrderId) {
    if (!pushDataBuf.keySet().contains(sysOrderId)) {
      return;
    }
    JSONObject data = pushDataBuf.get(sysOrderId);
    pushDataCallback(data);
  }

  public void addPushData(String sysOrderId, JSONObject data) {
    pushDataBuf.put(sysOrderId, data);
  }

  public OrderData getOrderWithSysOrderId(String sysOrderId) {
    String localOrderId = sysLocalOrderIdMap.get(sysOrderId);
    if (StringUtils.isBlank(localOrderId)) {
      return null;
    } else {
      return getOrderWithLocalOrderId(localOrderId);
    }
  }

  public OrderData getOrderWithLocalOrderId(String localOrderId) {
    OrderData orderData = orderDataMap.get(localOrderId);
    if (orderData != null) {
      return orderData.deepClone();
    } else {
      return new OrderData();
    }
  }

  public void onOrder(OrderData orderData) {
    // localOrderId : orderData
    orderDataMap.put(orderData.getOrderId(), orderData.deepClone());
    gateway.onOrder(orderData);
  }

  public void cancelOrder(CancelRequest request) {
    String sysOrderId = getSysOrderId(request.getOrderId());
    if (StringUtils.isBlank(sysOrderId)) {
      cancelRequestBuf.put(request.getOrderId(), request);
    }
    gateway.cancelOrder(request);
  }

  public String getSysOrderId(String localOrderId) {
    return localSysOrderIdMap.get(localOrderId);
  }

}
