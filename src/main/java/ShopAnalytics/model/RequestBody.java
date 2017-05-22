package ShopAnalytics.model;

import javafx.beans.property.IntegerProperty;
import lombok.Data;

/**
 * Created by gman0_000 on 20.05.2017.
 */
@Data
public class RequestBody<T> {

    private Long id;
    private T body;

    public RequestBody() {}

    public RequestBody(String stringId, Long id, T body){
        this.id = id;
        this.body = body;
    }

    public <R> R getRequestBody(){
        return (R)body;
    }

}
