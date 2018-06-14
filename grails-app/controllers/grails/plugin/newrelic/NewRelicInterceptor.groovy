package grails.plugin.newrelic

class NewRelicInterceptor {

    NewRelicService newRelicService

    NewRelicInterceptor() {
        matchAll()
    }

    boolean before() {
        if (controllerName) {
            newRelicService.setTransactionName(null, "/${controllerName}" + (actionName ? "/${actionName}" : "/"))
        } else {
            newRelicService.setTransactionName(null, request.getServletPath())
        }

        return true
    }

}
