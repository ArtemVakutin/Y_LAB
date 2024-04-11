package out;

import in.dto.Response;

public class OutputWriter {
    public static void writeResponse(Response response){
        System.out.println(response.getResponseBody());
    }
}
