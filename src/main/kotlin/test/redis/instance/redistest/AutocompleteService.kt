package test.redis.instance.redistest
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class AutocompleteService() {
    val stringMap = mutableMapOf<String, MutableList<String>>()

    //l :[Localization, Localization Retail]
    //lo : [Localization, Localization Retail]
    @Cacheable(value = ["autocompleteCache"], key = "#query.toLowerCase()")
    fun getSuggestions(query: String): List<String> {
        val mutableList = mutableListOf<String>()
        stringMap.forEach{ (k, v) -> println("key:$k, val:$v")}
        try {
            Thread.sleep(3000)
            stringMap[query]?.let { mutableList.addAll(it) }
        } catch (ex:Exception){
            println(ex)
        }
        println("Fetching from DB")
        mutableList.forEach{x -> println(x) }
        return mutableList
    }

    //Localization
    //Localization Retail
    //Localization Warehouse
    //Loc


    //L :[Localization, Localization Retail]
    //Lo : [Localization, Localization Retail]
    //Loc:
    //Loca:

    fun addNewEntry(entry: String, cacheable: (String, String)->Unit) {
        val length = minOf(entry.length, 5)
        for (i in 1..length) {
            val partialString = entry.substring(0, i).lowercase(Locale.getDefault())
            cacheable(partialString, entry)
        }
    }

    //L :[Localization, Localization Retail]
    //Lo : [Localization, Localization Retail]
    @Cacheable(value = ["autocompleteCache"], key = "#partialString.toLowerCase()")
    fun addToStringMapWithCache(partialString: String, entry: String): List<String> {
        if (stringMap.containsKey(partialString))
            stringMap[partialString]?.add(entry)
        else {
            val mutableList = mutableListOf(entry)
            stringMap[partialString] = mutableList
        }
        return stringMap[partialString]?: emptyList()
    }

    fun addToStringMapWithOutCache(partialString: String, entry: String): List<String> {
        if (stringMap.containsKey(partialString))
            stringMap[partialString]?.add(entry)
        else {
            val mutableList = mutableListOf(entry)
            stringMap[partialString] = mutableList
        }
        return stringMap[partialString]?: emptyList()
    }
}
