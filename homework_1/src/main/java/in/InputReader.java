package in;

import in.dto.Request;
import in.dto.Response;
import lombok.extern.slf4j.Slf4j;
import model.DefaultUser;
import out.OutputWriter;
import session.Operation;
import session.OperationStatus;
import session.Session;
import session.SessionManager;

import java.util.Scanner;

@Slf4j
public class InputReader {

    private static InputReader instance;
    DispatcherServlet dispatcherServlet = DispatcherServlet.getInstance();
    private final Scanner scanner = new Scanner(System.in);



    private InputReader() {}

    public static InputReader getInstance() {
        if (instance == null) {
            instance = new InputReader();
        }
        return instance;
    }

    public void readLines() {
        while (true) {
            String requestBody = scanner.nextLine().replaceAll("\\s+", " ");
            log.debug("REQUEST BODY IS : {}", requestBody);

            Request request = createRequest(requestBody);

            log.debug("REQUEST IS : {}", request);

            Response response = dispatcherServlet.service(request);

            log.debug("RESPONSE IS : {}", response);

            OutputWriter.writeResponse(response);
        }
    }

    private Request createRequest(String requestBody) {
        String operation = "HELP";
        Session session = SessionManager.getSession();
        DefaultUser user = session.getUser();

        String[] s = requestBody.split(" ");

        if (s.length > 0) {
            try {
               operation = s[0].toUpperCase();
               s[0] = "";
               requestBody = String.join(" ", s).trim();

            } catch (IllegalArgumentException ignored) {
            }
        }
        return new Request(requestBody, operation, user);
    }
}
