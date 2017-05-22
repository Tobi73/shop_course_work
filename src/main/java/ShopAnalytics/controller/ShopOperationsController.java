package ShopAnalytics.controller;

import ShopAnalytics.App;
import ShopAnalytics.bll.OperationHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gman0_000 on 26.04.2017.
 */

@RestController(value= ShopOperationsController.BASE_PATH)
@Api(value = "shop-operations")
public class ShopOperationsController {
    public final static String BASE_PATH = "/" + App.ROOT_PATH + "/" + "shop-operations";

    @Autowired
    private OperationHandler operationHandler = new OperationHandler();


    @RequestMapping(method = RequestMethod.GET, path=BASE_PATH + "/sell/{productId}&{customerINN}&{userId}")
    @ApiOperation(value = "Sell product to customer by INN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> sellProduct(@PathVariable("productId") Long productId,
                            @PathVariable("customerINN") Long inn,
                            @PathVariable("userId") long userId) {
        try {
            operationHandler.sellProduct(productId, inn, userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path=BASE_PATH + "/purchase/{productId}&{customerINN}&{userId}")
    @ApiOperation(value = "Purchase product from business partner")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> purchaseProduct(@PathVariable("productId") Long productId,
                                         @PathVariable("customerINN") Long inn,
                                         @PathVariable("userId") long userId) {
        try{
            operationHandler.purchaseProduct(productId, inn, userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}