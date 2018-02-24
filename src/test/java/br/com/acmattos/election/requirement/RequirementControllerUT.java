package br.com.acmattos.election.requirement;

import br.com.acmattos.election.UnitTest;
import br.com.acmattos.election.requirement.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author acmattos
 * @since 11/02/2018
 */
public class RequirementControllerUT extends UnitTest {
   @InjectMocks
   private RequirementController controller;
   
   @Autowired
   private ObjectMapper objectMapper;
   
   @Mock
   private RequirementRepository repository;
   
   private MockMvc mvc;
   
   private RequirementVO vo;
   
   private Requirement entity;
   
   @Before
   public void setup() throws Exception {
      this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
      this.vo = RequirementVO.builder()
         .id(1L)
         .description("description")
         .build();
      this.entity = RequirementVO.toEntity(this.vo);
   }
   
   @Test
   public void create_v1_invalidVO() throws Exception {
      RequirementVO vo = null;
//      Recurso recurso = getRecurso();
//      List<Recurso> recursos = new ArrayList<Recurso>(){{ add(recurso);}};
//
      
      this.mvc.perform(post("/v1/requirements")
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
//      RequirementVO vo = RequirementVO.builder()
//               .id(4L)
//               .requirementId(5)
//               .name("name")
//               .email("email")
//               .credential(credentialVO)
//               .build();
      doThrow(DataIntegrityViolationException.class).when(repository).save(any(Requirement.class));
      this.mvc.perform(post("/v1/requirements")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(this.vo)))
         .andExpect(status().isConflict())
         //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         //.andExpect(jsonPath("$", hasSize(1)))
         .andExpect(header().string("location", not(containsString("/v1/requirements/"))))
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
//      RequirementVO vo = RequirementVO.builder()
//               .id(4L)
//               .requirementId(5)
//               .name("name")
//               .email("email")
//               .credential(credentialVO)
//               .build();
      when(repository.save(any(Requirement.class))).thenReturn(this.entity);
      this.mvc.perform(post("/v1/requirements")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(this.vo)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(header().string("location", containsString("/v1/requirements/")))
         .andExpect(header().string("warning", not(containsString("Data already exists!"))))
         .andExpect(jsonPath("$.id", is(1)))
         .andExpect(jsonPath("$.description", is("description")))
      ;
      verify(repository, times(1)).save(any(Requirement.class));
      verifyNoMoreInteractions(repository);
   }
   
   @Test
   public void read_v1_NoEntitiesFound() throws Exception {
      List<Requirement> entities = new ArrayList<Requirement>();
      when(repository.findAll()).thenReturn(entities);
      this.mvc.perform(get("/v1/requirements"))
         .andExpect(status().isNotFound())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(header().string("location", not(containsString("/v1/requirements/"))))
         .andExpect(jsonPath("$", hasSize(0)))
      ;
      verify(repository, times(1)).findAll();
      verifyNoMoreInteractions(repository);
   }
   
   @Test
   public void read_v1_DoneSuccessfully() throws Exception {
      Requirement entity = this.entity;
      List<Requirement> entities = new ArrayList<Requirement>() {{ add(entity); }};
      when(repository.findAll()).thenReturn(entities);
      this.mvc.perform(get("/v1/requirements"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(header().string("location", not(containsString("/v1/requirements/"))))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(1)))
         .andExpect(jsonPath("$[0].description", is("description")))
      ;
      verify(repository, times(1)).findAll();
      verifyNoMoreInteractions(repository);
   }
   
   @Test
   public void readOne_v1_NoEntityFound() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(null);
      this.mvc.perform(get("/v1/requirements/2"))
         .andExpect(status().isNotFound())
         //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(header().string("location", not(containsString("/v1/requirements/2"))))
      //.andExpect(jsonPath("$", hasSize(0)))
      ;
      verify(repository, times(1)).findOne(anyLong());
      verifyNoMoreInteractions(repository);
   }
   
   @Test
   public void readOne_v1_DoneSuccessfully() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(entity);
      this.mvc.perform(get("/v1/requirements/1"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(header().string("location", not(containsString("/v1/requirements/1"))))
         .andExpect(jsonPath("$.id", is(1)))
         .andExpect(jsonPath("$.description", is("description")))
      ;
      verify(repository, times(1)).findOne(anyLong());
      verifyNoMoreInteractions(repository);
   }
   
   
   @Test
   public void update_NotFoundEntity() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(null);
      this.mvc.perform(put("/v1/requirements")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(this.vo)))
         .andExpect(status().isNotFound())
      ;
      verify(repository, times(1)).findOne(anyLong());
      verify(repository, times(0)).save(any(Requirement.class));
      verifyNoMoreInteractions(repository);
      
   }

   @Test
   public void update_v1_validVOButConstraintViolation() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(this.entity);
      doThrow(DataIntegrityViolationException.class).when(repository).save(any(Requirement.class));
      this.mvc.perform(put("/v1/requirements")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(this.vo)))
         .andExpect(status().isConflict())
         //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         //.andExpect(jsonPath("$", hasSize(1)))
         .andExpect(header().string("location", not(containsString("/v1/requirements/"))))
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
      when(repository.save(any(Requirement.class))).thenReturn(entity);
      this.mvc.perform(put("/v1/requirements")
         .contentType(MediaType.APPLICATION_JSON_UTF8)
         .content(objectMapper.writeValueAsString(this.vo)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("$.id", is(1)))
         .andExpect(jsonPath("$.description", is("description")))
      ;
      verify(repository, times(1)).findOne(anyLong());
      verify(repository, times(1)).save(any(Requirement.class));
      verifyNoMoreInteractions(repository);
      
   }
   @Test
   public void delete_NotFoundEntity() throws Exception {
      when(repository.findOne(anyLong())).thenReturn(null);
      this.mvc.perform(delete("/v1/requirements/10"))
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
      this.mvc.perform(delete("/v1/requirements/1"))
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