package pl.michalkruczek.tradehelper.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mikr on 27/08/17.
 */

public class Order implements Serializable{

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("companyId")
    @Expose
    private Long companyId;
    @SerializedName("productId")
    @Expose
    private Long productId;
    @SerializedName("userId")
    @Expose
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", productId=" + productId +
                '}';
    }
}
