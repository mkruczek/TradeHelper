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
    private long id;
    @SerializedName("companyId")
    @Expose
    private long companyId;
    @SerializedName("productId")
    @Expose
    private long productId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
