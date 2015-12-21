package com.overturelabs.cannon.toolbox.gson.models;

/**
 * Abstract Class which caters to mixed type object currently only handles String and Custom Class
 * e.g. User ID(String) and User(Custom Class)
 */
public abstract class JsonMixed {
    // Primitive Types
    protected String mString;
    protected int mInteger;
    protected boolean mBoolean;
    // Json Types
    protected Boolean mIsJsonObject, mIsJsonArray;

    public JsonMixed(){}

    /**
     * Return the json string or string value
     *
     * @return json string or string value
     */
    public String getString() {
        return mString;
    }

    /**
     * Set the json string or string value
     *
     * @param obj json string or string value
     */
    public void setString(String obj) {
        mString = obj;
    }

    /**
     * Return the integer value
     *
     * @return integer value
     */
    public int getInteger() {
        return mInteger;
    }

    /**
     * Set the integer value
     *
     * @param obj integer value
     */
    public void setInteger(int obj) {
        mInteger = obj;
    }

    /**
     * Return the boolean value
     *
     * @return boolean value
     */
    public boolean getBoolean() {
        return mBoolean;
    }

    /**
     * Set the boolean value
     *
     * @param obj boolean value
     */
    public void setBoolean(boolean obj) {
        mBoolean = obj;
    }

    /**
     * Get whether it is a Json Object
     *
     * @return whether it is a Json Object
     */
    public boolean isJsonObject() {
        return mIsJsonObject;
    }

    /**
     * Set whether it is a Json Object
     *
     * @param isJsonObject true is jsonObject, false is primitiveObject
     */
    public void setIsJsonObject(Boolean isJsonObject) {
        mIsJsonObject = isJsonObject;
    }

    /**
     * Get whether it is a Json Array
     *
     * @return whether it is a Json Array
     */
    public boolean isJsonArray() {
        return mIsJsonArray;
    }

    /**
     * Set whether it is a Json Array
     *
     * @param isJsonArray true is jsonArray
     */
    public void setIsJsonArray(Boolean isJsonArray) {
        mIsJsonArray = isJsonArray;
    }

    /**
     * Transform string into custom class
     */
    public abstract void transform();

    @Override
    public String toString() {
        return "String: "+(mString == null ? "null" : mString)
                + "\nIsJsonObject: "+(mIsJsonObject == null ? "null" : mIsJsonObject.toString());
    }
}
