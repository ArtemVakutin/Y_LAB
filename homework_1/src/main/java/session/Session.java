package session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.DefaultUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private DefaultUser user;

}
