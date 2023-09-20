# getting-started-with-rp_ap
Sample tests to show how to use ReportPortalIntegration and ApplitoolsIntegration utilities

## Build
`mvn clean install -DskipTests`
> If facing issues with dependencies not being resolved from https://jitpack.io, then check the `settings.xml` file you're using for building your maven projects. If you've proxies configured in the same, then make sure `jitpack.io` is part of `nonProxyHosts` configuration. For instance
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
`mvn clean test -DsuiteXmlFile=testng.xml -DCONFIG=src/test/resources/config/webConfig.properties`
### AppTests
#### Android
`mvn clean test -DsuiteXmlFile=testng.xml -DCONFIG=src/test/resources/config/androidConfig.properties`
