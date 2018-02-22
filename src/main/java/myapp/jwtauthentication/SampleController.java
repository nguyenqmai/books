package myapp.jwtauthentication;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;

import com.google.api.client.util.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
public class SampleController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    com.google.api.services.calendar.Calendar calendarService;



    public SampleController() throws IOException {
//        calendarService = Quickstart.getCalendarService();
    }

    @RequestMapping(value = "/calendars", method = RequestMethod.GET)
    @ResponseBody
    String readEvents(@RequestHeader(value = "authorization") String authorization) throws IOException {
        // String token = authorization.replace("Bearer ", "");
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
//        try {
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secret"))
//                    .withIssuer("auth0")
//                    .build(); //Reusable verifier instance
//            DecodedJWT jwt = verifier.verify(token);
//        } catch (JWTVerificationException exception){
//            //Invalid signature/claims
//        }

//        com.google.api.services.calendar.Calendar service = Quickstart.getCalendarService();
//
//        // List the next 10 events from the primary calendar.
//        DateTime now = new DateTime(System.currentTimeMillis());
//        Events events = service.events().list("primary")
//                .setMaxResults(10)
//                .setTimeMin(now)
//                .setOrderBy("startTime")
//                .setSingleEvents(true)
//                .execute();
//        List<Event> items = events.getItems();
//        if (items.size() == 0) {
//            System.out.println("No upcoming events found.");
//        } else {
//            System.out.println("Upcoming events");
//            for (Event event : items) {
//                DateTime start = event.getStart().getDateTime();
//                if (start == null) {
//                    start = event.getStart().getDate();
//                }
//                System.out.printf("%s (%s)\n", event.getSummary(), start);
//            }
//        }
        return "OK";
    }

    @RequestMapping(value = "/calendars/{storeEmail}", method = RequestMethod.PUT)
    @ResponseBody
    String newCalendar(@RequestHeader(value = "authorization") String authorizationBearer,
                       @RequestParam(value = "name") String calendarName,
                       @RequestParam(value = "description") String calendarDescription,
                       @RequestParam(value = "timezone") String timezone) throws IOException {
        // Create a new calendar
//        com.google.api.services.calendar.model.Calendar calendar = new Calendar();
//        calendar.setSummary(calendarName);
//        calendar.setDescription(calendarDescription);
//        calendar.setTimeZone(timezone);
//
//// Insert the new calendar
//        Calendar createdCalendar = calendarService.calendars().insert(calendar).execute();
//
//        System.out.println(createdCalendar.getId());
//        return createdCalendar.getId();

        return calendarName;
    }

    @RequestMapping(value = "/calendars/{storeEmail}", method = RequestMethod.GET)
    @ResponseBody
    protected String readStoreEvents(@RequestHeader(value = "authorization") String authorizationBearer,
                                     @RequestParam(value = "name") String calendarName,
                                     @RequestParam("maxDate") @DateTimeFormat(pattern = "dd.MM.yyyy") DateTime maxDate
    ) throws IOException {
//        com.google.api.services.calendar.Calendar service = Quickstart.getCalendarService();

        // List the next 10 events from the primary calendar.
//        DateTime now = new DateTime(System.currentTimeMillis());
//        Events events = service.events().list(calendarName)
//                .setMaxResults(10)
//                .setTimeMin(now)
//                .setTimeMax(maxDate)
//                .setOrderBy("startTime")
//                .setSingleEvents(true)
//                .execute();
//        List<Event> items = events.getItems();
//        if (items.size() == 0) {
//            System.out.println("No upcoming events found.");
//        } else {
//            System.out.println("Upcoming events");
//            for (Event event : items) {
//                DateTime start = event.getStart().getDateTime();
//                if (start == null) {
//                    start = event.getStart().getDate();
//                }
//                System.out.printf("%s (%s)\n", event.getSummary(), start);
//            }
//        }
        return "OK";
    }
}