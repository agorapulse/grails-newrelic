package grails.newrelic

import grails.plugins.Plugin
import groovy.transform.CompileStatic

@CompileStatic
class NewrelicGrailsPlugin extends Plugin {

    def grailsVersion = '3.3.0 > *'

    def title = 'Newrelic Plugin'
    def author = 'Benoit Hediard'
    def authorEmail = 'ben@agorapulse.com'
    def description = '''\
Newrelic plugin for Grails 3.0.
'''
    def profiles = ['web']

    def documentation = 'https://github.com/agorapulse/grails-newrelic'
    def license = 'APACHE'
    def organization = [name: 'AgoraPulse', url: 'http://www.agorapulse.com/']
    def issueManagement = [system: 'github', url: 'https://github.com/agorapulse/grails-newrelic/issues']
    def developers = [[name: 'C.P. Lim', email: 'c.p.lim@sensis.com.au']]
    def scm = [url: 'https://github.com/agorapulse/grails-newrelic']

}
