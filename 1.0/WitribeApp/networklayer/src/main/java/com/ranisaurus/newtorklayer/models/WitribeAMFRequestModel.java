package com.ranisaurus.newtorklayer.models;

/**
 * Created by muzammilpeer on 9/27/15.
 */
public class WitribeAMFRequestModel extends BaseModel {

    private String[] parameters;

    private String methodName;

    private String serviceName;

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "ClassPojo [parameters = " + parameters + ", methodName = " + methodName + ", serviceName = " + serviceName + "]";
    }
}
