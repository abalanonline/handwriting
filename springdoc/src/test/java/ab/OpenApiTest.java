package ab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OpenApiTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void getJson() throws Exception {
    MockHttpServletResponse openApiResponse = mvc.perform(MockMvcRequestBuilders.get("/v3/api-docs/"))
        .andReturn().getResponse();
    assertThat(openApiResponse.getStatus(), is(200));
    String openApiContent = openApiResponse.getContentAsString(StandardCharsets.UTF_8);
    assertThat(openApiContent.startsWith("{\"openapi\":\"3.0.1\","), is(true));
  }

  @Test
  public void getHtml() throws Exception {
    MockHttpServletResponse openApiRedirect = mvc.perform(MockMvcRequestBuilders.get("/swagger-ui.html"))
        .andReturn().getResponse();
    assertThat(openApiRedirect.getStatus(), is(302));

    MockHttpServletResponse openApiResponse = mvc
        .perform(MockMvcRequestBuilders.get(openApiRedirect.getHeader("location")))
        .andReturn().getResponse();
    assertThat(openApiResponse.getStatus(), is(200));
    String openApiContent = openApiResponse.getContentAsString(StandardCharsets.UTF_8);
    assertThat(openApiContent.contains("<html"), is(true));
  }

}
