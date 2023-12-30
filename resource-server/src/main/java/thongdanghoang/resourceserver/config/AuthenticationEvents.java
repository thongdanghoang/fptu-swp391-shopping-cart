package thongdanghoang.resourceserver.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class AuthenticationEvents {

    private final Log logger = LogFactory.getLog(getClass());

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        var token = (BearerTokenAuthentication) success.getAuthentication();
        var details = (WebAuthenticationDetails) token.getDetails();

        String audit = "User " + token.getTokenAttributes().get("id")
                + " logged in at " + convertToHumanReadable(success.getTimestamp())
                + " from " + details.getRemoteAddress() + ".";
        logger.info(audit);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        logger.info("Authentication failure: " + failures.getException().getMessage());
    }

    private String convertToHumanReadable(long timestamp) {
        // Convert the timestamp to Instant
        Instant instant = Instant.ofEpochMilli(timestamp);

        // Define a specific time zone asian/Ho_Chi_Minh
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");

        // Convert Instant to LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

        // Define the date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format LocalDateTime to a human-readable string
        return localDateTime.format(formatter);
    }
}