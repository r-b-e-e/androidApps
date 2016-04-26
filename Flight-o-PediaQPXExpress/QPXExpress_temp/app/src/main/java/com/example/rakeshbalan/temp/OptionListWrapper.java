package com.example.rakeshbalan.temp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rakeshbalan on 1/2/2016.
 */
public class OptionListWrapper implements Serializable {
    List<Option> optionList;

    public OptionListWrapper(List<Option> optionList) {
        this.optionList = optionList;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }
}
