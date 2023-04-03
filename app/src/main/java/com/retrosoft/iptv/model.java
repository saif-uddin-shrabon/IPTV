package com.retrosoft.iptv;

public class model {
    String COLUMN_NAME,COLUMN_LOGO,COLUMN_LINK;

    public model(String COLUMN_NAME, String COLUMN_LOGO, String COLUMN_LINK) {
        this.COLUMN_NAME = COLUMN_NAME;
        this.COLUMN_LOGO = COLUMN_LOGO;
        this.COLUMN_LINK = COLUMN_LINK;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public String getCOLUMN_LOGO() {
        return COLUMN_LOGO;
    }

    public void setCOLUMN_LOGO(String COLUMN_LOGO) {
        this.COLUMN_LOGO = COLUMN_LOGO;
    }

    public String getCOLUMN_LINK() {
        return COLUMN_LINK;
    }

    public void setCOLUMN_LINK(String COLUMN_LINK) {
        this.COLUMN_LINK = COLUMN_LINK;
    }
}
