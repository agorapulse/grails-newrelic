package grails.plugin.newrelic


import grails.testing.web.taglib.TagLibUnitTest
import grails.util.Environment
import spock.lang.Specification

class NewRelicTagLibSpec extends Specification implements TagLibUnitTest<NewRelicTagLib> {

    def "should be enabled for PRODUCTION by default"() {
        when:
            setEnvironment(Environment.PRODUCTION)

        then:
            tagLib.enabled
    }

    def "should be disabled for NON-PRODUCTION by default"() {
        when:
            setEnvironment(Environment.CUSTOM)

        then:
            !tagLib.enabled
    }

    def "should be enabled when config enables NewRelic"() {
        when:
            enableNewRelic(true)

        then:
            tagLib.enabled
    }

    def "should be disabled for PRODUCTION when config disables NewRelic"() {
        when:
            setEnvironment(Environment.PRODUCTION)
            enableNewRelic(false)

        then:
            !tagLib.enabled
    }

    private setEnvironment(environment) {
        Environment.metaClass.static.getCurrent = { ->
            return environment
        }
    }

    private enableNewRelic(boolean value) {
        config.newrelic = [
                enabled: value
        ]
    }
}
