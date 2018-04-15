Andromeda Blueprint Website
===========================

Attention! This is work in progress!

A Pippo application showing best practices as well as the use of the Andromeda extensions.
It is a complete example application featuring a couple of static pages rendered via template inheritance, a blog and two collections of articles - all three provided via the fragments library.
Furthermore it uses the pippo-contact-route library to easily enable a contact form and has a calendar powered by Google Calendar to display important events. Example use of internationalization, session handling and logging is thrown into the mix, too.
While it has all these features implemented and ready to use, you need to bring your own configuration, to enable some of them.
It uses the Open Source template [Flexor](https://bootstrapmade.com/flexor-free-multipurpose-bootstrap-template/).

## Features
The following list describes the features of Pippo, as well as the Andromeda extensions, which are used in this application.

### General
- Example [SLF4J](https://www.slf4j.org/) and [Logback](https://logback.qos.ch/) configuration. It uses the ConsoleAppender as well as the RollingFileAppender together with a TimeBasedRollingPolicy set to a maximum history of 30 days.
- Logging and information best practices. This means to write important configuration settings into the logfile directly after start-up of the application as well as before normally shutdown.

### Pippo
- Use of [PippoSettings](http://www.pippo.ro/doc/settings.html). All major constants are set via the pom and filtered into PippoSettings.
- Use of [Pebble Templates](http://www.pippo.ro/doc/templates/pebble.html). All HTML is templated via Pebble using template inheritance.
- Use of [Sessions](http://www.pippo.ro/doc/session.html). Sessions are handled for the email contact form. This is used to present an unfinished email, if an error occurs to just that user.
- TODO: I18N, Upload, Flash, Security

### Andromeda

#### Fragments
- Blog, Articles and I18N

#### Pippo Contact Route
- Email sending. Due to the sensitivity of the matter: Bring your own config! Also, never check your mail configuration into public git!

#### Andromeda Tools

##### Calendar
Filling a local calendar via Google Calendar. Bring your own Calendar.

##### Downloader
Used to download a copy of the aforementioned calendar.