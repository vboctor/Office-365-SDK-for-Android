/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.microsoft.office.integration.test;

public enum AuthType {
    BASIC("basic"), 
    AAD("aad"), 
    UNDEFINED(""), 
    ;

    private String mValue;

    private AuthType(String pValue) {
        mValue = pValue;
    }

    private String getValue() {
        return mValue;
    }

    public static AuthType fromString(String authType) {
        for (AuthType type : values()) {
            if (authType.equalsIgnoreCase(type.getValue())) {
                return type;
            }
        }
        return UNDEFINED;
    }
}
