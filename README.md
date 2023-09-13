# getting-started-with-rp_ap
Sample tests to show how to use ReportPortalIntegration and ApplitoolsIntegration utilities

## Build
`mvn clean install -DskipTests `

## Execute Tests
### WebTests
` mvn clean test -DsuiteXmlFile=testng.xml -DCONFIG=src/test/resources/config/webConfig.properties`
