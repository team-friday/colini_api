/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.friday.colini.firdaycoliniaccountapi.exception;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException{

    private Long id;

    public AccountNotFoundException(Long id) {
        this.id = id;
    }
}
