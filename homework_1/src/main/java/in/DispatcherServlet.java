package in;

import in.dto.Request;
import in.dto.Response;
import lombok.extern.slf4j.Slf4j;
import model.Audit;
import model.DefaultUser;
import service.*;
import session.OperationStatus;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
public class DispatcherServlet {
    private static DispatcherServlet instance;
    private final HelpService helpService = new HelpServiceImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final AuthenticationService authenticationService = new AuthenticationServiceImpl();
    private final TrainingService trainingService = new TrainingServiceImpl();
    private final TrainingTypeService trainingTypeService = new TrainingTypeServiceImpl();
    private final StatisticService statisticService = new StatisticServiceImpl();
    private final AuditService auditService = new AuditServiceImpl();


    private DispatcherServlet() {
    }

    public static DispatcherServlet getInstance() {
        if (instance == null) {
            instance = new DispatcherServlet();
        }
        return instance;
    }

    public Response service(Request request) {
        String responseBody = "";
        Response response = new Response();

        String[] strings = RequestBodyParser.parseRequest(request.requestBody());
        log.debug(Arrays.toString(strings));

        try {
            if (request.user() == null) {
                responseBody = switchNoAuth(request);
            } else if (request.user().getRole().equals(DefaultUser.Role.ADMIN)) {
                responseBody = switchAdmin(request);
            } else {
                responseBody = switchUser(request);
            }
            response.setResponseBody(responseBody);
            response.setOperationStatus(OperationStatus.SUCCESS);
        } catch (Exception e) {
            response.setResponseBody(e.getMessage());
            response.setOperationStatus(OperationStatus.FAIL);
        }

        saveAudit(request, response);

        return response;
    }

    private String switchNoAuth(Request request) {
        return switch (request.operation().toUpperCase()) {
            case "HELP" -> helpService.help();
            case "REG" -> registrationService.registerUser(request);
            case "AUTH" -> authenticationService.authenticate(request);
            default -> helpService.help();
        };
    }

    private String switchAdmin(Request request) {
        return switch (request.operation().toUpperCase()) {
            case "LOGOUT" -> authenticationService.logout();
            case "FINDALL" -> registrationService.getAllUsers(request);
            case "AUTHBYUSER" -> authenticationService.authByUser(request);
            case "AUDIT" -> auditService.findAllAudit();
            default -> helpService.helpAdmin();
        };
    }

    private String switchUser(Request request) {
        return switch (request.operation().toUpperCase()) {
            case "LOGOUT" -> authenticationService.logout();
            case "CREATETR" -> trainingService.createTraining(request);
            case "UPDATETR" -> trainingService.updateTraining(request);
            case "DELETETR" -> trainingService.deleteTraining(request);
            case "GETTR" -> trainingService.getTraining(request);
            case "GETTRS" -> trainingService.getTrainings(request);
            case "CREATETYPE" -> trainingTypeService.createTrainingType(request);
            case "DELETETYPE" -> trainingTypeService.deleteTrainingType(request);
            case "STAT" -> statisticService.getStatistic(request);
            default -> helpService.helpUser();
        };
    }

    private void saveAudit(Request request, Response response) {
        auditService.addAudit(new Audit(request.user(),
                request.operation(),
                response.getOperationStatus(),
                LocalDateTime.now()));
    }

}
