package ShopAnalytics.controller;

import ShopAnalytics.App;

import ShopAnalytics.bll.InfrastructureHandler;
import ShopAnalytics.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by gman0_000 on 20.05.2017.
 */
@RestController(value= InfrastructureController.BASE_PATH)
@Api(value = "shop-infrastructure")
public class InfrastructureController {
    public final static String BASE_PATH = "/" + App.ROOT_PATH + "/" + "shop-infrastructure";

    @Autowired
    private InfrastructureHandler handler = new InfrastructureHandler();



    @RequestMapping(method = RequestMethod.POST, path=BASE_PATH + "/{operation}/{entity}")
    @ApiOperation(value = "Unified post method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> postOperation(@PathVariable(name = "operation") String operationName
            , @RequestBody(required = false) ShopAnalytics.model.RequestBody body
            , @PathVariable(name = "entity") String entityName
            , @RequestParam(name = "id", required = false) Long id) {
        try{
            return new ResponseEntity(handler.handleControllerRequest(entityName, operationName, body),
                    HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path=BASE_PATH + "/insert/product")
    @ApiOperation(value = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> insertProduct(@RequestBody Product product) {
        try{
            handler.insertNewObject(product);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path=BASE_PATH + "/insert/transactionType")
    @ApiOperation(value = "Create new transaction type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> insertTransactionType(@RequestBody TransactionType transactionType) {
        try{
            handler.insertNewObject(transactionType);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path=BASE_PATH + "/insert/role")
    @ApiOperation(value = "Create new role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> insertRole(@RequestBody Role role) {
        try{
            handler.insertNewObject(role);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path=BASE_PATH + "/insert/user")
    @ApiOperation(value = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> insertUser(@RequestBody User user) {
        try{
            handler.insertNewObject(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path=BASE_PATH + "/insert/businessEntity")
    @ApiOperation(value = "Create new business entity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> insertBusinessEntity(@RequestBody BusinessEntity businessEntity) {
        try{
            handler.insertNewObject(businessEntity);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path=BASE_PATH + "/authenticate")
    @ApiOperation(value = "Authentication")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> authenticate(@RequestParam(name = "login") String login,
                                          @RequestParam(name = "password") String password) {
        try{
            return new ResponseEntity(handler.authenticate(login, password),
                    HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
