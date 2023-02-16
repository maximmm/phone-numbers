package com.example.country

import com.example.country.core.ExceptionHandlerAdvice
import org.junit.Before
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Subject

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@WebMvcTest
class CountryControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @SpringBean
    private CountryService service = Stub(CountryService)

    @Subject
    def controller = new CountryController(service)

    @Before
    void setup() {
        this.mvc = standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerAdvice())
                .build()
    }

    def "should return country name"() {
        given:
        service.resolveCountryBy('+37129343536') >> 'Latvia'

        expect:
        mvc.perform(post("/country").content('+37129343536'))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString == 'Latvia'
    }

    def "should return 'bad request' with message "() {
        given:
        service.resolveCountryBy('abc') >> { throw new Exception() }

        expect:
        mvc.perform(post("/country").content('abc'))
                .andExpect(status().isBadRequest())
                .andReturn()
                .response
                .contentAsString == 'Something went wrong'

    }
}
