package pl.michalkruczek.tradehelper.mainmenu;

import java.util.Arrays;
import java.util.List;

import pl.michalkruczek.tradehelper.R;

/**
 * Created by mikr on 28/08/17.
 */

public class Icon {


    public final static List<Icon> ICON_LIST = Arrays.asList(
            new Icon(0, R.mipmap.icon_company, "Company"),
            new Icon(1, R.mipmap.icon_product, "Products"),
            new Icon(2, R.mipmap.icon_order, "Order"),
            new Icon(3, R.mipmap.icon_clock, "Day Plan"),
            new Icon(4, R.mipmap.icon_notes, "Notes"),
            new Icon(5, R.mipmap.icon_info, "Info")
    );

    private int id;
    private int resources;
    private String name;

    public Icon(int id, int resources, String name) {
        this.id = id;
        this.resources = resources;
        this.name = name;
    }

    public int getResources() {
        return resources;
    }

    public String getName() {
        return name;
    }
}

