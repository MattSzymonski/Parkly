package pw.react.backend.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

public class PageDTO<T> {
    
    @ApiModelProperty(position = 1)
    @Getter @Setter private int currentPage;

    @ApiModelProperty(position = 2)
    @Getter @Setter private List<T> items;

    @ApiModelProperty(position = 3)
    @Getter @Setter private long totalItems;

    @ApiModelProperty(position = 4)
    @Getter @Setter private int totalPages;

    public PageDTO(int currentPage, List<T> items, long totalItems, int totalPages){
        this.currentPage = currentPage;
        this.items = items;
        this.totalItems = totalItems;
        this.totalPages = totalPages;  
    }
}