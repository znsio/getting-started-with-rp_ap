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

## Pre-requisites for executing App Tests

1. Install node-js and npm - https://docs.npmjs.com/downloading-and-installing-node-js-and-npm

2. To install Appium and other dependency - Execute `npm install`

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

#### Android

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
