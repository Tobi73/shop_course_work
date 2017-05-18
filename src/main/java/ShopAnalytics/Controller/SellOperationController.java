package ShopAnalytics.Controller;

import ShopAnalytics.App;
import ShopAnalytics.BLL.OperationHandler;
import ShopAnalytics.Repository.ProductDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gman0_000 on 26.04.2017.
 */

@RestController(value= SellOperationController.BASE_PATH)
@Api(value = "sell")
public class SellOperationController {
    public final static String BASE_PATH = "/" + App.ROOT_PATH + "/" + "sell";

    @Autowired
    private OperationHandler operationHandler = new OperationHandler();
    @Autowired
    private ProductDao products;


    @RequestMapping(method = RequestMethod.GET, path=BASE_PATH + "/{productId}&{customerINN}&{userId}")
    @ApiOperation(value = "Sell product to customer by INN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> sellProduct(@PathVariable("productId") String productId,
                            @PathVariable("customerINN") int inn,
                            @PathVariable("userId") long userId) {
        try{
            operationHandler.sellProduct(productId, inn, userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path=BASE_PATH + "/")
    @ApiOperation(value = "Show products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> showProduct() {
        try{
            return new ResponseEntity(products.findAll(),HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}