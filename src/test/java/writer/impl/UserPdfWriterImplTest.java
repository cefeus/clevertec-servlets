package writer.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import dto.UserDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import util.UserTestBuilder;
import writer.Writer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserPdfWriterImplTest {

    private final Writer writer = new UserPdfWriterImpl();
    private final String PATH = "test.pdf";

    @SneakyThrows
    @Test
    void print_shouldContainWrittenValues() {
        // given
        UserDto first = UserTestBuilder.builder().build().buildUserDto();
        UserDto second = UserTestBuilder.builder()
                .withName("Пример имени")
                .withSurname("Пример фамилии")
                .withEmail("primer2@gmail.com")
                .withAge(14)
                .build().buildUserDto();
        List<UserDto> expected = List.of(first, second);
        // when
        writer.print(expected, PATH);
        // then
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(PATH));
        String actual = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(1), new LocationTextExtractionStrategy());
        assertThat(actual)
                .contains(first.name())
                .contains(first.surname())
                .contains(first.email())
                .contains(String.valueOf(first.age()))
                .contains(second.name())
                .contains(second.surname())
                .contains(second.email())
                .contains((String.valueOf(second.age())));
    }
}
