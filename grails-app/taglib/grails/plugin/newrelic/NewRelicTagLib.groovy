package grails.plugin.newrelic

import com.newrelic.api.agent.NewRelic
import grails.core.GrailsApplication
import grails.util.Environment
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

@CompileStatic
class NewRelicTagLib {

    static namespace = 'newrelic'

    GrailsApplication grailsApplication

    def browserTimingHeader = {
        if (enabled) {
            out << NewRelic.browserTimingHeader
        }
    }

    def browserTimingFooter = {
        if (enabled) {
            out << NewRelic.browserTimingFooter
        }
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    boolean isEnabled() {
        // default enabled for PROD
        boolean configEnabled = (Environment.current == Environment.PRODUCTION)

        // if config specified, use that instead
        if (grailsApplication.config.newrelic) {
            configEnabled = grailsApplication.config.newrelic.enabled as Boolean
        }

        return configEnabled
    }

}
