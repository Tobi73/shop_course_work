package ShopAnalytics.controller;

import ShopAnalytics.App;
import ShopAnalytics.bll.DataAnalysis;
import ShopAnalytics.model.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by andreyzaytsev on 22.05.17.
 */

@RestController(value = DataAnalysisController.BASE_PATH)
public class DataAnalysisController {
    public final static String BASE_PATH = "/" + App.ROOT_PATH + "/" + "data-analysis";

    @Autowired
    private DataAnalysis analysis;

    @RequestMapping(method = RequestMethod.GET, path = BASE_PATH + "/partners-analytics")
    @ApiOperation(value = "Recieve graph data for partners analytics")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BusinessPartnerNode.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> getPartnersGraph(@RequestParam(name = "productId") Long productId){
        try{
            return new ResponseEntity<>(analysis.buildBusinessPartnerGraphRelation(productId), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = BASE_PATH + "/price-analytics")
    @ApiOperation(value = "Get best offer option from available business partners")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AnalyticsData.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> getBestOffer(@RequestParam(name = "productName") String productName,
                                          @RequestParam(name = "productPrice") int price,
                                          @RequestBody PriceCriteria criteria){
        try{
            return new ResponseEntity<>(analysis.determineBestPurchaseOption(productName, price, criteria), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(method = RequestMethod.POST, path = BASE_PATH + "/select-transactions")
    @ApiOperation(value = "Select all transactions by rule")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Transaction.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> getTransactionsByRule(@RequestBody TransactionSelectionRule criteria){
        try{
            return new ResponseEntity<>(analysis.selectTransactionByRule(criteria), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
