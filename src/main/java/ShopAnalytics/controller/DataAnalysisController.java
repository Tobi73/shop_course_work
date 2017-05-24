package ShopAnalytics.controller;

import ShopAnalytics.App;
import ShopAnalytics.bll.DataAnalysis;
import ShopAnalytics.model.BusinessPartnerNode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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



}
