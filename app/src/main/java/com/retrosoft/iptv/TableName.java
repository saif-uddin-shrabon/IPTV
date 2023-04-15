package com.retrosoft.iptv;

public class TableName {
    String COLUMN_NAME,COLUMN_LINK;

    public TableName(String COLUMN_NAME, String COLUMN_LINK) {
        this.COLUMN_NAME = COLUMN_NAME;
        this.COLUMN_LINK = COLUMN_LINK;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public String getCOLUMN_LINK() {
        return COLUMN_LINK;
    }

    public void setCOLUMN_LINK(String COLUMN_LINK) {
        this.COLUMN_LINK = COLUMN_LINK;
    }
}
