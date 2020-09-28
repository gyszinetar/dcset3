package hu.prooktatas.dcset3.async

import android.os.AsyncTask
import com.github.kittinunf.fuel.Fuel
import hu.prooktatas.dcset3.key
interface lastseensidinterface{
    fun sendid(id:Int)
}
class GetLastseenState(var kuldo:String,var lastseensidinterface: lastseensidinterface): AsyncTask<Unit, Unit, Int>() {
    override fun doInBackground(vararg p0: Unit?): Int {
        val bodyJson2 = """{ "key" : "$key",
                            "kuldo" : "$kuldo"
                          }"""
        val (request, response, result)
                = Fuel.post("https://dcset.ditusz.hu/dcset/getallert")
            .body(bodyJson2)
            .response()



        var lastseenid = 0
        String(response.data).toInt().let{
             lastseenid = it
        }
        return lastseenid
    }

    override fun onPostExecute(lastseenid: Int) {
        lastseenid?.let {
            lastseensidinterface.sendid(it)
        }
    }
}