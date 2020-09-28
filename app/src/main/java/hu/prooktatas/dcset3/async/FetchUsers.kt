package hu.prooktatas.dcset3.async

import android.os.AsyncTask
import com.github.kittinunf.fuel.Fuel
import hu.prooktatas.dcset3.key
import org.json.JSONArray
import org.json.JSONObject


interface allUsers{
    fun fetchUsers(list:List<JSONObject>)
}
class FetchUsers(var userId:String,var kuldo:String,var allUsers: allUsers): AsyncTask<Unit, Unit,List<JSONObject>>() {
    override fun doInBackground(vararg p0: Unit?): List<JSONObject> {
        var list = mutableListOf<JSONObject>()
        val bodyJson = """{ "key" : "$key",
                            "userId" : "$userId",
                            "kuldo" : "$kuldo"
                          }"""
        val (request, response, result)
                = Fuel.post("https://dcset.ditusz.hu/dcset/responseusers")
            .body(bodyJson)
            .response()



        if (response.statusCode==200){
        val a=JSONArray(String(response.data))
        for (i in 0..a.length()-1) {
         list.add(JSONObject(a[i].toString()))
        }
            return list}

        list.add(JSONObject("""{"name":"Error!"}"""))
        return list
    }


    override fun onPostExecute(result: List<JSONObject>?) {
        result?.let {
            allUsers.fetchUsers(it)
        }
    }



}