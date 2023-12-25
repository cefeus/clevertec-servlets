package controller.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import service.impl.UserServiceImpl;
import util.ReadProperties;
import validation.Validator;
import writer.Writer;
import writer.impl.UserPdfWriterImpl;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserService service = new UserServiceImpl();
    private ObjectMapper jsonMapper = new ObjectMapper();
    private Writer<UserDto> pdfWriter = new UserPdfWriterImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String needPdf = req.getParameter("pdf");
        String user;
        try {
            if (id == null) {
                String pageSize = req.getParameter("pagesize") == null ? ReadProperties.getPropertyByKey("PAGESIZE") : req.getParameter("pagesize");
                String page = req.getParameter("page") == null ? "0" : req.getParameter("page");
                user = jsonMapper.writeValueAsString(service.getAll(Integer.parseInt(pageSize), Integer.parseInt(pageSize) * Integer.parseInt(page)));
            } else {
                user = jsonMapper.writeValueAsString(service.get(UUID.fromString(id)));
            }
            if (needPdf.equalsIgnoreCase("true")) {
                createPdf(resp, user);
            }
            assembleResponse(resp, HttpServletResponse.SC_OK, user);
        } catch (RuntimeException e) {
            assembleResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void createPdf(HttpServletResponse resp, String source) throws IOException {
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"user.pdf\"");
        pdfWriter.print(source, resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String user = getBody(req);
            var userdto = jsonMapper.readValue(user, UserDto.class);
            Validator.validate(userdto);
            service.create(userdto);
            assembleResponse(resp, HttpServletResponse.SC_CREATED, "User created");
        } catch (RuntimeException e) {
            assembleResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String id = req.getParameter("id");
            String user = getBody(req);
            var userdto = jsonMapper.readValue(user, UserDto.class);
            Validator.validate(userdto);
            service.update(UUID.fromString(id), userdto);
            assembleResponse(resp, HttpServletResponse.SC_OK, "User updated");
        } catch (RuntimeException e) {
            assembleResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        try {
            if (id != null) {
                service.delete(UUID.fromString(id));
                assembleResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
            } else {
                assembleResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Id expected");
            }
        } catch (RuntimeException e) {
            assembleResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    public String getBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().sequential().reduce(System.lineSeparator(), String::concat);
    }

    private void assembleResponse(HttpServletResponse resp, int status, String responseBody) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.getWriter().print(responseBody);
    }
}
