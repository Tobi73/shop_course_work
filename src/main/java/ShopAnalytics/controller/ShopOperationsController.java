package ShopAnalytics.controller;

import ShopAnalytics.App;
import ShopAnalytics.bll.OperationHandler;
import ShopAnalytics.model.Pair;
import ShopAnalytics.model.Transaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by gman0_000 on 26.04.2017.
 */

@RestController(value= ShopOperationsController.BASE_PATH)
@Api(value = "shop-operations")
public class ShopOperationsController {
    public final static String BASE_PATH = "/" + App.ROOT_PATH + "/" + "shop-operations";

    @Autowired
    private OperationHandler operationHandler = new OperationHandler();


    @RequestMapping(method = RequestMethod.GET, path=BASE_PATH + "/sell")
    @ApiOperation(value = "Sell product to customer by INN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> sellProduct(@RequestParam(name = "productId") Long productId,
                            @RequestParam(name = "customerINN") Long inn,
                            @RequestParam(name = "userId") long userId) {
        try {
            operationHandler.sellProduct(productId, inn, userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path=BASE_PATH + "/purchase")
    @ApiOperation(value = "Purchase product from business partner")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> purchaseProduct(@RequestParam("productId") Long productId,
                                         @RequestParam("customerINN") Long inn,
                                         @RequestParam("userId") long userId,
                                             @RequestParam("price") int price) {
        try{
            operationHandler.purchaseProduct(productId, inn, userId, price);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path=BASE_PATH + "/multiple/sell")
    @ApiOperation(value = "Sell multiple purchases")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> sellMultipleProduct(@RequestBody List<Pair<Long, Integer>> products,
                                             @RequestParam("customerINN") Long inn,
                                             @RequestParam("userId") long userId) {
        try{
            operationHandler.sellProducts(products, inn, userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path=BASE_PATH + "/multiple/purchase")
    @ApiOperation(value = "Purchase multiple purchases")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> purchaseMultipleProduct(@RequestBody List<Transaction> transactions,
                                                 @RequestParam("userId") long userId) {
        try{
            operationHandler.purchaseProducts(transactions, userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path=BASE_PATH + "/statistics")
    @ApiOperation(value = "Recieve all data about sellings per month")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> recieveStatistics(@RequestParam("productId") long productId) {
        try{
            return new ResponseEntity(operationHandler.recieveStatistics(productId),HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}