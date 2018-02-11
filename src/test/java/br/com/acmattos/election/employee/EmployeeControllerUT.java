package br.com.acmattos.election.employee;

import br.com.acmattos.election.UnitTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

   private EmployeeVO vo;

   private Employee entity;

    @Before
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
      CredentialVO credentialVO = CredentialVO.builder()
              .id(3L)
              .username("username")
              .password("password")
              .enabled(true)
              .build();
      this.vo = EmployeeVO.builder()
              .id(1L)
              .employeeId(2)
              .name("name")
              .email("email")
              .credential(credentialVO)
              .build();
      this.entity = EmployeeVO.toEntity(this.vo);
    }

    @Test
   public void create_v1_invalidVO() throws Exception {
        EmployeeVO vo = null;
//      Recurso recurso = getRecurso();
//      List<Recurso> recursos = new ArrayList<Recurso>(){{ add(recurso);}};
//

      this.mvc.perform(post("/v1/employees")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
//         .content(objectMapper.writeValueAsString(null)))
         .content("{}"))
                .andExpect(status().isBadRequest())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//         .andExpect(jsonPath("$", hasSize(1)))
//         .andExpect(jsonPath("$[0].id", is(1)))
//         .andExpect(jsonPath("$[0].descricao", is("descricao")));
//      verify(recursoRepository, times(1))
//         .findAllRecursosNaoVotadosPorFuncionario(anyLong());
//      verifyNoMoreInteractions(recursoRepository)
 ;
    }

   @Test
   public void create_v1_validVOButConstraintViolation() throws Exception {
//      CredentialVO credentialVO = CredentialVO.builder()
//               .id(6L)
//               .username("username")
//               .password("password")
//               .enabled(true)
//               .build();
//      EmployeeVO vo = EmployeeVO.builder()
//               .id(4L)
//               .employeeId(5)
//               .name("name")
//               .email("email")
//               .credential(credentialVO)
//               .build();
      doThrow(DataIntegrityViolationException.class).when(repository).save(any(Employee.class));
      this.mvc.perform(post("/v1/employees")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(this.vo)))
         .andExpect(status().isConflict())
         //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         //.andExpect(jsonPath("$", hasSize(1)))
         .andExpect(header().string("location", not(containsString("/v1/employees/"))))
         .andExpect(header().string("warning", containsString("Data already exists!")))
//         .andExpect(jsonPath("$[0].descricao", is("descricao")));
//      verify(recursoRepository, times(1))
//         .findAllRecursosNaoVotadosPorFuncionario(anyLong());
//      verifyNoMoreInteractions(recursoRepository)
      ;
   }

   @Test
   public void create_v1_DoneSuccessfully() throws Exception {
//      CredentialVO credentialVO = CredentialVO.builder()
//               .id(6L)
//               .username("username")
//               .password("password")
//               .enabled(true)
//               .build();
//      EmployeeVO vo = EmployeeVO.builder()
//               .id(4L)
//               .employeeId(5)
//               .name("name")
//               .email("email")
//               .credential(credentialVO)
//               .build();
      when(repository.save(any(Employee.class))).thenReturn(this.entity);
      this.mvc.perform(post("/v1/employees")
              .contentType(MediaType.APPLICATION_JSON_UTF8)
              .content(objectMapper.writeValueAsString(this.vo)))
              .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(header().string("location", containsString("/v1/employees/")))
              .andExpect(header().string("warning", not(containsString("Data already exists!"))))
              .andExpect(jsonPath("$.id", is(1)))
              .andExpect(jsonPath("$.name", is("name")))
              .andExpect(jsonPath("$.credential.id", is(3)))
      ;
      verify(repository, times(1)).save(any(Employee.class));
      verifyNoMoreInteractions(repository);
   }

   @Test
   public void read_v1_NoEntitiesFound() throws Exception {
      List<Employee> entities = new ArrayList<Employee>();
      when(repository.findAll()).thenReturn(entities);
      this.mvc.perform(get("/v1/employees"))
              .andExpect(status().isNotFound())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(header().string("location", not(containsString("/v1/employees/"))))
              .andExpect(jsonPath("$", hasSize(0)))
      ;
      verify(repository, times(1)).findAll();
      verifyNoMoreInteractions(repository);
   }

   @Test
   public void read_v1_DoneSuccessfully() throws Exception {
//      CredentialVO credentialVO = CredentialVO.builder()
//               .id(6L)
//               .username("username")
//               .password("password")
//               .enabled(true)
//               .build();
//      EmployeeVO vo = EmployeeVO.builder()
//               .id(4L)
//               .employeeId(5)
//               .name("name")
//               .email("email")
//               .credential(credentialVO)
//               .build();
      Employee entity = this.entity;
      List<Employee> entities = new ArrayList<Employee>() {{ add(entity); }};
      when(repository.findAll()).thenReturn(entities);
      this.mvc.perform(get("/v1/employees"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(header().string("location", not(containsString("/v1/employees/"))))
              .andExpect(jsonPath("$", hasSize(1)))
              .andExpect(jsonPath("$[0].id", is(1)))
              .andExpect(jsonPath("$[0].name", is("name")))
              .andExpect(jsonPath("$[0].credential.id", is(3)))
      ;
      verify(repository, times(1)).findAll();
      verifyNoMoreInteractions(repository);
   }

   @Test
   public void readOne_v1_NoEntityFound() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(null);
      this.mvc.perform(get("/v1/employees/2"))
              .andExpect(status().isNotFound())
              //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(header().string("location", not(containsString("/v1/employees/2"))))
              //.andExpect(jsonPath("$", hasSize(0)))
      ;
      verify(repository, times(1)).findOne(anyLong());
      verifyNoMoreInteractions(repository);
   }

   @Test
   public void readOne_v1_DoneSuccessfully() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(entity);
      this.mvc.perform(get("/v1/employees/1"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(header().string("location", not(containsString("/v1/employees/1"))))
              .andExpect(jsonPath("$.id", is(1)))
              .andExpect(jsonPath("$.name", is("name")))
              .andExpect(jsonPath("$.credential.id", is(3)))
      ;
      verify(repository, times(1)).findOne(anyLong());
      verifyNoMoreInteractions(repository);
   }


   @Test
   public void update_NotFoundEntity() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(null);
      this.mvc.perform(put("/v1/employees")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(this.vo)))
         .andExpect(status().isNotFound())
;
      verify(repository, times(1)).findOne(anyLong());
      verify(repository, times(0)).save(any(Employee.class));
      verifyNoMoreInteractions(repository);

   }

//} catch(DataIntegrityViolationException dive){
//    85             //TODO Tratamento adequado de email nao unico
//    86             dive.printStackTrace();
//    87             builder.key("Warning").value("Data already exists!").CONFLICT();
//    88          }
   @Test
   public void update_v1_validVOButConstraintViolation() throws Exception {
      doThrow(DataIntegrityViolationException.class).when(repository).save(any(Employee.class));
      this.mvc.perform(put("/v1/employees")
       .contentType(MediaType.APPLICATION_JSON_UTF8)
       .content(objectMapper.writeValueAsString(this.vo)))
       .andExpect(status().isConflict())
       //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
       //.andExpect(jsonPath("$", hasSize(1)))
       .andExpect(header().string("location", not(containsString("/v1/employees/"))))
       .andExpect(header().string("warning", containsString("Data already exists!")))
//         .andExpect(jsonPath("$[0].descricao", is("descricao")));
//      verify(recursoRepository, times(1))
//         .findAllRecursosNaoVotadosPorFuncionario(anyLong());
//      verifyNoMoreInteractions(recursoRepository)
   ;
}

   @Test
   public void update_DoneSuccessfully() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(entity);
      when(repository.save(any(Employee.class))).thenReturn(entity);
      this.mvc.perform(put("/v1/employees")
              .contentType(MediaType.APPLICATION_JSON_UTF8)
              .content(objectMapper.writeValueAsString(this.vo)))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(jsonPath("$.id", is(1)))
              .andExpect(jsonPath("$.name", is("name")))
              .andExpect(jsonPath("$.credential.id", is(3)))
              ;
      verify(repository, times(1)).findOne(anyLong());
      verify(repository, times(1)).save(any(Employee.class));
      verifyNoMoreInteractions(repository);

   }
   @Test
   public void delete_NotFoundEntity() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(null);
      this.mvc.perform(delete("/v1/employees/10"))
              .andExpect(status().isNotFound())
              //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              //.andExpect(jsonPath("$.id", is(funcionario.getId().intValue())))
              //.andExpect(jsonPath("$.nome", is("deletar")))
       ;

      verify(repository, times(1)).findOne(anyLong());
      verify(repository, times(0)).delete(anyLong());
      verifyNoMoreInteractions(repository);

   }
   @Test
   public void delete_DoneSuccessfully() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(this.entity);
      doNothing().when(repository).delete(anyLong());
      this.mvc.perform(delete("/v1/employees/1"))
              .andExpect(status().isOk())
      //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      //.andExpect(jsonPath("$.id", is(funcionario.getId().intValue())))
      //.andExpect(jsonPath("$.nome", is("deletar")))
      ;

      verify(repository, times(1)).findOne(anyLong());
      verify(repository, times(1)).delete(anyLong());
      verifyNoMoreInteractions(repository);

   }
}