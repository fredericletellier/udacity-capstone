/*
 *     Food Inspector - Choose well to eat better
 *     Copyright (C) 2016  Frédéric Letellier
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.fredericletellier.foodinspector.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Event {

    public static final String STATUS_OK = "OK";
    public static final String STATUS_NOT_A_PRODUCT = "BARCODE_NOT_DESCRIBE_A_PRODUCT";
    public static final String STATUS_NOT_IN_OFF_DATABASE = "PRODUCT_IS_NOT_IN_OPENFOODFACTS_DATABASE";
    public static final String STATUS_NO_NETWORK = "NO_NETWORK";

    private long id;
    private long timestamp;
    private String barcode;
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public Event() {
    }

    /**
     *
     * @param timestamp
     * @param id
     * @param status
     * @param barcode
     */
    public Event(long id, long timestamp, String barcode, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.barcode = barcode;
        this.status = status;
    }

    //TODO public static Event from(Cursor cursor)

    //TODO public static Event from(ContentValues values)

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.id = id;
    }

    public Event withId(long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     * The timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Event withTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     *
     * @return
     * The barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     *
     * @param barcode
     * The barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Event withBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Event withStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(timestamp).append(barcode).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Event) == false) {
            return false;
        }
        Event rhs = ((Event) other);
        return new EqualsBuilder().append(id, rhs.id).append(timestamp, rhs.timestamp).append(barcode, rhs.barcode).append(status, rhs.status).isEquals();
    }

}