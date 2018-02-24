package br.com.acmattos.election.employee;

import br.com.acmattos.election.util.ResponseEntityBuilder;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API responsible for Employee data Manipulation.
 * @author acmattos
 * @since 18/01/2018
 * TODO JAVADOC, CHECK, UNIT TEST https://spring.io/guides/tutorials/spring-security-and-angular-js/#_sso_with_oauth2_angular_js_and_spring_security_part_v
 */
@RestController // TODO DOCUMENT SOLUTION http://www.restapitutorial.com/lessons/httpmethods.html
class EmployeeController {

   @Autowired
   private EmployeeRepository repository;

   /**
    * Creates a new valid employee. That employee must be provided with proper
    * attributes. Refer to EmployeeVO javadocs.
    * @param vo New employee.
    * @return Employee data as response body or error message.
    */
   // TODO https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X#apiresponses-apiresponse
   // TODO http://www.mimiz.fr/blog/use-authorization-header-with-swagger/
   // TODO https://stackoverflow.com/questions/43651298/adding-authorization-to-annotation-driven-swagger-json-with-jersey-2-and-spring
   // TODO https://github.com/swagger-api/swagger-samples/blob/master/java/java-jersey-jaxrs/src/main/java/io/swagger/sample/Bootstrap.java
   // TODO https://github.com/swagger-api/swagger-samples/tree/master/java/java-spring-boot
   // TODO https://stackoverflow.com/questions/32384493/swagger-2-0-where-to-declare-basic-auth-schema
   @ApiOperation(value = "Creates a new valid employee"//,
//      authorizations = {
//         @Authorization(
//            value="oAuth2"//,
//            scopes = { @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE", description="Auth descr"),
//                @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE1", description="Auth descr1") }
//         )
//      }
   )
   @ApiResponses(value = {
      @ApiResponse(
         code = 201,
         message = "New employee created",
         response = EmployeeVO.class,
         responseHeaders = @ResponseHeader(
            name = "Location",
            description = "/v1/employees/{NEW_EMPLOYEE_ID}",
            response = String.class)
      ),
      @ApiResponse(
         code = 409,
         message = "Data already exists!",
         responseHeaders = @ResponseHeader(
           name = "Warning",
           description = "Data already exists!",
           response = String.class)
      ),
      @ApiResponse(code = 400, message = "Invalid employee data!",
         responseHeaders = @ResponseHeader(
            name = "Error",
            description = "Invalid employee data! Creation not executed!",
            response = String.class))
   })
   @PostMapping(value = "/v1/employees", consumes = "application/json", produces = "application/json")
   //@PreAuthorize("hasAuthority('ROLE_CREATE_EMPLOYEE')")
   ResponseEntity<EmployeeVO> create(
      @ApiParam(value = "Valid employee data", required = true)
      @RequestBody EmployeeVO vo){
      Employee entity = null;
      ResponseEntityBuilder<EmployeeVO> builder =
         ResponseEntityBuilder.builder();

      if(vo.isValid()){
         entity = EmployeeVO.toEntity(vo);
         try{
            entity = repository.save(entity);
            builder.key("Location")
                .value("/v1/employees/" + entity.getId())
                .body(EmployeeVO.toVO(entity))
                .CREATED();
         } catch(DataIntegrityViolationException dive){
            //TODO Tratamento adequado de email, etc nao unico
            dive.printStackTrace();
            builder.key("Warning").value("Data already exists!").CONFLICT();
         }
      } else {
         builder.key("Error")
             .value("Invalid employee data! Creation not executed!")
             .BAD_REQUEST();
      }
      return builder.build();
   }

   @ApiOperation(value = "Lists existing employees"//,
//      authorizations = {
//         @Authorization(
//            value="oAuth2"//,
//            scopes = { @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE", description="Auth descr"),
//                @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE1", description="Auth descr1") }
//         )
//      }
   )
   @ApiResponses(value = {
       @ApiResponse(
           code = 200,
           message = "Lists existing employees",
           response = EmployeeVO.class
       ),
       @ApiResponse(
           code = 404,
           message = "No employee exists!"//,
//           responseHeaders = @ResponseHeader(
//               name = "Warning",
//               description = "Data already exists!",
//               response = String.class)
       )
   })
   @GetMapping("/v1/employees")
   ResponseEntity<List<EmployeeVO>> read() {
      List<Employee> entities = repository.findAll();

      // TODO 200 (OK), list of customers. Use pagination, sorting and filtering to navigate big lists.
      return ResponseEntityBuilder
          .builder()
          .body(EmployeeVO.toVOs(entities))
          .build();
   }

   @ApiOperation(value = "Lists a specific employee"//,
//      authorizations = {
//         @Authorization(
//            value="oAuth2"//,
//            scopes = { @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE", description="Auth descr"),
//                @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE1", description="Auth descr1") }
//         )
//      }
   )
   @ApiResponses(value = {
       @ApiResponse(
           code = 200,
           message = "Lists a specific employee",
           response = EmployeeVO.class
       ),
       @ApiResponse(
           code = 404,
           message = "No employee found!"//,
//           responseHeaders = @ResponseHeader(
//               name = "Warning",
//               description = "Data already exists!",
//               response = String.class)
       )
   })
   @GetMapping("/v1/employees/{id}")
   ResponseEntity<EmployeeVO> read(@PathVariable long id) {
      Employee entity = repository.findOne(id);

      // TODO 200 (OK), single customer. 404 (Not Found), if ID not found or invalid.
      return ResponseEntityBuilder
          .builder()
          .body(EmployeeVO.toVO(entity))
          .build();
   }

   @ApiOperation(value = "Creates a new valid employee"//,
//      authorizations = {
//         @Authorization(
//            value="oAuth2"//,
//            scopes = { @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE", description="Auth descr"),
//                @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE1", description="Auth descr1") }
//         )
//      }
   )
   @ApiResponses(value = {
       @ApiResponse(
           code = 201,
           message = "New employee created",
           response = EmployeeVO.class,
           responseHeaders = @ResponseHeader(
               name = "Location",
               description = "/v1/employees/{NEW_EMPLOYEE_ID}",
               response = String.class)
       ),
       @ApiResponse(
           code = 409,
           message = "Data already exists!",
           responseHeaders = @ResponseHeader(
               name = "Warning",
               description = "Data already exists!",
               response = String.class)
       ),
       @ApiResponse(code = 400, message = "Invalid employee data!",
           responseHeaders = @ResponseHeader(
               name = "Error",
               description = "Invalid employee data! Creation not executed!",
               response = String.class))
   })
   @PutMapping("/v1/employees")
   ResponseEntity<EmployeeVO> update(@RequestBody EmployeeVO vo){
      Employee entity = repository.findOne(vo.getId());
      ResponseEntityBuilder<EmployeeVO> builder =
         ResponseEntityBuilder.builder();

      if(null != entity){
         try{
            entity = EmployeeVO.toEntity(vo);
            repository.save(entity);
            builder.body(EmployeeVO.toVO(entity)).OK();
         } catch(DataIntegrityViolationException dive){
            //TODO Tratamento adequado de email nao unico
            dive.printStackTrace();
            builder.key("Warning").value("Data already exists!").CONFLICT();
         }
      } else {
         builder.key("Warning")
             .value("Invalid employee id! Update not executed!")
             .NOT_FOUND();
      }

      // TODO 200 (OK) or 204 (No Content). 404 (Not Found), if ID not found or invalid.
      return builder.build();
   }

   @ApiOperation(value = "Deletes an employee based on its ID"//,
//      authorizations = {
//         @Authorization(
//            value="oAuth2"//,
//            scopes = { @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE", description="Auth descr"),
//                @AuthorizationScope(scope = "ROLE_CREATE_EMPLOYEE1", description="Auth descr1") }
//         )
//      }
   )
   @ApiResponses(value = {
       @ApiResponse(
           code = 200,
           message = "Deletes an employee based on its ID",
           response = EmployeeVO.class
       ),
       @ApiResponse(
           code = 404,
           message = "Employee not found!",
           responseHeaders = @ResponseHeader(
               name = "Warning",
               description = "Invalid employee id! Delete not executed!",
               response = String.class)
       )
   })
   @DeleteMapping("/v1/employees/{id}")
   ResponseEntity<EmployeeVO> delete(@PathVariable long id) {
      Employee entity = repository.findOne(id);
      ResponseEntityBuilder<EmployeeVO> builder =
          ResponseEntityBuilder.builder();
      if(null != entity) {
         repository.delete(id);
         builder.OK();
      } else {
         builder.key("Warning")
             .value("Invalid employee id! Delete not executed!")
             .NOT_FOUND();
      }
      // TODO 200 (OK). 404 (Not Found), if ID not found or invalid.
      return builder.build();
   }
}
