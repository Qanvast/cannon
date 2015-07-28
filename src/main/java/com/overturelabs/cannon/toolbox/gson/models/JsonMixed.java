package com.overturelabs.cannon.toolbox.gson.models;

/**
 * Created by derricklee on 28/7/15.
 *
 * Abstract Class which caters to mixed type object currently only handles String and Custom Class
 * e.g. User ID(String) and User(Custom Class)
 */
public abstract class JsonMixed {
    protected String mStrObj;
    protected Boolean mIsJsonObject;

    public JsonMixed(){}

    /**
     * Return the json string
     *
     * @return json string
     */
    public String getString() {
        return mStrObj;
    }

    /**
     * Set the json string
     *
     * @param obj json string
     */
    public void setString(String obj) {
        mStrObj = obj;
    }

    public boolean isJsonObject() {
        return mIsJsonObject;
    }

    /**
     * Set whether it is a Json Object
     *
     * @param isJsonObject true is jsonObject, false is primitiveType OR jsonArray
     */
    public void setIsJsonObject(Boolean isJsonObject) {
        mIsJsonObject = isJsonObject;
    }

    /**
     * Transform string into custom class
     */
    public abstract void transform();

    @Override
    public String toString() {
        return "String: "+(mStrObj == null ? "null" : mStrObj)
                + "\nIsJsonObject: "+(mIsJsonObject == null ? "null" : mIsJsonObject.toString());
    }
}
