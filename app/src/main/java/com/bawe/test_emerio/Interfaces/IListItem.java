package com.bawe.test_emerio.Interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poing on 8/28/18.
 */

public interface IListItem {
    public void populate(List<Object> objects);
    public List<Object> getDataSet();
    public Object get(int index);
}
