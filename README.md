# getting-started-with-rp_ap

Sample Web and App tests to show how to use [ReportPortalIntegration](https://github.com/znsio/ReportPortalIntegration)
and [ApplitoolsIntegration](https://github.com/znsio/ApplitoolsIntegration) utilities in a Java-TestNG automation
framework

## Tech stack used

* [JAVA JDK-11](https://cucumber.io)
* [TestNG 7.8.x](https://testng.org/doc/)
* [Appium 2.x](https://appium.io)
    * https://javadoc.io/doc/io.appium/java-client/8.0.0-beta/deprecated-list.html
* [Selenium WebDriver 4.x](https://selenium.dev)
    * https://www.selenium.dev/selenium/docs/api/java/deprecated-list.html
* [Reportportal](https://reportportal.io)
* [Applitools](https://applitools.com)
* Build tool: [Maven](https://maven.apache.org/)

## Documentation for integration with ReportPortal and Applitools

* [ReportPortalPortal](https://github.com/znsio/ReportPortalIntegration/blob/main/README.md)
* [ApplitoolsIntegration](https://github.com/znsio/ApplitoolsIntegration/blob/main/README.md)

## Pre-requisite for Apps 
**Steps To Install Appium npm and Node.js** 

1. Before installing the Appium we need to install the Node.js and npm

2. Navigate to the node.js site: Go to the official Node.js website: https://nodejs.org/

3. Download the installer: On the Node.js homepage, you'll see two versions available for download: LTS (Long Term Support) and Current. It's recommended to download the LTS version for a stable environment.

4. Run the installer: Double-click the downloaded installer to run it.

5. Follow the installation process: The Node.js installer will guide you through the installation process. Accept the license agreement, choose the installation location (or keep the default), and make sure to check the box that says "Automatically install the npm package manager."

6. Complete the installation: Click "Next" and then "Install" to complete the installation. Node.js and npm will be installed on your system.

7. Verify the installation: Open a command prompt (search for "Command Prompt" or "cmd" in the start menu), and type the following commands to verify the installation:

   **node -v**

   **npm -v**


**How to Install Appium**

1. Now we have installed npm -v: 10.1.0 & node -v: 20.7.0

2. Install Appium globally: npm install -g appium

3. The -g flag installs Appium globally, making it accessible from any directory.

4. Check the installation: appium --version : 2.1.3

5. Now we need to use appium drivers to automate Android and IOS apps: Execute the command to see all available driver list: appium driver list

6. Install the necessary drivers:


    For Android: appium driver install uiautomator2
    For IOS: appium driver install xcuitest


## Build

`mvn clean install -DskipTests`
> If facing issues with dependencies not being resolved from https://jitpack.io, then check the `settings.xml` file
> you're using for building your maven projects. If you've proxies configured in the same, then make sure `jitpack.io`
> is part of `nonProxyHosts` configuration. For instance

```xml

<proxy>
    <id>httpmyproxy</id>
    <active>true</active>
    <protocol>http</protocol>
    <host>someHost</host>
    <port>8080</port>
    <username>UserName</username>
    <password>Password</password>
    <nonProxyHosts>*.google.com|*jitpack.io</nonProxyHosts>
</proxy>
```

## Execute Tests

### WebTests

`mvn clean test -DsuiteXmlFile=TestNG/webTestng.xml -DCONFIG=src/test/resources/config/webConfig.properties`

### AppTests

#### [WIP] Android
`mvn clean test -DsuiteXmlFile=TestNG/androidTestng.xml -DCONFIG=src/test/resources/config/androidConfig.properties`

## How the Dashboards look

### ReportPortal
#### Launch Page/Dashboard
<img width="1728" alt="RP_LaunchPage" src="https://github.com/znsio/getting-started-with-rp_ap/assets/121366435/fd24cac2-8e90-48ea-b9e0-0db162281e1b">

#### Test Name Page
<img width="1727" alt="RP_TestNamePage" src="https://github.com/znsio/getting-started-with-rp_ap/assets/121366435/ec9d7cf8-c3e5-42e9-aa94-750bf3e1f7a1">

#### Test Execution/Logs Page
<img width="1728" alt="RP_TestExecutionPage" src="https://github.com/znsio/getting-started-with-rp_ap/assets/121366435/929abb61-dfc2-4e36-80a2-27c6d596c669">

### Applitools (Visual Testing)
#### Test Execution/Dashboard
<img width="1727" alt="AP_TestExecutionPage" src="https://github.com/znsio/getting-started-with-rp_ap/assets/121366435/7852f86a-d490-4b7b-82ab-f775866d75cf">

#### Batch Details
<img width="364" alt="AP_BatchPage" src="https://github.com/znsio/getting-started-with-rp_ap/assets/121366435/0f7fbaba-a50c-4787-954e-bdb7e2116be2">
