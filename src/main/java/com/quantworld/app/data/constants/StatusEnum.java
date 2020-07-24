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
package com.quantworld.app.data.constants;

/**
 * @author: Shawn
 * @Date: 10/17/2019
 * @Description:
 */
public enum StatusEnum {

  NONE("NONE"),
  SUBMITTING("提交中"),      // active
  NOTTRADED("未成交"),       // active
  PARTTRADED("部分成交"),    // active
  CANCELLING("撤单中"),      // active
  ALLTRADED("全部成交"),
  PARTCANCELED("部分撤单"),   // active
  CANCELED("已撤销"),
  REJECTED("拒单"),
  CLOSED("订单关闭");
  String value;

  StatusEnum(String value) {
    this.value = value;
  }
}
