package com.papb.myfintrack.data.repository

interface Observer {
    fun update(newBalance: Double)
}