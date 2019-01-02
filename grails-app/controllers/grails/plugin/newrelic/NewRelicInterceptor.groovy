package grails.plugin.newrelic

import org.grails.web.util.WebUtils

class NewRelicInterceptor {

    NewRelicService newRelicService

    NewRelicInterceptor() {
        matchAll()
    }

    boolean before() {
        if (request.getAttribute(WebUtils.EXCEPTION_ATTRIBUTE)) {
            // error controllers
            newRelicService.setTransactionName(null, request.forwardURI)
        } else if(controllerName) {
            newRelicService.setTransactionName(null, "/${controllerName}"+(actionName ? "/${actionName}" : "/"))
        } else {
            newRelicService.setTransactionName(null, request.getServletPath())
        }

        return true
    }

}
