package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dto.UserDto;
import lombok.SneakyThrows;
import service.UserService;
import service.impl.UserServiceImpl;
import validation.Validator;
import writer.impl.UserPdfWriterImpl;

import java.util.UUID;

public class SimpleController {

    private UserService service;
    private ObjectMapper jsonMapper;
    private XmlMapper xmlMapper;
    private UserPdfWriterImpl writer = new UserPdfWriterImpl();

    public SimpleController() {
        service = new UserServiceImpl();
        jsonMapper = new ObjectMapper();
        xmlMapper = new XmlMapper();
    }

    /**
     * Retrieves a user by ID and returns it as a JSON string.
     *
     * @param id The ID of the user to retrieve.
     * @return JSON string representing the user.
     * @throws JsonProcessingException if JSON processing fails.
     */
    @SneakyThrows(JsonProcessingException.class)
    public String get(UUID id) {
        var user = service.get(id);
        writer.print(user, "user.pdf");
        return jsonMapper.writeValueAsString(user);
    }

    /**
     * Retrieves all users and returns them as a JSON string.
     *
     * @return A JSON string containing details of all users.
     * @throws JsonProcessingException If there's an issue while processing JSON.
     */
    @SneakyThrows(JsonProcessingException.class)
    public String getAll() {
        var users = service.getAll();
        writer.print(users, "users.pdf");
        return jsonMapper.writeValueAsString(users);
    }

    /**
     * Creates a new user from a JSON string representation.
     *
     * @param user A JSON string representing the user to be created.
     * @return A string indicating the success status.
     * @throws JsonProcessingException If there's an issue while processing JSON.
     */
    @SneakyThrows(JsonProcessingException.class)
    public String create(String user) {
        var save = jsonMapper.readValue(user, UserDto.class);
        Validator.validate(save);
        service.create(save);
        return "201";
    }

    /**
     * Updates an existing user identified by ID with data from a JSON string.
     *
     * @param id   The unique identifier of the user to be updated.
     * @param user A JSON string representing the updated user data.
     * @return A string indicating the success status.
     * @throws JsonProcessingException If there's an issue while processing JSON.
     */
    @SneakyThrows(JsonProcessingException.class)
    public String update(UUID id, String user) {
        var save = jsonMapper.readValue(user, UserDto.class);
        Validator.validate(save);
        service.update(id, save);
        return "201";
    }

    /**
     * Updates an existing user identified by ID with data from an XML string.
     *
     * @param id   The unique identifier of the user to be updated.
     * @param user An XML string representing the updated user data.
     * @return A string indicating the success status.
     * @throws JsonProcessingException If there's an issue while processing XML.
     */
    @SneakyThrows(JsonProcessingException.class)
    public String updateXML(UUID id, String user) {
        var save = xmlMapper.readValue(user, UserDto.class);
        Validator.validate(save);
        service.update(id, save);
        return "201";
    }

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id The unique identifier of the user to be deleted.
     * @return A string indicating the success status.
     */
    public String delete(UUID id) {
        service.delete(id);
        return "200";
    }
}
