NewRelic Grails Plugin
======================

[![Build Status](https://travis-ci.org/agorapulse/grails-newrelic.svg)](https://travis-ci.org/agorapulse/grails-newrelic)

# Introduction

This plugin will make [New Relic](http://newrelic.com) instrumentation available to a Grails project.  
It is a port to Grails 3.X of the original [NewRelic Grails Plugin](https://github.com/Sensis/grails-newrelic-plugin/), originally written by [CP Lim](https://github.com/cplim).

**Grails NewRelic Plugin** provides the following Grails artefacts:

* **NewRelicInterceptor** - An interceptor matching all requests to automatically name transactions as `{controllerName}/{actionName}`.
* **NewRelicService** - A client to call [NewRelic Java agent API](https://docs.newrelic.com/docs/agents/java-agent/custom-instrumentation/java-agent-api), which is a wrapper around [NewRelic API java library](http://newrelic.github.io/java-agent-api/javadoc/com/newrelic/api/agent/NewRelic.html).
* **NewRelicTagLib** - A collection of tags to easily integrate [NewRelic Browser](http://newrelic.com/browser-monitoring) in your GSPs for Real User Monitoring (RUM).

New Relic needs to be installed on the running application server in order for the plugin to work. 
This is [extensively documented](https://newrelic.com/docs/java/java-agent-installation) by the New Relic team.
Once installed, New Relic Browser will need to be configured for [manual instrumentation](https://docs.newrelic.com/docs/agents/java-agent/instrumentation/page-load-timing-java#manual_instrumentation) .


# Installation

Declare the plugin dependency in the _build.gradle_ file, as shown here:

```groovy
repositories {
    ...
    maven { url "http://dl.bintray.com/agorapulse/plugins" }
}
dependencies {
    ...
    compile "org.grails.plugins:newrelic:3.25.0"
}
```

# Config

By default the New Relic RUM code will only be enabled for Production environments. 
If you need it to be enabled for other environments, make sure that it is explicitly enabled in your configs

```yml
newrelic:
    enabled: true
```

# Usage

Once New Relic and this plugin has been added to your web application, you are ready to add the tags to your page(s).  
New Relic provides some [best practices](https://docs.newrelic.com/docs/agents/java-agent/instrumentation/page-load-timing-best-practices-java) on when to all these tag methods.  
Ideally, you would only need to add it to your layout page(s) as follows:

```jsp
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <newrelic:browserTimingHeader/>
    <!-- other tags -->
</head>
<body>
    <!-- more tags -->
    <newrelic:browserTimingFooter/>
</body>
</html>
```

However, if there are more GSP that need these tags, then just make sure they are added at the appropriate locations in the DOM.

# Enabling NewRelic for Development

NewRelic should be enabled in the production environment as per the instructions [here](https://newrelic.com/docs/java/java-agent-installation), but if you need to enable this in other environments, make sure that the configs have enabled NewRelic for your environment, and add the following to your GRAILS_OPT environment

```bash
export GRAILS_OPTS="-javaagent:/path/to/newrelic.jar"
```

The next time you execute 'run-app' or 'run-war', NewRelic instrumentation code will be included in your generated HTML pages.

On newer versions of grails that use a forked jvm, you may need to include the java agent in your tomcat configuration. This is in BuildConfig.groovy.

```groovy
grails.tomcat.jvmArgs = ["-javaagent:/path/to/newrelic.jar"]
```

# BONUS - Installing and configuring NewRelic on AWS Elastic Beanstalk

Here are some instructions to install/configure NewRelic app AND server monitoring on AWS ElasticBeanstalk.
It will also call the NewRelic deployment API each time you start a new env.

1- Create a folder `src/main/webapp/.ebextensions`, a folder `src/main/webapp/.ebextensions/files` and add the `newrelic.jar` in it.

2- Create a file `src/main/webapp/.ebextensions/files/newrelic.yml.sh` (to dynamically generate newrelic.yml based on app env properties)

```bash
cat << EOF
common: &default_settings
license_key: '$NR_LICENSE'
enable_auto_transaction_naming: false
app_name: $NR_APPNAME
EOF
```

3- Create a file `src/main/webapp/.ebextensions/newrelic.sh`

```bash
#!/bin/sh
# New Relic (Application monitoring)
mkdir /var/lib/newrelic
mv ./.ebextensions/files/newrelic*.jar /var/lib/newrelic/
bash ./.ebextensions/files/newrelic.yml.sh > /var/lib/newrelic/newrelic.yml

# New Relic Agent (Server monitoring)
rpm -Uvh https://yum.newrelic.com/pub/newrelic/el5/x86_64/newrelic-repo-5-3.noarch.rpm
yum -y install newrelic-sysmond
/usr/sbin/nrsysmond-config –set license_key=$NR_LICENSE
/etc/init.d/newrelic-sysmond start

# New Relic deployment event
export AP_VERSION=`` `cat ./META-INF/grails.build.info | grep info.app.version | cut -d= -f2` ``
java -jar /var/lib/newrelic/newrelic.jar deployment –revision=$AP_VERSION
```

4- Create a file `src/main/webapp/.ebextensions/app.config`

```
container_commands:
  newrelic:
    command: "bash -x .ebextensions/newrelic.sh"
```

Then, in your Beanstalk app config options, add `-javaagent:/var/lib/newrelic/newrelic.jar` to the JVM command line parameter and set `NR_LICENSE` and `NR_APPNAME` env properties.

# Bugs

To report any bug, please use the project [Issues](http://github.com/agorapulse/grails-newrelic/issues) section on GitHub.
