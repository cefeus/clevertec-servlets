import config.db.FlywayConfig;
import controller.SimpleController;
import lombok.val;

import java.util.UUID;

public class Main {

    private static final String JSON_USER = "{\"name\":\"Peter\",\"surname\":\"Parker\",\"email\":\"peterparker@gmail.com\",\"age\":25}";
    private static final String JSON_UPDATE_USER = "{\"name\":\"Petr\",\"surname\":\"Park\",\"email\":\"peterp@gmail.com\",\"age\":25}";
    private static final String XML_UPDATE_USER = "<UserDto><name>Petr</name><surname>Parer</surname><email>peter@gmail.com</email><age>24</age></UserDto>";
    private static final String EXISTING_UUID = "2565d11d-b07d-4049-a24f-40e7a9d2ff3b";
    private static final UUID ID = UUID.fromString(EXISTING_UUID);

    public static void main(String[] args) {
        val flywayConfig = new FlywayConfig();
        flywayConfig.flywayTemplate().migrate();
        val controller = new SimpleController();
        System.out.println(controller.get(ID));
        System.out.println(controller.getAll());
        System.out.println(controller.create(JSON_USER));
        System.out.println(controller.updateXML(ID, XML_UPDATE_USER));
        System.out.println(controller.update(ID, JSON_UPDATE_USER));
        System.out.println(controller.delete(ID));
    }

}
