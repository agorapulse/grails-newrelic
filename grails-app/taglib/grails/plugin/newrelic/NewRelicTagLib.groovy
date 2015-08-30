package grails.plugin.newrelic

import grails.core.GrailsApplication
import grails.util.Environment
import com.newrelic.api.agent.NewRelic

class NewRelicTagLib {
    static namespace = "newrelic"

    GrailsApplication grailsApplication

    def browserTimingHeader = {
        if(enabled) {
            out << NewRelic.browserTimingHeader
        }
    }

    def browserTimingFooter = {
        if(enabled) {
            out << NewRelic.browserTimingFooter
        }
    }

    boolean isEnabled() {
        // default enabled for PROD
        boolean configEnabled = (Environment.current == Environment.PRODUCTION)

        // if config specified, use that instead
        if (grailsApplication.config.newrelic) {
            configEnabled = grailsApplication.config.newrelic.enabled
        }

        return configEnabled
    }
}
