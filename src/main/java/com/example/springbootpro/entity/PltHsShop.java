package com.example.springbootpro.entity;

public class PltHsShop {
    private String orgcode;

    private String orgname;

    private String orgtype;

    private String orgclass;

    private String acclevel;

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode == null ? null : orgcode.trim();
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }

    public String getOrgtype() {
        return orgtype;
    }

    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype == null ? null : orgtype.trim();
    }

    public String getOrgclass() {
        return orgclass;
    }

    public void setOrgclass(String orgclass) {
        this.orgclass = orgclass == null ? null : orgclass.trim();
    }

    public String getAcclevel() {
        return acclevel;
    }

    public void setAcclevel(String acclevel) {
        this.acclevel = acclevel == null ? null : acclevel.trim();
    }
}