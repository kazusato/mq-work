package kazusato.example.api

import kazusato.example.api.model.MessagePostRequest
import kazusato.example.api.model.MessagePostResponse
import kazusato.example.api.tools.MessageSender
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/client")
class MqClientResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postMessage(req: MessagePostRequest): MessagePostResponse {
        MessageSender().sendMessage(req.message)
        return MessagePostResponse("OK")
    }

}