package com.satish.android.entertainmentminiapp.model

import java.util.*
/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

/**
 * for making the Horizontal lists bind when number of items in it becomes zero and viceversa.
 */

class ListCountModel(var id: Int, var count: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val that = other as ListCountModel

        return id == that.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(count)
    }
}