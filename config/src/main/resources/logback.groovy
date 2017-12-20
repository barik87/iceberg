appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%date %level [%logger{0}]: %msg%n"
    }
}
appender("ReportPortalAppender", org.iceberg.reportportal.logback.appender.ReportPortalAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level [%logger{0}]: %msg%n"
    }
}
root(ch.qos.logback.classic.Level.INFO, ["STDOUT", "ReportPortalAppender"])