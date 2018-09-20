package cn.dlj1.cms.web.auth.menu.impl;

import org.apache.commons.lang.ArrayUtils;

public class ControllerMenu {

    private String title;

    private String url;

    private String bean;

    private String[] keys;

    private Item[] items;

    public ControllerMenu(String title, String url, String bean) {
        this.title = title;
        this.url = url;
        this.bean = bean;
    }

    public void addItem(String title, String url, String method, String key) {
        if (null == items) {
            items = new Item[0];
        }
        this.items = (Item[]) ArrayUtils.add(this.items, new Item(title, url, method, key));
    }

    public void addKey(String key) {
        if (null == key) {
            return;
        }
        if (null == keys) {
            keys = new String[0];
        }
        this.keys = (String[]) ArrayUtils.add(this.keys, key);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public static class Item {
        private String title;

        private String url;

        private String method;

        private String key;

        public Item(String title, String url, String method, String key) {
            this.title = title;
            this.url = url;
            this.method = method;
            this.key = key;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

}
