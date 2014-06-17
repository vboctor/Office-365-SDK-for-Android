package com.example.simplelists;

/**
 * Copyright � Microsoft Open Technologies, Inc.
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

/**
 * Stores application public constants such as URLs to update configurations, default check back-in values, etc.
 */
public class Constants {

    /**
     * Login.
     * Example: name@your-domain.onmicrosoft.com
     * This can be blank.
     */
    public static final String USER_HINT = "";
    
   /**
     * Url for Oauth2 authorization page.
     */
    public static final String AUTHORITY_URL = "https://login.windows.net/common";

    /**
     * Application unique ID for Oauth2 authorization.
     */
    public static final String CLIENT_ID = "Enter your client ID here";

    /**
     * Resource id - URL of the resource we will work with.
     * TODO: Use Discovery API
     */
    public static final String RESOURCE_ID = "https://your-domain.sharepoint.com";

    /**
     * URL application will be redirected after authentication.
     */
    public static final String REDIRECT_URL = "Enter your redirect URL here";
	}
