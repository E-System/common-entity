/*
 * Copyright 2018 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.entity.model.audit.code;

import com.es.lib.entity.event.EMOperation;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
public interface IAuditActionCode {

    String INSERT = EMOperation.INSERT.name();
    String UPDATE = EMOperation.UPDATE.name();
    String DELETE = EMOperation.DELETE.name();

    String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    String LOGIN_ERROR = "LOGIN_ERROR";
    String LOGOUT = "LOGOUT";
    String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    String CHANGE_PERMISSION = "CHANGE_PERMISSION";

    String RESTORE_PASSWORD_REQUEST = "RESTORE_PASSWORD_REQUEST";
    String RESTORE_PASSWORD_SUCCESS = "RESTORE_PASSWORD_SUCCESS";
    String RESTORE_PASSWORD_EXPIRED = "RESTORE_PASSWORD_EXPIRED";
}
