package pw.react.backend.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Parking;

import java.util.ArrayList;
import java.util.List;

public class PageDTO<T> {
    
    @ApiModelProperty(position = 1)
    @Getter @Setter private int currentPage;

    @ApiModelProperty(position = 2)
    @Getter @Setter private List<T> parkings;

    @ApiModelProperty(position = 3)
    @Getter @Setter private long totalItems;

    @ApiModelProperty(position = 4)
    @Getter @Setter private int totalPages;

    public PageDTO(int currentPage, List<T> parkings, long totalItems, int totalPages){
        this.currentPage = currentPage;
        this.parkings = parkings;
        this.totalItems = totalItems;
        this.totalPages = totalPages;  
    }
}