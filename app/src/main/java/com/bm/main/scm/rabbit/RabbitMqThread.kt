package com.bm.main.scm.rabbit

import android.app.Activity
import androidx.annotation.Keep
import com.bm.main.fpl.utils.PreferenceClass
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
//import com.rabbitmq.client.QueueingConsumer
import timber.log.Timber

@Keep
class RabbitMqThread(val activity: Activity) : Thread() {

    var running = true

    private val factory by lazy {
        ConnectionFactory().apply {
            isAutomaticRecoveryEnabled = false
            username = "profitapp"
            password = "GKtZn9Gg5t7d4BEj"
            host = "35.185.179.236"
            port = 5672
            virtualHost = "qrisprofit"
        }
    }

    override fun run() {
        Timber.e("init rabbit thread")
        while (running) {
            try {
                val connection: Connection = factory.newConnection()
                val channel = connection.createChannel()
                channel.basicQos(1)
                val q: AMQP.Queue.DeclareOk = channel.queueDeclare()
                channel.queueBind(
                    q.queue,
                    "printstruk",
                    "trx_${PreferenceClass.getString("id_speedcash")}"
                )
               // val consumer = QueueingConsumer(channel)
                //channel.basicConsume(q.queue, true, consumer)

                /*while (running) {
                    val msg = String(consumer.nextDelivery().body)
                    Timber.e("rabbit - receive: $msg")
                    RabbitMqPrint.printStrukRabbit(msg, activity)
                }*/
            } catch (e: InterruptedException) {
                Timber.e("interupted exception error")
                Timber.e(e)
                break
            } catch (e1: Exception) {
                Timber.e("general error")
                Timber.e(e1)
                try {
                    Runtime.getRuntime().gc()
                    sleep(4000) //sleep and then try again
                } catch (e2: InterruptedException) {
                    Timber.e("general error 2")
                    Timber.e(e2)
                    break
                }
            }
        }
    }
}