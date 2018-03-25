package kazusato.example.api.tools

import org.slf4j.LoggerFactory
import javax.jms.*
import javax.naming.InitialContext

class MessageSender {

    companion object {
        val logger = LoggerFactory.getLogger(MessageSender::class.java)
    }

    fun sendMessage(msg: String) {
        val ic = InitialContext()
        val cf: QueueConnectionFactory = ic.lookup("jms/openmqConnectionFactory")
                as? QueueConnectionFactory
                ?: throw RuntimeException("JMS Connection Factory lookup failed.")

        var conn: QueueConnection? = null
        var session: QueueSession? = null
        var queueSender: QueueSender? = null

        try {
            conn = cf.createQueueConnection()
            session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE)
            val queue = session.createQueue("myqueue")

            val textMsg = session.createTextMessage(msg)
            queueSender = session.createSender(queue)
            queueSender.send(textMsg)
        } catch (e: Exception) {
            throw RuntimeException("Error occurred during sending a message.", e)
        } finally {
            queueSender?.close()
            session?.close()
            conn?.close()
        }
    }

}