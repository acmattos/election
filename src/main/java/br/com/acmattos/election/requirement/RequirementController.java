package br.com.acmattos.election.requirement;

import br.com.acmattos.election.util.ResponseEntityBuilder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author acmattos
 * @since 07/02/2018
 * TODO JAVADOC, CHECK, UNIT TEST
 */
@RestController
class RequirementController {
   @Autowired
   private RequirementRepository repository;

   /**
    * Creates a new valid employee. That employee must be provided with proper
    * attributes. Refer to EmployeeVO javadocs.
    * @param vo New employee.
    * @return Employee data as response body or error message.
    */
   @ApiOperation(value = "Creates a new valid requirement"//,
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
           message = "New requirement created",
           response = RequirementVO.class,
           responseHeaders = @ResponseHeader(
               name = "Location",
               description = "/v1/requirements/{NEW_REQUIREMENT_ID}",
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
   @PostMapping(value = "/v1/requirements", consumes = "application/json", produces = "application/json")
   //@PreAuthorize("hasAuthority('ROLE_CREATE_REQUIREMENT')")
   ResponseEntity<RequirementVO> create(
      @ApiParam(value = "Valid requirement data", required = true)
      @RequestBody RequirementVO vo){
      Requirement entity = null;
      ResponseEntityBuilder<RequirementVO> builder =
         ResponseEntityBuilder.builder();

      if(vo.isValid()){
         entity = RequirementVO.toEntity(vo);
         try{
            entity = repository.save(entity);
            builder.key("Location")
               .value("/v1/requirements/" + entity.getId())
               .body(RequirementVO.toVO(entity))
               .CREATED();
         } catch(DataIntegrityViolationException dive){
            //TODO Tratamento adequado de email, etc nao unico
            dive.printStackTrace();
            builder.key("Warning").value("Data already exists!").CONFLICT();
         }
      } else {
         builder.key("Error")
            .value("Invalid requirement data! Creation not executed!")
            .BAD_REQUEST();
      }
      return builder.build();
   }

   @GetMapping("/v1/requirements")
   ResponseEntity<List<RequirementVO>> read() {
      List<Requirement> entities = repository.findAll();
   
      // TODO 200 (OK), list of customers. Use pagination, sorting and filtering to navigate big lists.
      return ResponseEntityBuilder
         .builder()
         .body(RequirementVO.toVOs(entities))
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
         response = RequirementVO.class
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
   @GetMapping("/v1/requirements/{id}")
   ResponseEntity<RequirementVO> read(@PathVariable long id) {
      Requirement entity = repository.findOne(id);
   
      // TODO 200 (OK), single customer. 404 (Not Found), if ID not found or invalid.
      return ResponseEntityBuilder
         .builder()
         .body(RequirementVO.toVO(entity))
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
         response = RequirementVO.class,
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
   @PutMapping("/v1/requirements")
   ResponseEntity<RequirementVO> update(@RequestBody RequirementVO vo){
      Requirement entity = repository.findOne(vo.getId());
      ResponseEntityBuilder<RequirementVO> builder =
         ResponseEntityBuilder.builder();
   
      if(null != entity){
         try{
            entity = RequirementVO.toEntity(vo);
            repository.save(entity);
            builder.body(RequirementVO.toVO(entity)).OK();
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
         response = RequirementVO.class
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
   @DeleteMapping("/v1/requirements/{id}")
   ResponseEntity<RequirementVO> delete(@PathVariable long id) {
      Requirement entity = repository.findOne(id);
      ResponseEntityBuilder<RequirementVO> builder =
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
