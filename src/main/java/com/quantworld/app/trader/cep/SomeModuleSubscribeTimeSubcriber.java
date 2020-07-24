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
package com.quantworld.app.trader.cep;

import com.quantworld.app.data.constants.EventTypeEnum;

/**
 * @author: Shawn
 * @Date: 10/25/2019
 * @Description:
 */
public class SomeModuleSubscribeTimeSubcriber {
  int id;

  public SomeModuleSubscribeTimeSubcriber(int id) {
    this.id = id;
  }

  @OnEvent(eventType = EventTypeEnum.EVENT_TIMER)
  private void onTimerEvent(Event event) {
    System.out.println(id++ + ": date: " + event.getData());
  }

  @OnEvent(eventType = EventTypeEnum.EVENT_ACCOUNT)
  private void onAccountEvent(Event event) {
    System.out.println(id++ + ": date: " + event.getData());
  }
}
