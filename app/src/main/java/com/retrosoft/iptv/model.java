package com.retrosoft.iptv;

public class model {
    String COLUMN_NAME,COLUMN_LOGO,COLUMN_LINK  ,COLUMN_fvrt,COLUMN_ID;

    /*
    ,COLUMN_fvrt
    , String COLUMN_fvrt
     */



    public model(String COLUMN_NAME, String COLUMN_LOGO, String COLUMN_LINK, String COLUMN_fvrt, String COLUMN_ID) {
        this.COLUMN_NAME = COLUMN_NAME;
        this.COLUMN_LOGO = COLUMN_LOGO;
        this.COLUMN_LINK = COLUMN_LINK;
        this.COLUMN_fvrt = COLUMN_fvrt;
        this.COLUMN_ID =COLUMN_ID;
    }

    public String getCOLUMN_ID() {
        return COLUMN_ID;
    }

    public void setCOLUMN_ID(String COLUMN_ID) {
        this.COLUMN_ID = COLUMN_ID;
    }

    public String getCOLUMN_fvrt() {
        return COLUMN_fvrt;
    }

    public void setCOLUMN_fvrt(String COLUMN_fvrt) {
        this.COLUMN_fvrt = COLUMN_fvrt;
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
