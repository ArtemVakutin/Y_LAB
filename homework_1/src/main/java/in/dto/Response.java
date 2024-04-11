package in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import session.OperationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String responseBody;
    private OperationStatus operationStatus;
}
