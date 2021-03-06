package grails.plugin.newrelic

import grails.testing.services.ServiceUnitTest
import grails.util.Environment
import spock.lang.Specification

class NewRelicServiceSpec extends Specification implements ServiceUnitTest<NewRelicService> {

    def "should be enabled for PRODUCTION by default"() {
        when:
            setEnvironment(Environment.PRODUCTION)

        then:
            assert service.enabled == true
    }

    def "should be disabled for NON-PRODUCTION by default" () {
        when:
            setEnvironment(Environment.CUSTOM)

        then:
            assert service.enabled == false
    }

    def "should be enabled when config enables NewRelic" () {
        when:
            enableNewRelic(true)

        then:
            assert service.enabled == true
    }

    def "should be disabled for PRODUCTION when config disables NewRelic" () {
        when:
            setEnvironment(Environment.PRODUCTION)
            enableNewRelic(false)

        then:
            assert service.enabled == false
    }

    private setEnvironment(environment) {
        Environment.metaClass.static.getCurrent = { ->
            return environment
        }
    }

    private enableNewRelic(boolean value){
        config.newrelic = [
                enabled: value
        ]
    }
}
