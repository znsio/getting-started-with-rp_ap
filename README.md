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

## How the Dashboards look

### ReportPortal
TBA

### Applitools (Visual Testing)
TBA

## Execute Tests

### WebTests

`mvn clean test -DsuiteXmlFile=testng.xml -DCONFIG=src/test/resources/config/webConfig.properties`

### AppTests

#### [WIP] Android

`mvn clean test -DsuiteXmlFile=testng.xml -DCONFIG=src/test/resources/config/androidConfig.properties`
