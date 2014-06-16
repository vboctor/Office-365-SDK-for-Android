# Office 365 SDK for Android Preview #

**Table of Contents**

- [Overview](#overview)
- [Details](#details)
- [Quick Start for SharePoint lists and files](#quick-start-for-sharepoint-lists-and-files)
- [Quick Start for Exchange mail, calendar and contact apps](#quick-start-for-exchange-mail-calendar-and-contact-apps)
- [Building Mail-Calendar-Contact SDK from Sources](#building-mail-calendar-contact-sdk-from-sources)
- [Running Mail-Calendar-Contact e2e Tests](#running-mail-calendar-contact-e2e-tests)
- [Features](#features)
- [Tests](#tests)
- [Contributing](#contributing)
- [License](#license)

## Overview ##

With the Office 365 SDK for Android Preview, it’s now possible to use data stored in Microsoft Office 365 from your Android Apps. This means, you can access SharePoint lists, folders or Exchange calendar, contacts and emails from within your Android-based applications. 

[Microsoft Open Technologies, Inc. (MS Open Tech)](http://msopentech.com) has built the **Office 365 SDK for Android Preview**, an open source project that strives to help Android developers access Office 365 data from their apps.

This SDK provides access to: Microsoft SharePoint Lists, Microsoft SharePoint Files, Microsoft Exchange Calendar, Microsoft Exchange Contacts, Microsoft Exchange Mail.

## Details ##

The SDK is composed of three independent packages, so that you can import only the SDK that you need in your project.

- office365-files-sdk [depends on office365-base-sdk]
- office365-lists-sdk [depends on office365-base-sdk]
- office365-mail-calendar-contact-sdk ([see wiki](https://github.com/OfficeDev/Office-365-SDK-for-Android/wiki/User-guide-%28Exchange-SDK%29))

The SDK is compatible with the following Android versions: 4.0.3 (API15),4.1.2 (API16),4.2.2 (API 17), 4.3 (API18)

Exchange SDK [is available](http://search.maven.org/#browse%7C1648781292) as Maven dependency via Maven Central Repository.

```xml
<dependency>
   <groupId>com.microsoft.office</groupId>
   <artifactId>mail-calendar-contact</artifactId>
</dependency>
```

To help you get started quickly, we have created sample applications, including:

• Asset management app that allows the user to view the items in a particular list of a SharePoint site, add a new item with a picture into this list, update and delete an item from this list.

• Mail contact and calendar app that lets the user view all his mails from the drafts folder and send mail, events from all his calendars and all his contacts.

Additionally we are working on an SDK that covers the [discovery API](http://go.microsoft.com/fwlink/?LinkID=392944 "discovery API"). Until then please look at the files-discovery-app that we have written that uses the discovery API to get the list of files from my-lists on SharePoint.

## Quick Start for SharePoint lists and files ##

**Asset-management-app**

----------

Download the following code onto your machine from this repo: office365-base-sdk, office365-lists-sdk, asset-management from this repo and import the above code into your favorite IDE.

Add a dependency on the office365-base-sdk from the office365-lists-sdk.
Add a dependency on the office365-lists-sdk from the asset-management app

Subscribe to SharePoint online from [here](http://msdn.microsoft.com/en-us/library/fp179924(v=office.15).aspx) or use an existing SharePoint Online site.

The application expects a picture library on the site with Title and Description columns visible in the default view of the library.
Run the application. Click on the cog wheel at the top of the app on the first screen and you will see the second screen that has the list of settings that need to be configured. Examples are below.

- SharePoint URL would be like “https://foobar.sharepoint.com
- Site URL would be like “sites/developers”
- Library name would be something like “foobarPictureLibrary”
- Please choose the Cookie authentication method under authentication method.

The configuration for the app is done. You can go back to the first screen and click on the box next to the settings to retrieve the items in the picture library, add an item or update the title or description of an existing item.

All the code that calls into the lists SDK is in the /assetmanagement/src/com/microsoft/assetmanagement/datasource/ListItemsDataSource.java class

- View the list items – Refer to the getDefaultListViewItems method
- Add a list item – Refer to saveNewCar method
- Update a list item – Refer to updateCarData method
- Delete a list item – Refer to deleteCar method

Note: The app has been tested on Android versions API 14 and 17 .

**sample-file-discovery**

----------

Download the following code onto your machine from this repo: office365-base-sdk, office365-files-sdk, sample-file-discovery.
Download the Azure Active directory Android library [ADAL] from the following [repo](https://github.com/MSOpenTech/azure-activedirectory-library-for-android).

Import the above code into your favorite IDE.

Add a dependency on the office365-base-sdk from the office365-lists-sdk.
Add a dependency on the office365-base-sdk from the office365-files-sdk.
Add a dependency on the office365-files-sdk and ADAL the from the files demo app.

Subscribe to SharePoint online from [here](http://msdn.microsoft.com/en-us/library/fp179924(v=office.15).aspx) or use an existing SharePoint Online site.

Please edit the sample-file-discovery\src\com\microsoft\filediscovery\Constants.java file and provide the values for the constants below.Please refer to [this](http://msdn.microsoft.com/en-us/library/dn605895(v=office.15).aspx) to understand how to obtain the values below and set the right permissions for the app so that it can read files from sharepoint.
    
    public static final String CLIENT_ID = "your-client-id";
    public static final String REDIRECT_URL = "http://your-redirect-url.com";

A breakdown of the code is below.

Step 1: The app gets authorized by the user by calling the Authorization URL and passing its hardcoded scope.

Step 2: The app gets a token for Discovery by calling the Token URL and passing the code from 
OfficeClient in the file sample-file-discovery\src\com\microsoft\filediscovery\datasource\ListItemsDataSource.java

    officeClient = mApplication.getOfficeClient(DiscoveryFragment.this.getActivity(), Constants.DISCOVERY_RESOURCE_ID).get();
    
Step 3: The app discovers the services that implement its desired scope by calling the Discovery URL passing the token from step #2.
    
    List services = officeClient.getDiscoveryInfo("https://api.officeppe.com/discovery/me/services").get();

Step 4: For each consented capability, Discovery will return a service URL and a service resource ID.
    
    DiscoveryInformation fileService = null; for (DiscoveryInformation service : services) { if (service.getCapability().equals(Constants.MYFILES_CAPABILITY)) { fileService = service; break; } }

Then for the desired service, the app does

Step 5: Get a token for the service by calling the Token URL and passing the service resource ID from step #4.

Step 6: Now the app is set to call the service using the service URL and the token from step #5.

    String sharepointResourceId = fileService.getServiceResourceId(); String endpointUrl = fileService.getServiceEndpointUri(); String sharepointUrl = endpointUrl.split("_api")[0]; FileClient fileClient = mApplication.getFileClient(DiscoveryFragment.this.getActivity(), sharepointResourceId, sharepointUrl).get();

## Quick Start for Exchange mail, calendar and contact apps ##

There are 2 apps in the samples folder that utilize the mail-calendar-contact sdk. 
* The mail app is a simple app that retrieves emails from the user's drafts folder. 
* The mail-calendar-contact app retrieves emails from the users drafts folder, sends email, retrieves events from the user's calendar and retrieves contacts from the user's contact list.

**The set up for both apps is given below.**

 1. Download the following code onto your machine: 
- [mail-app](https://github.com/OfficeDev/Office-365-SDK-for-Android/tree/master/samples/mail-app) or the [mail-calendar-contact-app](https://github.com/OfficeDev/Office-365-SDK-for-Android/tree/master/samples/mail-calendar-contacts-app)
- [Azure Active Directory Android library](https://github.com/MSOpenTech/azure-activedirectory-library-for-android) (AADAL).

 2. Add a dependency on ADAL from the mail-app.

 3. Download [Otto library](http://square.github.io/otto/) (this is only required for mail-calendar-contact app). Put it in the 'libs' folder and add to build path.

 4. Subscribe to a mail account from [here](http://msdn.microsoft.com/en-us/library/fp179924(v=office.15).aspx) or use an existing mail account.

 5. Modify the following in the ```Constants.java``` file. Please refer to [this](http://msdn.microsoft.com/en-us/library/dn605895(v=office.15).aspx) to understand how to obtain the values below.
```java
    // For example if the user name is foo@bar.com, then bar.com is the domain name.
    String DOMAIN = "Enter the domain for the user name"; 
    // For example "b1392c0b-a846-2ffb-eb20-1a982f58b936".
    String CLIENT_ID = "Grab this from the Azure management portal after you register your application";
    // For example http://bar.com 
    String REDIRECT_URL = "Grab this from the Azure management portal after you register your application";
    // For example like foo@bar.com 
    String USER_HINT = "Enter your login here";
```
 If you hit a JAR Mismatch issue with the 
 ```android-support-v4.jar``` please replace this jar in the AADAL libs folder with the latest one from the Android SDK  ```<SDK-root>/extras/android/support/v4/```.
 6. Run the application. User will be asked to enter his account details and all the mails from the drafts folder are retrieved.


## Running Mail-Calendar-Contact e2e Tests ##

Mail-Calendar-Contact e2e tests: 
- Connect to the [endpoint](https://github.com/OfficeDev/Office-365-SDK-for-Android/blob/master/tests/office365-mail-calendar-contact-sdk-e2etests/testing-project-it/pom.xml#L36) that should be provided in pom.xml. 
- Require authentication so [credentials](https://github.com/OfficeDev/Office-365-SDK-for-Android/blob/master/tests/office365-mail-calendar-contact-sdk-e2etests/testing-project-it/pom.xml#L37) should be provided.
- Require [authentication type](https://github.com/OfficeDev/Office-365-SDK-for-Android/blob/master/tests/office365-mail-calendar-contact-sdk-e2etests/testing-project-it/pom.xml#L43) to be specified. Available option are ```basic``` and ```aad```. ```basic``` is used by default.

```xml
	<serviceRootURL>https://outlook.office365.com/ews/odata</serviceRootURL>
	<serviceUsername>Enter your login here</serviceUsername>
	<servicePassword>Enter your password here</servicePassword>
	<serviceResourceId>Enter your resource ID here</serviceResourceId>
	<serviceClientId>Enter your client ID here</serviceClientId>
	<serviceAuthorityUrl>https://login.windows.net/common/oauth2/token</serviceAuthorityUrl>
	<serviceRedirectUrl>Enter your redirect URL here</serviceRedirectUrl>
	<serviceAuth>basic</serviceAuth>
```

Configuration should be made in the [testing project pom](https://github.com/OfficeDev/Office-365-SDK-for-Android/blob/master/tests/office365-mail-calendar-contact-sdk-e2etests/testing-project-it/pom.xml). If service endpoint and authentication credentials are provided follow instruction on running tests in [Exchange Wiki](https://github.com/OfficeDev/Office-365-SDK-for-Android/wiki/User-guide-(Exchange-SDK)#running-mail-calendar-contact-e2e-tests).

## Features ##
For the entire list of methods available in the SDK, please refer to the java docs under each SDK in the SDK folder.

## Tests ##

Apart from the sample apps, we also have end to end tests that demonstrate the use of the SDK. Please look at the tests folder under the root of the SDK.

##Contributing##

You will need to sign a [Contributor License Agreement](https://cla.msopentech.com/) before submitting your pull request. To complete the Contributor License Agreement (CLA), you will need to submit a request via the form and then electronically sign the Contributor License Agreement when you receive the email containing the link to the document. This needs to only be done once for any Microsoft Open Technologies OSS project.

## License ##
Copyright (c) Microsoft Open Technologies, Inc. All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
