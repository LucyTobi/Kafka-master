package com.example.Kafka.DTO;

public class InformationDTO extends BaseDTO {
    private static final long serialVersionUID = 334794780764958176L;

    private boolean success;
    private String message;
    private String resourceKey;
    private Object data;
    private MetaData meta;

    public InformationDTO() {

    }

    public InformationDTO(boolean success, String message, String resourceKey, Object data, MetaData meta) {
        this.success = success;
        this.message = message;
        this.resourceKey = resourceKey;
        this.data = data;
        this.meta = meta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public Object getdata() {
        return data;
    }

    public void setdata(Object data) {
        this.data = data;
    }

    public static class MetaData {
        String total;
        String limit;
        String start;

        public MetaData() {

        }

        public MetaData(String total, String limit, String start) {
            this.total = total;
            this.limit = limit;
            this.start = start;
        }

        public String getTotal() {
            return total;
        }

        public String getLimit() {
            return limit;
        }

        public String getStart() {
            return start;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public void setStart(String start) {
            this.start = start;
        }
    }

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
