package br.com.acmattos.election.employee;

import br.com.acmattos.election.UnitTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author acmattos
 * @since 18/01/2018
 * TODO JAVADOC, CHECK, UNIT TEST
 */
@WebAppConfiguration
public class EmployeeControllerUT extends UnitTest {
    @InjectMocks
    private EmployeeController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private EmployeeRepository repository;

    private MockMvc mvc;

    @Before
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void create_v1_nullVO() throws Exception {
        EmployeeVO vo = null;
//      Recurso recurso = getRecurso();
//      List<Recurso> recursos = new ArrayList<Recurso>(){{ add(recurso);}};
//
//      when(recursoRepository.findAllRecursosNaoVotadosPorFuncionario(anyLong())).thenReturn(recursos);
      this.mvc.perform(post("/v1/employees")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
//         .content(objectMapper.writeValueAsString(null)))
         .content("{}"))
         .andExpect(status().isOk())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//         .andExpect(jsonPath("$", hasSize(1)))
//         .andExpect(jsonPath("$[0].id", is(1)))
//         .andExpect(jsonPath("$[0].descricao", is("descricao")));
//      verify(recursoRepository, times(1))
//         .findAllRecursosNaoVotadosPorFuncionario(anyLong());
//      verifyNoMoreInteractions(recursoRepository)
 ;
    }


}