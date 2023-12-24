package writer.impl;


import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import dto.UserDto;
import util.constants.ru.PdfUserConstants;
import writer.Writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static util.constants.ru.PdfUserConstants.BACKGROUND_PATH;
import static util.constants.ru.PdfUserConstants.ENCODING;
import static util.constants.ru.PdfUserConstants.FONT_PATH;
import static util.constants.ru.PdfUserConstants.SAVE_PATH;

public class UserPdfWriterImpl implements Writer<UserDto> {

    private PdfFont font;

    {
        initFont();
    }

    public void print(UserDto source, String path) {
        try (var document = new Document(new PdfDocument(new PdfReader(BACKGROUND_PATH), new PdfWriter(SAVE_PATH + path)));) {
            document.setFont(font);
            document.add(new Paragraph(build(source)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print(String source, OutputStream os) {
        try (var document = new Document(new PdfDocument(new PdfReader(BACKGROUND_PATH), new PdfWriter(os)));) {
            document.setFont(font);
            document.add(new Paragraph(source));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(List<UserDto> source, String path) {
        try (var document = new Document(new PdfDocument(new PdfReader(BACKGROUND_PATH), new com.itextpdf.kernel.pdf.PdfWriter(SAVE_PATH + path)));) {
            document.setFont(font);
            for (UserDto dto : source) {
                document.add(new Paragraph(build(dto)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initFont() {
        try {
            font = PdfFontFactory.createFont(FONT_PATH, ENCODING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String build(UserDto dto) {
        return PdfUserConstants.NAME
                + dto.name() + PdfUserConstants.NEW_LINE
                + PdfUserConstants.SURNAME + dto.surname() + PdfUserConstants.NEW_LINE
                + PdfUserConstants.EMAIL + dto.email() + PdfUserConstants.NEW_LINE
                + PdfUserConstants.AGE + dto.age() + PdfUserConstants.NEW_LINE;
    }
}
