package grails.newrelic

import grails.plugins.*

class NewrelicGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Newrelic Plugin"
    def author = "Benoit Hediard"
    def authorEmail = "ben@agorapulse.com"
    def description = '''\
Newrelic plugin for Grails 3.0.
'''
    def profiles = ['web']

    def documentation = "https://github.com/agorapulse/grails-newrelic"
    def license = "APACHE"
    def organization = [ name: "AgoraPulse", url: "http://www.agorapulse.com/" ]
    def issueManagement = [ system: "github", url: "https://github.com/agorapulse/grails-newrelic/issues" ]
    def developers = [ [ name: "C.P. Lim", email: "c.p.lim@sensis.com.au" ]]
    def scm = [  url: "https://github.com/agorapulse/grails-newrelic" ]

}
