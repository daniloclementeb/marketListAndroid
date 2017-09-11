package com.clemente.danilo.androiddevelopment.model;

import com.clemente.danilo.androiddevelopment.model.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 26/08/2017.
 */

public class Geometry {

    @SerializedName("location")
    @Expose
    private Location location;

    /**
     *
     * @return
     * The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

}