/*
 * Copyright 2017-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atomix.core.lock;

import io.atomix.core.lock.impl.DefaultDistributedLockService;
import io.atomix.core.lock.impl.DistributedLockProxyBuilder;
import io.atomix.core.lock.impl.DistributedLockResource;
import io.atomix.primitive.PrimitiveManagementService;
import io.atomix.primitive.PrimitiveType;
import io.atomix.primitive.resource.PrimitiveResource;
import io.atomix.primitive.service.PrimitiveService;
import io.atomix.primitive.service.ServiceConfig;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Distributed lock primitive type.
 */
public class DistributedLockType implements PrimitiveType<DistributedLockBuilder, DistributedLockConfig, DistributedLock, ServiceConfig> {
  private static final String NAME = "lock";

  /**
   * Returns a new distributed lock type.
   *
   * @return a new distributed lock type
   */
  public static DistributedLockType instance() {
    return new DistributedLockType();
  }

  @Override
  public String id() {
    return NAME;
  }

  @Override
  public PrimitiveService newService(ServiceConfig config) {
    return new DefaultDistributedLockService(config);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveResource newResource(DistributedLock primitive) {
    return new DistributedLockResource(primitive.async());
  }

  @Override
  public DistributedLockBuilder newPrimitiveBuilder(String name, PrimitiveManagementService managementService) {
    return newPrimitiveBuilder(name, new DistributedLockConfig(), managementService);
  }

  @Override
  public DistributedLockBuilder newPrimitiveBuilder(String name, DistributedLockConfig config, PrimitiveManagementService managementService) {
    return new DistributedLockProxyBuilder(name, config, managementService);
  }

  @Override
  public String toString() {
    return toStringHelper(this)
        .add("id", id())
        .toString();
  }
}
