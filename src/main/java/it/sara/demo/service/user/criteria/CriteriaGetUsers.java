package it.sara.demo.service.user.criteria;

import it.sara.demo.service.criteria.GenericCriteria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriteriaGetUsers extends GenericCriteria {

    @NotNull(message = "Query must not be null")
    private String query;

    @Min(value = 0, message = "Offset must be 0 or greater")
    private int offset;

    @Min(value = 1, message = "Limit must be at least 1")
    private int limit;
    

    @NotNull(message = "Order type must not be null")
    private OrderType order;

    @Getter
    public enum OrderType {
        BY_FIRSTNAME("by firstName"),
        BY_FIRSTNAME_DESC("by firstName desc"),
        BY_LASTNAME("by lastName"),
        BY_LASTNAME_DESC("by lastName");
        private final String displayName;

        OrderType(String displayName) {
            this.displayName = displayName;
        }
    }

}
